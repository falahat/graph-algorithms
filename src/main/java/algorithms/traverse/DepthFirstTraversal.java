package algorithms.traverse;

import model.graph.Graph;

import java.util.List;

public class DepthFirstTraversal<N extends Cloneable, E>  extends ListBackedGraphTraversal<N> {
    public DepthFirstTraversal(Graph<N> graph) {
        super(graph);
    }

    @Override
    int selectNextStep(List<TraversalStep> nextSteps) {
        // For Depth First Search, we want to explore the nodes which were most recently added to the queue, like a stack.
        // New nodes to visit are added to the end of "nextSteps", which is why we select the last element here.
        // This performs as a Stack and has Last-In-First-Out (LIFO) behavior
        return nextSteps.size() - 1;
    }
}
