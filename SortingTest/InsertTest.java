package SortingTest;

import org.junit.Test;
/*
 * —°‘Ò≈≈–Ú
 */
public class InsertTest {
 @Test
 public void test(){
	 int A[] = {5,12,7,45,3,6}; 
	 int n = 6; 
	 int a = -1;
     int b = 0;
     int c;
     for(int i = 0;i < n;i++){
         for(int x = 0;x < n - i;x++){
             if(a < A[x]){
              a = A[x];
              b = x;
             }
         }
         c =  A[n-i-1];
         A[n-i-1] = A[b];
         A[b] = c;
         a = -1;
 }
    for(int l = 0;l < n;l++){
    	System.out.println(A[l]);
    }
}
}
