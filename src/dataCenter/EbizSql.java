package dataCenter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gargoylesoftware.htmlunit.javascript.host.fetch.Request;

import ebizConcept.EbizCompany;
import ebizConcept.EbizDispatcher;
import ebizConcept.EbizInventory;
import ebizConcept.EbizModelListener;
import ebizConcept.EbizOperationRecord;
import ebizConcept.EbizPackage;
import ebizConcept.EbizProduct;
import ebizConcept.EbizUser;
import ebizConcept.EbizWarehouseOrder;
import ebizTools.GeneralMethod;
import nameEnum.EbizCompanyAddressEnum;
import nameEnum.EbizOperationNameEnum;
import nameEnum.EbizPackageCheckStatusEnum;
import nameEnum.EbizPackageColumnEnum;
import nameEnum.EbizPackageLabelStatusEnum;
import nameEnum.EbizPackagePayStatusEnum;
import nameEnum.EbizPackageStatusEnum;
import nameEnum.EbizProductColumnEnum;
import nameEnum.EbizUserPermissionEnum;
import nameEnum.EbizUserTypeEnum;
import nameEnum.EbizStatusEnum;
import nameEnum.EbizInventoryColumnEnum;;

public class EbizSql extends HttpServlet implements EbizModelListener  {

	public static EbizCompany company;
	public static EbizSql instance;
	public static String productListName="ProductList";
	public static String warehouseProductListName="WarehouseProductList";
	public static String packageListName="PackageList";
	public static String warehouseReceivedPackageListName="ReceivedPackageList";
	public static String buyerPackageListName="BuyerPackageList";
	public static String inventoryListName="InventoryList";
	public static String warehouseOrderListName="WarehouseOrderList";
	public static String SNListName="SNList";
	public static String userListName="EbizUser";
	public static String companyListName="EbizCompany";
	public static String dealListName="DealList";
	public static String operationRecordListName="OperationRecord";
	public static String masterUserNameString="goNewLife";
	//public static String hostString="jdbc:mysql://ebizmanager.cymt3eebqxic.us-west-2.rds.amazonaws.com:3306/myWarehouse?autoReconnect=true&useSSL=false";	
//原始
	//public static String hostString="jdbc:mysql://www.eastebiz.com:3306/myWarehouse?autoReconnect=true&useSSL=false";		
	  public static String hostString="jdbc:mysql://132.148.245.32:3306/test002mywarehouse?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";		
	
	
	public int loadrowNumber=800;
	public static String dataFileDri=System.getProperty("user.dir") +"\\allFiles\\";
	public String dataBackupFileDri=System.getProperty("user.dir") +"\\backUpAllFiles\\";
	public ConcurrentHashMap<Integer, EbizProduct> deals=new ConcurrentHashMap<Integer, EbizProduct>();
	public ConcurrentHashMap<Integer, EbizPackage> buyerPackages=new ConcurrentHashMap<Integer, EbizPackage>();
	public ConcurrentHashMap<String, EbizInventory> inventorys=new ConcurrentHashMap<String, EbizInventory>();
	public ConcurrentHashMap<Integer, EbizWarehouseOrder> warehouseOrders=new ConcurrentHashMap<Integer, EbizWarehouseOrder>();
	public ConcurrentHashMap<String, EbizUser> ebizUsers=new ConcurrentHashMap<String, EbizUser>();
	private static int tryconnectionCounter=0;
	private static int tryConnectionMaximumCount=3;
	public EbizSql() {
    	EbizDispatcher.getInstance().addListener(this);
	}
	public static synchronized EbizSql getInstance(){
		if (instance==null){
			instance= new EbizSql();
		}
			return instance;
	}
	public boolean isAdministrator(EbizUser user){
		return user.userName.toLowerCase().equals(masterUserNameString.toLowerCase());
	}
	  @Override
		public void modelChanged(Event event, Object value) {
			switch (event) {
			case updateUser:
				
				break;
			case Close:
				break;
			default:
				break;
			}
	    }
	  public boolean isWebPackManager(){
		  return hostString.contains("myWarehouse");
	  }
		
	public static void wirteLog(String string){
		
		//String time=GeneralMethod.getTimeStringForMillis(System.currentTimeMillis());
	}
	
	  public void writeAllDatas(){

			// since the program write data frequently. To save time, the data is
			// not sorted. however, data is sorted when they are read
			List<String> strings = new ArrayList<String>();
			GeneralMethod.writestrings(dataFileDri+productListName+".txt", strings, false);
			GeneralMethod.writestrings(dataBackupFileDri+productListName+".txt", strings, false);
			strings.clear();
			GeneralMethod.writestrings(dataFileDri+packageListName+".txt", strings, false);
			GeneralMethod.writestrings(dataBackupFileDri+packageListName+".txt", strings, false);
			strings.clear();
			for (EbizUser temp : ebizUsers.values()) {
				strings.add(temp.toString());
			}
			GeneralMethod.writestrings(dataFileDri+userListName+".txt", strings, false);
			GeneralMethod.writestrings(dataBackupFileDri+userListName+".txt", strings, false);
			strings.clear();
			for (EbizPackage temp : buyerPackages.values()) {
				strings.add(temp.toString());
			}
			GeneralMethod.writestrings(dataFileDri+buyerPackageListName+".txt", strings, false);
			GeneralMethod.writestrings(dataBackupFileDri+buyerPackageListName+".txt", strings, false);
			strings.clear();
			for (EbizInventory temp : inventorys.values()) {
				strings.add(temp.toString());
			}

			GeneralMethod.writestrings(dataFileDri+inventoryListName+".txt", strings, false);
			GeneralMethod.writestrings(dataBackupFileDri+inventoryListName+".txt", strings, false);
			strings.clear();
			for (EbizWarehouseOrder temp : warehouseOrders.values()) {
				strings.add(temp.toString());
			}

			GeneralMethod.writestrings(dataFileDri+warehouseOrderListName+".txt", strings, false);
			GeneralMethod.writestrings(dataBackupFileDri+warehouseOrderListName+".txt", strings, false);
	  }
	public String getMasterUserName(){
		return masterUserNameString;
	}
	
	public boolean isMasterUser(String userName){
		return (userName.toLowerCase().equals(masterUserNameString.toLowerCase()));
	}
	  
	public void setCompany(EbizCompany company){
		EbizSql.company=company;
	}
	public EbizCompany getCompany(){
		return company;
	}

	public ConcurrentHashMap<Integer, EbizPackage> getBuyerPackages() {
		return buyerPackages;
	}
	
	public boolean createSNTable(){
		wirteLog("Creating SNTable:"+SNListName);
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " +SNListName
	            + " (id INTEGER,"
	            + " SNString  VARCHAR(3000),"
	            + " PRIMARY KEY  (id))";

	    Statement stmt;
	    Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, SNListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
				return false;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				
				return createSNTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
			
		}finally {
			CloseConnection(connection,0);
		}	
	}
	public boolean createBuyerPackageTable() {
	    String sqlCreate =createPackageTableString(buyerPackageListName);
	    Statement stmt;
	    Connection connection = null;
		try {
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, buyerPackageListName, null);

			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("BuyerPakcageTable created");
				return true;
			} else {
				tryconnectionCounter=0;
				return false;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return createBuyerPackageTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
		}finally{
			CloseConnection(connection,0);
		}
	}
	private static String createPackageTableString(String packageListName) {
		wirteLog("Creating PackageTable:"+packageListName);
		return	  "CREATE TABLE IF NOT EXISTS " +packageListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " CompanyName  VARCHAR(50),"
	            + " TrackingNumber  VARCHAR(700),"
	            + " ShipID     VARCHAR(150),"
	            + " ModelNumber     VARCHAR(50),"
	            + " ProductName     VARCHAR(300),"
	            + " ProductCondition 		VARCHAR(20),"
	            + " UPC     		VARCHAR(30),"
	            + " ASIN     		VARCHAR(50),"
	            + " SKU     		VARCHAR(50),"
	            + " Brand			VARCHAR(30),"
	            + " Price			DOUBLE,"
	            + " BasePrice		DOUBLE,"
	            + " PromPrice		DOUBLE,"
	            + " Quantity		INTEGER,"
	            + " SerialNumber	VARCHAR(500),"
	            + " PromQuantity		INTEGER,"
	            + " StoreName		VARCHAR(30),"
	            + " UserName		VARCHAR(30),"
	            + " ShippingAddress	VARCHAR(150),"
	            + " Email			VARCHAR(50),"
	            + " PhoneNumber		VARCHAR(20),"
	            + " Receiver		VARCHAR(30),"
	            + " Note			VARCHAR(300),"
	            + " CreatedTime		VARCHAR(30),"
	            + " UpdateTime		VARCHAR(30),"
	            + " CreditCardNumber		VARCHAR(300),"
	            + " Status			VARCHAR(30),"
	            + " CheckStatus			VARCHAR(20),"
	            + " Checker			VARCHAR(30),"
	            + " LabelStatus			VARCHAR(20),"
	            + " Labeler			VARCHAR(30),"
	            + " PayStatus			VARCHAR(20),"
	            + " Payer			VARCHAR(30),"
	            + " UserNote			VARCHAR(500),"
	            + " PRIMARY KEY  (id))";
	}

	public boolean createCompanyTable() {
		wirteLog("Creating CompnayTable:"+companyListName);
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " +companyListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " CreatedTime  VARCHAR(30),"
	            + " UpdateTime  VARCHAR(30),"
	            + " CompanyName  VARCHAR(50),"
	            + " OwnerName  VARCHAR(30),"
	            + " Status  VARCHAR(20),"
	            + " Permision  VARCHAR(400)," //营业锟斤拷围,只锟叫癸拷司锟斤拷锟斤拷营锟侥ｏ拷锟脚伙拷锟斤拷示锟斤拷医锟斤拷锟斤拷锟斤拷权锟斤拷围锟斤拷锟节★拷
	            + " Balance  Double,"
	            + " Note  VARCHAR(400),"
	            + " PayTimeInfor  VARCHAR(200),"
	            + " Address  VARCHAR(400),"
	            + " Email  VARCHAR(100),"
	            + " PhoneNumber		VARCHAR(20),"
	            + " UserManual  VARCHAR(10000),"
	            + " PRIMARY KEY  (id) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci')";

	    Statement stmt;
	    Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt =connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, companyListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
			return false;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				
				return createCompanyTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
		}finally{
			CloseConnection(connection,0);
		}
	}
	public boolean addAddOperationRecord(EbizUser user,EbizCompany company,String listName, int uid){
		
    	   EbizOperationRecord record=new EbizOperationRecord();
    	   record.userName=user.userName;
    	   record.company=user.companyName;
    	   record.operationName=EbizOperationNameEnum.AddRow.getName();
    	   record.tableName=listName;
    	   record.rowId=uid;
    	   record.timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
    	   return addOperationRecord(record);
	}
	public boolean addUpdateOperationRecord(EbizUser user,EbizCompany company,String listName, String columnName,int uid,String value){
		
 	   EbizOperationRecord record=new EbizOperationRecord();
 	   record.userName=user.userName;
 	   record.company=user.companyName;
 	   record.operationName=EbizOperationNameEnum.UpdateColumn.getName();
 	   record.tableName=listName;
 	   record.rowId=uid;
 	   record.columnName=columnName;
 	   record.timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
 	   record.newValue=value;
 	   return addOperationRecord(record);
	}
	
	public boolean addOperationRecord(EbizOperationRecord record){	
        PreparedStatement pst;
        Statement stmt;
        Connection con=null;
        if (record.newValue!=null&&record.newValue.length()>=5000){
        	record.newValue=record.newValue.substring(1, 4800);
        }
        try {
			con =  EbizDataBase.getConnection();
			pst = con.prepareStatement("insert into " + operationRecordListName
					+ " (UserName,CompanyName,OperationName,TableName,ColumnName,RowId,"
					+"OperationStatus,OldValue,NewValue,TimeString,Discription,Note) "
					+"values(?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, record.userName);
			pst.setString(2, record.company);
			pst.setString(3, record.operationName);
			pst.setString(4, record.tableName);
			pst.setString(5, record.columnName);
			pst.setInt(6, record.rowId);
			pst.setString(7, record.operationStatus);
			pst.setString(8, record.oldValue);
			pst.setString(9, record.newValue);
			pst.setString(10, record.timeString);
			pst.setString(11, record.discription);
			pst.setString(12, record.note);
			wirteLog(pst.toString());
           if ( pst.executeUpdate()>0){
        	   stmt = con.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+userListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        		   record.id=lastid;
        		   return true;
        	   }
        	  return true;
           }else {
        	   return false;
           }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }finally{
        	CloseConnection(con,tryconnectionCounter);
        }
		return false;
	}
	public static boolean createOperationRecordTable() {
		wirteLog("Creating OperationRecord:"+operationRecordListName);
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " +operationRecordListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " UserName  VARCHAR(30),"
	            + " CompanyName  VARCHAR(30),"
	            + " OperationName  VARCHAR(30),"
	            + " TableName  VARCHAR(30),"
	            + " ColumnName  VARCHAR(30),"
	            + " RowId  MEDIUMINT(9),"
	            + " OperationStatus  VARCHAR(30),"
	            + " OldValue  VARCHAR(5000),"
	            + " NewValue  VARCHAR(5000),"
	            + " TimeString     VARCHAR(100),"
	            + " Discription     VARCHAR(300),"
	            + " Note     VARCHAR(300),"
	            + " PRIMARY KEY  (id))";
	    Statement stmt;
	    Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, userListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
			return false;
			}
		} catch (SQLException e) {
			//CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				
				return createOperationRecordTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
		}finally {
			//CloseConnection(connection,0);
		}
	    
	}
	public boolean createUserTable() {
		wirteLog("Creating userTable:"+userListName);
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " +userListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " UserName  VARCHAR(30),"
	            + " FirstName  VARCHAR(30),"
	            + " LastName  VARCHAR(30),"
	            + " CompanyName  VARCHAR(50),"
	            + " PassWord  VARCHAR(30),"
	            + " TempPassWord     VARCHAR(30),"
	            + " Email     VARCHAR(500),"
	            + " PhoneNumber     VARCHAR(300),"
	            + " Address 		VARCHAR(150),"
	            + " CreateTime     		VARCHAR(30),"
	            + " UpdateTime     		VARCHAR(20),"
	            + " Note			VARCHAR(300),"
	            + " Status			VARCHAR(400)," 
	            + " UserType     VARCHAR(300),"
	            + " Permissions			VARCHAR(600)," 
	            + " Balance			Double,"
	            + " PersonalLimit	INTEGER,"
	            + " ParameterString	VARCHAR(500),"
	            + " PayTimeInfor			VARCHAR(200),"
	            + " OperatingStatus			VARCHAR(200),"
	            + " OperationRecord			VARCHAR(2000),"
	            + " ActivityRecord			VARCHAR(10000),"
	            + " PRIMARY KEY  (id))";
	    Statement stmt;
	    Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, userListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
			return false;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				
				return createUserTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
		}finally {
			CloseConnection(connection,0);
		}
	    
	}
	
	
	public boolean createReceivedPackageTable() {
	    String sqlCreate =createPackageTableString(warehouseReceivedPackageListName);
	    Statement stmt;
	    Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, warehouseReceivedPackageListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
			return false;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				
				return createReceivedPackageTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
		}finally{
			CloseConnection(connection,0);
		}	    
	}

	public boolean createPackageTable() {
	    String sqlCreate =createPackageTableString(packageListName);
	    Statement stmt;
	    Connection connection=null;
		try {
			
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, packageListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
			return false;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return createPackageTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
		}finally{
			CloseConnection(connection,0);
		}	    
	}
	public boolean deleteTable(String tableName){
		PreparedStatement dropTable;
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			dropTable = connection.prepareStatement(
					String.format("DROP TABLE IF EXISTS "+ tableName));
			 boolean re=dropTable.execute();
			 tryconnectionCounter=0;
			 return re;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return deleteTable(tableName);
			}

			e.printStackTrace();
			return false;
		}finally{
			CloseConnection(connection,0);
		}
		
				
	}
	public boolean deleteRow(EbizUser user,EbizCompany company,String tableName, int uid){
		return updateValue(user, company, uid, tableName, "Status", EbizStatusEnum.Deleted.getName());	  
	}
	public boolean deleteProduct(EbizUser user,EbizCompany company,int uid){
		return updateValue(user, company, uid, productListName, "Status", EbizStatusEnum.Deleted.getName());

	}
	public boolean deleteDeal(EbizProduct product){
		boolean re;
		product.status=EbizStatusEnum.Deleted.getName();
		re=updateDealStatus(product);
		if(re){
			deals.remove(product.getUID());
		}
		return re;
	}
	public boolean deleteBuyerPackage(EbizUser user,EbizCompany company,EbizPackage pack){
		boolean re= deleteRow(user,company,buyerPackageListName, pack.UID);

		return re;
	}
	public boolean deleteCompany(EbizUser user,EbizCompany company,EbizCompany targetCompany){
		boolean re= deleteRow(user,company,companyListName, targetCompany.UID);		
		return re;
	}
	public boolean deleteUser(EbizUser user,EbizCompany company,EbizUser targetUser){
		boolean re= deleteRow(user,company,userListName, targetUser.UID);

		return re;
	}

	public boolean deletePackage(EbizUser user,EbizCompany company,EbizPackage pack){
		return deleteRow(user,company,packageListName, pack.UID);
	}
	public boolean deletePackage(EbizUser user,EbizCompany company,int uid){
		return deleteRow(user,company,packageListName, uid);
	}
	public boolean deleteInventory(EbizUser user,EbizCompany company,EbizInventory inventory){
		boolean re= deleteRow(user,company,inventoryListName, inventory.UID);
		if(re){
			inventorys.remove(inventory.getKeyString());
		}
		return re;		
	}
	
	public boolean DeleteSN(EbizUser user,EbizCompany company,int uid){
		boolean re= deleteRow(user,company,SNListName, uid);
		return re;	
	}
	
	public boolean deleteWareHouseOrder(EbizUser user,EbizCompany company,EbizWarehouseOrder inventoryOrder){
		boolean re= deleteRow(user,company,warehouseOrderListName, inventoryOrder.UID);
		if(re){
			warehouseOrders.remove(inventoryOrder.UID);
		}
		return re;		
	}
	
	public  boolean createDealListTable() {
		wirteLog("Create ProductTable:"+ dealListName);
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + dealListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " CompanyName  VARCHAR(50),"	            
	            + " Model  		VARCHAR(200),"
	            + " ProductName  VARCHAR(300),"
	            + " UPC          VARCHAR(30),"
	            + " ASIN         VARCHAR(100),"
	            + " SKU      	VARCHAR(100),"
	            + " Brand     	VARCHAR(15),"
	            + " Weight     	DOUBLE,"
	            + " Length     	DOUBLE,"
	            + " Width     	DOUBLE,"
	            + " Height     	DOUBLE,"
	            + " Price 		DOUBLE,"
	            + " PromotQuantity		INTEGER,"
	            + " PromotPrice		DOUBLE,"
	            + " WarehousePrice		DOUBLE,"
	            + " WarehousePromotQuantity		INTEGER,"
	            + " WarehousePromotePrice		DOUBLE,"
	            + " Status 		VARCHAR(20),"
	            + " CreatedTime		VARCHAR(30),"
	            + " UpdateTime		VARCHAR(30),"
	            + " Tickets    	INTEGER,"
	            + " LimitPerPerson    	INTEGER,"
	            + " OperatingStatus			VARCHAR(200),"
	            + " OperationRecord			VARCHAR(2000),"
	            + " ParameterString    	VARCHAR(500),"
	            + " URI    	VARCHAR(2000),"
	            + " PRIMARY KEY  (id))";
	    Statement stmt;
	    Connection connection=null;
		try {
			
			connection=EbizDataBase.getConnection();
			stmt =connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, dealListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
				return false;
			}
			
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				
				return createDealListTable();
			}
			creatTablefailed();
			e.printStackTrace();
			return false;
			
		}finally {
			CloseConnection(connection,0);
		}
	}
	
	public boolean createProductTable() {
		wirteLog("Create ProductTable:"+ productListName);
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + productListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " CompanyName  VARCHAR(50),"	            
	            + " Model  		VARCHAR(200),"
	            + " ProductName  VARCHAR(300),"
	            + " UPC          VARCHAR(30),"
	            + " ASIN         VARCHAR(100),"
	            + " SKU      	VARCHAR(100),"
	            + " Brand     	VARCHAR(15),"
	            + " Weight     	DOUBLE,"
	            + " Length     	DOUBLE,"
	            + " Width     	DOUBLE,"
	            + " Height     	DOUBLE,"
	            + " Price 		DOUBLE,"
	            + " PromotQuantity		INTEGER,"
	            + " PromotPrice		DOUBLE,"
	            + " WarehousePrice		DOUBLE,"
	            + " WarehousePromotQuantity		INTEGER,"
	            + " WarehousePromotePrice		DOUBLE,"
	            + " Status 		VARCHAR(20),"
	            + " CreatedTime		VARCHAR(30),"
	            + " UpdateTime		VARCHAR(30),"
	            + " Tickets    	INTEGER,"
	            + " LimitPerPerson    	INTEGER,"
	            + " OperatingStatus			VARCHAR(200),"
	            + " OperationRecord			VARCHAR(2000),"
	            + " ParameterString    	VARCHAR(500),"
	            + " URI    	VARCHAR(2000),"
	            + " UserNote    	VARCHAR(300),"
	            + " PRIMARY KEY  (id))";
	    Statement stmt;
	    Connection connection=null;
		try {
			
			connection=EbizDataBase.getConnection();
			stmt =connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, productListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
				return false;
			}
			
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				
				return createProductTable();
			}
			creatTablefailed();
			e.printStackTrace();
			return false;
			
		}finally {
			CloseConnection(connection,0);
		}
	}
	public boolean createWarehouseProductTable() {
		wirteLog("Create ProductTable:"+ warehouseProductListName);
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + warehouseProductListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " CompanyName  VARCHAR(50),"	            
	            + " Model  		VARCHAR(200),"
	            + " ProductName  VARCHAR(300),"
	            + " UPC          VARCHAR(30),"
	            + " ASIN         VARCHAR(100),"
	            + " SKU      	VARCHAR(100),"
	            + " Brand     	VARCHAR(15),"
	            + " Weight     	DOUBLE,"
	            + " Length     	DOUBLE,"
	            + " Width     	DOUBLE,"
	            + " Height     	DOUBLE,"
	            + " Price 		DOUBLE,"
	            + " PromotQuantity		INTEGER,"
	            + " PromotPrice		DOUBLE,"
	            + " WarehousePrice		DOUBLE,"
	            + " WarehousePromotQuantity		INTEGER,"
	            + " WarehousePromotePrice		DOUBLE,"
	            + " Status 		VARCHAR(20),"
	            + " PRIMARY KEY  (id))";
	    Statement stmt;
	    Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt =connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, productListName, null);
			if (tables.next()) {
				tryconnectionCounter=0;
				wirteLog("Table created");
			  return true;
			}
			else {
				tryconnectionCounter=0;
				return false;
			}
			
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				
				return createWarehouseProductTable();
			}
			creatTablefailed();
			e.printStackTrace();
			return false;
		}finally {
			CloseConnection(connection,0);
		}
	}
	
	//public Ara
	//deal模块
	public ConcurrentHashMap<Integer, EbizProduct> readAlldeals(boolean loadmore,boolean refresh,EbizUser user) {
		//createWarehouseProductTable();
		wirteLog("Reading All Deals");
		int offset=0;
		int rownumber=loadrowNumber;
		if(loadmore){
			offset=deals.size();
			rownumber=loadrowNumber;
		}else if(refresh){
			offset=0;
			rownumber=deals.size();
		}
		offset=0;		//just read all products. 1000 should be enough for three years. written on 20170530
		rownumber=1000;
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection,dealListName,offset,rownumber,user);
			if(!loadmore){
				deals.clear();
			}

			while (rs.next()) {			
				EbizProduct product = rsToEbizProduct(rs);
				if(product.status==null){
					product.status=EbizStatusEnum.Active.getName();
				}
				if(product.status.equals(EbizStatusEnum.Deleted.getName())){
					continue;
				}
				if (deals.get(product.getUID()) == null) {
					deals.put(product.getUID(), product);
				}
			}
			tryconnectionCounter=0;
			wirteLog("All Deals reading done");
			return deals;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(e.getMessage().contains("doesn't exist")){
				createDealListTable();
				return readAlldeals(loadmore,refresh,user);
			}else if(tryconnectionCounter<tryConnectionMaximumCount){
				return readAlldeals(loadmore, refresh,user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	
	

	public EbizProduct findProduct(String companyName,String model) {
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			
			Statement stmt;
			stmt = connection.createStatement();
			String sql = "select * from "+productListName+" where NOT Status='Deleted' AND CompanyName='"+companyName+"' AND Model='"+model+"'"+" ORDER BY id DESC LIMIT "+1;    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷


			while (rs.next()) {
				EbizProduct product = rsToEbizProduct(rs);
				return product;
			}
			tryconnectionCounter=0;
				return null;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return findProduct(companyName,model);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public EbizProduct findProduct(int UID) {
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection,productListName,"id",UID,1);
			while (rs.next()) {
				EbizProduct product = rsToEbizProduct(rs);
				return product;
			}
			tryconnectionCounter=0;
				return null;

		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return findProduct(UID);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizProduct> readAllLiveDealProducs(String companyName) {
		List<EbizProduct> products=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection,productListName,"CompanyName",companyName,"Status",EbizStatusEnum.LiveDeal.getName());
			while (rs.next()) {				
				EbizProduct product = rsToEbizProduct(rs);
				products.add(product);
			}
			tryconnectionCounter=0;
			if (products.size()>0){
				return products;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllLiveDealProducs(companyName);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizProduct> readAllActiveAndAliveDealProducs(String companyName,int offset,int rowNumber) {
		List<EbizProduct> products=new ArrayList<>();
		Connection connection=null;
		try {	
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchAllActiveAndAliveDealProductsSet(connection,companyName,offset,rowNumber);
			while (rs.next()) {				
				EbizProduct product =rsToEbizProduct(rs);
				products.add(product);
			}
			tryconnectionCounter=0;
			if (products.size()>0){
				return products;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllActiveAndAliveDealProducs(companyName,offset,rowNumber);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizProduct> readAllActiveAndAliveDealProducs(String companyName) {
		List<EbizProduct> products=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchAllActiveAndAliveDealProductsSet(connection,companyName);
			while (rs.next()) {				
				EbizProduct product =rsToEbizProduct(rs);
				products.add(product);
			}
			if (products.size()>0){
				return products;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizProduct> readAllNonDeletedProductSet(int offset,int rowNumber,String companyName) {
		List<EbizProduct> products=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchAllNonDeletedProductSet(connection, offset, rowNumber, companyName);
			while (rs.next()) {
				EbizProduct product = rsToEbizProduct(rs);
				products.add(product);
			}
			if (products.size()>0){
				return products;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	
	
	public List<EbizProduct> readAllActiveProducs(EbizUser user) {
		List<EbizProduct> products=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection,productListName,"CompanyName",user.companyName,"Status",EbizStatusEnum.Active.getName());
			while (rs.next()) {		
				EbizProduct product = rsToEbizProduct(rs);
				products.add(product);
			}
			tryconnectionCounter=0;
			if (products.size()>0){
				return products;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllActiveProducs(user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}

	public ConcurrentHashMap<Integer, EbizPackage> readAllBuyerPackages(boolean loadMore,boolean refresh,EbizUser user) {
		return null;
	}
	
	public EbizCompany findCompany(String companyName) {
		wirteLog("find Compnay: "+companyName);
		Connection connection=null;
		try {
			connection = EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection, companyListName, "CompanyName", companyName);
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				EbizCompany company = rsToEbizCompany(rs);
				tryconnectionCounter=0;
				wirteLog("found compnay");
				return company;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return findCompany(companyName);
			}
			if(e.getMessage().contains("doesn't exist")){
				createCompanyTable();
				return findCompany(companyName);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public String getPayTimeInfor(EbizUser user) {        
		Connection connection=null;
		try {
			connection = EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection, "PayTimeInfor",companyListName, "CompanyName",
					user.companyName);
			if (rs == null) {
				return null;
			}
			while (rs.next()) {

				return rs.getString(1);
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return getPayTimeInfor(user);
			}
			if(e.getMessage().contains("doesn't exist")){
				createUserTable();
				return getPayTimeInfor(user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public EbizUser findUser(Integer uid) {
		Connection connection=null;
		try {
			connection = EbizDataBase.getConnection();
			ResultSet rs = searchUserSet(connection,  uid);
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				EbizUser user = rsToEbizUser(rs);
				tryconnectionCounter=0;
				return user;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return findUser(uid);
			}
			if(e.getMessage().contains("doesn't exist")){
				createUserTable();
				return findUser(uid);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public EbizUser findUser(String userName) {	
		Connection connection=null;
		try {
			connection = EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection, userListName, "UserName", userName);
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				EbizUser user = rsToEbizUser(rs);
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}

	public EbizCompany getCompanyForDoctor(EbizUser user) {
		wirteLog("Reading Company");
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchCompanySetForDoctor(connection,user);
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				EbizCompany company = rsToEbizCompany(rs);
				tryconnectionCounter=0;
				wirteLog("Company reading done");
				return company;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return getCompanyForDoctor(user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public ConcurrentHashMap<String, EbizCompany> readAllCompanys(boolean loadMore,boolean refresh,EbizUser user) {
		ConcurrentHashMap<String, EbizCompany> ebizCompanys=new ConcurrentHashMap<String, EbizCompany>();
		wirteLog("Reading Company");
		int offset=0;
		int rownumber=loadrowNumber;
		if(loadMore){
			offset=0;
			rownumber=loadrowNumber;
		}else if(refresh){
			offset=0;
			rownumber=ebizCompanys.size();
		}
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection,companyListName,offset,rownumber,user);
			if (rs==null){
				return null;
			}
			if(!loadMore){
				ebizCompanys.clear();
			}
			while (rs.next()) {
				EbizCompany company = rsToEbizCompany(rs);
				if(ebizCompanys.get(company.companyName.toLowerCase())==null){
					ebizCompanys.put(company.companyName.toLowerCase(), company);
				}
			}
			tryconnectionCounter=0;
			wirteLog("Company reading done");
			return ebizCompanys;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return readAllCompanys(loadMore, refresh,user);
			}
			if(e.getMessage().contains("doesn't exist")){
				createUserTable();
				return readAllCompanys(loadMore,refresh,user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	
	public int reportedNnumberInresentTwodaysForUser(EbizUser user,String model){
		List<EbizPackage> packages=findPackagesInrecentTwoDaysForUser(user, model);
		if (packages==null||packages.size()==0){
			return 0;
		}else{
			int num=0;
			for(int i=0;i<packages.size();i++){
				num=num+packages.get(i).quantity;
			}
			return num;
		}
	}
	public List<String> readActiveNurseEmailRegistratedInlastTwoMonthForCompany(String company) {
		List<String> emaiList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql = "select Email from "+userListName+" where date(CreateTime)>DATE_SUB(NOW(), INTERVAL 3 MONTH) AND Status='Active' AND CompanyName= '"+company+"'"+" AND UserType= '"+EbizUserTypeEnum.Nurse.getName()+"'"+" ORDER BY id DESC";    //要执锟叫碉拷SQL
			ResultSet rs = stmt.executeQuery(sql);	

            
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				emaiList.add(rs.getString("Email"));
			}
			tryconnectionCounter=0;
			wirteLog("All packages read done");
			return emaiList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	
	public List<EbizPackage> findPackagesInrecentTwoDaysForUser(EbizUser user,String model) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchPackageSetInTwodays(connection,packageListName,"UserName",user.getUserName(),"ModelNumber",model);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			tryconnectionCounter=0;
			wirteLog("All packages read done");
			return packages;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			System.out.println("tryconnectionCounter is: "+tryconnectionCounter);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return findPackagesInrecentTwoDaysForUser(user,model);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<String> readMoreThanTenPackagesUserNameInLastTwoMonthForCompany(String companyName) {
		wirteLog("Read packages for User");
		List<String> userNameList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select UserName from "+packageListName+" where date(CreatedTime)>DATE_SUB(NOW(), INTERVAL 3 MONTH)"
					+ " AND NOT Status='Deleted' AND CompanyName='"+companyName+"' AND UserName in"
							+ " (select UserName FROM "+packageListName+" GROUP BY UserName HAVING COUNT(UserName)>10) ORDER BY id DESC";
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				userNameList.add(rs.getString("UserName"));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return userNameList;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readMoreThanTenPackagesUserNameInLastTwoMonthForCompany(companyName);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<String> readMoreThanFivePackagesUserNameInLastTwoMonthForCompany(String companyName) {
		wirteLog("Read packages for User");
		List<String> userNameList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select UserName from "+packageListName+" where date(CreatedTime)>DATE_SUB(NOW(), INTERVAL 3 MONTH)"
					+ " AND NOT Status='Deleted' AND CompanyName='"+companyName+"' AND UserName in"
							+ " (select UserName FROM "+packageListName+" GROUP BY UserName HAVING COUNT(UserName)>5) ORDER BY id DESC";
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				userNameList.add(rs.getString("UserName"));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return userNameList;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readMoreThanFivePackagesUserNameInLastTwoMonthForCompany(companyName);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	
	public List<String> readPackagesUserNameInLastTwoMonthForCompany(String companyName) {
		wirteLog("Read packages for User");
		List<String> userNameList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select UserName from "+packageListName+" where date(CreatedTime)>DATE_SUB(NOW(), INTERVAL 3 MONTH) AND NOT Status='Deleted' AND CompanyName='"+companyName+"' ORDER BY id DESC";
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷

			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				userNameList.add(rs.getString("UserName"));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return userNameList;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readPackagesUserNameInLastTwoMonthForCompany(companyName);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readAllLabeledkagesForUser(EbizUser user,EbizCompany company,int offset,int rownumber) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where Labeler='"+user.userName+"' AND LabelStatus='MadeLabel' AND CompanyName='"+company.companyName+"' ORDER BY id DESC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readAllPaidkagesForUser(EbizUser user,EbizCompany company,int offset,int rownumber) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where Payer='"+user.userName+"' AND PayStatus='Paid' AND CompanyName='"+company.companyName+"' ORDER BY id DESC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	
	public List<EbizPackage> readAllCheckedPackagesForUser(EbizUser user,EbizCompany company,int offset,int rownumber) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where Checker='"+user.userName+"' AND CheckStatus='Checked' AND Status!='Delivered' AND Status!='Deleted' AND Status!='EmailedLabel' AND Status!='Shipped' AND CompanyName='"+company.companyName+"' ORDER BY id DESC LIMIT "+rownumber+" OFFSET "+offset;
			
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readAllReceivedPackagesForUser(EbizUser user,EbizCompany company,int offset,int rownumber) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+warehouseReceivedPackageListName+" where Receiver='"+user.userName+"' ORDER BY id DESC LIMIT "+rownumber+" OFFSET "+offset;
			
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	
	public List<EbizPackage> readAllNeedCheckPackagesForCompany(int offset,int rownumber,String companyName) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where (CheckStatus='UnChecked' OR CheckStatus IS NULL) AND (Status='Delivered' OR Status='EmailedLabel' OR Status='Shipped') AND CompanyName='"+companyName+"' ORDER BY id ASC LIMIT "+rownumber+" OFFSET "+offset;
			//sql= "select * from "+packageListName+" where (Status='Delivered' OR Status='EmailedLabel' OR Status='Shipped') AND CompanyName='"+companyName+"' ORDER BY id ASC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readNeedLabeledPackForUser(EbizUser operator,EbizCompany company,String pickedUserName) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			//sql= "select * from "+packageListName+" where (LabelStatus='UnMade' OR LabelStatus IS NULL) AND UserName='"+pickedUserName
			//		+"' AND (Status= 'InStock' OR Status='UnReceived') AND CompanyName='"+company.companyName+"' ORDER BY id ASC";
			
			sql= "select * from "+packageListName+" where UserName='"+pickedUserName
					+"' AND Status= 'InStock' AND CompanyName='"+company.companyName+"' ORDER BY id ASC";
			
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readNeedPaidPackForUser(EbizUser operator,EbizCompany company,String pickedUserName) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where (PayStatus='UnPaid' OR PayStatus='PartlyPaid' OR PayStatus IS NULL) AND UserName='"+pickedUserName
					+"' AND Status!= 'Deleted' AND Status!='UnReceived' AND CompanyName='"+company.companyName+"' ORDER BY id ASC";
			
			//System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readAllNeedLabeListPackagesForCompany(int offset,int rownumber,String companyName) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where (LabelStatus='UnMade' OR LabelStatus IS NULL OR LabelStatus='') AND Status= 'InStock' AND CompanyName='"+companyName+"' ORDER BY id ASC LIMIT "+rownumber+" OFFSET "+offset;
			//sql= "select * from "+packageListName+" where Status= 'InStock' AND CompanyName='"+companyName+"' ORDER BY id ASC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readAllNeedPayListPackagesForCompany(int offset,int rownumber,String companyName) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where (PayStatus='UnPaid' OR PayStatus='PartlyPaid' OR PayStatus IS NULL) AND "
					+ "Status!= 'Deleted' AND Status!= 'UnReceived' AND CompanyName='"+companyName+"' ORDER BY id ASC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readMakingLabelTasksForCompany(EbizUser user,EbizCompany company,int offset,int rownumber) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where LabelStatus='"+EbizPackageLabelStatusEnum.MakingLable.getName()+"' AND Labeler='"+user.userName+"' ORDER BY id ASC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readPayingTasksForCompany(EbizUser user,EbizCompany company,int offset,int rownumber) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where PayStatus='"+EbizPackagePayStatusEnum.Paying.getName()+"' AND Payer='"+user.userName+"' ORDER BY id ASC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readCheckingTasksForCompany(EbizUser user,EbizCompany company,int offset,int rownumber) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+packageListName+" where CheckStatus='"+EbizPackageCheckStatusEnum.Checking.getName()+"' AND Checker='"+user.userName+"' ORDER BY id ASC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	
	public int getCheckTaskCount(EbizUser user,EbizCompany company){
		String tableName=packageListName;
		Connection connection = null;
	    int rowCount = -1;
	    try {
	    	connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql = "SELECT COUNT(*) from "+tableName+" Where CheckStatus='"+EbizPackageCheckStatusEnum.Checking.getName()+"' AND Checker='"+user.userName +"'";    
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			rs.next();
			int counter=rs.getInt(1);
			tryconnectionCounter=0;
            return counter;
	    	
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseConnection(connection,tryconnectionCounter);
	    }
	    return rowCount;	
	}
	public int getLabelTaskCount(EbizUser user,EbizCompany company){
		String tableName=packageListName;
		Connection connection = null;
	    int rowCount = -1;
	    try {
	    	connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql = "SELECT COUNT(*) from "+tableName+" Where LabelStatus='"+EbizPackageLabelStatusEnum.MakingLable.getName()+"' AND Labeler='"+user.userName +"'";    
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			rs.next();
			int counter=rs.getInt(1);
            return counter;
	    	
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseConnection(connection,tryconnectionCounter);
	    }
	    return rowCount;	
	}
	public int getPayTaskCount(EbizUser user,EbizCompany company){
		String tableName=packageListName;
		Connection connection = null;
	    int rowCount = -1;
	    try {
	    	connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql = "SELECT COUNT(*) from "+tableName+" Where PayStatus='"+EbizPackagePayStatusEnum.Paying.getName()+"' AND Payer='"+user.userName +"'";    
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			rs.next();
			int counter=rs.getInt(1);
            return counter;
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseConnection(connection,tryconnectionCounter);
	    }
	    return rowCount;	
	}
	
	//条件查询
	public List<EbizPackage> readAllPackagesForCompany(HttpServletRequest  request,int offset,int rownumber,String companyName,HashMap<String, String> keyValuePairs) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		HttpSession session = request.getSession();
		System.out.println("当前offset"+offset);
		System.out.println("当前rownumber"+rownumber);
		try {
			String temp="";
		
			for(String key:keyValuePairs.keySet()){//遍历集合 k值
				
				if(key.equals(EbizPackageColumnEnum.TrackingNumber.getName())){
					
						if(EbizPackageColumnEnum.TrackingNumber.getName()==null){
							temp=temp;
						}else{
							System.out.println("tracking----"+keyValuePairs.get(key));
							//把从页面获取的值存入作用域
							temp=temp+" AND '"+keyValuePairs.get(key) + "' LIKE CONCAT(" + "'%',"
									+ key + ",'%') AND Length("+key+")>5 OR " + key + " LIKE '%" + keyValuePairs.get(key) + "%'";
						}

					
				}else if(key.equals(EbizPackageColumnEnum.ShippingAddress.getName())){
					if (EbizCompanyAddressEnum.isCompanyAddress(keyValuePairs.get(key))){
						temp=temp+" AND "+key+" ='"+keyValuePairs.get(key)+"'";
					}else{
						temp=temp+" AND "+key+" !='"+EbizCompanyAddressEnum.Address1.getName()+"'";
						temp=temp+" AND "+key+" !='"+EbizCompanyAddressEnum.Address2.getName()+"'";
						temp=temp+" AND "+key+" !='"+EbizCompanyAddressEnum.Address3.getName()+"'";
					}

				}else if(key.equals(EbizPackageColumnEnum.Status.getName())){
					if (keyValuePairs.get(key).equals("UnMatch")||keyValuePairs.get(key).equals("UnConfirmed")){
						request.setAttribute("status", keyValuePairs.get(key));
						System.out.println("邮寄状态key="+key+"---键值"+keyValuePairs.get(key));
						temp=temp+" AND "+key+" LIKE '%" + keyValuePairs.get(key) + "%'";
					}else{
						temp=temp+" AND "+key+" ='"+keyValuePairs.get(key)+"'";
					}

				}
				else if(key.equals(EbizPackageColumnEnum.ProductName.getName())){
					
					System.out.println("key值"+key+"---值为"+keyValuePairs.get(key));
					//页面传来的值存入request作用域
					request.setAttribute("productName1", keyValuePairs.get(key));
						temp=temp+" AND "+key+" LIKE '%" + keyValuePairs.get(key) + "%'";
					

				}
				
				else{
					temp=temp+" AND "+key+" ='"+keyValuePairs.get(key)+"'";
				}
			}
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;																//select *  from  table  limit    (pagenumber-1)*pagesize,
			sql= "select * from "+packageListName+" where NOT Status='Deleted' AND CompanyName='"+companyName+"'"+temp+" ORDER BY id DESC LIMIT "+rownumber+" OFFSET "+offset;
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
				

			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			
			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		//获取页面的值
		
				String tracking1= request.getParameter("tracking");
				String productname= request.getParameter("productName");
				

				session.setAttribute("tracking", tracking1);
				//session.setAttribute("productname", productname);

				System.out.println("tracking1=="+tracking1);
				
		return null;
		
	}
	
	
	//产品管理页面查询
		public List<EbizProduct> readAllProductsForCompany(int offset,int rownumber,String companyName,HashMap<String, String> keyValuePairs) {
			List<EbizProduct> products=new ArrayList<>();
			Connection connection=null;
			try {
				String temp="";
				for(String key:keyValuePairs.keySet()){
					if(//仓库查询
							key.equals(EbizProductColumnEnum.WareHouse.getColumnName())){
						if (EbizCompanyAddressEnum.isCompanyAddress(keyValuePairs.get(key))){
							temp=temp+" AND "+key+" ='"+keyValuePairs.get(key)+"'";
						}else{
							temp=temp+" AND "+key+" !='"+EbizCompanyAddressEnum.Address1.getName()+"'";
							temp=temp+" AND "+key+" !='"+EbizCompanyAddressEnum.Address2.getName()+"'";
							temp=temp+" AND "+key+" !='"+EbizCompanyAddressEnum.Address3.getName()+"'";
						}

					}
					//uid查询
					else if(key.equals(EbizProductColumnEnum.UID.getColumnName())){
						//模糊查询    where where like '%%'
						System.out.println("uid  key值"+key+"---值为"+keyValuePairs.get(key));
							temp=temp+" AND "+key+" LIKE '%" + keyValuePairs.get(key) + "%'";
						

					}//品牌查询
					else if(key.equals(EbizProductColumnEnum.Brand.getColumnName())){
						//模糊查询    where where like '%%'
						System.out.println("品牌key值"+key+"---值为"+keyValuePairs.get(key));
							temp=temp+" AND "+key+" LIKE '%" + keyValuePairs.get(key) + "%'";
						

					}
					//型号查询
					else if(key.equals(EbizProductColumnEnum.ModelNumber.getColumnName())){
						//模糊查询    where where like '%%'
						System.out.println("型号key值"+key+"---值为"+keyValuePairs.get(key));
							temp=temp+" AND "+key+" LIKE '%" + keyValuePairs.get(key) + "%'";
						

					}
					//upc查询
					else if(key.equals(EbizProductColumnEnum.UPC.getColumnName())){
						//模糊查询    where where like '%%'
						System.out.println("UPC**key值"+key+"---值为"+keyValuePairs.get(key));
							temp=temp+" AND "+key+" LIKE '%" + keyValuePairs.get(key) + "%'";
						

					}
					else if(key.equals(EbizProductColumnEnum.ProductName.getColumnName())){
						//模糊查询    where where like '%%'
						System.out.println("产品名key值"+key+"---值为"+keyValuePairs.get(key));
							temp=temp+" AND "+key+" LIKE '%" + keyValuePairs.get(key) + "%'";
						

					}
					else{
						temp=temp+" AND "+key+" ='"+keyValuePairs.get(key)+"'";
					}
				}
				connection=EbizDataBase.getConnection();
				Statement stmt;
				stmt = connection.createStatement();
				String sql;
				System.out.println("即将经过数据库");

				sql= "select * from "+productListName+" where NOT Status='Deleted' AND CompanyName='"+companyName+"'"+temp+" ORDER BY id DESC LIMIT "+rownumber+" OFFSET "+offset;
				System.out.println("经过数据库"+sql);
				ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
				if (rs==null){
					return null;
				}
				while (rs.next()) {			
					products.add(rsToEbizProduct(rs));
					System.out.println("查完集合products"+products);
				}
				return products;
			} catch (SQLException e) {

				e.printStackTrace();
			}finally {
				CloseConnection(connection,0);
			}
			return null;
		}
	public List<EbizPackage> readAllPackagesForUser(int offset,int rownumber,EbizUser user) {
		wirteLog("Read packages for User");
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchAllSetForUser(connection,packageListName,offset,rownumber,user);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return packages;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllPackagesForUser(offset, rownumber,user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readPaidPackagesForUser(int offset,int rownumber,EbizUser user) {
		wirteLog("Read packages for User");
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchPaidSetForUser(connection,packageListName,offset,rownumber,user);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return packages;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readPaidPackagesForUser(offset, rownumber,user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return null;
	}
	public List<EbizPackage> readUnpaidPackagesForUser(int offset,int rownumber,EbizUser user) {
		wirteLog("Read packages for User");
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchUnpaidSetForUser(connection,packageListName,offset,rownumber,user);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return packages;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readUnpaidPackagesForUser(offset, rownumber,user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizPackage> readUnConfirmedSellOrOBOPackagesForUser(int offset,int rownumber,EbizUser user) {
		wirteLog("Read packages for User");
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchUnConfirmedSellOrOBOPackSetForUser(connection,packageListName,offset,rownumber,user);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return packages;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readUnConfirmedSellOrOBOPackagesForUser(offset, rownumber,user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizPackage> readUnReceivedAndUnConfirmedSellOrOBOPackagesForUser(int offset,int rownumber,EbizUser user) {
		wirteLog("Read packages for User");
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchUnReceivedAndUnConfirmedSellOrOBOPackSetForUser(connection,packageListName,offset,rownumber,user);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return packages;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readUnReceivedAndUnConfirmedSellOrOBOPackagesForUser(offset, rownumber,user);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizUser> readActiveDoctorUsers(int offset,int rownumber) {
		wirteLog("Read packages for User");
		List<EbizUser> ebizUsers=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchActiveDoctorUserSet(connection,offset,rownumber);
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				EbizUser user = rsToEbizUser(rs);
				ebizUsers.add(user); 
				//if(user.getUserPermissions().contains(EbizUserPermissionEnum.Active.getName())){
				//	user.addUserPermissions(EbizUserPermissionEnum.ReportPackage.getName());
				//	user.showDialogWhenUpdate=false;
				//	updateUser(user);
				//}
			}
			tryconnectionCounter=0;
			wirteLog("All Users read done");
			return ebizUsers;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readActiveDoctorUsers(offset, rownumber);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizUser> readAllSubUsersForCompany(String company,int offset,int rownumber) {
		wirteLog("Read packages for User");
		List<EbizUser> ebizUsers=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
				sql= "select * from "+userListName+" where NOT Status='Deleted' AND CompanyName= '"+company+"'"+" AND UserType!= '"+EbizUserTypeEnum.Doctor.getName()+"'"+" ORDER BY id DESC LIMIT "+rownumber+" OFFSET "+offset;    //要执锟叫碉拷SQL

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				EbizUser user = rsToEbizUser(rs);
				ebizUsers.add(user);
			}
			tryconnectionCounter=0;
			wirteLog("All Users read done");
			return ebizUsers;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllSubUsersForCompany(company,offset, rownumber);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	
	public List<String> readAllActiveUnTrustedNurseNameEmailForCompany(String company) { 
		wirteLog("Read packages for User");
		List<String> userEmailList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
				sql= "select Email from "+userListName+" where Status='Active' AND CompanyName= '"+company
						+"' AND UserType= '"+EbizUserTypeEnum.UnTrustedNurse.getName()+"' ORDER BY id DESC";    //要执锟叫碉拷SQL

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				userEmailList.add(rs.getString("Email"));
			}
			tryconnectionCounter=0;
			wirteLog("All Users read done");
			return userEmailList;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllActiveTrustedNurseNameEmailForCompany(company);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<String> readAllActiveTrustedNurseNameEmailForCompany(String company) { 
		wirteLog("Read packages for User");
		List<String> userEmailList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
				sql= "select Email from "+userListName+" where Status='Active' AND CompanyName= '"+company
						+"' AND UserType= '"+EbizUserTypeEnum.TrustedNurse.getName()+"' ORDER BY id DESC";    //要执锟叫碉拷SQL

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				userEmailList.add(rs.getString("Email"));
			}
			tryconnectionCounter=0;
			wirteLog("All Users read done");
			return userEmailList;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllActiveTrustedNurseNameEmailForCompany(company);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	
	public HashMap<String, String> readAllActiveNurseNameEmailForCompany(String company) { 
		wirteLog("Read packages for User");
		HashMap<String,String> userNameEmailMap=new HashMap<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
				sql= "select * from "+userListName+" where Status='Active' AND CompanyName= '"+company
						+"' AND (UserType= '"+EbizUserTypeEnum.Nurse.getName()+"'"+
						" OR UserType= '"+EbizUserTypeEnum.TrustedNurse.getName()+"') ORDER BY id DESC";    //要执锟叫碉拷SQL

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				EbizUser user=rsToEbizUser(rs);
				userNameEmailMap.put(user.getUserName(),user.getEmail());
			}
			tryconnectionCounter=0;
			wirteLog("All Users read done");
			return userNameEmailMap;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllActiveNurseNameEmailForCompany(company);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	//registrate in three month
	public List<EbizUser> readAllNewNurseForCompany(String company) {
		wirteLog("Read packages for User");
		List<EbizUser> ebizUsers=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			Statement stmt;
			stmt = connection.createStatement();
			String sql;
				sql= "select * from "+userListName+" where Status='Active' AND CompanyName= '"+company+"'"+" AND UserType= '"+EbizUserTypeEnum.Nurse.getName()+"'"+" ORDER BY id DESC";    //要执锟叫碉拷SQL

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				EbizUser user = rsToEbizUser(rs);
				ebizUsers.add(user);
			}
			tryconnectionCounter=0;
			wirteLog("All Users read done");
			return ebizUsers;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllNewNurseForCompany(company);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizUser> readAllDoctorUsers(int offset,int rownumber) {
		wirteLog("Read packages for User");
		List<EbizUser> ebizUsers=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchDoctorUserSet(connection,offset,rownumber);
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				EbizUser user = rsToEbizUser(rs);
				ebizUsers.add(user);
			}
			tryconnectionCounter=0;
			wirteLog("All Users read done");
			return ebizUsers;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readAllDoctorUsers(offset, rownumber);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	
	public List<EbizPackage> readPackagesForUser(int offset,int rownumber,EbizUser user,String status) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSetForUser(connection,packageListName,offset,rownumber,user,status);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}

			return packages;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizPackage> readPackagesForUser(int offset,int rownumber,EbizUser user,String status1,String status2,String status3) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSetForUser(connection,packageListName,offset,rownumber,user,status1,status2,status3);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}

			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizPackage> readPackagesForUser(int offset,int rownumber,EbizUser user,String status1,String status2,String status3,String status4) {
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSetForUser(connection,packageListName,offset,rownumber,user,status1,status2,status3,status4);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}

			return packages;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public List<EbizPackage> readPackagesForUser(int offset,int rownumber,EbizUser user,String status1,String status2) {
		wirteLog("Read packages for User");
		List<EbizPackage> packages=new ArrayList<>();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSetForUser(connection,packageListName,offset,rownumber,user,status1,status2);
			if (rs==null){
				return null;
			}
			while (rs.next()) {			
				packages.add(rsTopackage(rs));
			}
			tryconnectionCounter=0;
			wirteLog("Packages read done");
			return packages;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				CloseConnection(connection,tryconnectionCounter);
				return readPackagesForUser(offset, rownumber,user,status1,status2);
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}

		return null;
	}
	public boolean updateDealStatus(EbizProduct product){
		   PreparedStatement update;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update =con.prepareStatement("UPDATE "+dealListName+" SET Status=? WHERE id = ?");
			update.setString(1, product.status);
			update.setInt(2, product.UID);
			wirteLog(update.toString());
			if ( update.executeUpdate()>0){
				deals.remove(product.getUID());
				tryconnectionCounter=0;
				showMessage("Deal Status"+product.toString()+" updated to Deleted");
				return true;
			}else{

				return false;
			}
		} catch (SQLException e) {
			CloseConnection(con,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return updateDealStatus(product);
			}
			//if (showDialog){
				showMessage("Deal Status update failed, please check connection");
			//}
			e.printStackTrace();
		}finally {
			CloseConnection(con,0);
		}
		return false;	
	}

	public boolean updateProductTicket(EbizUser user, EbizCompany company, int uid, int value) {
		return updateValue(user, company, uid, productListName, "Tickets", value);
	}

	public boolean updateProductLimitPerPerson(EbizUser user, EbizCompany company, int uid, int value) {
		return updateValue(user, company, uid, productListName, "LimitPerPerson", value);
	}

	public boolean updateProductWeight(EbizUser user, EbizCompany company, int uid, double value) {
		return updateValue(user, company, uid, productListName, "Weight", value);
	}

	public boolean updateProductLength(EbizUser user, EbizCompany company, int uid, double value) {
		return updateValue(user, company, uid, productListName, "Length", value);
	}

	public boolean updateProductWidth(EbizUser user, EbizCompany company, int uid, double value) {
		return updateValue(user, company, uid, productListName, "Width", value);
	}

	public boolean updateProductHeight(EbizUser user, EbizCompany company, int uid, double value) {
		return updateValue(user, company, uid, productListName, "Height", value);
	}

	public boolean updateProductPrice(EbizUser user, EbizCompany company, int uid, double value) {
		return updateValue(user, company, uid, productListName, "Price", value);
	}

	public boolean updateProductPromotQuantity(EbizUser user, EbizCompany company, int uid, int value) {
		return updateValue(user, company, uid, productListName, "PromotQuantity", value);
	}

	public boolean updateProductPromotPrice(EbizUser user, EbizCompany company, int uid, double value) {
		return updateValue(user, company, uid, productListName, "PromotPrice", value);
	}

	public boolean updateProductWarehousePrice(EbizUser user, EbizCompany company, int uid, double value) {
		return updateValue(user, company, uid, productListName, "WarehousePrice", value);
	}

	public boolean updateProductWarehousePromotQuantity(EbizUser user, EbizCompany company, int uid, int value) {
		return updateValue(user, company, uid, productListName, "WarehousePromotQuantity", value);
	}

	public boolean updateProductWarehousePromotePrice(EbizUser user, EbizCompany company, int uid, double value) {
		return updateValue(user, company, uid, productListName, "WarehousePromotePrice", value);
	}

	public boolean updateProductSKU(EbizUser user, EbizCompany company, EbizProduct product, String newSKU) {
		updatePackSKUwithProduct newUpdate = new updatePackSKUwithProduct(user, company, product, newSKU);
		newUpdate.start();
		return updateValue(user, company, product.UID, productListName, "SKU", newSKU);
	}
	
	public class updatePackSKUwithProduct extends Thread{
		private EbizUser user;
		private EbizCompany company;
		   private EbizProduct product;
		   private String newSKU;


		   public updatePackSKUwithProduct(EbizUser user,EbizCompany company,EbizProduct product,String newSKU)
		   {
			   this.user=user;
			   this.company=company;
		      this.product = product;
		      this.newSKU = newSKU;

		   }
		   @Override
		   public void run()
		   {
			   updatePackageSKUWithProductSKUChange(user,company, product, newSKU);
		   }

	}
	
	public int updatePackageSKUWithProductSKUChange(EbizUser user,EbizCompany company,EbizProduct product,String newSKU){
		String tableName=packageListName;
		   PreparedStatement update;
		   Connection connection=null;
		try {
			String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
			String operationString=";update SKU to "+newSKU+" by "+user.userName;
			while(operationString.length()>2000){
				operationString=operationString.substring(operationString.length()-200,operationString.length());
			}
			String unreceived=EbizPackageStatusEnum.UnReceived.getColumnName();
			String instock=EbizPackageStatusEnum.InStock.getColumnName();

			connection = EbizDataBase.getConnection();
			update =connection.prepareStatement("UPDATE "+tableName+" SET SKU='"+newSKU+"',UpdateTime='"+timeString
					+"',OperationRecord=CONCAT(OperationRecord,'"+ operationString+"') WHERE ModelNumber='"+product.model+
					"' AND CompanyName='"+company.companyName+"' AND NOT SKU='"+newSKU+"' AND (Status='"+unreceived+"' OR Status='"+instock+"')");
			wirteLog(update.toString());
			int result=update.executeUpdate();
			return result;

		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,tryconnectionCounter);
		}
		return -1;	
	}

	public boolean updateProductModel(EbizUser user, EbizCompany company, int uid, String value) {
		return updateValue(user, company, uid, productListName, "Model", value);
	}
	public boolean updateProductBrand(EbizUser user, EbizCompany company, int uid, String value) {
		return updateValue(user, company, uid, productListName, "Brand", value);
	}
	public boolean updateProductASIN(EbizUser user, EbizCompany company, int uid, String value) {
		return updateValue(user, company, uid, productListName, "ASIN", value);

	}
	
	public boolean updateProductUPC(EbizUser user, EbizCompany company, int uid, String value) {
		return updateValue(user, company, uid, productListName, "UPC", value);
	}
	public String readValue(int uid,String tableName,String name){
		Connection connection=null;
		Statement stmt;
		try {
			connection = EbizDataBase.getConnection();
			stmt = connection.createStatement();
			String sql;
			sql= "select "+ name+" from "+tableName+" where id="+uid;

		ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
		while (rs.next()) {
			return rs.getString(name);
			
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public String readOperationRecord(int uid,String tableName){
		return readValue(uid,tableName,"OperationRecord");
	}
	public boolean updateValue(EbizUser user,EbizCompany company,int uid,String tableName,String name,double value){
		   PreparedStatement update;
		   Connection connection=null;
		try {
			String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
			connection = EbizDataBase.getConnection();
			update =connection.prepareStatement("UPDATE "+tableName+" SET "+name+"=?,UpdateTime=? WHERE id = ?");
			update.setDouble(1, value);
			update.setString(2, timeString);
			update.setInt(3, uid);
			wirteLog(update.toString());
			if ( update.executeUpdate()>0){
				addUpdateOperationRecord(user, company, tableName, name, uid, Double.toString(value));
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CloseConnection(connection,tryconnectionCounter);
		}
		return false;	
	}
	public boolean updateValue(EbizUser user,EbizCompany company,int uid,String tableName,String name,int value){
		   PreparedStatement update;
		   Connection connection=null;
		try {
			String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
			connection = EbizDataBase.getConnection();
			update =connection.prepareStatement("UPDATE "+tableName+" SET "+name+"=?,UpdateTime=? WHERE id = ?");
			update.setInt(1, value);
			update.setString(2, timeString);
			update.setInt(3, uid);
			wirteLog(update.toString());
			if ( update.executeUpdate()>0){
				addUpdateOperationRecord(user, company, tableName, name, uid, Integer.toString(value));
				return true;
			}else{

				return false;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			CloseConnection(connection,tryconnectionCounter);
		}
		return false;	
	}

	public boolean updateValue(EbizUser user,EbizCompany company,int uid,String tableName,String name,String value){
		   PreparedStatement update;
		   Connection connection=null;
		try {
			String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
			connection = EbizDataBase.getConnection();
			update =connection.prepareStatement("UPDATE "+tableName+" SET "+name+"=?,UpdateTime=? WHERE id = ?");
			update.setString(1, value);
			update.setString(2, timeString);
			update.setInt(3, uid);
			wirteLog(update.toString());
			if ( update.executeUpdate()>0){
				addUpdateOperationRecord(user, company, tableName, name, uid, value);
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CloseConnection(connection,tryconnectionCounter);
		}
		return false;	
	}
	public boolean updateProductURL(EbizUser user,EbizCompany company,int uid,String value){
		return 	updateValue(user,company,uid, productListName, "URI", value);
	}
	public boolean updateProductUserNote(EbizUser user,EbizCompany company,int uid,String value){
		return updateValue(user,company,uid, productListName, "UserNote", value);
	}
	public boolean updateProductName(EbizUser user,EbizCompany company,int uid,String value){
		return updateValue(user,company,uid, productListName, "ProductName", value);
	}
	public boolean updateProductStatus(EbizUser user,EbizCompany company,int uid,String value){
		return updateValue(user,company,uid, productListName, "Status", value);
	}
	public boolean updateCompanyPayTimeInfor(EbizUser user,EbizCompany company,int uid,String value){
		return updateValue(user,company,uid, companyListName, "PayTimeInfor", value);
	}
	public boolean updateCompanyUserMaunal(EbizUser user,EbizCompany company,int uid,String value){
		return updateValue(user,company,uid, companyListName, "UserManual", value);
	}
	public boolean updateCompanyPhoneNumber(EbizUser user,EbizCompany company, int uid,String value){
		return updateValue(user,company,uid, companyListName, "UserManual", value);
	}
	public boolean updateCompanyPermission(EbizUser user,EbizCompany company,int uid,String value){
		return updateValue(user, company, uid, companyListName, "Permision", value);	
	}
	public boolean updateCompanyEmail(EbizUser user,EbizCompany company,int uid,String value){
		return updateValue(user, company, uid, companyListName, "Email", value);
	}
	public boolean updateCompanyAddress(EbizUser user,EbizCompany company,int uid,String value){
		return updateValue(user, company, uid, companyListName, "Address", value);
	}
	public boolean updateUserBalance(EbizUser user,EbizCompany company,int uid,double balance){
		return updateValue(user,company,uid, userListName, "Balance", balance);
	}
	public boolean updateUserPersonalLimit(EbizUser user,EbizCompany company,int uid,int limit){
		return 	updateValue(user,company,user.UID, userListName, "PersonalLimit", limit);
	}
	public boolean updateUserStatus(EbizUser user,EbizCompany company,int uid,String value){
		return 
		updateValue(user,company,uid, userListName, "Status", value);
	}
	public boolean updateUserType(EbizUser user,EbizCompany company,int uid,String value){
		return 
		updateValue(user,company,uid, userListName, "UserType", value);
	}
	public boolean updateUserPermission(EbizUser user,EbizCompany company,int uid,String value){
		return 
		updateValue(user,company,uid, userListName, "Permissions", value);
	}
	public boolean updateUserNote(EbizUser user,EbizCompany company,int uid,String value){
		return 
		updateValue(user,company,uid, userListName, "Note", value);
	}
	public boolean updateUserAddress(EbizUser user,EbizCompany company,int uid,String value){
		return 
		updateValue(user,company,uid, userListName, "Address", value);
	}
	public boolean updateUserPhoneNumber(EbizUser user,EbizCompany company,int uid,String value){
		return 
		updateValue(user,company,uid, userListName, "PhoneNumber", value);
	}
	public boolean updateUserParameter(EbizUser user,EbizCompany company,int uid,String value){
		return 
		updateValue(user,company,uid, userListName, "ParameterString", value);
	}
	public boolean updateUserEmail(EbizUser user,EbizCompany company,int uid,String value ){
		return 
		updateValue(user,company,uid, userListName, "Email", value);
	}
	public boolean updateUserPassword(EbizUser user,EbizCompany company,int uid,String value){
		return 
		updateValue(user,company,uid, userListName, "PassWord", value);
	}
	public boolean updatePackageTrackingAndStatus(EbizPackage pack){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "TrackingNumber=?,Status=?,UpdateTime=?" + " WHERE id=?");
			update.setString(1, pack.trackingNumber);
			update.setString(2, pack.getStatus());
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, pack.UID);

			if (update.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}
	public boolean updatePackageNote(EbizUser user,EbizCompany company,int uid, String note){
		return updateValue(user,company,uid, packageListName, "Note", note);
	}
	public boolean updatePackageShipId(EbizUser user,EbizCompany company,int uid, String shipID){
		return updateValue(user,company,uid, packageListName, "ShipID", shipID);
	}
	public boolean updatePackageTracking(EbizUser user,EbizCompany company,int uid, String trackingNumber){
		return updateValue(user,company,uid, packageListName, "TrackingNumber", trackingNumber);
	}
	public boolean updatePackageCreditCard(EbizUser user,EbizCompany company,int uid, String creditcardNumber){
		return updateValue(user,company,uid, packageListName,"CreditCardNumber", creditcardNumber);
	}
	public boolean updatePackageAddress(EbizUser user,EbizCompany company,int uid,String address){
		return updateValue(user,company,uid, packageListName, "ShippingAddress", address);
	}
	public boolean updatePackageBrand(EbizUser user,EbizCompany company,int uid,String productBrand){
		return updateValue(user,company,uid, packageListName, "Brand", productBrand);
	}
	public boolean updatePackageName(EbizUser user,EbizCompany company,int uid,String productName){
		return updateValue(user,company,uid, packageListName, "ProductName", productName);
	}
	public boolean updatePackageModel(EbizUser user,EbizCompany company,int uid,String productModel){
		return updateValue(user,company,uid, packageListName, "ModelNumber", productModel);
	}
	public boolean updatePackageStatus(EbizUser user,EbizCompany company,int uid, String status){
		return updateValue(user,company,uid, packageListName, "Status", status);
	}
	public boolean updatePackagePayStatus(EbizUser user,EbizCompany company,int uid, String status){
		return updateValue(user,company,uid, packageListName, "PayStatus", status);
	}
	public boolean updatePackageLabelStatus(EbizUser user,EbizCompany company,int uid, String status){
		return updateValue(user,company,uid, packageListName, "LabelStatus", status);
	}
	public boolean updatePackageTrackingLabelStatusAndLabeler(EbizUser user,EbizCompany company,int uid,String tracking,String shipId){
		
		
		   PreparedStatement update;
		   Connection connection=null;
		try {
			String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
			connection = EbizDataBase.getConnection();
			update =connection.prepareStatement("UPDATE "+packageListName+" SET TrackingNumber=?,ShipID=?,Status=?,LabelStatus=?,Labeler=?,UpdateTime=? WHERE id = ?");
			update.setString(1, tracking);
			update.setString(2, shipId);
			update.setString(3, EbizPackageStatusEnum.EmailedLabel.getColumnName());
			update.setString(4, EbizPackageLabelStatusEnum.MadeLabel.getName());
			update.setString(5, user.userName);
			update.setString(6, timeString);
			update.setInt(7, uid);
			wirteLog(update.toString());
			if ( update.executeUpdate()>0){
				addUpdateOperationRecord(user, company, packageListName, "TrackingNumber", uid,  tracking);
				addUpdateOperationRecord(user, company, packageListName, "ShipID", uid,  shipId);
				addUpdateOperationRecord(user, company, packageListName, "Status", uid,  EbizPackageStatusEnum.EmailedLabel.getColumnName());
				addUpdateOperationRecord(user, company, packageListName, "LabelStatus", uid, EbizPackageLabelStatusEnum.MadeLabel.getName());
				addUpdateOperationRecord(user, company, packageListName, "Labeler", uid, user.userName);
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CloseConnection(connection,tryconnectionCounter);
		}
		return false;	
	}
	
	
	public boolean updatePackageCreditcardNoteAndPayStatus(EbizUser user,EbizCompany company,int uid, String creditCard,String note,String payStatus){
		
		
		   PreparedStatement update;
		   Connection connection=null;
		try {
			String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
			connection = EbizDataBase.getConnection();
			update =connection.prepareStatement("UPDATE "+packageListName+" SET CreditCardNumber=?,Note=?,PayStatus=?,Payer=?,UpdateTime=? WHERE id = ?");
			update.setString(1, creditCard);
			update.setString(2, note);
			update.setString(3, payStatus);
			update.setString(4, user.userName);
			update.setString(5, timeString);
			update.setInt(6, uid);
			wirteLog(update.toString());
			if ( update.executeUpdate()>0){
				addUpdateOperationRecord(user, company, packageListName, "CreditCardNumber", uid, creditCard);
				addUpdateOperationRecord(user, company, packageListName, "Note", uid, note);
				addUpdateOperationRecord(user, company, packageListName, "PayStatus", uid, payStatus);
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CloseConnection(connection,tryconnectionCounter);
		}
		return false;	
	}
	public boolean updatePackageQuantity(EbizUser user,EbizCompany company,int uid, int quantity){
		return updateValue(user,company,uid, packageListName, "Quantity", quantity);
	}
	public boolean updatePackagePrice(EbizUser user,EbizCompany company,int uid, double price){
		return updateValue(user,company,uid, packageListName, "Price", price);
	}
	public boolean updatePackageSKU(EbizUser user,EbizCompany company,int uid, String SKU){
		return updateValue(user,company,uid, packageListName, "SKU", SKU);
	}
	public boolean updatePackageUPC(EbizUser user,EbizCompany company,int uid, String UPC){
		return updateValue(user,company,uid, packageListName, "UPC", UPC);
	}
	public boolean updatePackageASIN(EbizUser user,EbizCompany company,int uid, String ASIN){
		return updateValue(user,company,uid, packageListName, "ASIN", ASIN);
	}
	
	public boolean takeCheckTasks(EbizUser user,EbizCompany company,int uid){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "CheckStatus=?,Checker=?,UpdateTime=? " + "WHERE id=? AND (CheckStatus IS NULL OR CheckStatus=?)");
			update.setString(1, EbizPackageCheckStatusEnum.Checking.getName());
			update.setString(2, user.getUserName());
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, uid);
			update.setString(5, EbizPackageCheckStatusEnum.UnChecked.getName());

			if (update.executeUpdate() > 0) {
				addUpdateOperationRecord(user, company, packageListName, "CheckStatus", uid, EbizPackageCheckStatusEnum.Checking.getName());
				addUpdateOperationRecord(user, company, packageListName, "Checker", uid,  user.getUserName());
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}
	
	public boolean takeLabelTasks(EbizUser user,EbizCompany company,int uid){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "LabelStatus=?,Labeler=?,UpdateTime=? WHERE id=? AND Status=?");
			update.setString(1, EbizPackageLabelStatusEnum.MakingLable.getName());
			update.setString(2, user.getUserName());
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, uid);
			update.setString(5, EbizPackageStatusEnum.InStock.getColumnName());
			
			System.out.println(update.toString());

			if (update.executeUpdate() > 0) {
				addUpdateOperationRecord(user, company, packageListName, "LabelStatus", uid, EbizPackageLabelStatusEnum.MakingLable.getName());
				addUpdateOperationRecord(user, company, packageListName, "Labeler", uid,  user.getUserName());
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}
	public boolean takePayTasks(EbizUser user,EbizCompany company,int uid){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "PayStatus=?,Payer=?,UpdateTime=? " + "WHERE id=? AND (PayStatus IS NULL OR PayStatus=?)");
			update.setString(1, EbizPackagePayStatusEnum.Paying.getName());
			update.setString(2, user.getUserName());
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, uid);
			update.setString(5, EbizPackagePayStatusEnum.UnPaid.getName());

			if (update.executeUpdate() > 0) {
				addUpdateOperationRecord(user, company, packageListName, "PayStatus", uid, EbizPackagePayStatusEnum.Paying.getName());
				addUpdateOperationRecord(user, company, packageListName, "Payer", uid,  user.getUserName());
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}
	public boolean finishCheckTask(EbizUser user,EbizCompany company,int uid,String status){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "CheckStatus=?,Status=?,UpdateTime=? " + "WHERE id=? AND Checker=? AND CheckStatus=? AND NOT Status=?");
			
			update.setString(1, EbizPackageCheckStatusEnum.Checked.getName());
			update.setString(2, status);
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, uid);
			update.setString(5, user.getUserName());
			update.setString(6, EbizPackageCheckStatusEnum.Checking.getName());
			update.setString(7, status);
			if (update.executeUpdate() > 0) {
				addUpdateOperationRecord(user, company, packageListName, "CheckStatus", uid, EbizPackageCheckStatusEnum.UnChecked.getName());
				addUpdateOperationRecord(user, company, packageListName, "Status", uid,  status);
				return true;
			} else {
				tryconnectionCounter = 0;
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}
	public boolean cancelPayTask(EbizUser user,EbizCompany company,int uid){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "PayStatus=?,Payer=?,UpdateTime=? " + "WHERE id=? AND Payer=? AND PayStatus=?");
			update.setString(1, EbizPackagePayStatusEnum.UnPaid.getName());
			update.setString(2, "");
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, uid);
			update.setString(5, user.getUserName());
			update.setString(6, EbizPackagePayStatusEnum.Paying.getName());
			if (update.executeUpdate() > 0) {
				addUpdateOperationRecord(user, company, packageListName, "PayStatus", uid, EbizPackagePayStatusEnum.UnPaid.getName());
				addUpdateOperationRecord(user, company, packageListName, "Payer", uid,  "");
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}
	public boolean cancelLabelTask(EbizUser user,EbizCompany company,int uid){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "LabelStatus=?,Labeler=?,UpdateTime=? " + "WHERE id=? AND Labeler=? AND LabelStatus=?");
			update.setString(1, EbizPackageLabelStatusEnum.UnMadeLabel.getName());
			update.setString(2, "");
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, uid);
			update.setString(5, user.getUserName());
			update.setString(6, EbizPackageLabelStatusEnum.MakingLable.getName());
			if (update.executeUpdate() > 0) {
				addUpdateOperationRecord(user, company, packageListName, "LabelStatus", uid, EbizPackageLabelStatusEnum.UnMadeLabel.getName());
				addUpdateOperationRecord(user, company, packageListName, "Labeler", uid,  "");
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}
	
	public boolean cancelCheckTask(EbizUser user,EbizCompany company,int uid){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "CheckStatus=?,Checker=?,UpdateTime=? " + "WHERE id=? AND Checker=? AND CheckStatus=?");
			update.setString(1, EbizPackageCheckStatusEnum.UnChecked.getName());
			update.setString(2, "");
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, uid);
			update.setString(5, user.getUserName());
			update.setString(6, EbizPackageCheckStatusEnum.Checking.getName());
			if (update.executeUpdate() > 0) {
				addUpdateOperationRecord(user, company, packageListName, "CheckStatus", uid, EbizPackageCheckStatusEnum.UnChecked.getName());
				addUpdateOperationRecord(user, company, packageListName, "Checker", uid,  "");
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}

	public boolean updatePackageQuantityAndPrice(EbizUser user,EbizCompany company,EbizPackage pack,int quantity,double price){
		   PreparedStatement update = null;
		   Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			update = con.prepareStatement(
					"UPDATE " + packageListName + " SET " + "Price=?,Quantity=?,UpdateTime=? " + "WHERE id=?");
			update.setDouble(1, price);
			update.setInt(2, quantity);
			update.setString(3, GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
			update.setInt(4, pack.UID);

			if (update.executeUpdate() > 0) {
				tryconnectionCounter = 0;
				return true;
			} else {
				tryconnectionCounter = 0;
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseConnection(con,tryconnectionCounter);
		}
		return false;	
	}
    public List<EbizPackage> findPackages(List<Integer> ids){
    	String idString="(";
    	
    	for(int i=0;i<ids.size();i++){
    		idString=idString+"'"+ids.get(i)+"',";
    	}
    	idString=idString.substring(0, idString.length()-1);
    	idString=idString+")";
    	Connection connection = null;
			try {  	
				connection=EbizDataBase.getConnection();
				Statement stmt;
				stmt = connection.createStatement();
				String sql = "select * from "+packageListName+" where id in "+idString;
				ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
				if(rs==null){
					return null;
				}
				List<EbizPackage> packs=new ArrayList<>();
				while (rs.next()) {
					EbizPackage pack = rsTopackage(rs);
					packs.add(pack);
			
				}
				return packs;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				CloseConnection(connection,0);
			}
			return null;
    }

    public EbizPackage findPackage(int id){
    	wirteLog("find existing tracking record");
    	// search record in database

    	Connection connection = null;
			try {  	
				connection=EbizDataBase.getConnection();
		    	ResultSet rs = searchSet(connection,packageListName, "id", id,1);
				if(rs==null){
					return null;
				}
				
				while (rs.next()) {
					EbizPackage pack = rsTopackage(rs);
				return pack;

				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				CloseConnection(connection,0);
			}
			return null;
    }
    public List<EbizPackage> readPackagesByTracking(String trackingNumber){
    	wirteLog("find existing tracking record");
    	// search record in database
    	List<EbizPackage> packagesList=new ArrayList<>();
    	Connection connection = null;
			try {
		    	
				connection=EbizDataBase.getConnection();
		    	ResultSet rs = searchTrackingOrSn(connection,packageListName, "TrackingNumber", trackingNumber);
				if(rs==null){
					return null;
				}
				
				while (rs.next()) {
					tryconnectionCounter=0;
					EbizPackage pack = rsTopackage(rs);
					packagesList.add(pack);

				}
				return packagesList;
			} catch (NumberFormatException e) {
				CloseConnection(connection,tryconnectionCounter);
				tryconnectionCounter++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
				if(tryconnectionCounter<tryConnectionMaximumCount){
					
					return readPackagesByTracking(trackingNumber);
				}
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				CloseConnection(connection,0);
			}
			return packagesList;
    }
    public EbizPackage existingTrackingRecord(String trackingNumber){
    	wirteLog("find existing tracking record");
    	// search record in database

    	Connection connection = null;
			try {	
				connection=EbizDataBase.getConnection();
		    	ResultSet rs = searchTrackingOrSn(connection,packageListName, "TrackingNumber", trackingNumber);
				if (rs==null){
					rs = searchTrackingOrSn(connection,buyerPackageListName, "TrackingNumber", trackingNumber);
				}
				if(rs==null){
					return null;
				}
				
				while (rs.next()) {
					tryconnectionCounter=0;
					EbizPackage pack = rsTopackage(rs);
				return pack;

				}
			} catch (NumberFormatException e) {
				CloseConnection(connection,tryconnectionCounter);
				tryconnectionCounter++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
				if(tryconnectionCounter<tryConnectionMaximumCount){
					
					return existingTrackingRecord(trackingNumber);
				}
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				CloseConnection(connection,0);
			}
			return null;
    }
    private EbizCompany rsToEbizCompany(ResultSet rs){
    	EbizCompany company = new EbizCompany();
		try {
			company.UID=rs.getInt("id");
			company.companyName=rs.getString("CompanyName");
			company.ownerName=rs.getString("OwnerName");
			company.status=rs.getString("Status");
			company.setPermissions(rs.getString("Permision"));
			company.balance=Double.parseDouble(rs.getString("Balance"));
			company.createdTime=rs.getString("CreatedTime");
			company.note=rs.getString("Note");
			company.setPayTimeInfor(rs.getString("PayTimeInfor"));
			company.setAddressString(rs.getString("Address"));
			company.setEmailAndPasswordString(rs.getString("Email"));
			company.setPhoneNumber(rs.getString("PhoneNumber"));
			company.setUserManual(rs.getString("UserManual"));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return company;	
    }
    private EbizProduct rsToEbizProduct(ResultSet rs){
		EbizProduct product = new EbizProduct();
		try {
			product.UID=Integer.parseInt(rs.getString("id"));
			product.company=rs.getString("CompanyName");
			product.model=rs.getString("Model");
			product.productName=rs.getString("ProductName");
			product.UPC=rs.getString("UPC");
			product.Asin=rs.getString("ASIN");
			product.SKU=rs.getString("SKU");
			product.brand=rs.getString("Brand");
			product.weight=rs.getDouble("Weight");
			product.length=rs.getDouble("Length");
			product.width=rs.getDouble("Width");
			product.height=rs.getDouble("Height");
			product.price=rs.getDouble("Price");
			product.promotQuantity=rs.getInt("PromotQuantity");
			product.promotPrice=rs.getDouble("PromotPrice");
			product.warehousePrice=rs.getInt("WarehousePrice");
			product.warehousePromotQuantity=rs.getInt("WarehousePromotQuantity");
			product.warehousePromotePrice=rs.getDouble("WarehousePromotePrice");
			product.status=rs.getString("Status");
			product.createdTime=rs.getString("CreatedTime");
			product.updateTime=rs.getString("UpdateTime");
			product.tickets=rs.getInt("Tickets");
			product.limitPerPerson=rs.getInt("LimitPerPerson");
			product.operatingStatus=rs.getString("OperatingStatus");
			product.operationRecord=rs.getString("OperationRecord");
			product.setParameterString(rs.getString("ParameterString"));
			product.uRI=rs.getString("URI");
			product.setUserNote(rs.getString("UserNote"));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return product;	
    }
    private EbizUser rsToEbizUser(ResultSet rs){
		EbizUser user = new EbizUser();
		try {
		user.UID=rs.getInt("id");
		user.userName=rs.getString("UserName");
		user.firstName=rs.getString("FirstName");
		user.lastName=rs.getString("LastName");
		user.companyName=rs.getString("CompanyName");
		user.passWord=rs.getString("PassWord");
		//user.tempPassWord=rs.getString("TempPassWord");
		user.setEmail(rs.getString("Email"));
		user.phoneNumber=rs.getString("PhoneNumber");
		user.address=rs.getString("Address");
		user.createTime=rs.getString("CreateTime");
		user.updateTime=rs.getString("UpdateTime");
		user.note=rs.getString("Note");
		user.setStatus(rs.getString("Status"));
		user.setUserType(rs.getString("UserType"));
		user.setUserPermissions(rs.getString("Permissions"));
		user.balance=rs.getDouble("Balance");
		user.personalLimit=rs.getInt("PersonalLimit");
		user.setParameterString( rs.getString("ParameterString"));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return user;
    }
    
	private EbizPackage rsTopackage(ResultSet rs) {
		EbizPackage pack = new EbizPackage();
		try {
			pack.UID = rs.getInt("id");
			pack.company = rs.getString("CompanyName");
			pack.trackingNumber = rs.getString("TrackingNumber");
			pack.shipID = rs.getString("ShipID");
			pack.modelNumber = rs.getString("ModelNumber");
			pack.productName = rs.getString("ProductName");
			pack.condition = rs.getString("ProductCondition");
			pack.UPC = rs.getString("UPC");
			pack.ASIN = rs.getString("ASIN");
			pack.SKU = rs.getString("SKU");
			pack.brand = rs.getString("Brand");
			pack.price = rs.getDouble("Price");
			pack.basePrice = rs.getDouble("BasePrice");
			pack.promPrice = rs.getDouble("PromPrice");
			pack.quantity = rs.getInt("Quantity");
			pack.promQuantity = rs.getInt("PromQuantity");
			pack.storeName = rs.getString("StoreName");
			pack.userName = rs.getString("UserName");
			//System.out.println(pack.userName);
			pack.shippingAddress = rs.getString("ShippingAddress");
			pack.email = rs.getString("Email");
			pack.phoneNumber = rs.getString("PhoneNumber");
			pack.receiver = rs.getString("Receiver");
			pack.recipient = rs.getString("Recipient");
			pack.note = rs.getString("Note");
			pack.createdTime = rs.getString("CreatedTime");
			pack.updateTime = rs.getString("UpdateTime");
			pack.creditcardNumber=rs.getString("CreditCardNumber");
			pack.setStatus(rs.getString("Status"));
			pack.setCheckStatus(rs.getString("CheckStatus"));
			pack.setChecker(rs.getString("Checker"));
			pack.setLabelStatus(rs.getString("LabelStatus"));
			pack.setLabeler(rs.getString("Labeler"));
			pack.setPayStatus(rs.getString("PayStatus"));
			pack.setPayer(rs.getString("Payer"));
			pack.setUserNote(rs.getString("UserNote"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pack;
	}  

	public int addBuyerPackage(EbizPackage pack,EbizUser user){	
		if(pack.userName==null||pack.userName.length()==0){
			pack.userName=getCurrentUserName();	
		}
		Connection connection=null;
        try {
        	
			connection =  EbizDataBase.getConnection();
            PreparedStatement update = null;
            Statement stmt;
        	update=prepareAddPackageStatement(connection,update,buyerPackageListName, pack);
        	
           if ( update.executeUpdate()>0){
        	   stmt = connection.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+buyerPackageListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        	   }
        	  pack.UID=lastid;
        	  pack.company=user.companyName;
        	  buyerPackages.put(pack.UID, pack);
        	  tryconnectionCounter=0;
        	  showMessage("Package Added");
        	  return lastid;           
           }else {
        	   showMessage("BuyerPackage not added, Please try it again");
           }
        } catch (SQLException ex) {
        	CloseConnection(connection,tryconnectionCounter);
        	tryconnectionCounter++;
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        	if (tryconnectionCounter<tryConnectionMaximumCount){	
        		return addBuyerPackage(pack,user);
        	}
        	showMessage("BuyerPackage not added, Please try it again");
           ex.printStackTrace();
        }finally{
        	CloseConnection(connection,0);
        }
        
		return -1;
	}
	
	private PreparedStatement prepareAddPackageStatement(Connection con, PreparedStatement update,String packageListName,  EbizPackage pack) throws SQLException {
		String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
		update = con.prepareStatement("insert into " + packageListName
				+ " (CompanyName,TrackingNumber,ShipID,ModelNumber,ProductName,ProductCondition,UPC,"
				+"ASIN,SKU,Brand,Price,BasePrice,PromPrice,Quantity,SerialNumber,PromQuantity,StoreName,"
				+"UserName,ShippingAddress,Email,PhoneNumber,Receiver,Recipient,Note,"
				+"CreatedTime,UpdateTime,CreditCardNumber,Status,PayStatus,UserNote) "
				+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		update.setString(1, pack.company);
		update.setString(2, pack.trackingNumber);
		update.setString(3, pack.shipID);
		update.setString(4, pack.modelNumber);
		update.setString(5, pack.productName);
		update.setString(6, pack.condition);
		update.setString(7, pack.UPC);
		update.setString(8, pack.ASIN);
		update.setString(9, pack.SKU);
		update.setString(10, pack.brand);
		update.setDouble(11, pack.price);
		update.setDouble(12, pack.basePrice);
		update.setDouble(13, pack.promPrice);
		update.setInt(14, pack.quantity);
		update.setString(15, pack.serialNumber);
		update.setInt(16, pack.promQuantity);
		update.setString(17, pack.storeName);
		update.setString(18, pack.userName);
		update.setString(19, pack.shippingAddress);
		update.setString(20, pack.email);
		update.setString(21, pack.phoneNumber);
		update.setString(22, pack.receiver);
		update.setString(23, pack.recipient);
		update.setString(24, pack.note);
		update.setString(25, timeString);
		update.setString(26, timeString);
		update.setString(27, pack.creditcardNumber);
		update.setString(28, pack.getStatus());
		update.setString(29, pack.getPayStatus());
		update.setString(30, pack.getUserNote());
		wirteLog(update.toString());
		return update;
	}

	public int addUser(EbizUser user,EbizCompany company){
        PreparedStatement pst;
        Statement stmt;
        Connection con=null;
        try {
			con =  EbizDataBase.getConnection();
			pst = con.prepareStatement("insert into " + userListName
					+ " (UserName,FirstName,LastName,CompanyName,PassWord,TempPassWord,"
					+"Email,PhoneNumber,Address,CreateTime,UpdateTime,Note,Status,UserType,Permissions,Balance,PersonalLimit,ParameterString) "
					+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, user.userName);
			pst.setString(2, user.firstName);
			pst.setString(3, user.lastName);
			pst.setString(4, user.companyName);
			pst.setString(5, user.passWord);
			pst.setString(6, user.tempPassWord);
			pst.setString(7, user.getEmailString());
			pst.setString(8, user.phoneNumber);
			pst.setString(9, user.address);
			pst.setString(10, user.createTime);
			pst.setString(11, user.updateTime);
			pst.setString(12, user.note);
			pst.setString(13, user.getStatus());
			pst.setString(14, user.getUserType());
			pst.setString(15, user.getPermissionString());
			pst.setDouble(16, user.balance);
			pst.setInt(17, user.personalLimit);
			pst.setString(18, user.getParametersString());
			wirteLog(pst.toString());
           if ( pst.executeUpdate()>0){
        	   stmt = con.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+userListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        	   }
        	  user.UID=lastid;
       	   addAddOperationRecord(user,company,userListName,lastid);
        return lastid;
           }
        } catch (SQLException ex) {
        	showMessage("User not added, Please try it again");
           ex.printStackTrace();
        }finally{
        	CloseConnection(con,tryconnectionCounter);
        }
		return -1;
	}
	public int addCompany(EbizUser user,EbizCompany company){
		Connection con=null;
		try {
		con =  EbizDataBase.getConnection();
        PreparedStatement pst;
        Statement stmt;
			pst = con.prepareStatement("insert into " + companyListName
					+ " (CompanyName,OwnerName,Status,CreateTime,Permision) "
					+"values(?,?,?,?,?)");
			pst.setString(1, company.companyName);
			pst.setString(2, company.ownerName);
			pst.setString(3, company.status);
			pst.setString(4, company.createdTime);
			pst.setString(5, company.getPermissionString());
			wirteLog(pst.toString());
           if ( pst.executeUpdate()>0){
        	   stmt = con.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+companyListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        	   }
        	   company.UID=lastid;
           	   addAddOperationRecord(user,company,companyListName,lastid);
        	  return lastid;
           }    
        } catch (SQLException ex) {
           ex.printStackTrace();
        }finally{
        	CloseConnection(con,tryconnectionCounter);
        }
		return -1;
	}

	public int addPackage(EbizUser user,EbizCompany company,EbizPackage pack){
		Connection con=null;
        try {
		con =  EbizDataBase.getConnection();
        PreparedStatement update = null;
        Statement stmt;
        	update=prepareAddPackageStatement(con,update,packageListName, pack);
           if ( update.executeUpdate()>0){
        	   stmt = con.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+packageListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        	   }
        	  pack.UID=lastid;
        	  addAddOperationRecord(user,company,packageListName,lastid);
        	  return lastid;
           }
            
        } catch (SQLException ex) {

           ex.printStackTrace();
        }finally{
        	CloseConnection(con,tryconnectionCounter);
        }
		return -1;
	}
	public boolean addReceivedPackage(EbizUser user,EbizCompany company,EbizPackage pack){
		Connection con=null;
        try {
		con =  EbizDataBase.getConnection();
        PreparedStatement update = null;
        Statement stmt;
        	update=prepareAddPackageStatement(con,update,warehouseReceivedPackageListName, pack);
           if ( update.executeUpdate()>0){
        	   stmt = con.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+packageListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        	   }
        	  pack.UID=lastid;
        	  addAddOperationRecord(user,company,warehouseReceivedPackageListName,lastid);
        	  return true;
           }
            
        } catch (SQLException ex) {

           ex.printStackTrace();
        }finally{
        	CloseConnection(con,tryconnectionCounter);
        }
		return false;
	}
	public int addDeal(EbizUser user,EbizCompany company,EbizProduct product){
		Connection con = null;
        try {
    		
			con =  EbizDataBase.getConnection();
            PreparedStatement pst;
            Statement stmt;
			pst = con.prepareStatement("insert into " + dealListName
					+ " (CompanyName,Model,ProductName,UPC,ASIN,SKU,Brand,Weight,Length, Width,Height,Price,"
					+ "PromotQuantity,PromotPrice,WarehousePrice,WarehousePromotQuantity,"
					+ "WarehousePromotePrice,Status,Tickets,LimitPerPerson,ParameterString,URI,OperationRecord,CreatedTime,UpdateTime) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, company.companyName);
            pst.setString(2, product.model);
            pst.setString(3, product.productName);
            pst.setString(4, product.UPC);
            pst.setString(5, product.Asin);
            pst.setString(6, product.SKU);
            pst.setString(7, product.brand);
            pst.setDouble(8, product.weight);
            pst.setDouble(9, product.length);
            pst.setDouble(10, product.width);
            pst.setDouble(11, product.height);
            pst.setDouble(12, product.price);
            pst.setDouble(13, product.promotQuantity);
            pst.setDouble(14, product.promotPrice);
            pst.setDouble(15, product.warehousePrice);
            pst.setDouble(16, product.warehousePromotQuantity);
            pst.setDouble(17, product.warehousePromotePrice);
            pst.setString(18, product.status);
            pst.setInt(19, product.tickets);
            pst.setInt(20, product.limitPerPerson);
            pst.setString(21, product.parameterString);
            pst.setString(22, product.uRI);
            pst.setString(23, product.operationRecord);
            pst.setString(24, product.createdTime);
            pst.setString(25, product.updateTime);
        	wirteLog(pst.toString());
           if ( pst.executeUpdate()>0){
        	   stmt = con.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+productListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        	   }
        	  product.UID=lastid;
        	  product.company=company.companyName;
        	  addAddOperationRecord(user, company, dealListName, lastid);
        	  return lastid;
           }    
        } catch (SQLException ex) {
        	 ex.printStackTrace();
        }finally{
        	CloseConnection(con,0);
        }
		return -1;
	}
	public int addProduct(EbizUser user,EbizCompany company,EbizProduct product){
		Connection con = null;
        try {
    		
			con =  EbizDataBase.getConnection();
            PreparedStatement pst;
            Statement stmt;
			pst = con.prepareStatement("insert into " + productListName
					+ " (CompanyName,Model,ProductName,UPC,ASIN,SKU,Brand,Weight,Length, Width,Height,Price,"
					+ "PromotQuantity,PromotPrice,WarehousePrice,WarehousePromotQuantity,"
					+ "WarehousePromotePrice,Status,Tickets,LimitPerPerson,ParameterString,URI,OperationRecord,CreatedTime,UpdateTime,UserNote) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, company.companyName);
            pst.setString(2, product.model);
            pst.setString(3, product.productName);
            pst.setString(4, product.UPC);
            pst.setString(5, product.Asin);
            pst.setString(6, product.SKU);
            pst.setString(7, product.brand);
            pst.setDouble(8, product.weight);
            pst.setDouble(9, product.length);
            pst.setDouble(10, product.width);
            pst.setDouble(11, product.height);
            pst.setDouble(12, product.price);
            pst.setDouble(13, product.promotQuantity);
            pst.setDouble(14, product.promotPrice);
            pst.setDouble(15, product.warehousePrice);
            pst.setDouble(16, product.warehousePromotQuantity);
            pst.setDouble(17, product.warehousePromotePrice);
            pst.setString(18, product.status);
            pst.setInt(19, product.tickets);
            pst.setInt(20, product.limitPerPerson);
            pst.setString(21, product.parameterString);
            pst.setString(22, product.uRI);
            pst.setString(23, product.operationRecord);
            pst.setString(24, product.createdTime);
            pst.setString(25, product.updateTime);
            pst.setString(26, product.getUserNote());
        	wirteLog(pst.toString());
           if ( pst.executeUpdate()>0){
        	   stmt = con.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+productListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        	   }
        	  product.UID=lastid;
        	  product.company=company.companyName;
        	  addAddOperationRecord(user, company, productListName, lastid);
        	  return lastid;
           }
            
        } catch (SQLException ex) {
        	 ex.printStackTrace(); 
        }finally{
        	CloseConnection(con,0);
        }
		return -1;
	}
	
	public boolean createWarehouseOrderTable() {
		
   		
	    String sqlCreate =  "CREATE TABLE IF NOT EXISTS " +warehouseOrderListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " CompanyName  VARCHAR(50),"
	            + " OrderNumber  VARCHAR(50),"
	            + " Model  VARCHAR(200),"
	            + " Name     VARCHAR(300),"
	            + " ProductCondition     VARCHAR(30),"
	            + " UPC     VARCHAR(30),"
	            + " ASIN     VARCHAR(100),"
	            + " SKU     VARCHAR(100),"
	            + " Brand			VARCHAR(30),"
	            + " Price			DOUBLE,"
	            + " Quantity		INTEGER,"
	            + " Shipped			INTEGER,"
	            + " StoreName		VARCHAR(30),"
	            + " UserName		VARCHAR(50),"
	            + " ReceiverName		VARCHAR(50),"
	            + " ShippingAddress		VARCHAR(150),"
	            + " Email		VARCHAR(50),"
	            + " PhoneNumber		VARCHAR(30),"
	            + " CreatedTime		VARCHAR(30),"
	            + " UpdateTime		VARCHAR(30),"
	            + " Note		VARCHAR(300),"
	            + " Packages		INTEGER,"
	            + " WareHouse		VARCHAR(30),"
	            + " Status		VARCHAR(30),"
	            + " PRIMARY KEY  (id))";
	    Statement stmt;
	    Connection connection=null;
		try {
			connection = EbizDataBase.getConnection();
			stmt = connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, warehouseOrderListName, null);
			tryconnectionCounter=0;
			if (tables.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if (tryconnectionCounter<tryConnectionMaximumCount){

				createWarehouseOrderTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
		}finally {
			CloseConnection(connection,0);
		}
	}
	
	public boolean createInventoryTable() {
	    String sqlCreate =  "CREATE TABLE IF NOT EXISTS " +inventoryListName
	            + " (id MEDIUMINT(9) NOT NULL AUTO_INCREMENT,"
	            + " CompanyName  VARCHAR(50),"
	            + " Model  VARCHAR(200),"
	            + " Name     VARCHAR(300),"
	            + " ProductCondition     VARCHAR(30),"
	            + " UPC     VARCHAR(30),"
	            + " ASIN     VARCHAR(100),"
	            + " SKU     VARCHAR(100),"
	            + " Brand			VARCHAR(30),"
	            + " Price			DOUBLE,"
	            + " Received		INTEGER,"
	            + " InStock		INTEGER,"
	            + " PreSend		INTEGER,"
	            + " Shipping		INTEGER,"
	            + " TotalShipped		INTEGER,"
	            + " CreatedTime		VARCHAR(30),"
	            + " UpdateTime		VARCHAR(30),"
	            + " Note		VARCHAR(300),"
	            + " WareHouse		VARCHAR(30),"
	            + " Status		VARCHAR(20),"
	            + " PRIMARY KEY  (id))";

	    Statement stmt;
	    Connection connection=null;
		try {
			connection = EbizDataBase.getConnection();
			stmt = connection.createStatement();
			wirteLog(sqlCreate.toString());
			stmt.execute(sqlCreate);
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, inventoryListName, null);
			tryconnectionCounter=0;
			if (tables.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if (tryconnectionCounter<tryConnectionMaximumCount){

				createInventoryTable();
			}
			creatTablefailed();			
			e.printStackTrace();
			return false;
		}finally{
			CloseConnection(connection,0);
		}
	}

	public ResultSet searchSet(Connection connection,String searchAttribute,String tableName,String attribute, String attributeValue) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql = "select "+searchAttribute +" from "+tableName+" where "+attribute+"="+"'"+attributeValue+"'"+" ORDER BY id DESC";    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	public ResultSet searchPackageSetInTwodays(Connection connection,String tableName,String attribute1, String attributeValue1,String attribute2, String attributeValue2) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql = "select * from "+tableName+" where date(CreatedTime)>DATE_SUB(NOW(), INTERVAL 48 HOUR) AND "+attribute1+"='"+attributeValue1+"' AND "+attribute2+"='"+attributeValue2+"'"+" ORDER BY id DESC";    //要执锟叫碉拷SQL
			ResultSet rs = stmt.executeQuery(sql);	
            return rs;
	}
	public ResultSet searchSet(Connection connection,String searchAttribute,String tableName,String attribute1, String attributeValue1,String attribute2, String attributeValue2) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql = "select "+searchAttribute +" from "+tableName+" where "+attribute1+"='"+attributeValue1+"' AND "+attribute2+"='"+attributeValue2+"'"+" ORDER BY id DESC";    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	public ResultSet searchSet(Connection connection,String tableName,String attribute1, String attributeValue1,String attribute2, String attributeValue2) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql = "select * from "+tableName+" where "+attribute1+"='"+attributeValue1+"' AND "+attribute2+"='"+attributeValue2+"'"+" ORDER BY id DESC";    //要执锟叫碉拷SQL
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	public ResultSet searchAllActiveAndAliveDealProductsSet(Connection connection,String companyName) throws SQLException{
		Statement stmt;
		stmt = connection.createStatement();
		String sql = "select * from "+productListName+" where CompanyName='"+companyName+"' AND (Status='"+EbizStatusEnum.Active.getName()+"' OR Status='"+EbizStatusEnum.LiveDeal.getName()+"') ORDER BY UpdateTime DESC";    //要执锟叫碉拷SQL
		wirteLog(sql.toString());
		ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
		return rs;
	}
	public ResultSet searchAllActiveAndAliveDealProductsSet(Connection connection,String companyName,int offset,int rowNumber) throws SQLException{
		Statement stmt;
		stmt = connection.createStatement();
		String sql = "select * from "+productListName+" where CompanyName='"+companyName+"' AND (Status='"+EbizStatusEnum.Active.getName()+"' OR Status='"+EbizStatusEnum.LiveDeal.getName()+"') ORDER BY UpdateTime DESC LIMIT "+rowNumber+" OFFSET "+offset;  //要执锟叫碉拷SQL
		wirteLog(sql.toString());
		ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
		return rs;
	}
	public ResultSet searchSet(Connection connection,String tableName,String attribute1, String attributeValue1,String attribute2, String attributeValue2,int limit) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql = "select * from "+tableName+" where "+attribute1+"='"+attributeValue1+"' AND "+attribute2+"='"+attributeValue2+"'"+" ORDER BY id DESC LIMIT "+limit;    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	
	public ResultSet searchUserNameAndPassword(Connection connection,String userName, String passWord ) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql = "select * from "+userListName+" where UserName='"+userName+"' AND PassWord='"+passWord+"'"+" ORDER BY id DESC";    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	public ResultSet searchSet(Connection connection,String tableName,String attribute, String attributeValue) throws SQLException{
		Statement stmt;

			stmt = connection.createStatement();
			String sql = "select * from "+tableName+" where "+attribute+"="+"'"+attributeValue+"'" +" ORDER BY id DESC";    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	public ResultSet searchUserSet(Connection connection, int id) throws SQLException{
		Statement stmt;

			stmt = connection.createStatement();
			String sql = "select * from "+userListName+" where id="+id +" LIMIT 1";    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	
	public ResultSet searchTrackingOrSn(Connection connection,String tableName,String attribute, String attributeValue) throws SQLException{
		Statement stmt;

			stmt = connection.createStatement();
			String sql = "SELECT * FROM " + tableName + " WHERE '" + attributeValue + "' LIKE CONCAT(" + "'%',"
					+ attribute + ",'%') AND Length("+attribute+")>5 OR " + attribute + " LIKE '%" + attributeValue + "%'"+" ORDER BY id DESC";
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);// 锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	
	public ResultSet searchSet(Connection connection,String tableName,String attribute, int attributeValue) throws SQLException{
		Statement stmt;

			stmt = connection.createStatement();
			String sql = "select * from "+tableName+" where "+attribute+"="+attributeValue;    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;	
	}
	public ResultSet searchSet(Connection connection,String tableName,String attribute, int attributeValue,int limit) throws SQLException{
		Statement stmt;

			stmt = connection.createStatement();
			String sql = "select * from "+tableName+" where "+attribute+"="+attributeValue+" LIMIT "+limit;    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;	
	}
	
	
	public ResultSet searchInventroy(Connection connection,String company, String model,String condition,String warehouse) throws SQLException{
		Statement stmt;
 
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+inventoryListName+" where CompanyName= '"+company+"' AND Model='" +model+"' AND ProductCondition='" +condition+"' AND WareHouse='"+warehouse+"'"+" ORDER BY id DESC";
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	
	}

	public ResultSet searchSetOfInventory(Connection connection,String company,int offset,int rowNumber) throws SQLException{

		Statement stmt;

			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+inventoryListName+" where CompanyName= '"+company+"'";
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
	}
	
	public ResultSet searchSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user,String status) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			
			if(status!=null&&status.length()!=0){
				sql= "select * from "+tableName+" where Status='"+status+"' AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
			}else{
				sql= "select * from "+tableName+" where UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
			}

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user,String status1,String status2) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			
			sql= "select * from "+tableName+" where (Status='"+status1+"' OR Status='"+status2+"') AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
			

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user,String status1,String status2,String status3) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			
			sql= "select * from "+tableName+" where (Status='"+status1+"' OR Status='"+status2+"' OR Status='"+status3+"') AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
			

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user,String status1,String status2,String status3,String status4) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			
			sql= "select * from "+tableName+" where (Status='"+status1+"' OR Status='"+status2+"' OR Status='"+status3+"' OR Status='"+status4+"') AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
			

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchUnConfirmedSellOrOBOPackSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
		
			sql= "select * from "+tableName+" where (Status LIKE '%UnConfirmed%' OR Status='Refused') AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchUnReceivedAndUnConfirmedSellOrOBOPackSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			String status=EbizPackageStatusEnum.UnReceived.getColumnName();
			sql= "select * from "+tableName+" where (Status='"+status+"' OR Status LIKE '%UnConfirmed%' OR Status='Refused') AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);// 创建数据对象 
            return rs;
		
	}
	public ResultSet searchUnpaidSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			sql= "select * from "+tableName+" where NOT Status='Deleted' AND PayStatus!='"+EbizPackagePayStatusEnum.Paid.getName()+"' AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchPaidSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			

			sql= "select * from "+tableName+" where NOT Status='Deleted' AND PayStatus='"+EbizPackagePayStatusEnum.Paid.getName()+"' AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;


			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchAllSetForUser(Connection connection,String tableName,int offset,int rowNumber,EbizUser user) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			

			sql= "select * from "+tableName+" where NOT Status='Deleted' AND UserName='"+user.userName+"' ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;


			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}

	public ResultSet searchCompanySetForDoctor(Connection connection, EbizUser user) throws SQLException {
		Statement stmt;
		stmt = connection.createStatement();
		String sql;
		sql = "select * from " + companyListName + " where CompanyName= '" + user.companyName + "' ORDER BY id DESC LIMIT 1";
		ResultSet rs = stmt.executeQuery(sql);// 锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
		return rs;

	}
	public ResultSet searchDoctorUserSet(Connection connection,int offset,int rowNumber) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
				sql= "select * from "+userListName+" where UserType= '"+EbizUserTypeEnum.Doctor.getName()+"'"+" ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;    //要执锟叫碉拷SQL

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchActiveDoctorUserSet(Connection connection,int offset,int rowNumber) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
				sql= "select * from "+userListName+" where UserType= '"+EbizUserTypeEnum.Doctor.getName()+"' AND Status LIKE 'Active%'"+" ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;    //要执锟叫碉拷SQL

			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchAllNonDeletedProductSet(Connection connection,int offset,int rowNumber,String companyName) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
				sql= "select * from "+productListName+" where NOT Status='Deleted' AND CompanyName= '"+companyName+"'"+" ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public ResultSet searchSet(Connection connection,String tableName,int offset,int rowNumber,EbizUser user) throws SQLException{
		Statement stmt;
			stmt = connection.createStatement();
			String sql;
			if (user==null||user.userName.toLowerCase().equals(masterUserNameString.toLowerCase())||tableName.equals(companyListName)){
				sql= "select * from "+tableName+" ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;    //要执锟叫碉拷SQL
				// people has these two permissions can see all packages for the company
			}else if(tableName.equals(packageListName)){
				if (user.getUserPermissions().contains(EbizUserPermissionEnum.UserAnalysis.getName())){
					
					sql= "select * from "+tableName+" where CompanyName= '"+user.companyName+"'"+" ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
					
				}			
				//锟斤拷锟角癸拷司锟斤拷锟斤拷锟斤拷员也锟角癸拷锟斤拷锟斤拷员锟斤拷锟杰匡拷锟斤拷锟斤拷司锟斤拷锟皆硷拷锟斤拷锟斤拷锟斤拷锟絧ackage
				else  if((user.getUserPermissions().contains(EbizUserPermissionEnum.PayPackage.getName()))
						&&user.getUserPermissions().contains(EbizUserPermissionEnum.ReceivePackage.getName())){

					if(user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())){
						sql= "select * from "+tableName+" where NOT Status='Deleted' AND (UserName= '"+user.userName+"' OR Receiver='" +user.userName+"' OR CompanyName='" +user.companyName+"') ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
						
					}else{
						sql= "select * from "+tableName+" where NOT Status='Deleted' AND (Receiver= '"+user.userName+"' OR CompanyName='" +user.companyName+"') ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
					}
				
					
				}
				//锟斤拷司锟斤拷锟斤拷锟斤拷员 只锟杰匡拷锟斤拷锟皆硷拷锟斤拷司锟侥伙拷锟斤拷			
				else if(user.getUserPermissions().contains(EbizUserPermissionEnum.PayPackage.getName())){
					sql= "select * from "+tableName+" where NOT Status='Deleted' AND (CompanyName= '"+user.companyName+"')"+" ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;    //要执锟叫碉拷SQL
					//锟街匡拷锟秸伙拷锟斤拷员锟斤拷锟竭伙拷士只锟杰匡拷锟斤拷锟皆硷拷锟斤拷锟斤拷幕锟斤拷锟�
				}else if(user.getUserPermissions().contains(EbizUserPermissionEnum.ReceivePackage.getName())){
					if(user.getUserPermissions().contains(EbizUserPermissionEnum.ReportPackage.getName())){
						sql= "select * from "+tableName+" where NOT Status='Deleted' AND (UserName= '"+user.userName+"' OR Receiver='" +user.userName+"') ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
						
					}else{
						sql= "select * from "+tableName+" where NOT Status='Deleted' AND (Receiver= '"+user.userName+"')"+" ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;
					}
				} else {
					String deletString = EbizPackageStatusEnum.Deleted.getColumnName();
					sql = "select * from " + tableName + " where UserName= '" + user.userName + "' AND NOT Status='"
							+ deletString + "' ORDER BY id DESC LIMIT " + rowNumber + " OFFSET " + offset;
				}
				} else if(tableName.equals(productListName)
					||tableName.equals(inventoryListName)
					||tableName.equals(buyerPackageListName)
					||tableName.equals(warehouseOrderListName)
					||tableName.equals(userListName)
					||tableName.equals(companyListName)
					||tableName.equals(dealListName)){
				sql= "select * from "+tableName+" where CompanyName= '"+user.companyName+"'"+" ORDER BY id DESC LIMIT "+rowNumber+" OFFSET "+offset;    //要执锟叫碉拷SQL

			} else {
				sql = "select * from " + tableName + " where UserName= '" + user.userName + "'" + " ORDER BY id DESC LIMIT " + rowNumber
						+ " OFFSET " + offset;
			}
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            return rs;
		
	}
	public static void createAllTable(){
		//createSNTable();
		//createBuyerPackageTable();
		//createPackageTable();
		//createProductTable();
		//createWarehouseOrderTable();
		//createInventoryTable();
		//createUserTable();
		//createCompanyTable();
		//createDealListTable();

		
	}
	public boolean ifExist(String tableName, int id){
		Statement stmt;
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			String sql = "select * from "+tableName+" where id="+id;    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
            if(rs.getRow()>0){
            	tryconnectionCounter=0;
            	return true;
            }
            
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){

				return ifExist(tableName, id);
			}
			showMessage("Can not read product list, please check connection");
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		
		return false;
		
	}
	
	public String getCurrentUserName(){

		Statement stmt;
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT CURRENT_USER()";    //要执锟叫碉拷SQL
			wirteLog(sql.toString());
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			
			if (rs.next()){
				tryconnectionCounter=0;
				String nameString=rs.getString(1).split("@")[0];
				return nameString;
			}
			
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){

				return getCurrentUserName();
			}
			e.printStackTrace();
		}finally {
			CloseConnection(connection,0);
		}
		return "";
	
	
		
	}
	public int getRowCount(String tableName){
		Statement stmt;
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT COUNT(*) from "+tableName;    //要执锟叫碉拷SQL
			ResultSet rs = stmt.executeQuery(sql);//锟斤拷锟斤拷锟斤拷锟捷讹拷锟斤拷
			int temp=rs.getInt(0);
			tryconnectionCounter=0;
            return temp;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){

				return getRowCount(tableName);
			}
			e.printStackTrace();
		} finally{
			CloseConnection(connection,0);
		}
		return -1;
	
	}
	public static void creatTablefailed(){
		System.out.println( "table was not created, please try again");
	}
	public static void showMessage(String msg){
		System.out.println(msg);
	}


	
	
	public boolean updateSN(int uid,String SNString, boolean showDialog) {
		return addSN(uid, SNString);
	}
	
	public boolean updateWarehouseOrder(EbizWarehouseOrder inventoryOrder, boolean showDialog) {
		
		   PreparedStatement update;
		   Connection con=null;
		try {
			con =  EbizDataBase.getConnection();
			update = con.prepareStatement("UPDATE " + warehouseOrderListName+" SET"
					+" OrderNumber=?,Model=?,Name=?,ProductCondition=?,UPC=?,ASIN=?,SKU=?,"
					+"Brand=?,Price=?,Quantity=?,Shipped=?,StoreName=?,UserName=?,ReceiverName=?,"
					+ "ShippingAddress=?,Email=?,PhoneNumber=?,CreatedTime=?,UpdateTime=?,Note=?,Packages=?,WareHouse=?,Status=? "
					+"WHERE id=?");
			
     	   if(!inventoryOrder.orderNumber.contains("MRB")){
    		   inventoryOrder.orderNumber="MRB"+inventoryOrder.UID;
    	   }
			update.setString(1, inventoryOrder.orderNumber);
			update.setString(2, inventoryOrder.modelNumber);
			update.setString(3, inventoryOrder.productName);
			update.setString(4, inventoryOrder.condition);
			update.setString(5, inventoryOrder.UPC);
			update.setString(6, inventoryOrder.ASIN);
			update.setString(7, inventoryOrder.SKU);
			update.setString(8, inventoryOrder.brand);
			update.setDouble(9, inventoryOrder.price);
			update.setInt(10, inventoryOrder.quantity);
			update.setInt(11, inventoryOrder.shipped);
			update.setString(12, inventoryOrder.storeName);
			update.setString(13, inventoryOrder.userName);
			update.setString(14, inventoryOrder.receiverName);
			update.setString(15, inventoryOrder.shippingAddress);
			update.setString(16, inventoryOrder.email);
			update.setString(17, inventoryOrder.phoneNumber);
			update.setString(18, inventoryOrder.createTime);
			update.setString(19, inventoryOrder.updateTime);
			update.setString(20, inventoryOrder.note);
			update.setInt(21, inventoryOrder.packages);
			update.setString(22, inventoryOrder.warehouse);
			update.setString(23, inventoryOrder.status);
			update.setInt(24, inventoryOrder.UID);
			wirteLog(update.toString());
			if ( update.executeUpdate()>0){
				warehouseOrders.put(inventoryOrder.UID, inventoryOrder);
				tryconnectionCounter=0;
				if (showDialog){
					
					showMessage("Order updated");
				}
				return true;
			}else{
				tryconnectionCounter=0;
				if (showDialog){
					
					showMessage("Order update failed");
				}
				return false;
			}
		} catch (SQLException e) {
			CloseConnection(con,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){

				return updateWarehouseOrder(inventoryOrder, showDialog);
			}
			if (showDialog){
				showMessage("Order update failed, please check connection");
			}
			e.printStackTrace();
		}finally{
			CloseConnection(con,0);
		}
		return false;	
	}
	
	

	public boolean updateInventory(EbizInventory inventory, boolean showDialog) {
		
		   PreparedStatement update;
		   Connection con=null;
		try {
			con =  EbizDataBase.getConnection();
			update = con.prepareStatement("UPDATE " + inventoryListName+" SET"
					+" Model=?,Name=?,ProductCondition=?,UPC=?,ASIN=?,SKU=?,"
					+"Brand=?,Price=?,Received=?,InStock=?,PreSend=?,Shipping=?,"
					+"TotalShipped=?,CreatedTime=?,UpdateTime=?,Note=?,"
					+"WareHouse=? "
					+"WHERE id=?");

			update.setString(1, inventory.modelNumber);
			update.setString(2, inventory.productName);
			update.setString(3, inventory.condition);
			update.setString(4, inventory.UPC);
			update.setString(5, inventory.ASIN);
			update.setString(6, inventory.SKU);
			update.setString(7, inventory.brand);
			update.setDouble(8, inventory.price);
			update.setInt(9, inventory.received);
			update.setInt(10, inventory.inStock);
			update.setInt(11, inventory.quantityPreSend);
			update.setInt(12, inventory.quantityShipping);
			update.setInt(13, inventory.quantityShipped);
			update.setString(14, inventory.createdTime);
			update.setString(15, inventory.updatedTime);
			update.setString(16, inventory.note);
			update.setString(17, inventory.warehouse);
			update.setInt(18, inventory.UID);
			wirteLog(update.toString());
			if ( update.executeUpdate()>0){
				tryconnectionCounter=0;
				inventorys.put(inventory.getKeyString(), inventory);
				//if (showDialog){
				
					showMessage("Inventory updated");
				//}
				return true;
			}else{
				//if (showDialog){
				tryconnectionCounter=0;
					showMessage("Inventory update failed");
				//}
				return false;
			}
		} catch (SQLException e) {
			CloseConnection(con,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return updateInventory(inventory, showDialog);
			}
			e.printStackTrace();
		}finally{
			CloseConnection(con,0);
		}
		return false;	
	}
	public boolean addSN(int uid, String SNString){
		if (!createSNTable()){
			showMessage("SN table can not be created in database, Please check and try it again");
			return false;
		}
		Connection con = null ;
		try {
			con= EbizDataBase.getConnection();
			PreparedStatement update;
			update = con.prepareStatement("INSERT INTO " + SNListName
					+ " (id,SNString) values(?,?) ON DUPLICATE KEY UPDATE SNString=VALUES(SNString)");
			update.setInt(1, uid);
			update.setString(2, SNString);
			wirteLog(update.toString());
			if (update.executeUpdate() > 0) {
				tryconnectionCounter=0;
				return true;
			} else {
				tryconnectionCounter=0;
				showMessage("SN not added, Please try it again");
			}
		} catch (SQLException ex) {
			CloseConnection(con,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return addSN(uid, SNString);
			}
        	showMessage("Order not added, Please try it again");
           ex.printStackTrace();
        }finally{
        	CloseConnection(con,0);
        }
		return false;
	}
	
	public int addWarehouseOrder(EbizWarehouseOrder inventoryOrder,EbizUser user) {
		Connection con = null;
        try {
		con=  EbizDataBase.getConnection();
        PreparedStatement update;
        Statement stmt;
			update = con.prepareStatement("insert into " + warehouseOrderListName
					+ " (CompanyName,OrderNumber,Model,Name,ProductCondition,UPC,ASIN,SKU,"
					+"Brand,Price,Quantity,Shipped,StoreName,UserName,ReceiverName,ShippingAddress,"
					+ "Email,PhoneNumber,CreatedTime,UpdateTime,Note,WareHouse,Status) "
					+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			update.setString(1, user.companyName);
			update.setString(2, inventoryOrder.orderNumber);
			update.setString(3, inventoryOrder.modelNumber);
			update.setString(4, inventoryOrder.productName);
			update.setString(5, inventoryOrder.condition);
			update.setString(6, inventoryOrder.UPC);
			update.setString(7, inventoryOrder.ASIN);
			update.setString(8, inventoryOrder.SKU);
			update.setString(9, inventoryOrder.brand);
			update.setDouble(10, inventoryOrder.price);
			update.setInt(11, inventoryOrder.quantity);
			update.setInt(12, inventoryOrder.shipped);
			update.setString(13, inventoryOrder.storeName);
			update.setString(14, inventoryOrder.userName);
			update.setString(15, inventoryOrder.receiverName);
			update.setString(16, inventoryOrder.shippingAddress);
			update.setString(17, inventoryOrder.email);
			update.setString(18, inventoryOrder.phoneNumber);
			update.setString(19, inventoryOrder.createTime);
			update.setString(20, inventoryOrder.updateTime);
			update.setString(21, inventoryOrder.note);
			update.setString(22, inventoryOrder.warehouse);
			update.setString(23, inventoryOrder.status);
			wirteLog(update.toString());
           if ( update.executeUpdate()>0){
        	   stmt = con.createStatement();
        	   ResultSet rs=stmt.executeQuery("SELECT MAX(id) AS id FROM "+warehouseOrderListName);
        	   int lastid = 0;
        	   while (rs.next()){
        		   lastid = rs.getInt("id");
        	   }
        	   inventoryOrder.UID=lastid;
        	   inventoryOrder.company=user.companyName;
        	   if(!inventoryOrder.orderNumber.contains("MRB")){
        		   inventoryOrder.orderNumber="MRB"+inventoryOrder.UID;
        		   updateWarehouseOrder(inventoryOrder, false);
        	   }
        	  warehouseOrders.put(inventoryOrder.UID, inventoryOrder);
        	  tryconnectionCounter=0;
        	  showMessage("Order Added");
        	  return lastid;
           }else {
        	   tryconnectionCounter=0;
        	   showMessage("Order not added, Please try it again");
           }
        } catch (SQLException ex) {
			CloseConnection(con,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return addWarehouseOrder(inventoryOrder,user);
			}
        	showMessage("Order not added, Please try it again");
           ex.printStackTrace();
        }finally{
        	CloseConnection(con,0);
        }
		return -1;
	}
	
	public EbizInventory findInventory(String company,String model,String condition,String warehouse){
		wirteLog("find Inventory");
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchInventroy(connection,company,model,condition,warehouse);
			if (rs==null){
				return null;
			}
			while (rs.next()) {
				double price = Double.parseDouble(rs.getString(10));
				EbizInventory inventory = new EbizInventory(Integer.parseInt(rs.getString(1)), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
						rs.getString(8), rs.getString(9), price, Integer.parseInt(rs.getString(11)),Integer.parseInt(rs.getString(12)),Integer.parseInt(rs.getString(13)),
						Integer.parseInt(rs.getString(14)), Integer.parseInt(rs.getString(15)),
						Integer.parseInt(rs.getString(16)), Integer.parseInt(rs.getString(17)), rs.getString(18),
						rs.getString(19), rs.getString(20), rs.getString(21));
				tryconnectionCounter=0;
				return inventory;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return findInventory(company, model, condition, warehouse);
			}
			e.printStackTrace();
		}finally{
			CloseConnection(connection,0);
		}
		return null;
	}

	public int addInventory(EbizInventory inventory,EbizUser user) {
		wirteLog("Add Inventory");
		Connection con=null;
		try {
			con = EbizDataBase.getConnection();
			PreparedStatement update;
			Statement stmt;
			update = con.prepareStatement(
					"insert into " + inventoryListName + " (CompanyName,Model,Name,ProductCondition,UPC,ASIN,SKU,"
							+ "Brand,Price,Received,InStock,PreSend,Shipping,"
							+ "TotalShipped,CreatedTime,UpdateTime,Note,WareHouse) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			update.setString(1, user.companyName);
			update.setString(2, inventory.modelNumber);
			update.setString(3, inventory.productName);
			update.setString(4, inventory.condition);
			update.setString(5, inventory.UPC);
			update.setString(6, inventory.ASIN);
			update.setString(7, inventory.SKU);
			update.setString(8, inventory.brand);
			update.setDouble(9, inventory.price);
			update.setInt(10, inventory.received);
			update.setInt(11, inventory.inStock);
			update.setInt(12, inventory.quantityPreSend);
			update.setInt(13, inventory.quantityShipping);
			update.setInt(14, inventory.quantityShipped);
			update.setString(15, inventory.createdTime);
			update.setString(16, inventory.updatedTime);
			update.setString(17, inventory.note);
			update.setString(18, inventory.warehouse);
			wirteLog(update.toString());
			if (update.executeUpdate() > 0) {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS id FROM " + inventoryListName);
				int lastid = 0;
				while (rs.next()) {
					lastid = rs.getInt("id");
				}
				inventory.UID = lastid;
				inventory.company = user.companyName;
				inventorys.put(inventory.getKeyString(), inventory);
				tryconnectionCounter=0;
				return lastid;
			} else {
				tryconnectionCounter=0;
				showMessage("Inventory not added, Please try it again");
			}

		} catch (SQLException ex) {
			CloseConnection(con,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return addInventory(inventory,user);
			}
        	showMessage("Inventory not added, Please try it again");
           ex.printStackTrace();
        }finally{
			CloseConnection(con,0);
		}
		return -1;
	}
	public ConcurrentHashMap<Integer, EbizWarehouseOrder> getWareHouseOrders() {
		return warehouseOrders;
	}
	public ConcurrentHashMap<String, EbizInventory> getInventorys() {
		return inventorys;
	}
	public String readSNForOneWareHouseOrder(int uid) {
		createSNTable();
		Connection connection=null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection,SNListName,"id", uid);
			if (rs==null){
				return null;
			}
			while (rs.next()) {	
				tryconnectionCounter=0;
				String sNsString=rs.getString(2);
				return sNsString;
			}
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return readSNForOneWareHouseOrder(uid);
			}
			e.printStackTrace();
		}finally{
			CloseConnection(connection,0);
		}
		return null;
	}
	public ResultSet findSN(Connection connection,String sNString) throws SQLException {
			ResultSet rs = searchTrackingOrSn(connection,SNListName,"SNString", sNString);
			if (rs==null){
				return null;
			}
			while (rs.next()) {	
				int packageId=Integer.parseInt(rs.getString(1));
				ResultSet rs1=searchSet(connection,buyerPackageListName, "id", packageId);
				return rs1;
			}
			return null;
	}
	
	public ConcurrentHashMap<Integer, EbizWarehouseOrder> readAllWarehouseOrders(boolean loadmore,boolean refresh,EbizUser user) {
		//createWarehouseOrderTable();
		int offset=0;
		int rownumber=loadrowNumber;
		if(loadmore){
			offset=warehouseOrders.size();
			rownumber=loadrowNumber;
		}else if(refresh){
			offset=0;
			rownumber=warehouseOrders.size();
		}
		Connection connection = null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSet(connection,warehouseOrderListName,offset,rownumber,user);
			if (rs==null){
				return null;
			}
			if(!loadmore){
				warehouseOrders.clear();
			}
			while (rs.next()) {
				String pack=rs.getString(23);
				int packages=0;
				if (pack!=null){
					packages=Integer.parseInt(pack);
				}
				EbizWarehouseOrder warehouseOrder = new EbizWarehouseOrder(Integer.parseInt(rs.getString(1)),
						rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						Double.parseDouble(rs.getString(11)), Integer.parseInt(rs.getString(12)),
						Integer.parseInt(rs.getString(13)), rs.getString(14), rs.getString(15), rs.getString(16),
						rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21),
						rs.getString(22), packages,rs.getString(24), rs.getString(25));
				if (warehouseOrders.get(warehouseOrder.UID) == null) {
					warehouseOrders.put(warehouseOrder.UID, warehouseOrder);
				}
			}
			tryconnectionCounter=0;
			return warehouseOrders;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return readAllWarehouseOrders(loadmore, refresh,user);
			}
			if(e.getMessage().contains("doesn't exist")){
				createWarehouseOrderTable();
				return readAllWarehouseOrders(loadmore,refresh,user);
			}
			e.printStackTrace();
		}finally{
			CloseConnection(connection,0);
		}
		return null;
	}
	
	private void CloseConnection(Connection connection, int tryconnectionCounter2){
		if(tryconnectionCounter2>=2){
			EbizDispatcher.getInstance().getDataBase().Close();		
			EbizDataBase.setNewDataSource();
		}
		try {
			if(connection==null){
				return;
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ConcurrentHashMap<String, EbizInventory> readAllInventorys(boolean loadmore,boolean refresh,EbizUser user) {
		//createInventoryTable();
		int offset=0;
		int rownumber=loadrowNumber;
		if(loadmore){
			offset=inventorys.size();
			rownumber=loadrowNumber;
		}else if(refresh){
			offset=0;
			rownumber=inventorys.size();
		}
		Connection connection = null;
		try {
			connection=EbizDataBase.getConnection();
			ResultSet rs = searchSetOfInventory(connection,user.companyName,offset,rownumber);
			if (rs==null){
				CloseConnection(connection,tryconnectionCounter);
				return null;
			}
			if(!loadmore){
				inventorys.clear();
			}
			while (rs.next()) {
				double price = Double.parseDouble(rs.getString(10));				
				EbizInventory inventory = new EbizInventory(Integer.parseInt(rs.getString(1)), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
						rs.getString(8), rs.getString(9), price, 0,0,0,0,0,0,0, rs.getString(16),
						rs.getString(17), rs.getString(18), rs.getString(19));
				if (inventorys.get(inventory.getKeyString()) == null) {
					inventorys.put(inventory.getKeyString(), inventory);
				}
			}
			tryconnectionCounter=0;
			return inventorys;
		} catch (SQLException e) {
			CloseConnection(connection,tryconnectionCounter);
			tryconnectionCounter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(tryconnectionCounter<tryConnectionMaximumCount){
				return readAllInventorys(loadmore, refresh,user);
			}
			if(e.getMessage().contains("doesn't exist")){
				createInventoryTable();
				return readAllInventorys(loadmore,refresh,user);
			}
			e.printStackTrace();
		}finally{
			CloseConnection(connection,0);
		}
		return null;
	}
	public static void deletAllTable(){
	}
	
	public static void main(String args[]) {


        }

	public ConcurrentHashMap<Integer, EbizProduct> getDeals() {
		return deals;
	}
}