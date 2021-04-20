package algorithms.traverse;

import model.graph.Graph;
import model.node.Node;

import java.util.List;

public class DepthFirstTraversal extends ListBackedGraphTraversal {
    public DepthFirstTraversal(Graph graph) {
        super(graph);
    }

    @Override
    int selectNextStep(List<Node> nextSteps) {
        // For Depth First Search, we want to explore the nodes which were most recently added to the queue, like a stack.
        // New nodes to visit are added to the end of "nextSteps", which is why we select the last element here.
        // This performs as a Stack and has Last-In-First-Out (LIFO) behavior
        return nextSteps.size() - 1;
    }
}
