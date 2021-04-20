import model.DirectedAdjacencyListGraph;
import model.TestItemGenerator;
import model.graph.EdgeTest;
import model.graph.NodeTest;

public class EdgeTest_String_DirectedAdjacencyList extends EdgeTest<String, DirectedAdjacencyListGraph<String>> {
    @Override
    public TestItemGenerator<String, DirectedAdjacencyListGraph<String>> itemGenerator() {
        return new StringDALGenerator();
    }
}