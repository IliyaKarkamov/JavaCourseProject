package engine.resources;

import engine.resources.exceptions.ResourceLoadException;
import engine.resources.interfaces.IResourceFactory;
import engine.resources.interfaces.IResourceManager;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager implements IResourceManager {
    private final Map<Class<?>, IResourceFactory> factories = new HashMap<>();
    private final Map<String, Object> resources = new HashMap<>();

    public <T> T get(Class<T> resourceClass, String resourceKey) throws ResourceLoadException {
        T resource = (T) resources.get(resourceKey);

        if (resource != null) {
            return resource;
        }

        IResourceFactory<T> factory = factories.get(resourceClass);

        if (factory == null) {
            throw new ResourceLoadException("Failed to load resource! Missing factory class!");
        }

        resource = factory.create(resourceKey);

        resources.put(resourceKey, resource);

        return resource;
    }

    public void registerFactory(IResourceFactory<?> factory) {
        factories.put(factory.getType(), factory);
    }
}
