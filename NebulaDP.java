import java.util.Arrays;

public class NebulaDP {
    public static int solution(boolean[][] g) {
        int h = g.length;
        int w = g[0].length;
        // Solve using the following induction:
        // State: number of columns left to build, the chosen state of the previous column in the previous step
        // Base case:
        //    if there are no columns left: there is only one solution
        // Induction step:
        //   try all states of the current column:
        //   check the validity of the current column by simulating the step and ensure that the combination of the
        //   current and previous column produces the previous column in the current step.
        // Use Dynamic Programming to solve this induction
        int states = (1 << (h + 1));
        int columns = w + 1;
        int[][] dp = new int[columns + 1][states];
        // Base case
        Arrays.fill(dp[columns], 1);
        // Build bottom-up, move backwards
        for (int column = columns - 1; column >= 0; column--) {
            // loop over previous column state in the previous step
            for (int previousState = 0; previousState < states; previousState++) {
                int result = 0;
                // loop over current column state in the previous step
                for (int currentState = 0; currentState < states; currentState++) {
                    if (column == 0 || checkStates(g, column - 1, previousState, currentState))
                        result += dp[column + 1][currentState];
                }
                dp[column][previousState] = result;
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        System.out.println(solution(new boolean[][]{{true, false, true}, {false, true, false}, {true, false, true}}));
        System.out.println(solution(new boolean[][]{
                {true, true, false, true, false, true, false, true, true, false},
                {true, true, false, false, false, false, true, true, true, false},
                {true, true, false, false, false, false, false, false, false, true},
                {false, true, false, false, false, false, true, true, false, false}
        }));
    }

    public static boolean checkStates(boolean[][] currentStep, int column, int stateCol1, int stateCol2) {
        for (int row = 0; row < currentStep.length; row++) {
            int count = 0;
            if ((stateCol1 & (1 << row)) != 0) count++;
            if ((stateCol1 & (1 << (row + 1))) != 0) count++;
            if ((stateCol2 & (1 << row)) != 0) count++;
            if ((stateCol2 & (1 << (row + 1))) != 0) count++;
            if (currentStep[row][column] != (count == 1))
                return false;
        }
        return true;
    }
}
