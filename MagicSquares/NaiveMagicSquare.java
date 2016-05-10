/**
 * Course: CPE-593-B
 * Final Project: Naive Magic Square
 * Algorithm: Backtracking
 * @author Zeyu Ni/Student ID:10410962
 * @Create Date:: 5/4/2016
 */
package NaiveMagicSquare;

public class  NaiveMagicSquare{
	private int[] m;//array of size n*n
	private int N;//square of n
	private int n;//n * n Magic Square
	private int Magic_Constant;//the sum of each row, column and diagonal
	
	public NaiveMagicSquare(int n){//constructor
		m = new int[n * n];//array of length n*n
		this.n = n;
		N = n * n;
		Magic_Constant = n * (n * n + 1) / 2;//calculate the sum of each row, column and diagonal
		//permute(0);//permute from 0
	}//end constructor
	
	private boolean checkRows(int j){
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
			if(sum != Magic_Constant){
				return false;
			}
		}
		return true;
	}//end consistent(int j)
	
	private void permute(int j){
		if(j == N){
			if(checkCols(m) && checkDiags(m)){
				print();
			}
		}else{
			for(int i = 1; i <= N; i++){
				m[j] = i;
				if(checkRows(j)){
					permute(j + 1);
				}	
			}
		}
	}//end permute(int j)
	
	private boolean checkCols(int[] m){
		for (int i = 0; i < n; i++) {
			int sum = 0;
			for(int j = 0; j < m.length; j++){
				if(j  %  n == i){
					sum += m[j];
				}
			}
			if(sum != Magic_Constant){
				return false;
			}
		}
		return true;	
	}//end checkCols(int[] m)
	
	private boolean checkDiags(int[] m){
		int sum1 = 0;
		int sum2 = 0;
		for (int i = 0; i < n; i++) {
			for(int j = i * n; j < m.length; j++){
				if(j  %  n == i){
					sum1 += m[j];
				    break;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			for(int j = i * n; j < m.length; j++){
				if(j  %  n == n - i - 1){
					sum2 += m[j];
				    break;
				}
			}
		}
		//System.out.println(sum1);
		if((sum1 != Magic_Constant) || (sum2 != Magic_Constant) ){
			return false;
		}
		return true;	
	}//end checkDiags(int[] m)
	
	private void print(){
		for(int i = 0; i < N; i++){
			System.out.print(m[i] + " ");
			
			if((i + 1) % n == 0){
				System.out.println();
			}
		}
		System.out.println();
	}//end print()
	
}//end class NavieMagicSquare