package com.theironyard.timeless.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.theironyard.timeless.models.Entry;

@Service
public class EntryReaderWriter {
	
	private SimpleDateFormat formatter;
	
	public EntryReaderWriter() {
		formatter = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	public Entry getCurrent() throws IOException, ParseException {
		Entry entry = null;
		try (Reader in = new FileReader("entries.csv");
			 CSVParser records = CSVFormat.DEFAULT.parse(in)) {
			for (CSVRecord record : records) {
				entry = new Entry();
				double[] hours = new double[5];
			    entry.setDateFromString(record.get(0));
			    entry.setSubmitted(Boolean.parseBoolean(record.get(1)));
			    for (int i = 0; i < hours.length; i += 1) {
			    		hours[i] = Double.parseDouble(record.get(i + 2));
			    }
			    entry.setHours(hours);
			    break;
			}
		} catch (FileNotFoundException fnfe) {}
		return entry;
	}

	public List<Entry> getSubmitted() throws IOException, ParseException {
		ArrayList<Entry> entries = new ArrayList<>();
		boolean isFirst = true;
		try (Reader in = new FileReader("entries.csv");
			 CSVParser records = CSVFormat.DEFAULT.parse(in)) {
			for (CSVRecord record : records) {
				if (isFirst) {
					isFirst = false;
					continue;
				}
				double[] hours = new double[5];
			    Entry entry = new Entry();
			    entry.setDateFromString(record.get(0));
			    entry.setSubmitted(Boolean.parseBoolean(record.get(1)));
			    for (int i = 0; i < hours.length; i += 1) {
			    		hours[i] = Double.parseDouble(record.get(i + 2));
			    }
			    entry.setHours(hours);
			}
		} catch (FileNotFoundException fnfe) {}
		return entries;
	}
	
	public void writeAll(Entry current, List<Entry> entries) throws IOException {
		ArrayList<Entry> allEntries = new ArrayList<>();
		allEntries.add(current);
		allEntries.addAll(entries);
		try (Writer out = new FileWriter("entries.csv");
			 CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
			List<String[]> rows = new ArrayList<>();
			for (Entry entry : allEntries) {
				String date = "";
				if (entry.getDate() != null) {
					date = formatter.format(entry.getDate());
				}
				String[] columns = {
					date,
					String.valueOf(entry.isSubmitted()),
					null,
					null,
					null,
					null,
					null
				};
				for (int i = 0; i < 5; i += 1) {
					columns[i + 2] = String.valueOf(entry.getHours()[i]);
				}
				rows.add(columns);
			}
			printer.printRecords(rows);
		}
	}

}
