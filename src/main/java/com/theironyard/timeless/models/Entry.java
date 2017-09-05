package com.theironyard.timeless.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry {
	
	private Date date;
	private double[] hours;
	private boolean isSubmitted;
	
	public Entry() {
		hours = new double[5];
	}
	
	public double getTotal() {
		double value = 0;
		for (double d : hours) {
			value += d;
		}
		return value;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setDateFromString(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			this.date = formatter.parse(date);
		} catch (ParseException pe) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				this.date = formatter.parse(date);
			} catch (ParseException pe2) {}
		}
	}
	
	public double[] getHours() {
		return hours;
	}
	public void setHours(double[] hours) {
		this.hours = hours;
	}
	
	public boolean isSubmitted() {
		return isSubmitted;
	}
	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
	
}
