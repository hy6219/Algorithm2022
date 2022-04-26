package implementation.boj14503;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, m;
    //로봇청소기의 좌표, 방향 d:0 북 , 1 동, 2 남, 3 서
    static int r, c, d;
    //빈칸,벽
    static int[][] room;
    static int[][] dir = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };
    static int cnt = 0;//청소 영역


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        room = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                room[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //청소확인, 청소
        dfs(r, c, d);

        System.out.println(cnt);
    }

    static void dfs(int r, int c, int d) {
        boolean isCleaned = false;
        //방향 저장
        int tempDir = d;

        //현재 위치 청소
        if (room[r][c] == 0) {
            room[r][c] = 3;//청소 구분
            cnt++;
        }

        for (int i = 0; i < 4; i++) {
            //왼쪽으로 회전
            int nd = (d + 3) % 4;
            int nr = r + dir[nd][0];
            int nc = c + dir[nd][1];

            if (nr >= 0 && nc >= 0 && nr < n && nc < m) {
                //청소가 되어있었거나 벽이라면 스킵
                if (room[nr][nc] == 0) {
                    dfs(nr, nc, nd);
                    isCleaned = true;
                    return;
                }
            }

            d = (d + 3) % 4;
        }

        //네 방향 모두 청소된 경우, 후진
        if (!isCleaned) {
            //<->180 도 방향으로 전진
            int nd = (tempDir + 2) % 4;
            int nr = r + dir[nd][0];
            int nc = c + dir[nd][1];

            if (nr >= 0 && nc >= 0 && nr < n && nc < m) {
                if (room[nr][nc] != 1) {
                    //벽이 아니면 후진
                    dfs(nr, nc, tempDir);
                }
            }
        }
    }

}
