import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day04 {
    public static void main(String[] args) throws IOException {
        String fileName = "input_p4.txt";

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

        int[][] offsets = {
                { -1, -1 }, { -1, 0 }, { -1, 1 },
                { 0, -1 }, { 0, 1 },
                { 1, -1 }, { 1, 0 }, { 1, 1 }
        };
        int sum =0;
        int ans = 0;
        int count = 0;

        while (true) {
            ans = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char ch = grid[i][j];
                    if (ch == '.' || ch == 'x') {
                        continue;
                    }
                    count = 0;
                    for (int[] d : offsets) {
                        int nr = i + d[0];
                        int nc = j + d[1];
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                            if (grid[nr][nc] == '@') {
                                count++;
                            }
                            if (count == 4)
                                break;
                        }
                        //System.err.println();
                    }

                    if (count < 4) {
                        ans++;
                        grid[i][j] = 'x';
                    }
                    //System.out.print(grid[i][j]);
                    // if (rows == 1) {
                    //     return;
                    // }

                }

            }
            System.out.println("problem 1 " + ans); // remove the while loop for problem 1
            sum += ans;
            if(ans == 0){
                break;
            }
        }
        System.out.println(sum);
    }
}
