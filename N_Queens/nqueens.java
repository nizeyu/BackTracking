import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class nqueens {
//     int N;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

    
  // System.out.println("Enter the number of queens");
		//Scanner reader = new Scanner(System.in);
		long start = System.nanoTime();
		int n = 12;
		int[][] Queen = new int[n][n];
		int[] flag = new int[5*n-2];
		Arrays.fill(flag,1);
	    for (int j = 0; j < n; j++) {
//	         solver(Queen, 0, j, n);
	         bettersolver(Queen, flag, 0, j , n);
	     }
	    long end = System.nanoTime();
	    long used = end-start;
	    System.out.println("used:"+TimeUnit.NANOSECONDS.toMillis(used)+" ms");
	}
	/* A optimized utility function to solve N
    Queen problem  */
	public static void bettersolver(int[][] Queen, int[] flag, int CurrentRow, int CurrentColumn, int n) {
		/* Place this queen in board[CurrentRow][CurrentColumn] */
		Queen[CurrentRow][CurrentColumn] = 1;
		flag[CurrentColumn] = 0;
		flag[n+CurrentColumn+CurrentRow] = 0 ;
		flag[4*n + CurrentColumn -CurrentRow - 2] = 0;
		/* base case: If all queens are placed
	      then print out */
		if (CurrentRow == n-1) {
	            for (int i = 0; i < n; i++) {
	                //String s = new String();
	                for (int j = 0; j < n; j++) {
	                	 System.out.print(" " + Queen[i][j] + " ");

	                }
	                System.out.println();
	            }
	            System.out.println();
	            Queen[CurrentRow][CurrentColumn] = 0;
	  	      flag[CurrentColumn] = 1;
	  		  flag[n+CurrentColumn+CurrentRow] = 1 ;
	  		  flag[4*n + CurrentColumn -CurrentRow - 2] = 1;
	            return;
	            
		 }
		 for (int j = 0; j < n ; j++ ) {
			 /* Check if queen can be placed on
	            board[CurrentRow+1][j] */
			 if ( flag[j] == 1 && flag[n+j+CurrentRow+1] == 1 && flag[4*n + j -CurrentRow-1 - 2] == 1) {
				 /* recur to place rest of the queens */
				 bettersolver(Queen, flag, CurrentRow+1, j,  n);
             }
	      }
		 /* If placing queen in board[i][col]
	        doesn't lead to a solution, then
	        remove queen from  Queen[CurrentRow][CurrentColumn] */
	      Queen[CurrentRow][CurrentColumn] = 0;//  BACKTRACK
	      flag[CurrentColumn] = 1;//  BACKTRACK
		  flag[n+CurrentColumn+CurrentRow] = 1 ;//  BACKTRACK
		  flag[4*n + CurrentColumn -CurrentRow - 2] = 1;//  BACKTRACK
	}
	 
	/* A recursive utility function to solve N
    Queen problem */
	public static void solver(int[][] Queen, int CurrentRow, int CurrentColumn, int n) {
		/* Place this queen in board[CurrentRow][CurrentColumn] */
		Queen[CurrentRow][CurrentColumn] = 1;
		/* base case: If all queens are placed
	      then print out */
        if (CurrentRow == n-1) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                	 System.out.print(" " + Queen[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
            Queen[CurrentRow][CurrentColumn] = 0;
            return;
        }
        /* Consider this row and try placing
        this queen in all columns one by one */
        for (int j = 0; j < n ; j++ ) {
        	/* Check if queen can be placed on
            board[CurrentRow+1][j] */
            if (IsVaild(Queen, CurrentRow+1, j, n)) {
            	/* recur to place rest of the queens */
                solver(Queen, CurrentRow+1, j,  n);
            }
        }
        /* If placing queen in board[i][col]
        doesn't lead to a solution, then
        remove queen from  Queen[CurrentRow][CurrentColumn] */
        Queen[CurrentRow][CurrentColumn] = 0;//  BACKTRACK
    }
	
	/* A utility function to check if a queen can
    be placed on board[row][col] safely. Note that this
    function is called when "row" queens are already
    placeed in columns from 0 to row -1. So we need
    to check only up side for attacking queens */
    public static boolean IsVaild(int[][] Queen, int CurrentRow, int CurrentColumn,int n) {
    	/* Check this column on upper side */
    	for (int i = CurrentRow-1; i >= 0 ; i--) {
            if(Queen[i][CurrentColumn] == 1) {
                return false;
            }
        }
    	/* Check 135бу diagonal on upper side */
        for (int i = CurrentRow-1, j = CurrentColumn-1; i >= 0 && j >= 0; i--, j-- ) {
             if(Queen[i][j] == 1) {
                 return false;
             }
        }
        /* Check 45бу diagonal on upper side */
        for (int i = CurrentRow-1, j = CurrentColumn+1; i >= 0 && j < n; i--, j++ ) {
             if(Queen[i][j] == 1) {
                 return false;
             }
        }
        return true;
    }

}
