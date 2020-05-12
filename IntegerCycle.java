import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IntegerCycle {

    private static void reverse(char[] arr) {
        for (int i = 0; i < arr.length - i - 1; i++) {
            char temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }

    public static int solution(String n, int b) {
        Map<String, Integer> seen = new HashMap<String, Integer>();
        while (!seen.containsKey(n)) {
            seen.put(n, seen.size());
            char[] nCharArr = n.toCharArray();
            Arrays.sort(nCharArr);
            String y = new String(nCharArr);
            reverse(nCharArr);
            String x = new String(nCharArr);
            n = sub(x, y, b);
        }
        return seen.size() - seen.get(n);
    }

    public static void main(String[] args) {
        System.out.println(solution("210022", 3));
        System.out.println(solution("1211", 10));
    }

    private static String sub(String a, String b, int base) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        for (int i = ((int)a.length()) - 1; i >= 0; i--) {
            int value = a.charAt(i) - b.charAt(i) + carry;
            carry = 0;
            if(value < 0) {
                carry = -1;
                value += base;
            }
            value %= base;
            result.append(value);
        }
        result.reverse();
        return result.toString();
    }
}
