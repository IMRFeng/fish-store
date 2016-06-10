package com.leyoho.examples.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.leyoho.examples.entity.Fish;
import com.leyoho.examples.service.FishService;

/**
 * This class is a RESTful web service controller. The
 * <code>@RestController</code> annotation informs Spring that each
 * <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>
 * which, by default, contains a ResponseEntity converted into JSON with an
 * associated HTTP status code.
 * 
 * @author Victor Feng
 */
@RestController
public class FishesController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	 /**
     * The FishService business service.
     */
	@Autowired
	FishService fishService;

	 /**
     * Web service end-point to fetch all Fish entities.
     * 
     * @return A ResponseEntity containing a Collection of Fish objects as JSON.
     */
	@CrossOrigin
	@RequestMapping(value = "/api/fishes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Fish>> getAllFishes() {
		Collection<Fish> fishes = fishService.findAllFishes();

		if (fishes.isEmpty()) {
			logger.warn("No fishes at all");
			// Or return HttpStatus.NOT_FOUND
			return new ResponseEntity<Collection<Fish>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Collection<Fish>>(fishes, HttpStatus.OK);
	}

	/**
     * Web service end-point to fetch a single Fish entity by primary key identifier.
     * 
     * If found, the Fish is returned as JSON with HTTP status 200.
     * 
     * If not found, the service returns an empty response body with HTTP status 404.
     * 
     * @param id A Long URL path variable containing the Fish primary key identifier.
     * @return A ResponseEntity containing a single Fish object, if found,
     *         and a HTTP status code as described in the method comment.
     */
	@RequestMapping(value = "/api/fishes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fish> getFish(@PathVariable("id") long id) {
		logger.info("Fetching Fish with id " + id);
		Fish fish = fishService.findById(id);
		if (fish == null) {
			logger.warn("Fish with id " + id + " not found");
			return new ResponseEntity<Fish>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Fish>(fish, HttpStatus.OK);
	}

	  /**
     * Web service end-point to create a single Fish entity. 
     * The HTTP request body is expected to contain a Fish object in JSON format.
     * 
     * If created successfully, the persisted Fish is returned as JSON and headers with HTTP status 201.
     * 
     * If not created successfully, the service returns an empty response body with HTTP status 409.
     * 
     * @param fish The Fish object to be created.
     * 
     * @return A ResponseEntity containing a single Fish object, if created successfully, 
     * 			and a HTTP status code as described in the method comment.
     */
	@RequestMapping(value = "/api/fishes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fish> createFish(@RequestBody Fish fish, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Fish " + fish.getName());
//		if (fishService.findByName(fish.getName()) != null) {
//			logger.error("A Fish with name " + fish.getName() + " already exist");
//			return new ResponseEntity<Fish>(HttpStatus.CONFLICT);
//		}

		Fish savedFish = fishService.saveFish(fish);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/fishes/{id}").buildAndExpand(fish.getId()).toUri());
		return new ResponseEntity<Fish>(savedFish, headers, HttpStatus.CREATED);
	}

	/**
     * Web service end-point to update a single Fish entity. 
     * The HTTP request body is expected to contain a Fish object in JSON format.
     * 
     * If updated successfully, the persisted Fish is returned as JSON with HTTP status 200.
     * 
     * If not found, the service returns an empty response body and HTTP status 404.
     * 
     * @param fish The Fish object to be updated.
     * 
     * @return A ResponseEntity containing a single Fish object, if updated successfully, 
     *         and a HTTP status code as described in the method comment.
     */
	@RequestMapping(value = "/api/fishes/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fish> updateFish(@RequestBody Fish fish) {
		logger.info("Updating Fish " + fish.getId());

		boolean isExisted = fishService.isFishExisted(fish);

		if (!isExisted) {
			logger.error("Fish with id " + fish.getId() + " not found");
			// or return HttpStatus.NOT_FOUND
			return new ResponseEntity<Fish>(HttpStatus.NOT_FOUND); 
		}
		Fish updatedFish = fishService.updateFish(fish);

		return new ResponseEntity<Fish>(updatedFish, HttpStatus.OK);
	}

	/**
     * Web service end-point to delete a single Fish entity. 
     * The primary key identifier of the Fish to be deleted is supplied in the URL as a path variable.
     * 
     * If deleted successfully, the service returns an empty response body with HTTP status 204.
     * 
     * If not deleted successfully, the service returns an empty response body with HTTP status 404.
     * 
     * @param id A Long URL path variable containing the Fish primary key identifier.
     *        
     * @return A ResponseEntity with an empty response body and a HTTP status
     *         code as described in the method comment.
     */
	@RequestMapping(value = "/api/fishes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Fish> deleteFish(@PathVariable("id") Long id) {
		logger.info("Deleting Fish with id " + id);

//		Fish fish = fishService.findById(id);
//		if (fish == null) {
//			logger.warn("Unable to delete. Fish with id " + id + " not found");
//			return new ResponseEntity<Fish>(HttpStatus.NOT_FOUND);
//		}

		fishService.deleteFishById(id);
		return new ResponseEntity<Fish>(HttpStatus.NO_CONTENT);
	}

	 /**
     * Web service end-point to delete all Fish entities. The HTTP request
     * body is empty.
     * 
     * If deleted successfully, the service returns an empty response body with HTTP status 204.
     * 
     * If not deleted successfully, the service returns an empty response body with HTTP status 500.
     * 
     * @return A ResponseEntity with an empty response body and a HTTP status
     *         code as described in the method comment.
     */
	@RequestMapping(value = "/api/fishes", method = RequestMethod.DELETE)
	public ResponseEntity<Fish> deleteAllFishes() {
		logger.info("Deleting All Fishes");

		fishService.deleteAllFishes();
		return new ResponseEntity<Fish>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Set up which fields cannot be modified. e.g. id
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id");
	}

}