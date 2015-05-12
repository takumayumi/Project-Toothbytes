/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package utilities;

import java.io.*;

/**
 * <h1>FileReaderWriter</h1>
 * The {@code FilreReaderWrite} class allows reading/writing one object to file.
 */
public class FileReaderWriter {

    /**
     * This method reads specified file and return 1 object or null if file 
     * doesn't exist.
     * @param   f1
     *          The file to read.
     * @return  x
     */
    public Object readObjectFromFile(File f1) {
        Object x = null;

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(f1));
            x = input.readObject();
        } catch (IOException ioException) {
            System.out.println("error" + ioException);
        } catch (ClassNotFoundException classnotFoundException) {
            System.out.println("error" + classnotFoundException);
        }

        return x;
    }

    /**
     * This method writes object to file.
     * @param   o
     *          The object to write out
     * @param   f1
     *          The file to write to
     */
    public void writeObjectToFile(File f1, Object o) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f1));
            out.writeObject(o);
            out.close();
        } catch (IOException ioException) {
            System.out.println("error " + ioException);
        }
    }

}
