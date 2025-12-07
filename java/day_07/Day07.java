import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day07 {
    public static void main(String[] args) throws IOException {
        String fileName = "sample.txt";
        // String fileName = "input_p7.txt";

        List<String> lines = Files.readAllLines(Path.of(fileName))
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        if (lines.isEmpty()) {
            System.out.println("No data found in file: " + fileName);
            return;
        }

        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            char[] row = lines.get(r).toCharArray();
            if (row.length != cols) {
                throw new IllegalStateException("Inconsistent row length at line " + (r + 1));
            }
            grid[r] = row;
        }

        // problem 1
        int count = 0;

        for (int row = 0; row < rows - 1; row++) {

            for (int col = 0; col < cols; col++) {

                if (grid[row][col] == 'S') {
                    grid[row + 1][col] = '|';
                }
                if (grid[row][col] == '|') {
                    if (grid[row + 1][col] == '^') {
                        count++;
                        if (grid[row + 1][col - 1] == '.') {
                            grid[row + 1][col - 1] = '|';

                        }
                        if (grid[row + 1][col + 1] == '.') {
                            grid[row + 1][col + 1] = '|';
                        }
                    } else if (grid[row + 1][col] == '.') {
                        grid[row + 1][col] = '|';
                    }

                }
                //System.out.print(grid[row][col]);
            }
            //System.out.println();
        }
        System.out.println(count); // p1 = 1628

        // problem 2
        long timelines = countTimelines(grid);
        System.out.println("total timelines: " + timelines);
    }

    private static long countTimelines(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        long[][] ways = new long[rows][cols];
        ways[0][((cols - 1) / 2)] = 1L;// start position S

        long totalExit = 0L;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                long w = ways[r][c];
                if (w == 0L){
                    System.err.print(ways[r][c]);
                    continue;
                }
                // If we're on the last row, next step is outside the manifold
                if (r == rows - 1) {
                    totalExit += w;
                    continue;
                }

                char below = grid[r + 1][c];

                if (below == '.') {
                    // Just move straight down
                    ways[r + 1][c] += w;
                } else if (below == '^') {
                    // Splitter under us: each timeline branches into two

                    // Left branch
                    ways[r + 1][c - 1] += w;
                    // Right branch
                    ways[r + 1][c + 1] += w;
                    
                } else {
                    ways[r + 1][c] += w;
                }
                System.err.print(ways[r][c]);
            }
            System.out.println();
        }

        return totalExit;
    }
}