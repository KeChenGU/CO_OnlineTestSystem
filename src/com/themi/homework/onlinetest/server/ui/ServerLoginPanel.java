package com.themi.homework.onlinetest.server.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//import com.themi.homework.onlinetest.client.ui.LoginFormProvider;

public class ServerLoginPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ImageIcon backGroundImage;
	
	JLabel titleLabel;
	
	JLabel nameLabel;
	
	JTextField userNameField;
	
	JLabel passwordLabel;
	
	JPasswordField passwordField;
	
	JButton loginButton;
	
	JButton exitButton;
	
	public ServerLoginPanel () {
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		initControls();
		LoginFormProvider provider = new LoginFormProvider();
		provider.createLoginForm(new JLabel[] {nameLabel, passwordLabel}, 
				new JTextField[] {userNameField, passwordField}, 
				new JButton[] {loginButton, exitButton});
		this.add(titleLabel, BorderLayout.NORTH);
		//this.add(backLable);
		this.add(provider.loginFormPanel);
		this.setSize(1000, 666);
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		int panelHeight = this.getHeight();
		int panelWidth = this.getWidth();
		int height = 0;
		for (int i = 0; height < panelHeight; ++i) {
			height = backGroundImage.getIconHeight() * i;
			g.drawImage(backGroundImage.getImage(), 100, height, this);
			g.drawImage(backGroundImage.getImage(), panelWidth - 200, height, this);
		}
	}
	private void initControls() {
		
		titleLabel = new JLabel("计算机组成原理选择题在线测试服务端");
		titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
		titleLabel.setForeground(Color.RED);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		
		backGroundImage = new ImageIcon("image/harddrive.png");
		
		nameLabel = new JLabel("用户名： ");
		nameLabel.setFont(new Font("Microsoft YaHei", Font.CENTER_BASELINE, 15));
		userNameField = new JTextField(20);
		
		passwordLabel = new JLabel("密码：");
		passwordLabel.setFont(new Font("Microsoft YaHei", Font.CENTER_BASELINE, 15));
		
		passwordField = new JPasswordField(20);//(JPasswordField) new JTextField(20);
		
		loginButton = new JButton("登录");
		loginButton.setFont(new Font("Microsoft YaHei", Font.CENTER_BASELINE, 15));
		
		exitButton = new JButton("退出");
		exitButton.setFont(new Font("Microsoft YaHei", Font.CENTER_BASELINE, 15));
	}


}

class LoginFormProvider {
	
	//JPanel inputPanel;
	
	JPanel loginFormPanel;
	
	//JPanel buttonPanel;
	
	int parentHeight;
	
	LoginFormProvider() {
		loginFormPanel = new JPanel();
		loginFormPanel.setBorder(BorderFactory.createEtchedBorder());
	}
	
	void createLoginForm(JLabel[] labels, 
			JTextField[] fields, 
			JButton[] buttons) {
		int labelLen = labels.length;
		int fieldLen = fields.length;
		if (labelLen != fieldLen) {
			return;// null;
		}
		//loginFormPanel.add(Box.createVerticalStrut(66));
		//for (int i = 0; i < 100; ++i) {
		//	loginFormPanel.add(new JLabel());
		//}
		Box bigBox = Box.createVerticalBox();
		bigBox.add(Box.createVerticalStrut(180));
		Box formBox = Box.createVerticalBox();
		formBox.setBorder(BorderFactory.createEtchedBorder());
		formBox.add(Box.createVerticalStrut(25));
		for (int i = 0; i < labelLen; ++i) {
			Box formItemBox = Box.createHorizontalBox();
			formItemBox.add(Box.createHorizontalStrut(25));
			formItemBox.add(labels[i]);
			formItemBox.add(fields[i]);
			formItemBox.add(Box.createHorizontalStrut(25));
			formBox.add(formItemBox);
			formBox.add(Box.createVerticalStrut(25));
		}
		Box buttonBox = Box.createHorizontalBox();
		for (int i = 0; i < buttons.length; ++i) {
			//buttonBox.add(Box.createHorizontalStrut(25));
			buttonBox.add(buttons[i]);
			if (i < buttons.length - 1)
				buttonBox.add(Box.createHorizontalStrut(25));
			
		}
		formBox.add(buttonBox);
		formBox.add(Box.createVerticalStrut(25));
//		formBox.setBounds(loginFormPanel.getWidth(), 
//				loginFormPanel.getHeight(), 300, 200);
		//formBox.setSize(500, 500);
		//formBox.setSize(200, 150);
		bigBox.add(formBox);
		//loginFormPanel.add(formBox);
		loginFormPanel.add(bigBox);
	}
}