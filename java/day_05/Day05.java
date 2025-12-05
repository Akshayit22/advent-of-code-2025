import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class Day05 {
    private record Range(long start, long end) {
    }

    public static void main(String[] args) {

        String fileName = args.length > 0 ? args[0] : "input_p5.txt";
        List<Range> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        try {
            boolean parsingRanges = true;
            for (String line : Files.readAllLines(Path.of(fileName))) {
                line = line.trim();
                if (line.isEmpty()) {
                    parsingRanges = false;
                    continue;
                }
                if (parsingRanges) {
                    String[] p = line.split("-");
                    ranges.add(new Range(Long.parseLong(p[0].trim()), Long.parseLong(p[1].trim())));
                } else {
                    ids.add(Long.parseLong(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }
        

        // problem 1
        Long count = 0l;

        for (Long id : ids) {
            for (Range r : ranges) {
                long start = r.start();
                long end = r.end();

                if (id >= start && id <= end) {
                    // // Long c = (end - start + 1l);
                    // count = count + c;
                    // System.err.println(c);
                    count++;
                    break;
                }
            }
        }
        System.out.println(count);

        // problem 2
        ranges.sort((a, b) -> Long.compare(a.start(), b.start()));
        
        count = 0l;
        Long currentStart = -1l;

        for (Range r : ranges) {
            long start = r.start();
            long end = r.end();
            if(currentStart >= start){
                start = currentStart + 1l;
            }
            if(start <= end){
                //System.out.println("  " + start + "-" + end);
                count += (end - start + 1l);
            }

            currentStart = Long.max(currentStart, end);
        }

        System.out.println(count);
    }
}