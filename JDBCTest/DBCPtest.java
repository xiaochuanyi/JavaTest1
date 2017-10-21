package JDBCTest;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

/*
 * ʵ�����ݿ����ӳصĲ��Է���
 * 1.����jar��dbcp��pool������libĿ¼�£�Ȼ��add buildpath
 * 2.�������ݿ����ӳ�
 */
public class DBCPtest{
@Test
/*
 * 1.����properties�����ļ��ļ���˵��������Ҫ��
 * 2.����asicDataSourceFactory.createDataSource����
 * 3.��DataSourceʵ���л�ȡ���ݿ�����
 */
	public void test1(){
		try {
			Properties properties = new Properties();
			InputStream in = DBCPtest.class.getClassLoader()
					.getResourceAsStream("dbcp.properties");
			properties.load(in);
			DataSource dataSource = BasicDataSourceFactory
					.createDataSource(properties);
			System.out.println(dataSource.getConnection());
			BasicDataSource basicDataSource = (BasicDataSource) dataSource;
			System.out.println(basicDataSource.getMaxWait());
			//����dbcp.properties�еĵȴ�ʱ���Ƿ���Ч
		} catch (Exception e) {
			e.printStackTrace();
	}
}
@Test
/*
 *dbcp�����ݿ�ص�һ���ֲ��Դ���
 */
	public void test(){
	BasicDataSource dataSource = null;
		try {
			 dataSource = new BasicDataSource();
			//Ϊdbcp��������Դ
			 dataSource.setUsername("root");
			 dataSource.setPassword("xcy199776");
			 dataSource.setUrl("jdbc:mysql://localhost:3306/test");
			 dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			 //Ϊ����Դʵ��ָ�����������
			 Connection conn = dataSource.getConnection();
			 //������Դ�л�ȡ���ݿ�����
			 dataSource.setInitialSize(10);
			 //ָ���������ӳس�ʼ�����ӵĸ���
			 dataSource.setMaxActive(50);
			 //ָ���������ӳ����������:ͬһʱ�̿��������ݿ������������
			 dataSource.setMinIdle(5);
			 //ָ���������ӳ���С�����������������ӳ��б���Ŀ��е���С������
			 dataSource.setMaxWait(1000*5);
			 //ָ���ȴ����ݿ����ӳط������ӵ����ȴ�ʱ�䣬����ʱ�伴�׳��쳣������ָ����ʱ��Ϊ5s
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
	}
	
}
