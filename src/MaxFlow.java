import javax.swing.*;
import java.lang.*;
import java.util.LinkedList;

class MaxFlow {

    private int NBR_OF_VERTECES;
    private int[][] startGraph;
    private int xCount;
    private int yCount;
    private int[][] residualGraph;

    public void addGraph(int[][] startGraph) {
        this.startGraph = startGraph;
    }

    public String calculateMaxFlow() {
        String res = fordFulkerson(startGraph, 0, startGraph.length - 1);
        res += findMatches();
        return res;
    }



    public void setxCount(int xCount) {
        this.xCount = xCount;
    }

    public void setyCount(int yCount) {
        this.yCount = yCount;
    }

    private String fordFulkerson(int[][] startGraph, int sourceIndex, int sinkIndex) {

        int maxFlow = 0;
        this.NBR_OF_VERTECES = startGraph.length;

        //To keep track of the residual flow in the network
        residualGraph = new int[NBR_OF_VERTECES][NBR_OF_VERTECES];

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

        return "MaxFlow = " + maxFlow + "\n \n";
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

    private String findMatches() {

        int[][] miniInput = new int[xCount][yCount];
        int[][] miniResidual = new int[xCount][yCount];

        String res = "Matches: \n";

        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                miniInput[x][y] = startGraph[x + 1][xCount + 1 + y];
                miniResidual[x][y] = residualGraph[x + 1][xCount + 1 + y];
            }
        }

        System.out.println();
        System.out.println("Matches made:");
        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                if (miniInput[x][y] == 1 && miniResidual[x][y] == 0) {
                    res += "X" + (x + 1) + " -> " + "Y" + (y + 1) + "\n";
//                    System.out.println("X" + (x + 1) + " -> " + "Y" + (y + 1));
                }
            }
        }

        System.out.println("Start Graph");
        for (int x = 0; x < NBR_OF_VERTECES; x++) {
            for (int y = 0; y < NBR_OF_VERTECES; y++) {
                System.out.print(startGraph[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

        System.out.println("Residual Graph");
        for (int x = 0; x < NBR_OF_VERTECES; x++) {
            for (int y = 0; y < NBR_OF_VERTECES; y++) {
                System.out.print(residualGraph[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

        System.out.println("Mini Input");
        for (int x = 0; x < miniInput.length; x++) {
            for (int y = 0; y < miniInput[0].length; y++) {
                System.out.print(miniInput[x][y] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("mini Residual");
        for (int x = 0; x < miniResidual.length; x++) {
            for (int y = 0; y < miniResidual[0].length; y++) {
                System.out.print(miniResidual[x][y] + " ");
            }
            System.out.println();
        }


        return res;
    }

    public static void main(String[] args) throws java.lang.Exception {
        // Let us create a graph shown in the above example
        int graph[][] = new int[][]{
                //MaxFlow = 3, Mathes: 0->0, 1->1, 2->2
//                {0, 1, 1, 1, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 1, 0},
//                {0, 0, 0, 0, 1, 1, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0}

                //MaxFlow = 2 Matches 0->0, 2->1
//                {0, 1, 1, 1, 0, 0, 0},
//                {1, 0, 0, 0, 1, 1, 0},
//                {1, 0, 0, 0, 1, 0, 0},
//                {1, 0, 0, 0, 0, 1, 0},
//                {0, 1, 1, 0, 0, 0, 1},
//                {0, 1, 0, 1, 0, 0, 1},
//                {0, 0, 0, 0, 1, 1, 0}

                //MaxFlow = 2, Matches 0->1, 1 ->0
//                {0, 1, 1, 0, 0, 0, 0, 0},
//                {1, 0, 0, 0, 1, 1, 0, 0},
//                {1, 0, 0, 1, 0, 1, 1, 0},
//                {0, 0, 1, 0, 0, 0, 1, 1},
//                {0, 1, 1, 0, 0, 0, 0, 1},
//                {0, 1, 1, 0, 0, 0, 0, 1},
//                {0, 1, 1, 0, 0, 0, 0, 1},
//                {0, 0, 0, 1, 1, 1, 1, 0}

                {0, 1, 1, 0, 0, 0},
                {1, 0, 0, 1, 1, 0},
                {1, 0, 0, 1, 1, 0},
                {0, 1, 1, 0, 0, 1},
                {0, 1, 1, 0, 0, 1},
                {0, 0, 0, 1, 1, 0}
        };

        MaxFlow m = new MaxFlow();
        m.addGraph(graph);
        m.setxCount(Integer.parseInt(JOptionPane.showInputDialog("Enter the number of X vertices")));
        m.setyCount(Integer.parseInt(JOptionPane.showInputDialog("Enter the number of Y vertices")));
//        m.setxCount(2);
//        m.setyCount(2);

        JOptionPane.showMessageDialog(null, m.calculateMaxFlow());
    }
}