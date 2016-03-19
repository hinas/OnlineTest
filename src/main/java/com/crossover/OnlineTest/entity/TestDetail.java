package com.crossover.OnlineTest.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "test")
public class TestDetail implements Serializable {

	public TestDetail() {
		super();
	}

	public TestDetail(int test_id, String testName, String description, int duration) {
		super();
		this.test_id = test_id;
		this.testName = testName;
		this.description = description;
		this.duration = duration;
	}
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int test_id;

	private String testName;

	private String description;

	private int duration;

	private List<TestQuestions> testQuestions;

	@Id
	@GeneratedValue
	public int getTest_id() {
		return test_id;
	}

	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}

	@Column(name = "name")
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@OneToMany(mappedBy = "testDetail", cascade = CascadeType.ALL)
	public List<TestQuestions> getTestQuestions() {
		return testQuestions;
	}

	public void setTestQuestions(List<TestQuestions> testQuestions) {
		this.testQuestions = testQuestions;
	}

}
