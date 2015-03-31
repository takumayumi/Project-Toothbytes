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
 * @(#)FileReaderWriter.java
 *
 *
 * @author jheraldinetbugtong
 * @version 1.00 2015/3/23
 */


import javax.swing.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.Serializable;

/**
 * Class that allows reading/writing one object to file.
 */
public class FileReaderWriter

{
	/**
	 * read specified file and return 1 object
	 * or null if file doesnt exist
	 * @param f1 the File to read
	 */
	public Object readObjectFromFile(File f1)
	{
		Object x=null;

		try
		{
			ObjectInputStream input=new ObjectInputStream(new FileInputStream(f1));
			x=input.readObject();
		}
		catch (IOException ioException)
		{
			System.out.println("error"+ioException);
	    }
		catch (ClassNotFoundException classnotFoundException)
		{
			System.out.println("error"+classnotFoundException);
	    }

		return x;
	}


	/**
	 * write object to file
	 * @param o the object to write out
	 * @param f1 the file to write to
	 */
	public void writeObjectToFile(File f1, Object o)
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f1));
			out.writeObject(o);
			out.close();
		}
		catch (IOException ioException)
		{
			System.out.println("error "+ioException);
	    }
	}

}
