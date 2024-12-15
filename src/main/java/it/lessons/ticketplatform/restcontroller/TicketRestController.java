package it.lessons.ticketplatform.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.lessons.ticketplatform.model.Tickets;
import it.lessons.ticketplatform.repository.TicketRepository;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {
	
	@Autowired
	private TicketRepository ticketRepo;
	
	@GetMapping
	public ResponseEntity<List<Tickets>> index(@RequestParam(name = "keyword", required = false) String keyword){
		
		try {	
		
		if(keyword != null && !keyword.isBlank()) {
			
			return new ResponseEntity<List<Tickets>>(ticketRepo.findByNameContaining(keyword), HttpStatus.OK);
			
		}else {
			return ResponseEntity.ok(ticketRepo.findAll());
			}
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tickets> update(@PathVariable Integer id, @RequestBody Tickets ticket){
		
		try {
			
		Optional<Tickets> byId = ticketRepo.findById(id);
		Tickets dbTicket = byId.get();
		
		dbTicket.setName(ticket.getName());
		dbTicket.setDescription(ticket.getDescription());
		
		return ResponseEntity.ok(dbTicket);

		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();		
			}		
		}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		
		try {
			ticketRepo.deleteById(id);
		}
		catch (Exception e) {
			System.err.println("Errore in fase di cancellazione");
		}
		
	}
	
	@PostMapping
	public Tickets create(@Valid @RequestBody Tickets ticket) {
		
		return ticketRepo.save(ticket);
	}
	

}
