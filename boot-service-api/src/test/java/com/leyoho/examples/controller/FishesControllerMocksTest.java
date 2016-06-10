package com.leyoho.examples.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.leyoho.examples.AbstractControllerTest;
import com.leyoho.examples.entity.Fish;
import com.leyoho.examples.service.FishService;

/**
 * Unit tests for the FishesController using Mockito mocks and spies.
 * 
 * These tests utilize the Mockito framework objects to simulate interaction
 * with back-end components. The controller methods are invoked directly
 * bypassing the Spring MVC mappings. Back-end components are mocked and
 * injected into the controller. Mockito spies and verifications are performed
 * ensuring controller behaviors.
 * 
 * @author Victor Feng
 */
@Transactional
public class FishesControllerMocksTest extends AbstractControllerTest {

	/**
     * A mocked FishService
     */
    @Mock
    private FishService fishService;

    /**
     * A FishesController instance with <code>@Mock</code> components injected
     * into it.
     */
    @InjectMocks
    private FishesController fishesController;

    /**
     * Setup each test method. Initialize Mockito mock and spy objects. Scan for
     * Mockito annotations.
     */
    @Before
    public void setUp() {
        // Initialize Mockito annotated components
        MockitoAnnotations.initMocks(this);
        // Prepare the Spring MVC Mock components for stand-alone testing
        setUp(fishesController);
    }

    @Test
    public void testGetAllFishes() throws Exception {

        // Create some test data
        Collection<Fish> list = getEntityListStubData();

        // Stub the fishService.findAllFishes method return value
        when(fishService.findAllFishes()).thenReturn(list);

        // Perform the behavior being tested
        String uri = "/api/fishes";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the fishService.findAllFishes method was invoked once
        verify(fishService, times(1)).findAllFishes();

        // Perform standard JUnit assertions on the response
        assertEquals("failure - expected HTTP status 200", 200, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);
    }
    
    @Test
    public void testGetAllFishesNotFound() throws Exception {

        // Stub the fishService.findAllFishes method return value
        when(fishService.findAllFishes()).thenReturn(new ArrayList<Fish>());

        // Perform the behavior being tested
        String uri = "/api/fishes";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the fishService.findAllFishes method was invoked once
        verify(fishService, times(1)).findAllFishes();

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 204", 204, status);
        assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);
    }

    @Test
    public void testGetFish() throws Exception {

        // Create some test data
        Long id = new Long(1);
        Fish entity = getEntityStubData();

        // Stub the fishService.findById method return value
        when(fishService.findById(id)).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/fishes/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the fishService.findById method was invoked once
        verify(fishService, times(1)).findById(id);

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 200", 200, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);
    }

    @Test
    public void testGetFishNotFound() throws Exception {

        // Create some test data
        Long id = Long.MAX_VALUE;

        // Stub the fishService.findById method return value
        when(fishService.findById(id)).thenReturn(null);

        // Perform the behavior being tested
        String uri = "/api/fishes/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the fishService.findById method was invoked once
        verify(fishService, times(1)).findById(id);

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 404", 404, status);
        assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);
    }

    @Test
    public void testCreateFish() throws Exception {

        // Create some test data
    	Fish entity = getEntityStubData();

        // Stub the fishService.saveFish method return value
        when(fishService.saveFish(any(Fish.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/fishes";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the fishService.saveFish method was invoked once
        verify(fishService, times(1)).saveFish(any(Fish.class));

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 201", 201, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Fish createdEntity = super.mapFromJson(content, Fish.class);

        assertNotNull("failure - expected entity not null",
                createdEntity);
        assertNotNull("failure - expected id attribute not null",
                createdEntity.getId());
        assertEquals("failure - expected text attribute match",
                entity.getName(), createdEntity.getName());
    }

    @Test
    public void testUpdateFish() throws Exception {

        // Create some test data
        Fish entity = getEntityStubData();
        entity.setName(entity.getName() + " test");
        Long id = new Long(1);

        // Stub the fishService.updateFish and fishService.isFishExisted methods return value
        when(fishService.isFishExisted(any(Fish.class))).thenReturn(true);
        when(fishService.updateFish(any(Fish.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/fishes/{id}";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the fishService.isFishExisted method was invoked once
        verify(fishService, times(1)).isFishExisted(any(Fish.class));
        // Verify the fishService.updateFish method was invoked once
        verify(fishService, times(1)).updateFish(any(Fish.class));

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 200", 200, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Fish updatedEntity = super.mapFromJson(content, Fish.class);

        assertNotNull("failure - expected entity not null",
                updatedEntity);
        assertEquals("failure - expected id attribute unchanged",
                entity.getId(), updatedEntity.getId());
        assertEquals("failure - expected text attribute match",
                entity.getName(), updatedEntity.getName());
    }
    
    @Test
    public void testUpdateFishWithInternalError() throws Exception {
    	// Create some test data
        Fish entity = getEntityStubData();
        entity.setName(entity.getName() + " test");
        Long id = new Long(1);

        // Stub the fishService.updateFish and fishService.isFishExisted methods return value
        when(fishService.isFishExisted(any(Fish.class))).thenReturn(false);

        // Perform the behavior being tested
        String uri = "/api/fishes/{id}";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status
        int status = result.getResponse().getStatus();

        // Verify the fishService.isFishExisted method was invoked once
        verify(fishService, times(1)).isFishExisted(any(Fish.class));

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 404", 404, status);
    }

    @Test
    public void testDeleteFish() throws Exception {

        // Create some test data
        Long id = new Long(1);

        // Perform the behavior being tested
        String uri = "/api/fishes/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the fishService.deleteFishById method was invoked once
        verify(fishService, times(1)).deleteFishById(any(Long.class));

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 204", 204, status);
        assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);
    }
    
    @Test
    public void testDeleteAllFishes() throws Exception {
    	// Perform the behavior being tested
        String uri = "/api/fishes";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the fishService.deleteAllFishes method was invoked once
        verify(fishService, times(1)).deleteAllFishes();

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 204", 204, status);
        assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);
    }

    private Collection<Fish> getEntityListStubData() {
        Collection<Fish> list = new ArrayList<Fish>();
        list.add(getEntityStubData());
        return list;
    }

    private Fish getEntityStubData() {
    	Fish entity = new Fish();
        entity.setId(1L);
        entity.setName("mockitoTestName");
        return entity;
    }
}
