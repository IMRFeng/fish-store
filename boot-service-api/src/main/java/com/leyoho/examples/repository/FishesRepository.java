package com.leyoho.examples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leyoho.examples.entity.Fish;

@Repository
public interface FishesRepository extends JpaRepository<Fish, Long> {

}
