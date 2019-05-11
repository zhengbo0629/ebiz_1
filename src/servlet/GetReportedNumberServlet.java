package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataCenter.EbizSql;
import ebizConcept.EbizUser;

@WebServlet("/GetReportedNumberServlet")
public class GetReportedNumberServlet extends HttpServlet{

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
        EbizUser currentUser=(EbizUser)request.getSession().getAttribute("currentUser");
        String model = request.getParameter("Model");
        int reportedQuantity=EbizSql.getInstance().reportedNnumberInresentTwodaysForUser(currentUser, model);
        //返回客户端结果
        int result = getResponseResult(200,reportedQuantity);

        //将result返回客户端
        response.getWriter().print(result);

        //这里可以不用关闭 resp.getWriter()流，由容器负责管理

    }
    //这里为了简单，没有引入处理json的包，这是模拟json数据
    public static int getResponseResult(int status,int message){

        return  message;
    }


}