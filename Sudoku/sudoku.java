package backtracking;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;


public class sudoku {
	
	private final int NORMS = 9; //sudoku��9 X 9
	private final int MAX_SOLUTIONS = 10000; //if solutions==1000, end
	private int[][] sudoku = new int[NORMS][NORMS]; //sudoku array
	private int[][] sudoku_copy = new int[NORMS][NORMS]; //Tempt save
	private SudokuSolution solutions = new SudokuSolution(); //solutions
	private int[][][] ctbl; //Candidate array
	private Scanner input;
	//User input Sudoku puzzle
	public void inputSudokuGame() throws FileNotFoundException {
		input = new Scanner(new FileReader("input2.dat"));
		for (int row = 0; row < NORMS; row++) {
			for (int col = 0; col < NORMS; col++) {
				sudoku[row][col] = input.nextInt();
				sudoku_copy[row][col] = sudoku[row][col];
			}
		}
		System.out.println("Your input:");
		printSudoku(false);
	} // end method inputSudoku
	
	//Print array
	public void printSudoku(boolean sparse) {
		for (int row = 0; row < NORMS; row++) {
			for (int col = 0; col < NORMS; col++) {
				if (sudoku[row][col] > 0 && sparse)
					System.out.printf(" *%d", sudoku[row][col]);
				else
					System.out.printf("%3d", sudoku[row][col]);
			}
			System.out.println();
		}
	}// end method printSudoku

	//Save to .txt file
	public void saveSudoku() {
		String filename = "";
		File file = null;
		for (int i = 1; i < 100; i++) {
			filename = "sudoku_" + i + ".txt";
			file = new File(filename);
			if (!file.exists()) {
				break;
			}
			file = null;
		}// end for: look for a valid file name
		try {
			if (null == file)
				file = new File("tmp.txt");
			FileWriter writer = new FileWriter(file);
			for (int row = 0; row < NORMS; row++) {
				String s = new String();
				for (int col = 0; col < NORMS; col++) {
					s += String.valueOf(sudoku[row][col]);
					s += " ";
				}// end for: write a row of the sudoku board
				s += "\r\n";
				writer.write(s);
			}// end for
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.printf("File [%s] created!", filename);
		}// end try-catch-finally block
	}// end method saveSudoku
	
	//sudoku solve
	public int solveSudoku() {
		solutions.clearSolutions();
		int count = 0;
		boolean unique;
		ctbl = new int[NORMS][NORMS][10];
		for (int col = 0; col < NORMS; col++) {
			for (int row = 0; row < NORMS; row++) {
				if (0 == sudoku[row][col]) {
					getConstraint(col, row, ctbl[row][col], true);
					//getConstraint(col, row, ctbl[row][col], false);
//					printArray(ctbl[row][col], new String("constraints(" + row
//							+ ", " + col + ") "));
					count++;
				}
			}
		}// end for: initialize all candiate number
		while (true) {
			unique = false;
			for (int col = 0; col < NORMS; col++) {
				for (int row = 0; row < NORMS; row++) {
					if (1 == constraintSize(ctbl[row][col])) {
						sudoku_copy[row][col] = ctbl[row][col][0]; //save to sudoku_copy
						setCell(row, col, ctbl[row][col][0]);
						count--;
						unique = true;
					}// if cell just have one candiate then put in
				}// end for
			}// end for
			if (0 == count) {
				solutions.addSolution(sudoku);
				break;
			}// If all the cell has been fill ,return this one solution,because this is a very hard sudoku
			if (unique == false) {
				return multipleSolutions(count);
			}// end if: call backtracking method
		}// end while
		return 1;
	}// end method solveSudoku

	//Print the solution of sudoko
	public void printSudokuSolutions() {		
		solutions.printSolutions();
	} // end method printSudokuSolutions
	//Backtracking
	private int multipleSolutions(int count) {
		//count Total number of free cell
		Stack<Integer> s = new Stack<Integer>();
		int results = 0; //Number of solution
		int reach = 0; //how many cell have been fill
		int row = 0;
		int col = 0;
		int next = -1; //order of the candidate
//		System.out.println("After first step resolving:");
//		printSudoku(false);
//		System.out.println();
//		System.out.println("Copy:");
//		printSudoku(sudoku_copy);
//		System.out.println();
		while (true) {
			next = (-1 == next) ? 0 : next;
			if (0 == sudoku[row][col]) {
				while (next >= constraintSize(ctbl[row][col])) {
					if (s.isEmpty() || results == MAX_SOLUTIONS) {
						return results;
					}
					//backtracking
					next = s.pop() + 1;
					do {
						row--;
						if (-1 == row) {
							row = NORMS - 1;
							col--;
						}
					} while (0 != sudoku_copy[row][col]);
					sudoku[row][col] = 0;
					//update the candiate
					updateAllConstraints();
					reach--;
				}
				setCell(row, col, ctbl[row][col][next]);
				reach++;
				s.push(next);
				next = -1;
			} 
			if (reach == count) {
				//find a solution
				results++;
				solutions.addSolution(sudoku);
				
				//backtracking
				next = s.pop() + 1;
				sudoku[row][col] = 0;
				reach--;
				//update the candiate
				updateAllConstraints();
				row--;
			}
			row++;
			if (NORMS == row) {
				row = 0;
				col++;
			}
		} // end while
	}// end method multipleSolutions

	// set the candiate of sudoku[row][col] be val, then partly update the number��same row, same col, same 9 cell��
	private void setCell(int row, int col, int val) {
		sudoku[row][col] = val;
		updateConstraints(row, col, val);
	}// end method setCell

	//get candiate of sudoku[row][col],and put in the array of constraint
	private void getConstraint(int col, int row, int[] constraint,
			boolean allIter) {
		int[] cc = new int[10];
		int[] cr = new int[10];
		int[] cb = new int[10];
		for (int i = 1; i <= NORMS; i++) {
			cc[i] = cr[i] = cb[i] = i;
			constraint[i] = 0;
		}// reset constraints
		constraint[0] = 0;

		int end_row = allIter ? NORMS : row;
		int end_col = allIter ? NORMS : col;
		for (int r = 0; r < end_row; r++) {
			cc[sudoku[r][col]] = 0;
		}// get column constraint
		for (int c = 0; c < end_col; c++) {
			cr[sudoku[row][c]] = 0;
		}// get row constraint
		int br = (row / 3) * 3;
		int bc = (col / 3) * 3;
		for (int c = bc; c < bc + 3; c++) {
			for (int r = br; r < br + 3; r++) {
				cb[sudoku[r][c]] = 0;
			}
		}// get block constraint
		int idx = 0;
		int count;

		for (int i = 1; i <= NORMS; i++) {
			count = 0;
			for (int j = 1; j <= NORMS; j++) {
				count += (cc[j] == i ? 1 : 0);
				count += (cr[j] == i ? 1 : 0);
				count += (cb[j] == i ? 1 : 0);
				if (3 == count)
					break;
			}
			if (3 == count) {
				constraint[idx] = i;
				idx++;
			}
		}// end for(join constraints)

	}// end method getConstraint

	//update the candiate of all cell
	private void updateAllConstraints() {
		for (int col = 0; col < NORMS; col++) {
			for (int row = 0; row < NORMS; row++) {
				if (0 == sudoku[row][col]) {
					getConstraint(col, row, ctbl[row][col], true);
				}
			}
		}// end for
	}// end method updateAllConstraints

	//update the candiate of sudoku[row][col]and delete val
	private void updateConstraints(int row, int col, int val) {
		int len;
		boolean shouldMove;
		for (int _col = 0; _col < NORMS; _col++) {
			len = constraintSize(ctbl[row][_col]);
			shouldMove = false;
			for (int i = 0; i < len; i++) {
				if (false == shouldMove && ctbl[row][_col][i] == val) {
					ctbl[row][_col][i] = 0;
					shouldMove = true;
				}
				if (shouldMove) {
					ctbl[row][_col][i] = ctbl[row][_col][i + 1];
				}
			}
		}// cut out the value from the constraints of the cells in the same row
		for (int _row = 0; _row < NORMS; _row++) {
			len = constraintSize(ctbl[_row][col]);
			shouldMove = false;
			for (int i = 0; i < len; i++) {
				if (false == shouldMove && ctbl[_row][col][i] == val) {
					ctbl[_row][col][i] = 0;
					shouldMove = true;
				}
				if (shouldMove) {
					ctbl[_row][col][i] = ctbl[_row][col][i + 1];
				}
			}
		}// cut out the value from the constraints of the cells in the same
			// column
		for (int i = row / 3 * 3; i < row / 3 * 3 + 3; i++) {
			for (int j = col / 3 * 3; j < col / 3 * 3 + 3; j++) {
				if (i != row && j != col) {
					len = constraintSize(ctbl[i][j]);
					shouldMove = false;
					for (int s = 0; s < len; s++) {
						if (false == shouldMove && ctbl[i][j][s] == val) {
							ctbl[i][j][s] = 0;
							shouldMove = true;
						}
						if (shouldMove) {
							ctbl[i][j][s] = ctbl[i][j][s + 1];
						}
					}
				}
			}
		}// cut out the value from the constraints of the cells in the same
			// block
	}// end method updateConstraints
	//get the range of candiate array
	private int constraintSize(int[] constraint) {
		int len = 0;
		for (int i = 0; i < 10; i++) {
			if (0 == constraint[i]) {
				len = i;
				break;
			}
		}// end for
		return len;
	}// end method constraintSize
}// end class test

//Vector to store the solution of sudoku


