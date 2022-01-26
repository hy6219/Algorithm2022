package dp.boj2839;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Scanner scanner;
    static int N;
    static int[] dp;

    static void input(){
        scanner=new Scanner(System.in);
        N = Integer.parseInt(scanner.nextLine());
        dp = new int[10001];
    }

    static void makeTable(){
        Arrays.fill(dp,10000);//최솟값 선택 영향 배제시키기 위함
        dp[3] = 1;
        dp[5] = 1;

        for(int cand = 6; cand <=N; cand++){
            dp[cand] = Math.min(dp[cand-3]+1, dp[cand-5]+1);
        }

        if(dp[N] >=10000) dp[N] = -1;
    }

    static void pro(){
        input();
        makeTable();
        System.out.println(dp[N]);
    }

    public static void main(String[] args) {
        pro();
    }
}
