package com.crossover.OnlineTest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="test_question")
public class TestQuestions implements Serializable{
	
	public TestQuestions()
	{
		super();
	}
	
	public TestQuestions(int id, TestDetail testDetail, Question question) {
		super();
		this.id = id;
		this.testDetail = testDetail;
		this.question = question;
	}

	private int id;
	
	private TestDetail testDetail;
	private Question question;

	@ManyToOne
	@JoinColumn(name="test_id")
	public TestDetail getTestDetail() {
		return testDetail;
	}

	public void setTestDetail(TestDetail testDetail) {
		this.testDetail = testDetail;
	}

	@ManyToOne
	@JoinColumn(name="question_id")
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) { 
		this.id = id;
	}
	
	 
	
	

}
