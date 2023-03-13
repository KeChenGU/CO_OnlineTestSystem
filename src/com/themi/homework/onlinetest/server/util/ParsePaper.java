package com.themi.homework.onlinetest.server.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.themi.homework.onlinetest.server.serial.PaperInfo;
import com.themi.homework.onlinetest.server.serial.QuestionInfo;

public class ParsePaper {

	private List<QuestionInfo> quesList = new ArrayList<>();
	
	private List<PaperInfo> paperList = new ArrayList<>();
	
	public static final String[][] PARSE_CHARS = {
			{"A.", "Ａ.", "A．"}, 
			{"B.", "Ｂ.", "B．"}, 
			{"C.", "Ｃ.", "C．"}, 
			{"D.", "Ｄ.", "D．"}
	};
	
	public void parsePapers(String papers) {
		PaperInfo paper = new PaperInfo();
		paper.setName("卷" + (paperList.size() + 1));
		paper.setDesc("选择题，每题5分，总分100分");
		parseQuestions(papers);
		paper.setQueList(quesList);
		paperList.add(paper);
		quesList = new ArrayList<>();
	}
	
	
	public List<QuestionInfo> getQuesList() {
		return quesList;
	}


	public void setQuesList(List<QuestionInfo> quesList) {
		this.quesList = quesList;
	}


	public List<PaperInfo> getPaperList() {
		return paperList;
	}


	public void setPaperList(List<PaperInfo> paperList) {
		this.paperList = paperList;
	}


	private void parseQuestions(String questions) {
		String[] ques = questions.split("\n");
		StringBuilder builder = new StringBuilder();
		QuestionInfo question = new QuestionInfo();
		for (int i = 0; i < ques.length; ++i) {
			//int n = 0;
			int flag = 0;
			String line = ques[i];
			int kind = PARSE_CHARS[0].length;
			for (int j = 0; j < kind; ++j) {
				//int kind = PARSE_CHARS[i].length;
				int a = line.indexOf(PARSE_CHARS[0][j]);
				int b = line.indexOf(PARSE_CHARS[1][j]);
				int c = line.indexOf(PARSE_CHARS[2][j]);
				int d = line.indexOf(PARSE_CHARS[3][j]);
				if (a >= 0) {
					//question.setDesc(builder.toString());
					int end = b > a ? b : line.length();
					question.setA(line.substring(a, end));
					//builder = new StringBuilder();
					//System.err.println("AAAAA");
					flag = 1;
				}
				if (b >= 0) {
					int end = c > b ? c : line.length();
					question.setB(line.substring(b, end));
					//System.err.println("BBBBB");
					flag = 1;
				}
				if (c >= 0) {
					int end = d > c ? d : line.length();
					question.setC(line.substring(c, end));
					//System.err.println("CCCCCC");
					flag = 1;
				}
				if (d >= 0) {
					int end = line.length();
					question.setD(line.substring(d, end));
					//quesList.add(question);
					//question = new QuestionInfo();
					//System.err.println("DDDDDD");
					flag = 1;
				}
			}
			if (flag == 0) {
				builder.append(line);
			} else if (question.getD() != null) {
				question.setDesc(builder.toString());
				quesList.add(question);
				question = new QuestionInfo();
				builder = new StringBuilder();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			ParsePaper parPaper = new ParsePaper(); 
			for (int i = 0; i < 10; ++i) {
				String questions = FileUtil.readStringData("papers/" + "paper" + (i + 1) + ".txt");
				parPaper.parsePapers(questions);
			}
			Iterator<PaperInfo> iterator = parPaper.paperList.iterator();
			//System.out.println(parPaper.paperList.size());
			while (iterator.hasNext()) {
				PaperInfo info = iterator.next();
				System.out.println(info.getName());
				System.out.println(info.getDesc());
				Iterator<QuestionInfo> iterator2 = info.getQueList().iterator();
				//System.out.println(info.getQueList());
				System.out.println(info.getQueList().size());
				while (iterator2.hasNext()) {
					QuestionInfo info2 = iterator2.next();
					System.out.println(info2.getDesc() + info2.getA() + info2.getB() + info2.getC() + info2.getD());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
