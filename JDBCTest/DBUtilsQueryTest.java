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
	  *��ý�����ĵ�һ����¼ 
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
	  * ���ص��ǽ����
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
	  * ����sql��Ӧ��һ����¼��Ӧ��map����,key���������
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
	  * ���ص���һ��list���ϣ������һ������map��һ��map����һ����¼ 
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
	  * ���ص���һ��ֵ
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
