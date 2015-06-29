/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
 */
package components;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import utilities.DBAccess;
import utilities.DataMan;

/**
 * <h1>Cover</h1>
 * The {@code Cover} manages the opening screen of the program Toothbytes.
 */
public class Cover extends JPanel {

    /**
     * This constructor layouts the opening screen of the program.
     */
    private JLabel timeLbl, dateLbl;
    private JLabel record, appoint, payment;
    private JPanel info, rInfo, aInfo, pInfo, dateTime;

    public Cover() {

        this.setLayout(new MigLayout("fill, wrap 6"));
        this.setBackground(Color.white);
        JLabel logo = new JLabel(new ImageIcon("res/icons/main_logo.png"));

        DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm a");

        timeLbl = new JLabel();
        dateLbl = new JLabel();

        Timer setTime = new Timer();
        setTime.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    int hour = LocalDateTime.now().getHour();
                    int minute = LocalDateTime.now().getMinute();

                    int year = LocalDateTime.now().getYear();
                    int month = LocalDateTime.now().getMonthValue();
                    int day = LocalDateTime.now().getDayOfMonth();

                    String sHour = String.valueOf(hour);
                    String sMin = String.valueOf(minute);
                    String hourFormat = "AM";

                    String date = month + " / " + day + " / " + year;

                    if (hour > 12) {
                        hour = hour - 12;
                        hourFormat = "PM";
                        sHour = String.valueOf(hour);
                    } else if (hour == 0) {
                        hour = 12;
                        sHour = "12";
                    }

                    if (hour < 10) {
                        sHour = "0" + sHour;
                    }

                    if (minute < 10) {
                        sMin = "0" + sMin;
                    }

                    Date d = new SimpleDateFormat("MM / dd / yyyy").parse(date);

                    dateLbl.setText(dateFormat.format(d));
                    timeLbl.setText(sHour + " : " + sMin + " " + hourFormat);

                } catch (ParseException ex) {
                    Logger.getLogger(Cover.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }, 1000, 1000);

        Calendar cal = Calendar.getInstance();
        dateLbl = new JLabel(dateFormat.format(cal.getTime()));

        dateLbl.setFont(new Font("Arial", Font.PLAIN, 24));
        dateLbl.setHorizontalAlignment(SwingConstants.CENTER);
        dateLbl.setForeground(new Color(30, 250, 100));

        timeLbl.setFont(new Font("Arial", Font.PLAIN, 24));
        timeLbl.setHorizontalAlignment(SwingConstants.CENTER);
        timeLbl.setForeground(new Color(40, 40, 40));

        dateTime = new JPanel(new MigLayout("fill, wrap 3"));
        dateTime.add(dateLbl, "center, span 3");
        dateTime.add(timeLbl, "center, span 3");

        this.add(dateTime, "span 6, growx, north, gapy 0 10");
        this.add(logo, "gapx 10 10, center");

        rInfo = new JPanel(new MigLayout("fill, wrap 3"));
        rInfo.setBackground(new Color(60, 60, 60));
        
        aInfo = new JPanel(new MigLayout("fill, wrap 3"));
        aInfo.setBackground(new Color(60, 60, 60));
        
        pInfo = new JPanel(new MigLayout("fill, wrap 3"));
        pInfo.setBackground(new Color(60, 60, 60));
        
        info = new JPanel(new MigLayout("wrap 1"));

        record = new JLabel(DataMan.ResizeImage("res/buttons/PatientRecords_G.png", 70, 70));
        appoint = new JLabel(DataMan.ResizeImage("res/buttons/Appointments_G.png", 70, 70));
        payment = new JLabel(DataMan.ResizeImage("res/buttons/Finances_G.png", 70, 70));

        double[] count = DBAccess.countResults();
        
        JLabel rText = new JLabel("Total No. of Patients| " + (int)count[0]);
        rText.setFont(new Font("Arial", Font.PLAIN, 18));
        rText.setForeground(Color.WHITE);
        
        JLabel aText = new JLabel("Scheduled Appointments| " + (int)count[2]);
        aText.setFont(new Font("Arial", Font.PLAIN, 18));
        aText.setForeground(Color.WHITE);

        JLabel pText = new JLabel("Unpaid Balances| P" + count[1]);
        pText.setFont(new Font("Arial", Font.PLAIN, 18));
        pText.setForeground(Color.WHITE);

        rInfo.add(record, "span 1");
        rInfo.add(rText, "span 2, align left");
        
        aInfo.add(appoint, "span 1");
        aInfo.add(aText, "span 2, align left");
        
        pInfo.add(payment, "span 1");
        pInfo.add(pText, "span 2, align left");

        info.add(rInfo, "span 1, w 350:350:350, h 90:90:90, growx, gapy 10 10");
        info.add(aInfo, "span 1, w 350:350:350, h 90:90:90, growx, gapy 10 10");
        info.add(pInfo, "span 1, w 350:350:350, h 90:90:90, growx, gapy 10 10");
        info.setBackground(Color.WHITE);

        this.add(info, "east");

    }

}
