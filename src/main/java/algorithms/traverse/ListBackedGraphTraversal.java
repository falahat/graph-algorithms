package algorithms.traverse;

import model.graph.Graph;
import model.node.Node;

import java.util.*;

public abstract class ListBackedGraphTraversal extends BaseGraphTraversal {
    private final List<Node> possibleSteps;

    public ListBackedGraphTraversal(Graph graph) {
        super(graph);
        this.possibleSteps = new ArrayList<>();
    }

    @Override
    public void addPossibleTraversals(Collection<Node> nextPossible) {
        possibleSteps.addAll(nextPossible); // Goes to end of list
    }

    @Override
    public Optional<Node> selectAndRemoveNextCandidate() {
        if (possibleSteps.isEmpty()) {
            return Optional.empty();
        }
        // DFS and BFS can override this "selectNextStep" to either return the 1st element (bfs) or last (dfs)
        int indexOfResult = selectNextStep(possibleSteps);
        Node nextStep = possibleSteps.remove(indexOfResult);
        return Optional.of(nextStep);
    }

    abstract int selectNextStep(List<Node> nextSteps);
}
