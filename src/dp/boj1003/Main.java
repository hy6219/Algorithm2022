package dp.boj1003;

import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static int T;
    static int[] cmd;
    static StringBuilder sb;
    static int[] zero = new int[10001];
    static int[] one = new int[10001];

    /**
     * zero[n>=2]=0호출 갯수=zero[n-1]+zero[n-2]
     * one[n>=2]=1호출 갯수=one[n-1]+one[n-2]
     */

    static void input() {
        scanner = new Scanner(System.in);
        T = Integer.parseInt(scanner.nextLine());

        cmd = new int[T + 1];

        for (int i = 0; i < T; i++) {
            cmd[i] = Integer.parseInt(scanner.nextLine());
        }
        sb = new StringBuilder();
    }

    static void fib() {
        zero[0] = 1;
        zero[1] = 0;

        one[0] = 0;
        one[1] = 1;

        for (int i = 2; i <= 40; i++) {
            zero[i] = zero[i - 1] + zero[i - 2];
            one[i] = one[i - 1] + one[i - 2];
        }
    }

    static void keep() {
        for (int i = 0; i < T; i++) {
            int val = cmd[i];
            sb.append(zero[val]).append(" ").append(one[val]).append('\n');
        }
        System.out.print(sb);
    }

    static void pro() {
        input();
        fib();
        keep();
    }

    public static void main(String[] args) {
        pro();
    }
}
