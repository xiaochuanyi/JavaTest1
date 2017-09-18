package MapTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class CollectionsTest {
@Test
public void test(){
	List list=new ArrayList();
	list.add(11);
	list.add(13);
	list.add(16);
	list.add(10);
	//添加元素
	Iterator it=list.iterator();
	while(it.hasNext()){
		System.out.println(it.next());
	}
	//用iterator遍历list并输出
	Collections.reverse(list);//反转list
	System.out.println(list);
	Collections.sort(list);
	//将list进行自然排序
	System.out.println(list);
	Object obj=Collections.max(list);//获得集合中最大的元素
	System.out.println(obj);
}
}
