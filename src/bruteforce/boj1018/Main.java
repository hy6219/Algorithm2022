package bruteforce.boj1018;

import java.util.Scanner;

public class Main {
    /**
     * 1. W->T, B->F
     * 2.시작점을
     * - 행: [0,N-7]
     * - 열: [0,M-7]
     * 에서 뽑기
     * 3. 끝점=시작점+8
     * 4. 이웃하는 열과 행에서 색바꿔가면서 칠해주고, 이때 예상되는 컬러값이 아니면 변경수 증가
     * 5. 최소 변경수 갱신
     */

    static Scanner scan;
    static int M, N;
    static int min = Integer.MAX_VALUE;
    static boolean[][] arr;

    static void input() {
        scan = new Scanner(System.in);

        N = scan.nextInt();
        M = scan.nextInt();

        scan.nextLine();//개행문자
        arr = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String line = scan.nextLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j) == 'W' ? true : false;
            }
        }
    }

    static void brute_force() {
        for (int i = 0; i < N - 7; i++) {
            for (int j = 0; j < M - 7; j++) {
                paint(i, j);
            }
        }
        System.out.println(min);
    }

    /**
     * @param sr 시작행
     * @param sc 시작열
     */
    static void paint(int sr, int sc) {
        /**
         * er 끝행
         * ec 끝열
         */
        int er = sr + 8;
        int ec = sc + 8;
        int cnt = 0;
        //시작점에서의 색상 저장
        boolean STC = arr[sr][sc];

        for (int i = sr; i < er; i++) {
            for (int j = sc; j < ec; j++) {
                //올바른 컬러가 아니면 바꿔 칠하는 횟수 증가
                if (arr[i][j] != STC) {
                    cnt++;
                }
                //다음칸 색 변경
                STC = !STC;
            }
            //개행으로 인한 색 변경
            STC = !STC;
        }

        //(첫칸기준으로 색변경 가짓수, 첫칸과 반대로 진행했을 때의 색변경 가짓수) 중 최솟값 찾기
        cnt = Math.min(cnt, 64 - cnt);
        //min값 갱신
        min = Math.min(min, cnt);
    }

    static void pro() {
        input();
        brute_force();
    }

    public static void main(String[] args) {
        pro();
    }
}
