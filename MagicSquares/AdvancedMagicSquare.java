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
	
	public static void main(String[] a) throws Exception{
		System.out.println("Magic Square Working.");
		AdvancedMagicSquare magic = new AdvancedMagicSquare(4);
		
		long t0 = System.currentTimeMillis();
		magic.permute(0);
		long t1 = System.currentTimeMillis();
		System.out.println(magic.count);
		
		System.out.println("The time cost is: " + (t1-t0));
		
	}//end main
	
	/** 
	* FunName: permute 
	* Description: the main function to solve magic square problem based on backtracking.
	*              it can call checking function and if it satisfy with checking function, 
	*              it can recursive to next state. If not, it can backtracking to earlier state 
	*              based on DFS.           
	* @param: int testIndex //A index to traversing the result array.
	* @return: void  
	* @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
	* @Create Date: 4/10/2016 
	*/ 
	private void permute(int testIndex){
		if(testIndex == maxNum){//Get one solution
				//print();//print the result square
				count++;//count for the solution
		}else{
			for(int i = 1; i <= maxNum; i++){//travsing all the number set
				m[testIndex] = i;//try each number
				if(checking(testIndex)){//call checking function
					permute(testIndex + 1);//recursive to next state
				}
			}
		}
	}//end permute(int j)
	
	/** 
	* FunName: checking 
	* Description: the checking function to judge validity for current attempt 
	*              and if it meet all the conditions, return true; otherwise, reture false.
	*              These checking funtion primarily include:
	*              1. Check Number Repetition
	*              2. Check Row, column, main/minor diagonal sum
	*              3. Check Row, column, main/minor diagonal for the first several numbers
	*                 to avoid too small or too large        
	* @param: int testIndex //A index to traversing the result array.
	* @return: boolean  
	* @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
	* @Create Date: 4/10/2016 
	*/ 
	private boolean checking(int testIndex){
		int remainder = testIndex % order;//remainder for testIndex corresponding cols
		int quotient = testIndex / order;//quotient for testIndex corresponding rows
		
		/**
		 * Section1: Check Number Repetition
		 * Description:check and not allow has the same number before
		 * @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
		 * @Create Date:: 4/10/2016
		 */
		for(int i = 0; i < testIndex; i++){ //
			if(m[testIndex] == m[i]){
				return false;
			}	
		}
		
		/**
		 * Section2: Check Row Sum
		 * Description: check the last one in the row, if row sum != Magic_Constant,
		 *              return false.
		 * @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
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
		 * @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
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
		 * @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
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
		 * @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
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
		 * Section6: Check Row too small or too big for first several numbers 
		 * Description: check if the first several numbers in a row too small or too big 
		 *              return false.
		 * @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
		 * @Create Date:: 4/19/2016
		 */
		if(remainder > 0 && remainder != order - 1){
			//calculate the sum of passed numbers in the row
			int rowSum = 0;
			for(int i = testIndex - remainder; i <= testIndex; i++){
				rowSum += m[i];
			}
			//judge the rowSum whether too small
			int bigNum = maxNum;
			int bigNumSum = 0;
			for(int j = 0; j < order - remainder - 1; j++){
				bigNumSum += bigNum;
				bigNum--;
			}
			if(rowSum < Magic_Constant - bigNumSum){
				return false;
			}
			//judge the rowSum whether too big
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
		 * Section7: Check Column too small or too big for first several numbers 
		 * Description: check if the first several numbers in a column too small or too big
		 *              return false.
		 * @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng / CS-593-B / Final Project
		 * @Create Date:: 4/21/2016
		 */
		if(testIndex >= order){
			//calculate the sum of passed numbers in the column
			int colSum = 0;
			for(int i = 0; i <= testIndex; i++){
				if(i % order == remainder){
					colSum += m[i];
				}	
			}
			//judge the colSum whether too small
			int bigNum = maxNum;
			int bigNumSum = 0;
			for(int j = 0; j < order - quotient - 1; j++){
				bigNumSum += bigNum;
				bigNum--;
			}
			if(colSum < Magic_Constant - bigNumSum){
				return false;
			}
			//judge the colSum whether too big
			int smallNum = 1;
			int smallNumSum = 0;
			for(int j = 0; j < order - quotient - 1; j++){
				smallNumSum += smallNum;
				smallNum++;
			}
			if(colSum > Magic_Constant - smallNumSum){
				return false;
			}
		}
				
		return true;//if all conditions pass, return ture
	}//end consistent(int j)
	
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
	
	
}//end class MagicSquare
