package com.crossover.OnlineTest.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="test_history")
public class TestHistory {

	public TestHistory()
	{
		super();
	}
	public TestHistory(Integer student_id, Integer test_id, Integer total_questions, Integer total_answered,
			Integer total_correct_ans, char grade, Date date) {
		super();
		this.student_id = student_id;
		this.test_id = test_id;
		this.total_questions = total_questions;
		this.total_answered = total_answered;
		this.total_correct_ans = total_correct_ans;
		this.grade = grade;
		this.date = date;
	}
	private Integer id;
	private Integer student_id;
	private Integer test_id;
	private Integer total_questions;
	private Integer total_answered;
	private Integer total_correct_ans;
	private char grade;
	private Date date;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getTotal_questions() {
		return total_questions;
	}
	public void setTotal_questions(Integer total_questions) {
		this.total_questions = total_questions;
	}
	public Integer getTotal_answered() {
		return total_answered;
	}
	public void setTotal_answered(Integer total_answered) {
		this.total_answered = total_answered;
	}
	public Integer getTotal_correct_ans() {
		return total_correct_ans;
	}
	public void setTotal_correct_ans(Integer total_correct_ans) {
		this.total_correct_ans = total_correct_ans;
	}
	public char getGrade() {
		return grade;
	}
	public void setGrade(char grade) {
		this.grade = grade;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
