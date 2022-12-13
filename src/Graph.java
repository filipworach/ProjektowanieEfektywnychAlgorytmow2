import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private int[][] distanceMatrix;

    public Graph(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public Graph() {

    }


    public static ArrayList<Integer> swap (ArrayList<Integer> path) {
        Random random = new Random();

        int position1 = random.nextInt(path.size() - 2) + 1;
        int position2 = random.nextInt(path.size() - 2) + 1;

        while(position1 == position2) {
            position2 = random.nextInt(path.size() - 2) + 1;
        }

        int temporary = path.get(position1);
        path.set(position1, path.get(position2));
        path.set(position2, temporary);
        return path;

    }

    public ArrayList<Integer> getFirstPath() {
        ArrayList firstPath = new ArrayList<Integer>();
        int verticesAmount = distanceMatrix.length;
        Random random = new Random();
        int minDistance;
        int nextVertex = -1;
        int firstVertex = random.nextInt(verticesAmount);
        boolean visitedVertices[] = new boolean[verticesAmount];
        for(boolean vertex : visitedVertices) vertex = false;
        firstPath.add(firstVertex);
        visitedVertices[firstVertex] = true;
        int lastVertex = firstVertex;
        for(int i = 0; i < verticesAmount - 1; i++) {
            minDistance = Integer.MAX_VALUE;
            for (int j = 0; j < verticesAmount; j++) {
                if (distanceMatrix[firstVertex][j] < minDistance && !visitedVertices[j]) {
                    minDistance = distanceMatrix[firstVertex][j];
                    nextVertex = j;
                }
            }
            firstPath.add(nextVertex);
            visitedVertices[nextVertex] = true;
            firstVertex = nextVertex;
        }
        firstPath.add(lastVertex);
        return firstPath;
    }
    public int calculateDistance(ArrayList<Integer> path) {
        int distance = 0;
        int arraySize = path.size();
        for(int i = 0; i < arraySize - 1; i++) {
            distance+= distanceMatrix[path.get(i)][path.get(i+1)];
        }
        return distance;
    }

    public static void printPath(ArrayList<Integer> path) {
        int size = path.size();
        for (int i = 0; i < path.size(); i++) {
            if (i != path.size() - 1) {
                System.out.print(path.get(i));
                System.out.print(" -> ");
            } else System.out.print(path.get(i));
        }
        System.out.println();
    }

    public int getHowManyCities() {
        return distanceMatrix.length;
    }

}
