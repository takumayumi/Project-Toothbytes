/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui;

/**
 *
 * @author jheraldinebugtong
 */
/**
 * @(#)About.java
 *
 *
 * @author jheraldinetbugtong
 * @version 1.00 2015/2/26
 */


import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;
import toothbytes.ui.toolbars.TBMenuBar;

public class About{

	public JFrame frame = new JFrame();

	public About(){

		ImageIcon icon = new ImageIcon("dentist.png");
		JLabel label = new JLabel(icon),name;

		Container contentPane = frame.getContentPane();

		contentPane.add(label,BorderLayout.CENTER);
		contentPane.add(name = new JLabel("Toothbytes",JLabel.CENTER),BorderLayout.SOUTH);
		contentPane.add(new JLabel(" "),BorderLayout.NORTH);
		contentPane.add(new JLabel("  "),BorderLayout.WEST);
		contentPane.add(new JLabel("  "),BorderLayout.EAST);

		Font f = new Font("Arial",Font.BOLD,18);
		name.setFont(f);
		name.setForeground(Color.YELLOW);
		contentPane.setBackground(Color.RED);

		frame.pack();

		int w = 500;
		int h = 500;
		frame.setSize(w,h);

		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		frame.setLocation(center.x-w/2, center.y-h/2+25);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(false);

	}
}
