package algorithms.traverse;

import model.graph.Graph;

import java.util.List;

public class BreadthFirstTraversal<K>  extends ListBackedGraphTraversal<K> {
    public BreadthFirstTraversal(Graph<K> graph, K... initialNodes) {
        super(graph, initialNodes);
    }

    @Override
    int selectNextStep(List<K> nextSteps) {
        // For Breadth First Search, we want to explore the nodes which were first added to the queue, like a queue.
        // New nodes to visit are added to the end of "nextSteps", so we will instead select the elements at the
        // begining of the list (i.e. at the front of the line.)
        // This performs as a Stack and has First-In-First-Out (LIFO) behavior
        return nextSteps.size() - 1;
    }
}
