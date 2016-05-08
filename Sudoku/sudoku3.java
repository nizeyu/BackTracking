package sudoku;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class sudoku3 {
	private final int NORMS = 9; //������ģ��9 X 9
	private final int MAX_SOLUTIONS = 10000; //�⵽10000���Ͳ�����
	private int[][] sudoku = new int[NORMS][NORMS]; //����������ݵľ���
	private int[][] sudoku_copy = new int[NORMS][NORMS]; //һ�������������㷨ʵ���е�һ����������
	private SudokuSolution solutions = new SudokuSolution(); //��Ž�
	private int[][][] ctbl; //��ź�ѡ���ֵľ���
	
	//ö�٣��򵥡��еȡ�����������Ϸ���׳̶�
	public enum DifficultyLevel {
		EASY, MEDIUM, HARD
	} // end enumeration DifficultyLevel

	//����һ����������
	public void generateSudoku() {
		while (!generate()) {

		}
		SudokuSolution.copySudoku(sudoku, sudoku_copy);
	} // end method generateSudoku
	
	//�����ɵ�������������ȡ������ʵ������һ��������Ϸ
	public void newSudokuGame(DifficultyLevel level) {
		generateSudoku();
		int nRemove = 0;
		switch (level) {
		case EASY:
			nRemove = 30;
			break;
		case MEDIUM:
			nRemove = 50;
			break;
		case HARD:
			nRemove = 70;
			break;
		default:
			break;
		}
		Random random = new Random();
		for (int i = 0; i < nRemove; i++) {
			int x = random.nextInt(NORMS);
			int y = random.nextInt(NORMS);
			sudoku[x][y] = sudoku_copy[x][y] = 0;
		}
		System.out.println("New game:");
		printSudoku(false);
		System.out.println();
	} // end method newSudokuGame

	//�û�������������
	public void inputSudokuGame() {
		Scanner scanner = new Scanner(System.in);
		for (int row = 0; row < NORMS; row++) {
			for (int col = 0; col < NORMS; col++) {
				sudoku[row][col] = scanner.nextInt();
				sudoku_copy[row][col] = sudoku[row][col];
			}
		}
		System.out.println("Your input:");
		printSudoku(false);
	} // end method inputSudoku
	
	//��ӡ����
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

	//����������txt�ļ���
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
	
	//�����������
	public int solveSudoku() {
		solutions.clearSolutions();
		int count = 0;
		boolean unique;
		ctbl = new int[NORMS][NORMS][10];
		for (int col = 0; col < NORMS; col++) {
			for (int row = 0; row < NORMS; row++) {
				if (0 == sudoku[row][col]) {
					getConstraint(col, row, ctbl[row][col], true);
//					printArray(ctbl[row][col], new String("constraints(" + row
//							+ ", " + col + ") "));
					count++;
				}
			}
		}// end for: ��ʼ�����еĺ�ѡ����
		while (true) {
			unique = false;
			for (int col = 0; col < NORMS; col++) {
				for (int row = 0; row < NORMS; row++) {
					if (1 == constraintSize(ctbl[row][col])) {
						sudoku_copy[row][col] = ctbl[row][col][0]; //ͬ��sudoku_copy
						setCell(row, col, ctbl[row][col][0]);
						count--;
						unique = true;
					}// �������ֻ��һ����ѡ���־�����
				}// end for
			}// end for
			if (0 == count) {
				solutions.addSolution(sudoku);
				break;
			}// ������еĸ��������ˣ���ô˵���������������һ���������⣬Ҳ����ֻ��һ���⣬���ؾͿ�����
			if (unique == false) {
				return multipleSolutions(count);
			}// end if: ���û��ݷ������������
		}// end while
		return 1;
	}// end method solveSudoku

	//��ӡ��������Ľ�
	public void printSudokuSolutions() {		
		solutions.printSolutions();
	} // end method printSudokuSolutions

	//����������һ��ѭ��
	private boolean generate() {
		int[] constraint = new int[10];
		resetSudoku();
		for (int col = 0; col < NORMS; col++) {
			for (int row = 0; row < NORMS; row++) {
				getConstraint(col, row, constraint, false);
				if (0 == constraint[0]) {
					return false;
				}
				sudoku[row][col] = randomCandidate(constraint);
			}// end for
		}// end for
		return true;
	} // end method generate

	//���ݷ������������
	private int multipleSolutions(int count) {
		//count �Ǵ���ĸ��ӵ�������
		Stack<Integer> s = new Stack<Integer>();
		int results = 0; //��õĽ�����
		int reach = 0; //��¼�Ѿ���д�ĸ�����
		int row = 0;
		int col = 0;
		int next = -1; //�����ѡ�����������е����
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
					//����
					next = s.pop() + 1;
					do {
						row--;
						if (-1 == row) {
							row = NORMS - 1;
							col--;
						}
					} while (0 != sudoku_copy[row][col]);
					sudoku[row][col] = 0;
					//���º�ѡ����
					updateAllConstraints();
					reach--;
				}
				setCell(row, col, ctbl[row][col][next]);
				reach++;
				s.push(next);
				next = -1;
			} 
			if (reach == count) {
				//���һ����
				results++;
				solutions.addSolution(sudoku);
				
				//����
				next = s.pop() + 1;
				sudoku[row][col] = 0;
				reach--;
				//���º�ѡ����
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

	// ���ø���sudoku[row][col]��ѡ���� val, ���ֲ����º�ѡ���֣�ͬ�С�ͬ�С�ͬ�飩
	private void setCell(int row, int col, int val) {
		sudoku[row][col] = val;
		updateConstraints(row, col, val);
	}// end method setCell

	//��ȡsudoku[row][col]�ĺ�ѡ���ִ���constraint������
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

	//�������еĸ��ӵĺ�ѡ����
	private void updateAllConstraints() {
		for (int col = 0; col < NORMS; col++) {
			for (int row = 0; row < NORMS; row++) {
				if (0 == sudoku[row][col]) {
					getConstraint(col, row, ctbl[row][col], true);
				}
			}
		}// end for
	}// end method updateAllConstraints

	//����sudoku[row][col]�ĺ�ѡ���֣�ȥ��val
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

	//�Ӻ�ѡ��������constraint�л�ȡһ������ĺ�ѡ����
	private int randomCandidate(int[] constraint) {
		int len = constraintSize(constraint);
		Random r = new Random();
		int result = r.nextInt(len);
		return constraint[result];
	}// end method randomCandidate

	//����sudoku_copy��������
	private void resetSudoku() {
		for (int row = 0; row < NORMS; row++) {
			for (int col = 0; col < NORMS; col++) {
				sudoku[row][col] = sudoku_copy[row][col];
			}
		}// end for
	}// end method resetSudoku

	//��ȡ��ѡ��������Ĵ�С
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

	//��ӡsudoku���ڵ��Ե�ʱ���õ�
	private void printSudoku(int[][] sudoku) {

		for (int row = 0; row < NORMS; row++) {
			for (int col = 0; col < NORMS; col++) {
				System.out.printf("%3d", sudoku[row][col]);
			}// end for
			System.out.println();
		}// end for

	}

	//��ӡһ��һά���飬���Ե�ʱ���õ�
	private void printArray(int[] array, String description) {
		System.out.print(description + ": ");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}// end method printArray: for test, print a 1-d vector(array)
}// end class test

//һ�����ʾ��������Ľ⣬��Ҫ��һ��Vector���������������Ľ�
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

	//��ӡ���еĽ�
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

	//һ����̬�Ŀ���֮�俽�����ݵķ���
	static void copySudoku(int[][] src, int[][] dest) {
		for (int row = 0; row < NORMS; row++) {
			for (int col = 0; col < NORMS; col++) {
				dest[row][col] = src[row][col];
			}
		}
	}// end method copySudoku
}// end inner class SodukuSolution: for storing the solutions of the board

