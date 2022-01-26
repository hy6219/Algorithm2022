package bfs.boj2839;

import java.util.Scanner;

public class Main {

    static Scanner scanner;
    static int N;

    static void input(){
        scanner = new Scanner(System.in);
        N = Integer.parseInt(scanner.nextLine());
    }

    static void solve(){
        int res =Integer.MAX_VALUE;
        int temp = 0;//무게 체크
        for(int i = 0; i < N; i++){//3kg
            for(int j = 0 ; j < N; j++) {//5kg
                temp = 5 * j+3*i;
                if(temp == N) {
                    res= i+j;
                    System.out.println(res);
                    return;
                }
            }
        }
        System.out.println(-1);
        return;
    }

    static void pro(){
        input();
        solve();
    }

    public static void main(String[] args) {
        pro();
    }
}
