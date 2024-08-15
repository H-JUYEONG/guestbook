package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println(action);

		if ("addList".equals(action)) {

			// 접수
			System.out.println("리스트 요청");

			// db데이터 가져오기
			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestVo> guestList = guestbookDao.getGuestList();

			// 화면그리기 --> 포워드
			// request 에 리스트주소 넣기
			request.setAttribute("guestList", guestList);

			// 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/addList.jsp");
			rd.forward(request, response);

		} else if ("insert".equals(action)) {

			System.out.println("등록 요청 데이터 3개 저장해줘");

			// 나머지 파라미터 꺼내서 PersonVo로 묶기
			String name = request.getParameter("name");
			String pw = request.getParameter("password");
			String content = request.getParameter("content");

			GuestVo guestVo = new GuestVo(name, pw, content);

			// Dao를 메모리에 올린다.
			GuestbookDao guestbookDao = new GuestbookDao();

			// insertPerson(personVo) 사용해서 db에 저장한다.
			guestbookDao.insertGuest(guestVo);

			// 리다이렉트
			response.sendRedirect("/guestbook/gbc?action=addList");

		} else if ("deleteForm".equals(action)) {

			System.out.println("삭제 폼 요청");

			// 파라미터는 기본이 문자형이기때문에 형변환 필요
			int no = Integer.parseInt(request.getParameter("no"));
			
			GuestVo guestVo = new GuestVo(no);

			// 화면+데이터 수정폼
			// 리퀘스트 어트리뷰트 영역에 guestVo 주소를 담는다
			request.setAttribute("guestVo", guestVo);

			// 포워드 deleteForm.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteForm.jsp");
			rd.forward(request, response);

		} else if ("delete".equals(action)) {

			// 파라미터는 기본이 문자형이기때문에 형변환 필요
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("password");

			GuestVo guestVo = new GuestVo(no, pw);

			// Dao를 메모리에 올린다
			GuestbookDao guestbookDao = new GuestbookDao();

			// GuestbookDao를 통해서 삭제(delete)를 시킨다
			guestbookDao.deleteGuest(guestVo);

			// 리다이렉트
			response.sendRedirect("/guestbook/gbc?action=addList");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
