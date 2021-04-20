package algorithms.traverse;

import model.graph.Graph;
import model.node.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class BaseGraphTraversal<K> implements GraphTraversal<K> {
    private final Graph<K> graph;
    private final Set<K> visitedNodes;
    private final boolean isInitialized;
    private final List<K> initialNodes;

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
        addPossibleTraversals(initialNodes);
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
            Optional<K> nextPossibleStepOpt = selectAndRemoveNextCandidate();
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

            // Mark this node as visted and call hooks
            markAsVisited(this.currentNode);
            onVisit(this.currentNode);

            // Calculate + insert next possible steps to traverse
            addPossibleTraversals(graph.edges(currentNode));

            return toReturn;
        }

        throw new NoSuchElementException("Graph is fully traversed");
    }

    @Override
    public void markAsVisited(K visited) {
        this.visitedNodes.add(visited);
    }

    @Override
    public boolean isVisited(K node) {
        return !visitedNodes.contains(node);
    }

    public abstract void addPossibleTraversals(Collection<K> nextPossible);

    public abstract Optional<K> selectAndRemoveNextCandidate(); // TODO: rename to popNextCandidate?

    private List<K> getTraversalsFromInitialNodes() {
        return new ArrayList<>(initialNodes);
    }

    @Override
    public GraphTraversal<K> copy() {
        try {
            return (GraphTraversal<K>) this.getClass().getConstructors()[0].newInstance(this.graph);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            // TODO: do something
            return null;
        }
    }
}
