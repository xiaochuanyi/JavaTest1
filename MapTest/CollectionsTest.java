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
	//���Ԫ��
	Iterator it=list.iterator();
	while(it.hasNext()){
		System.out.println(it.next());
	}
	//��iterator����list�����
	Collections.reverse(list);//��תlist
	System.out.println(list);
	Collections.sort(list);
	//��list������Ȼ����
	System.out.println(list);
	Object obj=Collections.max(list);//��ü���������Ԫ��
	System.out.println(obj);
}
}
