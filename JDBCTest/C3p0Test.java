package JDBCTest;

import java.sql.Connection;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Test {
	@Test
	/*
	 * ֱ���������ݿ�ķ���
	 */
	public void d3p0test() throws Exception{
	ComboPooledDataSource cpds = new ComboPooledDataSource();
	cpds.setDriverClass("com.mysql.jdbc.Driver");
	cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
	cpds.setUser("root");
	cpds.setPassword("xcy199776");
	System.out.println(cpds.getConnection());
}
	@Test
	/*
	 * ͨ�������ļ��ķ�ʽ�������ݿ�
	 */
	public void test1() throws Exception{
		 ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
		System.out.println(cpds.getConnection());
	}
	@Test
	public void test2() throws Exception{
		Connection conn = JDBCTools.connection();
		System.out.println(conn);
	}
}
