package com.theironyard.timeless.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.theironyard.timeless.models.Entry;

public class EntryTests {
	
	private Entry entry;
	
	@Before
	public void setUp() {
		entry = new Entry();
	}

	@Test
	public void test_new_Entry_has_five_zeroed_hours() {
		// Arrange
		
		// Act
		double[] hours = entry.getHours();
		
		// Assert
		assertThat(hours).containsExactly(0, 0, 0, 0, 0);
	}
	
	@Test
	public void test_new_Entry_has_null_date() {
		// Arrange
		
		// Act
		Date date = entry.getDate();
		
		// Assert
		assertThat(date).isNull();
	}
	
	@Test
	public void test_new_Entry_isSubmitted_is_false() {
		// Arrange
		
		// Act
		boolean isSubmitted = entry.isSubmitted();
		
		// Assert
		assertThat(isSubmitted).isFalse();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_setDateFromString_sets_date_from_mmddyyyy() {
		// Arrange
		entry.setDateFromString("12/31/1999");
		
		// Act
		Date date = entry.getDate();
		
		// Assert
		assertThat(date.getTime()).isEqualTo(Date.parse("12/31/1999"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_setDateFromString_sets_date_from_yyyymmdd() {
		// Arrange
		entry.setDateFromString("1999-12-31");
		
		// Act
		Date date = entry.getDate();
		
		// Assert
		assertThat(date.getTime()).isEqualTo(Date.parse("12/31/1999"));
	}
	
}
