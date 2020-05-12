import java.math.BigInteger;

public class BinaryFuel {
    // Complexity O(N) for the solution but it's dominated by Java's BigInteger.toString(base) complexity
    public static int solution(String x) {
        // convert to binary
        char[] binary = new BigInteger(x).toString(2).toCharArray();

        int result = 0;
        // move in reverse
        for (int digit = binary.length - 1; digit > 0; digit--) {
            if (binary[digit] == '0') result += 1; // divide by 2
            else {
                if (digit <= 1 || binary[digit - 1] == '0') { // a bit set to 1 and not followed by 1
                    result += 2; // subtract and divide by 2
                } else {
                    // find the interval of 1s
                    int intervalBound = digit;
                    for (; intervalBound >= 0 && binary[intervalBound] == '1'; intervalBound--) ;
                    // all chars from intervalBound + 1 to digit are 1
                    result += 1; // add 1
                    result += (digit - intervalBound); // divide by 2 for the length of the interval
                    digit = intervalBound + 1; // fast-forward
                    if (digit > 0) binary[digit - 1] = '1'; // set the next bit outside the interval to 1 instead of 0
                    /* else if digit < 0 add one more bit with 1 but that will not affect result */
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(solution("15"));
        System.out.println(solution("4"));
        for (int it = 1; it <= 5000; it++) {
//            int x = ThreadLocalRandom.current().nextInt(1, 5000);
            int x = it;
            if(stress(x) != solution(String.valueOf(x))) {
                System.out.println("Solution for " + x + " is: " + stress(x) + " and got: " + solution(String.valueOf(x)));
            }
        }
    }

    private static int stress(int x) {
        if (x <= 1) return 0;
        if (x % 2 == 0) return 1 + stress(x / 2);
        return 1 + Math.min(stress(x + 1), stress(x - 1));
    }
}
