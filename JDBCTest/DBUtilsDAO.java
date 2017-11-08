package JDBCTest;

import java.sql.Connection;
import java.util.List;

public interface DBUtilsDAO<T> {
	/*
	 * 批量处理
	 */
	void batch(Connection conn,String sql,Object[] ... args);
	/*
	 * 返回一个具体的值
	 */
	<E>E getforvalue(Connection conn,String sql,Object ... args);
	/*
	 * 返回一个集合，集合里的每一个元素是一条记录
	 */
	List<T> getforlist(Connection conn,String sql,Object ... args);
	/*
	 * 返回一个对象，为查询的一条记录
	 */
	T get(Connection conn,String sql,Object ... args);
	/*
	 * 更新操作
	 */
	void update(Connection conn,String sql,Object ... args);
}
