package com.crossover.OnlineTest.services;

import java.util.List;

import com.crossover.OnlineTest.beans.ResultsVO;
import com.crossover.OnlineTest.beans.StudentResponseVO;
import com.crossover.OnlineTest.beans.StudentVO;
import com.crossover.OnlineTest.entity.Question;
import com.crossover.OnlineTest.entity.StudentResponse;
import com.crossover.OnlineTest.entity.TestDetail;

public interface OnlineExamService {

	/**
	 * This service method will authenticate the sudent based on its username and password 
	 * @param studentUserName
	 * @param password
	 * @return
	 */
	public StudentVO authenticateStudent(String studentUserName, String password);

	/**
	 * This service method will get all the test details based on testID passed
	 * @param testId
	 * @return
	 */
	public TestDetail getTestDetails(int testId);

	/**
	 * This mehtod will get the question and its options based on the question ID passed
	 * @param question_id
	 * @return
	 */
	public Question getExamQuestionByID(int question_id);

	/**
	 * This method will save the student response to database after student submits answer for a question
	 * if response already found in database then it will update the existing response with new option no
	 * @param questionVO
	 * @return
	 */
	public int saveStudentResponse(StudentResponseVO questionVO);

	/**
	 * This method will delete the response based on ID
	 * @param id
	 * @return
	 */
	public int deleteStudentResponse(int id);

	/**
	 * This method will get all the responses of student, based on student ID and test ID
	 * @param student_id
	 * @param test_id
	 * @return
	 */
	public List<StudentResponse> getStudentResponseForTest(int student_id, int test_id);
	
	/**
	 * This method will get a response for a particular question, based on passed question id
	 * @param student_id
	 * @param test_id
	 * @param question_id
	 * @return
	 */
	public StudentResponse getStudentResponseForQuestion(int student_id, int test_id, int question_id) ;

	/**
	 * This method will calculate the testresult, by getting all the the responses student gave for a particular test
	 * @param studentID
	 * @param testID
	 * @return
	 */
	public ResultsVO calculateTestResult(int studentID, int testID);

	public int saveTestHistoy(Integer studentID, Integer testID, ResultsVO result);
	

}

