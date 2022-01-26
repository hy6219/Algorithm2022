package dp.boj2156;

import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static int N;
    static int[] podoju;
    static int[] dp;

    static void input() {
        scanner = new Scanner(System.in);
        N = Integer.parseInt(scanner.nextLine());

        podoju = new int[10001];

        for (int i = 1; i <= N; i++) {
            podoju[i] = Integer.parseInt(scanner.nextLine());
        }

        dp = new int[10001];

    }

    static void drink() {
        //dp[n]에서 n>=3
        //초기값
        dp[1] = podoju[1];
        dp[2] = podoju[1] + podoju[2];

        for (int i = 3; i <= N; i++) {
            //마지막잔을 마셨을 경우, 직전잔을 마신 경우와 마시지 않은 경우의 최댓값을 고려
            dp[i] = Math.max(podoju[i] + podoju[i - 1] + dp[i - 3], podoju[i] + dp[i - 2]);
            //마지막잔을 마시지 않았을 경우도 존재
            dp[i] = Math.max(dp[i], dp[i - 1]);
        }
    }

    static void pro() {
        input();
        drink();
        System.out.println(dp[N]);
    }

    public static void main(String[] args) {
        pro();
    }
}
