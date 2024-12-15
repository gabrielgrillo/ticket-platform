package it.lessons.ticketplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.ticketplatform.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
