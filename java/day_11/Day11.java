import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day11 {
    public static void main(String[] args) throws IOException {
        Path input = Path.of("sample.txt");
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
        System.out.println(nodes);
        System.out.println(edges);
        // System.out.println(v);
        // System.out.println("souce: you " + nodes.get("you"));
        // System.out.println("souce: out " + nodes.get("out"));

        ArrayList<ArrayList<Integer>> paths = findNumberOfPaths(v, nodes.get("you"), nodes.get("out"), edges);
        System.out.println(paths.size());

        // for (ArrayList<Integer> path : paths) {
        //     for (int vtx : path) {
        //         System.out.print(vtx + " ");
        //     }
        //     System.out.println();
        // }
    }

    public static ArrayList<ArrayList<Integer>> findNumberOfPaths(int vertext, int src, int dest, ArrayList<ArrayList<Integer>> edges) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for(int i=0;i< vertext;i++){
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

        while(!q.isEmpty()){
            ArrayList<Integer> path = q.poll();
            int current = path.get(path.size() - 1);
            if(current == dest){
                paths.add(new ArrayList<>(path));
                // System.out.println(path);
            }

            for(int adj: graph.get(current)){
                ArrayList<Integer> newPath = new ArrayList<>();
                newPath.add(adj);
                q.add(newPath);
            }
        }
        // System.out.println(paths);
        
        return paths;

    }
}
