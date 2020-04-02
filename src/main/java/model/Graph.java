package model;

import java.util.List;
import java.util.stream.Collectors;

public interface Graph<N extends Node, E extends Edge<N>> {
    void add(N node);

    void remove(N node);

    void connect(N first, N second);

    void disconnect(N first, N second);

    List<N> nodes();

    List<E> edges();

    List<E> edges(Node n);

    default List<N> neighbors(N node) {
        return edges(node)
                .stream()
                .map(edge -> edge.other(node))
                .collect(Collectors.toList());
    }
}
