package JDBCTest;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import com.mysql.jdbc.ResultSetMetaData;

class Test{
	public <T> T get(Class<T> clazz,String sql,Object ... agr){
		T entity = null;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet re=null;
		try {
			conn=JDBCTools.connection();
			preparedStatement = conn.prepareStatement(sql);
			//把sql放入preparedSstatement中
			for(int i = 0;i<agr.length;i++){
				preparedStatement.setObject(i+1, agr[i]);
				//把传入的对象依次放入sql中
			}
			re = preparedStatement.executeQuery();
			//得到查询的结果并放入resultSet中
			ResultSetMetaData rs=(ResultSetMetaData) re.getMetaData();
			//得带resultsetmetadata对象
			Map<String,Object> map=new HashMap<>();
			//建立一个map，键为列的别名，值为获得的结果
			if(re.next()){
				for(int i = 0;i<rs.getColumnCount();i++){
					String lable=rs.getColumnLabel(i + 1);
					Object value=re.getObject(i + 1);
					map.put(lable, value);
					//用循环获得查询的列的值和列名，并依次放入map集合中
				}
			if(map.size()>0){			
				//当map不为空时，创建类对象
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
				
			}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			JDBCTools. release(preparedStatement, conn, null);
		}
		return entity;
	}
}
public class QueryTest {
	public static void main(String[] args) {
		Test test=new Test();
		String sql="select StudentId studentId,Studentname studentName,Age age,Majors majors1 from student where StudentId=?";
		 Student stu=test.get(Student.class, sql, 12345678);
	}

}
