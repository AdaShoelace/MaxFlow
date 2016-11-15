/**
 * Created by kristoffer and Pierre on 11/7/16.
 */
public class BipartiteMatching {
    private int applicants, jobs;
    private int[][] startGraph;

    private void addGraph(int[][] input) {
        this.startGraph = input;
    }

    private void calculateMatching() {
        matching(startGraph);
    }

    private void matching(int[][] input) {
        this.applicants = input.length;
        this.jobs = input[0].length;

        int[] elligibleJobs = new int[jobs];
        for (int i = 0; i < elligibleJobs.length; i++) {
            elligibleJobs[i] = -1;
        }
        int result = 0;
        boolean jobsVisited[] = new boolean[jobs];

        for (int i = 0; i < applicants; i++) {
            //Clear the seen array
            for (int j = 0; j < jobsVisited.length; j++) {
                jobsVisited[j] = false;
            }

            if (hasMatch(input, i, jobsVisited, elligibleJobs))
                result++;
        }
        System.out.println(result);
    }

    private boolean hasMatch(int[][] startGraph, int applicant, boolean[] jobsVisited, int[] elligibleJobs) {

        for (int i = 0; i < elligibleJobs.length; i++) {

            if (startGraph[applicant][i] == 1 && jobsVisited[i] == false) {
                jobsVisited[i] = true; //visited this job

                if (elligibleJobs[i] < 0 || hasMatch(startGraph, elligibleJobs[i], jobsVisited, elligibleJobs)) {
                    elligibleJobs[i] = applicant;
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {

        int[][] input = new int[][]{
//                {0, 1, 1, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0},
//                {1, 0, 0, 1, 0, 0},
//                {0, 0, 1, 0, 0, 0},
//                {0, 0, 1, 1, 0, 0},
//                {0, 0, 0, 0, 0, 1}

                {1, 1},
                {1, 0},
                {0, 1},
        };

        BipartiteMatching bipartiteMatching = new BipartiteMatching();
        bipartiteMatching.addGraph(input);
        bipartiteMatching.calculateMatching();

    }
}