package algorithms.traverse;

import model.graph.Graph;

import java.util.List;

public class DepthFirstTraversal<K> extends ListBackedGraphTraversal<K> {
    public DepthFirstTraversal(Graph<K> graph) {
        super(graph);
    }

    @Override
    int selectNextStep(List<K> nextSteps) {
        // For Depth First Search, we want to explore the nodes which were most recently added to the queue, like a stack.
        // New nodes to visit are added to the end of "nextSteps", which is why we select the last element here.
        // This performs as a Stack and has Last-In-First-Out (LIFO) behavior
        return nextSteps.size() - 1;
    }
}
