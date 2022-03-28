package dfs.boj2468;

import java.util.Scanner;

/**
 * 틀렸던 이유: 비가 아예 오지 않는 경우(0)도 고려해야 함!!
 * https://www.acmicpc.net/board/view/80151
 */
public class Main {

    static Scanner scanner;
    static int N;
    static int[][] map;
    static int[][] copyMap;
    static boolean[][] visited;
    //비양 최대 높이
    static int RAIN_HEIGHT_MAX = Integer.MIN_VALUE;
    //최대 영역수 갱신
    static int MAX_AREA = 0;
    static int[][] dir = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    static void input() {
        scanner = new Scanner(System.in);

        N = scanner.nextInt();

        map = new int[N][N];
        copyMap = new int[N][N];
        visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = scanner.nextInt();
                map[i][j] = num;
                //비 높이 영역 최소, 최대도 이때 알아두기
                RAIN_HEIGHT_MAX = Math.max(RAIN_HEIGHT_MAX, num);

            }
        }
    }

    static void simulation() {

        for (int trial = 0; trial <= RAIN_HEIGHT_MAX; trial++) {
            //매번 초기화
            for (int i = 0; i < N; i++) {
                copyMap[i] = map[i].clone();
            }
            //dfs
            visited = new boolean[N][N];
            int area = 0;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(copyMap[i][j]<= trial) continue;
                    if(visited[i][j]) continue;
                    area += dfs(i, j, trial);
                }
            }

            MAX_AREA = Math.max(MAX_AREA, area);
        }
        System.out.print(MAX_AREA);
    }

    /**
     * dfs
     */
    static int dfs(int r, int c, int quantity) {
        visited[r][c] = true;

        for (int i = 0; i < 4; i++) {
            int nr = r + dir[i][0];
            int nc = c + dir[i][1];

            if(nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
            if(visited[nr][nc]) continue;
            if(copyMap[nr][nc] <= quantity) continue;

            dfs(nr,nc,quantity);
        }

        return 1;
    }

    static void pro() {
        input();
        simulation();
    }

    public static void main(String[] args) {
        pro();
    }
}
