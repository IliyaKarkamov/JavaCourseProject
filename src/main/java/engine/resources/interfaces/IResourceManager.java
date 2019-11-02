package engine.resources.interfaces;

import engine.resources.exceptions.ResourceLoadException;

public interface IResourceManager {
    <T> T get(Class<T> resourceClass, String resourceKey) throws ResourceLoadException;

    void registerFactory(IResourceFactory<?> factory);
}
