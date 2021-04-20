package model;

import java.util.Collection;

public class DirectedAdjacencyListGraph<K> extends AdjacencyListGraph<K> {

    @Override
    public Collection<K> edges(K source) {
        return this.outgoingConnections.get(source);
    }
}
