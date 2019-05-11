package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.*;
import ebizConcept.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");// ȡ��login.jsp��ֵ
		List<String> info = new ArrayList<String>();
		if (username == null || "".equals(username)) { // �û��������ʽ����
			info.add("�û�������Ϊ��");
		}

		if (password == null || "".equals(password)) {// ���������ʽ����
			info.add("���벻��Ϊ��");
		}
		if (info.size() == 0) {
			EbizUser user = EbizSql.getInstance().findUser(username);
			
			if (user == null) {
				info.add("�û���������");
				// response.setHeader("refresh", "0;url=login.jsp");
				request.setAttribute("info", info);// ���������Ϣ
				request.getRequestDispatcher("login.jsp").forward(request, response);// ��ת
			} else if (user.passWord.equals(password)) {

				
				request.getSession().setAttribute("currentUser", user);

				
				if (user.isActive()) {
					
					EbizCompany company=EbizSql.getInstance().findCompany(user.companyName);
					request.getSession().setAttribute("currentCompany", company);
					response.sendRedirect("loginsuccess.jsp");
				} else {
					response.sendRedirect("registrationsuccess.jsp");
				}
			} else {
				// response.setHeader("refresh", "0;url=login.jsp");
				info.add("�������");
				request.setAttribute("info", info);// ���������Ϣ
				request.getRequestDispatcher("login.jsp").forward(request, response);// ��ת
			}
		} else {
			request.setAttribute("info", info);// ���������Ϣ
			request.getRequestDispatcher("login.jsp").forward(request, response);// ��ת
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}