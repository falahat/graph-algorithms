import model.TestItemGenerator;
import model.UndirectedAdjacencyListGraph;

public class StringUALGenerator implements TestItemGenerator<String, UndirectedAdjacencyListGraph<String>> {
    @Override
    public UndirectedAdjacencyListGraph<String> generateGraph() {
        return new UndirectedAdjacencyListGraph<>();
    }

    @Override
    public String generateNode(long seedValue) {
        return String.format("xyz_%d_abc", seedValue);
    }
}
