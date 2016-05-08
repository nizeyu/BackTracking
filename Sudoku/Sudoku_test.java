package sudoku;

import java.io.FileNotFoundException;

public class Sudoku_test {
	public static void main(String[] args) throws FileNotFoundException {
		sudoku3 sudoku = new sudoku3();
		sudoku.inputSudokuGame();
//		sudoku.newSudokuGame(sudoku3.DifficultyLevel.HARD);
		
		if(sudoku.solveSudoku() == 0) {
			System.out.println("No solutions found!");
		}
		else {
			sudoku.printSudokuSolutions();			
		}
	}// end main
}
