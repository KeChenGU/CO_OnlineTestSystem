package com.themi.homework.onlinetest.server;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.themi.homework.onlinetest.server.ui.ServerFrame;
import com.themi.homework.onlinetest.server.ui.ServerLoginPanel;
import com.themi.homework.onlinetest.server.ui.ServerWatchPanel;
import com.themi.homework.onlinetest.server.ui.TestDetailDialogPanel;

public class Test {
	
	public static void main(String[] args) {
//		JFrame frame = new JFrame("Test");
//		ServerLoginPanel loginPanel = new ServerLoginPanel();
//		ServerWatchPanel watchPanel = new ServerWatchPanel("ะกรื");
//		TestDetailDialogPanel dialog = new TestDetailDialogPanel(99, 69.9, 53, new String[] {"123465"},
//				new String[] {"laogutou"},
//				new String[] {"92"});
//		loginPanel.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				super.mouseClicked(e);
//				frame.getContentPane().remove(loginPanel);
//				frame.repaint();
//				frame.getContentPane().add(watchPanel);
//				frame.validate();
//			}
//		});
//		
//		frame.getContentPane().add(loginPanel);
//		Toolkit kit = Toolkit.getDefaultToolkit();
//		Dimension screenSize = kit.getScreenSize();
//		double windowWidth = screenSize.getWidth();
//		double windowHeight = screenSize.getHeight();
//		frame.setBounds((int)(windowWidth / 2 - 500),(int)(windowHeight / 2 - 333) , 1000, 666);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		frame.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				// TODO Auto-generated method stub
//				super.windowClosing(e);
//				System.exit(0);
//			}
//		});
		ServerFrame frame = new ServerFrame();
		
	}
}
