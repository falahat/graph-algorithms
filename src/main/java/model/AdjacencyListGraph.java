package model;

import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyListGraph<N extends Node, E extends Edge<N>> implements Graph<N, E> {
    private Map<N, List<E>> nodesToEdges;

    public AdjacencyListGraph() {
        nodesToEdges = new HashMap<>(); // Contains both incoming + outgoing connections
    }

    @Override
    public boolean contains(N node) {
        return nodesToEdges.containsKey(node);
    }

    @Override
    public void add(N node) {
        if (contains(node)) {
            throw new IllegalStateException("Node already exists");
        }
        nodesToEdges.put(node, new ArrayList<>());
    }

    @Override
    public void add(E edge) {
        nodesToEdges.putIfAbsent(edge.node1(), new ArrayList<>());
        nodesToEdges.putIfAbsent(edge.node2(), new ArrayList<>());

        nodesToEdges.get(edge.node1()).add(edge);
        nodesToEdges.get(edge.node2()).add(edge);
    }

    @Override
    public void remove(N node) {
        if (!contains(node)) {
            throw new IllegalStateException("Node does not exist");
        }
        List<E> edges = nodesToEdges.remove(node);
        edges.forEach(this::remove);
    }

    @Override
    public void remove(E edge) {
        if (!nodesToEdges.get(edge.node1()).remove(edge) || !nodesToEdges.get(edge.node2()).remove(edge)) {
            throw new IllegalStateException("Edge does not exist for at least one of the nodes");
        }
    }

    @Override
    public Collection<N> nodes() {
        return nodesToEdges.keySet();
    }

    @Override
    public Collection<E> edges() {
        return nodesToEdges.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<E> edges(N node) {
        return nodesToEdges.get(node);
    }
}
