package com.theironyard.timeless.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.timeless.models.Entry;
import com.theironyard.timeless.services.EntryRepository;

public class EntryControllerTests {
	
	private EntryRepository repo;
	private EntryController controller;
	private Entry entry;
	
	@Before
	public void setUp() {
		repo = mock(EntryRepository.class);
		entry = new Entry();
		controller = new EntryController(repo);
	}

	@Test
	public void test_redirect_to_entries() {
		// Arrange
		
		// Act
		String actual = controller.redirectToEntries();
		
		// Assert
		assertThat(actual).isEqualTo("redirect:/entries");
	}
	
	@Test
	public void test_showEntriesForm_adds_message_and_gets_current_and_entries_and_puts_them_into_view_models_for_the_view() throws IOException, ParseException {
		// Arrange
		
		// Act
		ModelAndView actual = controller.showEntriesForm("Hi");
		
		// Assert
		verify(repo).getCurrent();
		verify(repo).getEntries();
		assertThat(actual.getViewName()).isEqualTo("timeless/default");
		Map<String, Object> map = actual.getModel();
		assertThat(map.get("error")).isEqualTo("Hi");
		assertThat(map.get("current")).isNotNull();
		assertThat(map.get("entries")).isNotNull();
	}
	
	@Test
	public void test_showEntriesForm_adds_io_error_message_and_removes_entries_on_IOException() throws IOException, ParseException {
		// Arrange
		when(repo.getCurrent()).thenThrow(new IOException());
		
		// Act
		ModelAndView actual = controller.showEntriesForm("Hi");
		
		// Assert
		verify(repo).getCurrent();
		assertThat(actual.getViewName()).isEqualTo("timeless/default");
		Map<String, Object> map = actual.getModel();
		assertThat(map.get("error")).isEqualTo("Failed to load entries from data file.");
		assertThat(map.get("current")).isNull();
		assertThat(map.get("entries")).isNull();
	}
	
	@Test
	public void test_handleSubmission_calls_updateCurrent_with_entry_when_action_is_update_and_redirects_to_entries() throws IOException, ParseException {
		// Arrange

		// Act
		String actual = controller.handleSubmission(entry, "update");
		
		// Assert
		verify(repo).updateCurrent(entry);
		assertThat(actual).isEqualTo("redirect:/entries");
	}
	
	@Test
	public void test_handleSubmission_calls_updateCurrent_and_closeOutCurrent_when_action_is_complete_and_redirects_to_entries() throws IOException, ParseException {
		// Arrange

		// Act
		String actual = controller.handleSubmission(entry, "complete");
		
		// Assert
		verify(repo).updateCurrent(entry);
		verify(repo).closeOutCurrent();
		assertThat(actual).isEqualTo("redirect:/entries");
	}

	
	@Test
	public void test_redirects_to_entries_with_a_message_on_IOException() throws IOException, ParseException {
		// Arrange
		doThrow(new IOException()).when(repo).updateCurrent(any());

		// Act
		String actual = controller.handleSubmission(entry, "complete");
		
		// Assert
		assertThat(actual).isEqualTo("redirect:/entries?message=Failed to save changes");
	}

}
