import java.util.*;

import com.joptimizer.exception.JOptimizerException;
import com.joptimizer.optimizers.*;

public class Blotto {
    private static HashMap<Integer, List<Integer>> pureStrategyMap = new HashMap<>(); // pureStrategyMap maps index to one possible pure strategy
    private static HashMap<String, Integer> pureStrategyIndexMap = new HashMap<>(); // pureStrategyIndexMap maps string representation of pure strategy to its index in pureStrategyMap
    private static HashMap<Integer, String> pureStrategyStringMap = new HashMap<>(); // pureStrategyStringMap maps index with string representation of pure strategy

    /* ---- User Input Handling Zone --- *
    /* ---- START HERE ---- */

    // Handle the request to find mixed strategy to find max points
    private static void handleFindMaxPoint(int unitAmount, List<Integer> battleGround, double tolerant) {
        initializePureStrategyMap(unitAmount, battleGround.size());
        // Amount of pure strategies
        int rows = pureStrategyMap.size(), minBG = battleGround.get(0);
        for(int bg: battleGround) minBG = Math.min(minBG, bg);

        // A matrix for linear Programming method
        double[][] pointMatrix = createPointMatrix(battleGround, minBG / 0.1);

        // b matrix for linear Programming method
        double[] b = new double[rows];
        Arrays.fill(b, -1);

        // lowerBounds matrix for linear Programming method
        double[] lowerBounds = new double[rows * 2]; // auto-init to 0

        // upperBounds matrix for linear Programming method
        double[] upperBounds = new double[rows * 2];
        for(int r = 0; r < rows; r++)
            upperBounds[r] = 10;
        for(int r = rows; r < upperBounds.length; r++)
            upperBounds[r] = 1;

        // objective matrix for linear Programming method
        double[] objective = new double[rows * 2];
        for(int r = 0; r < rows; r++)
            objective[r] = 1;

        double[] strategy = linearProgramming(rows, pointMatrix, b, lowerBounds, upperBounds, objective);
        for(int i = 0; i < strategy.length; i++)
            if(strategy[i] > tolerant)
                System.out.println(pureStrategyStringMap.get(i) + strategy[i]);
    }

    // Handle the request to find mixed strategy to find max wins
    private static void handleFindMaxWin(int unitAmount, List<Integer> battleGround, double tolerant) {
        initializePureStrategyMap(unitAmount, battleGround.size());
        // Amount of pure strategies
        int rows = pureStrategyMap.size();

        // A matrix for linear Programming method
        double[][] winMatrix = createWinMatrix(battleGround);

        // b matrix for linear Programming method
        double[] b = new double[rows];
        Arrays.fill(b, -1);

        // lowerBounds matrix for linear Programming method
        double[] lowerBounds = new double[rows * 2]; // auto-init to 0

        // upperBounds matrix for linear Programming method
        double[] upperBounds = new double[rows * 2];
        for(int r = 0; r < rows; r++)
            upperBounds[r] = 10;
        for(int r = rows; r < upperBounds.length; r++)
            upperBounds[r] = 1;

        // objective matrix for linear Programming method
        double[] objective = new double[rows * 2];
        for(int r = 0; r < rows; r++)
            objective[r] = 1;

        double[] strategy = linearProgramming(rows, winMatrix, b, lowerBounds, upperBounds, objective);
        for(int i = 0; i < strategy.length; i++)
            if(strategy[i] > tolerant)
                System.out.println(pureStrategyStringMap.get(i) + strategy[i]);
    }

    // Handle the request to verify if the input is mixed strategy to find max points
    private static void handleVerifyMaxPoint(List<String> mixedStrategy, List<Integer> battleGround, double tolerant) {
        int unitAmount = findUnitAmount(mixedStrategy.get(0), battleGround.size());
        initializePureStrategyMap(unitAmount, battleGround.size());
        verifyMaxPointStrategy(mixedStrategy, battleGround, tolerant);
    }

    // Handle the request to verify if the input is mixed strategy to find max wins
    private static void handleVerifyMaxWin(List<String> mixedStrategy, List<Integer> battleGround, double tolerant) {
        int unitAmount = findUnitAmount(mixedStrategy.get(0), battleGround.size());
        initializePureStrategyMap(unitAmount, battleGround.size());
        verifyMaxWinStrategy(mixedStrategy, battleGround, tolerant);
    }

    /* ---- END HERE ---- */
    /* ---- User Input Handling Zone --- */

    /* ---- Core Algorithm Zone --- *
    /* ---- START HERE ---- */

    // Verify if the mixed strategy is the equilibrium strategy for player 1 to yield max points
    private static void verifyMaxPointStrategy(List<String> mixedStrategy, List<Integer> battleGround, double tolerant) {
        int battleGroundSize = battleGround.size(), sum = 0;
        for (int point : battleGround) sum += point;

        HashMap<String, Double> probMap = generateProbMap(mixedStrategy, battleGroundSize);
        double lowerBound = sum / 2.0 - tolerant, upperBound = sum / 2.0 + tolerant;

        // Player 1 use mixed strategy, Player 2 use pure strategy. Expect E(x*, y*) <= E(x*, j)
        for (int strategy = 0; strategy < pureStrategyMap.size(); strategy++) {
            double res = calculatePointResultP2Pure(battleGround, pureStrategyMap.get(strategy), probMap);
            if (res < lowerBound) {
                System.out.println("E(x*, " + pureStrategyMap.get(strategy) + ") = " + res + " < " + lowerBound);
                return;
            }
        }

        // Player 1 use pure strategy, Player 2 use mixed strategy. Expect E(i, y*) <= E(x*, y*)
        for (int strategy = 0; strategy < pureStrategyMap.size(); strategy++) {
            double res = calculatePointResultP1Pure(battleGround, pureStrategyMap.get(strategy), probMap);
            if (res > upperBound) {
                System.out.println("E(" + pureStrategyMap.get(strategy) + ", y*) = " + res + " > " + upperBound);
                return;
            }
        }

        System.out.println("PASSED");
    }

    // Verify if the mixed strategy is the equilibrium strategy for player 1 to yield max wins
    private static void verifyMaxWinStrategy(List<String> mixedStrategy, List<Integer> battleGround, double tolerant) {
        int battleGroundSize = battleGround.size();
        HashMap<String, Double> probMap = generateProbMap(mixedStrategy, battleGroundSize);

        double lowerBound = 0.5 - tolerant, upperBound = 0.5 + tolerant;

        // Player 1 use mixed strategy, Player 2 use pure strategy. Expect E(x*, y*) <= E(x*, j)
        for (int strategy = 0; strategy < pureStrategyMap.size(); strategy++) {
            double res = calculateWinResultP2Pure(battleGround, pureStrategyMap.get(strategy), probMap);
            if (res < lowerBound) {
                System.out.println("E(x*, " + pureStrategyMap.get(strategy) + ") = " + res + " < " + lowerBound);
                return;
            }
        }

        // Player 1 use pure strategy, Player 2 use mixed strategy. Expect E(i, y*) <= E(x*, y*)
        for (int strategy = 0; strategy < pureStrategyMap.size(); strategy++) {
            double res = calculateWinResultP1Pure(battleGround, pureStrategyMap.get(strategy), probMap);
            if (res > upperBound) {
                System.out.println("E(" + pureStrategyMap.get(strategy) + ", y*) = " + res + " > " + upperBound);
                return;
            }
        }

        System.out.println("PASSED");
    }

    /* ---- END HERE ---- */
    /* ---- Core Algorithm  Zone --- */

    /* ---- Helper Function Zone --- *
    /* ---- START HERE ---- */

    // Initialize pureStrategyMap which maps index to one possible pure strategy and pureStrategyIndexMap which maps string representation of pure strategy to its index in pureStrategyMap
    private static void initializePureStrategyMap(int unitAmount, int battleGroundSize) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        initializePureStrategyMapRecursive(unitAmount, battleGroundSize, 0, new ArrayList<>(), output);

        int index = 0;
        for (ArrayList<Integer> list : output) {
            pureStrategyMap.put(index, list);

            StringBuilder sb = new StringBuilder();
            for (int i : list)
                sb.append(i).append(",");
            int len = sb.length();
            pureStrategyIndexMap.put(sb.toString().substring(0, len - 1), index);
            pureStrategyStringMap.put(index, sb.toString());
            index++;
        }
    }

    // Recursive helper function for initializePureStrategyMap
    private static void initializePureStrategyMapRecursive(int target, int battleGroundSize, int currSum, ArrayList<Integer> result, ArrayList<ArrayList<Integer>> output) {
        // If we already generate a full-length array list
        if (result.size() == battleGroundSize) {
            if (currSum == target)
                output.add(result);
            return;
        }

        for (int curr = 0; curr <= target; curr++) {
            int newSum = currSum + curr;
            // If the new sum exceeds the target, we don't need to iterate anymore
            if (newSum > target) break;

            ArrayList<Integer> newResult = new ArrayList<>(result);
            newResult.add(curr);
            initializePureStrategyMapRecursive(target, battleGroundSize, newSum, newResult, output);
        }
    }

    // Create the point matrix for Player 1, row = player 1 pure strategy, col = player 2 pure strategy (The output is negative transposed)
    private static double[][] createPointMatrix(List<Integer> battleGround, double factor) {
        int len = pureStrategyMap.size(), sum = 0;
        for (int point : battleGround) sum += point;

        double[][] output = new double[len][len * 2];
        for (int row = 0; row < len; row++) {
            for (int col = row; col < len; col++) {
                if (row == col)
                    output[row][col] = ((sum / 2.0) / factor) * -1;
                else {
                    double p1Score = calculatePointResult(battleGround, pureStrategyMap.get(row), pureStrategyMap.get(col));
                    output[col][row] = (p1Score / factor) * -1;
                    output[row][col] = ((sum - p1Score) / factor) * -1;
                }
            }
        }

        for(int row = 0; row < len; row++)
            output[row][len + row] = 1;
        
        return output;
    }

    // Calculate the point result for player 1 against pure strategy
    private static double calculatePointResult(List<Integer> battleGround, List<Integer> p1Strategy, List<Integer> p2Strategy) {
        double p1Score = 0;
        for (int i = 0; i < battleGround.size(); i++) {
            int battleValue = battleGround.get(i), p1Troop = p1Strategy.get(i), p2Troop = p2Strategy.get(i);
            if (p1Troop > p2Troop) p1Score += battleValue;
            else if (p1Troop == p2Troop) p1Score += battleValue / 2.0;
        }
        return p1Score;
    }

    // Calculate the point result for player 1 with pure strategy against player 2 with mixed strategy
    private static double calculatePointResultP1Pure(List<Integer> battleGround, List<Integer> p1Strategy, HashMap<String, Double> p2Strategy) {
        double res = 0;
        for (String str : p2Strategy.keySet()) {
            List<Integer> p2curr = pureStrategyMap.get(pureStrategyIndexMap.get(str));
            res += p2Strategy.get(str) * calculatePointResult(battleGround, p1Strategy, p2curr);
        }
        return res;
    }

    // Calculate the point result for player 2 with pure strategy against player 1 with mixed strategy
    private static double calculatePointResultP2Pure(List<Integer> battleGround, List<Integer> p2Strategy, HashMap<String, Double> p1Strategy) {
        double res = 0;
        for (String str : p1Strategy.keySet()) {
            List<Integer> p1curr = pureStrategyMap.get(pureStrategyIndexMap.get(str));
            res += p1Strategy.get(str) * calculatePointResult(battleGround, p1curr, p2Strategy);
        }
        return res;
    }

    // Create the win matrix for Player 1, row = player 1 pure strategy, col = player 2 pure strategy (The output is negative transposed)
    private static double[][] createWinMatrix(List<Integer> battleGround) {
        int len = pureStrategyMap.size();

        double[][] output = new double[len][len * 2];
        for (int row = 0; row < len; row++) {
            for (int col = row; col < len; col++) {
                if (row == col)
                    output[row][col] = -0.6;
                else {
                    double p1Score = calculateWinResult(battleGround, pureStrategyMap.get(row), pureStrategyMap.get(col)) + 0.1;
                    output[col][row] = p1Score * (p1Score == 0 ? 1 : -1);
                    if (p1Score == 0.6) output[row][col] = -0.6;
                    else if (p1Score == 1.1) output[row][col] = -0.1;
                    else output[row][col] = -1.1;
                }
            }
        }

        for(int row = 0; row < len; row++)
            output[row][len + row] = 1;

        return output;
    }

    // Calculate the win result for player 1 against pure strategy
    private static double calculateWinResult(List<Integer> battleGround, List<Integer> p1Strategy, List<Integer> p2Strategy) {
        double p1Score = 0, p2Score = 0;
        for (int i = 0; i < battleGround.size(); i++) {
            int battleValue = battleGround.get(i), p1Troop = p1Strategy.get(i), p2Troop = p2Strategy.get(i);
            if (p1Troop > p2Troop) {
                p1Score += battleValue;
            } else if (p1Troop == p2Troop) {
                p1Score += battleValue / 2.0;
                p2Score += battleValue / 2.0;
            } else {
                p2Score += battleValue;
            }
        }

        if (p1Score > p2Score) return 1.0;
        else if (p1Score == p2Score) return 0.5;
        else return 0.0;
    }

    // Calculate the point result for player 1 with pure strategy against player 2 with mixed strategy
    private static double calculateWinResultP1Pure(List<Integer> battleGround, List<Integer> p1Strategy, HashMap<String, Double> p2Strategy) {
        double res = 0;
        for (String str : p2Strategy.keySet()) {
            List<Integer> p2curr = pureStrategyMap.get(pureStrategyIndexMap.get(str));
            res += p2Strategy.get(str) * calculateWinResult(battleGround, p1Strategy, p2curr);
        }
        return res;
    }

    // Calculate the point result for player 2 with pure strategy against player 1 with mixed strategy
    private static double calculateWinResultP2Pure(List<Integer> battleGround, List<Integer> p2Strategy, HashMap<String, Double> p1Strategy) {
        double res = 0;
        for (String str : p1Strategy.keySet()) {
            List<Integer> p1curr = pureStrategyMap.get(pureStrategyIndexMap.get(str));
            res += p1Strategy.get(str) * calculateWinResult(battleGround, p1curr, p2Strategy);
        }
        return res;
    }

    // Generate the probMap, which maps the pure strategy with its probability
    private static HashMap<String, Double> generateProbMap(List<String> mixedStrategy, int battleGroundSize) {
        HashMap<String, Double> output = new HashMap<>();
        for (String str : mixedStrategy) {
            int commaCount = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == ',') {
                    commaCount++;
                    if (commaCount == battleGroundSize) {
                        output.put(str.substring(0, i), Double.valueOf(str.substring(i + 1)));
                    }
                }
            }
        }
        return output;
    }

    // Based on the input, find the unit amount
    private static int findUnitAmount(String strategy, int battleGroundSize) {
        int res = 0, sum = 0, remain = battleGroundSize;
        for(int i = 0; i < strategy.length() && remain > 0; i++) {
            char curr = strategy.charAt(i);
            if(Character.isDigit(curr)) {
                sum = sum * 10 + Character.getNumericValue(curr);
            }
            else if(curr == ',') {
                res += sum;
                sum = 0;
                remain--;
            }
        }
        return res;
    }

    /* ---- END HERE ---- */
    /* ---- Helper Function Zone --- */

    /* ---- JOptimizer Linear Programming Example Provided BY Professor Jim Glenn --- *
    /* ---- START HERE ---- */
    private static double[] linearProgramming(int rows, double[][] A, double[] b, double[] lowerBounds, double[] upperBounds, double[] objective) {
        LPOptimizationRequest prob = new LPOptimizationRequest();
        prob.setC(objective);
        prob.setA(A);
        prob.setB(b);
        prob.setLb(lowerBounds);
        prob.setUb(upperBounds);
        prob.setTolerance(1e-08);

        LPPrimalDualMethod opt = new LPPrimalDualMethod();
        opt.setLPOptimizationRequest(prob);
        try{
            opt.optimize();
        }
        catch(JOptimizerException e){
            System.out.println("JOptimizerException detected. Fix your linear programming input.");
        }
        double[] pSolution = opt.getOptimizationResponse().getSolution();

        // solution gives us p1,...,pm; v = 1/(p1+...+pm); x1 = p1*v
        double denom = 0.0;
        for (int r = 0; r < rows; r++) {
            denom += pSolution[r];
        }
        double v = 1.0 / denom;
        double[] xSolution = new double[rows];
        for (int r = 0; r < rows; r++) {
            xSolution[r] = pSolution[r] * v;
        }
        return xSolution;
    }

    /* ---- END HERE ---- */
    /* ---- JOptimizer Linear Programming Example Provided BY Professor Jim Glenn --- */

    public static void main(String[] args){
        double tolerant = Math.pow(10, -6); // Default tolerant threshold
        int ptr = 0;
        boolean isFindRequest = true; // If true, then we are handling find equilibrium request; Else, we are handling verify request.
        boolean isScoreRequest = true; // If true, then we are handling find the strategy to yield max total point scored; Else, max expected number of win


        if (args[ptr].equals("--verify"))
            isFindRequest = false;
        ptr++; // Move to args[1]

        // If there is customized tolerance threshold, we need to record it
        if (args[ptr].equals("--tolerance")) {
            ptr++; // Move to args[2]
            tolerant = Double.valueOf(args[2]);
            ptr++; // Move to args[3]
        }

        // Need to find the strategy to yield max total point scored
        if (args[ptr].equals("--win"))
            isScoreRequest = false;
        ptr++; // Move to args[4] if has tolerance, args[2] if not

        // If we are handling find request, we need to take input from arguments
        if (isFindRequest) {
            ptr++; // Move to args[5] if has tolerance, args[3] if not in order to skip --units

            int unitAmount = Integer.valueOf(args[ptr]);
            List<Integer> battleGround = new ArrayList<>();
            ptr++; // Move to args[6] if has tolerance, args[4] if not
            for (; ptr < args.length; ptr++) {
                battleGround.add(Integer.valueOf(args[ptr]));
            }

            if (isScoreRequest)
                handleFindMaxPoint(unitAmount, battleGround, tolerant);
            else
                handleFindMaxWin(unitAmount, battleGround, tolerant);
        }

        // If we are handling verify request, we need to take input from stdin
        else {
            List<Integer> battleGround = new ArrayList<>();
            for (; ptr < args.length; ptr++) {
                battleGround.add(Integer.valueOf(args[ptr]));
            }

            // Take the mixed strategy from stdin
            Scanner scanner = new Scanner(System.in);
            List<String> mixedStrategy = new ArrayList<>();
            while (scanner.hasNextLine()) {
                mixedStrategy.add(scanner.nextLine());
            }
            scanner.close();

            if (isScoreRequest)
                handleVerifyMaxPoint(mixedStrategy, battleGround, tolerant);
            else
                handleVerifyMaxWin(mixedStrategy, battleGround, tolerant);
        }
    }
}