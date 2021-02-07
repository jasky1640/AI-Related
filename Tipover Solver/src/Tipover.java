import java.util.*; // ArrayList and Scanner

public class Tipover {
    private final static String DELIMITER = " "; // The delimiter for coordinate storage in HashSet and solution path
    private final static int[] ROW_OFFSET = {-1, 1, 0, 0}; // up, down, left, right
    private final static int[] COL_OFFSET = {0, 0, -1, 1}; // up, down, left, right

    // This function uses Depth-First Search and Backtracking to search for current tipover game's solution path (if exists)
    private static List<String> backtrack(int curr_row, int curr_col, int[][] curr_board, List<String> path, HashSet<String> visited) {
        // Base case: goal state
        if(curr_board[curr_row][curr_col] == -1)
            return path;

        // Mark the current coordinate as visited for this particular board
        visited.add(curr_row + DELIMITER + curr_col);

        // Explore all possible tipover, which could be tipover to up/down/left/right
        for(int index = 0; index < 4; index++) {
            // up, down, left, right
            int row_move = ROW_OFFSET[index], col_move = COL_OFFSET[index];

            // A legal tipover is 1)in boundary and 2) no other blocks in the path
            if(isLegalTipOver(curr_row, curr_col, row_move, col_move, curr_board)) {
                int height = curr_board[curr_row][curr_col];

                // Update the board to reflect the tipover
                updateBoard(curr_row, curr_col, row_move, col_move, curr_board);

                // Update the new tipover to the path solution
                List<String> new_path = new ArrayList<>(path);
                new_path.add(curr_row + DELIMITER + curr_col + DELIMITER + row_move + DELIMITER + col_move);

                List<String> res = backtrack(curr_row + row_move, curr_col + col_move, curr_board, new_path, new HashSet<>());
                // Find the solution
                if(res != null) {
                    return res;
                }
                // Otherwise we recover the board to original layout
                else{
                    recoverBoard(curr_row, curr_col, row_move, col_move, height, curr_board);
                }
            }
        }

        // Explore all possible moves (not tipover), which is up/down/left/right
        for(int index = 0; index < 4; index++) {
            // up, down, left, right
            int moved_row = curr_row + ROW_OFFSET[index], moved_col = curr_col + COL_OFFSET[index];

            // A legal move is 1) not visited, 2) in boundary, and 3) not walking into lava
            if(!visited.contains(moved_row + DELIMITER + moved_col) && isLegalMove(moved_row, moved_col, curr_board)) {

                List<String> res = backtrack(moved_row, moved_col, curr_board, new ArrayList<>(path), visited);
                if(res != null) {
                    return res;
                }
            }
        }

        // If we reach here, it means this is a dead end
        return null;
    }

    // This function verifies whether the move is 1) not visited, 2) in boundary, and 3) not walking into lava
    private static boolean isLegalMove(int curr_row, int curr_col, int[][] curr_board) {
        // Boundary check
        if(curr_row < 0 || curr_row >= curr_board.length || curr_col < 0 || curr_col >= curr_board[0].length) return false;
        // Lava check
        return curr_board[curr_row][curr_col] != 0;
    }

    // This function verifies whether the tipover is 1)in boundary and 2) no other blocks in the path
    private static boolean isLegalTipOver(int curr_row, int curr_col, int row_move, int col_move, int[][] curr_board) {
        // Only stack with 2 ore more height can be tipped over
        if(curr_board[curr_row][curr_col] <= 1) return false;
        int moved_row = curr_row, moved_col = curr_col, height = curr_board[curr_row][curr_col];

        for(int index = 0; index < height; index++) {
            moved_row += row_move;
            moved_col += col_move;
            // boundary check
            if(moved_row < 0 || moved_row >= curr_board.length || moved_col < 0 || moved_col >= curr_board[0].length) return false;
            // obstacle check
            if(curr_board[moved_row][moved_col] != 0) return false;
        }

        return true;
    }

    // This function updates the board layout to reflect the tipover move
    private static void updateBoard(int curr_row, int curr_col, int row_move, int col_move, int[][] curr_board) {
        int moved_row = curr_row, moved_col = curr_col, height = curr_board[curr_row][curr_col];
        curr_board[moved_row][moved_col] = 0;

        for(int index = 0; index < height; index++) {
            moved_row += row_move;
            moved_col += col_move;
            curr_board[moved_row][moved_col] = 1;
        }
    }

    // This function recovers the board layout from the tipover move
    private static void recoverBoard(int curr_row, int curr_col, int row_move, int col_move, int height, int[][] curr_board) {
        int moved_row = curr_row, moved_col = curr_col;
        curr_board[moved_row][moved_col] = height;
        for(int index = 0; index < height; index++) {
            moved_row += row_move;
            moved_col += col_move;
            curr_board[moved_row][moved_col] = 0;
        }
    }

    // The main function reads input from stdin to build the initial board layout, calls find_solution function to solve, and prints out path solution (if exists)
    public static void main(String[] args) {
        try{
            // This section reads the input from stdin and verifies the input
            Scanner scanner = new Scanner(System.in);
            int rowLen = scanner.nextInt();
            if(rowLen <= 0) System.exit(0);

            int colLen = scanner.nextInt();
            if(colLen <= 0) System.exit(0);

            int startRow = scanner.nextInt();
            if(startRow < 0 || startRow >= rowLen) System.exit(0);

            int startCol = scanner.nextInt();
            if(startCol < 0 || startCol >= colLen) System.exit(0);

            List<String> rows = new ArrayList<>();
            for(int i = 0; i < rowLen; i++) {
                rows.add(scanner.next());
                if(rows.get(i).length() != colLen) System.exit(0);
            }
            scanner.close();

            // Generate the initial board layout: 0 for lava, >= 1 for stack with that height, -1 for goal
            int[][] board = new int[rowLen][colLen];
            for(int r = 0; r < rowLen; r++) {
                char[] arr = rows.get(r).toCharArray();
                for(int c = 0; c < colLen; c++) {
                    char curr = arr[c];
                    if(curr == '.') board[r][c] = 0;
                    else if(curr == '*') board[r][c] = -1;
                    else board[r][c] = Character.getNumericValue(curr);
                }
            }

            // If the starting point is in lava, we have no solution
            if(board[startRow][startCol] == 0) System.exit(0);

            List<String> res = backtrack(startRow, startCol, board, new ArrayList<>(), new HashSet<>());
            if(res != null)
                for(String path: res)
                    System.out.println(path);
        }
        catch (InputMismatchException e) {
            System.exit(0);
        }
    }
}