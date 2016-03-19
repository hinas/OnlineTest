package com.crossover.OnlineTest.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 436393738498159182L;

	public Question() {
		super();
	}

	public Question(int question_id, String question_txt) {
		super();
		this.question_id = question_id;
		this.question_txt = question_txt;
	}

	private int question_id;

	private String question_txt;

	private Set<Option> options;

	private Set<TestQuestions> testQuestions;

	@Id
	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	@Column(name = "text")
	public String getQuestion_txt() {
		return question_txt;
	}
 
	public void setQuestion_txt(String question_txt) {
		this.question_txt = question_txt;
	}

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL) 
	public Set<Option> getOptions() {
		return options;
	}

	public void setOptions(Set<Option> options) {
		this.options = options;
	}

	@OneToMany(mappedBy = "question",  cascade = CascadeType.ALL)
	public Set<TestQuestions> getTestQuestions() {
		return testQuestions;
	}
 
	public void setTestQuestions(Set<TestQuestions> testQuestions) {
		this.testQuestions = testQuestions;
	}

}
