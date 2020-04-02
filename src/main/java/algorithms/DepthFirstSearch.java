package algorithms;

import model.Edge;
import model.Graph;
import model.Node;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch<N extends Node, E extends Edge<N>>  extends GraphTraversal<N, E> {
    public DepthFirstSearch(Graph<N, E> graph) {
        super(graph);
    }

    @Override
    int selectIndexOfNextNode(List<N> nextNodes) {
        return nextNodes.size() - 1;
    }

    @Override
    List<N> selectFirstNodes() {
        return new ArrayList<>(graph.nodes());
    }

    @Override
    void visit(N node, Edge<N> edgeToNode) {
        // No-Op
    }

    @Override
    boolean canVisitNode(N node) {
        return true;
    }

    @Override
    boolean canVisitEdge(E edge) {
        return true;
    }
}
