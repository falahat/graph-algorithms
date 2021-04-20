package algorithms.traverse;

import model.graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ListBackedGraphTraversal<K> extends BaseGraphTraversal<K> {
    private final List<K> possibleSteps;

    public ListBackedGraphTraversal(Graph<K> graph) {
        super(graph);
        this.possibleSteps = new ArrayList<>();
    }

    @Override
    public void addPossibleTraversals(Collection<K> nextPossible) {
        possibleSteps.addAll(nextPossible); // Goes to end of list
    }

    @Override
    public Optional<K> selectAndRemoveNextCandidate() {
        if (possibleSteps.isEmpty()) {
            return Optional.empty();
        }
        // DFS and BFS can override this "selectNextStep" to either return the 1st element (bfs) or last (dfs)
        int indexOfResult = selectNextStep(possibleSteps);
        K nextStep = possibleSteps.remove(indexOfResult);
        return Optional.of(nextStep);
    }

    abstract int selectNextStep(List<K> nextSteps);
}
