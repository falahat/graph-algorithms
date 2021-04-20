import model.TestItemGenerator;
import model.UndirectedAdjacencyListGraph;
import model.graph.NodeTest;

public class NodeTest_String_UndirectedAdjacencyList extends NodeTest<String, UndirectedAdjacencyListGraph<String>> {

    @Override
    public TestItemGenerator<String, UndirectedAdjacencyListGraph<String>> itemGenerator() {
        return new StringUALGenerator();
    }

}
