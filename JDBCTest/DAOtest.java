package JDBCTest;

import java.util.Iterator;
import java.util.List;

public class DAOtest {

	public DAOtest() {
	}

	public static void main(String[] args) {
		String sql = "insert into cumstomers values (?,?,?)";
		String sql1 = "select StudentId studentId,Studentname studentName,Age age,Majors majors1 from student where StudentId=?";
		DAO dao = new DAO();
		dao.update(sql, "xcy",9,100);
		Student stu=dao.query(Student.class, sql1, 12345678);
		System.out.println(stu);		
		String sql2 = "select StudentId studentId,Studentname studentName,Age age,Majors majors1 from student";
		List<Student> list = dao.getForList(Student.class, sql2);
		Iterator it = list.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		String sql3 = "select Studentname studentName from student where  StudentId=?";
		String a = dao.getforvalue(sql3, 12345678);
		System.out.println(a);
	}

}
