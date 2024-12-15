package it.lessons.ticketplatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.lessons.ticketplatform.model.Note;
import it.lessons.ticketplatform.repository.NoteRepository;
import it.lessons.ticketplatform.repository.TicketRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private NoteRepository noteRepo;

	@Autowired
	TicketRepository ticketRepo;

	@GetMapping
	public String index(Model model) {
		List<Note> all = noteRepo.findAll();

		model.addAttribute("notes", all);
		model.addAttribute("newnot", new Note());

		return "notes/index";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model, Authentication authentication) {

		if (bindingResult.hasErrors()) {

			return "notes/edit";
		}
		
		String username = authentication.getName();
	    note.setCreatedBy(username);      
	    
		noteRepo.save(note);

		return "redirect:/tickets/show/" + note.getTickets().getId();
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {

		noteRepo.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Ticked deleted!");

		return "redirect:/notes";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {

		Note note = noteRepo.findById(id).get();

		model.addAttribute("note", note);
		model.addAttribute("edit", true);

		return "notes/edit";
	}

	@PostMapping("/edit/{id}")
	public String edit(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model, Authentication authentication) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			return "notes/edit";
		}
		String username = authentication.getName();
	    note.setCreatedBy(username);   
		
		note.setValid(false);
		noteRepo.save(note);

		return "redirect:/tickets/show/" + note.getTickets().getId();

	}

}
