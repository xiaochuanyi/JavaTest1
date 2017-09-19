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
			//��sql����preparedSstatement��
			for(int i = 0;i<agr.length;i++){
				preparedStatement.setObject(i+1, agr[i]);
				//�Ѵ���Ķ������η���sql��
			}
			re = preparedStatement.executeQuery();
			//�õ���ѯ�Ľ��������resultSet��
			ResultSetMetaData rs=(ResultSetMetaData) re.getMetaData();
			//�ô�resultsetmetadata����
			Map<String,Object> map=new HashMap<>();
			//����һ��map����Ϊ�еı�����ֵΪ��õĽ��
			if(re.next()){
				for(int i = 0;i<rs.getColumnCount();i++){
					String lable=rs.getColumnLabel(i + 1);
					Object value=re.getObject(i + 1);
					map.put(lable, value);
					//��ѭ����ò�ѯ���е�ֵ�������������η���map������
				}
			if(map.size()>0){			
				//��map��Ϊ��ʱ�����������
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
