package engine.resources.factories;

import engine.core.utils.ArrayHelper;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Model;
import engine.graphics.interfaces.IModel;
import engine.renderer.opengl.interfaces.ITexture2D;
import engine.resources.exceptions.ResourceLoadException;
import engine.resources.interfaces.IResourceFactory;
import engine.resources.interfaces.IResourceManager;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.Objects;
import java.util.Vector;

public class ModelFactory implements IResourceFactory<IModel> {
    private IResourceManager resourceManager;

    public ModelFactory(IResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public Class<IModel> getType() {
        return IModel.class;
    }

    @Override
    public IModel create(String resource) throws ResourceLoadException {
        AIScene aiScene = Assimp.aiImportFile(resource, Assimp.aiProcess_JoinIdenticalVertices |
                Assimp.aiProcess_Triangulate | Assimp.aiProcess_FixInfacingNormals);

        if ((aiScene == null) || (aiScene.mFlags() & Assimp.AI_SCENE_FLAGS_INCOMPLETE) == 1 || (aiScene.mRootNode() == null)) {
            throw new ResourceLoadException("Model not loaded correctly. Error: " + Assimp.aiGetErrorString());
        }

        IModel model = new Model();
        Vector<Material> materials = new Vector<>(10);

        processMaterials(aiScene, materials);
        processNode(model, Objects.requireNonNull(aiScene.mRootNode()), aiScene, materials);

        aiScene.free();

        return model;
    }

    @Override
    public IResourceManager getResourceManager() {
        return resourceManager;
    }

    private void processNode(IModel model, AINode aiNode, AIScene aiScene, Vector<Material> materials) {
        PointerBuffer sceneMeshes = aiScene.mMeshes();
        IntBuffer nodeMeshes = aiNode.mMeshes();

        assert sceneMeshes != null;
        assert nodeMeshes != null;

        for (int i = 0; i < aiNode.mNumMeshes(); i++) {
            AIMesh aiMesh = AIMesh.create(sceneMeshes.get(nodeMeshes.get(i)));
            model.addMesh(processMesh(aiMesh, materials));
        }

        PointerBuffer children = aiNode.mChildren();

        if (children != null) {
            while (children.hasRemaining()) {
                AINode childNode = AINode.create(children.get());
                processNode(model, childNode, aiScene, materials);
            }
        }
    }

    private Mesh processMesh(AIMesh aiMesh, Vector<Material> materials) {
        final int verticesCount = aiMesh.mNumVertices();

        Vector<Float> positions = new Vector<>(verticesCount * 3);
        Vector<Float> normals = new Vector<>(verticesCount * 3);
        Vector<Float> texCoords = new Vector<>(verticesCount * 2);
        Vector<Integer> indices = new Vector<>();

        processVertices(aiMesh, positions);
        processNormals(aiMesh, normals);
        processTextureCoords(aiMesh, texCoords);
        processIndices(aiMesh, indices);

        if (normals.isEmpty() && !positions.isEmpty()) {
            for (Float position : positions) {
                normals.add((float) 0.0);
            }
        }

        final int materialIndex = aiMesh.mMaterialIndex();

        Material material;

        if (materialIndex >= 0 && materialIndex < materials.size()) {
            material = materials.get(materialIndex);
        } else {
            material = new Material();
        }

        return new Mesh(ArrayHelper.toArray(positions, 0),
                ArrayHelper.toArray(normals, 0),
                ArrayHelper.toArray(texCoords, 0),
                ArrayHelper.toArray(indices, 0),
                material);
    }

    private void processVertices(AIMesh aiMesh, Vector<Float> positions) {
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();

        while (aiVertices.hasRemaining()) {
            AIVector3D aiVertex = aiVertices.get();

            positions.add(aiVertex.x());
            positions.add(aiVertex.y());
            positions.add(aiVertex.z());
        }
    }

    private void processNormals(AIMesh aiMesh, Vector<Float> normals) {
        AIVector3D.Buffer aiNormals = aiMesh.mNormals();

        while (aiNormals != null && aiNormals.hasRemaining()) {
            AIVector3D aiNormal = aiNormals.get();

            normals.add(aiNormal.x());
            normals.add(aiNormal.y());
            normals.add(aiNormal.z());
        }
    }

    private void processTextureCoords(AIMesh aiMesh, Vector<Float> texCoords) {
        AIVector3D.Buffer aiTextures = aiMesh.mTextureCoords(0);

        if (aiTextures != null) {
            while (aiTextures.hasRemaining()) {
                AIVector3D aiTexCoords = aiTextures.get();

                texCoords.add(aiTexCoords.x());
                texCoords.add(aiTexCoords.y());
            }
        } else {
            texCoords.add(0.f);
            texCoords.add(0.f);
        }
    }

    private void processIndices(AIMesh aiMesh, Vector<Integer> indices) {
        int indicesCount = 0;

        int numFaces = aiMesh.mNumFaces();
        AIFace.Buffer aiFaces = aiMesh.mFaces();

        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get();
            IntBuffer buffer = aiFace.mIndices();

            indicesCount += buffer.remaining();
        }

        aiFaces = aiFaces.position(0);

        indices.ensureCapacity(indicesCount);

        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get();
            IntBuffer buffer = aiFace.mIndices();

            while (buffer.remaining() > 0) {
                indices.add(buffer.get());
            }
        }
    }

    private void processMaterials(AIScene aiScene, Vector<Material> materials) throws ResourceLoadException {
        PointerBuffer aiMaterials = aiScene.mMaterials();

        if (aiMaterials == null) {
            return;
        }

        while (aiMaterials.hasRemaining()) {
            AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get());
            processMaterial(aiMaterial, materials);
        }
    }

    private void processMaterial(AIMaterial aiMaterial, Vector<Material> materials) throws ResourceLoadException {
        ITexture2D diffuseTexture;
        ITexture2D specularTexture;
        ITexture2D emissionTexture;

        float shininess;

        diffuseTexture = processTexture(aiMaterial, Assimp.aiTextureType_DIFFUSE);
        specularTexture = processTexture(aiMaterial, Assimp.aiTextureType_SPECULAR);
        emissionTexture = processTexture(aiMaterial, Assimp.aiTextureType_EMISSIVE);

        {
            float[] floatBuf = new float[1];
            int[] size = new int[]{1};

            Assimp.aiGetMaterialFloatArray(aiMaterial, Assimp.AI_MATKEY_SHININESS, Assimp.aiTextureType_NONE, 0, floatBuf, size);

            shininess = floatBuf[0];
        }

        Material material = new Material();

        material.setDiffuseTexture(diffuseTexture);
        material.setSpecularTexture(specularTexture);
        material.setEmissionTexture(emissionTexture);
        material.setShininess(shininess);

        materials.add(material);
    }

    private ITexture2D processTexture(AIMaterial aiMaterial, int textureType) throws ResourceLoadException {
        AIString path = AIString.calloc();
        Assimp.aiGetMaterialTexture(aiMaterial, textureType, 0, path, (IntBuffer) null, null, null, null, null, null);
        String texturePath = path.dataString();

        ITexture2D texture = null;

        if (texturePath != null && texturePath.length() > 0) {
            texture = getResourceManager().get(ITexture2D.class, texturePath);
        }

        return texture;
    }
}
