package bfs.boj17142;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static class Virus {
        private int r;
        private int c;
        private int time;

        public Virus(int r, int c, int time) {
            this.r = r;
            this.c = c;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Virus{" +
                    "r=" + r +
                    ", c=" + c +
                    ", time=" + time +
                    '}';
        }
    }

    static Scanner scanner;
    static int N, M;
    static int[][] map;
    static int[][] dir = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };
    static boolean isEmptyExist;
    static int MIN_TIME = Integer.MAX_VALUE;
    static LinkedList<Virus> virusList;
    static ArrayList<Integer> selected;
    static int[][] copyMap;
    static Queue<Virus> queue;
    static int curMax;

    static void input() {
        scanner = new Scanner(System.in);

        N = scanner.nextInt();
        M = scanner.nextInt();

        map = new int[N][N];
        copyMap = new int[N][N];

        queue = new LinkedList<>();
        virusList = new LinkedList<>();
        selected = new ArrayList<>();


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = scanner.nextInt();

                map[i][j] = num;

                if (num == 0) {
                    isEmptyExist  = true;
                }
                if (num == 2) {
                    virusList.add(new Virus(i, j, 3));
                }
            }
        }

    }

    static void dfs(int k, int idx) {

        //중복없이 M개 뽑을것
        if (k == M) {
            for(int i = 0; i < N; i++){
                copyMap[i] = map[i].clone();
            }
            curMax = 0;
            queue.clear();

            for(int i = 0; i < selected.size(); i++){
                Virus virus = virusList.get(selected.get(i));
                queue.add(new Virus(virus.r, virus.c, 3));//활성상태
                copyMap[virus.r][virus.c] = 3;//활성 상태 기록
            }
            bfs();

            boolean isBlankExist = false;

            Loop:
            for(int i = 0; i < N; i++){
                for(int j = 0 ; j < N; j++){
                    if(copyMap[i][j] == 0){
                        isBlankExist = true;
                        break Loop;
                    }
                }
            }

            if(!isBlankExist){
                MIN_TIME  = Math.min(MIN_TIME, curMax);
            }

            return;
        } else {
            for (int cand = idx; cand < virusList.size(); cand++) {

                selected.add(cand);
                dfs(k + 1, cand + 1);
                selected.remove(selected.size() - 1);
            }
        }
    }

    static void bfs() {
        while(!queue.isEmpty()){
            Virus v = queue.poll();

            for(int i = 0; i < 4; i++){
                int nr = v.r+dir[i][0];
                int nc = v.c+dir[i][1];
                int nt = v.time + 1;//초기 시간을 3으로 잡았기 때문에 nt-3을 비활성상태에서 확인해주어야 함

                if(nr<0 || nc<0 || nr>=N || nc>=N) continue;
                //벽
                if(copyMap[nr][nc] == 1) continue;
                //활성상태로 체크된 곳
                if(copyMap[nr][nc] > 2) continue;

                //비활성상태일 때에는 큐에 넣어주어도, 시간체크는 하지 않음
                if(copyMap[nr][nc] != 2){
                    if(curMax < nt-3) curMax = nt-3;
                }
                queue.add(new Virus(nr,nc,nt));
                copyMap[nr][nc] = nt;
            }
        }
    }

    static void pro() {
        input();

        dfs(0, 0);
        System.out.println(MIN_TIME == Integer.MAX_VALUE ? -1 : MIN_TIME);
    }

    public static void main(String[] args) {
        pro();
    }
}