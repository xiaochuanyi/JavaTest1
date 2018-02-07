package StringTest;

import org.junit.Test;
/*
 * 测试旋转词的算法，判断一个字符串是否为另一个字符串的旋转词
 */
public class StringTest1 {
	@Test
	public void test1(){
		String a = "abcde";
		String b = "cdeab";
		String c = a+a;
		//当两个a相连接时，那么如果b为a的旋转词，则c一定包含b
		int s = c.indexOf(b);
		System.out.println(s);
	}
}
