#version 400 core
out vec4 FragColor;

in vec2 TexCoord;
in vec3 Normal;
in vec3 toLightVector;

struct Material
{
    vec4 ambientColor;
    vec4 diffuseColor;
    vec4 specularColor;

    float shininess;

    sampler2D diffuseTexture;
};

uniform Material material;

void main()
{
    vec3 unitNormal = normalize(Normal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.1);

    vec3 diffuse = brightness * vec3(1.0, 1.0, 1.0);d


    FragColor = vec4(diffuse, 1.0) * texture(material.diffuseTexture, TexCoord);
}