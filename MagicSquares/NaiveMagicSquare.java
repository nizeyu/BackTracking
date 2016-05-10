/**
 * Course: CPE-593-B
 * Final Project: Advanced Magic Square
 * Method: Backtracking
 * @author Zeyu Ni/Student ID:10410962
 * @Create Date:: 5/4/2016
 */
package AdvancedMagicSquare;

public class AdvancedMagicSquare{
	private int[] m;//array of size n*n
	private int maxNum;//the biggest number in the square
	private int order;//the order of square
	private int Magic_Constant;//the sum of each row, column and diagonal
	private int count;//count for the number of result square
	
	public AdvancedMagicSquare(int n){//constructor
		m = new int[n * n];//array of length n*n
		this.order = n;
		maxNum = n * n;//calculate the the biggest number in the square
		Magic_Constant = n * (n * n + 1) / 2;//calculate the sum of each row, column and diagonal
		//permute(0);//permute from 0
	}//end constructor
	
	private boolean condition(int testIndex){
		int remainder = testIndex % order;//remainder for testIndex corresponding cols
		int quotient = testIndex / order;//quotient for testIndex corresponding rows
		
		/**
		 * Section1: Check Number Repetition
		 * Description:check and not allow has the same number before
		 * @author Zeyu Ni/Student ID:10410962/CPE-593-B
		 * @Create Date:: 4/10/2016
		 */
		for(int i = 0; i < testIndex; i++){ //O(n^2)
			if(m[testIndex] == m[i]){
				return false;
			}	
		}
		
		/**
		 * Section2: Check Row Sum
		 * Description: check the last one in the row, if row sum != Magic_Constant,
		 *              return false.
		 * @author Zeyu Ni/Student ID:10410962/CPE-593-B
		 * @Create Date:: 4/10/2016
		 */
		if(remainder == order - 1){//last one in the row sum = Magic_Constant
			int sum = 0;
			for(int i = testIndex - order + 1; i <= testIndex; i++){
				sum += m[i];
			}
			if(sum != Magic_Constant){
				return false;
			}
		}
		
		/**
		 * Section3: Check Column Sum
		 * Description: check the last one in the column, if row sum != Magic_Constant,
		 *              return false.
		 * @author Zeyu Ni/Student ID:10410962/CPE-593-B
		 * @Create Date:: 4/15/2016
		 */
		if(testIndex >= maxNum - order){//last one in the column sum = Magic_Constant
			int sum = 0;
			for(int i = 0; i < maxNum; i++){
				if( i % order == remainder){
					sum += m[i];
				}	
			}
			if(sum != Magic_Constant){
				return false;
			}
		}
	
		/**
		 * Section4: Check main diagonal sum
		 * Description: check the last one in the main diagonal, if main diagonal sum != Magic_Constant,
		 *              return false.
		 * @author Zeyu Ni/Student ID:10410962/CPE-593-B
		 * @Create Date:: 4/17/2016
		 */
		if(testIndex == maxNum - 1){///last one in the main diagonal sum = Magic_Constant
			int sum = 0;
			for (int i = 0; i < order; i++) {
				for(int j = i * order; j <  (i + 1) * order; j++){
					if(j  %  order == i){
						sum += m[j];
					}
				}
				
			}
			if(sum != Magic_Constant){
				return false;
			}
		}
		
		/**
		 * Section5: Check minor diagonal sum
		 * Description: check the last one in the minor diagonal, if minor diagonal sum != Magic_Constant,
		 *              return false.
		 * @author Zeyu Ni/Student ID:10410962/CPE-593-B
		 * @Create Date:: 4/17/2016
		 */
		if(testIndex == maxNum - order){///last one in the minor diagonal sum = Magic_Constant
			int sum = 0;
			for (int i = 0; i < order; i++) {
				for(int j = i * order; j < (i + 1) * order; j++){
					if(j  %  order == order - i - 1){
						sum += m[j];
					}
				}
			}
			if(sum != Magic_Constant){
				return false;
			}
		}
		
		/**
		 * Section6: Check Row too small for fisrt serveral numbers 
		 * Description: check if the fisrt serveral numbers in a row too small 
		 *              return false.
		 * @author Zeyu Ni/Student ID:10410962/CPE-593-B
		 * @Create Date:: 4/19/2016
		 */
		if(remainder > 0 && remainder != order - 1){
			int rowSum = 0;
			for(int i = testIndex - remainder; i <= testIndex; i++){
				rowSum += m[i];
			}
			int bigNum = maxNum;
			int bigNumSum = 0;
			for(int j = 0; j < order - remainder - 1; j++){
				bigNumSum += bigNum;
				bigNum--;
			}
			if(rowSum < Magic_Constant - bigNumSum){
				return false;
			}
		}
		
		/**
		 * Section7: Check Row too big for fisrt serveral numbers 
		 * Description: check if the fisrt serveral numbers in a row too big
		 *              return false.
		 * @author Zeyu Ni/Student ID:10410962/CPE-593-B
		 * @Create Date:: 4/19/2016
		 */
		if(remainder > 0 && remainder != order - 1){
			int rowSum = 0;
			for(int i = testIndex - remainder; i <= testIndex; i++){
				rowSum += m[i];
			}
			int smallNum = 1;
			int smallNumSum = 0;
			for(int j = 0; j < order - remainder - 1; j++){
				smallNumSum += smallNum;
				smallNum++;
			}
			if(rowSum > Magic_Constant - smallNumSum){
				return false;
			}
		}
		
		/**
		 * Section8: Check Column too big for fisrt serveral numbers 
		 * Description: check if the fisrt serveral numbers in a column too big
		 *              return false.
		 * @author Zeyu Ni/Student ID:10410962/CPE-593-B
		 * @Create Date:: 4/21/2016
		 */
		if(testIndex >= order){
			int colSum = 0;
			for(int i = 0; i <= testIndex; i++){
				if(i % order == remainder){
					colSum += m[i];
				}	
			}
			int bigNum = maxNum;
			int bigNumSum = 0;
			for(int j = 0; j < order - quotient - 1; j++){
				bigNumSum += bigNum;
				bigNum--;
			}
			if(colSum < Magic_Constant - bigNumSum){
				return false;
			}
		}
		
		

		return true;//if all conditions pass, return ture
	}//end consistent(int j)
	
	private void test(int j){
		if(j == maxNum){
				//print();
				count++;
		}else{
			for(int i = 1; i <= maxNum; i++){
				m[j] = i;
				if(condition(j)){
					test(j + 1);
				}	
			}
		}
	}//end permute(int j)
	
	/** 
	* FunName: print1 
	* Description: Print the result square.               
	* @param: void
	* @return: void 
	* @Author: Zeyu Ni / Student ID: 10410962 / CS-593-B
	* @Create Date: 04/10/2016 
	*/ 
	private void print(){
		for(int i = 0; i < maxNum; i++){
			System.out.print(m[i] + " ");
			
			if((i + 1) % order == 0){
				System.out.println();
			}
		}
		System.out.println();
	}//end print()
	
	public static void main(String[] a) throws Exception{
		System.out.println("Magic Square Working.");
		AdvancedMagicSquare magic = new AdvancedMagicSquare(4);
		
		long t0 = System.currentTimeMillis();
		magic.test(0);
		long t1 = System.currentTimeMillis();
		System.out.println(magic.count);
		
		System.out.println("The time cost is: " + (t1-t0));
		
	}//end main
}//end class MagicSquare
