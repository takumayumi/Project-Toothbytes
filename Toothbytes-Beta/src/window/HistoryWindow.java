/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.DentalChart;
import models.OrganizedTreatment;

/**
 *
 * @author Jolas
 */
public class HistoryWindow extends JDialog {

    private String[] dates;
    private DentalChart dc;

    public HistoryWindow(Frame f, ArrayList<OrganizedTreatment> otList) {
        super(f);
        this.setTitle("History");
        this.setSize(1000, 600);

        dates = createList(otList);

        JList list = new JList(dates);
        JScrollPane scroll = new JScrollPane(list);
        dc = new DentalChart();
        JScrollPane scrollDC = new JScrollPane(dc);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int j = list.getSelectedIndex();
                    
                    for (int i = 1; i < 53; i++) {
                        if (otList.get(j).getHm().containsKey(i)) {
                            dc.updateTooth(i + 1, otList.get(j).getHm().get(i).toLowerCase());
                            dc.updateUI();
                        }
                    }
                }
            }
        });
        this.add(scrollDC, BorderLayout.CENTER);
        this.add(scroll, BorderLayout.WEST);
    }

    public String[] createList(ArrayList<OrganizedTreatment> otList) {
        String[] dates = new String[otList.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        for (int i = 0; i < otList.size(); i++) {
            dates[i] = sdf.format(otList.get(i).getDate().getTime());
        }
        return dates;
    }
}
