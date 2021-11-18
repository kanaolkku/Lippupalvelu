package com.lippujarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//@RepositoryRestResource
public interface TicketRepository extends CrudRepository<Ticket, Long> {
	List<Ticket> findByTicketId(/* @Param("ticketId") */ Long ticketId);
}
