import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day11 {
    public static void main(String[] args) throws IOException {
        Path input = Path.of("input_p11.txt");
        List<String> lines = Files.readAllLines(input);

        HashMap<String, Integer> nodes = new HashMap<>();
        ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
        int v = 0;
        for (String line : lines) {
            String[] parts = line.split(": ");
            for (String p : parts[1].split(" ")) {
                if (!p.equalsIgnoreCase("you")) {
                    if (!nodes.containsKey(parts[0])) {
                        nodes.put(parts[0], v);
                        v++;
                    }
                    if (!nodes.containsKey(p)) {
                        nodes.put(p, v);
                        v++;
                    }

                    if (nodes.containsKey(parts[0]) && nodes.containsKey(p)) {
                        edges.add(new ArrayList<Integer>(List.of(nodes.get(parts[0]), nodes.get(p))));
                    }
                }
            }
        }
        // System.out.println(nodes);
        // System.out.println(edges);
        // System.out.println(v);
        // System.out.println("souce: you " + nodes.get("you"));
        // System.out.println("souce: out " + nodes.get("out"));

        int src = nodes.get("svr");
        int dest = nodes.get("out");
        int fft = nodes.get("fft");
        int dac = nodes.get("dac");
        int you = nodes.get("you");

        // part - 01
        ArrayList<ArrayList<Integer>> paths = findNumberOfPaths(v, you, dest, edges);
        System.out.println(paths.size());

        // part - 02

        BigInteger result = countPathsThroughBig(v, src, dest, edges, fft, dac);
        System.out.println("Total paths from svr to out via fft & dac : " + result);

    }

    public static ArrayList<ArrayList<Integer>> findNumberOfPaths(int vertext, int src, int dest,
            ArrayList<ArrayList<Integer>> edges) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < vertext; i++) {
            graph.add(new ArrayList<>());
        }

        for (ArrayList<Integer> edge : edges) {
            graph.get(edge.get(0)).add(edge.get(1));
        }

        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        Queue<ArrayList<Integer>> q = new LinkedList<>();

        ArrayList<Integer> initialPath = new ArrayList<>();
        initialPath.add(src);
        q.add(initialPath);

        while (!q.isEmpty()) {
            ArrayList<Integer> path = q.poll();
            int current = path.get(path.size() - 1);
            if (current == dest) {
                // System.out.println(path);
                paths.add(new ArrayList<>(path));
            }

            for (int adj : graph.get(current)) {
                ArrayList<Integer> newPath = new ArrayList<>(path);
                newPath.add(adj);
                q.add(newPath);
            }
        }
        return paths;
    }

    public static BigInteger countPathsThroughBig(int vertexCount, int src, int dest,
            ArrayList<ArrayList<Integer>> edges, int a, int b) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            graph.add(new ArrayList<>());
        }
        for (ArrayList<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            graph.get(u).add(v); // directed u -> v
        }

        // memo[node][mask], where:
        // mask 0 = visited none
        // mask 1 = visited a (fft)
        // mask 2 = visited b (dac)
        // mask 3 = visited both
        BigInteger[][] memo = new BigInteger[vertexCount][4];
        boolean[] inStack = new boolean[vertexCount]; // to prevent cycles -> simple paths only

        return dfsBig(src, 0, dest, a, b, graph, memo, inStack);
    }

    private static BigInteger dfsBig(
            int node,
            int mask,
            int dest,
            int a,
            int b,
            List<List<Integer>> graph,
            BigInteger[][] memo,
            boolean[] inStack) {
        // Update mask for special nodes
        if (node == a)
            mask |= 1; // visited fft
        if (node == b)
            mask |= 2; // visited dac

        // Destination reached
        if (node == dest) {
            return (mask == 3) ? BigInteger.ONE : BigInteger.ZERO;
        }

        // Memoized?
        if (memo[node][mask] != null) {
            return memo[node][mask];
        }

        // Prevent cycles: no revisiting a node in current path
        if (inStack[node]) {
            return BigInteger.ZERO;
        }

        inStack[node] = true;
        BigInteger ways = BigInteger.ZERO;
        for (int next : graph.get(node)) {
            ways = ways.add(dfsBig(next, mask, dest, a, b, graph, memo, inStack));
        }
        inStack[node] = false;

        memo[node][mask] = ways;
        return ways;
    }
}
