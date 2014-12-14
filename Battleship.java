import java.util.Scanner;
import java.util.Random;

public class Battleship {

    public static void main(String[] args) {

        // Set up the player boards. Each element in the 2D array represents a position on the board, with the following
        // meanings:
        //  0 - No guess made yet, no ship at this location
        //  1 - No guess made yet, ship located here!
        //  2 - Guess made, this was a hit!
        //  3 - Guess made, this was a miss!
        // The boards are both initialized to 0 (empty), and you will write methods that place 1s randomly across the
        // board to represent the ships.
        int [][] playerOneBoard = new int[10][10];
        int [][] playerTwoBoard = new int[10][10];

        // Set up ship sizes and the number of hits it takes to win the game.
        int ship1Size = 5, ship2Size = 4, ship3Size = 3, ship4Size = 3, ship5Size = 2;
        int maxHits = ship1Size + ship2Size + ship3Size + ship4Size + ship5Size;
        int player = 1;

        // Randomly place each of the five ship sizes for each player's board.
        System.out.println("Placing ships...");
        placeShip(playerOneBoard, ship1Size);
        placeShip(playerOneBoard, ship2Size);
        placeShip(playerOneBoard, ship3Size);
        placeShip(playerOneBoard, ship4Size);
        placeShip(playerOneBoard, ship5Size);
        placeShip(playerTwoBoard, ship1Size);
        placeShip(playerTwoBoard, ship2Size);
        placeShip(playerTwoBoard, ship3Size);
        placeShip(playerTwoBoard, ship4Size);
        placeShip(playerTwoBoard, ship5Size);
        System.out.println("Ships have been placed!");

        Scanner scan = new Scanner(System.in);
        
        // Loop until someone wins the game.
        while(true) {
            System.out.println("Player " + playerName(player) + ", it's your turn!");

            // This is the board the current player will be guessing against - the other player's board.
            int [][] targetBoard;
            if (player == 1) {
                targetBoard = playerTwoBoard;
            }
            else {
                targetBoard = playerOneBoard;
            }

            // Loop until the player has made a valid guess
            boolean fire = false;
            while (!fire) {
                System.out.println("Choose an option, " + playerName(player) + ".");
                System.out.println("1 - Fire!");
                System.out.println("2 - Print your shots");
                System.out.println("3 - Cheat");

                // Get the player's selection
                String input = scan.next();
                int selection;
                try {
                    selection = Integer.parseInt(input);
                }
                catch (Exception e) {
                    selection = -1;
                }
                switch(selection) {
                
                    // Fire at a particular cell
                    case 1:

                        // Prompt the user for a row and column to identify the cell. Remember, arrays are 0-indexed,
                        // so we need to decrement each by 1 to get the proper index.
                        System.out.println("What row?");
                        int row = scan.nextInt();
                        row--;
                        System.out.println("What col?");
                        int col = scan.nextInt();
                        col--;

                        // Check if the guess is valid. If so, make the guess and tell the user if it was a hit or a
                        // miss.
                        if (isValidGuess(targetBoard, row, col)) {
                            if (makeGuess(targetBoard, row, col)) {
                                System.out.println(" --- HIT!!! ---\n");
                            }
                            else {
                                System.out.println(" --- Miss :( ---\n");
                            }
                            fire = true;
                        } else {
                            System.out.println("Please enter a valid row and col!\n");
                        }
                        break;

                    // Print out the guesses (misses, hits, nothing guessed) for the board the current player is
                    // targeting
                    case 2:
                        printBoard(targetBoard);
                        break;

                    // Reveal the underlying values (0: no guess, 1: ship, 2: hit, 3: miss) for the board the current
                    // player is targeting
                    case 3:
                        revealBoard(targetBoard);
                        break;
                    default:
                        System.out.println("Please make a valid selection!\n");
                }
            }

            // Check if the game is over after the guess was made. If everything has been hit, the current player wins.
            if (gameOver(targetBoard, maxHits)) {
                System.out.println("GAME OVER! " + playerName(player) + " WINS!!!");
                return;
            }

            // Change players for the next round.
            player = -player;

        }

    }

    /********************************* YOUR CODE GOES IN THE METHODS BELOW! *******************************************/

    // Place a ship horizontally to the left of the given col within the given row, with the given size.
    public static void placeShipLeft(int[][] gameBoard, int row, int col, int shipSize)
        for(int counter = 0; counter < shipSize; counter++) {
            gameBoard[row - counter][col] = 1;
        }
    }

    // Place a ship horizontally to the right of the given col within the given row, with the given size.
    public static void placeShipRight(int[][] gameBoard, int row, int col, int shipSize) {
        for(int counter = 0; counter < shipSize; counter++) {
            gameBoard[row + counter][col] = 1;
        }
    }

    // Place a ship vertically above the given row within the given col, with the given size. 
    public static void placeShipUp(int[][] gameBoard, int row, int col, int shipSize) {
        for(int counter = 0; counter < shipSize; counter++) {
            gameBoard[row][col - counter] = 1;
        }
    }

    // Place a ship vertically below the given row within the given col, with the given size.
    public static void placeShipDown(int[][] gameBoard, int row, int col, int shipSize) {
        for(int counter = 0; counter < shipSize) {
            gameBoard[row][col + counter] = 1;
        }
    }

    // Validate that a ship placed in the given row starting at startCol and ending at endCol will be valid.
    public static boolean isValidShipRow(int[][] gameBoard, int startCol, int endCol, int row) {
        boolean result = true;
        if(startCol > 0 && endCol < 9) {
            for(int counter = startCol; counter = endCol; counter++) {
                if(gameBoard[row][counter]==1) {
                    result = false;
                }
            }
        }
        return result;
    }

    // Validate that a ship placed in the given col starting at startRow and ending at endRow will be valid.
    public static boolean isValidShipCol(int[][] gameBoard, int startRow, int endRow, int col) {
        boolean result = true;
        if(startRow > 0 && endRow < 9) {
            for(int counter = startRow; counter = endRow; counter++) {
                if(gameBoard[counter][col]==1) {
                    result = false;
                }
            }
        }
        return result;
    }

    // Returns true if the row and col represent a cell that is a valid guess, false otherwise.
    public static boolean isValidGuess (int[][] gameBoard, int row, int col) {
        boolean result = false;
        if(row >= 0 && row <= 9) {
            if(col >= 0 && col <= 9) {
                if(gameBoard[row][col] == 0 || gameBoard[row][col] == 1) {
                    result = true;
                }
            }
        }
        return result;
    }

    // Make a guess for the given row and col, returning true if the guess was a HIT and false if the guess was a MISS.
    public static boolean makeGuess (int[][] gameBoard, int row, int col) {
        boolean result = false;
        if(gameBoard[row][col] == 0) {
            gameBoard[row][col] = 3;
        }
        else if(gameBoard[row][col] == 1) {
            result = true;
            gameBoard[row][col] = 2;
        }
        return result;
    }

    // Return true if the game is over for the given board and max number of hits, false otherwise.
    public static boolean gameOver(int[][] playerBoard, int maxHits) {
        int hitSum = 0;
        boolean result = false;
        for(int counterh = 0; counterh = 9; counterh++) {
            for(int counterv = 0; counterv = 9; counterv++) {
                if(gameBoard[counterh][counterv] ==2) {
                    hitSum++;
                }
            }
        }
        if(hitSum ==maxHits) {
            result = true;
        }
        return result;
    }

    /******************************************************************************************************************/

    // Place a ship of a given size on the game board at a random location, in a random orientation (horizontal or
    // vertical) and a random direction (left or right or up or down). Keep generating new locations, orientations, and
    // directions until the ship has been validly placed on the board.
    public static void placeShip(int[][] gameBoard, int shipSize) {
        Random random = new Random();
        boolean shipPlaced = false;
        while (!shipPlaced) {
            int orientation = random.nextInt(2); // 0 for horizontal, 1 for vertical
            int direction = random.nextInt(2); // 0 for left/up, 1 for right/down
            int row = random.nextInt(10);
            int col = random.nextInt(10);

            if (orientation == 0) {
                if (direction == 0) {
                    if (isValidShipRow(gameBoard, col - shipSize + 1, col, row)) {
                        placeShipLeft(gameBoard, row, col, shipSize);
                        shipPlaced = true;
                    }
                }
                else { 
                    if (isValidShipRow(gameBoard, col, col + shipSize - 1, row)) {
                        placeShipRight(gameBoard, row, col, shipSize);
                        shipPlaced = true;
                    }
                }
            }
            else {
                if (direction == 0) {
                    if (isValidShipCol(gameBoard, row - shipSize + 1, row, col)) {
                        placeShipUp(gameBoard, row, col, shipSize);
                        shipPlaced = true;
                    }
                }
                else { 
                    if (isValidShipCol(gameBoard, row, row + shipSize - 1, col)) {
                        placeShipDown(gameBoard, row, col, shipSize);
                        shipPlaced = true;
                    }
                }
            }
        }

    }

    // Print out the board with the guesses that have been made.    
    public static void printBoard(int[][] playerBoard) {
        System.out.println("   1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < playerBoard.length; i++) {
            System.out.print(i + 1);
            if (i + 1 < 10) {
                System.out.print(" ");
            }
            System.out.print("|");
            for (int j = 0; j < playerBoard.length; j++) {
                // This guess was a miss
                if (playerBoard[i][j] == 3) {
                    System.out.print("O|");
                }
                // This guess was a hit
                else if (playerBoard[i][j] == 2) {
                    System.out.print("X|");
                }
                // This hasn't been guessed yet
                else {
                    System.out.print(" |");
                }
            }
            System.out.println("\n");
        }
    }

    // Reveal the underlying representation of the board. Useful for debugging to make sure your board is being updated
    // properly. Remember, 0 means no guess; 1 means there is a ship; 2 means the cell has been guessed and it's a hit;
    // 3 means the cell has been guessed and it's a miss.
    public static void revealBoard(int[][] playerBoard) {
        System.out.println("   1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < playerBoard.length; i++) {
            System.out.print(i + 1);
            if (i + 1 < 10) {
                System.out.print(" ");
            }
            System.out.print("|");
            for (int j = 0; j < playerBoard.length; j++) {
                System.out.print(playerBoard[i][j]+"|");
            }
            System.out.println("\n");
        }
    }

    // Helper function to translate between player number and the player's name.
    public static String playerName(int playerNum) {
        if (playerNum == 1) {
            return "Player 1";
        }
        return "Player 2";
    }

}
