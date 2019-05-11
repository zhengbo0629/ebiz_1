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
import ebizTools.GeneralMethod;
import nameEnum.EbizUserPermissionEnum;
import nameEnum.EbizUserTypeEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/NurseRegistrationServlet")
public class NurseRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NurseRegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");// 取出login.jsp的值
		String companyName = (String) request.getParameter("companyName");
		String address = (String) request.getParameter("address");
		String zipcode = (String) request.getParameter("zipcode");
		String lastName = (String) request.getParameter("lastName");
		String firstName = (String) request.getParameter("firstName");
		String contactPhone = (String) request.getParameter("contactPhone");
		String contactEmail = (String) request.getParameter("contactEmail");

		List<String> info = new ArrayList<String>();
		EbizCompany company = null;
		if (info.size() == 0) {
			EbizUser user = EbizSql.getInstance().findUser(username);
			company = EbizSql.getInstance().findCompany(companyName);
			if (user != null) {
				info.add("用户名已存在,请用其他用户名重新注册");
				// response.setHeader("refresh", "0;url=login.jsp");
			}
			if (company == null) {
				info.add("公司名不存在,请输入正确的公司名称");
			}
		}
		if (info.size() != 0) {
			request.setAttribute("info", info);// 保存错误信息
			request.getRequestDispatcher("nurseregistration.jsp").forward(request, response);// 跳转
		} else {
			String createTime=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
			EbizUser user=new EbizUser(0,username,firstName,lastName,companyName, password, "11111111", contactEmail, contactPhone,  address+","+zipcode,
					createTime,createTime,"","UnActive",EbizUserTypeEnum.Nurse.getName(), 0,1000,"" );
			
			for(EbizUserPermissionEnum permission:EbizUserPermissionEnum.values()){
				if(permission.getRole().equals("NurseDefault"))
					user.addUserPermissions(permission.getName());
			}
			user.addUserPermissions(EbizUserPermissionEnum.NursePackageManager.getName());
			
			EbizSql.getInstance().addUser(user,company);
			user.updateLoginCounter();
			user.balance=0;
			request.getSession().setAttribute("currentUser",user);
			request.getRequestDispatcher("registrationsuccess.jsp").forward(request, response);// 跳转
		}

	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
	}
 
}