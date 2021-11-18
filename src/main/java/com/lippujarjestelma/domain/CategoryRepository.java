package com.lippujarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {
	List<Category> findByName(/* @Param("name") */ String name);
}
