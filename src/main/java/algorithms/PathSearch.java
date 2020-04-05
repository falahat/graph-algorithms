package algorithms;

import model.Edge;
import model.Graph;
import model.LabeledGraph;
import model.Node;

import java.util.*;
import java.util.function.Predicate;

public class PathSearch<N extends Node, E extends Edge<N>> extends BreadthFirstTraversal<N, E> {

    private final N startNode;
    private final Predicate<TraversalStep<N, E>> required;

    public PathSearch(Graph<N, E> graph, N start, Predicate<TraversalStep<N, E>> required) {
        super(graph);
        this.startNode = start;
        this.required = required;


    }

    public List<TraversalStep<N, E>> search() {
        BreadthFirstTraversal<N, E> bfs = new BreadthFirstTraversal<>(graph);
        StepLabeledGraph<N, E> pathToSourceGraph = new StepLabeledGraph<>(graph);

        List<TraversalStep<N, E>> stepsToDestination = findFirstMatchingStep(bfs, pathToSourceGraph)
                .map(destination -> getReversePathToSource(pathToSourceGraph, destination))
                .orElse(Collections.emptyList());

        if (!stepsToDestination.isEmpty() && stepsToDestination.get(0).node().equals(startNode)) {
            return stepsToDestination;
        }

        return Collections.emptyList();
    }

    private Optional<TraversalStep<N, E>> findFirstMatchingStep(BreadthFirstTraversal<N, E> bfs, StepLabeledGraph<N, E> pathToSource) {
        TraversalStep<N, E> destination = null;

        for (TraversalStep<N, E> step : bfs) {
            pathToSource.setLabel(step.node(), step);

            // TODO: Create a Path class?
            // Destination found, now retrace steps back to source
            if (this.required.test(step)) {
                destination = step;
                break;
            }
        }
        return Optional.ofNullable(destination);
    }

    private List<TraversalStep<N, E>> getReversePathToSource(StepLabeledGraph<N, E> pathToSource,
                                                             TraversalStep<N, E> destination) {
        Set<N> visitedNodes = new HashSet<>();
        TraversalStep<N, E> currentStep = destination;
        List<TraversalStep<N, E>> stepsToDestination = new Stack<>();
        stepsToDestination.add(currentStep);
        while (currentStep != null // This should be impossible unless the search algorithm is broken
                && !currentStep.node().equals(startNode) // We reached the source and have a full path from source to dest.
                && !visitedNodes.contains(currentStep.node()) // We hit a cycle for some reason. Exit now. If one of the nodes in the cycle was the source, we would have already exited.
                && currentStep.edgeToNode().isPresent()) { // If the current step has no edge to it, we have no way of seeing edges behind it. In this case, it must be source or we do not have a path

            stepsToDestination.add(currentStep);
            visitedNodes.add(currentStep.node());
            N previousNode = currentStep.edgeToNode().get().other(currentStep.node());
            currentStep = pathToSource.getLabel(previousNode).orElse(null);
        }
        return stepsToDestination;
    }

    @Override
    public List<N> selectInitialNodes() {
        return Collections.singletonList(startNode);
    }

    @Override
    public void onVisit(TraversalStep<N, E> step) {

    }

    public static class StepLabeledGraph<N extends Node, E extends Edge<N>> extends LabeledGraph<N, E, TraversalStep<N, E>> {
        public StepLabeledGraph(Graph<N, E> innerGraph) {
            super(innerGraph);
        }
    }
}
