package model;

import model.node.Node;

import java.util.Collection;

public class UndirectedAdjacencyListGraph extends AdjacencyListGraph {

    @Override
    public void connect(Node source, Node destination) {
        super.connect(source, destination);
        super.connect(destination, source);
    }

    @Override
    public Collection<Node> edges(Node source) {
        return this.outgoingConnections.get(source);
    }
}
