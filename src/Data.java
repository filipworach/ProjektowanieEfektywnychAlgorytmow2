import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Data {
    public static Graph createDistanceMatrix(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        for (int i = 0; i < 3; i++)
            if (scanner.hasNextLine()) scanner.nextLine();

        if (scanner.hasNext()) {
            scanner.next();
        }
        int dimension = scanner.nextInt();
        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < 4; i++) if (scanner.hasNextLine()) scanner.nextLine();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = scanner.nextInt();
                if(i == j) matrix[i][j] = -1;
            }
        }

        return new Graph(matrix);
    }
}
