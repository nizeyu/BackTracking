import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class nqueens {
//     int N;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//long start = System.nanoTime();
	    //time consuming code here.
	    //...
	   // long end = System.nanoTime();
//	    long used = end-start;
//	    System.out.println("used:"+TimeUnit.NANOSECONDS.toMillis(used)+" ms");
    
  // System.out.println("Enter the number of queens");
		//Scanner reader = new Scanner(System.in);
		long start = System.nanoTime();
//		nqueens queen = new nqueens();
		//int n = reader.nextInt();
		int n = 12;
//		final int n = queen.N;
		int[][] Queen = new int[n][n];
		int[] flag = new int[5*n-2];
		Arrays.fill(flag,1);
//	       List<List<String>> result = new ArrayList<List<String>>();
	    for (int j = 0; j < n; j++) {
	         //solver(Queen, 0, j, n);
	         bettersolver(Queen, flag, 0, j , n);
	     }
	    long end = System.nanoTime();
	    long used = end-start;
	    System.out.println("used:"+TimeUnit.NANOSECONDS.toMillis(used)+" ms");
	}
	public static void bettersolver(int[][] Queen, int[] flag, int CurrentRow, int CurrentColumn, int n) {
		Queen[CurrentRow][CurrentColumn] = 1;
		flag[CurrentColumn] = 0;
		flag[n+CurrentColumn+CurrentRow] = 0 ;
		flag[4*n + CurrentColumn -CurrentRow - 2] = 0;
		if (CurrentRow == n-1) {
	            for (int i = 0; i < n; i++) {
	                //String s = new String();
	                for (int j = 0; j < n; j++) {
	                	 System.out.print(" " + Queen[i][j] + " ");
	                	// System.out.println();
//	                    if (Queen[i][j] == 1) {
//	                        s += "Q";
//	                    } else {
//	                        s += ".";
//	                    }
	                }
	                System.out.println();
//	                solution.add(s);
	            }
	            System.out.println();
	            Queen[CurrentRow][CurrentColumn] = 0;
	  	      flag[CurrentColumn] = 1;
	  		  flag[n+CurrentColumn+CurrentRow] = 1 ;
	  		  flag[4*n + CurrentColumn -CurrentRow - 2] = 1;
	            return;
	            
		 }
		 for (int j = 0; j < n ; j++ ) {
			 if ( flag[j] == 1 && flag[n+j+CurrentRow+1] == 1 && flag[4*n + j -CurrentRow-1 - 2] == 1) {
                bettersolver(Queen, flag, CurrentRow+1, j,  n);
             }
	      }
	      Queen[CurrentRow][CurrentColumn] = 0;
	      flag[CurrentColumn] = 1;
		  flag[n+CurrentColumn+CurrentRow] = 1 ;
		  flag[4*n + CurrentColumn -CurrentRow - 2] = 1;
	}
	public static void solver(int[][] Queen, int CurrentRow, int CurrentColumn, int n) {
        Queen[CurrentRow][CurrentColumn] = 1;
        if (CurrentRow == n-1) {
            //List<String> solution = new ArrayList<String> ();
            for (int i = 0; i < n; i++) {
                //String s = new String();
                for (int j = 0; j < n; j++) {
                	 System.out.print(" " + Queen[i][j] + " ");
                }
                System.out.println();
//                solution.add(s);
            }
            System.out.println();
//            result.add(solution);
            Queen[CurrentRow][CurrentColumn] = 0;
            return;
            
        }
        for (int j = 0; j < n ; j++ ) {
            if (IsVaild(Queen, CurrentRow+1, j, n)) {
                solver(Queen, CurrentRow+1, j,  n);
            }
        }
        Queen[CurrentRow][CurrentColumn] = 0;
        
    }
    public static boolean IsVaild(int[][] Queen, int CurrentRow, int CurrentColumn,int n) {
        for (int i = CurrentRow-1; i >= 0 ; i--) {
            if(Queen[i][CurrentColumn] == 1) {
                return false;
            }
        }
        for (int i = CurrentRow-1, j = CurrentColumn-1; i >= 0 && j >= 0; i--, j-- ) {
             if(Queen[i][j] == 1) {
                 return false;
             }
        }
        for (int i = CurrentRow-1, j = CurrentColumn+1; i >= 0 && j < n; i--, j++ ) {
             if(Queen[i][j] == 1) {
                 return false;
             }
        }
        return true;
        
        
    }

}
