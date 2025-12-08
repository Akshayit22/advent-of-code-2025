import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day08 {
    private static final class Point {
        final int x;
        final int y;
        final int z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ", " + z + ")";
        }
    }

    private record Distance(int a, int b, long distance) { }

    public static void main(String[] args) throws IOException {
        Path input = Path.of("input_p8.txt");
        List<Point> points = loadPoints(input);

        //System.out.println(getDistanceSqure(new Point(2, 3, 5), new Point(4, -1, 7)));
        //points.forEach(System.out::println);
        ArrayList<Distance> distances = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < i; j++) {
                long dist = getDistanceSqure(points.get(i), points.get(j));
                distances.add(new Distance(i, j, dist));
            }
        }
        distances.sort((a, b) -> Long.compare(a.distance(), b.distance()));
        //distances.forEach(System.out::println);

        int n = points.size();
        int edgesToConnect = 1000;

        int[] group = new int[n];
        int[] size = new int[n];
        for (int i = 0; i < n; i++) {
            group[i] = i;  // each node starts in its own group
            size[i] = 1;   // each group has initial size 1
        }

        int connectionsToProcess = Math.min(edgesToConnect, distances.size());
        for (int k = 0; k < connectionsToProcess; k++) {
            Distance d = distances.get(k);
            int a = d.a();
            int b = d.b();

            int ga = group[a];
            int gb = group[b];

            if (ga == gb) {
                // already in the same circuit, nothing to do
                continue;
            }

            // Merge group gb into group ga by relabeling all members
            for (int i = 0; i < n; i++) {
                if (group[i] == gb) {
                    group[i] = ga;
                }
            }

            // Update sizes: ga absorbs gb
            size[ga] += size[gb];
            size[gb] = 0;
        }

        // Collect circuit sizes from the size[] array
        List<Integer> circuitSizes = new ArrayList<>();
        for (int g = 0; g < n; g++) {
            if (size[g] > 0) {
                circuitSizes.add(size[g]);
            }
        }

        // Sort circuit sizes descending
        circuitSizes.sort((a, b) -> Integer.compare(b, a));

        if (circuitSizes.size() < 3) {
            throw new IllegalStateException("Expected at least 3 circuits, found: " + circuitSizes.size());
        }

        long result = 1L * circuitSizes.get(0) * circuitSizes.get(1) * circuitSizes.get(2);
        System.out.println("Product of sizes of three largest circuits: " + result);


        // --- Part Two ---

        int components = 0;
        for (int g = 0; g < n; g++) {
            if (size[g] > 0) {
                components++;
            }
        }

        int lastXA = -1;
        int lastXB = -1;

        for (int k = connectionsToProcess; k < distances.size() && components > 1; k++) {
            Distance d = distances.get(k);
            int aIdx = d.a();
            int bIdx = d.b();

            int ga = group[aIdx];
            int gb = group[bIdx];

            if (ga == gb) {
                // already in the same circuit, nothing to do
                continue;
            }

            // Merge group gb into group ga by relabeling all members
            for (int i = 0; i < n; i++) {
                if (group[i] == gb) {
                    group[i] = ga;
                }
            }

            // Update sizes and component count
            size[ga] += size[gb];
            size[gb] = 0;
            components--;

            // If this merge results in a single circuit, remember this pair
            if (components == 1) {
                lastXA = points.get(aIdx).x;
                lastXB = points.get(bIdx).x;
                break;
            }
        }

        if (lastXA == -1 || lastXB == -1) {
            throw new IllegalStateException("Did not reach a single circuit in Part Two.");
        }

        long part2 = 1L * lastXA * lastXB;
        System.out.println("Product of X coordinates of last connected pair: " + part2);


    }

    static long getDistanceSqure(Point a, Point b) {
        long dx = (long) a.x - b.x;
        long dy = (long) a.y - b.y;
        long dz = (long) a.z - b.z;
        return dx * dx + dy * dy + dz * dz;
    }

    private static List<Point> loadPoints(Path input) throws IOException {
        List<String> lines = Files.readAllLines(input);
        List<Point> points = new ArrayList<>();

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split(",");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Expected 3 comma-separated values per line, got: " + line);
            }

            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            int z = Integer.parseInt(parts[2].trim());
            points.add(new Point(x, y, z));
        }

        return points;
    }
}