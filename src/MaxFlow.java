/**
 * Created by kristoffer on 11/10/16.
 */

import java.lang.*;
import java.util.LinkedList;

class MaxFlow {

    private int NBR_OF_VERTECES;
    private int[][] startGraph;

    public void addGraph(int[][] startGraph) {
        this.startGraph = startGraph;
    }

    public void calculateMaxFlow() {
        fordFulkerson(startGraph, 0, startGraph.length - 1);
    }

    private void fordFulkerson(int[][] startGraph, int sourceIndex, int sinkIndex) {

        int maxFlow = 0;
        this.NBR_OF_VERTECES = startGraph.length;

        //To keep track of the residual flow in the network
        int residualGraph[][] = new int[NBR_OF_VERTECES][NBR_OF_VERTECES];

        for (int i = 0; i < NBR_OF_VERTECES; i++) {
            for (int j = 0; j < NBR_OF_VERTECES; j++) {
                residualGraph[i][j] = startGraph[i][j];
            }
        }

        int parent[] = new int[NBR_OF_VERTECES];

        while (breadthFirstSearch(residualGraph, sourceIndex, sinkIndex, parent)) {

            //Find the residual capacity
            int pathFlow = Integer.MAX_VALUE;
            int temp;

            for (int i = sinkIndex; i != sourceIndex; i = parent[i]) {
                temp = parent[i];
                pathFlow = Math.min(pathFlow, residualGraph[temp][i]);
            }

            for (int i = sinkIndex; i != sourceIndex; i = parent[i]) {
                temp = parent[i];
                residualGraph[temp][i] -= pathFlow;
                residualGraph[i][temp] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        System.out.println("MaxFlow = " + maxFlow);
    }

    private boolean breadthFirstSearch(int[][] residualGraph, int sourceIndex, int sinktIndex, int[] parent) {

        boolean visitedVerteces[] = new boolean[NBR_OF_VERTECES];
        for (int i = 0; i < NBR_OF_VERTECES; i++)
            visitedVerteces[i] = false;

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(sourceIndex);
        visitedVerteces[sourceIndex] = true;
        parent[sourceIndex] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int i = 0; i < NBR_OF_VERTECES; i++) {
                if (visitedVerteces[i] == false && residualGraph[u][i] > 0) {
                    queue.add(i);
                    parent[i] = u;
                    visitedVerteces[i] = true;
                }
            }
        }

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visitedVerteces[sinktIndex] == true);
    }


    public static void main(String[] args) throws java.lang.Exception {
        // Let us create a graph shown in the above example
        int graph[][] = new int[][]{
                {0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        MaxFlow m = new MaxFlow();
        m.addGraph(graph);
        m.calculateMaxFlow();
    }
}
