package dataCenter;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import ebizTools.GeneralMethod;
public final class EbizDataBase {
	private static BasicDataSource dataSource;
	public EbizDataBase() {
		setNewDataSource();
	}
	private static void output() {
		// System.out.println("getMinIdle:"+dataSource.getMinIdle());
		// System.out.println("getMaxIdle:"+dataSource.getMaxIdle());
		//// System.out.println("getMaxActive:"+dataSource.getMaxActive());
		// System.out.println("getInitialSize:"+dataSource.getInitialSize());
		// System.out.println("getNumActive:"+remoteDataSource.getNumActive());
		// System.out.println("getNumIdle:"+dataSource.getNumIdle());
		// System.out.println("getMaxOpenPreparedStatements:"+dataSource.getMaxOpenPreparedStatements());
		System.out.println("get connection at: " + GeneralMethod.getTimeStringForMillis(System.currentTimeMillis()));
		
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			StackTraceElement ste = stack[i];
			if (ste.getClassName().contains("EbizSql")){
				System.out.println(ste.getClassName() + "." + ste.getMethodName() + "(...);");
			// System.out.println(i + "--" + ste.getMethodName());
			// System.out.println(i + "--" + ste.getFileName());
			// System.out.println(i + "--" + ste.getLineNumber());		
			}
		}
	}

	public static void setNewDataSource() {
		if(dataSource==null){
			dataSource = new BasicDataSource();
		}
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		//远程数据库
		/*dataSource.setUrl("jdbc:mysql://132.148.245.32:3306/test002mywarehouse?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true");
		dataSource.setUsername("tozhr");
		dataSource.setPassword("east#!20181022");*/
		//测试专用数据库
		dataSource.setUrl("jdbc:mysql://localhost:3306/test002mywarehouse?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		
		
		
		dataSource.setInitialSize(10);
		dataSource.setMaxActive(100);
		dataSource.setMaxIdle(30);
		dataSource.setMinIdle(5);
		dataSource.setMaxOpenPreparedStatements(200);
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setValidationQuery("select count(*) from DUAL");

	}


	public static Connection getConnection(){
			output();
			Connection connection=null;
			if(dataSource==null){
				setNewDataSource();
			}
			try {
				connection=dataSource.getConnection();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				dataSource=null;
				setNewDataSource();
				try {
					connection=dataSource.getConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
				
			}
			return connection;

	}

	public void Close() {
		try {
			dataSource.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}