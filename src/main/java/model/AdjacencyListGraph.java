package model;

import model.edge.Edge;
import model.graph.Graph;
import model.node.Node;

import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyListGraph<N, E> implements Graph {
    private Map<Node, List<Edge>> nodesToEdges;
    private Map<String, Node> nodeKeyToNode;

    public AdjacencyListGraph() {
        nodesToEdges = new HashMap<>(); // Contains both incoming + outgoing connections
        nodeKeyToNode = new HashMap<>();
    }

    @Override
    public boolean contains(Node node) {
        return nodesToEdges.containsKey(node);
    }

    @Override
    public Node add(String nodeKey) {
        if (nodeKeyToNode.containsKey(nodeKey)) {
            throw new IllegalStateException("Node already exists");
        }

        Node node = Node.withNodeId(nodeKey);
        nodeKeyToNode.put(nodeKey, node);
        nodesToEdges.put(node, new ArrayList<>());
        return node;
    }

    @Override
    public Edge connect(Node source, Node destination) {
        Edge edge = new Edge(source, destination);
        nodesToEdges.putIfAbsent(source, new ArrayList<>());
        nodesToEdges.putIfAbsent(destination, new ArrayList<>());

        nodesToEdges.get(source).add(edge);
        nodesToEdges.get(destination).add(edge);

        return edge;
    }

    @Override
    public void remove(Node node) {
        if (!contains(node)) {
            throw new IllegalStateException("Node does not exist");
        }

        List<Edge> edges = nodesToEdges.remove(node);
        edges.forEach(edge -> disconnect(edge.source(), edge.destination()));
    }

    @Override
    public void disconnect(Node node1, Node node2) {
        Edge edge = edge(node1, node2);
        this.nodesToEdges.get(node1).remove(edge);
        this.nodesToEdges.get(node2).remove(edge);
    }

    @Override
    public List<Node> nodes() {
        return new ArrayList<>(nodesToEdges.keySet());
    }

    @Override
    public List<Node> nodes(String targetValue) {
        return new ArrayList<>(nodesToEdges.keySet());
    }

    @Override
    public Edge edge(Node node1, Node node2) {
        return nodesToEdges.get(node1).stream()
                .filter(e -> e.source().equals(node2) || e.destination().equals(node2))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Tried to get a non-existent edge"));
        // TODO: convert this API to lenient optional
    }

    @Override
    public List<Edge> edges() {
        return nodesToEdges.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Edge> edges(Node node) {
        return nodesToEdges.get(node);
    }
}
