package com.lippujarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//@RepositoryRestResource
public interface EventRepository extends CrudRepository<Event, Long> {
	List<Event> findByTitle(/* @Param("title") */ String title);
}
