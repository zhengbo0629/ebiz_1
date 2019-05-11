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
        //���ö�doPost������get��post��ͬ������
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EbizUser currentUser=(EbizUser)request.getSession().getAttribute("currentUser");
        String model = request.getParameter("Model");
        int reportedQuantity=EbizSql.getInstance().reportedNnumberInresentTwodaysForUser(currentUser, model);
        //���ؿͻ��˽��
        int result = getResponseResult(200,reportedQuantity);

        //��result���ؿͻ���
        response.getWriter().print(result);

        //������Բ��ùر� resp.getWriter()�����������������

    }
    //����Ϊ�˼򵥣�û�����봦��json�İ�������ģ��json����
    public static int getResponseResult(int status,int message){

        return  message;
    }


}