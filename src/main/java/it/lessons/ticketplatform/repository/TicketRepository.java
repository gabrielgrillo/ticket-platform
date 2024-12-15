package it.lessons.ticketplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.ticketplatform.model.Tickets;


public interface TicketRepository extends JpaRepository<Tickets, Integer>{

	public List<Tickets> findByNameContaining(String name);

}
