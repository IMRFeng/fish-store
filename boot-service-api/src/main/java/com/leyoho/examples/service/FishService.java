package com.leyoho.examples.service;

import java.util.Collection;


import com.leyoho.examples.entity.Fish;

/**
 * This interface defines all public business behaviors for operations on the Fish entity model.
 * 
 * This interface should be injected via <code>@Autowired</code> into FishService clients, not the implementation bean.
 * 
 * @author Victor Feng
 */
public interface FishService {

	/**
     * Find a single Fish entity by primary key identifier.
     * 
     * @param id A Long primary key identifier.
     * 
     * @return A Fish or <code>null</code> if none found.
     */
    Fish findById(Long id);

    /**
     * Find a single Fish entity by its name.
     * 
     * @param id A String name.
     * 
     * @return A Fish or <code>null</code> if none found.
     */
    Fish findByName(String name);

    /**
     * Find all Fish entities.
     * 
     * @return A Collection of Fish objects.
     */
    Collection<Fish> findAllFishes();

    /**
     * Persists a Fish entity in the data store.
     * 
     * @param fish A Fish object to be persisted.
     * 
     * @return The persisted Fish entity.
     */
    Fish saveFish(Fish fish);

    /**
     * Updates a previously persisted Fish entity in the data store.
     * 
     * @param fish A Fish object to be updated.
     * 
     * @return The updated Fish entity.
     */
    Fish updateFish(Fish fish);

    /**
     * Removes a previously persisted Fish entity by primary key identifier from the data store.
     * 
     * @param id A Long primary key identifier.
     */
    void deleteFishById(Long id);

    /**
     * Verifies a previously persisted Fish entity in the data store.
     * 
     * @param fish A Fish object to be verified.
     * 
     * @return The verified Fish's status.
     */
    boolean isFishExisted(Fish fish);

    /**
     * Removes all previously persisted Fish entities from the data store.
     */
    void deleteAllFishes();

    /**
     * Evicts all members of the "fishes" cache.
     */
    void evictCache();
}