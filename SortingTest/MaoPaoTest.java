package SortingTest;
import org.junit.Test;
/*
 * ц╟ещеепР
 */
public class MaoPaoTest {
	@Test
	public void test(){
		int A[] = {1,2,3,5,2,3};
		int n = 0;
	     int a=0;
	        for(int i = 0;i < n;i++){
				for(int j = 0;j < n-i;j++){
	                if(A[j] > A[j+1]){
	                    a = A[j];
	                    A[j] = A[j+1];
	                    A[j+1] = a;
	                }
	            }
	        }
	        System.out.println(A[1]);
	    }
	}
