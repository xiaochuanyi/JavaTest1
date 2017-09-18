package JDBCTest;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.Test;


public class jdbctest {
	@Test
 public void test1(){ 
	 Connection conn=null;
	 PreparedStatement preparedStatement=null;
	 try {
		conn=JDBCTools.connection();
		String sql="insert into cumstomers values (?,?,?)";
		preparedStatement=conn.prepareStatement(sql);
		preparedStatement.setString(1, "小明");
		preparedStatement.setInt(2, 8);
		preparedStatement.setInt(3, 18);
		preparedStatement.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		JDBCTools. release(preparedStatement, conn, null);
	}
 }
	/*
	 * Object ... args 代表可以传入任意个参数，
	 * 因为你插入的信息可能是插入多个，有不确定性
	 */
public void update(String sql,Object ... args){
	 Connection conn=null;
	 PreparedStatement preparedStatement=null;
	 try {
		 conn=JDBCTools.connection();
		 preparedStatement=conn.prepareStatement(sql);
		 for(int i=0;i<args.length;i++){			
			 preparedStatement.setObject(i+1, args[i]);//利用for循环，循环的插入你说所放入的值
		 }
		 preparedStatement.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		JDBCTools.release(preparedStatement,conn,null);
		//关闭连接
	}
}
@Test
public void test(){
	String sql="insert into student value (?,?,?,?)";
	update(sql,"12345685","xcy",21,"信息安全");
	//这里可以吧传入的数据写成scanner让自己手动输入
}
}