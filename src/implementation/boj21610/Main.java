package implementation.boj21610;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Command {
        int di;
        int si;

        public Command() {
        }

        public Command(int di, int si) {
            this.di = di;
            this.si = si;
        }

        @Override
        public String toString() {
            return "Command{" +
                    "di=" + di +
                    ", si=" + si +
                    '}';
        }
    }

    static class Cloud{
        int r;
        int c;

        public Cloud(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static Scanner scanner;
    static int N, M;
    static int[][] plot;//물양
    static List<Cloud> clouds;
    static List<Command> commands;
    static boolean[][] visit;//구름이 제거된 위치 확인(true)
    static int[][] dir = {
            {0, -1},
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, 1},
            {1, 1},
            {1, 0},
            {1, -1}
    };

    static void input() {
        scanner = new Scanner(System.in);

        N = scanner.nextInt();
        M = scanner.nextInt();

        plot = new int[N][N];
        clouds = new LinkedList<>();
        commands = new LinkedList<>();
        visit =new boolean[N][N];
        /*
         *초기위치(왼쪽하단 모퉁이)
         */
        clouds.add(new Cloud(N-1,0));
        clouds.add(new Cloud(N-1,1));
        clouds.add(new Cloud(N-2,0));
        clouds.add(new Cloud(N-2,1));

        //물양 입력받기
        for(int i = 0 ; i < N; i++){
            for(int j = 0 ; j < N; j++){
                plot[i][j] = scanner.nextInt();
            }
        }

        //d,s입력받아 저장!
        for(int i = 0 ; i < M; i++){
            commands.add(new Command(scanner.nextInt(), scanner.nextInt()));
        }
    }

    static void work(){
        for(int i = 0; i < M; i++){
            int di = commands.get(i).di-1;
            int si = commands.get(i).si;
            visit = new boolean[N][N];
            //1.모든 구름이 di 방향으로 si 만큼 이동
            move(di,si);
            //2.각 구름이 있는 칸의 물 양을 1 증가
            rain();
            //4.물복사마법
            copyWater();
            //3.모든 구름 제거==>사실상 다른 작업도 해야해서 4다음에 실행!
            clouds.clear();
            //5.새롭게 구름 위치시키기(>=2)
            makeNewClouds();
        }
    }

    /**
     * 1.모든 구름이 di 방향으로 si 만큼 이동
     * @param d
     * @param s
     */
    static void move(int d, int s){
        int nr=0, nc =0;
        //길이가 N보다 크게될 경우도 존재 -> s=s%N==>a
        //다음위치=현재위치+방향*a
        for(int i = 0; i <clouds.size();i++){
            Cloud now = clouds.get(i);
            /*
             nr = (now.r +N+dir[d][0]*(s%N))%N;는
            https://zoosso.tistory.com/933 참조(PS: 연결되는 행렬)
            ==>1. 이동하는 칸 갯수는 N보다 클 수 있기 때문에 실제 이동은 (s 거리%N)만큼 이동
            2.왼쪽으로 이동한다던지,윗쪽으로 이동하면 음수가 발생될수 있어서 절댓값처리처럼
            N+"1"*방향(==>negative방향)을 진행해주고,2까지 진행된 결과를 N으로 나누어줄것
            (또 N으로 나누는 것은 보드크기를 넘어갈 경우를 막기 위함)

            -->예: N=10,(2,2)에서 좌측으로 5칸 이동
            ----->(2,7)로 이동-->열: (2+10+(5%10)*(-1))%10 =7

            ==>next=(current+N+dir*(이동할 거리%N))%N
             */
            nr = (now.r +N+dir[d][0]*(s%N))%N;
            nc = (now.c +N+dir[d][1]*(s%N))%N;

            //위치 이동시켜주기!
            now.r=nr;
            now.c=nc;
        }
    }

    //2.각 구름이 있는 칸의 물 양을 1 증가
    static void rain(){
        for(int i = 0 ; i <clouds.size();i++){
            Cloud now = clouds.get(i);
            int r = now.r;
            int c = now.c;

            plot[r][c]++;
            //해당 칸 구름 제거
            visit[r][c]=true;
        }
    }

    /**
     * 4.물복사마법
     */
    static void copyWater(){
        for(int i = 0; i < clouds.size();i++){
            Cloud now = clouds.get(i);
            int cnt =0;
            int nr = 0, nc =0;
            //대각선은 1,3,5,7에만 존재
            for(int di = 1; di < dir.length;di+=2){
                nr = now.r + dir[di][0];
                nc = now.c+dir[di][1];

                if(nr < 0 || nc <0 || nr >= N || nc >=N) continue;
                if(plot[nr][nc] <=0) continue;
                cnt++;
            }
            plot[now.r][now.c]+=cnt;
        }
    }

    /**
     * 5.새롭게 구름 위치시키기(>=2)
     */
    static void makeNewClouds(){
        for(int i = 0 ; i < N;i++){
            for(int j = 0 ; j <N; j++){
                if(plot[i][j] >= 2 && !visit[i][j]){
                    plot[i][j]-=2;
                    clouds.add(new Cloud(i,j));
                }
            }
        }
    }
    
    static int count(){
        int sum = 0;

        for(int i = 0 ; i < N; i++){
            for(int j = 0; j <N ;j++){
                sum+= plot[i][j];
            }
        }

        return sum;
    }

    static void pro(){
        input();
        work();
        System.out.println(count());
    }

    public static void main(String[] args) {
        pro();
    }
}
