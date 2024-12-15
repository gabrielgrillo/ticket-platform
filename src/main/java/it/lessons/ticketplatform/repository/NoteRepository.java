package it.lessons.ticketplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.ticketplatform.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
