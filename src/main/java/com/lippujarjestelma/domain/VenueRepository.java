package com.lippujarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//@RepositoryRestResource
public interface VenueRepository extends CrudRepository<Venue, Long> {
	List<Venue> findByName(/* @Param("name") */ String name);
}
