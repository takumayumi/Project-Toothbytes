/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import components.listener.ToothListener;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jolas
 */
public class DentalChart extends JPanel implements ToothListener {

    private Graphics2D g2d;
    private ArrayList<Tooth> allTeeth, l1, l2, l3, l4, l5, l6, l7, l8;
    private MigLayout layout, mainLayout;
    private boolean enabled = false;
    private JPopupMenu tablePop;
    private JMenuItem deleteRow;
    private JScrollPane tableScroll;
    private static DefaultTableModel tableModel;
    private JTable table;
    private JPanel tablePanel;
    private ChartPanel chartPanel;

    public DentalChart() {
        mainLayout = new MigLayout("wrap 4", "[][][][]", "[300px][250px]");

        this.setSize(970, 600);
        this.setLayout(mainLayout);

        chartPanel = new ChartPanel();

        tablePanel = new JPanel(new BorderLayout());

        this.add(chartPanel, "span 4 1");

        allTeeth = new ArrayList<Tooth>();
        l1 = new ArrayList<Tooth>();
        l2 = new ArrayList<Tooth>();
        l3 = new ArrayList<Tooth>();
        l4 = new ArrayList<Tooth>();
        l5 = new ArrayList<Tooth>();
        l6 = new ArrayList<Tooth>();
        l7 = new ArrayList<Tooth>();
        l8 = new ArrayList<Tooth>();

        initTeeth();

        //5th quadrant
        chartPanel.add(l5.get(4), "skip 3,span 1");
        for (int i = 3; i >= 0; i--) {
            chartPanel.add(l5.get(i), "span 1");
        }
        //6th quadrant
        for (int i = 0; i < 5; i++) {
            chartPanel.add(l6.get(i), "span 1");
        }
        //1st quadrant
        chartPanel.add(l1.get(7), "skip 3, span 1");
        for (int i = 6; i >= 0; i--) {
            chartPanel.add(l1.get(i), "span 1");
        }
        //2nd quadrant
        for (int i = 0; i < 8; i++) {
            chartPanel.add(l2.get(i), "span 1");
        }
        //4th quadrant
        for (int i = 7; i >= 0; i--) {
            chartPanel.add(l4.get(i), "span 1");
        }
        //3rd quadrant
        for (int i = 0; i < 8; i++) {
            chartPanel.add(l3.get(i), "span 1");
        }

        //8th quadrant
        chartPanel.add(l8.get(4), "skip 3,span 1");
        for (int i = 3; i >= 0; i--) {
            chartPanel.add(l8.get(i), "span 1");
        }
        //7th quadrant
        for (int i = 0; i < 5; i++) {
            chartPanel.add(l7.get(i), "span 1");
        }

        tablePop = new JPopupMenu();
        deleteRow = new JMenuItem("Delete Entry");
        deleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable t = (JTable) e.getSource();
                t.getValueAt(t.getSelectedRow(), 0);
                tableModel.removeRow(t.getSelectedRow());
            }
        });
        tablePop.add(deleteRow);
        table = new JTable();
        table.setComponentPopupMenu(tablePop);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        setupTable();

        tableScroll = new JScrollPane(table);
//        tablePanel.add(tableScroll, BorderLayout.CENTER);
        this.add(tableScroll, "span 4 1");
    }

    private void initTeeth() {

        int tens = 10; // increment
        int max = 1; // maximum number

        for (int i = 1; i <= 52; i++) {
            try {
                //premanent teeth
                if (i <= 32) {
                    Tooth temp = new Tooth(tens + max, "normal", false);
                    temp.addToothListener(this);
                    allTeeth.add(temp);
                    max++;

                    if (max > 8) {
                        max = 1;
                        tens += 10;
                    }

                    //prime teeth
                } else {
                    Tooth temp = new Tooth(tens + max, "normal", false);

                    temp.addToothListener(this);
                    allTeeth.add(temp);
                    max++;

                    if (max > 5) {
                        max = 1;
                        tens += 10;
                    }
                }

            } catch (IOException e) {
                //todo
            }

        }

        for (int i = 0; i < 8; i++) {
            l1.add(allTeeth.get(i));
            l2.add(allTeeth.get(i + 8));
            l3.add(allTeeth.get(i + 16));
            l4.add(allTeeth.get(i + 24));
        }

        for (int i = 32; i < 37; i++) {
            l5.add(allTeeth.get(i));
            l6.add(allTeeth.get(i + 5));
            l7.add(allTeeth.get(i + 10));
            l8.add(allTeeth.get(i + 15));
        }
    }

    public void updatePreState(String s) {
        for (int i = 0; i < 52; i++) {
            allTeeth.get(i).setPreState(s);
        }
    }

    public void updateTooth(int n, String state) {
        for (int i = 0; i < 52; i++) {
            if (allTeeth.get(i).getNumber() == n) {
                allTeeth.get(i).setState(state);
                allTeeth.get(i).setModified(true);
                updateTable(n, state);
            }
        }
    }

    public void setEnabled(boolean b) {
        this.enabled = b;
    }

    public void setupTable() {
        tableModel.addColumn("Tooth No.");
        tableModel.addColumn("Condition");
        tableModel.addColumn("Remarks");

        table = new JTable(tableModel);

        table.getColumnModel().getColumn(0).setMinWidth(100);
        table.getColumnModel().getColumn(1).setMinWidth(60);
        table.getColumnModel().getColumn(2).setMinWidth(100);

        table.getColumnModel().getColumn(0).setMaxWidth(200);
        table.getColumnModel().getColumn(1).setMaxWidth(100);
        table.getColumnModel().getColumn(2).setMaxWidth(300);

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getTableHeader().setReorderingAllowed(false);
    }
//
//    public void overwriteRow(int row, int n, String s) {
//
//    }
//
//    public void addRow(int n, String s) {
//        tableModel.addRow(new Object[]{"", "", "", ""});
//        table.setValueAt(n, tableModel.getRowCount() - 1, 0);
//        table.setValueAt(s, tableModel.getRowCount() - 1, 1);
//        table.setValueAt("", tableModel.getRowCount() - 1, 2);
//    }

    public void updateTable(int n, String s) {

        if (tableModel.getRowCount() != 0) {
            boolean trigger = true;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                boolean check = table.getValueAt(i, 0).toString().equals(n);
                System.out.println(table.getValueAt(i, 0).toString()+" vs "+n+" = "+check);
                
                if (Integer.parseInt(table.getValueAt(i, 0).toString()) == n) {
                    
                    table.setValueAt(n, i, 0);
                    table.setValueAt(s, i, 1);
                    table.setValueAt("", i, 2);
                    trigger = false;
                    break;
                }
            }
            if (trigger) {
                tableModel.addRow(new Object[]{"", "", "", ""});
                table.setValueAt(n, tableModel.getRowCount() - 1, 0);
                table.setValueAt(s, tableModel.getRowCount() - 1, 1);
                table.setValueAt("", tableModel.getRowCount() - 1, 2);
            }
        } else {
            tableModel.addRow(new Object[]{"", "", "", ""});
            table.setValueAt(n, tableModel.getRowCount() - 1, 0);
            table.setValueAt(s, tableModel.getRowCount() - 1, 1);
            table.setValueAt("", tableModel.getRowCount() - 1, 2);
        }
    }

    public void notify(int number, String state) {
        updateTable(number, state);
    }
}
