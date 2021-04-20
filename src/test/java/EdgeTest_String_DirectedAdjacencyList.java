import model.DirectedAdjacencyListGraph;
import model.GraphTestUtil;
import model.graph.EdgeTest;

public class EdgeTest_String_DirectedAdjacencyList extends EdgeTest {
    @Override
    public GraphTestUtil<String, DirectedAdjacencyListGraph<String>> testUtil() {
        return new StringDALGenerator();
    }
}
