/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package window;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import components.ModuleWindow;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Collections;
import java.text.NumberFormat;
import java.io.File;
import models.AppointmentOld;
import net.miginfocom.swing.MigLayout;

/**
 * <h1>AppointmentsWindow</h1>
 * The {@code AppointmentsWindow} class constructs a window of Appointments.
 */
public class AppointmentsWindow extends ModuleWindow {

    private MigLayout layout;
    Button next, previous, today, monthly, annually, addNewAppointment;
    List list;
    JScrollPane scroll;
    Label title, search;
    TextField textsearch;
    TextArea textdefinition;
    Panel p_up, p_title, p_t1, p_choices, p_body, p_down, p_complete;
    Data d = new Data();
    String[] keys, delete = null;
    String viewWord = "";
    int currentindex = 0;

    private static final String FRAME_TITLE = "Appointments";

    static final int ST_DISPLAY_YEAR = 1;
    static final int ST_DISPLAY_MONTH = 2;
    static final int ST_DISPLAY_TODAY = 3;
    static final int ST_DISPLAY_DAY = 4;
    static final int ST_VIEW_APPOINTMENT = 5;
    static final int ST_VIEW_ANNUAL_APPOINTMENT = 10;
    static final int ST_ADD_APPOINTMENT = 6;
    static final int ST_ADD_ANNUAL_APPOINTMENT = 9;
    static final int ST_LOAD_FROM_FILE = 7;
    static final int ST_NEW_FILE = 8;

    private Action addAction, deleteAction, viewAboutAction, addAnnualAction, 
            viewDayAction, viewWeekAction, viewMonthAction, viewYearAction, 
            saveToFileAction, loadFromFileAction, newFileAction, viewTodayAction;
    private int currentState;
    private CalendarStorage calendarStore; // Data storage for storing appointments.
    private File calendarStoreFile;
    private Calendar cal; // Used to store the currently selected year/month/day.
    private int arrayListIndex;
    private double screenWidth;
    private double screenHeight;

    /**
     * This constructor completes it all and add to frame. It also initializes 
     * data storage structure, and if possible, load it from a file.
     */
    public AppointmentsWindow() {
        p_title = new Panel();
        p_title.setLayout(new BorderLayout());
        p_title.add(title = new Label("Appointments", Label.CENTER), BorderLayout.CENTER);
        title.setForeground(Color.RED);
        p_up = new Panel();
        p_up.setLayout(new GridLayout(2, 1));
        p_up.add(p_title);
        p_body = new Panel();
        p_body.setLayout(new BorderLayout());
        p_body.add(new JScrollPane(scroll, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        
        p_complete = new Panel();
        p_complete.setLayout(new BorderLayout());
        p_complete.add(p_up, BorderLayout.NORTH);
        p_complete.add(p_body, BorderLayout.CENTER);
        p_complete.add(new Label(""), BorderLayout.SOUTH);
        this.add(p_complete);

        cal = Calendar.getInstance(TimeZone.getDefault());

        calendarStore = new CalendarStorage();
        calendarStoreFile = new File("temp.dat");

        if (calendarStoreFile.exists()) {
            calendarStore.openFromFile(calendarStoreFile);
        }

        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setPreferredSize(new Dimension(((int) screenWidth * 10 / 10), ((int) screenHeight * 10 / 10)));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
                System.exit(0);
            }
        });

        addAction = new ChangeStateAction("New Appointment", KeyEvent.VK_A, ST_ADD_APPOINTMENT);

        addAnnualAction = new ChangeStateAction("Annual Appointment", KeyEvent.VK_P, ST_ADD_ANNUAL_APPOINTMENT);

        viewDayAction = new ChangeStateAction("Day", KeyEvent.VK_D, ST_DISPLAY_DAY);

        viewMonthAction = new ChangeStateAction("Month", KeyEvent.VK_M, ST_DISPLAY_MONTH);

        viewTodayAction = new ChangeStateAction("Today", KeyEvent.VK_T, ST_DISPLAY_TODAY);

        viewYearAction = new ChangeStateAction("Year", KeyEvent.VK_Y, ST_DISPLAY_YEAR);

        viewAboutAction = new NoChangeStateAction("About", KeyEvent.VK_B, NoChangeStateAction.ABOUT);

        loadFromFileAction = new ChangeStateAction("Open", KeyEvent.VK_L, ST_LOAD_FROM_FILE);

        newFileAction = new ChangeStateAction("New", KeyEvent.VK_N, ST_NEW_FILE);

        saveToFileAction = new NoChangeStateAction("Save As", KeyEvent.VK_S, NoChangeStateAction.SAVE);

        // Initialises member variables eg. set the appropiate start state.
        this.setState(ST_DISPLAY_DAY);
    }

    /**
     * This method enables a File saves to CalendarStorage.
     */
    private void close() {
        calendarStore.saveToFile(calendarStoreFile);
    }

    /**
     * <h1>AppropriateToolBar</h1>
     * The {@code AppropriateToolBar} class creates toolbar for the 
     * Appointments Window.
     */
    public class AppropriateToolBar extends JPanel {

        JButton action;

        /**
         * This constructs the toolbar.
         */
        public AppropriateToolBar() {

            Action[] actions = {addAction, viewTodayAction, viewDayAction, viewMonthAction, viewYearAction};

            for (Action action : actions) {
                add(new JButton(action));
            }
            
        }

    }

    /**
     * This method sets layout, add toolbar, add header panels, and add footer 
     * panels to wrap off header panels and allow central GUI to scroll. It 
     * also displays necessary GUI depending upon state, warn any changes that 
     * can lead to losing of any data. It lets user choose file. 
     */
    public void setState(int state) {

        currentState = state;

        p_body.removeAll();
        p_body.setLayout(new BoxLayout(p_body, BoxLayout.PAGE_AXIS));
        p_body.add(new AppropriateToolBar());
        p_body.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.LINE_AXIS));
        centerPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        centerPanel.add(Box.createHorizontalGlue());

        JPanel scrollPanel = new JPanel();

        String title;

        switch (currentState) {
            case ST_DISPLAY_YEAR:
                title = "" + cal.get(Calendar.YEAR);
                p_body.add(new TitlePanel(title, Calendar.YEAR));
                scrollPanel.add(new YearPanel(cal.get(Calendar.YEAR)));
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_DISPLAY_MONTH:
                title = getMonthString(cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.YEAR);
                p_body.add(new TitlePanel(title, Calendar.MONTH));
                scrollPanel.add(new MonthPanel(cal.get(Calendar.YEAR), cal.get(cal.MONTH)));
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_DISPLAY_TODAY:
                cal = Calendar.getInstance(TimeZone.getDefault());
                currentState = ST_DISPLAY_DAY;
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_DISPLAY_DAY:
                title = cal.get(Calendar.DAY_OF_MONTH) + " " + getMonthString(cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.YEAR);
                p_body.add(new TitlePanel(title, Calendar.DAY_OF_MONTH));
                scrollPanel.add(new DayPanel(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_ADD_APPOINTMENT:
                scrollPanel.add(new AppointmentPanel(cal.get(Calendar.YEAR), cal.get(cal.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_ADD_ANNUAL_APPOINTMENT:
                scrollPanel.add(new AnnualAppointmentPanel(cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_VIEW_APPOINTMENT:
                scrollPanel.add(new AppointmentPanel(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this.arrayListIndex));
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_VIEW_ANNUAL_APPOINTMENT:
                scrollPanel.add(new AnnualAppointmentPanel(cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this.arrayListIndex));
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_NEW_FILE:
                String[] choices = {"yes", "no"};

                int chosen = JOptionPane.showOptionDialog(null,
                        "Warning you will lose any unsaved changes. OK to proceed?",
                        "Lose any changes?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices,
                        choices[0]);
                if (chosen == 0) {
                    calendarStore.deleteAll();
                }
                SwingUtilities.updateComponentTreeUI(this);
                break;

            case ST_LOAD_FROM_FILE:
                String[] choices2 = {"yes", "no"};

                int chosen2 = JOptionPane.showOptionDialog(null,
                        "Warning. OK to proceed?",
                        "Lose any changes?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices2,
                        choices2[0]);

                if (chosen2 == 0) {
                    JFileChooser obox = new JFileChooser();
                    int returnVal = obox.showOpenDialog(null);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File f1 = obox.getSelectedFile();
                        calendarStore.openFromFile(f1);
                        scrollPanel.add(new YearPanel(cal.get(cal.YEAR)));
                        SwingUtilities.updateComponentTreeUI(this);
                    }
                }
                break;

            default:
                break;
        }

        JScrollPane scrollPane = new JScrollPane(scrollPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setPreferredSize(new Dimension(((int) screenWidth * 7 / 10), ((int) screenHeight * 7 / 10)));

        centerPanel.add(scrollPane);

        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        p_body.add(centerPanel);
        p_body.add(Box.createRigidArea(new Dimension(0, 10)));
        p_body.add(Box.createVerticalGlue());
        
    }

    /**
     * <h1>ChangeStateAction</h1>
     * The {@code ChangeStateAction} class
     */
    public class ChangeStateAction extends AbstractAction {

        private int state;

        public ChangeStateAction(String text, Integer mnemonic, int state) {
            super(text, new ImageIcon("i/" + text.replace(" ", "") + ".gif"));
            putValue(MNEMONIC_KEY, mnemonic);
            this.state = state;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setState(this.state);
        }
    }

    /**
     * <h1>NoChangeStateAction</h1>
     * The {@code NoChangeStateAction} class to define an action that does not 
     * change the state but does take action assumes button images are in i 
     * folder with text as file name where text has had its whitespace removed 
     * text used for both action text and description.
     */
    public class NoChangeStateAction extends AbstractAction {

        public final static int SAVE = 1;
        public final static int ABOUT = 3;

        private int action = 0;

        public NoChangeStateAction(String text, Integer mnemonic, int action) {
            super(text, new ImageIcon("i/" + text.replace(" ", "") + ".gif"));
            putValue(MNEMONIC_KEY, mnemonic);
            this.action = action;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action) {
                case SAVE:
                    // Let user choose file.
                    JFileChooser sbox = new JFileChooser();
                    int returnVal2 = sbox.showSaveDialog(null);

                    if (returnVal2 == JFileChooser.APPROVE_OPTION) {
                        File f2 = sbox.getSelectedFile();
                        calendarStore.saveToFile(f2);
                    }
                    break;

                default:
                    break;
            }

        }
    }

    /**
     * <h1>YearPanel</h1>
     * This {@code YearPanel} class creates panel to allow month/day selection, 
     * and displays the year.
     */
    public class YearPanel extends JPanel {

        public YearPanel(int year) {
            setLayout(new GridLayout(0, 3));

            for (int i = 0; i < 12; i++) {
                add(new MonthPanel(year, i, true));
            }
        }
    }

    /**
     * <h1>MonthPanel</h1>
     * This {@code MonthPanel} class creates panel to allow day selection, 
     * and displays the month.
     */
    public class MonthPanel extends JPanel implements MouseListener {

        int year;
        int month;

        /**
         * This is a default constructor for when you want the normal 
         * sized version.
         */
        public MonthPanel(int year, int month) {
            this(year, month, false);
        }

        /**
         * This constructor stores as member variables, uses calendar object to 
         * find how many days in month, etc. It creates border and set 
         * layout. It also creates labels panel, actual grid of days
         * @param   year
         *          Integer representation of the year.
         * @param   month
         *          Integer representation of the month.
         * @param   smallVersion
         *          If true then panel is smaller.
         */
        public MonthPanel(int year, int month, boolean smallVersion) {
            int dayOfWeek;
            int initalValueOfFirstGrid;
            int daysInThisMonth;

            this.year = year;
            this.month = month;

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);

            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            daysInThisMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (initalValueOfFirstGrid = 1; (dayOfWeek + 7) % 7 != Calendar.MONDAY; dayOfWeek--) {
                initalValueOfFirstGrid--;
            }

            if (smallVersion) {
                TitledBorder monthBorder = BorderFactory.createTitledBorder(getMonthString(month));
                monthBorder.setTitleJustification(TitledBorder.CENTER);
                setBorder(monthBorder);
                setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            }

            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new GridLayout(0, 7));

            for (int i = 0; i < 7; i++) {
                JLabel a = new JLabel(getDayString(i, smallVersion), JLabel.CENTER);
                dayPanel.add(a);
            }

            for (int i = initalValueOfFirstGrid; i <= daysInThisMonth; i++) {
                if (i >= 1) {
                    dayPanel.add(new SelectDayButton(year, month, i));
                } else {
                    dayPanel.add(new JLabel(""));
                }
            }
            add(dayPanel);

            addMouseListener(this);
        }

        /**
         * This method is an event when mouse clicked setState, it will display 
         * month.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            setState(ST_DISPLAY_MONTH);
        }

        public void mouseExited(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        /**
         * This method returns String representation of days.
         * @param   day
         *          Integer representation of day.
         * @param   shortVersions
         *          Boolean representation if a month has 28-30 days.
         * @return  Array of days.
         */
        private String getDayString(int day, boolean shortVersions) {
            final String[] dayLabels = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            final String[] shortDayLabels = {"M", "T", "W", "Th", "F", "S", "Sun"};

            if (shortVersions) {
                return shortDayLabels[day];
            } else {
                return dayLabels[day];
            }
            
        }

        /**
         * <h1>SelectDayButton</h1>
         * This {@code SelectDayButton} class creates a button to represent 
         * one given day.
         */
        public class SelectDayButton extends JButton implements ActionListener {

            int year;
            int month;
            int day;

            /**
             * This method stores variables
             * @param   year
             *          Integer representation of the year.
             * @param   month
             *          Integer representation of the month.
             * @param   day
             *          Integer representation of the day.
             */
            public SelectDayButton(int year, int month, int day) {
                this.year = year;
                this.month = month;
                this.day = day;

                this.setLabel("" + day);
                this.addActionListener(this);
                this.setMargin(new Insets(0, 0, 0, 0));

                ArrayList appointments = calendarStore.getArrayListOfObjectsFor(year, month, day);
                ArrayList annualAppointments = calendarStore.getArrayListOfAnnualObjectsFor(month, day);
                Calendar c = Calendar.getInstance(TimeZone.getDefault());

                /**
                 * if today change colour (green)
                 */
                if (year == c.get(Calendar.YEAR) && month == c.get(Calendar.MONTH) && day == c.get(Calendar.DAY_OF_MONTH)) {
                    this.setBackground(new Color((float) 0.5, (float) 1, (float) 0.5));
                } /**
                 * else if appointments exist change colour (yellow)
                 */
                else if (appointments != null) {
                    float darkness; //value 0-1 representing how dark a colour

                    darkness = (float) (0.1 + (0.7 * appointments.size() / 10));
                    if (darkness > 1) {
                        darkness = (float) 1;
                    }

                    this.setBackground(new Color((float) 1, (float) 1, 1 - darkness));
                } /**
                 * else if annual appointments change colour (red)
                 */
                else if (annualAppointments != null) {
                    this.setBackground(new Color((float) 1, (float) 0.5, (float) 0.5));
                }

            }

            public void actionPerformed(ActionEvent e) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                setState(ST_DISPLAY_DAY);
            }
        }
    }

    /**
     * panel to display title string and allow left right movement of selected
     * time division
     *
     * @param calConst the calendar constant to move +/- 1, eg. Calendar.YEAR
     */
    public class TitlePanel extends JPanel implements ActionListener {

        private JButton leftButton;
        private JButton rightButton;
        private int calConst;

        public TitlePanel(String title, int calConst) {
            this.calConst = calConst;

            JPanel titlePanel = new JPanel();
            JPanel outerPanel = new JPanel(new BorderLayout());

            // create left right buttons and attach actions
            leftButton = new JButton("<");
            rightButton = new JButton(">");

            rightButton.setMargin(new Insets(1, 1, 1, 1));
            leftButton.setMargin(new Insets(1, 1, 1, 1));

            rightButton.addActionListener(this);
            leftButton.addActionListener(this);

            titlePanel.add(leftButton);
            titlePanel.add(new JLabel(title));
            titlePanel.add(rightButton);

            outerPanel.add(titlePanel, BorderLayout.CENTER);
            add(outerPanel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object eo = e.getSource();

            if (eo.equals(leftButton)) {
                cal.set(calConst, cal.get(calConst) - 1);
                setState(currentState);
            } else if (eo.equals(rightButton)) {
                cal.set(calConst, cal.get(calConst) + 1);
                setState(currentState);
            }
        }
    }

    /*
     * class to display the appointments for one given day
     */
    public class DayPanel extends JPanel {

        public DayPanel(int year, int month, int day) {
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            ArrayList daysAppointments = calendarStore.getArrayListOfObjectsFor(year, month, day);
            ArrayList daysAnnualAppointments = calendarStore.getArrayListOfAnnualObjectsFor(month, day);

            /**
             * annual appointments
             */
            if (daysAnnualAppointments != null) {
                for (int i = 0; i < daysAnnualAppointments.size(); i++) {
                    add(new SmallAppointmentPanel(month, day, i, ((AppointmentOld) daysAnnualAppointments.get(i))));
                }
            }

            /**
             * normal appointments
             */
            // if no appointments
            if (daysAppointments == null) {
                JPanel outer = new JPanel();
                JPanel inner = new JPanel(new BorderLayout());
                JPanel side = new JPanel(new BorderLayout());
                inner.add(new JButton(addAction), BorderLayout.NORTH);
                side.add(new JButton(viewTodayAction), BorderLayout.SOUTH);
                outer.add(inner);
                outer.add(side);
                add(outer);

            } else {
                Collections.sort(daysAppointments);

                for (int i = 0; i < daysAppointments.size(); i++) {
                    add(new SmallAppointmentPanel(year, month, day, i, ((AppointmentOld) daysAppointments.get(i))));
                }

            }
        }

    }

    /*
     * small appointment panel, does not allow editing
     */
    public class SmallAppointmentPanel extends JPanel implements MouseListener {

        private boolean annualAppointment;
        private int year;
        private int month;
        private int day;
        private int alIndex;
        private AppointmentOld app;
        private Border startBorder;

        public SmallAppointmentPanel(int month, int day, int alIndex, AppointmentOld app) {
            this.month = month;
            this.day = day;
            this.alIndex = alIndex;
            this.app = app;

            this.annualAppointment = true;

            generatePanel();
        }

        public SmallAppointmentPanel(int year, int month, int day, int alIndex, AppointmentOld app) {
            this.annualAppointment = false;
            this.year = year;
            this.month = month;
            this.day = day;
            this.alIndex = alIndex;
            this.app = app;

            generatePanel();
        }

        public void generatePanel() {

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumIntegerDigits(2);

            String subject = app.getSubject();
            String notes = app.getNotes();
            String time = "" + nf.format(app.getStartHour())
                    + ":"
                    + nf.format(app.getStartMinute())
                    + " - "
                    + nf.format(app.getEndHour())
                    + ":"
                    + nf.format(app.getEndMinute());
            String category = "" + app.getCategory();

            // create buttons etc
            JButton categoryButton = new JButton(new ImageIcon("i/cat" + category + ".gif"));
            JLabel timeLabel = new JLabel(time);
            JLabel subjectLabel = new JLabel(subject);
            JLabel notesLabel = new JLabel(notes);
            notesLabel.setForeground(Color.GRAY);

            double totalWidth = screenWidth * 6.5 / 10;

            categoryButton.setPreferredSize(new Dimension(40, 25));
            timeLabel.setPreferredSize(new Dimension(120, 25));
            subjectLabel.setPreferredSize(new Dimension(160, 25));
            notesLabel.setPreferredSize(new Dimension(((int) totalWidth - 320), 25));

            add(new JLabel(new ImageIcon("i/cat" + category + ".gif")));
            add(timeLabel);
            add(subjectLabel);
            add(notesLabel);

            setBorder(BorderFactory.createLineBorder(getBackground()));

            addMouseListener(this);

        }

        public void mouseClicked(MouseEvent e) {
            if (!annualAppointment) {
                cal.set(Calendar.YEAR, year);
            }
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            arrayListIndex = alIndex;

            if (annualAppointment) {
                setState(ST_VIEW_ANNUAL_APPOINTMENT);
            } else {
                setState(ST_VIEW_APPOINTMENT);
            }
        }

        public void mouseEntered(MouseEvent e) {
            startBorder = getBorder();
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }

        public void mouseExited(MouseEvent e) {
            setBorder(startBorder);
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

    }


    /*
     * allows editing of appointments or creation of new appointments
     */
    public class AppointmentPanel extends JPanel implements ActionListener {

        private JComboBox yearComboBox, monthComboBox, dayComboBox, categoryComboBox,
                startHourComboBox, startMinuteComboBox,
                endHourComboBox, endMinuteComboBox;
        private JButton saveButton, deleteButton;
        private JTextField subjectTF;
        private JTextArea notesTA;
        private int year;
        private int month;
        private int day;
        private int arrayListIndex = -1;


        /*
         * constructor for adding a new appointment
         */
        public AppointmentPanel(int year, int month, int day) {
            this(year, month, day, -1);
        }

        /**
         * constructs panel of exisitng appointment for editing
         */
        public AppointmentPanel(int year, int month, int day, int arrayListIndex) {
            //store variables to member variables
            this.year = year;
            this.month = month;
            this.day = day;
            this.arrayListIndex = arrayListIndex;

            // create arrays of choices for combo boxes etc.
            String[] yearChoices = {"" + (year - 1), "" + year, "" + (year + 1), "" + (year + 2), "" + (year + 3)};
            final String[] monthLabels = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            final String[] hourChoices = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
            final String[] minuteChoices = {"00", "15", "45"};
            final String[] categoryLabels = {"Personal", "Business", "School"};
            String[] dayChoices;

            /**
             * create components
             */
            // year/month/day  drop downs
            yearComboBox = new JComboBox(yearChoices);
            yearComboBox.setSelectedItem("" + year);
            yearComboBox.addActionListener(this);

            monthComboBox = new JComboBox(monthLabels);
            monthComboBox.setSelectedItem("" + monthLabels[month]);
            monthComboBox.addActionListener(this);

            dayComboBox = new JComboBox();
            refreshDaysDropdown();
            dayComboBox.setSelectedItem("" + day);

            // start finish time comboboxes
            startHourComboBox = new JComboBox(hourChoices);
            endHourComboBox = new JComboBox(hourChoices);
            startMinuteComboBox = new JComboBox(minuteChoices);
            endMinuteComboBox = new JComboBox(minuteChoices);

            // subject text field
            subjectTF = new JTextField(30);

            //notes scrollable text area
            notesTA = new JTextArea(8, 30);
            notesTA.setLineWrap(true);
            JScrollPane notesSP = new JScrollPane(notesTA,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            // category Combo  Box
            categoryComboBox = new JComboBox(categoryLabels);

            /**
             * if editing existing appointment initialise to its values
             */
            AppointmentOld appointment = null;
            if (arrayListIndex != -1) {
                appointment = (AppointmentOld) calendarStore.getObject(year, month, day, arrayListIndex);

                if (appointment != null) {
                    startHourComboBox.setSelectedItem("" + appointment.getStartHour());
                    startMinuteComboBox.setSelectedItem("" + appointment.getStartMinute());
                    endHourComboBox.setSelectedItem("" + appointment.getEndHour());
                    endMinuteComboBox.setSelectedItem("" + appointment.getEndMinute());
                    subjectTF.setText(appointment.getSubject());
                    notesTA.setText(appointment.getNotes());
                    categoryComboBox.setSelectedIndex(appointment.getCategory());
                }
            }

            /**
             * add components to panel
             */
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            JPanel temp;

            // date section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Date:"));
            temp.add(dayComboBox);
            temp.add(monthComboBox);
            temp.add(yearComboBox);
            add(temp);

            // start time section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Start Time:"));
            temp.add(startHourComboBox);
            temp.add(startMinuteComboBox);
            add(temp);

            // end time section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("End Time:"));
            temp.add(endHourComboBox);
            temp.add(endMinuteComboBox);
            add(temp);

            // end time section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Category:"));
            temp.add(categoryComboBox);
            add(temp);

            // subject section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Subject:"));
            temp.add(subjectTF);
            add(temp);

            // notes section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Notes:"));
            temp.add(notesSP);
            add(temp);

            // save or delete section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Action:"));

            saveButton = new JButton("Save");
            saveButton.addActionListener(this);
            temp.add(saveButton);

            // only add delete button if editing an existing appointment
            if (appointment != null) {
                deleteButton = new JButton("Delete");
                deleteButton.addActionListener(this);
                temp.add(deleteButton);
            }

            add(temp);
        }

        public void actionPerformed(ActionEvent e) {
            Object eo = e.getSource();

            //***** if year or month changed update number of days available to choose
            if (eo.equals(yearComboBox) || eo.equals(monthComboBox)) {
                refreshDaysDropdown();
            } //***** else if save button pressed, check if update or add, then goto viewDay
            else if (eo.equals(saveButton)) {
                int startEndTimeDiff;
                startEndTimeDiff = (Integer.parseInt((String) endHourComboBox.getSelectedItem()) * 60 + Integer.parseInt((String) endMinuteComboBox.getSelectedItem()))
                        - (Integer.parseInt((String) startHourComboBox.getSelectedItem()) * 60 + Integer.parseInt((String) startMinuteComboBox.getSelectedItem()));

                // check the user entered a subject
                if (subjectTF.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "You must enter a subject", "", JOptionPane.INFORMATION_MESSAGE);
                } else if (startEndTimeDiff < 0) {
                    JOptionPane.showMessageDialog(null, "Appointment cannot end before it begins.", "", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // if this was an update , delete old appointment
                    if (this.arrayListIndex != -1) {
                        calendarStore.deleteObject(this.year, this.month, this.day, this.arrayListIndex);
                    }

                    //add new appointment object to storage
                    AppointmentOld app = new AppointmentOld(Integer.parseInt((String) startHourComboBox.getSelectedItem()),
                            Integer.parseInt((String) startMinuteComboBox.getSelectedItem()),
                            Integer.parseInt((String) endHourComboBox.getSelectedItem()),
                            Integer.parseInt((String) endMinuteComboBox.getSelectedItem()),
                            categoryComboBox.getSelectedIndex(),
                            subjectTF.getText(),
                            notesTA.getText());

                    calendarStore.addObject(Integer.parseInt((String) yearComboBox.getSelectedItem()),
                            monthComboBox.getSelectedIndex(),
                            Integer.parseInt((String) dayComboBox.getSelectedItem()),
                            app);

                    // set day/month/year to be looked at then setState to look at that day
                    cal.set(Calendar.MONTH, monthComboBox.getSelectedIndex());
                    cal.set(Calendar.YEAR, Integer.parseInt((String) yearComboBox.getSelectedItem()));
                    cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt((String) dayComboBox.getSelectedItem()));
                    setState(ST_DISPLAY_DAY);
                }

            } //***** else if delete button pressed then delete and goto viewDay in question
            else if (eo.equals(deleteButton)) {
                calendarStore.deleteObject(this.year, this.month, this.day, this.arrayListIndex);
                setState(ST_DISPLAY_DAY);
            }
        }

        /**
         * call this to calculate and refresh choice of days allowed calculates
         * number of days in month, for given year/month and adjusts the
         * comboBox
         */
        private void refreshDaysDropdown() {
            //create calendar for whatever year month is set
            Calendar dropDownsCal = Calendar.getInstance(TimeZone.getDefault());
            dropDownsCal.set(Calendar.MONTH, monthComboBox.getSelectedIndex());
            dropDownsCal.set(Calendar.YEAR, Integer.parseInt((String) yearComboBox.getSelectedItem()));

            int daysInThisMonth = dropDownsCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            String[] dayChoices = new String[daysInThisMonth];

            String prevSelectedDay = (String) dayComboBox.getSelectedItem();
            dayComboBox.removeAllItems();

            for (int i = 1; i <= daysInThisMonth; i++) {
                dayComboBox.addItem("" + i);
            }

            if (prevSelectedDay == null) {
                dayComboBox.setSelectedItem("" + day);
            } else {
                dayComboBox.setSelectedItem(prevSelectedDay);
            }

        }

    }

    /*
     * allows editing of appointments or creation of new appointments
     */
    public class AnnualAppointmentPanel extends JPanel implements ActionListener {

        private JComboBox yearComboBox, monthComboBox, dayComboBox,
                startHourComboBox, startMinuteComboBox,
                endHourComboBox, endMinuteComboBox;
        private JButton saveButton, deleteButton;
        private JTextField subjectTF;
        private JTextArea notesTA;

        private int month;
        private int day;
        private int arrayListIndex = -1;


        /*
         * for adding a new appointment
         */
        public AnnualAppointmentPanel(int month, int day) {
            this(month, day, -1);
        }

        /**
         * initialises panel panel displays exisitng appointment for editing or
         * new panel to add
         *
         * @param arrayListIndex -1 if new appointment or array list index
         * otherwise
         */
        public AnnualAppointmentPanel(int month, int day, int arrayListIndex) {

            //store variables to member variables
            this.month = month;
            this.day = day;
            this.arrayListIndex = arrayListIndex;

            // create arrays of choices for combo boxes etc.
            final String[] monthLabels = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            final String[] hourChoices = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
            final String[] minuteChoices = {"00", "15", "45"};
            String[] dayChoices;

            /**
             * create components
             */
            monthComboBox = new JComboBox(monthLabels);
            monthComboBox.setSelectedItem("" + monthLabels[month]);
            monthComboBox.addActionListener(this);

            dayComboBox = new JComboBox();

            for (int i = 1; i <= 31; i++) {
                dayComboBox.addItem("" + i);
            }

            dayComboBox.setSelectedItem("" + day);

            // start finish time comboboxes
            startHourComboBox = new JComboBox(hourChoices);
            endHourComboBox = new JComboBox(hourChoices);
            startMinuteComboBox = new JComboBox(minuteChoices);
            endMinuteComboBox = new JComboBox(minuteChoices);

            // subject text field
            subjectTF = new JTextField(30);

            //notes scrollable text area
            notesTA = new JTextArea(8, 30);
            notesTA.setLineWrap(true);
            JScrollPane notesSP = new JScrollPane(notesTA,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            /**
             * if editing existing appointment initialise to its values
             */
            AppointmentOld appointment = null;
            if (arrayListIndex != -1) {
                appointment = (AppointmentOld) calendarStore.getAnnualObject(month, day, arrayListIndex);

                if (appointment != null) {
                    startHourComboBox.setSelectedItem("" + appointment.getStartHour());
                    startMinuteComboBox.setSelectedItem("" + appointment.getStartMinute());
                    endHourComboBox.setSelectedItem("" + appointment.getEndHour());
                    endMinuteComboBox.setSelectedItem("" + appointment.getEndMinute());
                    subjectTF.setText(appointment.getSubject());
                    notesTA.setText(appointment.getNotes());
                }
            }

            /**
             * add components to panel
             */
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            JPanel temp;

            // date section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Date:"));
            temp.add(dayComboBox);
            temp.add(monthComboBox);
            add(temp);

            // start time section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Start Time:"));
            temp.add(startHourComboBox);
            temp.add(startMinuteComboBox);
            add(temp);

            // end time section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("End Time:"));
            temp.add(endHourComboBox);
            temp.add(endMinuteComboBox);
            add(temp);

            // subject section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Subject:"));
            temp.add(subjectTF);
            add(temp);

            // notes section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Notes:"));
            temp.add(notesSP);
            add(temp);

            // save or delete section
            temp = new JPanel(new FlowLayout());
            temp.setBorder(BorderFactory.createTitledBorder("Action:"));

            saveButton = new JButton("Save");
            saveButton.addActionListener(this);
            temp.add(saveButton);

            // only add delete button if editing an existing appointment
            if (appointment != null) {
                deleteButton = new JButton("Delete");
                deleteButton.addActionListener(this);
                temp.add(deleteButton);
            }

            add(temp);
        }

        public void actionPerformed(ActionEvent e) {
            Object eo = e.getSource();

            if (eo.equals(saveButton)) {
                int startEndTimeDiff;
                startEndTimeDiff = (Integer.parseInt((String) endHourComboBox.getSelectedItem()) * 60 + Integer.parseInt((String) endMinuteComboBox.getSelectedItem()))
                        - (Integer.parseInt((String) startHourComboBox.getSelectedItem()) * 60 + Integer.parseInt((String) startMinuteComboBox.getSelectedItem()));

                // check the user entered a subject
                if (subjectTF.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "You must enter a subject", "", JOptionPane.INFORMATION_MESSAGE);
                } else if (startEndTimeDiff < 0) {
                    JOptionPane.showMessageDialog(null, "Appointment cannot end before it begins.", "", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // if this was an update , delete old appointment
                    if (this.arrayListIndex != -1) {
                        calendarStore.deleteAnnualObject(this.month, this.day, this.arrayListIndex);
                    }

                    //add new appointment object to storage
                    AppointmentOld app = new AppointmentOld(Integer.parseInt((String) startHourComboBox.getSelectedItem()),
                            Integer.parseInt((String) startMinuteComboBox.getSelectedItem()),
                            Integer.parseInt((String) endHourComboBox.getSelectedItem()),
                            Integer.parseInt((String) endMinuteComboBox.getSelectedItem()),
                            3,
                            subjectTF.getText(),
                            notesTA.getText());

                    calendarStore.addAnnualObject(monthComboBox.getSelectedIndex(),
                            Integer.parseInt((String) dayComboBox.getSelectedItem()),
                            app);

                    // set day/month to be looked at then setState to look at that day
                    cal.set(Calendar.MONTH, monthComboBox.getSelectedIndex());
                    cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt((String) dayComboBox.getSelectedItem()));
                    setState(ST_DISPLAY_DAY);
                }

            } // else if delete button pressed then delete and goto viewDay in question
            else if (eo.equals(deleteButton)) {
                calendarStore.deleteAnnualObject(this.month, this.day, this.arrayListIndex);
                setState(ST_DISPLAY_DAY);
            }
        }

    }

    /**
     * takes an int representing a month and returns the string for it
     */
    public static String getMonthString(int month) {
        final String[] monthLabels = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthLabels[month];
    }

}
