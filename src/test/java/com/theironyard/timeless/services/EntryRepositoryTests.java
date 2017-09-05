package com.theironyard.timeless.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import com.theironyard.timeless.models.Entry;

public class EntryRepositoryTests {

	private EntryReaderWriter rw;
	private EntryRepository repo;
	private Entry current;
	private List<Entry> submitted;
	
	@Captor
	private ArgumentCaptor<List<Entry>> captor;
	
	@Before
	public void setUp() {
		rw = mock(EntryReaderWriter.class);
		repo = new EntryRepository(rw);
		current = new Entry();
		submitted = new ArrayList<>();
		submitted.add(new Entry());
		submitted.add(new Entry());
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_getCurrent_calls_rw_getCurrent_and_returns_that_value() throws IOException, ParseException {
		// Arrange
		when(rw.getCurrent()).thenReturn(current);
		
		// Act
		Entry actual = repo.getCurrent();
		
		// Assert
		assertThat(actual).isSameAs(current);
		verify(rw).getCurrent();
	}
	
	@Test
	public void test_getEntries_calls_rw_getSubmitted_and_returns_that_value() throws IOException, ParseException {
		// Arrange
		when(rw.getSubmitted()).thenReturn(submitted);
		
		// Act
		List<Entry> actual = repo.getEntries();
		
		// Assert
		assertThat(actual).contains(submitted.get(0));
		assertThat(actual).contains(submitted.get(1));
		verify(rw).getSubmitted();
	}
	
	@Test
	public void test_two_calls_to_getCurrent_results_in_only_one_call_to_rw_getCurrent() throws IOException, ParseException {
		// Arrange
		
		// Act
		repo.getCurrent();
		repo.getCurrent();
		
		// Assert
		verify(rw).getCurrent();
	}
	
	@Test
	public void test_two_calls_to_getEntries_results_in_only_one_call_to_rw_getSubmitted() throws IOException, ParseException {
		// Arrange
		
		// Act
		repo.getEntries();
		repo.getEntries();
		
		// Assert
		verify(rw).getSubmitted();
	}
	
	@Test
	public void test_updateCurrent_sets_date_and_hours_of_current_and_writes_the_changes() throws IOException, ParseException {
		// Arrange
		Date now = Calendar.getInstance().getTime();
		double[] hours = {};
		Entry entry = new Entry();
		entry.setDate(now);
		entry.setHours(hours);
		when(rw.getCurrent()).thenReturn(current);
		
		// Act
		repo.updateCurrent(entry);
		
		// Assert
		verify(rw).getCurrent();
		verify(rw).writeAll(eq(current), any());
		assertThat(current.getDate()).isSameAs(now);
		assertThat(current.getHours()).isSameAs(hours);
	}
	
	@Test
	public void test_closeOutCurrent_adds_current_to_entries_and_creates_a_new_current_and_writes_everything() throws IOException, ParseException {
		// Arrange
		when(rw.getCurrent()).thenReturn(current);
		
		// Act
		repo.closeOutCurrent();
		
		// Assert
		verify(rw).getCurrent();
		verify(rw).writeAll(AdditionalMatchers.not(eq(current)), captor.capture());
		assertThat(repo.getEntries()).contains(current);
		assertThat(captor.getValue()).contains(current);
	}
	
}
