package com.crossover.OnlineTest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "options")
public class Option implements Serializable{

	public Option() {
		super();
	}

	public Option(int id, int option_no, String option_txt, char is_answer, Question question) {
		super(); 
		this.id = id;
		this.option_no = option_no;
		this.option_txt = option_txt;
		this.is_answer = is_answer;
		this.question = question;
	}
	
	private int id;
	
	private int option_no;
	private String option_txt;
	private char is_answer;

	private Question question;

	public int getOption_no() {
		return option_no;
	}

	public void setOption_no(int option_no) {
		this.option_no = option_no;
	}

	public String getOption_txt() {
		return option_txt;
	}

	public void setOption_txt(String option_txt) {
		this.option_txt = option_txt;
	}

	public char getIs_answer() {
		return is_answer;
	}

	public void setIs_answer(char is_answer) {
		this.is_answer = is_answer;
	}

	@ManyToOne
	@JoinColumn(name = "question_id")
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true; 
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option other = (Option) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
	

}
