/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javax.swing.JPanel;
import models.Appointment;
import models.PatientX;
import utilities.DBAccess;

/**
 *
 * @author Ecchi Powa
 */
public class CalendarWin extends JPanel{
      
    public CalendarWin(String date) throws ParseException{
        JFXPanel calendarWin = new JFXPanel();
        BorderPane mainLayout = new BorderPane();
        GridPane timeLayout = timeLayoutSetup(date);
        
        ScrollPane sp = new ScrollPane(timeLayout);
        
        sp.setMaxHeight(600);
        sp.setMinWidth(400);
        mainLayout.setCenter(sp);
        
        Label dateLabel = setDateLabel(date);
        mainLayout.setTop(dateLabel);
        
        Platform.runLater(new Runnable(){
            public void run(){
                Scene mainScene = new Scene(mainLayout);
                calendarWin.setScene(mainScene);
            }
        });
        this.add(calendarWin);
    }
    
    private GridPane timeLayoutSetup(String date){
        GridPane mainLayout = new GridPane();
        String[] datePart = date.split("-");
        Calendar now = Calendar.getInstance();
        now.set(Integer.parseInt(datePart[0]), Integer.parseInt(datePart[1]), Integer.parseInt(datePart[2]));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now.getTime()));
        ArrayList<Appointment> appointmentList = DBAccess.getAppointmentData(" WHERE APPOINTMENTDATE LIKE '"+sdf.format(now.getTime())+"';");
        ArrayList<PatientX> patientList = DBAccess.getPatientXData("");
        
        int startTime = 6;
        int addMin = 0;
        int x = 0;
        int colorNext = 0;
        
        for(int i = 0; i < 49; i++){
            HBox timeFrame = new HBox();
            Label currReason = new Label("");
            Label currPatient = new Label("");
            String min = String.valueOf((addMin%48)*15);
            
            if(i%4 == 0){
                if((startTime + x) > 11){
                    x = -6;
                }
                
                x++;
                addMin = 0;
            }
            
            addMin++;
            
            String hour = String.valueOf(startTime + x);
                        
            if(Integer.parseInt(min) == 60 || Integer.parseInt(min) == 0){
                min = "00";
            }
            
            Label currTime = new Label(hour +":"+ min + ":00");
            currTime.setStyle("-fx-alignment: center-left;");
            
            if(colorNext > 0){
                timeFrame.setStyle("-fx-background-color:#7fff00; -fx-spacing: 50px;");
                colorNext--;
            } else {
                currReason = new Label("Vacant");
                currReason.setStyle("-fx-alignment: center;");
                
                currPatient = new Label("Vacant");
                currPatient.setStyle("-fx-alignment: center-right;");
                
                timeFrame.setStyle("-fx-spacing: 50px");
                
                for(int y = 0; y < appointmentList.size(); y++){
                    if(currTime.getText().equals(appointmentList.get(y).getAppointmentTime())){
                        currReason.setText(appointmentList.get(y).getAppointmentRemarks());
                        currPatient.setText(getPatient(patientList, appointmentList.get(y).getPatientID()));
                        timeFrame.setStyle("-fx-background-color:#7fff00; -fx-spacing: 50px");

                        String[] timeNow = appointmentList.get(y).getAppointmentTime().split(":");
                        int nowHour = Integer.parseInt(timeNow[0]);       
                        int nowMin = Integer.parseInt(timeNow[1]);

                        String[] timeLater = appointmentList.get(y).getAppointmentEndTime().split(":");
                        int laterHour = Integer.parseInt(timeLater[0]);
                        int laterMin = Integer.parseInt(timeLater[1]);

                        int hourDiff = 0;
                        int minDiff = 0;
                        int totalDiff = 0;

                        if(nowMin > laterMin){
                            laterMin = laterMin + 60;
                            laterHour --;
                        }

                        if(nowHour > laterHour){
                            laterHour = laterHour + 12;
                        }

                        hourDiff = laterHour - nowHour;
                        minDiff = laterMin - nowMin;

                        totalDiff = (hourDiff * 60) + minDiff;
                        colorNext = totalDiff / 15;
                    }
                }
            }
                        
            timeFrame.getChildren().addAll(currTime, currReason, currPatient);

            mainLayout.add(timeFrame, 0, i);
        }
        
        mainLayout.setPadding(new Insets(25));
        
        mainLayout.setStyle(""
                + "-fx-font-size:13px;"
                + "-fx-hgap: 10px;"
                + "-fx-vgap: 8px;");
        
       return mainLayout;
   }

    private String getPatient(ArrayList<PatientX> patientList, int patientID){
        for(int i = 0; i < patientList.size(); i++){
            if(patientList.get(i).getId() == patientID){
                return patientList.get(i).getFullName();
            }
        }
        return "Vacant";
    }

    private Label setDateLabel(String date) throws ParseException{
        Label label = new Label();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        now.setTime(sdf.parse(date));
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
        sdf = new SimpleDateFormat("MMMM dd, yyyy");
        label.setText(sdf.format(now.getTime()));
        label.setStyle("-fx-font-size:20px; -fx-font-weight: bold; -fx-text-alignment: Center; -fx-label-padding: 10px; -fx-alignment: Center;");
        
        return label;
    }
}