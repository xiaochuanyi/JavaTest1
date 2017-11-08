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
	 * 执行更新操作
	 */
	public void update(String sql,Object ... args){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn=JDBCTools.connection();
			preparedStatement=conn.prepareStatement(sql);
			//连接数据库，获得prepaerdStatement对象
			for(int i = 0;i<args.length;i++){
				preparedStatement.setObject(i + 1, args[i]);
				//利用for循环获得传入的参数并放入preparedStatement中
			}
			preparedStatement.executeUpdate();
			//执行更新
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(preparedStatement, conn, null);
		}
	}
	/*
	 * 执行查询一条记录的操作,返回对应的对象
	 */
	public <T> T query(Class<T> clazz,String sql,Object ... args){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet re=null;
		T entity = null;
		try {
			conn = JDBCTools.connection();
			preparedStatement = conn.prepareStatement(sql);
			//获取数据库连接并且获得preparedStatement对象
			for(int i = 0; i < args.length; i++){
				preparedStatement.setObject(i + 1, args[i]);
				//将传入的对象放入sql语句中
			}
			re = preparedStatement.executeQuery();
			//执行查询操作并用result获查询对象	
			Map<String,Object> map = new HashMap<>();
			if(re.next()){
				List<String> columnCount = getColumnlable(re);
				//获得存放了集合列名的list				
				for(String columnlables : columnCount){
					//用增强for循环，每次遍历columnCount，获得的结果为columnlables
					Object value = re.getObject(columnlables);
					//获得列名对应的值
					map.put(columnlables, value);
					//将列名和值放入到map中
				}
			}
			entity = clazz.newInstance();
			//利用反射创建对象
			for(Map.Entry<String, Object> entry : map.entrySet()){
				String fieldName = entry.getKey();
				Object valuename = entry.getValue();
				Field f1=clazz.getDeclaredField(fieldName);
				//获得这个变量名
				f1.setAccessible(true);
				//使私有变量课件
				f1.set(entity, valuename);
				//给变量赋值
			}
					} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(preparedStatement, conn, re);
			}
		return entity;
	}
	/*
	 * 返回多条查询记录，用list存储
	 */
	public <T> List<T> getForList(Class<T> clazz,String sql,Object ... args){
		List<T> list = new ArrayList<>();
		//准备一个集合list存放对象
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet re=null;
		T entity = null;
		try {
			conn = JDBCTools.connection();
			preparedStatement = conn.prepareStatement(sql);
			List<Map<String,Object>> list1 = new ArrayList<>();
			//准备一个集合list1准备存放Map集合，而Map集合里面存放的是列名和属性值
			for(int i = 0; i < args.length; i++){
				preparedStatement.setObject(i+1, args[i]);
				//把传入的参数放入sql中
			}
			re = preparedStatement.executeQuery();
			//用resultSet获得查询到的结果
			list1=getList(re, list1);
			//用自己抽取的getList方法获取了list1集合，存放的多个map对象，每一个map就是一条查询记录
			list = Tgetlist(re, list1, entity, clazz, list);
			//用自己抽取的Tgetlist方法获取list集合，里面存放的多个对象，每一个对象就是一条查询记录
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(preparedStatement, conn, re);
		}
		return list;
	}
	/*
	 * 返回某条记录的某一个字段的值，或一个统计的值(一共有多少记录等)
	 */
	public <E> E getforvalue(String sql,Object ... args){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet re=null;
		try {
			conn = JDBCTools.connection();
			preparedStatement = conn.prepareStatement(sql);
			//获取数据库连接并且获得preparedStatement对象
			for(int i = 0; i < args.length; i++){
				preparedStatement.setObject(i + 1, args[i]);
				//将传入的对象放入sql语句中
			}
			re = preparedStatement.executeQuery();
			//执行查询操作并用result获查询对象
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
	 * 用来获取一个存放结果集列名的list
	 */
	private List<String> getColumnlable(ResultSet re) throws Exception{
		List<String> list = new ArrayList<>();
		ResultSetMetaData rs = re.getMetaData();
		//获取结果集的列名
		for(int i = 0; i < rs.getColumnCount(); i++){
			list.add(rs.getColumnLabel(i + 1));
		}
		return list;
	}
	/*
	 * 获得一个存放了多个map的list集合，每一个map中存放的就是一个查询出的对象
	 */
	private List<Map<String,Object>> getList(ResultSet re,List<Map<String,Object>> list1) throws Exception{
		List<String> columnCount = getColumnlable(re);
		//获得存放了集合列名的list
		Map<String,Object> map = null;
		while(re.next()){
			map =new HashMap<>();
			for(String columnlables : columnCount){
				//用增强for循环，每次遍历columnCount，获得的结果为columnlables
				Object value = re.getObject(columnlables);
				//获得列名对应的值
				map.put(columnlables, value);
				//将列名和值放入到map中
			}
			list1.add(map);
			//将map放入到list1中
		}					
		 	return list1;
	}
	/*
	 * 用来获取存放了对象的list集合，将每一个map中的信息放入到对象中，每一个对象就是一个类
	 */
	private <T> List<T> Tgetlist(ResultSet re,List<Map<String,Object>> list1,T entity,Class<T> clazz,List<T> list) throws Exception{
		list1=getList(re, list1);
		if(list1.size() > 0){
			for(Map<String,Object> m : list1){
					entity = clazz.newInstance();
					//利用反射创建对象
				for(Map.Entry<String, Object> entry : m.entrySet() ){
					String name = entry.getKey();
					Object value1 = entry.getValue();						
					Field f1=clazz.getDeclaredField(name);
					//获得这个变量名
					f1.setAccessible(true);
					//使私有变量课件
					f1.set(entity, value1);
					//给变量赋值						
				}
				list.add(entity);
				//把对象添加到list集合中去，每一个entity代表一条记录
			}
		}		
		return list;
	}
	/*
	 * 测试事务的隔离级别
	 */
	public void test5(){
		
	}

}