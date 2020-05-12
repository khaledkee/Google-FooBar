import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BunnyFlow {

    private final static int MAX = 2000000;

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        // Make a dummy start and a dummy end
        int start = path.length;
        int end = start + 1;
        int n = path.length + 2;
        int[][] capacityMatrix = new int[n][n];
        for (int i = 0; i < path.length; i++)
            for (int j = 0; j < path[i].length; j++)
                capacityMatrix[i][j] = path[i][j];
        // connect the start to all entrances
        for (int enterance : entrances)
            capacityMatrix[start][enterance] = MAX;
        // connect all exits to the end
        for (int exit : exits)
            capacityMatrix[exit][end] = MAX;
        // Compute max flow from start to end
        MaxFlow maxFlow = new MaxFlow(n, capacityMatrix);
        return maxFlow.maxflow(start, end);
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{0, 1}, new int[]{4, 5}, new int[][]{
                {0, 0, 4, 6, 0, 0}, {0, 0, 5, 2, 0, 0}, {0, 0, 0, 0, 4, 4},
                {0, 0, 0, 0, 6, 6}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}
        }));
    }

    // Implementing Edmonds-Karp algorithm for computing Max flow in network graphs
    // Based on: https://cp-algorithms.com/graph/edmonds_karp.html
    private static class MaxFlow {
        int n;
        int[][] capacityMatrix;

        public MaxFlow(int n, int[][] cap) {
            this.n = n;
            this.capacityMatrix = cap;
        }

        int bfs(int s, int t, int[] parent) {
            Arrays.fill(parent, -1);
            parent[s] = -2;
            Queue<int[]> q = new LinkedList<int[]>();
            q.add(new int[]{s, Integer.MAX_VALUE});

            while (!q.isEmpty()) {
                int cur = q.peek()[0];
                int flow = q.peek()[1];
                q.poll();

                for (int next = 0; next < n; next++)
                    if (parent[next] == -1 && capacityMatrix[cur][next] != 0) {
                        parent[next] = cur;
                        int new_flow = Math.min(flow, capacityMatrix[cur][next]);
                        if (next == t)
                            return new_flow;
                        q.add(new int[]{next, new_flow});
                    }
            }
            return 0;
        }

        int maxflow(int s, int t) {
            int flow = 0;
            int[] parent = new int[n];
            int new_flow;

            while ((new_flow = bfs(s, t, parent)) != 0) {
                flow += new_flow;
                int cur = t;
                while (cur != s) {
                    int prev = parent[cur];
                    capacityMatrix[prev][cur] -= new_flow;
                    capacityMatrix[cur][prev] += new_flow;
                    cur = prev;
                }
            }

            return flow;
        }
    }
}
