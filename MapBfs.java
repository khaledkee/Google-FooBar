import java.util.LinkedList;
import java.util.Queue;

public class MapBfs {
    private static final Transition[] transitions = {
            new Transition(-1, 0),
            new Transition(1, 0),
            new Transition(0, -1),
            new Transition(0, 1)
    };

    public static int solution(int[][] map) {
        int h = map.length;
        int w = map[0].length;
        int removableWalls = 1;
        int[][][] statesDistance = new int[h][w][removableWalls + 1];
        boolean[][][] statesVisited = new boolean[h][w][removableWalls + 1];
        Queue<State> bfsQueue = new LinkedList<State>();
        bfsQueue.add(new State(0, 0, 0));
        statesVisited[0][0][0] = true;
        statesDistance[0][0][0] = 1;
        while (!bfsQueue.isEmpty()) {
            State currentState = bfsQueue.poll();
            int distance = statesDistance[currentState.row][currentState.col][currentState.removedWalls];
            for (Transition transition : transitions) {

                int row = currentState.row + transition.dx;
                if (row < 0 || row >= h) continue;

                int col = currentState.col + transition.dy;
                if (col < 0 || col >= w) continue;

                int removedWalls = currentState.removedWalls + map[row][col];
                if (removedWalls > removableWalls) continue;

                State nextState = new State(row, col, removedWalls);
                if (statesVisited[row][col][removedWalls]) continue;

                statesVisited[row][col][removedWalls] = true;
                statesDistance[row][col][removedWalls] = distance + 1;
                bfsQueue.add(nextState);
            }
        }
        int result = Integer.MAX_VALUE;
        for(int removedWalls = 0;removedWalls <= removableWalls;removedWalls++)
            if(statesVisited[h-1][w-1][removedWalls])
                result = Math.min(result, statesDistance[h-1][w-1][removedWalls]);

        return result;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0, 1, 1, 0}, {0, 0, 0, 1}, {1, 1, 0, 0}, {1, 1, 1, 0}}));
        System.out.println(solution(new int[][]{{0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}, {0, 1, 1,
            1, 1
            , 1}, {0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 0}}));
    }

    static class State {
        int row, col, removedWalls;

        public State(int r, int c, int rw) {
            row = r;
            col = c;
            removedWalls = rw;
        }
    }

    static class Transition {
        int dx, dy;

        public Transition(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}
