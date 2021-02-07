import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShutTheBox {

    private static final int POSSIBLE_LAYOUT_LEN = 512; // Amount of possible layout in shut the box
    private static final int POSSIBLE_SCORE_LEN = 45; // Amount of possible score in shut the box
    private static final int POSSIBLE_ROLL_LEN = 13; // Amount of possible roll in shut the box
    private static final String INITIAL_LAYOUT = "123456789"; // The String representation of initial layout for shut the box

    private static HashMap<Integer, List<Integer>> layoutDict = new HashMap<>(); // map index with topological-sorted layout (in the form of list)
    private static HashMap<Integer, String> layoutStringDict = new HashMap<>(); // map index with topological-sorted layout (in the form of String)
    private static HashMap<String, Integer> reverseLayoutDict = new HashMap<>(); // map topological-sorted layout with index
    private static HashMap<String, Boolean> isLargerThanSixDict = new HashMap<>(); // map layout with a boolean: if the sum is larger than six
    private static HashMap<Integer, Double> oneDiceDict = new HashMap<>(); // map one dice point with the corresponding probability
    private static HashMap<Integer, Double> twoDiceDict = new HashMap<>(); // map two dices point with the corresponding probability
    private static HashMap<String, HashMap<Integer, List<List<Integer>>>> possibleMovesDict = new HashMap<>(); // map layout with a map linking possible roll to all possible moves
    private static double[][] playerOneResult = new double[POSSIBLE_LAYOUT_LEN][POSSIBLE_ROLL_LEN]; // Possible state layout, roll (1-12, 0 is unused) for player 1
    private static double[][][] playerTwoResult = new double[POSSIBLE_LAYOUT_LEN][POSSIBLE_SCORE_LEN][POSSIBLE_ROLL_LEN]; // Possible state layout, opponent score, roll (1-12, 0 is unused) for player 2

    static{
        // Initialize the layout dictionary to map index with topological-sorted layout (in form of list, string, and reverse string)
        initializeLayoutDict();
        // Initialize the dictionary that maps layout with boolean isLargerThanSix
        initializeIsLargerThanSixDict();
        // Initialize the dices dictionary to map dice point with the corresponding probability
        initializeOneDiceDict();
        initializeTwoDiceDict();
        // calculate the result of player two's all possibilities
        generatePlayer2Result();
    }

    /* ---- User Input Handling Zone --- *
    /* ---- START HERE ---- */

    // Handle the calculation for --one --move
    private static List<Integer> calculateP1Move(String layout, int roll) {
        generatePlayer1Result();

        int index = reverseLayoutDict.get(layout);
        List<Integer> layoutList = layoutDict.get(index);
        List<List<Integer>> possibleMoves = calculatePossibleMoves(layoutList, layout, roll);
        double target = playerOneResult[index][roll];

        for(List<Integer> possibleMove: possibleMoves) {
            double curr = calculateP1RandomState(layoutList, possibleMove);
            if(curr == target)
                return possibleMove;
        }

        return new ArrayList<>();
    }

    // Handle the calculation for --one --expect
    private static double calculateP1Expect(String layout) {
        generatePlayer1Result();

        double res = 0;
        int index = reverseLayoutDict.get(layout);
        if(isLargerThanSixDict.get(layout)) {
            for(int roll = 2; roll <= 12; roll++) {
                res += twoDiceDict.get(roll) * playerOneResult[index][roll];
            }
        }
        else {
            for(int roll = 1; roll <= 6; roll++) {
                res += oneDiceDict.get(roll) * playerOneResult[index][roll];
            }
        }

        return res;
    }

    // Handle the calculation for --two --move
    private static List<Integer> calculateP2Move(String layout, int score, int roll) {
        int index = reverseLayoutDict.get(layout);
        List<Integer> layoutList = layoutDict.get(index);
        List<List<Integer>> possibleMoves = calculatePossibleMoves(layoutList, layout, roll);
        double target = playerTwoResult[index][score][roll];

        for(List<Integer> possibleMove: possibleMoves) {
            double curr = calculateP2RandomState(layoutList, possibleMove, score);
            if(curr == target)
                return possibleMove;
        }

        return new ArrayList<>();
    }

    // Handle the calculation for --two --expect
    private static double calculateP2Expect(String layout, int score) {
        double res = 0;
        int index = reverseLayoutDict.get(layout);
        if(isLargerThanSixDict.get(layout)) {
            for(int roll = 2; roll <= 12; roll++) {
                res += twoDiceDict.get(roll) * playerTwoResult[index][score][roll];
            }
        }
        else {
            for(int roll = 1; roll <= 6; roll++) {
                res += oneDiceDict.get(roll) * playerTwoResult[index][score][roll];
            }
        }

        return res;
    }

    /* ---- END HERE ---- */
    /* ---- User Input Handling Zone --- *

    /* ---- Core Algorithm Zone --- *
    /* ---- START HERE ---- */
    private static void generatePlayer1Result() {
        // Calculate for each layout
        for(int layout = 0; layout < POSSIBLE_LAYOUT_LEN; layout++) {
            List<Integer> currLayout = layoutDict.get(layout);
            String currLayoutString = layoutStringDict.get(layout);
            boolean isLargerThanSix = isLargerThanSixDict.get(currLayoutString);

            // Use two dices
            if(isLargerThanSix) {
                for(int roll = 2; roll <= 12; roll++) {
                    List<List<Integer>> combinations = calculatePossibleMoves(currLayout, currLayoutString, roll);
                    // Terminal state
                    if(combinations.size() == 0) {
                        playerOneResult[layout][roll] = calculateP1TerminalState(currLayout);
                    }
                    // Random state
                    else {
                        for(List<Integer> combination: combinations) {
                            playerOneResult[layout][roll] = Math.max(playerOneResult[layout][roll], calculateP1RandomState(currLayout, combination));
                        }
                    }
                }
            }

            // Use one dice
            else {
                for(int roll = 1; roll <= 6; roll++) {
                    List<List<Integer>> combinations = calculatePossibleMoves(currLayout, currLayoutString, roll);
                    // Terminal state
                    if(combinations.size() == 0) {
                        playerOneResult[layout][roll] = calculateP1TerminalState(currLayout);
                    }
                    // Random state
                    else{
                        for(List<Integer> combination: combinations) {
                            playerOneResult[layout][roll] = Math.max(playerOneResult[layout][roll], calculateP1RandomState(currLayout, combination));
                        }
                    }
                }
            } // end of else
        } // end of for
    }

    private static void generatePlayer2Result() {
        // Repeat to calculate for every possible opponent score
        for(int score = 0; score < POSSIBLE_SCORE_LEN; score++) {
            // Calculate for each layout
            for(int layout = 0; layout < POSSIBLE_LAYOUT_LEN; layout++) {
                List<Integer> currLayout = layoutDict.get(layout);
                String currLayoutString = layoutStringDict.get(layout);
                boolean isLargerThanSix = isLargerThanSixDict.get(currLayoutString);

                // Use two dices
                if(isLargerThanSix) {
                    for(int roll = 2; roll <= 12; roll++) {
                        List<List<Integer>> combinations = calculatePossibleMoves(currLayout, currLayoutString, roll);
                        // Terminal state
                        if(combinations.size() == 0) {
                            playerTwoResult[layout][score][roll] = calculateP2TerminalState(currLayout, score);
                        }
                        // Random state
                        else {
                            for(List<Integer> combination: combinations) {
                                playerTwoResult[layout][score][roll] = Math.max(playerTwoResult[layout][score][roll], calculateP2RandomState(currLayout, combination, score));
                            }
                        }
                    }
                }

                // Use one dice
                else {
                    for(int roll = 1; roll <= 6; roll++) {
                        List<List<Integer>> combinations = calculatePossibleMoves(currLayout, currLayoutString, roll);
                        // Terminal state
                        if(combinations.size() == 0) {
                            playerTwoResult[layout][score][roll] = calculateP2TerminalState(currLayout, score);
                        }
                        // Random state
                        else{
                            for(List<Integer> combination: combinations) {
                                playerTwoResult[layout][score][roll] = Math.max(playerTwoResult[layout][score][roll], calculateP2RandomState(currLayout, combination, score));
                            }
                        }
                    }
                } // end of else
            } // end of for (layout)
        } // end of for (score)
    }

    /* ---- END HERE ---- */
    /* ---- Core Algorithm Zone --- */


    /* ---- Helper Functions Zone --- */
    /* ---- START HERE ---- */
    // Initialize the layout dictionary to map index with topological-sorted layout
    private static void initializeLayoutDict() {
        List<Integer> input = new ArrayList<>();
        for(int i = 1; i <= 9; i++)
            input.add(i);

        int index = 0;
        for(List<Integer> subset: subsets(input)) {
            layoutDict.put(index, subset);

            StringBuilder sb = new StringBuilder();
            for (int i : subset)
                sb.append(i);
            layoutStringDict.put(index, sb.toString());
            reverseLayoutDict.put(sb.toString(), index);

            index++;
        }
    }

    // Initialize the dictionary that maps layout with boolean isLargerThanSix
    private static void initializeIsLargerThanSixDict() {
        for(int layout = 0; layout < POSSIBLE_LAYOUT_LEN; layout++) {
            List<Integer> currLayout = layoutDict.get(layout);
            String currLayoutString = layoutStringDict.get(layout);
            int sum = 0;
            for(int i: currLayout)
                sum += i;
            boolean res = sum > 6;
            isLargerThanSixDict.put(currLayoutString, res);
        }
    }

    // Initialize the one dice dictionary to map dice point with the corresponding probability
    private static void initializeOneDiceDict() {
        for(int point = 1; point <= 6; point++)
            oneDiceDict.put(point, 1.0 / 6.0);
    }

    // Initialize the two dice dictionary to map dice point with the corresponding probability
    private static void initializeTwoDiceDict() {
        for(int point = 2; point <= 12; point++) {
            double curr_res = 0;
            for(int i = 1; i <= 6; i++) {
                for(int j = 1; j <= 6; j++) {
                    if((i + j) == point) {
                        curr_res += (1.0 / 36.0);
                    }
                }
            }
            twoDiceDict.put(point, curr_res);
        }
    }

    // Calculate all available moves with input layout and roll, if there is none, return empty list
    private static List<List<Integer>> calculatePossibleMoves(List<Integer> layout, String currLayoutString, int roll) {
        // Avoid redundant calculation
        if(possibleMovesDict.containsKey(currLayoutString) && possibleMovesDict.get(currLayoutString).containsKey(roll))
            return possibleMovesDict.get(currLayoutString).get(roll);

        List<List<Integer>> res = new ArrayList<>();
        for(List<Integer> combination: subsets(layout)) {
            int sum = 0;
            for(int i: combination)
                sum += i;
            if(sum == roll)
                res.add(combination);
        }

        if(!possibleMovesDict.containsKey(currLayoutString))
            possibleMovesDict.put(currLayoutString, new HashMap<>());
        possibleMovesDict.get(currLayoutString).put(roll, res);

        return res;
    }

    // Calculate the result value for Player 1's terminal state
    private static double calculateP1TerminalState(List<Integer> layout) {
        int sum = 0;
        for(int i: layout)
             sum += i;
        // If Player 1 shuts the box, Play 1 wins automatically
        if(sum == 0)
            return 1.0;
        else
            return 1.0 - calculateP2Expect(INITIAL_LAYOUT, sum);
    }

    // Calculate the result value for Player 1's random state
    private static double calculateP1RandomState(List<Integer> layout, List<Integer> combination) {
        List<Integer> currLayout = new ArrayList<>(layout);
        currLayout.removeAll(combination);

        StringBuilder sb = new StringBuilder();
        for(int i:  currLayout)
            sb.append(i);
        int layoutIndex = reverseLayoutDict.get(sb.toString());
        boolean isLargerThanSix = isLargerThanSixDict.get(sb.toString());

        double res = 0;
        if(isLargerThanSix) {
            for(int roll = 2; roll <= 12; roll++)
                res += twoDiceDict.get(roll) * playerOneResult[layoutIndex][roll];
        }
        else {
            for(int roll = 1; roll <= 6; roll++)
                res += oneDiceDict.get(roll) * playerOneResult[layoutIndex][roll];
        }
        return res;
    }

    // Calculate the result value for Player 2's terminal state
    private static double calculateP2TerminalState(List<Integer> layout, int score) {
        // If Player 1 shuts the box, Player 2 loses automatically
        if(score == 0) return 0.0;

        // Calculate the remaining sum
        int sum = 0;
        for(int i: layout)
            sum += i;

        // 1.0 for winning, 0.5 for draw, 0.0 for losing
        if(sum > score)
            return 0.0;
        else if(sum == score)
            return 0.5;
        else
            return 1.0;
    }

    // Calculate the result value for Player 2's random state
    private static double calculateP2RandomState(List<Integer> layout, List<Integer> combination, int score) {
        List<Integer> currLayout = new ArrayList<>(layout);
        currLayout.removeAll(combination);

        StringBuilder sb = new StringBuilder();
        for(int i:  currLayout)
            sb.append(i);
        int layoutIndex = reverseLayoutDict.get(sb.toString());
        boolean isLargerThanSix = isLargerThanSixDict.get(sb.toString());

        double res = 0;
        if(isLargerThanSix) {
            for(int roll = 2; roll <= 12; roll++)
                res += twoDiceDict.get(roll) * playerTwoResult[layoutIndex][score][roll];
        }
        else {
            for(int roll = 1; roll <= 6; roll++)
                res += oneDiceDict.get(roll) * playerTwoResult[layoutIndex][score][roll];
        }
        return res;
    }

    /* ---- END HERE ---- */
    /* ---- Helper Functions Zone --- */

     /* ---- This part is provided by Professor Glenn's CombinatoricsTool.java, https://bitbucket.org/jglenn301/cpsc474_subsets/src/master/java/CombinatoricsTools.java --- */
     /* ---- START HERE ---- */
    private static < T > List< List < T > > subsets(List< T > source) {
        List< List< T > > result = new ArrayList<>();

        // initialize the array
        List<T> elts = new ArrayList<>(source);

        // make array of indices for helper to permute
        int[] indices = new int[elts.size()];
        for (int i = 0; i < indices.length; i++)
            indices[i] = i;

        // helper function adds all subsets of elts of each possible size
        for (int sz = 0; sz <= elts.size(); sz++)
            subsetsHelper(indices, result, elts, 0, sz);

        return result;
    }

    private static <T> void subsetsHelper(int[] indices, List< List< T > > result, List< T > elts, int currCount, int targetCount)
    {
        if (currCount == targetCount) {
            // have the right number of elements; copy them into a set and add that set to the result
            List< T > subset = new ArrayList<>();
            for (int i = 0; i < currCount; i++)
                subset.add(elts.get(indices[i]));
            result.add(subset);
        }

        else {
            // iterate over all possible next elements
            for (int i = currCount; i < indices.length; i++) {
                // enforce selecting elements from elts in order of increasing index so we get each subset only once in the output
                if (currCount == 0 || indices[i] > indices[currCount - 1]) {
                    swap(indices, currCount, i);
                    subsetsHelper(indices, result, elts, currCount + 1, targetCount);
                    swap(indices, currCount, i);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    /* ---- END HERE ---- */
    /* ---- This part is provided by Professor Glenn's CombinatoricsTool.java, https://bitbucket.org/jglenn301/cpsc474_subsets/src/master/java/CombinatoricsTools.java --- */

    public static void main(String[] args) {
        /*
        System.out.format("%.6f", calculateP1Expect("123456789")); // Example 1
        System.out.println();
        System.out.format("%.6f", calculateP1Expect("146789")); // Example 2
        System.out.println();
        System.out.println(calculateP1Move("146789", 9)); // Example 3
        System.out.format("%.6f", calculateP2Expect("123456789", 8)); // Example 4
        System.out.println();
        System.out.format("%.6f", calculateP2Expect("12345689", 41)); // Example 5
        System.out.println();
        System.out.format("%.6f", calculateP2Expect("13456789", 43)); // Example 6
        System.out.println();
        System.out.println(calculateP2Move("13456789", 17, 12)); // Example 7
         */
        if(args[0].equals("--one")) {
            // Handle Player1 move calculation
            if(args[1].equals("--move")) {
                System.out.println(calculateP1Move(args[2], Integer.valueOf(args[3])));
            }
            // Handle Player1 win rate calculation
            else{
                System.out.format("%.6f", calculateP1Expect(args[2]));
                System.out.println();
            }
        }

        else {
            // Handle Player2 move calculation
            if (args[1].equals("--move")) {
                System.out.println(calculateP2Move(args[2], Integer.valueOf(args[3]), Integer.valueOf(args[4])));
            }
            // Handle Player2 win rate calculation
            else {
                System.out.format("%.6f", calculateP2Expect(args[2], Integer.valueOf(args[3])));
                System.out.println();
            }
        }
    }
}