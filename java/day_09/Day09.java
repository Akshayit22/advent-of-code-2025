
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day09 {
    public static void main(String[] args) {
        Path inputPath = Path.of("input_p9.txt");
        List<int[]> coords = new ArrayList<>();

        try {
            for (String line : Files.readAllLines(inputPath)) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(",");
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                coords.add(new int[] { x, y });
            }
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            return;
        }

        if (coords.isEmpty()) {
            System.out.println("No coordinates found.");
            return;
        }

        Long max = 0l;

        for(int i=0;i<coords.size();i++){
            for (int j = i; j < coords.size(); j++) {
                if(i != j){
                    // System.out.print(coords.get(i)[0] + "   " + coords.get(i)[1] + " --> ");

                    Long area = (long)(Math.abs(
                            coords.get(i)[1] - coords.get(j)[1]) + 1) * (Math.abs(
                                    coords.get(j)[0] - coords.get(i)[0]) + 1) ;

                    // System.out.println(coords.get(j)[0] + "   " + coords.get(j)[1] + " ----> " + area);

                    if(area < 0){
                        area *= -1; 
                    }

                    if(max < area){
                        max = area;
                    }

                }
                
            }
            
        }
        System.out.println(max/25);

    }
}
