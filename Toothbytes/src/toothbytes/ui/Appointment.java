/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui;

/**
 *
 * @author jheraldinebugtong
 */
/**
 * @(#)Appointment.java
 *
 *
 * @author jheraldinetbugtong
 * @version 1.00 2015/3/22
 */

import java.io.Serializable;

public class Appointment implements Serializable, Comparable{

	public static final int MIN_HOUR = 0;
	public static final int MAX_HOUR = 23;
	public static final int MIN_MINUTE = 0;
	public static final int MAX_MINUTE = 59;
	public static final int MAX_SUBJECT_LENGTH = 50;

	private int startHour;
	private int startMinute;
	private int endHour;
	private int endMinute;
	private String subject;
	private String notes;
	private int category;

	public Appointment(int startHour, int startMinute, int endHour, int endMinute, int category, String subject, String notes){

		setStartHour(startHour);
		setStartMinute(startMinute);
		setEndHour(endHour);
		setEndMinute(endMinute);
		setSubject(subject);
		setNotes(notes);
		setCategory(category);

	}

	public int compareTo(Object o) throws ClassCastException{

		if(!(o instanceof Appointment))
			throw new ClassCastException("An appointment object expected.");

		return (this.getStartHour()*60+getStartMinute()) - (((Appointment)o).getStartHour()*60+((Appointment)o).getStartMinute());

	}

	public void setStartHour(int startHour){

	 	if(startHour<MIN_HOUR || startHour>MAX_HOUR)
	 		throw new IllegalArgumentException("hours input outside possible range");

	 	this.startHour = startHour;

	}

	public void setEndHour(int endHour){
	 	if(endHour<MIN_HOUR || endHour>MAX_HOUR)
	 		throw new IllegalArgumentException("hours input outside possible range");

	 	this.endHour = endHour;
	}

	public void setStartMinute(int startMinute)
	 {
	 	if(startMinute<MIN_MINUTE || startMinute>MAX_MINUTE)
	 		throw new IllegalArgumentException("hours input outside possible range");

	 	this.startMinute = startMinute;
	 }

	 public void setEndMinute(int endMinute)
	{
		if(endMinute<MIN_MINUTE || endMinute>MAX_MINUTE)
			throw new IllegalArgumentException("hours input outside possible range");

		this.endMinute = endMinute;
	}

	public void setSubject(String subject)
	{
		if(subject.length()>50)
		{
			this.subject = subject.substring(0,50);
		}
		else
		{
			this.subject = subject;
		}
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

		public void setCategory(int category)
	{
	  	this.category = category;
	}

		public int getStartHour()
	{
		return this.startHour;
	}

	public int getEndHour()
	{
		return this.endHour;
	}

	public int getStartMinute()
	{
		return this.startMinute;
	}

	public int getEndMinute()
	{
		return this.endMinute;
	}

	public String getSubject()
	{
		return this.subject;
	}

	public String getNotes()
	{
		return this.notes;
	}

	public int getCategory()
	{
		return this.category;
	}
}