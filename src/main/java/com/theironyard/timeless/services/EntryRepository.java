package com.theironyard.timeless.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.theironyard.timeless.models.Entry;

@Service
public class EntryRepository {

	private EntryReaderWriter readerWriter;
	private Entry current;
	private ArrayList<Entry> entries;
	
	public EntryRepository(EntryReaderWriter readerWriter) {
		this.readerWriter = readerWriter;
	}
	
	public Entry getCurrent() throws IOException, ParseException {
		init();
		return current;
	}

	public void updateCurrent(Entry entry) throws IOException, ParseException {
		init();
		current.setDate(entry.getDate());
		current.setHours(entry.getHours());
		writeAll();
	}

	public void closeOutCurrent() throws IOException, ParseException {
		init();
		entries.add(0, current);
		current = new Entry();
		writeAll();
	}

	public List<Entry> getEntries() throws IOException, ParseException {
		init();
		return entries;
	}
	
	private void writeAll() throws IOException {
		readerWriter.writeAll(current, entries);
	}
	
	private void init() throws IOException, ParseException {
		if (current == null) {
			current = readerWriter.getCurrent();
			entries = new ArrayList<Entry>(readerWriter.getSubmitted());
		}
		if (current == null) {
			current = new Entry();
			writeAll();
		}
	}
	
}
