package com.theironyard.timeless.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.timeless.models.Entry;
import com.theironyard.timeless.services.EntryRepository;
import com.theironyard.timeless.viewModels.EntryViewModel;

@Controller
@RequestMapping("/")
public class EntryController {

	private EntryRepository repository;

	public EntryController(EntryRepository repository) {
		this.repository = repository;
	}

	@GetMapping("")
	public String redirectToEntries() {
		return "redirect:/entries";
	}

	@GetMapping("entries")
	public ModelAndView showEntriesForm(String message) {
		ModelAndView mv = new ModelAndView("timeless/default");
		mv.addObject("error", message);
		try {
			mv.addObject("current", new EntryViewModel(repository.getCurrent()));
			mv.addObject("entries", EntryViewModel.fromEntries(repository.getEntries()));
		} catch (IOException | ParseException e) {
			mv.addObject("entries", null);
			mv.addObject("error", "Failed to load entries from data file.");
		}
		return mv;
	}

	@PostMapping("entries")
	public String handleSubmission(Entry entry, @RequestParam String action) {
		try {
			repository.updateCurrent(entry);
			if (action.equals("complete")) {
				repository.closeOutCurrent();
			}
		} catch (IOException | ParseException e) {
			return "redirect:/entries?message=Failed to save changes";
		}
		return "redirect:/entries";
	}

}
