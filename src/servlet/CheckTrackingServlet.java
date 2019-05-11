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
        //���ö�doPost������get��post��ͬ������
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
    //����Ϊ�˼򵥣�û�����봦��json�İ�������ģ��json����
    public static int getResponseResult(int status,int message){

        return  message;
    }


}