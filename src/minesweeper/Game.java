package minesweeper;

import java.util.Scanner;

public class Game {
	// methods I could need:
	// startGame, selectBox, newGame, 
	
	private char[][] playersMatrix = new char[10][10];
	private int[][] configMatrix = new int[10][10]; 
	
	private void fillPlayersMatrix() {
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				// convert from ASCII code to character
				char hiddenCell = (char) 9635;
				
				// Add the hidden cell character to the matrix
				this.playersMatrix[row][col] = hiddenCell;
			}
		}
	}
	
	private void fillConfigMatrix() {
		this.allocateMines();
		this.placeNumbersAroundMines();
	}
	
	private void allocateMines() {
		// place the mines inside the matrix 
		// TODO: fill the first row of the matrix. To distribute all the mines inside the matrix
		// Mine as an initial value is 9, as any number greater than 8 is not a valid number for the game.
		for(int i = 0; i < 10; i++ ) {
			this.configMatrix[0][i] = 9;
		}
	}
		
	private void placeNumbersAroundMines() {
		// place the numbers around the mines, indicating how many mines are adjacent to them.
		// look for an X
		
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col  < 10; col++) {
				int cell = this.configMatrix[row][col];
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
		this.configMatrix[row][col - 1] += 1;
	}
	
	private void incrementRight(int row, int col) {
		if(col == 9) {
			return;
		}
		this.configMatrix[row][col + 1] += 1;
	}

	private void incrementTop(int row, int col) {
		if(row == 0) {
			return;
		}
		
		this.configMatrix[row - 1][col] += 1;	
	}

	private void incrementBottom(int row, int col) {
		if(row == 9) {
			return;
		}
		
		this.configMatrix[row + 1][col] += 1;	
	}

	private void incrementTopLeft(int row, int col) {
		if(row == 0 || col == 0) {
			return;
		}
		
		this.configMatrix[row -1][col - 1] += 1;	
	}

	private void incrementBottomLeft(int row, int col) {
		if(row == 9 || col == 0) {
			return;
		}
		
		this.configMatrix[row + 1][col -1] += 1;	
	}

	private void incrementTopRight(int row, int col) {
		if(row == 0 || col == 9) {
			return;
		}
		
		this.configMatrix[row - 1][col + 1] += 1;	
	}

	private void incrementBottomRight(int row, int col) {
		if(row == 9 || col == 9) {
			return;
		}
		
		this.configMatrix[row + 1][col + 1] += 1;
	}

	
	// initialise the game
	public void startGame() {
		// prepare the fields, matrix: 10 x 10 that contains 10 mines.
		this.fillConfigMatrix();
		this.fillPlayersMatrix();
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
				char cell = this.playersMatrix[row][col];
				System.out.print(" " + cell + " ");
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
		
		// convert string inputFromUser to two integers 
		String[] arrInputFromUser = inputFromUser.split(",");
		int row = Integer.parseInt(arrInputFromUser[0]);
		int col = Integer.parseInt(arrInputFromUser[1]);
		
		// check if there is a mine with these coordinates
		int cell = this.configMatrix[row][col];
		
		// if the number is > 8 means it is a mine 
		if(cell > 8) {
			// put a mine symbol in the player matrix
			this.playersMatrix[row][col] = (char) 9677;
		} else {
			// copy the cell from the configMatrix to the playersMatrix, to get the number in the playersMatrix
			// Character.forDigit needs as the first parameter an integer and the sencond a radix(which means the number system I am using e.g. decimal)
			// this function changes a integer to a character
			this.playersMatrix[row][col] = Character.forDigit(this.configMatrix[row][col], 10);
		}
		
		// after the play show the matrix again
		this.displayMatrix();
		
	}
	
	public void newGame() {
		
	}
	
	
	
	
	
	
	
	
	
	
}
