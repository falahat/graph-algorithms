package model;

import model.graph.Graph;

import java.util.*;

public abstract class AdjacencyListGraph<K> implements Graph<K> {
    Map<K, Set<K>> outgoingConnections;

    public AdjacencyListGraph() {
        this.outgoingConnections = new HashMap<>();
    }

    @Override
    public void add(K node) {
        assertExists(node, false);
        this.outgoingConnections.put(node, new HashSet<>());
    }

    @Override
    public void remove(K node) {
        assertExists(node, true);
        this.outgoingConnections.remove(node);

        for (Set<K> destinations : this.outgoingConnections.values()) {
            destinations.remove(node); // TODO: be more efficient
        }
    }

    @Override
    public void connect(K source, K destination) {
        assertExists(source, true);
        assertExists(destination, true);

        outgoingConnections.get(source).add(destination);
    }

    @Override
    public void disconnect(K source, K destination) {
        assertExists(source, true);
        assertExists(destination, true);

        this.outgoingConnections.get(source).remove(destination);
    }

    @Override
    public Collection<K> nodes() {
        return this.outgoingConnections.keySet();
    }

    @Override
    public boolean contains(K node) {
        return outgoingConnections.containsKey(node);
    }

    @Override
    public boolean isConnected(K source, K destination) {
        return outgoingConnections.getOrDefault(source, Collections.emptySet()).contains(destination);
    }

    private void assertExists(K node, boolean isExpected) {
        boolean exists = contains(node);
        if (!isExpected && exists) {
            throw new IllegalStateException("Node must not exist");
        } else if (isExpected && !exists) {
            throw new IllegalStateException("Node must exist");
        }
    }
}
