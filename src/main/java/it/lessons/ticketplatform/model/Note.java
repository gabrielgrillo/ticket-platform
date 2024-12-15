package it.lessons.ticketplatform.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;


@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String notte;
	
	@NotNull(message = "Inserire una data")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "tickets_id", nullable = false)
	@JsonBackReference
	private Tickets tickets;
	
	private String createdBy; 
	
    private String updatedBy;
	
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	private boolean valid = true;
	
	
	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}


	public String getNotte() {
		return notte;
	}


	public void setNotte(String notte) {
		this.notte = notte;
	}


	public LocalDate getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	public Tickets getTickets() {
		return tickets;
	}


	public void setTickets(Tickets tickets) {
		this.tickets = tickets;
	}


	public Note() {
		this.createdAt = LocalDate.now();	}
		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	
}
