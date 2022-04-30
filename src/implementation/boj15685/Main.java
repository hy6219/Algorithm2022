package implementation.boj15685;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n;
    static int cnt;//다음단계는 전단계 변의 갯수만큼 이동
    static boolean[][] visit;
    /*
    0: x좌표가 증가하는 방향 (→)
1: y좌표가 감소하는 방향 (↑)
2: x좌표가 감소하는 방향 (←)
3: y좌표가 증가하는 방향 (↓)
     */
    static int[][] dir = {
            {0, 1},
            {-1, 0},
            {0, -1},
            {1, 0}
    };
    static int square;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        visit = new boolean[102][102];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());//방향
            int g = Integer.parseInt(st.nextToken());//세대
            //그 세대에서 반시계방향으로 90도 방향을 기억해두었다가 그려주어야 함
            draw(x, y, generationDir(d, g));
        }

        findSquare();
        System.out.println(square);
    }

    static void draw(int x, int y, List<Integer> directions) {
        visit[x][y] = true;

        for(int i = 0 ; i < directions.size(); i++){
            int d = directions.get(i);

            switch (d){
                case 0:
                    //오른쪽
                    visit[++x][y] = true;
                    break;
                case 1:
                    //위
                    visit[x][--y] = true;
                    break;
                case 2:
                    //왼쪽
                    visit[--x][y] = true;
                    break;
                case 3:
                    //오른쪽
                    visit[x][++y] = true;
                    break;
            }
        }
    }

    static List<Integer> generationDir(int d, int g) {
        //g세대->방향 g번 변경
        List<Integer> list = new ArrayList<>();
        list.add(d);

        while (g-- > 0) {
            //역순으로 넣어주어야 함(다음 세대를 위해서)
            for (int i = list.size() - 1; i >= 0; i--) {
                int nd = (list.get(i) + 1) % 4;
                list.add(nd);
            }
        }
        return list;
    }

    static void findSquare(){
        for(int i = 0 ; i < 101; i++){
            for(int j = 0 ; j < 101; j++){
                int curR = i;
                int curC = j;

               if(visit[curR][curC] && visit[curR+1][curC] && visit[curR+1][curC+1] && visit[curR][curC+1]) square++;
            }
        }
    }
}
