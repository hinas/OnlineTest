package com.crossover.OnlineTest.DAO;

import java.util.List;

import com.crossover.OnlineTest.entity.Question;
import com.crossover.OnlineTest.entity.Student;
import com.crossover.OnlineTest.entity.StudentResponse;
import com.crossover.OnlineTest.entity.TestDetail;
import com.crossover.OnlineTest.entity.TestHistory;

public interface OnlineTestDAO {
	
	/**
	 * This method will get the student details based on passed username
	 * @param studentUserName
	 * @return
	 */
	public Student getStudentDetails(String studentUserName);

	/**
	 * This method will get test details based on passed test id
	 * @param testId
	 * @return
	 */
	public TestDetail getTestDetails(int testId);

	/**
	 * This method will get a particular question and its options based on quesiton id passed 
	 * @param question_id
	 * @return
	 */
	public Question getExamQuestion(int question_id);

	/**
	 * This method will save the student response into database for a particular question
	 * @param studentResponse
	 * @return
	 */
	public int saveStudentResponse(StudentResponse studentResponse);

	/**
	 * This will delete the student repsonse
	 * @param id
	 * @return
	 */
	public int deleteStudentResponse(int id);

	/**
	 * This method will query database to get all the responses student submitted for a particular test
	 * @param student_id
	 * @param test_id
	 * @return
	 */
	public List<StudentResponse> getStudentResponseForTest(int student_id, int test_id);

	/**
	 * This method will only get response to a particular question, whic student submitted
	 * @param student_id
	 * @param test_id
	 * @param question_id
	 * @return
	 */
	public StudentResponse getStudentResponseForQuestion(int student_id, int test_id, int question_id);

	/**
	 * This method will update the existing response student gave, with new option no
	 * @param id
	 * @param newOption
	 * @return
	 */
	public int updateExistingOptionStudentResponse(int id, int newOption);

	/**
	 * This method will save the test history into database with grade of the student
	 * @param testHistory
	 * @return
	 */
	public int saveTestHistory(TestHistory testHistory);

}
