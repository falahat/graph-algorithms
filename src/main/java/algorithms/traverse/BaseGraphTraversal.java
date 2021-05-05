package algorithms.traverse;

import model.graph.Graph;

import java.util.*;

public abstract class BaseGraphTraversal<K> implements GraphTraversal<K> {
    private final Graph<K> graph;
    private final Set<K> visitedNodes;
    private final List<K> initialNodes;

    private boolean isInitialized;

    private K currentNode;

    // TODO: investigate "Possible heap pollution from parameterized vararg type"
    public BaseGraphTraversal(Graph<K> graph, K... initialNodes) {
        this(graph, Arrays.asList(initialNodes));
    }

    public BaseGraphTraversal(Graph<K> graph, List<K> initialNodes) {
        this.graph = graph;
        this.visitedNodes = new HashSet<>();
        this.isInitialized = false;
        this.initialNodes = initialNodes;
    }

    public void initialize() {
        pushCandidates(initialNodes);
        isInitialized = true;
    }

    @Override
    public final boolean hasNext() {
        // If we are beginning the search, do a one-time setup of some parameters.
        // We do not want to run this initialization unless all subclasses have also finished their constructors,
        // so we do not call initialize() in BaseGraphTraversal's contructor.
        if (!isInitialized) {
            initialize();
        }

        while (this.currentNode == null) {
            Optional<K> nextPossibleStepOpt = popNextCandidate();
            if (nextPossibleStepOpt.isEmpty()) {
                break;
            }

            K nextPossibleStep = nextPossibleStepOpt.get();
            if (!isVisited(nextPossibleStep) && canVisit(nextPossibleStep)) {
                this.currentNode = nextPossibleStep;
                // This will be visited when we return the next node in #next()
            }
        }

        return this.currentNode != null;
    }

    @Override
    public final K next() {
        if (hasNext()) {
            K toReturn = this.currentNode;

            // Mark this node as visited and call hooks
            markVisited(this.currentNode);
            onVisit(this.currentNode);

            // Calculate + insert next possible steps to traverse
            pushCandidates(graph.edges(currentNode));

            this.currentNode = null; // to avoid returning the same
            return toReturn;
        }

        throw new NoSuchElementException("Graph is fully traversed");
    }

    @Override
    public void markVisited(K visited) {
        this.visitedNodes.add(visited);
    }

    @Override
    public boolean isVisited(K node) {
        return visitedNodes.contains(node);
    }

    public abstract void pushCandidates(Collection<K> nextPossible);

    public abstract Optional<K> popNextCandidate(); // TODO: rename to popNextCandidate?

}
