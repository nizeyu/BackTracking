/**
 * Name: Test
 * Description: Test for the following three backtracking algorithms:
 *              1. Magic Square
 *              2. N-Queens
 *              3. Sudoku
 * @Author: Zeyu Ni, Binglin Xie, Yingbin Zheng/ CPE-593-B/ Final Project
 * @Create Date: 4/20/2016
 */
package Backtracking.test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import Backtracking.control.AdvancedMagicSquare;
import Backtracking.control.Nqueens;
import Backtracking.control.sudoku;

//In this class, it just call for other functions located in other packages to complete the program.
public class Test {
	public static void main(String[] args) throws Exception {
		
		System.out.println("The results for N-Queens: ");
		//test for nqueens
		Nqueens q = new Nqueens();
		long start = System.nanoTime();
//		nqueens queen = new nqueens();
		//int n = reader.nextInt();
		int n = 8;
//		final int n = queen.N;
		int[][] Queen = new int[n][n];
		int[] flag = new int[5*n-2];
		Arrays.fill(flag,1);
//	       List<List<String>> result = new ArrayList<List<String>>();
	    for (int j = 0; j < n; j++) {
	         //q.solver(Queen, 0, j, n);
	         q.bettersolver(Queen, flag, 0, j , n);
	     }
	    long end = System.nanoTime();
	    long used = end-start;
	    System.out.println("used:"+TimeUnit.NANOSECONDS.toMillis(used)+" ms");
		
	    
	    System.out.println("-------------------------------------------");
	    System.out.println("The results for Soduku: ");
	    
		//test for sudoku
		sudoku sudoku = new sudoku();
		long start1 = System.currentTimeMillis();
		sudoku.inputSudokuGame();
//		sudoku.newSudokuGame(sudoku3.DifficultyLevel.HARD);
		
		if(sudoku.solveSudoku() == 0) {
			System.out.println("No solutions found!");
		}
		else {
			sudoku.printSudokuSolutions();			
		}
		System.out.println("Runtime :"+(System.currentTimeMillis() - start1)+"ms");	
	    
		System.out.println("-------------------------------------------");
	    System.out.println("The results for Magic Square: ");
	    //test for magic square
		AdvancedMagicSquare a = new AdvancedMagicSquare(3);
		long t0 = System.currentTimeMillis();
		a.permute(0);
		long t1 = System.currentTimeMillis();
		System.out.println("The number of result set is: " + a.count);
		
		System.out.println("The time cost is: " + (t1-t0));
	}
}
