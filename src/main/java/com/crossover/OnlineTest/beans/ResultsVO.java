package com.crossover.OnlineTest.beans;

public class ResultsVO {

	public ResultsVO()
	{
		super();
	}
	
	public ResultsVO(int totalAnswered, int totalQuestions, int totalUnAnswered, int totalCorrectAnswered) {
		super();
		this.totalAnswered = totalAnswered;
		this.totalQuestions = totalQuestions;
		this.totalUnAnswered = totalUnAnswered;
		this.totalCorrectAnswered = totalCorrectAnswered;
	}
	private int totalAnswered;
	private int totalQuestions;
	private int totalUnAnswered;
	private int totalCorrectAnswered;
	public int getTotalAnswered() {
		return totalAnswered;
	}
	public void setTotalAnswered(int totalAnswered) {
		this.totalAnswered = totalAnswered;
	}
	public int getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public int getTotalUnAnswered() {
		return totalUnAnswered;
	}
	public void setTotalUnAnswered(int totalUnAnswered) {
		this.totalUnAnswered = totalUnAnswered;
	}
	public int getTotalCorrectAnswered() {
		return totalCorrectAnswered;
	}
	public void setTotalCorrectAnswered(int totalCorrectAnswered) {
		this.totalCorrectAnswered = totalCorrectAnswered;
	}
	
	

	
}
