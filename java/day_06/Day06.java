import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Day06 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("input_p6.txt");

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        List<List<Integer>> numbersMatrix = new ArrayList<>();

        List<Character> ops = new ArrayList<>();

        int lastIndex = lines.size() - 1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }

            if (i < lastIndex) {
                String[] parts = line.split("\\s+");
                List<Integer> row = new ArrayList<>(parts.length);
                for (String p : parts) {
                    if (!p.isEmpty()) {
                        row.add(Integer.parseInt(p));
                    }
                }
                numbersMatrix.add(row);
            } else {
                String[] parts = line.split("\\s+");
                for (String p : parts) {
                    if (!p.isEmpty()) {
                        ops.add(p.charAt(0));
                    }
                }
            }
        }
        // problem 1
        Long sum = 0l;

        for(int i=0;i<ops.size();i++){
            Character c = ops.get(i);
            Long temp = 0l;
            if(c == '*'){
                temp = 1l;
                for(int j=0;j<numbersMatrix.size();j++){
                    temp = (temp * numbersMatrix.get(j).get(i));
                }
            }
            else if(c == '+'){
                for (int j = 0; j < numbersMatrix.size(); j++) {
                    temp = (temp + numbersMatrix.get(j).get(i));
                }

            }
            sum += temp;
        }
        System.out.println(sum);

    }
}
