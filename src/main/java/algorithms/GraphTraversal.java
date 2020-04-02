package algorithms;

import model.Edge;
import model.Graph;
import model.Node;

import java.util.*;
import java.util.stream.Collectors;

public abstract class GraphTraversal<N extends Node, E extends Edge<N>> implements Iterator<N> {
    Graph<N, E> graph;
    private Set<N> visited;
    private List<E> edgesToVisit;
    private List<N> nodesToVisit;

    private N previousNode;
    private N currentNode;
    private E currentEdge;

    public GraphTraversal(Graph graph) {
        this.graph = graph;
        this.visited = new HashSet<>();
        this.nodesToVisit = selectFirstNodes();
    }

    @Override
    public final boolean hasNext() {

        while (this.currentNode == null && (!edgesToVisit.isEmpty() || !nodesToVisit.isEmpty())) {

            boolean hasEdgesToVisit = edgesToVisit.isEmpty();
            List<N> possibleNodes = hasEdgesToVisit ? getNextPossibleFromEdges() : nodesToVisit;
            int indexOfNext = selectIndexOfNextNode(possibleNodes);
            N nextNode = possibleNodes.remove(indexOfNext);
            E nextEdge = hasEdgesToVisit ? edgesToVisit.remove(indexOfNext) : null;

            if (!isVisited(nextNode)) {
                this.currentNode = nextNode;
                this.currentEdge = nextEdge;
                // This will be visited when we return the next node in #next()
            }
        }

        return this.currentNode != null;
    }

    private List<N> getNextPossibleFromEdges() {
        return edgesToVisit
                .stream()
                .map(e -> previousNode == null ? e.node1() : e.other(previousNode))
                .collect(Collectors.toList());
    }

    @Override
    public final N next() {
        if (hasNext()) {
            N toReturn = this.currentNode;

            visit(this.currentNode, this.currentEdge);
            markAsVisited(this.currentNode);

            edgesToVisit.addAll(graph.edges(currentNode));

            this.previousNode = this.currentNode;
            this.currentNode = null;
            this.currentEdge = null;

            return toReturn;
        }

        throw new NoSuchElementException("No More Nodes to Traverse");
    }

    public final void markAsVisited(N node) {
        visited.add(node);
    }

    public final boolean isVisited(N node) {
        return visited.contains(node);
    }


    abstract int selectIndexOfNextNode(List<N> nextNodes);

    abstract List<N> selectFirstNodes();

    abstract void visit(N node, Edge<N> edgeToNode);
}
