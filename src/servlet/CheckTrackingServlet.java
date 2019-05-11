package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataCenter.EbizSql;
import ebizConcept.EbizPackage;

@WebServlet("/CheckTrackingServlet")
public class CheckTrackingServlet extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
        //调用都doPost方法，get和post做同样处理
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String trackingNumber= (String) request.getParameter("trackingnumber");
		int uid = Integer.parseInt( request.getParameter("packageId"));
		System.out.println(uid);
		EbizPackage pack=EbizSql.getInstance().findPackage(uid);

    }
    //这里为了简单，没有引入处理json的包，这是模拟json数据
    public static int getResponseResult(int status,int message){

        return  message;
    }


}