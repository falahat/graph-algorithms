package model;

import model.graph.Graph;
import model.node.Node;

import java.util.*;

public abstract class AdjacencyListGraph implements Graph {
    Map<Node, Set<Node>> outgoingConnections;

    public AdjacencyListGraph() {
        this.outgoingConnections = new HashMap<>();
    }

    @Override
    public void add(Node node) {
        assertExists(node, false);
        this.outgoingConnections.put(node, new HashSet<>());
    }

    @Override
    public void remove(Node node) {
        assertExists(node, true);
        this.outgoingConnections.remove(node);

        for (Set<Node> destinations : this.outgoingConnections.values()) {
            destinations.remove(node); // TODO: be more efficient
        }
    }

    @Override
    public void connect(Node source, Node destination) {
        assertExists(source, true);
        assertExists(destination, true);

        outgoingConnections.get(source).add(destination);
    }

    @Override
    public void disconnect(Node source, Node destination) {
        assertExists(source, true);
        assertExists(destination, true);

        this.outgoingConnections.get(source).remove(destination);
    }

    @Override
    public Collection<Node> nodes() {
        return this.outgoingConnections.keySet();
    }

    @Override
    public boolean contains(Node node) {
        return outgoingConnections.containsKey(node);
    }

    @Override
    public boolean isConnected(Node source, Node destination) {
        return outgoingConnections.getOrDefault(source, Collections.emptySet()).contains(destination);
    }

    private void assertExists(Node node, boolean isExpected) {
        boolean exists = contains(node);
        if (!isExpected && exists) {
            throw new IllegalStateException("Adding node that already exists");
        } else if (isExpected && !exists) {
            throw new IllegalStateException("Removing node that does not exist");
        }
    }
}
