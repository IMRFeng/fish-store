package com.leyoho.examples.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.leyoho.examples.AbstractControllerTest;
import com.leyoho.examples.entity.Fish;
import com.leyoho.examples.service.FishService;

/**
 * Unit tests for the FishController using Spring MVC Mocks.
 * 
 * <pre>
 * These tests utilize the Spring MVC Mock objects to simulate sending actual
 * HTTP requests to the Controller component, and ensure that the
 * request and response bodies are serialized as expected.
 * </pre>
 * 
 * @author Victor Feng
 */
@Transactional
public class FishesControllerTest extends AbstractControllerTest {

	@Autowired
	private FishService fishService;

	@Before
	public void setUp() {
		super.setUp();
		fishService.evictCache();
	}

	@Test
	public void testGetAllFishes() throws Exception {
		String uri = "/api/fishes";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals("failure - expected HTTP status 200", 200, status);
		assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
	}
	
	@Test
    public void testGetFish() throws Exception {

        String uri = "/api/fishes/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        assertEquals("failure - expected HTTP status 200", 200, status);
        assertTrue("failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetFishNotFound() throws Exception {

        String uri = "/api/fishes/{id}";
        Long id = Long.MAX_VALUE;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        assertEquals("failure - expected HTTP status 404", 404, status);
        assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    @Test
    public void testCreateFish() throws Exception {

        String uri = "/api/fishes";
        Fish fish = new Fish();
        fish.setName("controllerTest");
        fish.setPrice(2.8);
        String inputJson = super.mapToJson(fish);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        assertEquals("failure - expected HTTP status 201", 201, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Fish createdFish = super.mapFromJson(content, Fish.class);

        assertNotNull("failure - expected fish not null",
        		createdFish);
        assertNotNull("failure - expected fish.id not null",
        		createdFish.getId());
        assertEquals("failure - expected fish.name match", "controllerTest",
        		createdFish.getName());

    }

    @Test
    public void testUpdateFish() throws Exception {

        String uri = "/api/fishes/{id}";
        Long id = new Long(1);
        Fish fish = fishService.findById(id);
        String updatedName = fish.getName() + " test";
        fish.setName(updatedName);
        String inputJson = super.mapToJson(fish);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        assertEquals("failure - expected HTTP status 200", 200, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Fish updatedFish = super.mapFromJson(content, Fish.class);

        assertNotNull("failure - expected fish not null",
        		updatedFish);
        assertEquals("failure - expected fish.id unchanged",
        		fish.getId(), updatedFish.getId());
        assertEquals("failure - expected updated fish text match",
        		updatedName, updatedFish.getName());

    }

    @Test
    public void testDeleteFish() throws Exception {

        String uri = "/api/fishes/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        assertEquals("failure - expected HTTP status 204", 204, status);
        assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

        Fish deletedFish = fishService.findById(id);

        assertNull("failure - expected fish to be null",
        		deletedFish);

    }
}
