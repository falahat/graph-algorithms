import model.DirectedAdjacencyListGraph;
import model.TestItemGenerator;
import model.graph.NodeTest;

public class NodeTest_String_DirectedAdjacencyList extends NodeTest<String, DirectedAdjacencyListGraph<String>> {
    @Override
    public TestItemGenerator<String, DirectedAdjacencyListGraph<String>> itemGenerator() {
        return new StringDALGenerator(); // temp hack
    }
}
