package com.themi.homework.onlinetest.server.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
//import java.util.Iterator;
import java.io.PrintWriter;

//import com.themi.homework.onlinetest.server.serial.PaperInfo;
//import com.themi.homework.onlinetest.server.serial.QuestionInfo;

public class FileUtil {
	
	//public static FileInputStream fis = null;
	
	public static Object readObject(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object object = ois.readObject();
		ois.close();
		return object;
	}
	
	public static void writeObject(String fileName, Object object) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(object);
		oos.close();
	}
	
	public static String readStringData(String fileName) throws IOException{
		FileInputStream fis = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		String line = null;
		String data = "";
		//int i = 0;
		while ((line = br.readLine()) != null) {
			data += line + "\n";
		}
		br.close();
		return data;
	}
	
	public static void writeStringData(String fileName, String data, boolean append) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName, append);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos, "UTF-8"));
		pw.println(data);
		pw.flush();
		pw.close();
	}
	
	public static void writeAllKindData(String fileName, Object o) throws IOException {
		
	}
	
	public static void main(String[] args) {
		try {
//			ParsePaper parPaper = new ParsePaper(); 
//			for (int i = 0; i < 10; ++i) {
//				String questions = FileUtil.readData("papers/" + "paper" + (i + 1) + ".txt");
//				parPaper.parsePapers(questions);
//			}
//			for (int i = 0; i < 10; ++i) {
//				writeObject("papers/object/pobject" + (i + 1) + ".txt"
//						, parPaper.getPaperList().get(i));
//			}
//			for (int i = 0; i < 10; ++i) {
//				PaperInfo info = (PaperInfo)readObject("papers/object/pobject" + (i + 1) + ".txt");
//				System.out.println(info.getName());
//				System.out.println(info.getDesc());
//				Iterator<QuestionInfo> iterator = info.getQueList().iterator();
//				while (iterator.hasNext()) {
//					QuestionInfo qinfo = iterator.next();
//					System.out.println(qinfo.getDesc());
//					System.out.println(qinfo.getA());
//					System.out.println(qinfo.getB());
//					System.out.println(qinfo.getC());
//					System.out.println(qinfo.getD());
//				}
//			}
//			for (int i = 0; i < 10; ++i) {
//				String chose = readStringData("papers/answsers/answer" + (i + 1) + ".txt");
//				String[] answers = chose.split("\n");
//				for (int j = 0; j < answers.length; ++j) {
//					String data = (answers[j].contains("A") ? "1" 
//							: (answers[j].contains("B") ? "2" 
//									: (answers[j].contains("C") ? "3" 
//											: (answers[j].contains("D") ? "4" : "0"))));
//					writeStringData("papers/panswer" + (i + 1) + ".txt", data, true);	
//				}
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}

/*	int a = line.indexOf("A.");
int a1 = line.indexOf("£Á");
int a2 = line.indexOf("A£®");
int b = line.indexOf("B.");
int b1 = line.indexOf("£Â");
int b2 = line.indexOf("B£®");
int c = line.indexOf("C.");
int c1 = line.indexOf("£Ã");
int c2 = line.indexOf("C£®");
int d = line.indexOf("D.");
int d1 = line.indexOf("£Ä");
int d2 = line.indexOf("D£®");
//++i;
if (a >= 0) {
	System.err.println(++i);
	int end = b > a ? b : line.length();
	System.err.println(line.substring(a, end));
}
if (b >= 0) {
	int end = c > b ? c : line.length();
	System.err.println(line.substring(b, end));
}
if (c >= 0) {
	int end = d > c ? d : line.length();
	System.err.println(line.substring(c, end));
}
if (d >= 0) {
	int end = line.length();
	System.err.println(line.substring(d, end));
}
if (a1 >= 0) {
	System.err.println(++i);
	int end = b1 > a1 ? b1 : line.length();
	System.err.println(line.substring(a1, end));
}
if (b1 >= 0) {
	int end = c1 > b1 ? c1 : line.length();
	System.err.println(line.substring(b1, end));
}
if (c1 >= 0) {
	int end = d1 > c1 ? d1 : line.length();
	System.err.println(line.substring(c1, end));
}
if (d1 >= 0) {
	int end = line.length();
	System.err.println(line.substring(d1, end));
}
if (a2 >= 0) {
	System.err.println(++i);
	int end = b2 > a2 ? b2 : line.length();
	System.err.println(line.substring(a2, end));
}
if (b2 >= 0) {
	int end = c2 > b2 ? c2 : line.length();
	System.err.println(line.substring(b2, end));
}
if (c2 >= 0) {
	int end = d2 > c2 ? d2 : line.length();
	System.err.println(line.substring(c2, end));
}
if (d2 >= 0) {
	int end = line.length();
	System.err.println(line.substring(d2, end));
}*/


