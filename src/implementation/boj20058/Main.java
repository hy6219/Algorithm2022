package implementation.boj20058;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int[][] dir = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };
    static int n, q;
    static int limit;
    static int[][] A;
    static List<Integer> cmd;
    static int max = 0;
    static int totalIce = 0;

    /**
     * 반례
     * 2 1
     * 0 0 0 0
     * 0 0 0 0
     * 0 0 0 0
     * 0 0 0 0
     * 0
     * ==>이를 위해 max 디폴트값은 0으로 잡아줄 것임
     * https://www.acmicpc.net/board/view/76134
     */

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        limit = (int) Math.pow(2, n);
        A = new int[limit][limit];
        cmd = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < limit; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < q; i++) {
            cmd.add(Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < q; i++) {
            int cur = cmd.get(i);
            //분할+회전
            A = divide(cur);
            //얼음 녹이기
            A = melt();
        }

        //가장 큰 영역 구하기
        findBiggest();
        System.out.println(totalIce);
        System.out.print(max);
    }

    static int[][] divide(int size) {
        int[][] temp = new int[limit][limit];
        int step = (int) Math.pow(2, size);

        for (int i = 0; i < limit; i += step) {
            for (int j = 0; j < limit; j += step) {
                rotate(i, j, step, temp);
            }
        }
        return temp;
    }

    static void rotate(int r, int c, int width, int[][] arr) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                arr[r + j][c + width - i - 1] = A[r + i][c + j];
            }
        }
    }

    static int[][] melt() {
        int[][] temp = new int[limit][limit];

        for (int i = 0; i < limit; i++) {
            temp[i] = Arrays.copyOf(A[i], limit);
        }

        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                int cnt = 0;

                //얼음없는 칸은 스킵
                if (A[i][j] == 0) continue;
                for (int k = 0; k < 4; k++) {
                    int nr = i + dir[k][0];
                    int nc = j + dir[k][1];

                    if (nr < 0 || nc < 0 || nr >= limit || nc >= limit) continue;
                    if (A[nr][nc] <= 0) continue;

                    cnt++;
                }
                //인접한 칸에 얼음이 남아 있는 곳이 3칸 미만이면 얼음 녹이기
                if (cnt < 3) temp[i][j]--;
            }
        }

        return temp;
    }

    static void findBiggest() {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[limit][limit];

        totalIce = 0;
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                if (A[i][j] <= 0) continue;
                if (visited[i][j]) continue;

                //방문처리
                visited[i][j] = true;
                queue.add(new int[]{i, j});
                totalIce += A[i][j];
                int local = 1;

                while (!queue.isEmpty()) {
                    int[] cur = queue.poll();
                    int r = cur[0];
                    int c = cur[1];

                    for (int k = 0; k < 4; k++) {
                        int nr = r + dir[k][0];
                        int nc = c + dir[k][1];

                        if (nr < 0 || nc < 0 || nr >= limit || nc >= limit) continue;
                        if (A[nr][nc] <= 0) continue;
                        if (visited[nr][nc]) continue;

                        local++;
                        visited[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                        totalIce += A[nr][nc];
                    }
                }

                max = Math.max(max, local);
            }
        }
    }
}