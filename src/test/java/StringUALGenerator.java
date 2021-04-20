import model.GraphTestUtil;
import model.UndirectedAdjacencyListGraph;

public class StringUALGenerator extends GraphTestUtil<String, UndirectedAdjacencyListGraph<String>> {
    @Override
    public UndirectedAdjacencyListGraph<String> generateGraph() {
        return new UndirectedAdjacencyListGraph<>();
    }

    @Override
    public String generateNode(long seedValue) {
        return String.format("xyz_%d_abc", seedValue);
    }
}
