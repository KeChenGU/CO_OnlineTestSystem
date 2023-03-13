package com.themi.homework.onlinetest.server.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class TestDetailDialogPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel titlePanel;
	
	JLabel maxLabel;
	
	JLabel averLabel;
	
	JLabel minLabel;
	
	Box stuBox;
	
	JLabel stuNumLabel;
	
	JLabel stuNameLabel;
	
	JLabel stuScoreLabel;
	
	Font titleFont = new Font("Microsoft YaHei", Font.CENTER_BASELINE, 18);
	
	Font tableFont = new Font("Microsoft YaHei", Font.CENTER_BASELINE, 15);
	
	Border etchedBlack = BorderFactory.createLineBorder(Color.BLACK);
	
	public TestDetailDialogPanel(int max, 
			double aver, 
			int min,
			String[] num,
			String[] name,
			String[] score) {
		// TODO Auto-generated constructor stub
		
		//this.setTitle("考生测试详情");
		//this.setIconImage(new ImageIcon("image/highlight.png").getImage());
		//this.setLayout(new GridLayout(0, 1));
		//this.setLayout(new BorderLayout());
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		titlePanel = new JPanel();
		maxLabel = new JLabel("最高分：" + String.valueOf(max));
		maxLabel.setFont(titleFont);
		//maxLabel.setBorder(etchedBlack);
		averLabel = new JLabel("平均分：" + String.valueOf(aver));
		averLabel.setFont(titleFont);
		//averLabel.setBorder(etchedBlack);
		minLabel = new JLabel("最低分：" + String.valueOf(min));
		minLabel.setFont(titleFont);
		//minLabel.setBorder(etchedBlack);
		titlePanel.add(maxLabel);
		titlePanel.add(averLabel);
		titlePanel.add(minLabel);
		titlePanel.setBorder(etchedBlack);
		//this.add(titlePanel, BorderLayout.NORTH);
		//this.add(titlePanel);
		
		stuBox = Box.createVerticalBox();
		//stuBox.setSize(250, height);
		//stuBox.setLayout(new GridLayout(0, 1));
		//stuBox.setFont(tableFont);
		JPanel temptPanel = new JPanel();
		temptPanel.setLayout(new GridLayout(1, 3));
		stuNumLabel = new JLabel("准考证号");
		stuNumLabel.setBorder(etchedBlack);
		stuNumLabel.setFont(tableFont);
		stuNumLabel.setHorizontalAlignment(JLabel.CENTER);
		stuNameLabel = new JLabel("用户名");
		stuNameLabel.setBorder(etchedBlack);
		stuNameLabel.setFont(tableFont);
		stuNameLabel.setHorizontalAlignment(JLabel.CENTER);
		stuScoreLabel = new JLabel("得分");
		stuScoreLabel.setBorder(etchedBlack);
		stuScoreLabel.setFont(tableFont);
		stuScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		temptPanel.add(stuNumLabel);
		temptPanel.add(stuNameLabel);
		temptPanel.add(stuScoreLabel);
		stuBox.add(temptPanel);
		int length = num.length;
		for (int i = 0; i < length; ++i) {
			JPanel tempPanel = new JPanel();
			tempPanel.setLayout(new GridLayout(1, 3));
			JLabel numLabel = new JLabel(num[i]);
			numLabel.setHorizontalAlignment(JLabel.CENTER);
			numLabel.setBorder(etchedBlack);
			numLabel.setFont(tableFont);
			tempPanel.add(numLabel);
			JLabel nameLabel = new JLabel(name[i]);
			nameLabel.setBorder(etchedBlack);
			nameLabel.setHorizontalAlignment(JLabel.CENTER);
			nameLabel.setFont(tableFont);
			tempPanel.add(nameLabel);
			JLabel scoreLabel = new JLabel(score[i]);
			scoreLabel.setHorizontalAlignment(JLabel.CENTER);
			scoreLabel.setBorder(etchedBlack);
			scoreLabel.setFont(tableFont);
			tempPanel.add(scoreLabel);
			stuBox.add(tempPanel);
		}
		//this.add(stuBox, BorderLayout.CENTER);
		//this.add(stuBox);
		//this.pack();
		//this.setSize(300, 500);
		this.setLayout(new BorderLayout());
		this.add(titlePanel, BorderLayout.NORTH);
		JPanel finalPanel = new JPanel();
		JPanel finalBufPanel = new JPanel();
		finalBufPanel.setLayout(new GridLayout(1, 1));
		//finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
		//finalPanel.setLayout(new GridLayout(0, 1));
		//finalPanel.add(titlePanel);
		finalBufPanel.add(stuBox);
		finalPanel.add(finalBufPanel);
		//finalPanel.add(createButtonBox());
		JScrollPane scroll = new JScrollPane(finalPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}
	
	
}
