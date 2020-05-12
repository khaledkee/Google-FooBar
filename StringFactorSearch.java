public class StringFactorSearch {
    // Overall complexity = O(n^2 * d(n)) where d(n) is the divisor function
    // Possible optimization is to hash substrings x[i..i+l] for all l that divides n
    // Complexity will become O(n^2) but not worth complication for n <= 200
    public static int solution(String x) {
        // Search for substring y=x[i..i+k] such that x can be factored into non-overlapping copies of y (up to cycle)
        // It's always possible to have y = x
        int result = 1;

        for (int start = 0; start < x.length(); start++) {
            // y can't be of length < start + 1 because we already tried x[start-l..start] for all l <= start
            for (int length = start + 1; length < x.length(); length++) {
                // y must have a length equal to a divisor of the length of x
                // Possible optimization here is to use the sqrt integer factorization speedup: only look at lengths L
                // less than sqrt(x.length()) and the other factors can be found using x.length() / L
                if (x.length() % length != 0) {
                    continue;
                }
                int number = x.length() / length;
                // if we already found better solution skip. Increasing length decreases number
                if(number <= result) break;
                boolean can = true;
                // y = x[start..start+length]
                for (int index = 0; index < x.length(); index++) {
                    // x[index] == y[index % length]
                    // x[index] == x[(start + (index % length)) % x.length()]
                    if(x.charAt(index) != x.charAt((start + (index % length)) % x.length())) {
                        can = false;
                        break;
                    }
                }
                if (can) {
                    result = number;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(solution("abcabcabcabc"));
        System.out.println(solution("abccbaabccba"));
    }
}