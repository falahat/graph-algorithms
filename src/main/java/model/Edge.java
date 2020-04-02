package model;

public interface Edge<N extends Node> {
    N node1();

    N node2();

    default N other(Node node) {
        if (node.equals(node1())) {
            return node2();
        } else if (node.equals(node2())) {
            return node1();
        } else {
            throw new IllegalArgumentException("Node does not exist on this edge");
        }
    }
}
