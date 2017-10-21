package JDBCTest;

public class Student {
	private	String studentName;
	private String studentId;
	private int age;
	private String majors1;
	@Override
	public String toString() {
		return "Student [studentName=" + studentName + ", studentId=" + studentId + ", age=" + age + ", majors="
				+ majors1 + "]";
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String studentName, String studentId, int age, String majors) {
		super();
		this.studentName = studentName;
		this.studentId = studentId;
		this.age = age;
		this.majors1 = majors;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((majors1 == null) ? 0 : majors1.hashCode());
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
		result = prime * result + ((studentName == null) ? 0 : studentName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (age != other.age)
			return false;
		if (majors1 == null) {
			if (other.majors1 != null)
				return false;
		} else if (!majors1.equals(other.majors1))
			return false;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		if (studentName == null) {
			if (other.studentName != null)
				return false;
		} else if (!studentName.equals(other.studentName))
			return false;
		return true;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getMajors() {
		return majors1;
	}
	public void setMajors(String majors) {
		this.majors1 = majors;
	}
}
