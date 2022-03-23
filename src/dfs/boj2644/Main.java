package dfs.boj2644;

import java.util.*;

public class Main {

    static Scanner scanner;
    static int N;
    static int relA, relB;
    static int M;
    static List<Integer>[] map;
    static boolean[] visited;
    static int cnt = -1;//관계가 없는 경우에는 -1

    static void input() {
        scanner = new Scanner(System.in);

        N = scanner.nextInt();
        relA = scanner.nextInt();
        relB = scanner.nextInt();
        M = scanner.nextInt();

        map = new List[N + 1];
        visited = new boolean[N + 1];
        cnt = 0;

        for (int i = 1; i <= N; i++) {
            map[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int first = scanner.nextInt();
            int second = scanner.nextInt();

            //방향성이 없어서 방향배열로 엮어서 생각하기 어려워서 연결 리스트 선택
            map[first].add(second);
            map[second].add(first);
        }
    }

    static void dfs(int start, int dest, int point) {
        if (start == dest) {
            cnt = point;//갱신
            return;
        }

        visited[start] = true;

        //연결된 모든 곳들을 방문
        for (int i = 0; i < map[start].size(); i++) {
            int nx = map[start].get(i);

            if (visited[nx]) continue;

            dfs(nx, dest, point + 1);
        }
    }


    static void pro() {
        input();
        dfs(relA, relB, 0);
        cnt = cnt == 0 ? -1 : cnt;
        System.out.println(cnt);
    }

    public static void main(String[] args) {
        pro();
    }
}
