package backtracking;
import java.io.FileNotFoundException;
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		sudoku sudoku = new sudoku();
		long start = System.currentTimeMillis();
		sudoku.inputSudokuGame();
//		sudoku.newSudokuGame(sudoku3.DifficultyLevel.HARD);
		
		if(sudoku.solveSudoku() == 0) {
			System.out.println("No solutions found!");
		}
		else {
			sudoku.printSudokuSolutions();			
		}
		System.out.println("Runtime :"+(System.currentTimeMillis() - start)+"ms");
	}// end main
}
