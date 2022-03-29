package bfs.boj16234;

import java.util.*;

public class Main {

    static class Country {
        private int r;
        private int c;
        private int people;
        //연합 번호
        private int unionNo;

        public Country(int r, int c, int people) {
            this.r = r;
            this.c = c;
            this.people = people;
            this.unionNo = 0;
        }
    }

    static Scanner scanner;
    static int N, L, R;
    static int day;
    static Country[][] countries;
    static int[][] dir = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };
    static Queue<Country> queue;
    static boolean[][] visited;
    //연합 평균->이동
    static ArrayList<Integer> avg;
    static boolean isOpenAndChanged;

    static void input() {
        scanner = new Scanner(System.in);

        N = scanner.nextInt();
        L = scanner.nextInt();
        R = scanner.nextInt();
        queue = new LinkedList<>();
        avg = new ArrayList<>();

        countries = new Country[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                countries[i][j] = new Country(i,j, scanner.nextInt());
            }
        }
    }

    /**
     * 인구차이가 L<= <=R에 만족하는 국가가 있는지 확인
     */
    static boolean continueOrNot(Country c1, Country c2) {
        int standard = Math.abs(c1.people - c2.people);
        return standard >= L && standard <= R;
    }

    static void move() {

        while (true) {
            visited = new boolean[N][N];
            int union = 0;
            //국경선 오픈 후 변경 확인
            isOpenAndChanged = false;

            //모든 지점을 방문하면서 bfs
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (visited[i][j]) continue;
                    Country country = countries[i][j];
                    //방문표시
                    visited[i][j] = true;
                    //방문할 곳으로 등록
                    queue.add(country);
                    //연합들을 탐색
                    bfs(union++);
                }
            }

            //연합이 없었다면 == 국가간 이동이 없었다면 출력하고 종료
            if(!isOpenAndChanged){
                System.out.println(day);
                return;
            }

            //연합 평균값을 넣어주기
            for(int i = 0 ; i < N; i++){
                for(int j = 0 ; j < N; j++){
                    Country country = countries[i][j];

                    country.people = avg.get(country.unionNo);
                }
            }

            //다음 bfs를 위해 비워두기
            avg.clear();
            //날짜 증가
            day++;
        }
    }

    static void bfs(int union) {
        //연합 인구수 더하기
        int unionPeopleSum = 0;
        //연합갯수
        int unionCnt = 0;

        while (!queue.isEmpty()) {
            Country current = queue.poll();

            //연합번호 부여
            current.unionNo = union;
            //연합수 확인
            unionCnt++;
            //연합 인구 평균을 구하기 위해서 더하기
            unionPeopleSum += current.people;

            for (int i = 0; i < 4; i++) {
                int nr = current.r + dir[i][0];
                int nc = current.c + dir[i][1];

                if (nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
                if (visited[nr][nc]) continue;

                Country another = countries[nr][nc];

                if (!continueOrNot(current, another)) continue;

                visited[nr][nc] = true;
                queue.add(another);
                isOpenAndChanged = true;//국경선 오픈 후 변경한 것!
            }
        }

        //해당 연합의 인구수 계산해서 저장해두기
        avg.add(unionPeopleSum / unionCnt);
    }

    static void pro() {
        input();
        move();
    }

    public static void main(String[] args) {
        pro();
    }
}
