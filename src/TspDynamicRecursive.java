import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Scanner;

public class TspDynamicRecursive {
    static ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
    static String ResultsFolderPath = "C:\\Users\\msvte\\Desktop\\Test"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    static int numberOfTrials = 3;
    static int cost = 0;
    static int N = 200;
    static int[] completed = new int[N];
    static int[][] costMatrix = new int[N][N];
    static int nodes = 256;


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
        //nodes = (int)(5 * Math.random());
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

    static void program(String resultsFileName) {

        // To open a file to write to
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return;
        }
        ThreadCpuStopWatch stopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials

        long elapsedTime = 0;
        System.gc();
        stopwatch.start(); // Start timer in nano secs

        create();
        System.out.println("Path: ");
        minCost(0);
        System.out.println("Minimum cost: " + cost);

        resultsWriter.printf("Minimum cost: \n", cost);

        elapsedTime = stopwatch.elapsedTime();

        System.out.println("Time: " + elapsedTime);

        // Output for testing

        resultsWriter.println("#Path     Node    AvgTime"); // # marks a comment in gnuplot data
        resultsWriter.flush();



        resultsWriter.printf("%-5d %20d \n", nodes, elapsedTime);
        resultsWriter.flush();
    }

    public static void main (String[] args) {
        System.out.println("X    Time");

        // getInput();
        // create();
        program("lab8.txt");

    }
}
