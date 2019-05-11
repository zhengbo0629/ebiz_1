package ebizConcept;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.annotation.WebServlet;

import dataCenter.EbizSql;
import ebizTools.GeneralMethod;
import nameEnum.EbizUserParaMeterEnum;
import nameEnum.EbizUserPermissionEnum;
import nameEnum.EbizUserTypeEnum;
import net.bytebuddy.asm.Advice.This;
@WebServlet("/EbizUser")
public class EbizUser{
	public Integer UID;
	public String userName;
	public String firstName;
	public String lastName;
	public String companyName;
	public String passWord;
	public String tempPassWord;
	private String email; // used to save all email have been used by this user;
	public String phoneNumber;
	public String address;
	public String createTime;
	public String updateTime;
	public String note;
	private String status;
	private String permissionString;
	private String userType;
	public double balance;
	public boolean showDialogWhenUpdate=true;
	private HashMap<String, String> parametersMap=new HashMap<String, String>();

	public int personalLimit;
	public HashSet<String> userPermissions=new HashSet<String>();

	public EbizUser() {
		
	}
	public EbizUser(Integer UID,String userName,String firstName,String lastName,String companyName, String passWord, String tempPassWord, String email, String phoneNumber, String address,String createTime,
			String updateTime,String note,String status,String userType, double balance,Integer personalLimit,String parameterString ) {
		super();
		this.UID=UID;
		this.userName = userName;
		this.firstName=firstName;
		this.lastName=lastName;
		this.companyName=companyName;
		this.passWord = passWord;
		this.tempPassWord = tempPassWord;
		this.email = email.replace(" ", "");
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.note = note;
		this.status=status;
		this.userType=userType;
		this.personalLimit=personalLimit;
		this.balance=balance;
		setUserPermissions(status);
		if (parameterString != null) {
			String[] parameter = parameterString.split("\n");

			for (int i = 0; i < parameter.length; i++) {
				String[] temp = parameter[i].split("=");
				if (temp.length==2){
					parametersMap.put(temp[0], temp[1]);
				}
			}
		}
		
	}
	   public static Comparator<EbizUser> UID_COMPARATOR = new Comparator<EbizUser>() {
		      @Override
		      public int compare(final EbizUser o1, final EbizUser o2) {
		         return Integer.valueOf(o2.UID).compareTo(o1.UID);
		      }
		   };


	public String toString(){
		return 	UID+";"
				+userName+";"
				+firstName+";"
				+lastName+","
				+companyName+";"
				+passWord+";"
				+tempPassWord+";"
				+email+";"
				+phoneNumber+";"
				+address+";"
				+createTime+";"
				+updateTime+";"
				+note+";"
				+status+";"
				+userType+";"
				+personalLimit+";"
				+getParametersString();
		
	}
	public String getParametersString(){
		String string="";
		for(String key:parametersMap.keySet()){
			string=string+key+"="+parametersMap.get(key)+"\n";
		}
		if (string.endsWith("\n")){
			string=string.substring(0,string.length()-1);
		}
		return string;
	}

	public void setParameter(String name, String value){
		parametersMap.put(name, value);
	}
	public void setParameterString(String parameterString){
		if (parameterString != null) {
			String[] parameter = parameterString.split("\n");

			for (int i = 0; i < parameter.length; i++) {
				String[] temp = parameter[i].split("=");
				if (temp.length==2){
					parametersMap.put(temp[0], temp[1]);
				}
			}
		}
	}
	public String getParameter(String name){
		return parametersMap.get(name);
	}
	public boolean updateLoginCounter(){
		if (parametersMap.get(EbizUserParaMeterEnum.LoginCounter.getName())==null){
			parametersMap.put(EbizUserParaMeterEnum.LoginCounter.getName(), Integer.toString(1));
		}else {
			int logingCounter=Integer.parseInt(parametersMap.get(EbizUserParaMeterEnum.LoginCounter.getName()));
			parametersMap.put(EbizUserParaMeterEnum.LoginCounter.getName(), Integer.toString(logingCounter+1));
		}
		parametersMap.put(EbizUserParaMeterEnum.LastLoginTime.getName(), GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000));
		return EbizSql.getInstance().updateUserParameter(this,null,this.UID, getParametersString());
	}

	public String getUserName(){
		return userName;
	}
	public Integer getUID() {
		return UID;
	}
	public void setUID(Integer uID) {
		UID = uID;
	}
	public void setUserPermissions(String permissionString){
		this.permissionString=permissionString;
		userPermissions.clear();
		if(permissionString==null) return;
		String[] strings=permissionString.split(",");
		for(int i=0;i<strings.length;i++){
			if(strings[i].length()>0){
				userPermissions.add(strings[i]);
			}
		}
		
	}
	public void addUserPermissions(String permission){
		if (!userPermissions.contains(permission)){
			userPermissions.add(permission);
			permissionString=permissionString+","+permission;
		}
		permissionString=permissionString.trim();
	}
	public String getPermissionString(){
		return permissionString;
	}
	public HashSet<String> getUserPermissions(){
		return userPermissions;
	}
	// get the current email added by user;
	public String getEmail(){
		String[] emailStrings=email.split("\n");
		return emailStrings[emailStrings.length-1]; 
	}
	public String getEmailString(){
		
		return email;
	}
	// if this is a new email, add it to the end of the email string
	public void setEmail(String newEmail){
			email=newEmail;
	}
	public boolean isActive(){
		if(status==null){
			return false;
		}
		return status.equals(EbizUserPermissionEnum.Active.getName());
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus(){
		return status;
	}
	public String getAddress(){
		return address;
	}
	public String getUserType(){
		return userType;
	}
	public boolean isDoctor(){
		return userType.equals(EbizUserTypeEnum.Doctor.getName());
	}
	public boolean isSelfEmployedDoctor(){
		return userType.equals(EbizUserTypeEnum.SelfEmployedDoctor.getName());
	}
	public boolean isNurese(){
		return userType.toLowerCase().contains(EbizUserTypeEnum.Nurse.getName().toLowerCase());
	}
	public boolean isOverSeaBuyer(){
		return  userType.equals(EbizUserTypeEnum.Oversea_Buyer.getName());
	}
	public void setUserType(String userType){
		this.userType=userType;
	}


}
