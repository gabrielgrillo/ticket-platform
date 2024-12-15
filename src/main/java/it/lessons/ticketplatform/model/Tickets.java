package it.lessons.ticketplatform.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Tickets {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	@NotNull
	private String name;

	@NotBlank
	@NotNull
	private String description;

	@OneToMany(mappedBy = "tickets", cascade = CascadeType.REMOVE)
	private List<Note> notes;

	@ManyToMany
	@JoinTable(name = "categories_tickets", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;
	
	@ManyToOne
    @JoinColumn(name = "operator_id", nullable = true) 
    private User assignedOperator;

    // Getter e Setter
    public User getAssignedOperator() {
        return assignedOperator;
    }

    public void setAssignedOperator(User assignedOperator) {
        this.assignedOperator = assignedOperator;
    }

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
