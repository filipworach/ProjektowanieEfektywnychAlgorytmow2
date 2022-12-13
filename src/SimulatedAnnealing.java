import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SimulatedAnnealing {
    private double temperature = -1d;
    private double differenceOfDistances = 0d;
    private double cooling = 0.99d;

    private int maxTime = 30000;


    private int distance = 0;

    private ArrayList actualPath = new ArrayList<Integer>();

    private ArrayList newPath = new ArrayList<Integer>();


    private Graph graph;


    public SimulatedAnnealing() {

    }


    public int run() {
        Random random = new Random();
        actualPath = graph.getFirstPath();
        distance = graph.calculateDistance(actualPath);
        int howManyVertices = graph.getHowManyCities();
        double timeWhenFoundTheBestSolution = 0;
        ArrayList bestPath;
        bestPath = (ArrayList) actualPath.clone();
        int bestDistance = distance;
        long actualTime = 0;
        long start;

        if(temperature == -1d) temperature = bestDistance * 100;
        double temperatureWhenFound = temperature;
        while(actualTime <= maxTime) {
            for (int i = 0; i < howManyVertices; i++) {
                start = System.currentTimeMillis();
                newPath = Graph.swap((ArrayList<Integer>) actualPath.clone());
                differenceOfDistances = graph.calculateDistance(newPath) - graph.calculateDistance(actualPath);
                if (graph.calculateDistance(newPath) < graph.calculateDistance(actualPath)) {
                    actualPath = (ArrayList) newPath.clone();
                    if (graph.calculateDistance(newPath) < bestDistance) {
                        bestPath = (ArrayList) newPath.clone();
                        bestDistance = graph.calculateDistance(newPath);
                        timeWhenFoundTheBestSolution = actualTime + System.currentTimeMillis() - start;
                        temperatureWhenFound = temperature;
                    }
                } else if (Math.exp(-differenceOfDistances / temperature) > random.nextDouble())
                    actualPath = (ArrayList) newPath.clone();
                actualTime += System.currentTimeMillis() - start;
                if (actualTime > maxTime) break;
            }
            temperature *= cooling;
        }
        Graph.printPath(bestPath);
        System.out.println("Czas, w którym znaleziono najlepszą ścieżkę: " + timeWhenFoundTheBestSolution/1000.0d + "s");
        System.out.println("Temperatura w tamtym momencie: " + temperatureWhenFound);
        return bestDistance;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime * 1000;
    }

    public void setCooling(double cooling) {
        this.cooling = cooling;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String string = "-1";
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
        Graph graph = new Graph();
        while (!string.equals("8")) {
            System.out.println("1. Wczytaj plik ftv47.atsp");
            System.out.println("2. Wczytaj plik ftv170.atsp");
            System.out.println("3. Wczytaj plik rbg403.atsp");
            System.out.println("4. Ustaw kryterium stopu");
            System.out.println("5. Ustaw temperaturę początkową");
            System.out.println("6. Ustaw współczynnik alpha");
            System.out.println("7. Symulowane wyżarzanie dla wcześniej wczytanego pliku");
            System.out.println("8. Koniec pracy z programem");
            Scanner scanner = new Scanner(System.in);
            string = scanner.nextLine();
            switch (string) {
                case "1" -> {
                    graph = Data.createDistanceMatrix("C:\\Users\\filip\\IdeaProjects\\ProjektowanieEfektywnychAlgorytmow2\\pliki_testowe\\ftv47.atsp");
                }
                case "2" -> {
                    graph = Data.createDistanceMatrix("C:\\Users\\filip\\IdeaProjects\\ProjektowanieEfektywnychAlgorytmow2\\pliki_testowe\\ftv170.atsp");
                }
                case "3" -> {
                    graph = Data.createDistanceMatrix("C:\\Users\\filip\\IdeaProjects\\ProjektowanieEfektywnychAlgorytmow2\\pliki_testowe\\rbg403.atsp");
                }
                case "4" -> {
                    System.out.println("Podaj czas w sekundach");
                    simulatedAnnealing.setMaxTime(scanner.nextInt());
                    scanner.nextLine();
                }
                case "5" -> {
                    System.out.println("Podaj temperaturę początkową");
                    simulatedAnnealing.setTemperature(scanner.nextDouble());
                    scanner.nextLine();
                }
                case "6" -> {
                    System.out.println("Podaj wartość współczynnika alpha");
                    simulatedAnnealing.setCooling(scanner.nextDouble());
                    scanner.nextLine();
                }
                case "7" -> {
                    simulatedAnnealing.setGraph(graph);
                    System.out.println(simulatedAnnealing.run());
                }
            }
        }
    }
}
