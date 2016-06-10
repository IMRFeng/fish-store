package com.leyoho.examples.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.After;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.leyoho.examples.AbstractTest;
import com.leyoho.examples.entity.Fish;

/**
 * Unit test methods for the FishService and FishServiceImpl.
 * 
 * @author Victor Feng
 */
@Transactional
public class FishServiceTest extends AbstractTest {

	@Autowired
	private FishService service;

	@Before
	public void setUp() {
		service.evictCache();
	}

	@Test
	public void testFindAll() {

		Collection<Fish> list = service.findAllFishes();

		assertNotNull("failure - expected not null", list);
		assertEquals("failure - expected list size", 9, list.size());

	}

	@Test
	public void testFindById() {

		Long id = new Long(1);

		Fish entity = service.findById(id);

		assertNotNull("failure - expected not null", entity);
		assertEquals("failure - expected id attribute match", id, entity.getId());

	}
	
	@Test
	public void testFindByName() {
		String name = "Lobster";
		
		Fish fish = service.findByName(name);
		
		assertNotNull("failure - expected not null", fish);
		assertEquals("failure - expected name attribute match", name, fish.getName());
	}

	@Test
	public void testIsFishExisted() {
		Fish fish = new Fish();
		fish.setId(1L);

		boolean fishExisted = service.isFishExisted(fish);
		assertTrue("failure - expected true", fishExisted);
		
		fish.setId(Long.MAX_VALUE);
		boolean fishNotExisted = service.isFishExisted(fish);
		assertFalse("failure - expected false", fishNotExisted);
	}

	@Test
	public void testFindOneNotFound() {

		Long id = Long.MAX_VALUE;

		Fish entity = service.findById(id);

		assertNull("failure - expected null", entity);
	}

	@Test
	public void testCreate() {

		Fish entity = new Fish();
		entity.setName("testFishName");
		entity.setPrice(1.86);

		Fish createdEntity = service.saveFish(entity);

		assertNotNull("failure - expected not null", createdEntity);
		assertNotNull("failure - expected id attribute not null", createdEntity.getId());
		assertEquals("failure - expected text attribute match", "testFishName", createdEntity.getName());

		Collection<Fish> list = service.findAllFishes();

		assertEquals("failure - expected size", 10, list.size());
	}

	@Test
	public void testCreateWithId() {

		Exception exception = null;
		Fish entity = new Fish();
		entity.setId(1L);
		entity.setName("test");
		entity.setPrice(1.80);

		try {
			service.saveFish(entity);
		} catch (EntityExistsException e) {
			exception = e;
		}

		assertNotNull("failure - expected exception", exception);
		assertTrue("failure - expected EntityExistsException", exception instanceof EntityExistsException);
	}

	@Test
	public void testCreateWithThrowException() {

		Exception exception = null;
		Fish entity = new Fish();
		entity.setId(11L);
		entity.setName("testRollBack");
		entity.setPrice(1.80);

		try {
			service.saveFish(entity);
		} catch (RuntimeException e) {
			exception = e;
		}

		assertNotNull("failure - expected exception", exception);
		assertTrue("failure - expected RuntimeException", exception instanceof RuntimeException);
	}

	@Test
	public void testUpdate() {

		Long id = new Long(1);

		Fish entity = service.findById(id);

		assertNotNull("failure - expected not null", entity);

		String updatedText = entity.getName() + " test";
		entity.setName(updatedText);
		Fish updatedEntity = service.updateFish(entity);

		assertNotNull("failure - expected not null", updatedEntity);
		assertEquals("failure - expected id attribute match", id, updatedEntity.getId());
		assertEquals("failure - expected text attribute match", updatedText, updatedEntity.getName());
	}

	@Test
	public void testUpdateNotFound() {

		Exception exception = null;

		Fish entity = new Fish();
		entity.setId(Long.MAX_VALUE);
		entity.setName("test");

		try {
			service.updateFish(entity);
		} catch (NoResultException e) {
			exception = e;
		}

		assertNotNull("failure - expected exception", exception);
		assertTrue("failure - expected NoResultException", exception instanceof NoResultException);
	}

	@Test
	public void testDelete() {

		Long id = new Long(1);

		Fish entity = service.findById(id);

		assertNotNull("failure - expected not null", entity);

		service.deleteFishById(id);

		Collection<Fish> list = service.findAllFishes();

		assertEquals("failure - expected size", 8, list.size());

		Fish deletedEntity = service.findById(id);

		assertNull("failure - expected null", deletedEntity);
	}

	@Test
	public void testDeleteAllFishes() {
		Collection<Fish> beforeRemoveList = service.findAllFishes();

		assertEquals("failure - expected size", 9, beforeRemoveList.size());

		service.deleteAllFishes();
		Collection<Fish> afterRemovedList = service.findAllFishes();

		assertEquals("failure - expected size", 0, afterRemovedList.size());
	}

	@After
	public void tearDown() {
		// Clean up after each test method
	}
}