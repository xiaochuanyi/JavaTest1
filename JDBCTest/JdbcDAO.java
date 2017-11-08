package JDBCTest;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

public class JdbcDAO<T> implements DBUtilsDAO<T> {
	private QueryRunner queryRunner = null;
	public JdbcDAO(){
		queryRunner = new QueryRunner();
	}
	public void batch(Connection conn, String sql, Object[]... args) {
			
	}

	public <E> E getforvalue(Connection conn, String sql, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getforlist(Connection conn, String sql, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(Connection conn, String sql, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Connection conn, String sql, Object... args) {
		// TODO Auto-generated method stub
		
	}

}
