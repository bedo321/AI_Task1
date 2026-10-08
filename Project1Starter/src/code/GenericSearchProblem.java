package code;
import java.util.*;
public abstract class GenericSearchProblem<S> {
    public abstract S getInitialState();
    public abstract Boolean isGoal(S state);
    public abstract List<Transition<S>> getSuccessors(S state);
    public abstract int getHeuristic(S state);
    public abstract Object getCoreState(S state);
    public abstract boolean isDominatedBy(S newState, S oldState);
}

class Transition<S> {
    String action;
    S nextState;
    int stepCost;
    public Transition(String action, S nextState, int stepCost) {
        this.action = action;
        this.nextState = nextState;
        this.stepCost = stepCost;
    }
}

class SearchTreeNode<S> implements Comparable<SearchTreeNode<S>> {
    S state;
    SearchTreeNode<S> parent;
    String action;
    int pathCost;
    int heuristic;
    int depth;

    public SearchTreeNode(S state, SearchTreeNode<S> parent, String action, int pathCost, int heuristic, int depth) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
        this.heuristic = heuristic;
        this.depth = depth;
    }

    @Override
    public int compareTo(SearchTreeNode<S> other) {
        return Integer.compare(this.pathCost + this.heuristic, other.pathCost + other.heuristic);
    }
}

interface QingFun<S> {
    Collection<SearchTreeNode<S>> makeQ();
    SearchTreeNode<S> removeFirst(Collection<SearchTreeNode<S>> nodes);
    void enqueue(Collection<SearchTreeNode<S>> nodes, List<SearchTreeNode<S>> children);
}

class PriorityQingFun<S> implements QingFun<S> {
    @Override
    public Collection<SearchTreeNode<S>> makeQ() {
        return new PriorityQueue<>(); // Sorted based on pathCost + heuristic
    }

    @Override
    public SearchTreeNode<S> removeFirst(Collection<SearchTreeNode<S>> nodes) {
        return ((PriorityQueue<SearchTreeNode<S>>) nodes).poll();
    }

    @Override
    public void enqueue(Collection<SearchTreeNode<S>> nodes, List<SearchTreeNode<S>> children) {
        nodes.addAll(children);
    }
}

class LIFOQingFun<S> implements QingFun<S> {
    @Override
    public Collection<SearchTreeNode<S>> makeQ() {
        return new ArrayDeque<>();
    }

    @Override
    public SearchTreeNode<S> removeFirst(Collection<SearchTreeNode<S>> nodes) {
        return ((ArrayDeque<SearchTreeNode<S>>) nodes).removeFirst();
    }

    @Override
    public void enqueue(Collection<SearchTreeNode<S>> nodes, List<SearchTreeNode<S>> children) {
        ArrayDeque<SearchTreeNode<S>> stack = (ArrayDeque<SearchTreeNode<S>>) nodes;
        // Add to front (LIFO). Reverse iteration preserves the standard left-to-right expansion order
        for (int i = children.size() - 1; i >= 0; i--) {
            stack.addFirst(children.get(i));
        }
    }
}

class GenericSearch {
    public static int expandedNodes = 0;
    private static boolean reachedDepthLimit = false;

    // Strategy Router
    public static <S> SearchTreeNode<S> search(GenericSearchProblem<S> problem, String strategy) {
        expandedNodes = 0; // Reset count for a new solve() call

        if (strategy.equals("UC")) {
            return generalSearch(problem, new PriorityQingFun<>(), Integer.MAX_VALUE, false);
        } else if (strategy.equals("AS")) {
            return generalSearch(problem, new PriorityQingFun<>(), Integer.MAX_VALUE, true);
        } else if (strategy.equals("ID")) {
            // Iterative Deepening wraps the GENERAL-SEARCH, incrementing the depth limit each time
            int limit = 0;
            while (true) {
                reachedDepthLimit = false;
                SearchTreeNode<S> result = generalSearch(problem, new LIFOQingFun<>(), limit, false);
                if (result != null) return result;
                if (!reachedDepthLimit) break; // Exhausted state space without hitting limit -> no solution
                limit++;
            }
        }
        return null;
    }
    private static <S> SearchTreeNode<S> generalSearch(GenericSearchProblem<S> problem, QingFun<S> qingFun, int depthLimit, boolean useHeuristic) {

        // 2: nodes <- MAKE-Q(MAKE-NODE(INIT-STATE(problem)))
        Collection<SearchTreeNode<S>> nodes = qingFun.makeQ();
        int initialHeuristic = useHeuristic ? problem.getHeuristic(problem.getInitialState()) : 0;
        nodes.add(new SearchTreeNode<>(problem.getInitialState(), null, null, 0, initialHeuristic, 0));

        // State tracking to prevent infinite loops (Graph Search redundancy check)
        Map<Object, List<S>> closed = new HashMap<>();
        Map<S, Integer> visitedDepth = new HashMap<>(); // Strictly for ID Search

        // 4: while nodes is not empty do
        while (!nodes.isEmpty()) {

            // 5: node <- REMOVE-FIRST(nodes)
            SearchTreeNode<S> node = qingFun.removeFirst(nodes);

            // Redundancy/Visited Pruning (Required to not crash memory)
            if (qingFun instanceof LIFOQingFun) {
                // For ID Search: allow revisit if we found a shorter path (depth)
                if (visitedDepth.containsKey(node.state) && visitedDepth.get(node.state) <= node.depth) continue;
                visitedDepth.put(node.state, node.depth);
            } else {
                // For UC/AS: Pareto-domination check for resources
                Object core = problem.getCoreState(node.state);
                List<S> history = closed.computeIfAbsent(core, k -> new ArrayList<>());
                boolean dominated = false;
                for (S old : history) {
                    if (problem.isDominatedBy(node.state, old)) {
                        dominated = true;
                        break;
                    }
                }
                if (dominated) continue;
                history.add(node.state);
            }

            // 6: if GOAL-TEST(problem, STATE(node)) then
            if (problem.isGoal(node.state)) {
                return node; // 7: return node
            }

            // Enforce Depth Limit (for Iterative Deepening)
            if (node.depth >= depthLimit) {
                reachedDepthLimit = true;
                continue;
            }

            // 9: children <- EXPAND(node, OPERATORS(problem))
            expandedNodes++; // Track expansions as required by output
            List<SearchTreeNode<S>> children = new ArrayList<>();
            for (Transition<S> t : problem.getSuccessors(node.state)) {
                int h = useHeuristic ? problem.getHeuristic(t.nextState) : 0;
                children.add(new SearchTreeNode<>(t.nextState, node, t.action, node.pathCost + t.stepCost, h, node.depth + 1));
            }

            // 10: QING-FUN(nodes, children)
            qingFun.enqueue(nodes, children);

            // 11: end while
        }

        // 13: return failure
        return null;
    }
}
