package dfs.boj1012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    /**
     * 1.배추위치를 map[][]로 기록
     * 2.배추가 있고! 방문하지 않았다면 지렁이가 갈수 있으므로
     * dfs진행하고, 지렁이1증가
     */

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

    static FastReader scan;
    static int T, M, N, K;
    static int[][] map;
    static int[][] dir = {
            //상하좌우
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };
    static boolean[][] visit;
    static int cnt;//지렁이 갯수

    static void pro() {
        scan = new FastReader();

        T = scan.nextInt();

        for (int i = 0; i < T; i++) {
            M = scan.nextInt();//가로길이
            N = scan.nextInt();//세로길이
            K = scan.nextInt();//배추갯수

            map = new int[M][N];
            visit = new boolean[M][N];
            cnt = 0;
            //배추위치입력받기(x,y 모두 0부터 시작
            for (int j = 0; j < K; j++) {
                map[scan.nextInt()][scan.nextInt()] = 1;
            }

            //배추가 있으면서, 방문하지 않았다면 dfs 진행
            for (int x = 0; x < M; x++) {
                for (int y = 0; y < N; y++) {
                    if (map[x][y] == 1 && !visit[x][y]) {
                        dfs(x, y);
                        cnt++;
                    }
                }
            }
            System.out.println(cnt);
        }
    }

    static void dfs(int x, int y) {
        //방문체크
        visit[x][y] = true;

        //4방면 방문
        for (int i = 0; i < 4; i++) {
            int nx = x + dir[i][0];
            int ny = y + dir[i][1];

            if (nx < 0 || ny < 0 || nx >= M || ny >= N) continue;
            if (visit[nx][ny]) continue;
            if (map[nx][ny] == 0) continue;
            //dfs(재귀)-방문처리하기(더이상 방문할 곳이 없을때까지~)
            dfs(nx, ny);
        }
    }

    public static void main(String[] args) {
        pro();
    }
}
