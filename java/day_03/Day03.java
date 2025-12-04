import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day03 {
    // part 2 from chat gpt , understood the concept
    private static long maxJoltageWithKDigits(String s, int k) {
        int n = s.length();
        int remove = n - k; // how many digits we must delete
        StringBuilder stack = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            // While the last chosen digit is smaller than the current one
            // and we still have deletions left, pop it to improve the number.
            while (remove > 0 && stack.length() > 0 && stack.charAt(stack.length() - 1) < ch) {
                stack.deleteCharAt(stack.length() - 1);
                remove--;
            }

            stack.append(ch);
        }

        // If we still need to remove digits, remove them from the end
        // (least significant side).
        if (remove > 0) {
            stack.setLength(stack.length() - remove);
        }

        // Ensure we keep exactly k digits.
        if (stack.length() > k) {
            stack.setLength(k);
        }

        return Long.parseLong(stack.toString());
    }

    public static void main(String[] args) {
        String fileName = "input_p3.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int sum  = 0;
            long sumPart2 = 0L;
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }
                int fBig = 0;
                int sBig = 0;

                int batteryBankSize = line.length();
                for(int i=0;i<batteryBankSize; i++){
                    char ch = line.charAt(i);
                    int digit = ch - '0';

                    if(digit > sBig){
                        sBig = digit;
                        if(sBig > fBig && (i != batteryBankSize-1)){
                            fBig = sBig;
                            sBig = 0;
                        }
                    }                    
                }
                sum += ((fBig * 10) + sBig);

                // solve next problem here    
                // ---------- Part 2: largest joltage using exactly 12 digits ----------
                long joltage12 = maxJoltageWithKDigits(line, 12);
                sumPart2 += joltage12;

            }
            System.err.println(sum);
            System.err.println(sumPart2);

        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
            e.printStackTrace();
        }
        
    }
}
