package SortingTest;

import org.junit.Test;
/*
 * ≤Â»Î≈≈–Ú
 */
public class ChooseTest {
	@Test
	public void test(){
		int A[] = {5,3,25,68,14,21};
		int n = 6;
		   int a = 0;
	        for(int i = 0;i < n-1 ;i++){
	            for(int x = i+1;x > 0;x--){
	                if(A[x] < A[x-1]){
	                    a = A[x];
	                    A[x] = A[x-1];
	                    A[x-1] = a;
	                }
	            }	            
	        }
	        for(int g = 0;g < n;g++){
	        	System.out.println(A[g]);
	        }
	}
}
