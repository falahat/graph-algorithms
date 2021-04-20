import model.DirectedAdjacencyListGraph;
import model.TestItemGenerator;

public class StringDALGenerator implements TestItemGenerator<String, DirectedAdjacencyListGraph<String>> {
    @Override
    public DirectedAdjacencyListGraph<String> generateGraph() {
        return new DirectedAdjacencyListGraph<>();
    }

    @Override
    public String generateNode(long seedValue) {
        return String.format("xyz_%d_abc", seedValue);
    }
}
