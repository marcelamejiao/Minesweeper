package minesweeper;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
	// methods I could need:
	// startGame, selectBox, newGame, 
	
	private int[][] matrix = new int[10][10]; 
	
	private void fillMatrix() {
		this.allocateMines();
		this.placeNumbersAroundMines();
	}
	
	private void allocateMines() {
		// place the mines inside the matrix 
		// TODO: fill the first row of the matrix. To distribute all the mines inside the matrix
		// Mine as an initial value is 9, as any number greater than 8 is not a valid number for the game.
		for(int i = 0; i < 10; i++ ) {
			this.matrix[0][i] = 9;
		}
	}
		
	private void placeNumbersAroundMines() {
		// place the numbers around the mines, indicating how many mines are adjacent to them.
		// look for an X
		
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col  < 10; col++) {
				int cell = this.matrix[row][col];
				// When number is > 8 is a Mine.
				if(cell > 8) {
					this.incrementLeft(row, col);
					this.incrementRight(row, col);
					this.incrementTop(row, col);
					this.incrementBottom(row, col);
					this.incrementTopLeft(row, col);
					this.incrementBottomLeft(row, col);
					this.incrementTopRight(row, col);
					this.incrementBottomRight(row, col);
				}	
			}
		}
	}
	
	private void incrementLeft(int row, int col) {
		if(col == 0) {
			return;
		} 				
		this.matrix[row][col - 1] += 1;
	}
	
	private void incrementRight(int row, int col) {
		if(col == 9) {
			return;
		}
		this.matrix[row][col + 1] += 1;
	}

	private void incrementTop(int row, int col) {
		if(row == 0) {
			return;
		}
		
		this.matrix[row - 1][col] += 1;	
	}

	private void incrementBottom(int row, int col) {
		if(row == 9) {
			return;
		}
		
		this.matrix[row + 1][col] += 1;	
	}

	private void incrementTopLeft(int row, int col) {
		if(row == 0 || col == 0) {
			return;
		}
		
		this.matrix[row -1][col - 1] += 1;	
	}

	private void incrementBottomLeft(int row, int col) {
		if(row == 9 || col == 0) {
			return;
		}
		
		this.matrix[row + 1][col -1] += 1;	
	}

	private void incrementTopRight(int row, int col) {
		if(row == 0 || col == 9) {
			return;
		}
		
		this.matrix[row - 1][col + 1] += 1;	
	}

	private void incrementBottomRight(int row, int col) {
		if(row == 9 || col == 9) {
			return;
		}
		
		this.matrix[row + 1][col + 1] += 1;
	}

	// initialise the game
	public void startGame() {
		// prepare the fields, matrix: 10 x 10 that contains 10 mines.
		this.fillMatrix();
		// display the matrix to the player
		this.displayMatrix();
		// ask the player to provide a coordinate to play.
		this.selectBox();
		// check if there is a mine at that coordinate.
		// if the player is allowed to continue playing, show the matrix again,
		// if the player loses show a message, if wins show another message.
		// ask the player if wants to play again or exit the game.
	}
	
	private void displayMatrix() {	
		System.out.println("");
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				int cell = this.matrix[row][col];
				if(cell > 8) {
					System.out.print(" x ");
				} else {
					System.out.print(" " + cell + " ");
				}
			}
			// print each row in a different line 
			System.out.println("");
		}
		System.out.println("");
		
	}
	
	
	public void selectBox(){
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter the coordinates e.g: 2,4 means row 2 and column 4.");
		String inputFromUser = s.next();
		System.out.println(inputFromUser);
		
		// convert string inputFromUser to two integers 
		String[] arrInputFromUser = inputFromUser.split(",");
		
		int row = Integer.parseInt(arrInputFromUser[0]);
		int col = Integer.parseInt(arrInputFromUser[1]);
		
		System.out.println(row);
		System.out.println(col);
		
	}
	
	public void newGame() {
		
	}
	
	
	
	
	
	
	
	
	
	
}
