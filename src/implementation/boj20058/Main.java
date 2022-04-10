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

            for (int j = 0; j < len; j += size) {
                for (int k = 0; k < len; k += size) {
                    rotate(j, k, size);
                }
            }

            //2.얼음이 3개 이상인 칸과 인접하지 않은 칸은 얼음 1씩 녹이기
            meltIce();
        }

        //얼음 덩어리 가장 큰 곳 찾기
        makeGroup();
        System.out.println(iceTot);
        System.out.println(area.get(0));
    }

    static void rotate(int x, int y, int size) {

        int number = size / 2;

        for (int n = 0; n < number; n++) {
            int width = size - 2 * n;
            int sx = x + n;
            int sy = y + n;
            int ex = x + size - n - 1;
            int ey = y + size - n - 1;
            int[] temp = new int[width - 1];

            int idx = 0;
            //우측 복사
            for (int i = sx + 1; i <= ex; i++) {
                temp[idx++] = arr[i][ey];
            }
            idx = sy + 1;
            //윗변->우측
            for (int i = sx + 1; i <= ex; i++) {
                arr[i][ey] = arr[sx][idx++];
            }
            idx = ex - 1;
            //왼쪽->윗측
            for (int i = sy + 1; i <= ey; i++) {
                arr[sx][i] = arr[idx--][sy];
            }
            idx = sy;
            //아랫측->왼측
            for (int i = sx; i < ex; i++) {
                arr[i][sy] = arr[ex][idx++];
            }

            idx = temp.length - 1;
            //우측->아랫변
            for (int i = sy; i < ey; i++) {
                arr[ex][i] = temp[idx--];
            }
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

        area.add(0);//덩어리가 없는 경우를 위함
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (arr[i][j] == 0 || visited[i][j]) continue;
                bfs(i, j);
            }
        }

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