package JDBCTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

public class DBUtilsQueryTest {
	 QueryRunner queryRunner = new QueryRunner();
	 class MyResultSetHandler implements ResultSetHandler{
		@Override
		public Object handle(ResultSet resultSet) throws SQLException {
			List<Student> list =new ArrayList<Student>();
			while(resultSet.next()){
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);
				int age = resultSet.getInt(3);
				String majors = resultSet.getString(4);
				Student stu = new Student(id,name,age,majors);
				list.add(stu);
			}
			return list;
		}
	 }
	 @Test
	 public void test(){
		 Connection conn = null;
		try {
			conn = JDBCTools.connection();
			String sql = "select studentid,studentname,age,majors "
					+ "from student ";
			Object obj = queryRunner.query(conn, sql, new MyResultSetHandler());
			System.out.println(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, conn, null);
		}
	 }
	 /*
	  *获得结果集的第一条记录 
	  */
	 @Test
	 public void test1(){
		Connection conn = null;
		try {
			conn = JDBCTools.connection();
			String sql = "select studentid,studentname,age,majors "
					+ "from student where studentid >= ? ";
			Student stu = queryRunner.query(conn, sql,new BeanHandler<>(Student.class) , 11111111);
			System.out.println(stu);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, conn, null);
		}
	 }
	 /*
	  * 返回的是结果集
	  */
	 @Test
	 public void test2(){
		 Connection conn = null;
		 try {
			 conn = JDBCTools.connection();
				String sql = "select studentid,studentname,age,majors "
						+ "from student where studentid  ";
				List<Student> list = queryRunner.query(conn, sql,new BeanListHandler<>(Student.class) );
				System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, conn, null);
		}
	 }
	 /*
	  * 返回sql对应第一条记录对应的map对象,key存的是列名
	  */
	 @Test
	 public void test3(){
		 Connection conn = null;
		 try {
			 conn = JDBCTools.connection();
			String sql = "select studentid,studentname,age,majors "
						+ "from student where studentid";
			Map<String, Object> map = queryRunner.query(conn, sql,new MapHandler() );
				System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, conn, null);
		}
	 }
	 /*
	  * 返回的是一个list集合，存的是一个个的map，一个map就是一条记录 
	  */
	 @Test
	 public void test4(){
		 Connection conn = null;
		 try {
			 conn = JDBCTools.connection();
			String sql = "select studentid,studentname,age,majors "
						+ "from student where studentid";
			List<Map<String, Object>> list = queryRunner.query(conn, sql,new MapListHandler() );
				System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, conn, null);
		}
	 }
	 /*
	  * 返回的是一个值
	  */
	 @Test
	 public void test5(){
		 Connection conn = null;
		 try {
			 conn = JDBCTools.connection();
			String sql = "select studentid "
						+ "from student where studentid=?";
			Object obj = queryRunner.query(conn, sql,new ScalarHandler(),11111111 );
				System.out.println(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, conn, null);
		}
	 }
}
