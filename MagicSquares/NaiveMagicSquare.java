/**
 * Course: CPE-593-B
 * Magic Square
 * Algorithm: Backtracking
 * @author Zeyu Ni/Student ID:10410962
 * @Create Date:: 5/4/2016
 */
package MagicSquare;

import java.io.PrintWriter;


public class MagicSquare{
	private int[] m;//array of size n*n
	private int N;//square of n
	private int n;//n * n Magic Square
	private int rowSum;//the sum of each row, column and diagonal
	
	public MagicSquare(int n){//constructor
		m = new int[n * n];//array of length n*n
		this.n = n;
		N = n * n;
		rowSum = n * (n * n + 1) / 2;//calculate the sum of each row, column and diagonal
		//permute(0);//permute from 0
	}//end constructor
	
	private boolean consistent(int j){
		//check and not allow has the same number before
		for(int i = 0; i < j; i++){ //O(n^2)
			if(m[j] == m[i]){
				return false;
			}	
		}
		if(j % n == n - 1){//last one in the row sum = rowSum
			int sum = 0;
			for(int i = j - n + 1; i <= j; i++){
				sum += m[i];
			}
			if(sum != rowSum){
				return false;
			}
		}
		return true;
	}//end consistent(int j)
	
	private void permute(int j){
		if(j == N){
			print();
		}else{
			for(int i = 1; i <= N; i++){
				m[j] = i;
				if(consistent(j)){
					permute(j + 1);
				}	
			}
		}
	}//end permute(int j)
	
	private void print(){
		for(int i = 0; i < N; i++){
			System.out.print(m[i] + " ");
			
			if((i + 1) % n == 0){
				System.out.println();
			}
		}
		System.out.println();
	}//end print()
	
	public static void main(String[] a) throws Exception{
		System.out.println("Magic Square Working.");
		MagicSquare magic = new MagicSquare(3);
		
		long t0 = System.currentTimeMillis();
		magic.permute(0);
		long t1 = System.currentTimeMillis();
		
		System.out.println("The time cost is: " + (t1-t0));
		
	}//end main
}//end class MagicSquare