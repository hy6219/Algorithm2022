package bfs.boj16234;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Remain {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, l, r;
    static Country[][] countries;
    //인구이동 일수
    static int day;
    //국경선
    static boolean isOpen;
    static Queue<Country> queue;
    static boolean[][] visited;
    static int[][] dir = {
            {-1, 0},
            {1, 0},
            {0, 1},
            {0, -1}
    };
    //연합 인구수 기록
    static List<Integer> unionAvg;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        countries = new Country[n][n];
        unionAvg = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                countries[i][j] = new Country(i, j, Integer.parseInt(st.nextToken()));
            }
        }

        queue = new LinkedList<>();

        while (true) {
            visited = new boolean[n][n];
            isOpen = false;//처음에는 국경선 닫아두기
            //연합별 진행
            int union = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (visited[i][j]) continue;
                    //bfs진행
                    queue.add(countries[i][j]);
                    visited[i][j] = true;
                    bfs(union++);
                }
            }

            //국경이 닫힌 경우
            if(!isOpen){
                System.out.println(day);
                return;
            }

            //연합을 이룬 인구수는 연합 평균 인구수로 변경됨
            //연합을 이루고 있는 각 칸의 인구수는 (연합의 인구수) / (연합을 이루고 있는 칸의 개수)가 된다. 편의상 소수점은 버린다.
            for(int i = 0 ; i < n; i++){
                for(int j = 0 ; j < n ; j++){
                    Country cur = countries[i][j];
                    cur.people = unionAvg.get(cur.union);
                }
            }
            unionAvg.clear();
            day++;
        }
    }

    static void bfs(int union) {

        //연합인구수
        int people = 0;
        //연합수
        int unionCnt = 0;

        while (!queue.isEmpty()) {
            Country cur = queue.poll();
            //연합번호부여
            cur.union = union;
            unionCnt++;//연합수 세기
            //연합인구수 카운트
            people += cur.people;

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dir[i][0];
                int nc = cur.c + dir[i][1];

                if (nr < 0 || nc < 0 || nr >= n || nc >= n) continue;
                if (visited[nr][nc]) continue;

                //국경선을 공유하는 두 나라의 인구 차이가 L명 이상, R명 이하 인 경우 국경선을 열음
                Country another = countries[nr][nc];

                if (!isPassedDiff(cur.people, another.people)) continue;

                queue.add(another);
                visited[nr][nc] = true;
                //국경선 열기(인구 조건도 만족)
                isOpen = true;
            }
        }
        unionAvg.add(people / unionCnt);
    }

    static boolean isPassedDiff(int p1, int p2) {
        int diff = Math.abs(p1 - p2);
        return (diff >= l && diff <= r);
    }

    static class Country {
        private int r;
        private int c;
        private int people;
        //연합
        private int union;

        public Country(int r, int c, int people) {
            this.r = r;
            this.c = c;
            this.people = people;
            this.union = 0;
        }
    }
}
