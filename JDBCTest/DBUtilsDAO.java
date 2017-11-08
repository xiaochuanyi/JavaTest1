package JDBCTest;

import java.sql.Connection;
import java.util.List;

public interface DBUtilsDAO<T> {
	/*
	 * ��������
	 */
	void batch(Connection conn,String sql,Object[] ... args);
	/*
	 * ����һ�������ֵ
	 */
	<E>E getforvalue(Connection conn,String sql,Object ... args);
	/*
	 * ����һ�����ϣ��������ÿһ��Ԫ����һ����¼
	 */
	List<T> getforlist(Connection conn,String sql,Object ... args);
	/*
	 * ����һ������Ϊ��ѯ��һ����¼
	 */
	T get(Connection conn,String sql,Object ... args);
	/*
	 * ���²���
	 */
	void update(Connection conn,String sql,Object ... args);
}
