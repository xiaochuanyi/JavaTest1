package JDBCTest;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

public class DBUtilsTest {
/*
 * ����QueryRunner���update����
 */
	@Test
	public void test1(){
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into cumstomers values (?,?,?)";
		Connection conn = null;
		try {
			conn = JDBCTools.connection();
			queryRunner.update(conn, sql, "xia",11,150);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, conn, null);
		}
	}
}
