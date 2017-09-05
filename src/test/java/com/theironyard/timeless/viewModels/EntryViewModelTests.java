package com.theironyard.timeless.viewModels;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.theironyard.timeless.models.Entry;

public class EntryViewModelTests {
	
	private Entry entry;
	private EntryViewModel model;
	
	@Before
	public void setUp() {
		entry = mock(Entry.class);
		model = new EntryViewModel(entry);
	}

	@Test
	public void test_getTotal_uses_entry_getTotal() {
		// Arrange
		when(entry.getTotal()).thenReturn(3.4);
		
		// Act
		double actual = model.getTotal();
		
		// Assert
		assertThat(actual).isEqualTo(3.4);
		verify(entry).getTotal();
	}
	
	@Test
	public void test_getDate_uses_entry_getDate() {
		// Arrange
		Date now = Calendar.getInstance().getTime();
		when(entry.getDate()).thenReturn(now);
		
		// Act
		Date actual = model.getDate();
		
		// Assert
		assertThat(actual).isEqualTo(actual);
		verify(entry).getDate();
	}
	
	@Test
	public void test_getHours_uses_entry_getHours() {
		// Arrange
		double[] values = {};
		when(entry.getHours()).thenReturn(values);
		
		// Act
		double[] actual = model.getHours();
		
		// Assert
		assertThat(actual).isSameAs(values);
		verify(entry).getHours();
	}
	
	@Test
	public void test_isSubmitted_uses_entry_isSubmitted() {
		// Arrange
		when(entry.isSubmitted()).thenReturn(true);
		
		// Act
		boolean actual = model.isSubmitted();
		
		// Assert
		assertThat(actual).isSameAs(true);
		verify(entry).isSubmitted();
	}
	
	@Test
	public void test_getFormattedDate_returns_mmddyyyy() {
		// Arrange
		Calendar c = Calendar.getInstance();
		c.set(2017, 2, 8);
		when(entry.getDate()).thenReturn(c.getTime());
		
		// Act
		String actual = model.getFormattedDate();
		
		// Assert
		verify(entry).getDate();
		assertThat(actual).isEqualTo("03/08/2017");
	}
	
	@Test
	public void test_fromEntries_returns_list_of_view_models_based_on_entries() {
		// Arrange
		Entry[] entries = {
			mock(Entry.class),
			mock(Entry.class)
		};
		
		// Act
		List<EntryViewModel> models = EntryViewModel.fromEntries(Arrays.asList(entries));
		
		// Assert
		assertThat(models).hasSize(2);
		models.get(0).getTotal();
		verify(entries[0]).getTotal();

		models.get(1).getTotal();
		verify(entries[1]).getTotal();
	}

}
