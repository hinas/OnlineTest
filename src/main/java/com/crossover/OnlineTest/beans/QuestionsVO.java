package com.crossover.OnlineTest.beans;

import java.util.List;

public class QuestionsVO {
	 
	private Integer student_id;
	private Integer test_id;
	private Integer question_id;
	private String testTitle;
	private Integer testDuration;
	private List<Integer> questionNos;

	public List<Integer> getQuestionNos() {
		return questionNos;
	}

	public void setQuestionNos(List<Integer> questionNos) {
		this.questionNos = questionNos;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}

	public Integer getTest_id() {
		return test_id;
	}

	public void setTest_id(Integer test_id) {
		this.test_id = test_id;
	}

	public Integer getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	public Integer getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(Integer testDuration) {
		this.testDuration = testDuration;
	}
}
