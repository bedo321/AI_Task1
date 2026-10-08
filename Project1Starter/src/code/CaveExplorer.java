package code;

import java.util.*;

class CaveState {
    public int x;
    public int y;
    public int energy;
    public int rope;
    public int lives;
    public boolean hasKey;
    public boolean[] keysCollected;
    public boolean[] doorsOpen;

    public CaveState(int x, int y, int energy, int rope, int lives, boolean hasKey, boolean[] keysCollected, boolean[] doorsOpen) {
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.rope = rope;
        this.lives = lives;
        this.hasKey = hasKey;
        this.keysCollected = keysCollected.clone();
        this.doorsOpen = doorsOpen.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CaveState other = (CaveState) obj;
        return x == other.x && y == other.y && energy == other.energy &&
                rope == other.rope && lives == other.lives && hasKey == other.hasKey &&
                Arrays.equals(keysCollected, other.keysCollected) &&
                Arrays.equals(doorsOpen, other.doorsOpen);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(x, y, energy, rope, lives, hasKey);
        result = 31 * result + Arrays.hashCode(keysCollected);
        result = 31 * result + Arrays.hashCode(doorsOpen);
        return result;
    }
}

class CoreState {
    int x, y;
    boolean hasKey;
    boolean[] keysCollected;
    boolean[] doorsOpen;

    public CoreState(int x, int y, boolean hasKey, boolean[] keysCollected, boolean[] doorsOpen) {
        this.x = x;
        this.y = y;
        this.hasKey = hasKey;
        this.keysCollected = keysCollected.clone();
        this.doorsOpen = doorsOpen.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CoreState)) return false;
        CoreState o = (CoreState) obj;
        return x == o.x && y == o.y && hasKey == o.hasKey &&
                Arrays.equals(keysCollected, o.keysCollected) &&
                Arrays.equals(doorsOpen, o.doorsOpen);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(x, y, hasKey);
        result = 31 * result + Arrays.hashCode(keysCollected);
        result = 31 * result + Arrays.hashCode(doorsOpen);
        return result;
    }
}

class Point {
    int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class CaveExplorer extends GenericSearchProblem<CaveState> {
    private int rows, cols;
    private int[][] grid;
    private List<Point> doors = new ArrayList<>();
    private List<Point> keys = new ArrayList<>();
    private CaveState initialState;
    private int minE = Integer.MAX_VALUE;

    public CaveExplorer(String initString) {
        String[] parts = initString.split(";", -1);
        String[] hw = parts[0].split(",");
        rows = Integer.parseInt(hw[0]);
        cols = Integer.parseInt(hw[1]);

        String[] xy = parts[1].split(",");
        int startX = Integer.parseInt(xy[0]);
        int startY = Integer.parseInt(xy[1]);

        String[] enRl = parts[2].split(",");
        int startEnergy = Integer.parseInt(enRl[0]);
        int startRope = Integer.parseInt(enRl[1]);

        grid = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            String[] rowVals = parts[3 + r].split(",");
            for (int c = 0; c < cols; c++) {
                grid[r][c] = Integer.parseInt(rowVals[c]);
                if (grid[r][c] > 0 && grid[r][c] < minE) {
                    minE = grid[r][c];
                }
            }
        }
        if (minE == Integer.MAX_VALUE) minE = 1;

        String doorsStr = parts[3 + rows];
        if (!doorsStr.isEmpty()) {
            String[] dCoords = doorsStr.split(",");
            for (int i = 0; i < dCoords.length; i += 2) {
                doors.add(new Point(Integer.parseInt(dCoords[i]), Integer.parseInt(dCoords[i+1])));
            }
        }

        String keysStr = parts[3 + rows + 1];
        if (!keysStr.isEmpty()) {
            String[] kCoords = keysStr.split(",");
            for (int i = 0; i < kCoords.length; i += 2) {
                keys.add(new Point(Integer.parseInt(kCoords[i]), Integer.parseInt(kCoords[i+1])));
            }
        }

        initialState = new CaveState(startX, startY, startEnergy, startRope, 3, false,
                new boolean[keys.size()], new boolean[doors.size()]);
    }

    public static String solve(String initString, String strategy) {
        CaveExplorer problem = new CaveExplorer(initString);
        SearchTreeNode<CaveState> resultNode = GenericSearch.search(problem, strategy);

        if (resultNode == null) return "No Solution";

        List<String> path = new ArrayList<>();
        SearchTreeNode<CaveState> curr = resultNode;

        while (curr.parent != null) {
            path.add(curr.action);
            curr = curr.parent;
        }
        Collections.reverse(path);

        String plan = String.join(",", path);
        int usedLives = 3 - resultNode.state.lives;
        int usedEnergy = problem.initialState.energy - resultNode.state.energy;

        return String.format("%s;%d;%d;%d", plan, usedLives, usedEnergy, GenericSearch.expandedNodes);
    }

    @Override
    public CaveState getInitialState() {
        return initialState;
    }

    @Override
    public Boolean isGoal(CaveState s) {
        for (boolean open : s.doorsOpen) {
            if (!open) return false;
        }
        return s.lives >= 1;
    }

    @Override
    public Object getCoreState(CaveState s) {
        return new CoreState(s.x, s.y, s.hasKey, s.keysCollected, s.doorsOpen);
    }

    @Override
    public boolean isDominatedBy(CaveState newState, CaveState oldState) {
        return oldState.energy >= newState.energy &&
                oldState.rope >= newState.rope &&
                oldState.lives >= newState.lives;
    }

    /*
     * HEURISTIC EXPLANATION:
     * This heuristic is strictly admissible (never overestimates) and integrates energy & lives.
     * 1. Energy factor: Uses Manhattan distance to the closest objective, multiplied by `minE`
     *    (the absolute cheapest traversable cell in the entire grid).
     * 2. Life factor: Evaluates if vertical drops (dy) exceed the current rope length. Forced
     *    jumpdowns incur a massive penalty matching the uniform cost weight for lives.
     */
    @Override
    public int getHeuristic(CaveState s) {
        if (isGoal(s)) return 0;

        int h = Integer.MAX_VALUE;

        if (s.hasKey) {
            for (int i = 0; i < doors.size(); i++) {
                if (!s.doorsOpen[i]) {
                    h = Math.min(h, getCostBound(s.x, s.y, s.rope, doors.get(i)));
                }
            }
        } else {
            for (int j = 0; j < keys.size(); j++) {
                if (!s.keysCollected[j]) {
                    int costToKey = getCostBound(s.x, s.y, s.rope, keys.get(j));
                    int minKeyToDoor = Integer.MAX_VALUE;

                    for (int i = 0; i < doors.size(); i++) {
                        if (!s.doorsOpen[i]) {
                            Point d = doors.get(i);
                            int dist = Math.abs(keys.get(j).x - d.x) + Math.abs(keys.get(j).y - d.y);
                            minKeyToDoor = Math.min(minKeyToDoor, dist * minE);
                        }
                    }
                    if (minKeyToDoor != Integer.MAX_VALUE) {
                        h = Math.min(h, costToKey + minKeyToDoor);
                    }
                }
            }
        }
        return h == Integer.MAX_VALUE ? 0 : h;
    }

    private int getCostBound(int x, int y, int rope, Point target) {
        int dist = Math.abs(x - target.x) + Math.abs(y - target.y);
        int dy = Math.max(0, target.y - y);
        int requiredLives = Math.max(0, dy - rope);
        return (dist * minE) + (requiredLives * 10000);
    }

    @Override
    public List<Transition<CaveState>> getSuccessors(CaveState s) {
        List<Transition<CaveState>> successors = new ArrayList<>();
        String[] actions = {"left", "right", "climbup", "climbdown", "jumpdown", "collect", "unlock"};

        for (String a : actions) {
            CaveState next = applyAction(s, a);
            if (next != null) {
                // Heavily penalize life loss (10000) to ensure we satisfy the requirement:
                // "less lives used is better even if energy used is higher"
                int lifeCost = (s.lives - next.lives) * 10000;
                int energyCost = s.energy - next.energy;
                successors.add(new Transition<>(a, next, lifeCost + energyCost));
            }
        }
        return successors;
    }

    private CaveState applyAction(CaveState s, String action) {
        int nx, ny;
        switch (action) {
            case "left":
                nx = s.x - 1; ny = s.y;
                if (nx >= 0 && grid[ny][nx] != 0 && s.energy >= grid[ny][nx])
                    return new CaveState(nx, ny, s.energy - grid[ny][nx], s.rope, s.lives, s.hasKey, s.keysCollected, s.doorsOpen);
                break;
            case "right":
                nx = s.x + 1; ny = s.y;
                if (nx < cols && grid[ny][nx] != 0 && s.energy >= grid[ny][nx])
                    return new CaveState(nx, ny, s.energy - grid[ny][nx], s.rope, s.lives, s.hasKey, s.keysCollected, s.doorsOpen);
                break;
            case "climbup":
                nx = s.x; ny = s.y - 1;
                if (ny >= 0 && grid[ny][nx] != 0 && s.energy >= grid[ny][nx] && s.rope >= 1)
                    return new CaveState(nx, ny, s.energy - grid[ny][nx], s.rope - 1, s.lives, s.hasKey, s.keysCollected, s.doorsOpen);
                break;
            case "climbdown":
                nx = s.x; ny = s.y + 1;
                if (ny < rows && grid[ny][nx] != 0 && s.energy >= grid[ny][nx] && s.rope >= 1)
                    return new CaveState(nx, ny, s.energy - grid[ny][nx], s.rope - 1, s.lives, s.hasKey, s.keysCollected, s.doorsOpen);
                break;
            case "jumpdown":
                nx = s.x; ny = s.y + 1;
                if (ny < rows && grid[ny][nx] != 0 && s.energy >= grid[ny][nx] && s.lives > 1)
                    return new CaveState(nx, ny, s.energy - grid[ny][nx], s.rope, s.lives - 1, s.hasKey, s.keysCollected, s.doorsOpen);
                break;
            case "collect":
                if (!s.hasKey) {
                    for (int i = 0; i < keys.size(); i++) {
                        if (keys.get(i).x == s.x && keys.get(i).y == s.y && !s.keysCollected[i]) {
                            CaveState next = new CaveState(s.x, s.y, s.energy, s.rope, s.lives, true, s.keysCollected, s.doorsOpen);
                            next.keysCollected[i] = true;
                            return next;
                        }
                    }
                }
                break;
            case "unlock":
                if (s.hasKey) {
                    for (int i = 0; i < doors.size(); i++) {
                        if (doors.get(i).x == s.x && doors.get(i).y == s.y && !s.doorsOpen[i]) {
                            CaveState next = new CaveState(s.x, s.y, s.energy, s.rope, s.lives, false, s.keysCollected, s.doorsOpen);
                            next.doorsOpen[i] = true;
                            return next;
                        }
                    }
                }
                break;
        }
        return null;
    }
}