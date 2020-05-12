public class Laser {

    private static int n, m, distance;
    private static int[] your_position, guard_position;

    public static int solution(int[] dimensions, int[] your_position, int[] guard_position, int distance) {
        Laser.n = dimensions[0];
        Laser.m = dimensions[1];
        Laser.your_position = your_position;
        Laser.guard_position = guard_position;
        Laser.distance = distance;

        // consider vector (directions) of co-prime integer coordinates only
        // Note: 1 and 1 are co-prime, 0 and 1 are co-prime
        int result = 0;
        if (your_position[0] == guard_position[0] && Math.abs(your_position[1] - guard_position[1]) <= distance)
            result++;
        if (your_position[1] == guard_position[1] && Math.abs(your_position[0] - guard_position[0]) <= distance)
            result++;
        // use Farey sequence to generate co-prime pairs
        // based on: https://en.wikipedia.org/wiki/Coprime_integers#Generating_all_coprime_pairs
        result += findSolution(1, 1);
        result += findSolution(2, 1);
        result += findSolution(3, 1);
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(getSmallestEquationSolution(new long[]{2, 1},
//                new long[]{1, 2}, new long[]{5, 3}, new long[]{3, 2}));
        System.out.println(solution(new int[]{3, 2}, new int[]{1, 1}, new int[]{2, 1}, 4));
        System.out.println(solution(new int[]{300, 275}, new int[]{150, 150}, new int[]{185, 100}, 500));
        System.out.println(solution(new int[]{1280, 1280}, new int[]{150, 150}, new int[]{185, 100}, 10000));
    }

    private static boolean tryDirection(int dx, int dy) {
        int[] current = your_position.clone();
        int[] target = guard_position.clone();
        int r = dx * dx + dy * dy;

        // if direction is negative flip the plane
        if (dx < 0) {
            dx = -dx;
            current[0] = n - current[0];
            target[0] = n - target[0];
        }
        if (dy < 0) {
            dy = -dy;
            current[1] = m - current[1];
            target[1] = m - target[1];
        }
        int[] me = current.clone();
        // after two reflections the plane is flipped twice
        dx %= 2 * n;
        dy %= 2 * m;
        int moved = 0;
        // didn't hit the target and didn't move more than the allowed distance
        while ((current[0] != target[0] || current[1] != target[1])) {
            moved++;
            if (moved * moved * r > distance * distance) break;
            // move to the next integer-coordinate point
            current[0] += dx;
            current[1] += dy;

            // handle reflections
            if (current[0] >= 2 * n) current[0] -= 2 * n;
            if (current[1] >= 2 * m) current[1] -= 2 * m;
            if (current[0] >= n) {
                current[0] -= n;
                target[0] = n - target[0];
                me[0] = n - me[0];
            }
            if (current[1] >= m) {
                current[1] -= m;
                target[1] = m - target[1];
                me[1] = m - me[1];
            }
            // Hit me standing!
            if (current[0] == me[0] && current[1] == me[1]) break;
        }
        return current[0] == target[0] && current[1] == target[1];
    }

    private static int findSolution(int a, int b) {
        if (a * a + b * b > distance * distance) return 0;
        int result = 0;
        for (int f = 0; f < (a == b ? 1 : 2); f++) {
            int dx = (f == 0 ? a : b);
            int dy = (f == 1 ? a : b);
            if (tryDirection(dx, dy)) result++;
            if (tryDirection(-dx, dy)) result++;
            if (tryDirection(dx, -dy)) result++;
            if (tryDirection(-dx, -dy)) result++;
        }
        if (a > 1) {
            // use Farey sequence to generate co-prime pairs
            // based on: https://en.wikipedia.org/wiki/Coprime_integers#Generating_all_coprime_pairs
            if (2 * a - b <= distance) result += findSolution(2 * a - b, a);
            if (2 * a + b <= distance) result += findSolution(2 * a + b, a);
            if (a + 2 * b <= distance) result += findSolution(a + 2 * b, b);
        }
        return result;
    }
}
