package ebizConcept;

import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.TimeZone;
import ebizTools.GeneralMethod;
import nameEnum.EbizCompanyAddressEnum;
import nameEnum.EbizCompanyPayPeriodEnum;

public class EbizCompany {
	public Integer UID;
	public String companyName;
	public String ownerName;
	public String createdTime;
	public String status;
	private String permissions;
	public double balance;
	public String note;
	private String payTimeInfor;
	private String userManual;
	private String addressString;
	private String emailAndPassword;
	private String phoneNumber;
	public HashSet<String> userPermissions=new HashSet<String>();
	


	public EbizCompany(String string) {
		super();
		String[] eles = string.split(",");
		UID = Integer.parseInt(eles[0]);
		this.companyName = eles[1];
		this.ownerName = eles[2];
		this.createdTime = eles[3];
		this.status = eles[4];
		this.note = eles[5];

	}

	public EbizCompany(String companyName, String ownerName, String status, String createTime, String permissions,double balance,String note) {
		super();
		this.companyName = companyName;
		this.ownerName = ownerName;
		this.status = status;
		this.createdTime = createTime;
		this.permissions=permissions;
		this.balance=balance;
		this.note = note;

	}
	public EbizCompany() {
		super();
	}

	public EbizCompany(Integer UID, String companyName, String ownerName, String status, String createTime, String permissions,double balance,
			String note) {
		super();
		this.UID = UID;
		this.companyName = companyName;
		this.ownerName = ownerName;
		this.status = status;
		this.createdTime = createTime;
		this.permissions=permissions;
		this.balance=balance;
		this.note = note;
	}

	public static Comparator<EbizCompany> UID_COMPARATOR = new Comparator<EbizCompany>() {
		@Override
		public int compare(final EbizCompany o1, final EbizCompany o2) {
			return Integer.valueOf(o2.UID).compareTo(o1.UID);
		}
	};

	public String toString() {
		return UID + ";" + companyName + ";" + ownerName + ";" + createdTime + ";" + status + ";" + note;

	}

	public Integer getUID() {
		return UID;
	}

	public void setUID(Integer uID) {
		UID = uID;
	}

	public String getPayTimeInfor() {
		return this.payTimeInfor;
	}

	public void setPayTimeInfor(String payTimeInfor) {
		this.payTimeInfor = payTimeInfor;
	}

	public String getAddressNameByAddress(String address) {
		if (address.equals(EbizCompanyAddressEnum.Address1.getName())) {
			return getAddress1Name();
		} else if (address.equals(EbizCompanyAddressEnum.Address2.getName())) {
			return getAddress2Name();
		} else if (address.equals(EbizCompanyAddressEnum.Address3.getName())) {
			return getAddress3Name();
		}
		return address;

	}
	public void setAddressString(String addressString) {
		if(addressString!=null&&addressString.length()!=0){
			this.addressString = addressString;
		}
	}

	public void setAddress1String(String address1) {
		if(address1==null||address1.length()==0) return;
		if(!addressString.contains("address::1")){
			addressString=addressString+";"+address1;
		}
		else {
			String address[]=addressString.split(";");
			String temp = "";
			for (int i = 0; i < address.length; i++) {
				if(address[i].contains("address::1")){
					address[i]=address1;
				}
				temp = temp + address[i] + ";";
			}
			if (temp.endsWith(";")) {
				temp = temp.substring(0, temp.length() - 1);
			}
			addressString = temp;
		}

	}
	public String getAddress1Name(){
		if(addressString==null||!addressString.contains("address::1")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::1")){
				String[]  elemets=temp[i].split("=");
				if(elemets.length>1){
					return elemets[1].trim();
				}else {
					return "";
				}
			}
		}
		return"";
	}
	
	public String getAddress1String(){
		if(addressString==null||!addressString.contains("address::1")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::1")){
				return temp[i];
			}
		}
		return"";
	}
	public String getAddress2Name(){
		if(addressString==null||!addressString.contains("address::2")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::2")){
				String[]  elemets=temp[i].split("=");
				if(elemets.length>1){
					return elemets[1].trim();
				}else {
					return "";
				}
			}
		}
		return"";
	}
	public String getAddress2String(){
		if(addressString==null||!addressString.contains("address::2")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::2")){
				return temp[i];
			}
		}
		return"";
	}
	public String getAddress3Name(){
		if(addressString==null||!addressString.contains("address::3")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::3")){
				String[]  elemets=temp[i].split("=");
				if(elemets.length>1){
					return elemets[1].trim();
				}else {
					return "";
				}
			}
		}
		return"";
	}
	public String getAddress3String(){
		if(addressString==null||!addressString.contains("address::3")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::3")){
				return temp[i];
			}
		}
		return"";
	}
	public String getAddress1(){
		if(addressString==null||!addressString.contains("address::1")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::1")){
				String[]  elemets=temp[i].split("=");
				if(elemets.length>2){
					return elemets[2].trim();
				}else {
					return "";
				}
			}
		}
		return"";
	}
	public String getAddress2(){
		if(addressString==null||!addressString.contains("address::2")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::2")){
				String[]  elemets=temp[i].split("=");
				if(elemets.length>2){
					return elemets[2].trim();
				}else {
					return "";
				}
			}
		}
		return"";
	}
	public String getAddress3(){
		if(addressString==null||!addressString.contains("address::3")){
			return "";
		}
		String[] temp=addressString.split(";");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("address::3")){
				String[]  elemets=temp[i].split("=");
				if(elemets.length>2){
					return elemets[2].trim();
				}else {
					return "";
				}
			}
		}
		return"";
	}
	public String getAddressString(){
		return addressString.trim();
	}

	public void setAddress2String(String address2) {
		if(address2==null||address2.length()==0) return;
		if(!addressString.contains("address::2")){
			addressString=addressString+";"+address2;
		}
		else {
			String address[]=addressString.split(";");
			String temp = "";
			for (int i = 0; i < address.length; i++) {
				if(address[i].contains("address::2")){
					address[i]=address2;
				}
				temp = temp + address[i] + ";";
			}
			if (temp.endsWith(";")) {
				temp = temp.substring(0, temp.length() - 1);
			}
			addressString = temp;
		}

	}

	public void setAddress3String(String address3) {
		if(address3==null||address3.length()==0) return;
		if(!addressString.contains("address::3")){
			addressString=addressString+";"+address3;
		}
		else {
			String address[]=addressString.split(";");
			String temp = "";
			for (int i = 0; i < address.length; i++) {
				if(address[i].contains("address::3")){
					address[i]=address3;
				}
				temp = temp + address[i] + ";";
			}
			if (temp.endsWith(";")) {
				temp = temp.substring(0, temp.length() - 1);
			}
			addressString = temp;
		}

	}

	public String getPayPeriod() {
		if (payTimeInfor == null || !payTimeInfor.contains(":")) {
			return null;
		} else {
			return payTimeInfor.split(":")[0];
		}

	}

	public int getPayYear() {
		if (payTimeInfor == null || !payTimeInfor.contains(":")) {
			return 0;
		} else {
			String[] strings = payTimeInfor.split(":");
			String[] yearmonthday = strings[1].split("-");
			int year = Integer.parseInt(yearmonthday[0]);
			return year;
		}

	}

	public int getPayMonth() {
		if (payTimeInfor == null || !payTimeInfor.contains(":")) {
			return 0;
		} else {
			String[] strings = payTimeInfor.split(":");
			String[] yearmonthday = strings[1].split("-");
			int month = Integer.parseInt(yearmonthday[1]);
			return month;
		}

	}

	public int getPayDay() {
		if (payTimeInfor == null || !payTimeInfor.contains(":")) {
			return 0;
		} else {
			String[] strings = payTimeInfor.split(":");
			String[] yearmonthday = strings[1].split("-");
			int day = Integer.parseInt(yearmonthday[2]);
			return day;
		}

	}
	public String getEmail() {
		if(emailAndPassword==null){
			return "";
		}
		if(emailAndPassword.split(" ").length>0){
			return emailAndPassword.split(" ")[0].replace("email=", "").trim();
		}else{
			return "";
		}
	}
	public String getEmailPassword() {
		if(emailAndPassword==null){
			return "";
		}
		if(emailAndPassword.contains(" ")&&emailAndPassword.split(" ").length>1){
			return emailAndPassword.split(" ")[1].replace("password=", "").trim();
		}else{
			return "";
		}
	}

	public void setEmailAndPasswordString(String emailAndPassword) {
		this.emailAndPassword = emailAndPassword;
	}
	public String getEmailAndPasswordString(){
		return emailAndPassword;
	}

	public String getPhoneNumber() {
		if(phoneNumber==null){
			return "";
		}
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserManual() {
		if (userManual == null) {
			return "";
		}
		return userManual;
	}
	public void setPermissions(String permissions){
		this.permissions=permissions;
		userPermissions.clear();
		if(permissions==null) return;
		String[] strings=permissions.split(",");
		for(int i=0;i<strings.length;i++){
			if(strings[i].length()>0){
				userPermissions.add(strings[i]);
			}
		}
	}
	public void addPermissions(String permission){
		if (!userPermissions.contains(permission)){
			userPermissions.add(permission);
			permissions=permissions+","+permission;
		}
	}
	public HashSet<String> getPermissions(){
		return userPermissions;
	}
	public String getPermissionString(){
		return permissions;
	}

	public void setUserManual(String userManual) {
		this.userManual = userManual;
	}
	public String getPayTimeString() {
		if(payTimeInfor==null){
			return null;
		}
		if (!payTimeInfor.contains(":"))
			return null;
		
		String[] strings = payTimeInfor.split(":");
		String periodString=strings[0];
		String[] yearmonthday = strings[1].split("-");
		int year = Integer.parseInt(yearmonthday[0]);
		int month = Integer.parseInt(yearmonthday[1]);
		int date = Integer.parseInt(yearmonthday[2]);

		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar now = new GregorianCalendar();

		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		now.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.set(year, month-1, date);
		now.set(GregorianCalendar.HOUR, 0);
		now.set(GregorianCalendar.MINUTE, 0);
		now.set(GregorianCalendar.SECOND, 0);

		if (strings[0].equals(EbizCompanyPayPeriodEnum.TwoMonth.getName())) {
			while (!gc.after(now)) {
				gc.add(GregorianCalendar.MONTH, 2);
			}
		} else if (strings[0].equals(EbizCompanyPayPeriodEnum.SixWeeks.getName())) {

			while (!gc.after(now)) {
				gc.add(GregorianCalendar.WEEK_OF_YEAR, 6);
			}
		} else if (strings[0].equals(EbizCompanyPayPeriodEnum.FiveWeeks.getName())) {

			while (!gc.after(now)) {
				gc.add(GregorianCalendar.WEEK_OF_YEAR, 5);
			}
		} else if (strings[0].equals(EbizCompanyPayPeriodEnum.OneMonth.getName())) {

			while (!gc.after(now)) {
				gc.add(GregorianCalendar.MONTH, 1);
			}
		} else if (strings[0].equals(EbizCompanyPayPeriodEnum.FourWeeks.getName())) {
			while (!gc.after(now)) {
				gc.add(GregorianCalendar.WEEK_OF_YEAR, 4);
			}
		} else if (strings[0].equals(EbizCompanyPayPeriodEnum.ThreeWeeks.getName())) {
			while (!gc.after(now)) {
				gc.add(GregorianCalendar.WEEK_OF_YEAR, 3);
			}
		} else if (strings[0].equals(EbizCompanyPayPeriodEnum.TwoWeeks.getName())) {
			while (!gc.after(now)) {
				gc.add(GregorianCalendar.WEEK_OF_YEAR, 2);
			}
		} else if (strings[0].equals(EbizCompanyPayPeriodEnum.OneWeek.getName())) {
			while (!gc.after(now)) {
				gc.add(GregorianCalendar.WEEK_OF_YEAR, 1);
			}
		}
		return "Pay Every "+periodString+": Next Payment Date: (M-D-Y) " + GeneralMethod.pad(gc.get(GregorianCalendar.MONTH)+1) + "-"
				+ GeneralMethod.pad(gc.get(GregorianCalendar.DAY_OF_MONTH)) + "-"
				+ GeneralMethod.pad(gc.get(GregorianCalendar.YEAR));
	}

}
