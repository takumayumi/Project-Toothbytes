/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvFlip;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;

/**
 *
 * @author Ecchi Powa
 */
public class Camera {
    public Camera(){
        try{
            CanvasFrame canvas = new CanvasFrame("Intraoral Camera");
            canvas.setLayout(new MigLayout("insets 10, gap 10", "grow", "grow"));
            JButton takePicture = new JButton("Take Picture");
            canvas.add(takePicture, "dock south, align center");
            takePicture.addActionListener(new TakePictureButtonPressed());
            canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            canvas.pack();
            
            FrameGrabber grab = new VideoInputFrameGrabber(0);
            
            grab.start();
            IplImage img;
            
            while(true){
                img = grab.grab();
                if(img != null){
                    cvFlip(img, img, 1);
                    cvSaveImage("camera.jpg", img);
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
                File source = new File("camera.jpg");
                File output = new File("db/image001.jpg");
                Files.copy(source.toPath(), output.toPath(), REPLACE_EXISTING);
            }catch(Exception ex){}
            
        }
        
    }
}
