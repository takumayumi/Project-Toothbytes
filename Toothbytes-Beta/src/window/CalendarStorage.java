/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package window;

import java.util.ArrayList;
import java.io.File;
import java.io.Serializable;
import utilities.FileReaderWriter;

public class CalendarStorage implements Serializable
{
	private ArrayList years;
	private Year everyYear;

	/**
	 * create a data store for days/months/objects
	 */
	public CalendarStorage()
	{
		years = new ArrayList();
		everyYear = new Year();
	}

	/**
	 * adds a Year object to the calendar for given year
	 * if year already exists it just returns reference to existing year
	 * @return Year object for given year
	 */
	private Year addYear(int year)
	{
		// if it exists return reference to existing year
		for(int i=0;i<years.size();i++)
		{
			if(((Year)years.get(i)).getYear()==year)
				return ((Year)years.get(i));
		}

		//  create year and return reference
		Year temp = new Year(year);
		years.add(temp);
		return temp;
	}


	/**
	 * gets a year object for a given year eg 2006.
	 * if Year object does not exist for that year, returns null
	 * @param year year to try and get
	 * @return Year object for given year id or null if it doesnt exist
	 */
	private Year getYear(int year)
	{
		// if it exists return reference to existing year
		for(int i=0;i<years.size();i++)
		{
			if(((Year)years.get(i)).getYear()==year)
				return ((Year)years.get(i));
		}

		// else create year and return reference
		return null;
	}


 	/**
 	 * adds an appointment to this year for the supplied month/day
 	 * @param o the object to add to the data store for given year/month/day
 	 */
 	 public void addObject(int year, int month, int day, Object o)
 	 {
 	 	// get reference to selected year and add appointment
 	 	Year selectedYear = addYear(year);

 	 	// check if day has an existinf Arraylist or if its null
 	 	selectedYear.addToDay(month, day, o);
 	 }

	 /**
	  * deletes object stored for given year/month/day and ArraylistIndex
	  * if no object stored at given position, no error thrown.
	  * @param ArraylistIndex the objects index withing the arrayList for that year/month/day
	  */
	 public void deleteObject( int year, int month, int day, int ArraylistIndex)
	 {
	 	Year selectedYear;
	 	selectedYear = this.getYear(year);

	 	if(selectedYear != null)
	 	{
	 		selectedYear.getDay(month,day).remove(ArraylistIndex);
	 	}
	 	else
	 	{
	 		throw new IndexOutOfBoundsException("cant delete object as no object exists at specified index");
	 	}
	 }


	 /**
	  * gets an arraylist of objects for a selected year,day,month
	  * or null if there are none
	  */
	 public ArrayList getArrayListOfObjectsFor(int year, int month, int day)
	 {
	 	Year selectedYear;
	 	selectedYear = this.getYear(year);

	 	if(selectedYear != null)
	 	{
	 		return selectedYear.getDay(month,day);
	 	}

	 	return null;
	 }


	 /**
	  * gets an object  for a selected year,day,month and arrayListIndex
	  * or null if there are none
	  */
	 public Object getObject(int year, int month, int day, int arrayListIndex)
	 {
	 	Year selectedYear;
	 	selectedYear = this.getYear(year);

	 	if(selectedYear != null)
	 	{
	 		ArrayList al = selectedYear.getDay(month,day);

	 		if(al!=null )
	 		{
	 			return al.get(arrayListIndex);
	 		}
	 	}

	 	return null;
	 }



 	/**
 	 * adds an Object that occurs every year
 	 */
 	 public void addAnnualObject(int month, int day, Object appointment)
 	 {
 	 	everyYear.addToDay(month, day, appointment);
 	 }

	  /**
	  * deletes annually recurring object
	  */
	 public void deleteAnnualObject( int month, int day, int ArraylistIndex)
	 {
	 	everyYear.getDay(month,day).remove(ArraylistIndex);
	 }

	 /**
	  * gets an arraylist of objects that occur annually
	  * for given day,month
	  * or null if there are none
	  */
	 public ArrayList getArrayListOfAnnualObjectsFor(int month, int day)
	 {
	 	return everyYear.getDay(month,day);
	 }


	 /**
	  * gets an object that occurs annually
	  * for a given day,month and arrayListIndex
	  * or null if there are none
	  */
	 public Object getAnnualObject(int month, int day, int arrayListIndex)
	 {
 		ArrayList al = everyYear.getDay(month,day);

 		if(al!=null )
 		{
 			return al.get(arrayListIndex);
 		}
 		return null;
	 }


	 /**
	  * deletes all
	  * deletes standard objects and recurring eg annual objects
	  */
	 public void deleteAll()
	 {
	 	years = new ArrayList();
	 	everyYear = new Year();
	 }



	 /**
	  * writes out all calendarStore data to file nominated
	  * @param storageFile the file to save to
	  */
	  public void saveToFile(File storageFile)
	  {
 	 	//save calendarStore to disk
 	 	FileReaderWriter fileRW = new FileReaderWriter();
 	 	fileRW.writeObjectToFile(storageFile,this);
	  }


	 /**
	  * if possible reads calendarStore data from disk and loads it
	  * warning any currently open calendar data will be lost
	  * @param storageFile the file to read from
	  */
	  public void openFromFile(File storageFile)
	  {
 	 	//read calendarStore from disk
 	 	FileReaderWriter fileRW = new FileReaderWriter();
 	 	CalendarStorage cs = ((CalendarStorage)fileRW.readObjectFromFile(storageFile));
 	 	years = cs.years;
 	 	everyYear = cs.everyYear;
	  }




	 /**
	  * storage structure for storing a year
	  * a year contains an 2d array of days.
	  * 1st dimension represents months
	  * 2nd dimension represents day of month
	  * constants used to allocate adequate storage for gregorian calendar
	  * year class can be used to store any type of object for a given day
	  */

	 public class Year implements Serializable
	 {
	 	public static final int MAX_MONTHS_PER_YEAR = 12;
	 	public static final int MAX_DAYS_PER_MONTH = 32;

	 	private int year=0;
	 	private ArrayList[][] day;

	 	/**
	 	 * creates a year for which the id is not important
	 	 * eg the year storing annually recurring objects
	 	 */
	 	Year()
	 	{
	 		this(0);
	 	}

	 	/**
	 	 * sets the id for what year this Year is holding eg. 2006
	 	 * and creates a 2d array representing [month][day_of_month]
	 	 * but does not allocate an arrayList to each element
	 	 * just leaves them null
	 	 * @param year the year id eg. 2006
	 	 */
	 	Year(int year)
	 	{
	 		this.year = year;
	 		this.day =  new ArrayList[MAX_MONTHS_PER_YEAR] [MAX_DAYS_PER_MONTH];
	 	}

	 	/**
	 	 * return an int identifying what year this Year holds eg. 2006
	 	 * @return an int identifying what year this Year holds eg. 2006
	 	 */
	 	public int getYear()
	 	{
	 		return year;
	 	}



		/**
		 * gets arraylist of objects for that day
		 * @return returns arraylist of objects or null
		 */
		 public ArrayList getDay(int month, int day)
		 {
		 	if(month>=0 && month<MAX_MONTHS_PER_YEAR && day>=0 && day<MAX_DAYS_PER_MONTH)
		 	{
	 			return (this.day[month][day]);
		 	}

		 	throw new IllegalArgumentException("month/day input not withing acceptable limits");
		 }

	 	/**
	 	 * adds an object for that day
	 	 * @param month an integer representing the month
	 	 * @param day the day of the month
	 	 * @param o object to add
	 	 */
		public void addToDay(int month, int day, Object o)
		{
			if(this.day[month][day]==null)
			{
				this.day[month][day] = new ArrayList();
			}

			this.day[month][day].add(o);
		}
	 }

}
