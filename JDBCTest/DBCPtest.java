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
 * 实现数据库连接池的测试方法
 * 1.加入jar包dbcp和pool，放在lib目录下，然后add buildpath
 * 2.创建数据库连接池
 */
public class DBCPtest{
@Test
/*
 * 1.加载properties配置文件文件，说明基本的要求
 * 2.调用asicDataSourceFactory.createDataSource方法
 * 3.从DataSource实例中获取数据库连接
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
			//测试dbcp.properties中的等待时长是否生效
		} catch (Exception e) {
			e.printStackTrace();
	}
}
@Test
/*
 *dbcp中数据库池的一部分测试代码
 */
	public void test(){
	BasicDataSource dataSource = null;
		try {
			 dataSource = new BasicDataSource();
			//为dbcp创建数据源
			 dataSource.setUsername("root");
			 dataSource.setPassword("xcy199776");
			 dataSource.setUrl("jdbc:mysql://localhost:3306/test");
			 dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			 //为数据源实例指定必须的属性
			 Connection conn = dataSource.getConnection();
			 //从数据源中获取数据库连接
			 dataSource.setInitialSize(10);
			 //指定数据连接池初始化连接的个数
			 dataSource.setMaxActive(50);
			 //指定数据连接池最大连接数:同一时刻可以向数据库申请的连接数
			 dataSource.setMinIdle(5);
			 //指定数据连接池最小连接数：在数据连接池中保存的空闲的最小连接数
			 dataSource.setMaxWait(1000*5);
			 //指定等待数据库连接池分配连接的最大等待时间，超过时间即抛出异常。这里指定的时间为5s
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
	}
	
}
