package implementation.boj21610;

import java.util.Arrays;
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

    static Scanner scanner;
    static int N, M;
    static int[][] water;//물양
    static boolean[][] visit;//구름이 제거된 위치 확인(true)
    static boolean[][] cloud;//구름이 현재 위치한 곳 체크(true)
    static List<Command> commands;
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

        water = new int[N][N];
        visit = new boolean[N][N];
        cloud = new boolean[N][N];

        commands = new LinkedList<>();

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                //처음 구름위치 잡기
                boolean cloudChk = initCloudPosition(row, col);
                water[row][col] = scanner.nextInt();//물양
                cloud[row][col] = cloudChk;
            }
        }

        for (int cmd = 0; cmd < M; cmd++) {
            commands.add(new Command(scanner.nextInt(), scanner.nextInt()));
        }

    }

    static void doRepeatJob() {
        int turn = 0;
        while (turn < M) {
            doThisTurn(turn++);
        }
    }

    /**
     * M번의 이동이 모두 끝난 후 바구니에 들어있는 물의 양의 합 구하기
     *
     * @return
     */
    static int getTotalWater() {
        int sum = 0;

        System.out.println("water: " + Arrays.deepToString(water));
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                sum += water[row][col];
            }
        }

        return sum;
    }

    static boolean initCloudPosition(int row, int col) {
        if ((row == N - 1 || row == N - 2) && (col == 0 || col == 1)) return true;
        return false;
    }

    /**
     * 매회 반복
     *
     * @param turn
     */
    static void doThisTurn(int turn) {
        //현재 구름이 있는 칸에서 di방향으로 si만큼 이동
        //-구름이 있는 칸을 찾고, di 방향으로 이동
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (cloud[row][col] && !visit[row][col]) {
                    //row 혹은 col+commands.get(turn).di*si 이동(단, 0과 N-1은 연결됨)
                    directionMove(row, col, turn);
                }
            }
        }
        //현재 구름이 있는 칸에서 비내리기(b)
        List<int[]> plots = new LinkedList<>();//비 내리는 위치 기록
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (cloud[row][col] && !visit[row][col]) {
                    plots.add(new int[]{row, col});//좌표기록
                    ++water[row][col];
                    //구름 모두 제거
                    visit[row][col] = true;
                }
            }
        }
        //물복사마법 for b 좌표들
        for (int i = 0; i < plots.size(); i++) {
            int[] p = plots.get(i);
            //물복사마법 진행
            copyWaterMagic(p[0], p[1]);
        }
        //visit[][] == false && 물양 >=2 인 모든곳에 cloud[][] = true 처리,물양-2
        makeNewCloud();
    }

    /**
     * row 혹은 col+commands.get(turn).di*si 이동
     *
     * @param row
     * @param col
     * @param turn
     */
    static void directionMove(int row, int col, int turn) {
        Command next = commands.get(turn);
        //https://zoosso.tistory.com/933 - N*N 행렬탐색에서 끝과 끝이 연결되는 경우
        int nr = row + +N + (dir[next.di - 1][0] * (next.si % N));
        int nc = col + N + (dir[next.di - 1][1] * (next.si % N));

        if(nr<0) nr+=N;
        if(nc<0) nr+=N;
        if(nr>=N) nr%=N;
        if(nc>=N) nc%=N;

        //이동한 칸의 cloud[][]값 설정해주기
        cloud[nr][nc] = true;
        //이동한 칸의 물양 1증가
        ++water[nr][nc];
    }

    /**
     * 좌표 내에서 움직일 수 있는지 확인
     *
     * @param row
     * @param col
     * @return
     */
    static boolean rangeCheck(int row, int col) {
        return (row >= 0 && row < N && col >= 0 && col < N);
    }

    /**
     * 물복사마법은 dir배열의 1,3,5,7 인덱스를 활용
     *
     * @param row
     * @param col
     */
    static void copyWaterMagic(int row, int col) {
        int nr = 0;
        int nc = 0;
        int cnt = 0;

        for (int d = 1; d < dir.length; d += 2) {
            nr = row + dir[d][0];
            nc = col + dir[d][1];

            if (!rangeCheck(nr, nc)) continue;//범위에 맞지 않음!!(좌표)<-이때는 이동과 다르게 경계를 넘어가는 칸은 대각선 방향으로 거리가 1인 칸이 아니다.
            if (water[nr][nc] <= 0) continue;
            //해당칸 물양 1증가
            cnt++;
        }
        water[row][col] += cnt;
    }

    /**
     * visit[][] == false && 물양 >=2 인 모든곳에 cloud[][] = true 처리&&
     * 물양-2
     */
    static void makeNewCloud() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (!visit[row][col] && water[row][col] >= 2) {
                    cloud[row][col] = true;
                    water[row][col] -= 2;
                }
            }
        }
    }

    static void pro() {
        input();
        doRepeatJob();
        //M번의 이동이 모두 끝난 후 바구니에 들어있는 물의 양의 합 구하기
        System.out.println(getTotalWater());
    }

    public static void main(String[] args) {
        pro();
    }
}
