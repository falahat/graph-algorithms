package model;

import model.node.Node;

import java.util.Collection;

public class UndirectedAdjacencyListGraph<K> extends AdjacencyListGraph<K> {

    @Override
    public void connect(K source, K destination) {
        super.connect(source, destination);
        super.connect(destination, source);
    }

    @Override
    public Collection<K> edges(K source) {
        return this.outgoingConnections.get(source);
    }
}
