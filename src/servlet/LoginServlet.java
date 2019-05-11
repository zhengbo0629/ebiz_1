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
		String password = (String) request.getParameter("password");// 取出login.jsp的值
		List<String> info = new ArrayList<String>();
		if (username == null || "".equals(username)) { // 用户名输入格式问题
			info.add("用户名不能为空");
		}

		if (password == null || "".equals(password)) {// 密码输入格式问题
			info.add("密码不能为空");
		}
		if (info.size() == 0) {
			EbizUser user = EbizSql.getInstance().findUser(username);
			
			if (user == null) {
				info.add("用户名不存在");
				// response.setHeader("refresh", "0;url=login.jsp");
				request.setAttribute("info", info);// 保存错误信息
				request.getRequestDispatcher("login.jsp").forward(request, response);// 跳转
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
				info.add("密码错误");
				request.setAttribute("info", info);// 保存错误信息
				request.getRequestDispatcher("login.jsp").forward(request, response);// 跳转
			}
		} else {
			request.setAttribute("info", info);// 保存错误信息
			request.getRequestDispatcher("login.jsp").forward(request, response);// 跳转
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