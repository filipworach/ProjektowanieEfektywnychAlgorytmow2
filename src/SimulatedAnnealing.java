import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    private double temperature = 10000d;
    private double differenceOfDistances = 0d;
    private double cooling = 0.99d;

    private final int maxTime = 360;


    private int distance = 0;

    private ArrayList actualPath = new ArrayList<Integer>();

    private ArrayList newPath = new ArrayList<Integer>();


    private Graph graph;
    public SimulatedAnnealing(Graph graph) {
        this.graph = graph;
    }

    public SimulatedAnnealing(double temperature, double differenceOfDistances, double cooling, double finalTemperature) {
        this.temperature = temperature;
        this.differenceOfDistances = differenceOfDistances;
        this.cooling = cooling;

    }

    public int run() {
        Random random = new Random();
        actualPath = graph.getFirstPath();
        distance = graph.calculateDistance(actualPath);
        ArrayList bestPath = new ArrayList<Integer>();
        bestPath = (ArrayList) actualPath.clone();
        int bestDistance = distance;
        long actualTime = 0;
        long start;
        while(actualTime <= maxTime) {
                start = System.nanoTime();
                newPath = Graph.swap((ArrayList<Integer>) actualPath.clone());
                differenceOfDistances = graph.calculateDistance(newPath) - graph.calculateDistance(actualPath);
                if (graph.calculateDistance(newPath) < graph.calculateDistance(actualPath)) {
                    actualPath = (ArrayList) newPath.clone();
                    if (graph.calculateDistance(newPath) < bestDistance) {
                        bestPath = (ArrayList) newPath.clone();
                        bestDistance = graph.calculateDistance(newPath);
                    }
                } else if (Math.exp(-differenceOfDistances / temperature) > random.nextDouble())
                    actualPath = (ArrayList) newPath.clone();
                actualTime += (System.nanoTime() - start)/1000000;
                if(actualTime > maxTime) break;
            temperature *= cooling;
        }
        for (Object path1 : bestPath) System.out.println(path1);
        return bestDistance;
    }

    public static void main(String[] args) throws FileNotFoundException {
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(Data.createDistanesMatrix("C:\\Users\\filip\\Downloads\\drive-download-20221210T204245Z-001\\ftv170.atsp"));
        System.out.println(simulatedAnnealing.run());

    }
}
