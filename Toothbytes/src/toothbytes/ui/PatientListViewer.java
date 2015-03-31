package toothbytes.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import toothbytes.database.DBAccess;
import toothbytes.model.Patient;
import toothbytes.model.TBListModel;

public class PatientListViewer extends JPanel {

    private Map<String, ImageIcon> iMap;
    private JList viewer;
    private JTextField searchField;
    int keyLength;

    public PatientListViewer(ArrayList<Patient> pList) {
        this.setLayout(new BorderLayout());

        iMap = mapImages(pList);
        viewer = new JList(new TBListModel(pList));
        viewer.setCellRenderer(new PatientCellRenderer());

        JScrollPane scroll = new JScrollPane(viewer);

        searchField = new JTextField("Search Patient");
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setForeground(Color.gray);

        searchField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        keyLength = searchField.getText().length();
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (keyLength > searchField.getText().length()) {
                            viewer.setModel(new TBListModel(pList));
                            filterList();
                        } else {
                            filterList();
                        }
                    }
                }
        );
        searchField.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        searchField.setText("");
                        searchField.setForeground(Color.black);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                }
        );
        this.add(searchField, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
    }

    /**
     * This method creates an array of full names of patients
     *
     * @param pList - list of patients
     * @return array of names
     */
    private String[] createNames(ArrayList<Patient> pList) {
        String[] names = new String[pList.size()];
        for (int i = pList.size() - 1; i > 0; i--) {
            names[i] = pList.get(i).getFullName();
        }
        return names;
    }

    /**
     * This method maps the image path to patient names
     *
     * @param pList - list of patients
     * @return map object
     */
    private Map<String, ImageIcon> mapImages(ArrayList<Patient> pList) {
        Map<String, ImageIcon> map = new HashMap<>();
        for (Patient p : pList) {
            File f = new File("src/toothbytes/res/photos/" + p.getId() + ".jpg");
            if (f.exists()) {
                map.put(p.getFullName() + "", new ImageIcon(
                        "src/toothbytes/res/photos/" + p.getId() + ".jpg"));
            } else {
                map.put(p.getFullName() + "", new ImageIcon(
                        "src/toothbytes/res/photos/patient.png"));
            }
        }
        return map;
    }

    public class PatientCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            Patient p = (Patient) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(iMap.get(p.getFullName()));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;
        }
    }

    DefaultListModel filteredModel;
    DefaultListModel noMatchModel;

    private void filterList() {
// Setting up the environment for the logic
        int start = 0;
        int itemIx = 0;

// Here the glitch that one should remeber
// Sets hold NO DUPLICATE values... :)
        Set resultSet = new HashSet();

        filteredModel = new DefaultListModel();

// Following logic is used to find an item in JList
        String prefix = searchField.getText();

        javax.swing.text.Position.Bias direction = javax.swing.text.Position.Bias.Forward;

        for (int i = 0; i < viewer.getModel().getSize(); i++) {
            itemIx = viewer.getNextMatch(prefix, start, direction);

// Following try-catch blog will enhance the user friendliness
            try {
                resultSet.add(viewer.getModel().getElementAt(itemIx));
            } catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "No entry is matched with your query....");
                searchField.setText("");
                return;
            }

            start++;
        }

        Iterator itr = resultSet.iterator();

// Adding the filtered results to the new model
        while (itr.hasNext()) {
            filteredModel.addElement(itr.next());
        }

// Setting the model to the list again
        viewer.setModel(filteredModel);
    }

    public void setListListener(ListSelectionListener lsl) {
        viewer.addListSelectionListener(lsl);
    }

    public Patient getSelectedPatient() {
        return (Patient) viewer.getSelectedValue();
    }
}
