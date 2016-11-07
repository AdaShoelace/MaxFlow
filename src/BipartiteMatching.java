/**
 * Created by kristoffer and Pierre on 11/7/16.
 */
public class BipartiteMatching {


    public void matching(int[][] input) {

    }

    public static void main(String[] args) {

        int[][] input = new int[][]{
                {0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 1}
        };
        BipartiteMatching bipartiteMatching = new BipartiteMatching();
        bipartiteMatching.matching(input);

    }

}
