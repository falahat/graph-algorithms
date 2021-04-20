package model;

import model.node.Node;

import java.util.Collection;

public class DirectedAdjacencyListGraph extends AdjacencyListGraph {

    @Override
    public Collection<Node> edges(Node source) {
        return this.outgoingConnections.get(source);
    }
}
