package JDBCTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;


public class JDBCtest2 {
@Test
public void test(){
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	try {
		String sql = "insert into student(StudentId,Studentname,Age,Majors) values (?,?,?,?)";
		conn = JDBCTools.connection();
		preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		//使用重载的prepareStatement(sql,flag)来生成preparedStatement对象
		preparedStatement.setInt(1, 11512222);
		preparedStatement.setString(2, "xx");
		preparedStatement.setInt(3, 20);
		preparedStatement.setString(4, "计算机");
		preparedStatement.executeUpdate();
		//通过preparedStatement.getGenseratedKeys();方法获取了新生成的主键的resultset
		rs = preparedStatement.getGeneratedKeys();
		System.out.println(rs.next());
		if(rs.next()){
			System.out.println(rs.getObject(1));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		JDBCTools.release(preparedStatement, conn, rs);
	}
}
}
