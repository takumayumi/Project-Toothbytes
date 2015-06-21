/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
 */
package utilities;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import models.OrganizedTreatment;
import models.Treatment;

/**
 * <h1>DataMan</h1>
 * The {@code DataMan} class for data manipulation.
 */
public class DataMan {

    /**
     * This method computes and returns the age given by the date.
     *
     * @param bday Calendar object.
     * @return Integer value of age.
     */
    public static int getAge(Calendar bday) {
        long milis = System.currentTimeMillis() - bday.getTime().getTime();
        return (int) (milis / 1000 / 31536000);
    }

    public static ArrayList<OrganizedTreatment> organizeTreatment(ArrayList<Treatment> tList) {
        int added = 0;
        ArrayList<OrganizedTreatment> newList = new ArrayList<OrganizedTreatment>();
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
//        for(int i=0; i<tList.size(); i++) {
//            System.out.println("sList CREATION "+sdf.format(tList.get(i).getDate().getTime()));
//        }
        
        for (int i = 0; i < tList.size(); i++) {
            try {
                
                OrganizedTreatment ot = new OrganizedTreatment(tList.get(i).getDate(),
                        Integer.parseInt(tList.get(i).getTooth()), tList.get(i).getProcedure());
                
                for (int j = i + 1; j < tList.size(); j++) {
                    
                    System.out.println(sdf.format(ot.getDate().getTime())+" vs "+sdf.format(tList.get(j).getDate().getTime()));
                    System.out.println(ot.getDate().getTime().equals(tList.get(j).getDate().getTime()));
                    
                    if (ot.getDate().getTime().equals(tList.get(j).getDate().getTime())) {
                        ot.getHm().put(Integer.parseInt(tList.get(j).getTooth()), tList.get(j).getProcedure());
                        added++;
                    }
                }
                newList.add(ot);
                if (i + added >= tList.size()) {
                    break;
                } else {
                    i += added;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Error: " + tList.get(i).getTooth() + " can't be converted to int");
            }
        }
        
        System.out.println("Treatments Organized...");
        
        for(int i = 0; i<newList.size(); i++ ) {
            System.out.println("Treatment at: "+sdf.format(newList.get(i).getDate().getTime()));
            Set s = newList.get(i).getHm().entrySet();
            Iterator it = s.iterator();
            while(it.hasNext()) {
                Map.Entry mapping = (Map.Entry) it.next();
                System.out.println("Tooth No: "+mapping.getKey()+" State: "+mapping.getValue());
            }
        }

        return newList;
    }

    public static ImageIcon ResizeImage(String imagePath) {
        ImageIcon MyImage = new ImageIcon(imagePath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }

    public static ImageIcon ResizeImage(String imagePath, int w, int h) {
        ImageIcon MyImage = new ImageIcon(imagePath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
}
