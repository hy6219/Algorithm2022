package implementation.boj17144;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static class Dust {
        private int r;
        private int c;
        private int quantity;

        public Dust(int r, int c, int quantity) {
            this.r = r;
            this.c = c;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "Dust{" +
                    "r=" + r +
                    ", c=" + c +
                    ", quantity=" + quantity +
                    '}';
        }
    }

    static int R, C, T;
    static int[][] map;
    static int[][] dir = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };
    //공기청정기 위치
    static int cleanerR = -1;
    static Queue<Dust> queue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        map = new int[R][C];

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

                if (cleanerR == -1 && map[i][j] == -1) {
                    cleanerR = i;
                }
            }

        }


        for (int i = 0; i < T; i++) {

            multisource();
            //1.미세먼지 확산
            spread();
            //2.공기청정기 작동
            activateCleaner();
        }

        int quantity = 0;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == -1) continue;

                quantity += map[i][j];
            }
        }
        System.out.println(quantity);
    }

    static void multisource() {
        queue = new LinkedList<>();//미세먼지가 존재하는 곳

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == -1 || map[i][j] == 0) continue;
                queue.add(new Dust(i, j, map[i][j]));
            }
        }
    }

    /**
     * 미세먼지 확산
     */
    static void spread() {

        while (!queue.isEmpty()) {
            Dust cur = queue.poll();
            int r = cur.r;
            int c = cur.c;
            //확산될 양이 없으면 확산불가(/5만큼을 인접칸에 주어야 하기 때문)
            if (cur.quantity < 5) continue;
            int temp = cur.quantity / 5;
            int cnt = 0;


            for (int i = 0; i < 4; i++) {
                int nr = r + dir[i][0];
                int nc = c + dir[i][1];

                if (nr < 0 || nc < 0 || nr >= R || nc >= C) continue;
                if (map[nr][nc] == -1) continue;//공기청정기 있는 곳으로 진입 불가

                map[nr][nc] += temp;//확산
                ++cnt;
            }

            map[r][c] -= temp * cnt;
        }
    }

    /**
     * 공기청정기 작동
     */
    static void activateCleaner() {

        //A.윗쪽
        int up = cleanerR;
        int down = cleanerR + 1;
        //좌측- 아래로 당기기
        for (int i = up - 1; i > 0; i--) {
            map[i][0] = map[i - 1][0];
        }
        //윗변- 왼쪽으로 당기기
        for (int i = 0; i < C - 1; i++) {
            map[0][i] = map[0][i + 1];
        }
        //우측- 위로 당기기(1행~up-1행)
        for (int i = 0; i < up; i++) {
            map[i][C - 1] = map[i + 1][C - 1];
        }
        //밑변 - 오른쪽으로 당기기
        for (int i = C - 1; i > 1; i--) {
            map[up][i] = map[up][i - 1];
        }

        map[up][1] = 0;//공기청정기

        //B.밑쪽

        //좌측-위로 올리기
        for (int i = down + 1; i < R - 1; i++) {
            map[i][0] = map[i + 1][0];
        }
        //밑변-왼쪽으로 옮기기
        for (int i = 0; i < C - 1; i++) {
            map[R - 1][i] = map[R - 1][i + 1];
        }
        //우측-아래로 내리기
        for (int i = R - 1; i > down; i--) {
            map[i][C - 1] = map[i - 1][C - 1];
        }

        //윗쪽-오른쪽으로
        for (int i = C - 1; i > 1; i--) {
            map[down][i] = map[down][i - 1];
        }

        map[down][1] = 0;
    }

}
