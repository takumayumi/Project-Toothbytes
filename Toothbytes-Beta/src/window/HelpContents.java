/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package window;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class HelpContents extends Frame implements ActionListener,ItemListener,TextListener{

    Button next, previous;
    List list;
    ScrollPane scroll;
    Label title, search;
    TextField textsearch;
    TextArea textdefinition;
    Panel p_up, p_title, p_t1, p_search, p_body, p_down, p_complete;
    Data d = new Data();
    String[] keys, delete = null;
    String viewWord = "";
    int currentindex = 0;

    public HelpContents(){

        super("HELP:Toothbytes");
        this.setSize(750,530);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(750,530));

        // Panel title.
        p_title = new Panel();
        p_title.setLayout(new BorderLayout());
        p_title.add(title = new Label("HELP: Toothbytes",Label.CENTER),BorderLayout.CENTER);
        Font f22 = new Font("Dialog",Font.PLAIN,22);
        title.setFont(f22);
        title.setForeground(Color.RED);

        p_down = new Panel();
        p_down.setLayout(new BorderLayout());
        p_t1 = new Panel();
        p_t1.setLayout(new FlowLayout());
        p_t1.add(previous = new Button("<"));
        p_t1.add(next = new Button(">"));
        p_down.add(p_t1,BorderLayout.EAST);

        // Panel search.
        p_search = new Panel();
        p_search.setLayout(new BorderLayout());
        p_search.add(textsearch = new TextField(),BorderLayout.CENTER);
        textsearch.setFont(f22);
        p_search.add(search = new Label("Search:"),BorderLayout.WEST);
        search.setFont(f22);
        search.setBackground(Color.blue);

        // Mix title + search.
        p_up = new Panel();
        p_up.setLayout(new GridLayout(2,1));
        p_up.add(p_title);
        p_up.add(p_search);
        p_body = new Panel();
        p_body.setLayout(new BorderLayout());
        p_body.add(list = new List(),BorderLayout.WEST);
        Font f14 = new Font("Cambria",Font.PLAIN,14);
        list.setFont(f14);
        p_body.add(textdefinition = new TextArea("",50,50,TextArea.SCROLLBARS_VERTICAL_ONLY),BorderLayout.CENTER);
        textdefinition.setFont(f14);
        textdefinition.setForeground(Color.BLUE);

        // Completed it all and add to frame.
        p_complete = new Panel();
        p_complete.setLayout(new BorderLayout());
        p_complete.add(p_up,BorderLayout.NORTH);
        p_complete.add(p_body,BorderLayout.CENTER);
        p_complete.add(new Label(""),BorderLayout.SOUTH);
        p_complete.add(p_down,BorderLayout.SOUTH);
        this.add(p_complete);

        // When program load.
        getKey();

        // Add event and function to do work.
        this.addWindowListener(closing());
        previous.addActionListener(this);
        next.addActionListener(this);
        list.addItemListener(this);
        textsearch.addTextListener(this);
    }

    void moveIndex(int index){
            
        list.select(index);
        String selected = list.getItem(index);

        if(d.hm.containsKey(selected)) {
            textdefinition.setText(d.hm.get(selected));
            viewWord += selected + "!";
        }
    }

    void getKey(){
        textsearch.requestFocusInWindow();
        keys = (String[])d.hm.keySet().toArray(new String[0]);
        Arrays.sort(keys,String.CASE_INSENSITIVE_ORDER);
        list.clear();
        for(String key : keys)
        list.add(key);
        moveIndex(0);

        // Make hand cursor on the list and move button.
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        list.setCursor(cursor);
        previous.setCursor(cursor);
        next.setCursor(cursor);
    }
    
    WindowAdapter closing(){
        WindowAdapter close = new WindowAdapter(){

        public void windowClosing(WindowEvent e){
            show(false);}
        };

        return close;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == previous) {
            currentindex = list.getSelectedIndex();
            list.select(currentindex);
            
            if(currentindex > 0) {
                    currentindex--;
            }
            
            moveIndex(currentindex);
            textsearch.setText(list.getItem(currentindex));
    	} else {
            currentindex = list.getSelectedIndex();
            list.select(currentindex);
            
            if(currentindex < (keys.length - 1)) {
                    currentindex++;
            }
            
            moveIndex(currentindex);
            textsearch.setText(list.getItem(currentindex));
    	}
    }
    
    @Override
    public void itemStateChanged(ItemEvent e){
        int index = (Integer)e.getItem();
        String selected = list.getItem(index);
        if(d.hm.containsKey(selected)){
                        textdefinition.setText(d.hm.get(selected));
                        textsearch.setText(selected);
        }
    }

    @Override
    public void textValueChanged(TextEvent e){
    	try{
            String search = textsearch.getText();
            for(int i = 0; i <= list.getItemCount();i++){
                String item = list.getItem(i);
                for(int s = 0; s <= item.length(); s++){
                        String sub = item.substring(0,s);
                    if(search.equalsIgnoreCase(sub)){
                            moveIndex(i);
                            return;
                    }
                }
            }
        } catch(ArrayIndexOutOfBoundsException ae) {
            return;
        }
    }

    void updateWord() {
    	final Frame frmupdate = new Frame();
    	final TextField txtword;
    	final TextArea txtdefi;
    	final Label view;
    	Panel head,body;
    	Button btnupdate;
    	frmupdate.setTitle("Update Word");
    	head = new Panel(new BorderLayout());
    	head.add(new Label("Enter Word: (Word in case sensitive)"),BorderLayout.NORTH);
    	head.add(txtword = new TextField(25),BorderLayout.CENTER);
    	head.add(view = new Label(""),BorderLayout.SOUTH);
    	body = new Panel(new BorderLayout());
    	body.add(new Label("Meaning:"),BorderLayout.NORTH);
    	body.add(txtdefi = new TextArea("",20,50,TextArea.SCROLLBARS_VERTICAL_ONLY),BorderLayout.CENTER);
    	frmupdate.add(head,BorderLayout.NORTH);
 		frmupdate.add(body,BorderLayout.CENTER);
    	frmupdate.add(btnupdate = new Button("Update"),BorderLayout.SOUTH);
    	txtword.addTextListener((TextEvent e) -> {
            try {
                String search1 = txtword.getText();
                for (int i = 0; i <= list.getItemCount(); i++) {
                    String item = list.getItem(i);
                    for (int s = 0; s <= item.length(); s++) {
                        String sub = item.substring(0,s);
                        if (search1.equalsIgnoreCase(sub)) {
                            moveIndex(i);
                            String selected = list.getSelectedItem();
                            view.setForeground(Color.RED);
                            view.setText(selected);
                            if(d.hm.containsKey(selected)){
                                txtdefi.setForeground(Color.BLUE);
                                txtdefi.setText(d.hm.get(selected));
                            }
                            return;
                        }
                    }
                }
            } catch(ArrayIndexOutOfBoundsException ae) {}
        });

    	btnupdate.addActionListener((ActionEvent e) -> {
            if(txtword.getText().length() > 0 && txtdefi.getText().length() > 0){
                String str = txtword.getText();
                String defi = txtdefi.getText();
                for(int i = 0; i < list.getItemCount(); i++){
                    if(str.equalsIgnoreCase(list.getItem(i))){
                        d.hm.remove(list.getItem(i));
                        d.hm.put(str,defi);
                        getKey();
                        JOptionPane.showMessageDialog( frmupdate,"Word update sucessfull!","Update Information", JOptionPane.INFORMATION_MESSAGE);
                        txtdefi.setText("");
                        txtword.setText("");
                        view.setText("");
                        return;
                    }
                }
                JOptionPane.showMessageDialog( frmupdate,"Sorry, you cannot update the world that not exist!","Update Warning", JOptionPane.WARNING_MESSAGE);
                
            }
            else
                JOptionPane.showMessageDialog( frmupdate, "Please enter both word and meaning for dictionary!","Update Error", JOptionPane.ERROR_MESSAGE);
            txtdefi.setText("");
            txtword.setText("");
            view.setText("");
            });
    	frmupdate.pack();
    	frmupdate.show();
    	frmupdate.addWindowListener(new WindowAdapter(){@Override
            public void windowClosing(WindowEvent e){frmupdate.show(false);}});
    	// make frame appear in center
			int w = 340;
			int h = 250;
			// set window position
			Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			frmupdate.setLocation(center.x-w/2, center.y-h/2+25);
    }
}
