package ca.purpleowl.example.timezones.service;

public abstract class AbstractService<E, A> {
    abstract A entityToAsset(E entity);
    abstract E assetToEntity(A asset);
}
