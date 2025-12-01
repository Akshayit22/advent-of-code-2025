import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Move {
    char direction;
    int steps;

    Move(char direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "{ '" + direction + "', " + steps + " }";
    }
}

public class Day01 {
    public static void main(String[] args) {

        List<Move> moves = new ArrayList<>();

        String fileName = "input_p1.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                char direction = line.charAt(0);
                int value = Integer.parseInt(line.substring(1));

                moves.add(new Move(direction, value));
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
            e.printStackTrace();
        }

        // moves.add(new Move('L', 68));
        // moves.add(new Move('L', 30));
        // moves.add(new Move('R', 48));
        // moves.add(new Move('L', 5));
        // moves.add(new Move('R', 60));
        // moves.add(new Move('L', 55));
        // moves.add(new Move('L', 1));
        // moves.add(new Move('L', 99));
        // moves.add(new Move('R', 14));
        // moves.add(new Move('L', 82));

        int pointer = 50;
        int count = 0;

        for (Move move : moves) {
            int moveSteps = move.steps % 100;
            if (move.direction == 'L') {
                pointer -= moveSteps;
                if (pointer < 0) {
                    pointer += 100;
                }
            } else {
                pointer += moveSteps;
                if (pointer >= 100) {
                    pointer -= 100;
                }
            }

            if (pointer == 100 || pointer == 0) {
                count++;
            }
        }
        System.out.println(count);
        count = 0;
        pointer = 50;

        for (Move move : moves) {
            int steps = move.steps;

            if (move.direction == 'L') {
                for (int i = 0; i < steps; i++) {
                    pointer--;
                    if (pointer < 0) {
                        pointer = 99;
                    }
                    if (pointer == 0) {
                        count++;
                    }
                }
            } else {
                for (int i = 0; i < steps; i++) {
                    pointer++;
                    if (pointer > 99) {
                        pointer = 0;
                    }
                    if (pointer == 0) {
                        count++;
                    }
                }
            }
        }

        System.out.println(count);
        count = 0;
        pointer = 50;
        // alternate way
        for (Move move : moves) {
            int steps = move.steps;
            char dir = move.direction;

            for (int i = 0; i < steps; i++) {
                if (dir == 'L') {
                    pointer = (pointer - 1 + 100) % 100;
                } else {
                    pointer = (pointer + 1) % 100;
                }

                if (pointer == 0) {
                    count++;
                }
            }

        }

        System.out.println(count);
    }
}