package com.themi.homework.onlinetest.server.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.themi.homework.onlinetest.server.serial.PaperInfo;
import com.themi.homework.onlinetest.server.util.FileUtil;

public class ServerFrame extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int PORT = 12306;

	private static final int WIDTH = 1000;
	
	private static final int HEIGHT = 666;
	
	private static final int DEFAULT_PAPER_NUM = 10;
	
	private static final int DEFAUTL_QUES_NUM = 20;
	
	private static final int DEFAULT_MAX_ONLINE = 50;
	
	private static int Port = PORT;
	
	private ServerLoginPanel loginPanel;
	
	private ServerWatchPanel watchPanel;
	
	private TestDetailDialogPanel detailDialog;
	
	private JMenuBar loginBar = null;
	
	private JMenu setMenu = null;
	
	private JMenuItem portItem = null;
	
	private ServerSocket serverSocket = null;
	
	private String[][] answerArray = null;
	
	private List<Socket> clientSocketList = null;
	
	private List<PaperInfo> paperList = null;
	
	private Map<String, Client> clientMap = null;
	
	private Map<String, Boolean> allocPaperMap = null;
	
	private Map<String, Boolean> finishMap = null;
	
	private Map<String, List<Integer>> scoreClientMap = null;
	
	private boolean isStart = false;
	
	private boolean isFinish = false;
	
	private int onlineNum = 0;
	
	private int finishNum = 0;
	
	private int midX = 0;
	
	private int midY = 0;
	
	public ServerFrame() {
		// TODO Auto-generated constructor stub
		clientSocketList = new ArrayList<>();
		clientMap = new HashMap<>();
		allocPaperMap = new HashMap<>();
		//checkConnMap = new HashMap<>();
		finishMap = new HashMap<>();
		scoreClientMap = new HashMap<>();
		this.setIconImage(new ImageIcon("image/harddrive.png").getImage());
		this.setTitle("计算机组成原理简易在线测试系统服务端");
		loginBar = new JMenuBar();
		setMenu = new JMenu("Setting");
		portItem = new JMenuItem("端口配置");
		portItem.addActionListener(this);
		portItem.setFont(new Font("Microsoft YaHei", Font.CENTER_BASELINE, 15));
		setMenu.add(portItem);
		setMenu.setFont(new Font("Microsoft YaHei", Font.CENTER_BASELINE, 18));
		loginBar.add(setMenu);
		loginPanel = new ServerLoginPanel();
		registerLoginModeListener();
		this.setSize(WIDTH, HEIGHT);
		this.add(loginPanel);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		midX = (int)(screenWidth / 2 - WIDTH / 2);
		midY = (int)(screenHeight / 2 - HEIGHT / 2);
		this.setLocation(midX, midY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(loginBar);
		this.setVisible(true);
		initPapers();
		initAnswers();
		//dealClientEvents();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == portItem) {
			String port = JOptionPane.showInputDialog("请输入端口号", "12306");
			if (port != null) {
				if (!port.equals("")) {
					Port = Integer.parseInt(port);
				}
			}
		}
		if (loginPanel == null) {
			return;
		}
		if (source == loginPanel.loginButton) {
			String name = loginPanel.userNameField.getText(); //loginPanel.getUserName();
			String number = new String(loginPanel.passwordField.getPassword()); //loginPanel.getUserNumber();
			if ((name == null || number == null) 
					|| (name.equals("") || number.equals(""))) {
				JOptionPane.showMessageDialog(this, "输入不得为空！", "非法输入", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				watchPanel = new ServerWatchPanel(loginPanel.userNameField.getText());
				registerWatchModeListener();
				Container container = this.getContentPane();
				container.remove(loginPanel);
				container.repaint();
				container.add(watchPanel);
				container.validate();
			}
			dealClientEvents();
		}
		if (source == loginPanel.exitButton) {
			System.exit(0);
		}
		if (watchPanel == null) {
			return;
		}
//		if (source == watchPanel.timeSelecBox) {
//			String text = (String) watchPanel.timeSelecBox.getEditor().getItem();
//			if (!text.contains("分钟") && !text.endsWith("分钟")) {
//				JOptionPane.showMessageDialog(this, "自定义时间必须包含分钟！", "编辑提示", JOptionPane.INFORMATION_MESSAGE);
//			}
//			watchPanel.timeSelecBox.addItem(text);
//			watchPanel.timeSelecBox.setSelectedItem(text);
//		}
		if (source == watchPanel.startButton) {
			if (clientSocketList.size() < 1) {
				JOptionPane.showMessageDialog(this, 
						"至少要1位学生连接才能进行测试！", 
						"提示", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String text = (String) watchPanel.timeSelecBox.getEditor().getItem();
			if (!text.contains("分钟") && !text.endsWith("分钟")) {
				JOptionPane.showMessageDialog(this, "自定义时间必须包含分钟！", "编辑提示", JOptionPane.INFORMATION_MESSAGE);
			}
			watchPanel.timeSelecBox.addItem(text);
			watchPanel.timeSelecBox.setSelectedItem(text);
			watchPanel.configCountTimer();
			watchPanel.startButton.setEnabled(false);
			watchPanel.seeDetailButton.setEnabled(true);
			watchPanel.timeSelecBox.setEnabled(false);
			watchPanel.paperSelecBox.setEnabled(false);
			int index = watchPanel.timeSelecBox.getSelectedIndex();
			watchPanel.listModel.addElement("本场考试开始！考试时间" + watchPanel.timeSelecBox.getItemAt(index));
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			isStart = true;
		}
		if (source == watchPanel.seeDetailButton) {
			//System.out.println("111111111111111111");
			if (!watchPanel.allowTimeChange) {
				int i = 0;
				int max = 0;
				int min = 100;
				double aver = 0;
				int size = clientMap.size();
				String[] num = new String[size];
				String[] name = new String[size];
				String[] score = new String[size];
				for (Entry<String, Client> entry: clientMap.entrySet()) {
					Client client = entry.getValue();
					if (max < client.getScore()) {
						max = client.score;
					}
					if (min > client.getScore()) {
						min = client.score;
					}
					String desc = client.getDesc();
					num[i] = desc.substring(desc.indexOf("准考证号") + 5, desc.indexOf("已"));
					name[i] = desc.substring(desc.indexOf("用户") + 3, desc.indexOf("准考证号") - 1);
					score[i++] = String.valueOf(client.getScore());
					aver += client.score;
				}
				aver /= size;
				detailDialog = new TestDetailDialogPanel(max, aver, min, num, name, score);
//				JScrollPane scroll = new JScrollPane(detailDialog, 
//						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
//						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				JDialog show = new JDialog(ServerFrame.this, "考生测试详情");
				show.setContentPane(detailDialog);
				//show.setLocationRelativeTo(ServerFrame.this);
				///show.pack();
				show.setSize(380, 460);
				show.setLocation(midX + this.getWidth() / 2 - show.getWidth() / 2, 
						midY + this.getHeight() / 2 - show.getHeight() / 2);
				show.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "请先等待考试结束！");
			}
		}
		if (source == watchPanel.exitButton) {
			System.exit(0);
		}
		if (source == watchPanel.clearButton) {
			watchPanel.listModel.clear();
			watchPanel.startButton.setEnabled(true);
			watchPanel.seeDetailButton.setEnabled(false);
			watchPanel.timeSelecBox.setEnabled(true);
			watchPanel.paperSelecBox.setEnabled(true);
			watchPanel.clearButton.setEnabled(false);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			resetState();
		}
	}
	
	private void dealClientEvents() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (!initSocket()) {
					JOptionPane.showMessageDialog(ServerFrame.this, 
							"绑定本地端口号出错！请更换其他端口号！", 
							"绑定错误", 
							JOptionPane.ERROR_MESSAGE);
					//return;
					System.exit(1);
				}
				receiveSocket();
			}
		}).start();
		
		
	}
	
	private boolean initSocket() {
		try {
			serverSocket = new ServerSocket(Port);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("监听本地端口号失败:" + e.getCause() + ": " + e.getMessage());
			return false;
		}
		return true;
	}
	
	private void receiveSocket() {
		//clientSocketList = new ArrayList<>();
		Socket clientSocket = null;
		//int stuNum = clientSocketList.size();
		while (clientSocketList.size() <= 50) {
			int stuNum = clientSocketList.size();
			try {
				clientSocket = serverSocket.accept();
				clientSocket.setSoTimeout(3000);
			} catch (IOException e) {
				// TODO: handle exception
				System.err.println("receiveSocket:" + stuNum + " " + e.getCause() + ": " + e.getMessage());
				e.printStackTrace();
			}
			if (clientSocket != null) { 
				System.out.println(stuNum);
				watchPanel.stuNum = onlineNum = stuNum;
				watchPanel.numLabel.setText("当前在线人数：" + stuNum);
				ClientDealer dealer = new ClientDealer(clientSocket, watchPanel.listModel);
				dealer.go();
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		serverSocket.close();
		super.finalize();
	}
	
	private void registerLoginModeListener() {
		loginPanel.loginButton.addActionListener(this);
		loginPanel.exitButton.addActionListener(this);
		//watchPanel.seeDetailButton.addActionListener(this);
	}
	
	private void registerWatchModeListener() {
		watchPanel.startButton.addActionListener(this);
		watchPanel.seeDetailButton.addActionListener(this);
		watchPanel.exitButton.addActionListener(this);
		watchPanel.clearButton.addActionListener(this);
	}
	
	private void initPapers() {
		paperList = new ArrayList<>();
		try {
			for (int i = 0 ; i < DEFAULT_PAPER_NUM; ++i) {
				PaperInfo paperInfo = 
						(PaperInfo)FileUtil.readObject("papers/object/pobject" + (i + 1) + ".txt");
				paperList.add(paperInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("initPapers:" + e.getCause() + ":" + e.getMessage());
			return;
		}
		System.out.println("初始化试卷成功！");
	}
	
	private void initAnswers() {
		answerArray = new String[DEFAULT_PAPER_NUM + 10][DEFAUTL_QUES_NUM + 10];
		try {
			for (int i = 0; i < DEFAULT_PAPER_NUM; ++i) {
				String queString = FileUtil.readStringData("papers/panswer" + (i + 1) + ".txt");
				String[] ques = queString.split("\n");
				for (int j = 0; j < DEFAUTL_QUES_NUM; ++j) {
					System.out.println("Paper" + (i + 1) + "Answer" + (j + 1) +  ":" + ques[j]);
					answerArray[i][j] = ques[j];
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("initAnswers:" + e.getCause() + " : " + e.getMessage());
			return;
		}
		System.out.println("初始化试卷答案成功！");
	}
	
	private void resetState() {
		
		if (clientSocketList.size() == 50) {
			clientSocketList.clear();
			dealClientEvents();
		}
		
		clientSocketList.clear();
		
		paperList.clear();
		
		clientMap.clear();
		
		allocPaperMap.clear();

		finishMap.clear();
		
		scoreClientMap.clear();
		
		isStart = false;
		
		isFinish = false;
		
		onlineNum = 0;
		
		finishNum = 0;
	}
	
	class Client {
		String desc;
		
		int score;

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}
		
		
	}
	
	class ClientDealer {
		
		Socket socket = null;
		
		BufferedReader bufReader = null;
		
		PrintWriter prtWriter = null;
		
		String recvMsg = null;
		
		DefaultListModel<String> msgList;
		
		
		ClientDealer(Socket socket, DefaultListModel<String> msgList) {
			// TODO Auto-generated constructor stub
			this.socket = socket;
			this.msgList = msgList;
		}
		
		void go() /*throws InterruptedException*/ {
			if (!initIOs()) {
				JOptionPane.showMessageDialog(ServerFrame.this, "初始化客户端读写流出错！", 
						"错误", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			new Thread(new Dealer()).start();
		}
		
		boolean initIOs() {
			try {
				bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				prtWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			} catch (IOException e) {
				System.err.println("initialIO: " + e.getCause() + ": " + e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		class Dealer implements Runnable {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					synchronized (this) {
						String line = null;
						StringBuilder builder = new StringBuilder();
						while ((line = bufReader.readLine()) != null) {
							builder.append(line).append('\n');
						}
						recvMsg = builder.toString();
						System.out.println("Recv:" + recvMsg);
						if (recvMsg == null) {
							return;
						}
						if (recvMsg.contains("准备就绪")) {
							if (isStart) {
								//System.out.println("11111111111111!");
								prtWriter.println("IsTesting...");
								prtWriter.flush();
								close();
								return;
							}
							String keyValue = recvMsg;
							recvMsg = recvMsg.substring(0, recvMsg.indexOf("ID:"));
							clientSocketList.add(socket);
							msgList.addElement(recvMsg);
							System.out.println(recvMsg);
							String key = keyValue.substring(keyValue.indexOf("ID:") + 3, keyValue.length() - 1);
							String value = keyValue.substring(0, keyValue.indexOf("ID:"));
							System.out.println(key + ":" + value);
							Client client = new Client();
							client.setDesc(value);
							clientMap.put(key, client);
							allocPaperMap.put(key, new Boolean(false));
							finishMap.put(key, false);
						} else if (recvMsg.contains("Leave:") 
								|| recvMsg.contains("SelfFinish:") 
								|| recvMsg.contains("Over:")) {
							String key = recvMsg.substring(recvMsg.indexOf(":") + 1, recvMsg.length() - 1);
							
							if (clientMap.containsKey(key)) {
								String value = clientMap.get(key).getDesc();
								value = value.substring(0, value.lastIndexOf("已"));
								String addValue = (recvMsg.contains("Leave:") ? " 断开了连接！ 无法在服务器测试！" :
									(recvMsg.contains("SelfFinish:") ? "已完成测试，提前离开！" : 
										(recvMsg.contains("Over:") ? "已回收交卷！退出客户端！" : "")));
								msgList.addElement(value + addValue);
								clientSocketList.remove(0);
								//clientMap.remove(key);
							}
							watchPanel.stuNum = onlineNum =  clientSocketList.size();
							watchPanel.numLabel.setText("当前在线人数：" + onlineNum);
							if (onlineNum == 0) {
								if (isStart) {
									isStart = false;	
								} 
								watchPanel.allowTimeChange = false;
								watchPanel.isFinish = true;
								watchPanel.terminateCountTimer();
								watchPanel.countLabel.setText("距离本场考试还剩下：");
								watchPanel.clearButton.setEnabled(true);
								watchPanel.numLabel.setText("当前在线人数：" + watchPanel.stuNum);
								String addValue = (recvMsg.contains("Leave:") ? "当前考试人数不足1人，考试异常终止！" :
									(recvMsg.contains("SelfFinish:") ? "所有人都已完成考试，本场考试结束！" : "" 
										/*(recvMsg.contains("Over:") ? "回收试卷完成，本场考试结束！" : "")*/));
								watchPanel.listModel.addElement(addValue + " 点击清空按钮重置...");
								if (recvMsg.contains("Leave:")) {
									System.err.println("考试异常终止！");
								} else {
									System.out.println("考试结束！");
									watchPanel.seeDetailButton.setEnabled(true);
								}
								
							}
						} else if (recvMsg.contains("Submit:")) {
							if (isStart) {
								String key = recvMsg.substring(0, recvMsg.indexOf("Submit"));
								String[] submission = recvMsg.split("\n");
								String paperNo = submission[0].substring(
										submission[0].indexOf(":") + 1, submission[0].length());
								System.out.println("paperNo:" + paperNo + "\n" + key);
								List<Integer> integers = new ArrayList<>();
								int sum = 0;
								for (int i = 1; i <= DEFAUTL_QUES_NUM; ++i) {
									if (answerArray[Integer.parseInt(paperNo) - 1][i - 1].equals(submission[i])) {
										System.out.println("right!");
										sum += 5;
										integers.add(5);
									} else {
										System.out.println("wrong!");
										integers.add(0);
									}
								}
								scoreClientMap.put(key, integers);
								clientMap.get(key).setScore(sum);
								String value = clientMap.get(key).getDesc();
								value = value.substring(0, value.indexOf("准"));
								msgList.addElement(value + "已经交卷！");
								++finishNum;
								if (finishNum == clientSocketList.size()) {
									isStart = false;
									isFinish = true;
								}
							}
						} else {
							//System.out.println("HeartBeating...");
							//System.out.println(recvMsg);
							//String key = recvMsg.substring(recvMsg.indexOf(".") - 1, recvMsg.length() - 1);
						}
						if (isStart) {
							prtWriter.write("testing...\r\n");
							int begin = recvMsg.indexOf(".") - 1;
							int end = recvMsg.length() - 1;
							if (begin <= 0 || end <= 0) {
								return; 
							}
							String key = recvMsg.substring(begin, end);
							if (allocPaperMap.containsKey(key)) {
								if (!allocPaperMap.get(key).booleanValue()) {
									String timeKind = (String) watchPanel.timeSelecBox.getSelectedItem();
									prtWriter.write(timeKind + "Paper:");
									String paperKind = (String) watchPanel.paperSelecBox.getSelectedItem();
									System.out.println("试卷种类：" + paperKind);
									int i = 0;
									if (paperKind.equals("全部")) {
										i = new Random().nextInt(10) + 1;
										System.out.println("选择了卷" + i);
									} else if (paperKind.equals("卷1-卷5")) {
										i = new Random().nextInt(5) + 1;
									} else if (paperKind.equals("卷6-卷10")) {
										i = new Random().nextInt(5) + 6;
									}
									prtWriter.println(i);
									allocPaperMap.put(key, new Boolean(true));
								}
							}
							
						} else {
							prtWriter.print("Hello Client!\r");
							prtWriter.write("\r\n");
							
						}
						int begin = recvMsg.indexOf(".") - 1; int end = recvMsg.length() - 1;
						if (begin < 0 || end < 0) {
							return;
						}
						String key = recvMsg.substring(recvMsg.indexOf(".") - 1, recvMsg.length() - 1);
						if (isFinish) {
							//接收答题数据，评完分再返还给客户端
							//prtWriter.println("test finished...");
							//String key = recvMsg.substring(recvMsg.indexOf(".") - 1, recvMsg.length() - 1);
							if (clientMap.containsKey(key) && scoreClientMap.containsKey(key)) {
								if (finishMap.containsKey(key)) {
									if (!finishMap.get(key).booleanValue()) {
										List<Integer> integers = scoreClientMap.get(key);
										Iterator<Integer> iterator = integers.iterator();
										prtWriter.write("Checked:;");
										System.out.println("Checked:");
										while (iterator.hasNext()) {
											Integer integer = iterator.next();
											prtWriter.write(integer + ";");
											System.out.println(integer);
										}
										finishMap.put(key, true);
									}
								}
							}
						} else if (clientMap.containsKey(key) && scoreClientMap.containsKey(key)) {
							if (finishMap.containsKey(key)) {
								if (!finishMap.get(key).booleanValue()) {
									List<Integer> integers = scoreClientMap.get(key);
									Iterator<Integer> iterator = integers.iterator();
									prtWriter.write("Checked:;");
									System.out.println("Checked:");
									while (iterator.hasNext()) {
										Integer integer = iterator.next();
										prtWriter.write(integer + ";");
										System.out.println(integer);
									}
									finishMap.put(key, true);
								}
							}
						}
						prtWriter.flush();
						close();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.err.println("Dealer: " + e.getCause() + " : " + e.getMessage());

				}
			}
		}
		
		void close() throws IOException {
			socket.close();
			bufReader.close();
			prtWriter.close();
		}
		

	}
	
	public static void main(String[] args) {
		ServerFrame frame = new ServerFrame();
	}
}

