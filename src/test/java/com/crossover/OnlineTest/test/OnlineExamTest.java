package com.crossover.OnlineTest.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.OnlineTest.beans.ResultsVO;
import com.crossover.OnlineTest.beans.StudentResponseVO;
import com.crossover.OnlineTest.entity.Question;
import com.crossover.OnlineTest.entity.StudentResponse;
import com.crossover.OnlineTest.entity.TestDetail;
import com.crossover.OnlineTest.services.OnlineExamService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
public class OnlineExamTest {

	@Autowired
	private ApplicationContext applicationContext;

	OnlineExamService onlineExamService;

	@Before
	public void setup() {
		onlineExamService = (OnlineExamService) applicationContext.getBean("onlineExamService");
	}

	@Test
	public void testContext() {
		Assert.assertNotNull(applicationContext.getBean("onlineExamService"));
	}

	@Test
	public void testRetrieveTestDescription() {

		int testID = 1;
		TestDetail expectedTestDetail = new TestDetail();
		expectedTestDetail.setDescription("This test will test your very basics skill in java");

		TestDetail actualTestDetail = onlineExamService.getTestDetails(testID);
		Assert.assertEquals(expectedTestDetail.getDescription(), actualTestDetail.getDescription());
	}

	@Test
	public void testNullStudentLogin() {

		// The below username doesnt exist in database
		String studentUserName = "crossover2";
		String password = "crossover2";

		Assert.assertNull(onlineExamService.authenticateStudent(studentUserName, password));
	}

	@Test
	public void testStudentLogin() {

		String studentUserName = "crossover1";
		String password = "crossover1";

		Assert.assertNotNull(onlineExamService.authenticateStudent(studentUserName, password));
	}

	@Test
	public void testGetExamQuestions() {

		Question expectedQuestion1 = new Question(1, "In which year java programming language was invented?");
		Question actualQuestion1 = onlineExamService.getExamQuestionByID(1);
		Assert.assertEquals(expectedQuestion1.getQuestion_txt(), actualQuestion1.getQuestion_txt());
		
	}
	
	@Test
	public void testSubmitStudentNewResponse()
	{
		//Preparing student response data to save in database
		StudentResponseVO studentResponse = new StudentResponseVO();
		studentResponse.setTest_id(10); 
		studentResponse.setStudent_id(10);
		studentResponse.setQuestion_id(10);
		studentResponse.setOption_no(2);
		
		int id= onlineExamService.saveStudentResponse(studentResponse);
		Assert.assertNotEquals(0, id);
		
		//Clean up of added Response
		onlineExamService.deleteStudentResponse(id);
	}
	
	@Test 
	public void testGetStudentTestResponse()
	{
		//Preparing student response data to save in database
		StudentResponseVO studentResponse = new StudentResponseVO();
		studentResponse.setTest_id(10); 
		studentResponse.setStudent_id(10);
		studentResponse.setQuestion_id(10);
		studentResponse.setOption_no(2);
		
		int id= onlineExamService.saveStudentResponse(studentResponse);
		Assert.assertNotEquals(0, id);
		
		List<StudentResponse> studentResponseList = onlineExamService.getStudentResponseForTest(studentResponse.getStudent_id(), studentResponse.getTest_id());
		Assert.assertEquals(2,studentResponseList.get(0).getOption_no());
		
		//Clean up of added Response
		onlineExamService.deleteStudentResponse(id);
	}
	
	@Test
	public void testSubmitStudentResponseTwice()
	{
		//Preparing student response data to save in database
		StudentResponseVO studentResponse = new StudentResponseVO();
		studentResponse.setTest_id(10); 
		studentResponse.setStudent_id(10);
		studentResponse.setQuestion_id(10);
		studentResponse.setOption_no(2);
	
		//Saving new response first
		int id= onlineExamService.saveStudentResponse(studentResponse);
		Assert.assertNotEquals(0, id);
		
		//changing option for same question id and submitting response
		studentResponse.setOption_no(3);
		int id2= onlineExamService.saveStudentResponse(studentResponse);
		
		Assert.assertEquals(1,id2);
		
		//Clean up of added Response
		onlineExamService.deleteStudentResponse(id);
	}
	
	@Test
	public void testResultsCalculation()
	{
		//Preparing student response data to save in database
		int testID = 1;
		int studentID = 1;
		StudentResponseVO studentResponse = new StudentResponseVO();
		studentResponse.setTest_id(testID); 
		studentResponse.setStudent_id(studentID);
		studentResponse.setQuestion_id(1);
		studentResponse.setOption_no(4);
	
		//Saving response 
		int id1= onlineExamService.saveStudentResponse(studentResponse);
		
		 studentResponse = new StudentResponseVO();
		studentResponse.setTest_id(testID); 
		studentResponse.setStudent_id(studentID);
		studentResponse.setQuestion_id(2);
		studentResponse.setOption_no(2);
	
		//Saving response 
		int id2= onlineExamService.saveStudentResponse(studentResponse);
		
		 studentResponse = new StudentResponseVO();
		studentResponse.setTest_id(testID); 
		studentResponse.setStudent_id(studentID);
		studentResponse.setQuestion_id(3);
		studentResponse.setOption_no(2);
	
		//Saving response 
		int id3= onlineExamService.saveStudentResponse(studentResponse);
		
		 studentResponse = new StudentResponseVO();
		studentResponse.setTest_id(testID); 
		studentResponse.setStudent_id(studentID);
		studentResponse.setQuestion_id(4);
		studentResponse.setOption_no(2); 
	
		//Saving response 
		int id4= onlineExamService.saveStudentResponse(studentResponse);
		
		//Calculating result
		ResultsVO resultVO = onlineExamService.calculateTestResult(studentID, testID);
		
		if(resultVO!=null) 
		{
			Assert.assertEquals(4, resultVO.getTotalAnswered());
			Assert.assertEquals(3, resultVO.getTotalCorrectAnswered());
		}else
		{
			Assert.assertFalse("Failed, got null result", false);
		}
		
		
		//Clean up of added Response
		onlineExamService.deleteStudentResponse(id1);
		onlineExamService.deleteStudentResponse(id2);
		onlineExamService.deleteStudentResponse(id3);
		onlineExamService.deleteStudentResponse(id4);
		
	}
	
}
