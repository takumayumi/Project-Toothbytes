/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvFlip;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import utilities.DBAccess;

public class Camera {
    private int patientID, picNo;
    private String patientName;
    
    public Camera(int patientID){
        try{
            this.patientID = patientID;
            
            CanvasFrame canvas = new CanvasFrame("Intraoral Camera");
            canvas.setLayout(new MigLayout("insets 10, gap 10", "grow", "grow"));
            JButton takePicture = new JButton("Take Picture");
            canvas.add(takePicture, "dock south, align center");
            takePicture.addActionListener(new TakePictureButtonPressed());
            canvas.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
            canvas.pack();
            canvas.setVisible(true);
            
            FrameGrabber grab = new VideoInputFrameGrabber(0);
            
            grab.start();
            IplImage img;
            
            while(true){
                img = grab.grab();
                if(img != null){
                    cvFlip(img, img, 1);
                    cvSaveImage("data/db/camera.jpg", img);
                    canvas.showImage(img);
                }
            }
        }catch(Exception e){
            
        }
    }
    
    private class TakePictureButtonPressed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                patientName = DBAccess.getPatientName(patientID);
                picNo = DBAccess.getLastNo("DENTAL_PICTURES WHERE patientID = " + patientID);
                picNo++;
                
                String patientPicDatabase = "data/db/"+patientName+"/"+picNo+".jpg";
                File source = new File("camera.jpg");
                File output = new File(patientPicDatabase);
                Files.copy(source.toPath(), output.toPath(), REPLACE_EXISTING);
            }catch(Exception ex){}
            
        }
        
    }
}
