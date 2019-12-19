import java.util.Scanner;

public class TspDynamicRecursive {
    static int cost = 0;
    static int N = 200;
    static int[] completed = new int[N];
    static int[][] costMatrix = new int[N][N];
    static int nodes;


    // To create elements in my array instead of hard coding
    // Testing purposes
    public static void getInput () {
        int i, j;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of nodes: ");
        nodes = scan.nextInt();
        System.out.println("Enter the cost: ");
        for (i = 0; i < nodes; ++i) {
            System.out.println("Enter Elements of Row: " + i+1);
            for (j = 0; j < nodes; ++j) {
                costMatrix[i][j] = scan.nextInt();
            }
            completed[i]=0;
        }

        System.out.println("The cost list is: ");
        for (i = 0; i < nodes; ++i) {
            for (j = 0; j < nodes; ++j) {
                System.out.println(costMatrix[i][j]);
            }
        }
    }

    // Create my array
    public static void create () {
        int i, j;

        // Create a random number for the nodes
        nodes = (int)(5 * Math.random());
        System.out.println("Nodes: " + nodes);

        // Create a random value for the costMatrix at the
        // element positions
        for (i = 0; i < nodes; ++i) {
            for (j = 0; j < nodes; ++j) {
                costMatrix[i][j] = (int)(10 * Math.random());
                System.out.println("Matrix[" + i + "][" + j + "]: " + costMatrix[i][j]);
            }
        }
    }

    public static void minCost(int node) {
        int next;
        completed[node] = 1;

        System.out.print(node+1 + "  ");
        // Call least to find the smaller node
        next = least(node);
        if (next == 1000) {
            next = 0;
            System.out.println(next+1);
            cost += costMatrix[node][next];
            return;
        }
        minCost(next);
    }

    public static int least(int ln) {
        int i, next = 1000;
        int min = 1000;
        int kmin = 0;

        // Make sure least node is not 0 and the completed not 0
        // if the elements checked < the minimum get new min
        // and increment i
        for (i = 0; i < nodes; ++i) {
            if ((costMatrix[ln][i] != 0) && (completed[i] == 0))
                if (costMatrix[ln][i] + costMatrix[i][ln] < min) {
                    min = costMatrix[i][0] + costMatrix[ln][i];
                    kmin = costMatrix[ln][i];
                    next = i;
                }
        }
        if (min != 1000)
            cost += kmin;
        return next;
    }

    public static void main (String[] args) {
        //getInput();
        create();
        System.out.println("Path: ");
        minCost(0);
        System.out.println("Minimum cost: " + cost);
    }
}
