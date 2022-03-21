package bruteforce.boj14889;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String nextLine() {
            String str = "";

            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return str;
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    static FastReader scanner;
    static int N;
    static int[][] skill;//능력치표
    //뽑았는지 중복체크(링크팀에 들어갔든, 스타트팀에 들어갔든 뽑힌 것을 체크)
    static boolean[] visited;
    //두 팀 능력치 차 최소
    static int MIN = Integer.MAX_VALUE;


    static void input() {
        scanner = new FastReader();

        N = scanner.nextInt();

        visited = new boolean[N + 1];

        skill = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                skill[i][j] = scanner.nextInt();
            }
        }
    }

    //스타트팀을 뽑으면 링크팀은 나머지로 계산
    static void rec_func(int idx, int k) {
        if (k == N / 2) {
            //다 뽑은 경우, 두 팀 간 스킬을 계산하고, 두 팀 점수차 최솟값 계산
            calculate();
            return;
        } else {
            //중복없이 뽑기 위해서 idx 이용+1~N 중 선택하므로 +1
            for (int cand = idx + 1; cand < N; cand++) {
                if (visited[cand]) continue;
                visited[cand] = true;
                rec_func(cand, k + 1);
                visited[cand] = false;
            }
        }
    }

    static void calculate() {
        //나머지는 링크팀으로 구성(<-visited 값으로 구별)

        //스타트팀
        int st = 0;
        //링크팀
        int lnk = 0;

        for (int i = 1; i < N; i++) {
            //겹치지 않으므로 살펴보기
            for (int j = i + 1; j <= N; j++) {
                //스타트팀
                if (visited[i] && visited[j]) {
                    st += skill[i][j] + skill[j][i];
                }else if (!visited[i] && !visited[j]) {
                    //링크팀
                    lnk += skill[i][j] + skill[j][i];
                }
            }
        }

        int chk = Math.abs(st - lnk);
        if(chk == 0){
            System.out.println(chk);
            System.exit(0);
        }

        MIN = Math.min(MIN, chk);
    }

    static void pro() {
        input();
        rec_func(0, 0);
        System.out.println(MIN);
    }

    public static void main(String[] args) {
        pro();
    }
}
