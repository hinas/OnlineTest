package com.crossover.OnlineTest.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.crossover.OnlineTest.DAO.OnlineTestDAO;
import com.crossover.OnlineTest.beans.ResultsVO;
import com.crossover.OnlineTest.beans.StudentResponseVO;
import com.crossover.OnlineTest.beans.StudentVO;
import com.crossover.OnlineTest.entity.Option;
import com.crossover.OnlineTest.entity.Question;
import com.crossover.OnlineTest.entity.Student;
import com.crossover.OnlineTest.entity.StudentResponse;
import com.crossover.OnlineTest.entity.TestDetail;
import com.crossover.OnlineTest.entity.TestHistory;
import com.crossover.OnlineTest.entity.TestQuestions;
import com.crossover.OnlineTest.util.Password;
import com.mysql.jdbc.StringUtils;

public class OnlineExamServiceImpl implements OnlineExamService {

	@Autowired
	OnlineTestDAO onlineTestDAO;

	public StudentVO authenticateStudent(String studentUserName, String password) {

		if (!StringUtils.isNullOrEmpty(studentUserName) && !StringUtils.isNullOrEmpty(password)) {
			Student student = onlineTestDAO.getStudentDetails(studentUserName);

			if (student != null) {
				
				if (Password.checkPassword(password, student.getPassword())) {
					StudentVO studentVO = new StudentVO();
					studentVO.setStudent_id(student.getStudent_id());
					studentVO.setName(student.getName());
					studentVO.setUsername(student.getUsername());
					studentVO.setEmail(student.getEmail());

					return studentVO;
				}
			}
		}

		return null;
	}

	public TestDetail getTestDetails(int testId) {
		return onlineTestDAO.getTestDetails(testId);
	}

	public Question getExamQuestionByID(int question_id) {
		return onlineTestDAO.getExamQuestion(question_id);
	}

	public int saveStudentResponse(StudentResponseVO studentResponseVO) {

		StudentResponse existingResponse = getStudentResponseForQuestion(studentResponseVO.getStudent_id(),
				studentResponseVO.getTest_id(), studentResponseVO.getQuestion_id());

		if (existingResponse != null) {
			// Existing response is present, update the new option selected
			return updateExistingOptionStudentResponse(existingResponse.getId(), studentResponseVO.getOption_no());
			
		} else {
			// No response found in database, add a new response
			StudentResponse studentResponse = new StudentResponse();
			studentResponse.setStudent_id(studentResponseVO.getStudent_id());
			studentResponse.setTest_id(studentResponseVO.getTest_id());
			studentResponse.setQuestion_id(studentResponseVO.getQuestion_id());
			studentResponse.setOption_no(studentResponseVO.getOption_no());
			return onlineTestDAO.saveStudentResponse(studentResponse);
		}

	}

	public int deleteStudentResponse(int id) {
		return onlineTestDAO.deleteStudentResponse(id);
	}

	public List<StudentResponse> getStudentResponseForTest(int student_id, int test_id) {
		return onlineTestDAO.getStudentResponseForTest(student_id, test_id);
	}

	public StudentResponse getStudentResponseForQuestion(int student_id, int test_id, int question_id) {
		return onlineTestDAO.getStudentResponseForQuestion(student_id, test_id, question_id);
	}

	public int updateExistingOptionStudentResponse(int id, int newOption) {
		return onlineTestDAO.updateExistingOptionStudentResponse(id, newOption);
	}

	public ResultsVO calculateTestResult(int studentID, int testID) {
		List<StudentResponse> studentResponse = onlineTestDAO.getStudentResponseForTest(studentID, testID);
		TestDetail testDetail = onlineTestDAO.getTestDetails(testID);

		if (studentResponse != null) {
			
			// Constructing a Map with questions mapped to its correct option nos
			Map<Integer, Integer> questionAnswerMap = new HashMap<Integer, Integer>();
			for (TestQuestions testQuestion : testDetail.getTestQuestions()) {
				for (Option option : testQuestion.getQuestion().getOptions()) {
					if (option.getIs_answer() == 'Y')
						questionAnswerMap.put(testQuestion.getQuestion().getQuestion_id(), option.getOption_no());
				}
			}

			int totalAnswered = studentResponse.size();
			int totalQuestions = questionAnswerMap.size();
			int totalUnAnswered = totalQuestions - totalAnswered;
			int totalCorrectAnswered = 0;

			//Calculating total correct answer
			for (StudentResponse studentResp : studentResponse) {
				Integer correctOptionNo = questionAnswerMap.get(studentResp.getQuestion_id());
				if (correctOptionNo == studentResp.getOption_no())
					totalCorrectAnswered++;
			}

			ResultsVO resultVO = new ResultsVO();
			resultVO.setTotalAnswered(totalAnswered);
			resultVO.setTotalCorrectAnswered(totalCorrectAnswered);
			resultVO.setTotalQuestions(totalQuestions);
			resultVO.setTotalUnAnswered(totalUnAnswered);

			return resultVO;
		}
		return null;
	}

	public int saveTestHistoy(Integer studentID, Integer testID, ResultsVO result) {

		char grade = calculateGrade(result);
		TestHistory testHistory = new TestHistory(studentID, testID, result.getTotalQuestions(),
				result.getTotalAnswered(), result.getTotalCorrectAnswered(), grade, new Date());

		return onlineTestDAO.saveTestHistory(testHistory);
	}

	/**
	 * This mehtod will calculate the grade of student
	 * @param result
	 * @return
	 */
	private char calculateGrade(ResultsVO result) {

		int percentage = result.getTotalCorrectAnswered() / result.getTotalQuestions() * 100;
		if (percentage >= 90 && percentage <= 100)
			return 'A';
		else if (percentage >= 70 && percentage <= 90)
			return 'B';
		else if (percentage >= 60 && percentage <= 80)
			return 'C';
		else
			return 'D';

	}

}
