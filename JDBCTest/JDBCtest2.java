package JDBCTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
/*
 * 实现了数据库插入图片的操作
 * 
 */
@Test
public void test1(){
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	try {
		String sql = "insert into test values (?,?,?,?,?)";
		conn = JDBCTools.connection();
		preparedStatement = conn.prepareStatement(sql);
		InputStream inputStream = new FileInputStream("Hydrangeas.jpg");
		//建立输入流
		preparedStatement.setBlob(1,inputStream);
		//放入文件
		preparedStatement.setString(2,"小白");
		preparedStatement.setInt(3, 18);
		preparedStatement.setInt(4,445);
		preparedStatement.setString(5, "厕所");
		preparedStatement.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		JDBCTools.release(preparedStatement, conn, rs);
	}
}
/*
 * 完成mysql对数据库读取
 */
@Test
public void test2(){
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	try {
		conn = JDBCTools.connection();
		String sql = "select * from test where Name='小明'";
		//查询语句
		preparedStatement = conn.prepareStatement(sql);
		rs = preparedStatement.executeQuery();
		//传入sql语句并获得查询的结果集
		while(rs.next()){
			String name = rs.getString(2);
			int age = rs.getInt(3);
			int score = rs.getInt(4);
			String adress = rs.getString(5);
			Blob picture = rs.getBlob(1);
			//获取图片
			System.out.println(name+ ","+age+","+score+","+adress+",");
			InputStream in = picture.getBinaryStream();
			OutputStream out = new FileOutputStream("flower.jpg");
			byte [] buffer = new byte[1024];
			//建立字节流数组将读取的内容放入到其中
			int len = 0;
			while((len =in.read(buffer))!=-1){
				out.write(buffer, 0, len);
				//将buffer中的内容写入到输出流中去
			}
			out.close();
			in.close();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		JDBCTools.release(preparedStatement, conn, rs);
	}
}
/*
 * 事务的处理,为了保证事务的一致性，必须用一个连接,才可以保证事务
 */
@Test
public void test3(){
	Connection conn = null;
	try {
		conn = JDBCTools.connection();
		//为了保证事务的一致性，必须用一个连接
		conn.setAutoCommit(false);
		//开始事务，取消默认提交
		String sql = "update user set money = money - 500 where id = 1";
		update(conn, sql);
		//执行转出
		sql = "update user set money = money + 500 where id = 2";
		update(conn, sql);
		//执行转入
		conn.commit();
		//提交事务
	} catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
			//出现错误就回滚事务
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}finally{
		JDBCTools.release(null, conn, null);
	}
}
/*
 * 用传入的连接，保证几个操作用的是一个连接保证了事务
 */
public void update(Connection conn,String sql,Object ... args){
	PreparedStatement preparedStatement = null;
	try {
		preparedStatement=conn.prepareStatement(sql);
		//连接数据库，获得prepaerdStatement对象
		for(int i = 0;i<args.length;i++){
			preparedStatement.setObject(i + 1, args[i]);
			//利用for循环获得传入的参数并放入preparedStatement中
		}
		preparedStatement.executeUpdate();
		//执行更新
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		JDBCTools.release(preparedStatement, null, null);
	}
}
}
