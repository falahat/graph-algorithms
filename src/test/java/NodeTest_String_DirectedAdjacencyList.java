import model.DirectedAdjacencyListGraph;
import model.GraphTestUtil;
import model.graph.NodeTest;

public class NodeTest_String_DirectedAdjacencyList extends NodeTest {
    @Override
    public GraphTestUtil<String, DirectedAdjacencyListGraph<String>> testUtil() {
        return new StringDALGenerator(); // temp hack
    }
}
