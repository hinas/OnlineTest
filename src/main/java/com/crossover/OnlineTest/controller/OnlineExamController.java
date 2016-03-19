package com.crossover.OnlineTest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crossover.OnlineTest.beans.HomepageVO;
import com.crossover.OnlineTest.beans.QuestionDetailsVO;
import com.crossover.OnlineTest.beans.QuestionsVO;
import com.crossover.OnlineTest.beans.ResultsVO;
import com.crossover.OnlineTest.beans.StudentResponseVO;
import com.crossover.OnlineTest.beans.StudentVO;
import com.crossover.OnlineTest.entity.Question;
import com.crossover.OnlineTest.entity.StudentResponse;
import com.crossover.OnlineTest.entity.TestDetail;
import com.crossover.OnlineTest.entity.TestQuestions;
import com.crossover.OnlineTest.services.OnlineExamService;
import com.crossover.OnlineTest.util.Constants;

@Controller
public class OnlineExamController {

	@Autowired
	OnlineExamService onlineExamService;

	/**
	 * This controller method will show the Homepage with test decription
	 * and will allow student to enter its credentials and login
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/Home")
	public ModelAndView getHomePage(HttpServletRequest request, HttpServletResponse response) {
		
		int testID = 1;
		
		//Get testDetails from database
		TestDetail testDetail = onlineExamService.getTestDetails(testID);
		
		HomepageVO homepageVO = new HomepageVO();
		homepageVO.setTestDescription(testDetail.getDescription());
		homepageVO.setTestTitle(testDetail.getTestName());
		homepageVO.setTestID(testDetail.getTest_id());

		return new ModelAndView("HomePage", "homepage", homepageVO);
	}

	/**
	 * This controller method will authenticate student and on successfull authentication
	 * it will show Questions of that particular test
	 * @param homepageVO
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "Login", method = RequestMethod.POST)
	public String login(@ModelAttribute("homepage") HomepageVO homepageVO, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Authenticate student - Service call
		StudentVO student = onlineExamService.authenticateStudent(homepageVO.getUsername(), homepageVO.getPassword());

		if (student != null) {
			
			request.getSession().setAttribute(Constants.SESSION_TEST_ID, homepageVO.getTestID());
			request.getSession().setAttribute(Constants.SESSION_STUDENT_ID, student.getStudent_id());
			
			homepageVO.setStudentId(student.getStudent_id());
			redirectAttributes.addFlashAttribute("homepage", homepageVO);
			return "redirect:/LandingPage";
			
		} else {
			homepageVO.setErrorMsg("Invalid username or password");
			return "redirect:/Home";
		}
	}

	/**
	 * This is Landing page after login, which will show questions of the test
	 * @param homepageVO
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(name = "/LandingPage", method = RequestMethod.GET)
	public ModelAndView showLandingPage(@ModelAttribute("homepage") HomepageVO homepageVO, HttpServletRequest request,
			HttpServletResponse response) 
	{
		// Student Authenticated, get test questions from db
		QuestionsVO questionsVO = null;
		TestDetail testdetail = onlineExamService.getTestDetails(homepageVO.getTestID());
		
		if (testdetail != null) {
			
			//Construct MAP of <Question_ID, Question Object>
			Map<Integer, Question> questionMap = new HashMap<Integer, Question>();
			for (TestQuestions testQuestion : testdetail.getTestQuestions())
				questionMap.put(testQuestion.getQuestion().getQuestion_id(), testQuestion.getQuestion());

			questionsVO = new QuestionsVO();
			List<Integer> questionNos = new ArrayList<Integer>(questionMap.keySet());

			questionsVO.setQuestionNos(questionNos);
			questionsVO.setStudent_id(homepageVO.getStudentId());
			questionsVO.setTest_id(testdetail.getTest_id());
			questionsVO.setTestTitle(testdetail.getTestName());
			questionsVO.setTestDuration(testdetail.getDuration());

			// Put questionMap bean in session for further use: format: questionMap_<student_id>_<test_id>
			request.getSession().setAttribute("questionMap_" + homepageVO.getStudentId() + "_" + testdetail.getTest_id(), questionMap);
		}
		
		return new ModelAndView("Questions", "questions", questionsVO);
	}

	/**
	 * This controller method will accept change the question text and options
	 * and send it to front end
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ChangeQuestion", method = RequestMethod.POST)
	public ModelAndView changeQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 1. get received JSON data from request and convert JSON to object
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) 
			json = br.readLine();
		ObjectMapper objectMapper = new ObjectMapper();
		QuestionsVO questionVO = objectMapper.readValue(json, QuestionsVO.class);

		//Get QuestionMap from session, format: questionMap_<student_id>_<test_id>
		Map<Integer, Question> questionMap = (Map<Integer, Question>) request.getSession().getAttribute("questionMap_" + questionVO.getStudent_id() + "_" + questionVO.getTest_id());
		Question question = questionMap.get(questionVO.getQuestion_id());
		QuestionDetailsVO questionDetails = new QuestionDetailsVO();
		questionDetails.setQuestion_id(question.getQuestion_id());
		questionDetails.setOptions(question.getOptions());
		questionDetails.setQuestion_txt(question.getQuestion_txt());

		// Get submitted option from Database, if any
		StudentResponse studentResponse = onlineExamService.getStudentResponseForQuestion(questionVO.getStudent_id(),
				questionVO.getTest_id(), question.getQuestion_id());
		if (studentResponse != null)
			questionDetails.setOptionChoosed(studentResponse.getOption_no());

		if (questionMap.size() > 0) {
			return new ModelAndView("QuestionDetails", "questionDetail", questionDetails);
		} else {
			return new ModelAndView("MessagePage", "message", "No Question Found");
		}
	}

	/**
	 * This method will submit the response to a particular question and save 
	 * the option number selected into database (student_response table)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/SubmitResponse", method = RequestMethod.POST)
	public ModelAndView submitResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 1. get received JSON data from request and convert JSON to object
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) 
			json = br.readLine();
		ObjectMapper objectMapper = new ObjectMapper();
		StudentResponseVO questionVO = objectMapper.readValue(json, StudentResponseVO.class);

		int updated = onlineExamService.saveStudentResponse(questionVO);

		if (updated > 0) {
			return new ModelAndView("MessagePage", "message", "Submitted");
		} else {
			return new ModelAndView("MessagePage", "message", "Failed");
		}
	}

	/**
	 * This will end the test and will display total questions answered to front end
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/EndTest", method = RequestMethod.GET)
	public ModelAndView entTestPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int testID = (Integer) request.getSession().getAttribute(Constants.SESSION_TEST_ID);
		int studentID = (Integer) request.getSession().getAttribute(Constants.SESSION_STUDENT_ID);

		ResultsVO resultVO = onlineExamService.calculateTestResult(studentID, testID);
		
		//Set results in session for further use
		request.getSession().setAttribute(Constants.SESSION_RESULTS, resultVO);

		if (resultVO != null) {
			return new ModelAndView("EndPage", "result", resultVO);
		} else {
			return new ModelAndView("MessagePage", "message", "Failed");
		}
	}

	/**
	 * This method will finish the test, calculate the grade and save it into database (test_history table)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/FinishTest", method = RequestMethod.GET)
	public ModelAndView finishTest(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ResultsVO result = (ResultsVO) request.getSession().getAttribute(Constants.SESSION_RESULTS);
		Integer testID = (Integer) request.getSession().getAttribute(Constants.SESSION_TEST_ID);
		Integer studentID = (Integer) request.getSession().getAttribute(Constants.SESSION_STUDENT_ID);

		if (result != null) {
			int id = onlineExamService.saveTestHistoy(studentID, testID, result);
			
			if(id > 0)
				return new ModelAndView("MessagePage", "message", "<legend>Thank you for taking the test</legend>");
			else
				return new ModelAndView("MessagePage", "message", "Failed");
		} else {
			return new ModelAndView("MessagePage", "message", "Failed");
		}
	}
	
	/**
	 * This method will take you back to Questions page
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/goBackToQuestions", method = RequestMethod.GET)
	public String goBackToQuestions(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws IOException {

		Integer testID = (Integer) request.getSession().getAttribute(Constants.SESSION_TEST_ID);
		Integer studentID = (Integer) request.getSession().getAttribute(Constants.SESSION_STUDENT_ID);
		HomepageVO homepageVO = new HomepageVO();
		homepageVO.setStudentId(studentID);
		homepageVO.setTestID(testID);
		
		redirectAttributes.addFlashAttribute("homepage", homepageVO);
		return "redirect:/LandingPage";

	}
}
