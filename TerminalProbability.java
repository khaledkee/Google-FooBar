import java.util.ArrayList;
import java.util.List;

public class TerminalProbability {
    /**
     * m(u, v) is the probability of moving from u to v
     * P(v) = probability of reaching v at any point in time
     * P(0) = 1
     * P(v) = Sum(m(u, v) * P(u)) for all u
     * We can model this as Linear system of equations Ax=B where x represents P
     * A[0] = [1 0 0 .... 0], B[0] = 1, representing P(0) * 1 + Sum(P(x) * 0) = 1
     * EQUATION i: 1 * P(i) - m(j,i) * P(j) for all j = 0
     * B[i] = 0 for i > 0
     *
     * @param mInt
     * @return probability of reach each terminal state
     */
    public static int[] solution(int[][] mInt) {
        int n = mInt.length;
        // initialize m
        Fraction[][] m = new Fraction[n][n];
        List<Integer> terminals = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) sum += mInt[i][j];
            if (sum == 0) {
                sum = 1;
                terminals.add(i);
            }
            for (int j = 0; j < n; j++)
                m[i][j] = new Fraction(mInt[i][j], sum);
        }
        // initialize matrix A
        // Add dump start node at 0 connected to the real 0 and shift index for all others
        // Add one more column to A equal to B to make it easier in gaussian elimination
        Fraction[][] A = new Fraction[n + 1][n + 2];

        A[0][0] = new Fraction(1);
        for (int j = 1; j <= n; j++) A[0][j] = new Fraction();
        A[0][n + 1] = new Fraction(1);

        for (int i = 1; i <= n; i++) {
            A[i][0] = new Fraction();
            for (int j = 1; j <= n; j++)
                A[i][j] = m[j - 1][i - 1].negate();
            A[i][i] = A[i][i].add(new Fraction(1));
            A[i][n + 1] = new Fraction();
        }
        A[1][0] = new Fraction(-1);
        // gaussian elimination
        // based on: https://cp-algorithms.com/linear_algebra/linear-system-gauss.html
        int[] where = new int[n + 1];
        for (int col = 0, row = 0; col < n + 1 && row < n + 1; col++) {
            int sel = row;
            for (int i = row + 1; i < n + 1; i++)
                if (A[i][col].abs().compareTo(A[sel][col].abs()) > 0)
                    sel = i;
            if (A[sel][col].numerator == 0)
                continue;

            if (row != sel)
                for (int i = col; i <= n + 1; ++i)
                    A[row][i].swap(A[sel][i]);
            where[col] = row;

            for (int i = 0; i < n + 1; ++i)
                if (i != row) {
                    Fraction c = A[i][col].multiply(A[row][col].inverse());
                    for (int j = col; j <= n + 1; ++j)
                        A[i][j] = A[i][j].add((A[row][j].multiply(c)).negate());
                }
            row++;
        }
        for (int i = 0; i <= n; i++)
            A[i][n + 1] = A[i][n + 1].multiply(A[i][i].inverse());
        // compute answer from A
        int[] ans = new int[terminals.size() + 1];
        int denominator = 1;
        for (Integer terminal : terminals) {
            denominator = (int) Fraction.lca(denominator, A[where[terminal] + 1][n + 1].denominator);
        }
        ans[terminals.size()] = denominator;
        for (int i = 0; i < terminals.size(); i++) {
            Integer terminal = terminals.get(i);
            ans[i] = (int) (A[where[terminal] + 1][n + 1].numerator *
                    (denominator / A[where[terminal] + 1][n + 1].denominator));
        }
        return ans;
    }

    public static void main(String[] args) {
        solution(new int[][]{
                {0, 2, 1, 0, 0}, {0, 0, 0, 3, 4}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}
        });
        solution(new int[][]{
                {0, 1, 0, 0, 0, 1}, {4, 0, 0, 3, 2, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        });
    }

    private static class Fraction implements Comparable<Fraction> {
        long numerator, denominator;

        public Fraction(long a, long b) {
            long g = Math.abs(gcd(a, b));
            if (b < 0) {
                a = -a;
                b = -b;
            }
            numerator = a / g;
            denominator = b / g;
        }

        public Fraction(long x) {
            numerator = x;
            denominator = 1;
        }

        public Fraction() {
            numerator = 0;
            denominator = 1;
        }

        static long gcd(long a, long b) {
            while (b != 0) {
                long t = a;
                a = b;
                b = t % b;
            }
            return a;
        }

        static long lca(long a, long b) {
            return a / gcd(a, b) * b;
        }

        public void swap(Fraction x) {
            long tempNumerator = numerator;
            numerator = x.numerator;
            x.numerator = tempNumerator;
            long tempDenominator = denominator;
            denominator = x.denominator;
            x.denominator = tempDenominator;
        }

        public Fraction add(Fraction x) {
            long a = numerator * x.denominator + x.numerator * denominator;
            long b = denominator * x.denominator;
            return new Fraction(a, b);
        }

        public Fraction negate() {
            return new Fraction(-numerator, denominator);
        }

        public Fraction abs() {
            return new Fraction(Math.abs(numerator), Math.abs(denominator));
        }

        public Fraction multiply(Fraction x) {
            long a = numerator * x.numerator;
            long b = denominator * x.denominator;
            return new Fraction(a, b);
        }

        public Fraction inverse() {
            return new Fraction(denominator, numerator);
        }

        @Override
        public int compareTo(Fraction o) {
            return Long.compare(numerator * o.denominator, o.numerator * denominator);
        }

        @Override
        public String toString() {
            return numerator + "/" + denominator;
        }
    }
}
