package engine.resources.interfaces;

import engine.resources.exceptions.ResourceLoadException;

public interface IResourceFactory<T> {
    Class<T> getType();
    T create(String resource) throws ResourceLoadException;
}
