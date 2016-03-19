package com.crossover.OnlineTest.beans;

import java.util.Set;

import com.crossover.OnlineTest.entity.Option;

public class QuestionDetailsVO {
	
	private int question_id;
	private Set<Option> options;
	private String question_txt;
	private int optionChoosed;
	
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public Set<Option> getOptions() {
		return options;
	}
	public void setOptions(Set<Option> options) {
		this.options = options;
	}
	public String getQuestion_txt() {
		return question_txt;
	}
	public void setQuestion_txt(String question_txt) {
		this.question_txt = question_txt;
	}
	public int getOptionChoosed() {
		return optionChoosed;
	}
	public void setOptionChoosed(int optionChoosed) {
		this.optionChoosed = optionChoosed;
	}
	
	
	

}
