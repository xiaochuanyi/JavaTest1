package JDBCTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
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
		//ʹ�����ص�prepareStatement(sql,flag)������preparedStatement����
		preparedStatement.setInt(1, 11512222);
		preparedStatement.setString(2, "xx");
		preparedStatement.setInt(3, 20);
		preparedStatement.setString(4, "�����");
		preparedStatement.executeUpdate();
		//ͨ��preparedStatement.getGenseratedKeys();������ȡ�������ɵ�������resultset
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
 * ʵ�������ݿ����ͼƬ�Ĳ���
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
		//����������
		preparedStatement.setBlob(1,inputStream);
		//�����ļ�
		preparedStatement.setString(2,"С��");
		preparedStatement.setInt(3, 18);
		preparedStatement.setInt(4,445);
		preparedStatement.setString(5, "����");
		preparedStatement.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		JDBCTools.release(preparedStatement, conn, rs);
	}
}
@Test
public void test2(){
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	try {
		conn = JDBCTools.connection();
		String sql = "select * from test where Name='С��'";
		//��ѯ���
		preparedStatement = conn.prepareStatement(sql);
		rs = preparedStatement.executeQuery();
		//����sql��䲢��ò�ѯ�Ľ����
		while(rs.next()){
			String name = rs.getString(2);
			int age = rs.getInt(3);
			int score = rs.getInt(4);
			String adress = rs.getString(5);
			Blob picture = rs.getBlob(1);
			//��ȡͼƬ
			System.out.println(name+ ","+age+","+score+","+adress+",");
			InputStream in = picture.getBinaryStream();
			OutputStream out = new FileOutputStream("flower.jpg");
			byte [] buffer = new byte[1024];
			//�����ֽ������齫��ȡ�����ݷ��뵽����
			int len = 0;
			while((len =in.read(buffer))!=-1){
				out.write(buffer, 0, len);
				//��buffer�е�����д�뵽�������ȥ
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
}
