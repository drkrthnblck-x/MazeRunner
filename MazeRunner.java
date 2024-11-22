import java.util.Scanner;

public class MazeRunner {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final char WALL = '#';
    private static final char EMPTY = ' ';
    private static final char PLAYER = '@';
    private static final char EXIT = 'E';
    private static int playerX = 1, playerY = 1;
    private static int score = 0;
    private static int currentLevel = 0;
    private static boolean gameOver = false;

    // Define levels using 10x10 grids
    private static char[][][] levels = {
        {   // Level 1
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#' },
            { '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', '#' },
            { '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', '#' },
            { '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#' },
            { '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'E', '#' },
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
        },
        {   // Level 2
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', '#' },
            { '#', ' ', '#', '#', '#', ' ', '#', ' ', ' ', '#' },
            { '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '#' },
            { '#', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', '#' },
            { '#', ' ', '#', '#', '#', ' ', '#', '#', '#', '#' },
            { '#', '#', '#', ' ', ' ', ' ', ' ', '#', '#', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'E', '#' },
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
        },
        {   // Level 3
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', '#' },
            { '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#' },
            { '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', '#' },
            { '#', ' ', '#', '#', '#', ' ', '#', '#', '#', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#' },
            { '#', ' ', '#', '#', '#', ' ', '#', ' ', ' ', ' ' },
            { '#', '#', '#', ' ', ' ', ' ', ' ', '#', '#', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'E', '#' },
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
        }
    };

    // Starting positions for the player on each level
    private static int[] startX = { 1, 1, 1 };
    private static int[] startY = { 1, 1, 1 };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            renderMaze();
            System.out.println("Score: " + score);
            System.out.println("Level: " + (currentLevel + 1));
            System.out.println("Use WASD to move (Q to quit)");

            String move = scanner.nextLine().toLowerCase();
            switch (move) {
                case "w":
                    movePlayer(0, -1);
                    break;
                case "s":
                    movePlayer(0, 1);
                    break;
                case "a":
                    movePlayer(-1, 0);
                    break;
                case "d":
                    movePlayer(1, 0);
                    break;
                case "q":
                    gameOver = true;
                    System.out.println("Game Over! You quit. Final Score: " + score);
                    break;
                default:
                    System.out.println("Invalid move. Use WASD to move.");
                    break;
            }

            // Check if player has reached the exit and move to next level
            if (playerX == WIDTH - 2 && playerY == HEIGHT - 2 && levels[currentLevel][playerY][playerX] == EXIT) {
                score += 10;
                currentLevel++;
                if (currentLevel < levels.length) {
                    // Reset player position to start of next level
                    playerX = startX[currentLevel];
                    playerY = startY[currentLevel];
                } else {
                    gameOver = true;
                    System.out.println("You won! Final Score: " + score);
                }
            }

            if (gameOver) {
                System.out.print("Do you want to restart the game? (y/n): ");
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("y")) {
                    resetGame();
                }
            }
        }

        scanner.close();
    }

    private static void renderMaze() {
        clearConsole();
        char[][] level = levels[currentLevel];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print(PLAYER);  // Print player
                } else {
                    System.out.print(level[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT) {
            if (levels[currentLevel][newY][newX] != WALL) {
                playerX = newX;
                playerY = newY;
            }

            // Game Over if player touches a wall
            if (levels[currentLevel][newY][newX] == WALL) {
                gameOver = true;
                System.out.println("Game Over. You hit a wall! Final Score: " + score);
            }
        }
    }

    private static void resetGame() {
        playerX = startX[0];
        playerY = startY[0];
        score = 0;
        currentLevel = 0;
        gameOver = false;
    }

    private static void clearConsole() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }
}
