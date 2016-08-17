package kr.ac.sungkyul.emaillist.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.emaillist.dao.EmailListDao;
import kr.ac.sungkyul.emaillist.vo.EmailListVo;

/**
 * Servlet implementation class EmaillistServlet
 */
@WebServlet("/el")
public class EmaillistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");

		if ("list".equals(actionName)) {
			EmailListDao dao = new EmailListDao();
			List<EmailListVo> list = dao.getList();

			/* request 범위(scope)에  list객체를 저장 ( 매개변수 - 참조이름, 참조값) */
			request.setAttribute("list", list); 
			
			/* 포워딩 (분기를 담당하는 객체 - 매개변수에는 분기하는 위치를 적어준다 , 여기로 분기해라)*/
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/list.jsp");
			dispatcher.forward(request, response);

		} else if ("form".equals(actionName)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/form.jsp");
			dispatcher.forward(request, response);
		} else {
			
			EmailListDao dao = new EmailListDao();
			List<EmailListVo> list = dao.getList();

			/* request 범위(scope)에  list객체를 저장 ( 매개변수 - 참조이름, 참조값) */
			request.setAttribute("list", list); 
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/list.jsp");
			dispatcher.forward(request, response);
		}
		
		if("insert".equals(actionName)) {
			String firstName = request.getParameter("fn");
			String lastName = request.getParameter("ln");
			String email = request.getParameter("email");

			EmailListVo vo = new EmailListVo();
			vo.setFirstName(firstName);
			vo.setLastName(lastName);
			vo.setEmail(email);

			EmailListDao dao = new EmailListDao();

			boolean result = dao.insert(vo);
			
			response.sendRedirect("/emaillist2/el");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request , response);
	}

}
