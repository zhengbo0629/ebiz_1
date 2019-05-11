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
import nameEnum.EbizPackageStatusEnum;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/EditWareHouseTrackingServlet")
public class EditWareHouseTrackingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditWareHouseTrackingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int quantity =Integer.parseInt(request.getParameter("quantity"));
		String creditcard = (String) request.getParameter("creditcard");
		int uid = Integer.parseInt( request.getParameter("packageId"));
		EbizPackage pack=EbizSql.getInstance().findPackage(uid);
		String allTrackingNumber= (String) request.getParameter("trackingnumber");
		int totalQuantity=Integer.parseInt( request.getParameter("totalQuantity"));
		double basePrice=Double.parseDouble(request.getParameter("basePrice"));
		double promPrice=Double.parseDouble( request.getParameter("promPrice"));
		int promQuantity=Integer.parseInt(request.getParameter("promQuantity"));
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
		double price=basePrice;
		if (quantity>=promQuantity){
		    price=promPrice;
		}
		//没有填写tracking，只是更新数量或者信用卡；
		String message="";
		if(totalQuantity==0||allTrackingNumber==null||allTrackingNumber.length()<=5){
			boolean update=false;
			
			if(pack.quantity!=quantity){

				update=EbizSql.getInstance().updatePackageQuantityAndPrice(currentUser,currentCompany,pack,quantity,price);
				if(update){
					pack.quantity=quantity;
					pack.price=price;
					message=message+"Quantity Update Sucessfully \n";
				}else{
					message=message+"Quantity Update Failed, Please Try Again \n";
				}
			}
			if(!pack.creditcardNumber.equals(creditcard)){

				update=EbizSql.getInstance().updatePackageCreditCard(currentUser,currentCompany,pack.UID,creditcard);
				if(update){
					pack.creditcardNumber=creditcard;
					message=message+"CreditCard Information Update Sucessfully \n";
				}else{
					message=message+"CreditCard Information Update Failed, Please Try Again \n";
				}
			}
			if(message.length()>0){
				response.getWriter().print(message);
			}else{
				response.getWriter().print("No Need Update");
			}
		}else{
			//check tracking
			List<String> badTrackingList=new ArrayList<>();
			String[] trackAndQuantity=allTrackingNumber.split("\\?");
			for(int i=0;i<trackAndQuantity.length;i++){
				String oneTrackingString=trackAndQuantity[i].split("_")[0];
				if(pack.trackingNumber!=null&&pack.trackingNumber.contains(oneTrackingString)) continue;
				
		        int count=count(allTrackingNumber,oneTrackingString);         
		        if (count>1){
		        	badTrackingList.add(oneTrackingString);
		        	continue;
		        }
		        
				List<EbizPackage> packagesList=EbizSql.getInstance().readPackagesByTracking(oneTrackingString);
				for(int j=0;j<packagesList.size();j++){
					//only the same user with different product can have same tracking;
					if(!packagesList.get(j).userName.equals(pack.userName)||packagesList.get(j).getModel().equals(pack.getModel())){
						// this tracking has been reported by other user or has been reported befor for the same product;
						badTrackingList.add(oneTrackingString);
					}
				}	
			}		
			if(badTrackingList.size()!=0){	
		        //返回客户端结果
		        String result = getResponseResult(200,badTrackingList);

		        //将result返回客户端
		        response.getWriter().print(result);
				
			}else{
				if(pack.quantity!=quantity){

					if(EbizSql.getInstance().updatePackageQuantityAndPrice(currentUser,currentCompany,pack,quantity,price)){
						pack.quantity=quantity;
						pack.price=price;
						message=message+"Quantity Update Sucessfully \n";
					}else{
						message=message+"Quantity Update Failed, Please Try Again \n";
					}
				}
				if(!pack.creditcardNumber.equals(creditcard)){
					
					if(EbizSql.getInstance().updatePackageCreditCard(currentUser,currentCompany,pack.UID,creditcard)){
						pack.creditcardNumber=creditcard;
						message=message+"Creditcard Information Updated Sucessfully;\n";
					}else {
						message=message+"CreditCard Information Update Failed, Please Try Again \n";
					}
				}			
				String temMessageString="";
				if(!pack.getStatus().equals(EbizPackageStatusEnum.Shipped.getColumnName())){
					pack.setStatus(EbizPackageStatusEnum.Shipped.getColumnName());
					temMessageString=temMessageString+"Status Updated Sucessfully\n";
				}
				if(!pack.trackingNumber.equals(allTrackingNumber)){
					pack.trackingNumber=allTrackingNumber;
					temMessageString=temMessageString+"Tracking Number Updated Sucessfully\n";
				}
				
				if(temMessageString.length()!=0){
					if(EbizSql.getInstance().updatePackageTrackingAndStatus(pack)){
						message=message+temMessageString+"\n";
						
					}else {
						message=message+"Update Failed, Please Try Again;\n";
					}
				}else {
					message=message+"Nothing Updated;\n";
				}
				response.getWriter().print(message);
			}
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
    public static String getResponseResult(int status,List<String> message){
    	String re="";
    	for (int i=0;i<message.size();i++){
    		re=re+message.get(i)+"\n";
    	}
    	re=re+"Above tracking have been reported, Please check or contact admistrator";
        return  re;
    }
    public static int count(String s, String key) {  
    	  int count=0;  
    	  int d=0;  
    	  while((d=s.indexOf(key,d))!=-1){  
    	      s=s.substring(d+key.length());  
    	      count++;  
    	  }      
    	    return count;  
    	} 

}