package com.theironyard.timeless.viewModels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.theironyard.timeless.models.Entry;

public class EntryViewModel {

	private Entry entry;
	private SimpleDateFormat formatter;
	
	public EntryViewModel(Entry entry) {
		this.entry = entry;
		formatter = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	public static List<EntryViewModel> fromEntries(List<Entry> entries) {
		ArrayList<EntryViewModel> models = new ArrayList<>();
		for (Entry entry : entries) {
			models.add(new EntryViewModel(entry));
		}
		return models;
	}
	
	public double getTotal() {
		return entry.getTotal();
	}
	
	public Date getDate() {
		return entry.getDate();
	}

	public double[] getHours() {
		return entry.getHours();
	}

	public boolean isSubmitted() {
		return entry.isSubmitted();
	}
	
	public String getFormattedDate() {
		Date date = entry.getDate();
		if (date == null) {
			return "";
		}
		return formatter.format(date);
	}
	
}
