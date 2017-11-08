package JDBCTest;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DAO {
	/*
	 * ִ�и��²���
	 */
	public void update(String sql,Object ... args){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn=JDBCTools.connection();
			preparedStatement=conn.prepareStatement(sql);
			//�������ݿ⣬���prepaerdStatement����
			for(int i = 0;i<args.length;i++){
				preparedStatement.setObject(i + 1, args[i]);
				//����forѭ����ô���Ĳ���������preparedStatement��
			}
			preparedStatement.executeUpdate();
			//ִ�и���
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(preparedStatement, conn, null);
		}
	}
	/*
	 * ִ�в�ѯһ����¼�Ĳ���,���ض�Ӧ�Ķ���
	 */
	public <T> T query(Class<T> clazz,String sql,Object ... args){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet re=null;
		T entity = null;
		try {
			conn = JDBCTools.connection();
			preparedStatement = conn.prepareStatement(sql);
			//��ȡ���ݿ����Ӳ��һ��preparedStatement����
			for(int i = 0; i < args.length; i++){
				preparedStatement.setObject(i + 1, args[i]);
				//������Ķ������sql�����
			}
			re = preparedStatement.executeQuery();
			//ִ�в�ѯ��������result���ѯ����	
			Map<String,Object> map = new HashMap<>();
			if(re.next()){
				List<String> columnCount = getColumnlable(re);
				//��ô���˼���������list				
				for(String columnlables : columnCount){
					//����ǿforѭ����ÿ�α���columnCount����õĽ��Ϊcolumnlables
					Object value = re.getObject(columnlables);
					//���������Ӧ��ֵ
					map.put(columnlables, value);
					//��������ֵ���뵽map��
				}
			}
			entity = clazz.newInstance();
			//���÷��䴴������
			for(Map.Entry<String, Object> entry : map.entrySet()){
				String fieldName = entry.getKey();
				Object valuename = entry.getValue();
				Field f1=clazz.getDeclaredField(fieldName);
				//������������
				f1.setAccessible(true);
				//ʹ˽�б����μ�
				f1.set(entity, valuename);
				//��������ֵ
			}
					} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(preparedStatement, conn, re);
			}
		return entity;
	}
	/*
	 * ���ض�����ѯ��¼����list�洢
	 */
	public <T> List<T> getForList(Class<T> clazz,String sql,Object ... args){
		List<T> list = new ArrayList<>();
		//׼��һ������list��Ŷ���
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet re=null;
		T entity = null;
		try {
			conn = JDBCTools.connection();
			preparedStatement = conn.prepareStatement(sql);
			List<Map<String,Object>> list1 = new ArrayList<>();
			//׼��һ������list1׼�����Map���ϣ���Map���������ŵ�������������ֵ
			for(int i = 0; i < args.length; i++){
				preparedStatement.setObject(i+1, args[i]);
				//�Ѵ���Ĳ�������sql��
			}
			re = preparedStatement.executeQuery();
			//��resultSet��ò�ѯ���Ľ��
			list1=getList(re, list1);
			//���Լ���ȡ��getList������ȡ��list1���ϣ���ŵĶ��map����ÿһ��map����һ����ѯ��¼
			list = Tgetlist(re, list1, entity, clazz, list);
			//���Լ���ȡ��Tgetlist������ȡlist���ϣ������ŵĶ������ÿһ���������һ����ѯ��¼
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(preparedStatement, conn, re);
		}
		return list;
	}
	/*
	 * ����ĳ����¼��ĳһ���ֶε�ֵ����һ��ͳ�Ƶ�ֵ(һ���ж��ټ�¼��)
	 */
	public <E> E getforvalue(String sql,Object ... args){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet re=null;
		try {
			conn = JDBCTools.connection();
			preparedStatement = conn.prepareStatement(sql);
			//��ȡ���ݿ����Ӳ��һ��preparedStatement����
			for(int i = 0; i < args.length; i++){
				preparedStatement.setObject(i + 1, args[i]);
				//������Ķ������sql�����
			}
			re = preparedStatement.executeQuery();
			//ִ�в�ѯ��������result���ѯ����
			if(re.next()){				
				return (E) re.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(preparedStatement, conn, re);
		}
			return null;
	}
	/*
	 * ������ȡһ����Ž����������list
	 */
	private List<String> getColumnlable(ResultSet re) throws Exception{
		List<String> list = new ArrayList<>();
		ResultSetMetaData rs = re.getMetaData();
		//��ȡ�����������
		for(int i = 0; i < rs.getColumnCount(); i++){
			list.add(rs.getColumnLabel(i + 1));
		}
		return list;
	}
	/*
	 * ���һ������˶��map��list���ϣ�ÿһ��map�д�ŵľ���һ����ѯ���Ķ���
	 */
	private List<Map<String,Object>> getList(ResultSet re,List<Map<String,Object>> list1) throws Exception{
		List<String> columnCount = getColumnlable(re);
		//��ô���˼���������list
		Map<String,Object> map = null;
		while(re.next()){
			map =new HashMap<>();
			for(String columnlables : columnCount){
				//����ǿforѭ����ÿ�α���columnCount����õĽ��Ϊcolumnlables
				Object value = re.getObject(columnlables);
				//���������Ӧ��ֵ
				map.put(columnlables, value);
				//��������ֵ���뵽map��
			}
			list1.add(map);
			//��map���뵽list1��
		}					
		 	return list1;
	}
	/*
	 * ������ȡ����˶����list���ϣ���ÿһ��map�е���Ϣ���뵽�����У�ÿһ���������һ����
	 */
	private <T> List<T> Tgetlist(ResultSet re,List<Map<String,Object>> list1,T entity,Class<T> clazz,List<T> list) throws Exception{
		list1=getList(re, list1);
		if(list1.size() > 0){
			for(Map<String,Object> m : list1){
					entity = clazz.newInstance();
					//���÷��䴴������
				for(Map.Entry<String, Object> entry : m.entrySet() ){
					String name = entry.getKey();
					Object value1 = entry.getValue();						
					Field f1=clazz.getDeclaredField(name);
					//������������
					f1.setAccessible(true);
					//ʹ˽�б����μ�
					f1.set(entity, value1);
					//��������ֵ						
				}
				list.add(entity);
				//�Ѷ�����ӵ�list������ȥ��ÿһ��entity����һ����¼
			}
		}		
		return list;
	}
	/*
	 * ��������ĸ��뼶��
	 */
	public void test5(){
		
	}

}