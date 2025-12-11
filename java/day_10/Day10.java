import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {
    private static final int MAX_BUTTONS_BRUTE_FORCE = 20; // guard to avoid explosion

    public static void main(String[] args) throws IOException {
        
        Path input = Path.of("input_p10.txt");
        List<String> lines = Files.readAllLines(input);

        ArrayList<ArrayList<Boolean>> lightIndicator = new ArrayList<ArrayList<Boolean>>();
        ArrayList<ArrayList<Set<Integer>>> buttonWirings = new ArrayList<ArrayList<Set<Integer>>>();
        ArrayList<Set<Integer>> linesOnButtons = new ArrayList<Set<Integer>>();

        // Parse each line into indicator lights, button wirings, and lit indices.
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            ArrayList<Set<Integer>> lineWirings = new ArrayList<Set<Integer>>();
            for (String part : parts) {
                ArrayList<Boolean> temp = new ArrayList<Boolean>();
                Set<Integer> onButtons = new HashSet<Integer>();
                if (part.contains("[")) {
                    String inner = part.subSequence(1, part.length() - 1).toString();
                    for (int i = 0; i < inner.length(); i++) {
                        if (inner.charAt(i) == '.') {
                            temp.add(false);
                        } else {
                            temp.add(true);
                            onButtons.add(i);
                        }
                    }
                    lightIndicator.add(temp);
                    linesOnButtons.add(onButtons);
                }

                if (part.contains("{")) {
                    continue;
                }

                if (part.contains("(")) {
                    String inner = part.substring(part.indexOf('(') + 1, part.lastIndexOf(')'));
                    Set<Integer> wiring = new HashSet<Integer>();
                    if (!inner.isBlank()) {
                        for (String num : inner.split(",")) {
                            wiring.add(Integer.parseInt(num.trim()));
                        }
                    }
                    lineWirings.add(wiring);
                }
            }
            buttonWirings.add(lineWirings);
        }


        int totalMinPresses = 0;

        int n = lightIndicator.size();
        for (int i = 0; i < n; i++) {
            ArrayList<Set<Integer>> wirings = buttonWirings.get(i);
            Set<Integer> targetOn = linesOnButtons.get(i);
            int lightCount = lightIndicator.get(i).size();

            int minPresses = findMinPressCount(wirings, targetOn, lightCount);
            if (minPresses < 0) {
                System.out.println("Line " + (i + 1) + ": no solution found within search guard.");
            } else {
                totalMinPresses += minPresses;
            }
        }

        System.out.println("Sum of minimum button presses: " + totalMinPresses);
    }

    private static int findMinPressCount(ArrayList<Set<Integer>> wirings, Set<Integer> targetOn, int lightCount) {
        int buttonCount = wirings.size();
        if (buttonCount > MAX_BUTTONS_BRUTE_FORCE) {
            return -1; // avoid explosion
        }

        int best = Integer.MAX_VALUE;
        int totalMasks = 1 << buttonCount;
        // start from all lights off
        for (int mask = 0; mask < totalMasks; mask++) {
            int presses = Integer.bitCount(mask);
            if (presses >= best) {
                continue;
            }

            boolean[] state = new boolean[lightCount];
            for (int b = 0; b < buttonCount; b++) {
                if ((mask & (1 << b)) == 0) {
                    continue;
                }
                for (int idx : wirings.get(b)) {
                    if (idx >= 0 && idx < lightCount) {
                        state[idx] = !state[idx];
                    }
                }
            }
            
            for(Boolean b: state){
                System.out.print(b + " ");
            }
            System.out.println(targetOn);
            if (matchesTarget(state, targetOn)) {
                best = presses;
            }
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }

    private static boolean matchesTarget(boolean[] state, Set<Integer> targetOn) {
        for (int i = 0; i < state.length; i++) {
            boolean shouldBeOn = targetOn.contains(i);
            if (state[i] != shouldBeOn) {
                return false;
            }
        }
        return true;
    }
}
