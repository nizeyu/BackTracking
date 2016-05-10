package backtracking;

import java.util.Vector;


class SudokuSolution {
	private static final int NORMS = 9;

	class Node {
		int[][] Sudoku = new int[NORMS][NORMS];

		Node(int[][] solution) {
			copySudoku(solution, Sudoku);
		}
	}// end inner class Node

	Vector<Node> Solutions;

	SudokuSolution() {
		Solutions = new Vector<Node>();
	}

	void addSolution(int[][] solution) {
		Node node = new Node(solution);
		Solutions.add(node);
	}

	void clearSolutions() {
		Solutions.clear();
	}

	//Print all the solution
	void printSolutions() {
		int[][] solution;
		for (int i = 0; i < Solutions.size(); i++) {
			solution = Solutions.get(i).Sudoku;
			System.out.println("Solution: " + (i + 1));
			for (int row = 0; row < NORMS; row++) {
				for (int col = 0; col < NORMS; col++) {
					System.out.print("  " + solution[row][col]);
				}
				System.out.println();
			}// end for: print row by row
		}// end for
	}// end method printSolutions

	//copy mehtod
	static void copySudoku(int[][] src, int[][] dest) {
		for (int row = 0; row < NORMS; row++) {
			for (int col = 0; col < NORMS; col++) {
				dest[row][col] = src[row][col];
			}
		}
	}// end method copySudoku
}// end inner class SodukuSolution: for storing the solutions of the board
