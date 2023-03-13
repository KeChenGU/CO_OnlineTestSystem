package com.themi.homework.onlinetest.server.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class ServerWatchPanel extends JPanel{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 66666666666666666L;
	
	private static final String[] defaultTimeSelection = {"10����", "20����", 
			"30����", "40����",
			"50����", "60����"};
	
	private static final String[] defaultPaperSelection = {"ȫ��", "��1-��5", "��6-��10"};
	
	JLabel titleLabel;
	
	JLabel nameLabel;
	
	JLabel dateLabel;
	
	JLabel countLabel;
	
	JLabel numLabel;
	
	JButton clearButton;
	
	JScrollPane listScroPane;
	
	DefaultListModel<String> listModel;
	
	JList<String> connectedStuds;
	
	JComboBox<String> timeSelecBox;
	
	JComboBox<String> paperSelecBox;
	
	JButton startButton;
	
	JButton exitButton;
	
	JButton seeDetailButton;
	
	Font commonFont = new Font("Microsoft YaHei", Font.CENTER_BASELINE, 15);
	
	//Timer dateTimer;
	
	Timer countTimer;
	
	int stuNum = 0;
	
	boolean allowTimeChange = true;
	
	boolean isFinish = false;
	
	public ServerWatchPanel(String name) {
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		Border etched = BorderFactory.createEtchedBorder();
		titleLabel = new JLabel("��������ԭ��������߲���ϵͳ");
		titleLabel.setFont(new Font("Microsoft YaHei", Font.CENTER_BASELINE, 20));
		titleLabel.setForeground(Color.RED);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setBorder(etched);
		
		nameLabel = new JLabel("�û�" + name + "�� ����");
		nameLabel.setBorder(etched);
		nameLabel.setFont(commonFont);
		
		dateLabel = new JLabel(new SimpleDateFormat("yyyy��MM��dd��hhʱmm�� EE"
				, Locale.CHINA).format(new Date()));
		dateLabel.setBorder(etched);
		dateLabel.setFont(commonFont);
		//configDateTimer();

		countLabel = new JLabel("���뱾�����Խ�����ʣ��");
		countLabel.setBorder(etched);
		countLabel.setFont(commonFont);
		//configCountTimer();
		
		numLabel = new JLabel("��ǰ��������:" + stuNum);
		numLabel.setBorder(etched);
		numLabel.setFont(commonFont);
		
		timeSelecBox = new JComboBox<>();
		//int tlen = defaultTimeSelection.length;
		for (String ts: defaultTimeSelection) {
			timeSelecBox.addItem(ts);
		}
		timeSelecBox.setSelectedIndex(1);
		timeSelecBox.setFont(commonFont);
		timeSelecBox.setEditable(true);
		
		paperSelecBox = new JComboBox<>();
		for (String ps: defaultPaperSelection) {
			paperSelecBox.addItem(ps);
		}
		paperSelecBox.setSelectedIndex(0);
		paperSelecBox.setFont(commonFont);
		//paperSelecBox.setEditable(true);
		
		listModel = new DefaultListModel<>();
		connectedStuds = new JList<>(listModel);
		connectedStuds.setFont(commonFont);
		
		listScroPane = new JScrollPane(connectedStuds, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		startButton = new JButton("��ʼ����");
		startButton.setFont(commonFont);
		
		exitButton = new JButton("�˳�");
		exitButton.setFont(commonFont);
		
		seeDetailButton = new JButton("�鿴��������");
		seeDetailButton.setEnabled(false);
		seeDetailButton.setFont(commonFont);
		
		clearButton = new JButton("�����Ϣ�б�");
		clearButton.setBorder(etched);
		clearButton.setFont(commonFont);
		clearButton.setEnabled(false);
		
		this.add(createTitlePanel(), BorderLayout.NORTH);
		this.add(createContentPanel(), BorderLayout.CENTER);
		this.add(exitButton, BorderLayout.SOUTH);
		
		configDateTimer();
		
		//configCountTimer();
		//changeTime();
	}
	
	public void addStudent(String student) {
		listModel.addElement(student);
		++stuNum;
		numLabel.setText("��ǰ��������:" + stuNum);
		
	}
	
//	boolean isTimeOut() {
//		return allowTimeChange; 
//	}
	
	void configDateTimer() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new DateTimerTask(), 0, 60 * 1000);
		allowTimeChange = true;
	}
	
	void configCountTimer() {	
		//Timer timer = new Timer();
		//timer.scheduleAtFixedRate(new WatchTimerTask(), 0, 60000);
		countTimer = new Timer();
		countTimer.scheduleAtFixedRate(new WatchTimerTask(), 0, 1000);
	}
	
	void terminateCountTimer() {
		countTimer.cancel();
	}
	
	void reset() {
		allowTimeChange = true;
		isFinish = false;
	}
	
	void dealCountTimerAuto() {
		if (!allowTimeChange) {
			isFinish = true;
			countTimer.cancel();
			countLabel.setText("���뱾�����Խ������У�");
			clearButton.setEnabled(true);
			listModel.addElement("���Խ���...\n�����հ�ť����");
			JOptionPane.showMessageDialog(this, "�������Խ�����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	protected class DateTimerTask extends TimerTask {
		@Override
		public void run() {
			// TODO Auto-generated method stub\
			dateLabel.setText(new SimpleDateFormat("yyyy��MM��dd��hhʱmm�� EE"
					, Locale.CHINA).format(new Date()));
		}
	}
	
	protected class WatchTimerTask extends TimerTask {
		String numString = (String)timeSelecBox.getSelectedItem();
		String[] nums =  numString.split("[^0-9]");
		int num = Integer.parseInt(nums[0]);
		int snum = num * 60;//nums.length > 1 ? Integer.parseInt(nums[1]) : 0;
		boolean overflag = false;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int sec = snum % 60;
			num = snum / 60;
			String mzero = num > 9 ? "" : "0";
			String szero = snum > 9 ? "" : "0";
			countLabel.setText("�౾�����Խ�����ʣ�� " + mzero + num + "����" + szero + sec + "��");
			--snum;
			allowTimeChange = num > 0 || snum > 0;
			dealCountTimerAuto();
		}
	}
	
//	private void changeTime() {
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while (allowTimeChange) {
//					dateLabel.setText(new SimpleDateFormat("yyyy��MM��dd��hhʱmm��ss�� EE"
//				, Locale.CHINA).format(new Date()));
//					//dateLabel.validate();
//				}
//				try {
//					Thread.sleep(1000);
//				} catch(InterruptedException exp) {
//					exp.printStackTrace();
//				}
//			}
//		}).start();
//	}
	
	private JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		//JPanel leftPart = new JPanel();
		//JPanel rightPart = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(nameLabel, BorderLayout.WEST);
		titlePanel.add(titleLabel, BorderLayout.CENTER);
		titlePanel.add(dateLabel, BorderLayout.EAST);
		return titlePanel;
	}
	
	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		JPanel leftPart = new JPanel();
		JPanel midPart = new JPanel();
		JPanel rightPart = new JPanel();
		leftPart.setLayout(new GridLayout(0, 1));
		JLabel time = new JLabel("����ʱ�䣺");
		time.setFont(commonFont);
		leftPart.add(time);
		leftPart.add(timeSelecBox);
		JLabel paper = new JLabel("�Ծ����ࣺ"); 
		paper.setFont(commonFont);
		leftPart.add(paper);
		leftPart.add(paperSelecBox);
		leftPart.add(numLabel);
		leftPart.add(clearButton);
		midPart.setLayout(new BorderLayout());
		midPart.add(listScroPane);
		rightPart.setLayout(new GridLayout(3, 1));
		rightPart.add(startButton);
		rightPart.add(seeDetailButton);
		rightPart.add(countLabel);
		contentPanel.add(leftPart, BorderLayout.WEST);
		contentPanel.add(midPart, BorderLayout.CENTER);
		contentPanel.add(rightPart, BorderLayout.EAST);
		return contentPanel;
	}
	
//	private JPanel createButtonBox() {
//		
//		return null;
//	}
	
//	private JPanel createWatchPanel() {
//		JPanel watchPanel = new JPanel();
//		
//		return watchPanel;
//	}
	

}
