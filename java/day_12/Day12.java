import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day12 {
    public static void main(String[] args) throws IOException{
        
        Path input = Path.of("input_p12.txt");
        List<String> lines = Files.readAllLines(input);

        ArrayList<HashMap<Integer, ArrayList<Integer>>> areaCount = new ArrayList<>();

        ArrayList<Integer> metrixCount  = new ArrayList<>();

        boolean flag = false;
        int countOccupied = 0;

        for (String line : lines) {
            
            if(line.contains("x")){
                String[] part = line.split(": ");
                String[] size = part[0].split("x");

                HashMap<Integer, ArrayList<Integer>> tempArea = new HashMap<>();

                int x = Integer.parseInt(size[0]);
                int y = Integer.parseInt(size[1]);

                ArrayList<Integer> temp = new ArrayList<>();

                for(String s: part[1].split(" ")){
                    temp.add(Integer.parseInt(s));
                }

                tempArea.put(x * y, temp);
                areaCount.add(tempArea);
            }

            if (line.contains(":")) {
                flag = true;
            }else{
                for(char c: line.toCharArray()){
                    if(c == '#'){
                        countOccupied++;
                    }
                }
            }

            if(line.isEmpty()){
                if(flag){
                    metrixCount.add(countOccupied);
                }
                flag = false;
                countOccupied = 0;
            }
        }
        
        int[] y = { 0 };

        for(int i=0;i< areaCount.size();i++){
            
            areaCount.get(i).forEach((a,b)->{
                // System.out.println(a);
                int areaRequired = 0;
                for(int j=0;j<b.size();j++){
                    areaRequired += b.get(j) * metrixCount.get(j);
                }

                if(areaRequired < a){
                    y[0]++;
                }
            });
        }

        System.out.println(y[0]);

        // System.out.println(areaCount);
        // System.out.println(metrixCount);

    }
}
