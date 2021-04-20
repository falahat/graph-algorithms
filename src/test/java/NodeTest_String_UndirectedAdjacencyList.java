import model.GraphTestUtil;
import model.UndirectedAdjacencyListGraph;
import model.graph.NodeTest;

public class NodeTest_String_UndirectedAdjacencyList extends NodeTest {

    @Override
    public GraphTestUtil<String, UndirectedAdjacencyListGraph<String>> testUtil() {
        return new StringUALGenerator();
    }

}
