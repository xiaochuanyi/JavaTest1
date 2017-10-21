package JDBCTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

public class Test111 {
	@Test
public void test(){
	try {
		Connection conn = JDBCTools.connection();
		System.out.println(conn);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	@Test
	public void test2(){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		//System.out.println(1);
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
}
