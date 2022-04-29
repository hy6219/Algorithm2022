package implementation.boj20058;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Remain {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, q;
    static int[][] board;
    static int[] cmd;
    static int limit;
    static int[][] dir = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };
    static int total = 0;
    static int MAX = 0;//덩어리가 없는 경우도 존재
    static boolean[][] visited;
    static int areaCnt = 0;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        limit = (int) Math.pow(2, n);
        board = new int[limit][limit];

        for (int i = 0; i < limit; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < limit; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        cmd = new int[q];

        for (int i = 0; i < q; i++) {
            cmd[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < q; i++) {
            int level = cmd[i];
            //1.각 파트별 시계방향 90도 회전
            int size = (int) Math.pow(2, level);

            board = divide(size);
            //2.얼음이 있는 칸이 3개 이상 인접하지 않으면 해당 칸의 얼음양 1 줄이기
            board = reduceIce();
        }

        //얼음 총양 구하기
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                total += board[i][j];
            }
        }

        /*
        남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 갯수
        ->인접칸이 있는 칸을 dfs로 카운트해서 더해주고,
        이를 최댓값 갱신하여 확인
         */
        visited = new boolean[limit][limit];
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                if (board[i][j] <= 0) continue;
                if (visited[i][j]) continue;

                areaCnt = 1;
                dfs(i, j);
                MAX = Math.max(MAX, areaCnt);
            }
        }
        String out = String.format("%d\n%d", total, MAX);
        System.out.print(out);
    }

    static int[][] divide(int size) {
        int[][] temp = new int[limit][limit];

        for (int i = 0; i < limit; i += size) {
            for (int j = 0; j < limit; j += size) {
                rotate(i, j, size, temp);
            }
        }
        return temp;
    }

    static void rotate(int x, int y, int width, int[][] arr) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                arr[x + j][y + width - i - 1] = board[x + i][y + j];
            }
        }
    }

    static int[][] reduceIce() {
        int[][] temp = new int[limit][limit];

        for (int i = 0; i < limit; i++) {
            temp[i] = Arrays.copyOf(board[i], limit);
        }


        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                int cnt = 0;
                if (board[i][j] <= 0) continue;

                for (int k = 0; k < 4; k++) {
                    int nr = i + dir[k][0];
                    int nc = j + dir[k][1];

                    if (nr < 0 || nc < 0 || nr >= limit || nc >= limit) continue;
                    if (board[nr][nc] <= 0) continue;
                    cnt++;
                }

                if (cnt < 3) {
                    temp[i][j]--;
                }
            }
        }

        return temp;
    }

    static void dfs(int r, int c) {

        visited[r][c] = true;

        for (int i = 0; i < 4; i++) {
            int nr = r + dir[i][0];
            int nc = c + dir[i][1];

            if (nr < 0 || nc < 0 || nr >= limit || nc >= limit) continue;
            if (!visited[nr][nc] && board[nr][nc] > 0) {
                dfs(nr, nc);
                areaCnt++;
            }
        }
    }
}
