package JDBCTest;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Driver;
/*
 * 操作JDBC的工具类，封装了获取连接的方法，释放连接的方法
 */
public class JDBCTools {	
	//关闭连接
	public static void release(Statement statement,Connection connection, ResultSet sr){
		if(sr!=null){
			try {
				sr.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if(statement!=null){
			try {				
				statement.close();//关闭连接
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
				if(connection!=null){
			try {				
				connection.close();//关闭连接
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		}
	}
			
	//连接数据库
	public static Connection connection() throws Exception{
		//1.准备连接数据库的4个字符串						
		//创建properties对象
		Properties properties=new Properties();
		//获取jdbc.properties对应的输入流
		InputStream in=JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");		
		//加载对应的输入流
		properties.load(in);
		//具体决定相应的字符串
		String driver=properties.getProperty("driver");	
		String jdbcUrl=properties.getProperty("jdbcUrl");
		System.out.println(jdbcUrl);
		String user=properties.getProperty("user");
		String password=properties.getProperty("password");
		//加载数据库驱动程序
		Class.forName(driver);						
		//获取数据库连接  两种方法，用DriverManager可以管理多个驱动程序，若注册了多个数据库连接，则调用getConnection方法
		//传入的参数不同，即返回不同的数据库连接。、
		return DriverManager.getConnection(jdbcUrl,user,password);
		//Connection connection=driver.connect(jdbcUrl,info);
		
	}

}