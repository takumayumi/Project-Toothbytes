package toothbytes.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
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
import javax.swing.event.ListSelectionListener;
import toothbytes.model.Patient;
import toothbytes.model.TBListModel;


/**
 * <h1>PatienListViewer</h1>
 * The {@code PatientListViewer} class creates a modified JList for patients.
 */
public class PatientListViewer extends JPanel {

    private Map<String, ImageIcon> iMap;
    private JList viewer;
    private JTextField searchField;
    int keyLength;

    /**
     * This method is used to construct the interface for viewing the list of 
     * patients.
     * @param   pList
     *          List of patients.
     */
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
     * This method creates an array of full names of patients.
     * @param   pList
     *          List of patients.
     * @return  Array of names.
     */
    private String[] createNames(ArrayList<Patient> pList) {
        String[] names = new String[pList.size()];
        for (int i = pList.size() - 1; i > 0; i--) {
            names[i] = pList.get(i).getFullName();
        }
        return names;
    }

    /**
     * This method maps the image path to patient names.
     * @param   pList
     *          List of patients.
     * @return  Map object.
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

    /**
     * <h1>PatientCallRenderer</h1>
     * The {@code PatientCallRenderer} class renders a cell for the JList.
     */
    public class PatientCellRenderer extends DefaultListCellRenderer {

        /**
         * This method is used to get the list of cell component when 
         * rendered.
         * @param   list
         *          JList object.
         * @param   value
         *          An object representation.
         * @param   index
         *          An integer representation.
         * @param   isSelected
         *          A boolean representation.
         * @param   cellHasFocus
         *          A boolean representation.
         * @return  JLabel.
         */
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

    /**
     * This method sets up the environment for the logic function of the 
     * program and sets hold NO DUPLICATE values. Following logic is used to 
     * find an item in JList and following try-catch blog will enhance the 
     * user friendliness. It allows to add the filtered results to the new 
     * model and sets the model to the list again.
     */
    private void filterList() {
        int start = 0;
        int itemIx = 0;

        Set resultSet = new HashSet();

        filteredModel = new DefaultListModel();

        String prefix = searchField.getText();

        javax.swing.text.Position.Bias direction = javax.swing.text.Position.Bias.Forward;

        for (int i = 0; i < viewer.getModel().getSize(); i++) {
            itemIx = viewer.getNextMatch(prefix, start, direction);
            
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

        while (itr.hasNext()) {
            filteredModel.addElement(itr.next());
        }

        viewer.setModel(filteredModel);
    }

    /**
     * This method sets a list listener for viewing the patients list.
     * @param   lsl 
     *          Object representation of lsl.
     */
    public void setListListener(ListSelectionListener lsl) {
        viewer.addListSelectionListener(lsl);
    }

    /**
     * This method is used to get the selected patient.
     * @return  A patient selected by the user.
     */
    public Patient getSelectedPatient() {
        return (Patient) viewer.getSelectedValue();
    }
}
