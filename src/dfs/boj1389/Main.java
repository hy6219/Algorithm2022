package dfs.boj1389;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    /**
     * @author gs813
     * 1.N입력
     * 2.arr[i][j]<-크기 N+1 N+1 : 1이면 연결, 0이면 연결x
     * 3.bfs 탐색(최단경로)
     */

    static Scanner scan;
    /**
     * N : 유저 수
     * M : 친구 관계 수
     */
    static int N, M;
    //연결관계
    static int[][] arr;
    //최단거리
    static int[][] distance;

    //입력받기
    static void input() {
        scan = new Scanner(System.in);
        N = scan.nextInt();
        M = scan.nextInt();

        arr = new int[N + 1][N + 1];
        distance = new int[N + 1][N + 1];

        //연결 정보 입력
        for (int i = 0; i < M; i++) {
            int a = scan.nextInt();
            int b = scan.nextInt();

            arr[a][b] = arr[b][a] = 1;
        }

    }


    //각 포인트별 최단거리 계산, 저장
    static void shortDistanceCalc() {
        for (int i = 1; i <= N; i++) {
            //각 포인트별 계산 진행
            bfs(i);//기준점 변경해가면서 계산
        }
    }

    static void bfs(int start) {
        boolean[] visit = new boolean[N + 1];

        Queue<Integer> queue = new LinkedList<>();
        //연결되는 모든 시작점을 등록
        for (int i = 1; i <= N; i++) {
            if (arr[start][i] == 1) {
                visit[i] = true;
                distance[start][i] = 1;
                queue.add(i);
            }
        }

        //탐색시작
        while (!queue.isEmpty()) {
            //하나 뽑기
            int current = queue.poll();

            //1~N까지 돌아다니면서
            //start 지점이 아닌 경우 연결가짓수 고려
            for (int i = 1; i <= N; i++) {
                if (arr[current][i] == 0) continue;//연결x
                if (visit[i]) continue;//방문x

                queue.add(i);
                distance[start][i] = distance[start][current] + 1;//(start,i)=이전에 (start,a),...,(a,b),...,(b,current)이므로
                //이전에 존재하던 연결정보에 누적되는 것
                visit[i] = true;
            }
        }

    }

    //케빈수 중 가장 작은 값일때 그 지점 알아보기
    static int getKelvinValue() {
        int min = Integer.MAX_VALUE;
        int ord = 0;
        for (int i = 1; i <= N; i++) {
            //누적
            int acc = 0;
            //누적합 구하기
            for (int j = 1; j <= N; j++) {
                acc += distance[i][j];
            }
            //최솟값 갱신
            if(acc < min){
                min = Math.min(min,acc);
                ord = i;
            }
        }
        return ord;
    }

    static void pro() {
        //입력받기
        input();
        //최단거리계산
        shortDistanceCalc();
        //최소 켈빈값을 갖는 사람 출력
        int minKelvin = getKelvinValue();
        System.out.println(minKelvin);
    }

    public static void main(String[] args) {
        pro();
    }
}
