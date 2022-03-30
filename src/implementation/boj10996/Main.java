package implementation.boj10996;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static int N;
    static StringBuilder sb = new StringBuilder();

    static void printStar() {
        for (int row = 0; row < 2 * N; row++) {
            for (int col = 0; col < N; col++) {
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        sb.append("*");
                    } else {
                        sb.append(" ");
                    }
                } else {
                    if (col % 2 == 0) {
                        sb.append(" ");
                    } else {
                        sb.append("*");
                    }
                }
            }
            sb.append("\n");
        }

        System.out.print(sb.toString().strip());
    }

    public static void main(String[] args) {
        N = scanner.nextInt();
        printStar();
    }
}
