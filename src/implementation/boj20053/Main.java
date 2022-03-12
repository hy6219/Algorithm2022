package implementation.boj20053;

import java.util.Scanner;

public class Main {

    static Scanner scanner;
    static int T, N;
    static int[] numerics;
    static StringBuilder sb;

    static void pro() {
        scanner = new Scanner(System.in);
        sb = new StringBuilder();
        T = scanner.nextInt();

        for (int i = 0; i < T; i++) {
            N = scanner.nextInt();

            numerics = new int[N];

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (int j = 0; j < N; j++) {
                numerics[j] = scanner.nextInt();

                min = Math.min(min, numerics[j]);
                max = Math.max(max, numerics[j]);
            }
            sb.append(min)
                    .append(' ')
                    .append(max)
                    .append('\n');
        }
        System.out.print(sb);
    }

    public static void main(String[] args) {
        pro();
    }
}
