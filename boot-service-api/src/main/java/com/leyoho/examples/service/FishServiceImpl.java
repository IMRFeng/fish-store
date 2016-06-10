package com.leyoho.examples.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.leyoho.examples.entity.Fish;
import com.leyoho.examples.repository.FishesRepository;

/**
 * This class encapsulates all business behaviors operating on the Fish entity model object.
 * 
 * @author Victor Feng
 */
@Service("fishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FishServiceImpl implements FishService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
     * The Spring Data repository for Fish entities.
     */
	@Autowired
	private FishesRepository fishesRepository;

	@Override
	public Collection<Fish> findAllFishes() {
		return fishesRepository.findAll();
	}

	@Override
	@Cacheable(value = "fishes", key = "#id")
	public Fish findById(Long id) {
		return fishesRepository.findOne(id);
	}

	@Override
	public Fish findByName(String name) {
		Collection<Fish> fishes = findAllFishes();

		return fishes.stream().filter(fish -> fish.getName().equalsIgnoreCase(name)).findFirst().get();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "fishes", key = "#result.id")
	public Fish saveFish(Fish fish) {
		
		// Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
		if (fish.getId() != null) {
			// Cannot create Fish with specified ID value
			logger.error("Attempted to create a Fish, but id attribute was not null.");
			throw new EntityExistsException("The id attribute must be null to persist a new entity.");
		}

		Fish savedFish = fishesRepository.save(fish);
		// Illustrate transaction roll back
//		if (savedFish.getId() == 11L) {
//			throw new RuntimeException("Roll me back!");
//		}
		
		return savedFish;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "fishes", key = "#fish.id")
	public Fish updateFish(Fish fish) {
		Fish fishPersisted = findById(fish.getId());
		if (fishPersisted == null) {
			// Cannot update Fish without specified ID value
			logger.error("Attempted to update a Fish, but the entity does not exist.");
			throw new NoResultException("Requested entity not found.");
		}
		
		return fishesRepository.save(fish);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "fishes", key = "#id")
	public void deleteFishById(Long id) {
		fishesRepository.delete(id);
	}

	@Override
	public boolean isFishExisted(Fish fish) {
		return findById(fish.getId()) != null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "fishes")
	public void deleteAllFishes() {
		fishesRepository.deleteAll();
	}
	
	@Override
	@CacheEvict(value = "fishes", allEntries = true)
	public void evictCache() {
	}

}