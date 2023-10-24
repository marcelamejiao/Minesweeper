package minesweeper;

import java.util.Scanner;

public class Game {
	// special characters for visual components
	// create constants 
	public static final char HIDDEN_CELL = '-';
	public static final char BOMB = (char) 9677; 
	
	//colours
	public static final String PURPLE = "\u001B[35m";
	public static final String GREY_BACKGROUND = "\u001B[47m";
	public static final String BLUE = "\u001B[34m";
	public static final String RED = "\u001B[31m";
	public static final String BLACK_BACKGROUND = "\u001B[40m";
	public static final String WHITE ="\u001B[37m";
	
	private char[][] playersMatrix = new char[10][10];
	private int[][] configMatrix = new int[10][10]; 
	int[][] vectorTransformations = {
        {-1,-1},{0,-1},{1,-1},{-1,0},{1,0},{-1,1},{0,1},{1,1}
 	};
	
	private void fillPlayersMatrix() {
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				// convert from ASCII code to character
				char hiddenCell = HIDDEN_CELL;
				
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
		// Mine as an initial value is 9, as any number greater than 8 is not a valid number for the game.
		
		int i = 0;
		while(i < 10) {
			// generate random numbers for row and col as POSITIONS inside the matrix
			int row = ((int) (Math.random() * 10));
			int col = ((int) (Math.random() * 10));
			
			// check if cell is equals to 9 and if it is 9 repeat the loop
			if(this.configMatrix[row][col] == 9) {
				continue;
			}
			// assign the number 9 to each cell that indicates a mine
			this.configMatrix[row][col] = 9;
			i++;
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
					for (int i = 0; i < vectorTransformations.length; ++i) {
					   try {
						   int deltaRow = vectorTransformations[i][0];
						   int deltaCol = vectorTransformations[i][1];
						   
						   int newRow = row + deltaRow;
						   int newCol = col + deltaCol;
						   
						   this.configMatrix[newRow][newCol] += 1;
					   } catch (Exception e) {
						   
					   }
					}
				}	
			}
		}
	}
	
	
	// initialise the game
	public void startGame() {
		// prepare the fields, matrix: 10 x 10 that contains 10 mines.
		this.fillConfigMatrix();
		this.fillPlayersMatrix();
		// display the matrix to the player
		this.displayMatrix();
		
		// this method is cretaed to test
//		this.displayMatrixTest();

		boolean continuePlaying = true;
		// the player can continue playing if has not win or lost.
		while(continuePlaying) {
			// ask the player to provide a coordinate to play.
			this.selectBox();
			
			// check if the player has won or lost, to know if we break the while loop.
			// If the player has lost, break the loop
			if(this.checkIfLose() == true) {
				continuePlaying = false;
				System.out.println(PURPLE + "You have lost! :( ");
			} // If the player has won, break the loop 
			else if (this.checkIfwin() == true){
				continuePlaying = false;
				System.out.println(PURPLE + "You have won! :) ");
			}
			
			// If the player has not won or lost, they need to keep playing so continue the loop
		}
		
		// ask the player if wants to play again or exit the game.
		if(this.askToPlayAgain()== true) {
			this.startGame();
		} else {
			System.out.println("Goodbye :)");
		}
	}
	
	private boolean checkIfLose() {
		
		
		// check the matrix cells to see if there is any mine, if true the game is over 
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				if(this.playersMatrix[row][col] == BOMB) {
					return true;
				} 
			}
		}
		
		return false;	
	}
	
	private boolean checkIfwin() {
		
		int count = 0;
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				if(this.playersMatrix[row][col] == HIDDEN_CELL) {
					count++;
				}
			}
		}
		
		if(count == 10) {
			return true;
		}
		return false;
	}
	
	
	private void displayMatrix() {	
		System.out.println("");
		
		// Draw row that shows the column numbers
		System.out.print(BLACK_BACKGROUND + WHITE + "    ");
		for (int i = 1; i < 11; i++) {
			System.out.print((" " + i + " "));
		}
		System.out.println("");
		
		for(int row = 0; row < 10; row++) {
			
			// Print the column that shows the row numbers
			if (row < 9) {
				System.out.print(BLACK_BACKGROUND + WHITE + "  " + (row + 1) + " ");
			} else {
				System.out.print(BLACK_BACKGROUND + WHITE + " " + (row + 1) + " ");
			}

			for(int col = 0; col < 10; col++) {
				char cell = this.playersMatrix[row][col];
				if(col < 9) {
					System.out.print( GREY_BACKGROUND + BLUE + (" " + cell + " "));
				} else {
					System.out.print( GREY_BACKGROUND + BLUE + (" " + cell + "  "));
				}
			}
			// print each row in a different line 
			System.out.println("");
		}
		System.out.println("");
		
	}
	
	// ONLY FOR TESTING
	private void displayMatrixTest() {	
		System.out.println("");
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				int cell = this.configMatrix[row][col];
				System.out.print(" " + cell + " ");
			}
			// print each row in a different line 
			System.out.println("");
		}
		System.out.println("");
		
	}
	
	private void selectBox(){
		// Ask for coordinates
		int[] coordinates = this.askForCoordinates();
		
		int row = coordinates[0];
		int col = coordinates[1];
		
		// play the coordinates
		this.playCoordinates(row, col);
		
		// after the play show the matrix again
		this.displayMatrix();
	}
	
	private int[] askForCoordinates()
	{
		Scanner s = new Scanner(System.in);
		System.out.println(BLUE + "Please enter the coordinates e.g: 2,4 means row 2 and column 4.");
		String inputFromUser = s.next();
		
		int[] coordinates = new int[2];
		
		// convert string inputFromUser to two integers 
		try {
			String[] arrInputFromUser = inputFromUser.split(",");
			int row = Integer.parseInt(arrInputFromUser[0]) - 1;
			int col = Integer.parseInt(arrInputFromUser[1]) - 1;
			
			coordinates[0] = row;
			coordinates[1] = col;
		} catch (Exception e) {
			System.out.println(RED + "Invalid input. Please try again.");
			coordinates = this.askForCoordinates();
		}
		
		return coordinates;
	}
	
	private void playCoordinates(int row, int col) {
		// check if there is a mine with these coordinates
		int cell = this.configMatrix[row][col];
		
		// if the number is > 8 means it is a mine 
		if(cell > 8) {
			// put a mine symbol in the player matrix
			this.playersMatrix[row][col] = BOMB;
		} else {
			// copy the cell from the configMatrix to the playersMatrix, to get the number in the playersMatrix
			// Character.forDigit needs as the first parameter an integer and the sencond a radix(which means the number system I am using e.g. decimal)
			// this function changes a integer to a character
			this.playersMatrix[row][col] = Character.forDigit(this.configMatrix[row][col], 10);
		}
		
		// if the cell is 0 check its surroundings until there are no more 0s.
		if(cell == 0) {
			for (int i = 0; i < vectorTransformations.length; ++i) {
			   try {
				   int deltaRow = vectorTransformations[i][0];
				   int deltaCol = vectorTransformations[i][1];
				   
				   int newRow = row + deltaRow;
				   int newCol = col + deltaCol;
				   
				   // If the player matrix in the new position still has a square, then we play that coordinate
				   if (this.playersMatrix[newRow][newCol] == HIDDEN_CELL) {
					   this.playCoordinates(newRow, newCol);
				   }
			   } catch (Exception e) {
				   
			   }
			}
		}
	}
	
	
	private boolean askToPlayAgain() {
		Scanner s = new Scanner(System.in);
		System.out.println(BLUE + "Would you like to play again? yes/no");
		String inputFromUser = s.next();
		
		if(inputFromUser.equals("yes")) {
			return true;
		}
		return false;	
	}
}
