/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import components.listener.ChartListener;
import components.listener.ToothListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.miginfocom.swing.MigLayout;
import utilities.Configuration;
import utilities.DBAccess;

/**
 *
 * @author Jolas
 */
public class DentalChart extends JPanel implements ToothListener {

    private Graphics2D g2d;
    private ArrayList<Tooth> allTeeth, l1, l2, l3, l4, l5, l6, l7, l8;
    private MigLayout layout, mainLayout;
    private boolean tableEnabled = true;
    private JPopupMenu tablePop;
    private JMenuItem deleteRow;
    private JScrollPane tableScroll;
    private static DefaultTableModel tableModel;
    private JTable table;
    private JPanel tablePanel;
    private ChartPanel chartPanel;
    private String[] conditions = {"normal", "missing", "unerupted", "decayed"};
    private ArrayList<String> feeList;
    private ChartListener cl;

    public DentalChart(boolean tableEnabled) {
        this.tableEnabled = tableEnabled;
        mainLayout = new MigLayout("wrap 4", "[][][][]", "[300px][180px]");

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

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col >= 2) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        setupTable();

        tableScroll = new JScrollPane(table);
//        tableScroll.setVisible(tableEnabled);
//        tablePanel.add(tableScroll, BorderLayout.CENTER);
        this.add(tableScroll, "span 4 1, grow");
        feeList = new ArrayList<String>();
    }

    public JTable getTable() {
        return this.table;
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

    public void updatePreState(String s, String t) {
        for (int i = 0; i < 52; i++) {
            allTeeth.get(i).setPreState(s);
            allTeeth.get(i).setSecondState(t);
        }
    }

    public void updateTooth(int n, String state, boolean delete) {
        for (int i = 0; i < 52; i++) {
            if (allTeeth.get(i).getNumber() == n) {
                allTeeth.get(i).setState(state);
                allTeeth.get(i).setModified(true);
                if (!delete) {
                    updateTable(n, state);
                }
            }
        }
    }

    public void deleteRow(int i) {
        System.out.println("Update tooth at:" + (Integer) table.getValueAt(i, 0));
        updateTooth((Integer) table.getValueAt(i, 0), "normal", true);
        tableModel.removeRow(i);
        this.updateUI();
    }

    public Object[][] getBreakDown() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < feeList.size(); i++) {
            if (map.isEmpty()) {
                map.put(feeList.get(i), 1);
            } else {
                if (!map.containsKey(feeList.get(i))) {
                    map.put(feeList.get(i), 1);
                } else {
                    map.replace(feeList.get(i), map.get(feeList.get(i)) + 1);
                }
            }
        }

        Object[][] bDown = new Object[map.size()][3];
        Set ents = map.entrySet();
        Iterator entIterator = ents.iterator();

        int i = 0;
        while (entIterator.hasNext()) {
            Map.Entry mapping = (Map.Entry) entIterator.next();
            bDown[i][0] = mapping.getKey();
            bDown[i][1] = mapping.getValue();
            bDown[i][2] = DBAccess.getServiceFee((String) mapping.getKey()) * (Integer) mapping.getValue();
            i++;
        }
        return bDown;
    }

    public void setTableEnabled(boolean b) {
        this.tableEnabled = b;
    }

    public void setupTable() {
        tableModel.addColumn("Tooth No.");
        tableModel.addColumn("Condition");
        tableModel.addColumn("Remarks");
        if (tableEnabled) {
            tableModel.addColumn(" ");
        }

        table = new JTable(tableModel);

        if (tableEnabled) {
            table.getColumn(" ").setCellRenderer(new ButtonRenderer());
            table.getColumn(" ").setCellEditor(
                    new ButtonEditor(new JCheckBox()));
            table.getColumnModel().getColumn(3).setMinWidth(50);
            table.getColumnModel().getColumn(3).setMaxWidth(125);
            table.getColumnModel().getColumn(3).setPreferredWidth(125);
        }
        table.setFont(Configuration.TB_FONT_HEADER);
        table.setRowHeight(30);

        table.getColumnModel().getColumn(0).setMinWidth(60);
        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(2).setMinWidth(350);

        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setMaxWidth(600);

        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(600);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getTableHeader().setReorderingAllowed(false);
    }

    public void updateTable(int n, String s) {

        if (tableModel.getRowCount() != 0) {
            boolean trigger = true;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                boolean check = table.getValueAt(i, 0).toString().equals(n);
                System.out.println(table.getValueAt(i, 0).toString() + " vs " + n + " = " + check);

                if (Integer.parseInt(table.getValueAt(i, 0).toString()) == n) {

                    table.setValueAt(n, i, 0);
                    table.setValueAt(s, i, 1);
                    table.setValueAt("", i, 2);
                    if (tableEnabled) {
                        table.setValueAt("Delete Entry", i, 3);
                    }
                    trigger = false;
                    break;
                }
            }
            if (trigger) {
                tableModel.addRow(new Object[]{"", "", "", ""});
                table.setValueAt(n, tableModel.getRowCount() - 1, 0);
                table.setValueAt(s, tableModel.getRowCount() - 1, 1);
                table.setValueAt("", tableModel.getRowCount() - 1, 2);
                if (tableEnabled) {
                    table.setValueAt("Delete Entry", tableModel.getRowCount() - 1, 3);
                }
            }
        } else {
            tableModel.addRow(new Object[]{"", "", "", ""});
            table.setValueAt(n, tableModel.getRowCount() - 1, 0);
            table.setValueAt(s, tableModel.getRowCount() - 1, 1);
            table.setValueAt("", tableModel.getRowCount() - 1, 2);
            if (tableEnabled) {
                table.setValueAt("Delete Entry", tableModel.getRowCount() - 1, 3);
            }
        }
        for (int i = 0; i < conditions.length; i++) {
            if (!s.matches(conditions[i])) {
                feeList.add(s);
            }
        }
    }

    public void notify(int number, String state) {
        updateTable(number, state);
        if (cl != null) {
            notifyChartListener(true);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    public void addChartListener(ChartListener cl) {
        this.cl = cl;
    }

    public void notifyChartListener(boolean b) {
        cl.notify(b);
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;

        private String label;

        private boolean isPushed;
        private int delRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
//                    fireEditingStopped();
                    if (tableModel.getRowCount() == 1) {
                        if (cl != null) {
                            notifyChartListener(true);
                        }
                    }
                    deleteActionFired();
                    cancelCellEditing();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            delRow = row;
            isPushed = true;
            return button;
        }

        public void deleteActionFired() {
            deleteRow(delRow);
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // 
                // 
                JOptionPane.showMessageDialog(button, label + ": Ouch!");
                // System.out.println(label + ": Ouch!");
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
