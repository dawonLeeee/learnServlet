package edu.kh.jsp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.kh.jsp.model.vo.Person;

@WebServlet("/elTest")
public class ELTestServlet extends HttpServlet{

	// ** 데이터 전달 방식(get/post) 에 따라서
	//   하나의 요청 주소로 여러가지 처리가 가능하다! **
	
	
	// a태그로 요청(GET)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher("/WEB-INF/views/el/elTest.jsp");
		
		// WEB-INF 폴더는
		// 외부 접근 방법을 통해서 접근 불가
		// 단, 내부 접근(Servlet, JSP에서의 직접 접근)은 가능
	
		dispatcher.forward(req, resp);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 요청 데이터 문자 인코딩 지정
		req.setCharacterEncoding("UTF-8");
		
		// 파라미터 얻어오기
		String name = req.getParameter("inputName");
		int age = Integer.parseInt(req.getParameter("inputAge"));
		String address = req.getParameter("inputAddress");
		
		String message = name + "님은 " + age + "세 이고 "
				+ address + "에 거주중입니다.";
		
		// person객체 생성
		Person p = new Person();
		p.setName(name + "님");
		p.setAddress("대한민국" + address);
		p.setAge(age + 10000);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/el/elResult.jsp");
		
		//요청 위임시 추가할 값 셋팅
		req.setAttribute("message", message);
		req.setAttribute("person", p);
		
		
		//JSP로 요청 위임
		dispatcher.forward(req, resp);
		
	}
	
	
	
	
	
	
	
	
}