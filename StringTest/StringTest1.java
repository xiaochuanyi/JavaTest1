package StringTest;

import org.junit.Test;
/*
 * ������ת�ʵ��㷨���ж�һ���ַ����Ƿ�Ϊ��һ���ַ�������ת��
 */
public class StringTest1 {
	@Test
	public void test1(){
		String a = "abcde";
		String b = "cdeab";
		String c = a+a;
		//������a������ʱ����ô���bΪa����ת�ʣ���cһ������b
		int s = c.indexOf(b);
		System.out.println(s);
	}
}
