package implementation.boj20058;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, q;
    static int len;
    static int[][] arr;
    static boolean[][] visited;
    //l명령
    static ArrayList<Integer> list;
    //얼음합
    static int iceTot;
    static int[][] dir = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };
    static List<Integer> area;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        len = (int) Math.pow(2, n);
        arr = new int[len][len];
        list = new ArrayList<>();
        area = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < len; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                iceTot += arr[i][j];
            }
        }

        st = new StringTokenizer(br.readLine());
        list = new ArrayList<>();

        for (int i = 0; i < q; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < q; i++) {
            int stage = list.get(i);

            //1.영역별로 회전시키기
            int size = (int) Math.pow(2, stage);

            rotate(size);

            //2.얼음이 3개 이상인 칸과 인접하지 않은 칸은 얼음 1씩 녹이기
            meltIce();
        }

        //얼음 덩어리 가장 큰 곳 찾기
        makeGroup();
        System.out.println(iceTot);
        System.out.println(area.get(0));
    }

    static void rotate(int size) {
        int[][] map = new int[len][len];

        for (int i = 0; i < len; i += size) {
            for (int j = 0; j < len; j += size) {
                int sr = i;
                int sc = j;

                for (int c = j; c < j + size; c++) {
                    sc = j;
                    for (int r = i + size - 1; r >= i; r--) {
                        map[sr][sc++] = arr[r][c];
                    }
                    sr++;
                }
            }
        }

        for (int i = 0; i < len; i++) {
            arr[i] = map[i].clone();
        }
    }

    static void meltIce() {
        //인접한 칸들 중 얼음이 있는 칸이 3개 이상이 아닌 경우는 얼음양 1씩 줄이기
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (arr[i][j] == 0) continue;//얼음이 없으면 볼 필요 없음
                //인접칸 세기
                int adj = 0;

                for (int k = 0; k < 4; k++) {
                    int nr = i + dir[k][0];
                    int nc = j + dir[k][1];

                    if (nr < 0 || nc < 0 || nr >= len || nc >= len) continue;
                    if (arr[nr][nc] == 0) continue;
                    adj++;
                }

                //얼음 녹이기(얼음총합도 --)
                if (adj < 3) {
                    arr[i][j]--;
                    iceTot--;
                }
            }
        }
    }

    //얼음군집
    static void makeGroup() {
        visited = new boolean[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                //얼음이 없는 경우 스킵
                if (arr[i][j] == 0) continue;
                if (visited[i][j]) continue;
                bfs(i, j);
            }
        }

        //없는 경우대비
        area.add(0);
        //내림차순 정렬
        Collections.sort(area, Collections.reverseOrder());
    }

    static void bfs(int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{r, c});
        visited[r][c] = true;
        int cnt = 1;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nr = cur[0] + dir[i][0];
                int nc = cur[1] + dir[i][1];

                if (nr < 0 || nc < 0 || nr >= len || nc >= len) continue;
                if (visited[nr][nc]) continue;

                queue.add(new int[]{nr, nc});
                visited[nr][nc] = true;
                cnt++;
            }
        }

        area.add(cnt);
    }
}