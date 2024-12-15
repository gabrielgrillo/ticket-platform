package it.lessons.ticketplatform.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.lessons.ticketplatform.model.Note;
import it.lessons.ticketplatform.model.Tickets;
import it.lessons.ticketplatform.model.User;
import it.lessons.ticketplatform.repository.CategoryRepository;
import it.lessons.ticketplatform.repository.TicketRepository;
import it.lessons.ticketplatform.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	

	@GetMapping
	public String index(Authentication authentication, Model model,
			@RequestParam(name = "keyword", required = false) String keyword) {

		List<Tickets> tickets;
		if (keyword != null && !keyword.isBlank()) {
			tickets = ticketRepo.findByNameContaining(keyword);
			model.addAttribute("keyword", keyword);
		} else {

			tickets = ticketRepo.findAll();
		}

		model.addAttribute("ticket", tickets);

		return "/tickets/tickets";
	}

	@GetMapping("/create")
	public String create(Model model) {

		List<User> listUsers = userRepo.findAll();

		
		model.addAttribute("user", listUsers);
		model.addAttribute("ticket", new Tickets());

		return "/tickets/create";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Tickets formTicket, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			List<User> listUsers = userRepo.findAll();

			model.addAttribute("user", listUsers);
			return "/tickets/create";
		}
		
		ticketRepo.save(formTicket);
		redirectAttributes.addFlashAttribute("successMessage", "Ticket created!");

		return "redirect:/tickets";
	}

	@GetMapping("show/{id}")
	public String show(@PathVariable Integer id, Model model) {

		Optional<Tickets> ticketOptional = ticketRepo.findById(id);

		if (ticketOptional.isPresent()) {
			Tickets ticket = ticketOptional.get();
			model.addAttribute("ticket", ticketOptional.get());
			model.addAttribute("operator", ticket.getAssignedOperator());
		}
		
		return ("/tickets/show");
	}

	@GetMapping("edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {

		List<User> listUsers = userRepo.findAll();
		
		model.addAttribute("ticket", ticketRepo.findById(id).get());
		model.addAttribute("user", listUsers);

		return "/tickets/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String update(@PathVariable Integer id, @Valid @ModelAttribute("ticket") Tickets formTicket,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "tickets/edit";
		}
		
	    ticketRepo.save(formTicket);
		redirectAttributes.addFlashAttribute("successMessage", "Ticket updated!");

		return "redirect:/tickets";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

		ticketRepo.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Ticked deleted!");

		return "redirect:/tickets";
	}

	@GetMapping("/{id}/notes")
	public String note(@PathVariable Integer id, Model model) {

		Tickets ticket = ticketRepo.findById(id).get();

		Note notes = new Note();
		notes.setTickets(ticket);
		notes.setCreatedAt(LocalDate.now());

		model.addAttribute("note", notes);

		return "notes/edit";
	}

}
