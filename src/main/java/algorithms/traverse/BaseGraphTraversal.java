package algorithms.traverse;

import model.graph.Graph;
import model.node.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseGraphTraversal implements GraphTraversal {
    private Graph graph;
    private Set<Node> visitedNodes;
    private boolean isInitialized;
    private List<Node> initialNodes;

    private Node currentNode;

    // TODO: investigate "Possible heap pollution from parameterized vararg type"
    public BaseGraphTraversal(Graph graph, Node... initialNodes) {
        this(graph, Arrays.asList(initialNodes));
    }

    public BaseGraphTraversal(Graph graph, List<Node> initialNodes) {
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
            Optional<Node> nextPossibleStepOpt = selectAndRemoveNextCandidate();
            if (nextPossibleStepOpt.isEmpty()) {
                break;
            }

            Node nextPossibleStep = nextPossibleStepOpt.get();
            if (isNotVisited(nextPossibleStep) && canVisit(nextPossibleStep)) {
                this.currentNode = nextPossibleStep;
                // This will be visited when we return the next node in #next()
            }
        }

        return this.currentNode != null;
    }

    @Override
    public final Node next() {
        if (hasNext()) {
            Node toReturn = this.currentNode;

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
    public void markAsVisited(Node visited) {
        this.visitedNodes.add(visited);
    }

    @Override
    public boolean isNotVisited(Node node) {
        return !visitedNodes.contains(node);
    }

    public abstract void addPossibleTraversals(Collection<Node> nextPossible);

    public abstract Optional<Node> selectAndRemoveNextCandidate(); // TODO: rename to popNextCandidate?

    private List<Node> getTraversalsFromInitialNodes() {
        return new ArrayList<>(initialNodes);
    }

    @Override
    public GraphTraversal copy() {
        try {
            return (GraphTraversal) this.getClass().getConstructors()[0].newInstance(this.graph);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            // TODO: do something
            return null;
        }
    }
}
