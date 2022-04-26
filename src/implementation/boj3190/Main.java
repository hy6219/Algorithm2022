package implementation.boj3190;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, k, l;
    static int[][] board;
    static char[] directions;
    static int[] time;
    static int[][] dir = {
            {0, 1},
            {-1, 0},
            {0, -1},
            {1, 0}
    };
    static Deque<int[]> snake;
    //현재시간
    static int curTime = 0;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        k = Integer.parseInt(st.nextToken());

        board = new int[n + 1][n + 1];

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            board[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = 5;//사과 존재
        }

        st = new StringTokenizer(br.readLine());

        l = Integer.parseInt(st.nextToken());

        time = new int[10001];
        directions = new char[10001];

        for (int i = 0; i < l; i++) {
            st = new StringTokenizer(br.readLine());

            time[i] = Integer.parseInt(st.nextToken());
            directions[i] = st.nextToken().charAt(0);
        }

        int turn = 0;
        snake = new LinkedList<>();

        //현재 뱀의 방향 - 초기에는 동쪽
        int d = 0;
        //뱀위치는 1로 마킹해두고 덱에도 뱀 기록해두기
        board[1][1] = 1;
        snake.addFirst(new int[]{1, 1});
        //현재 뱀의 위치
        int r = 1, c = 1;

        for (int i = 0; i <= 10001; i++) {
            if (turn < l && time[turn] == curTime) {
                //명령 시도!!
                d = rotateHead(d, directions[turn++]);
            }

            int nr = r + dir[d][0];
            int nc = c + dir[d][1];

            //머리 넣기
            snake.addFirst(new int[]{nr, nc});

            if (nr <= 0 || nc <= 0 || nr > n || nc > n) {
                curTime++;
                break;
            }

            /*
            사과가 있다면(5) 사과는 없어지고, 꼬리는 움직이지 않음

            사과가 없다면, 꼬리를 줄여주고

            자기자신과 부딪히면 게임종료
             */
            if (board[nr][nc] == 5) {
                //사과가 있는 경우
                board[nr][nc] = 1;//뱀이 지나가기만 한 것
            } else if (board[nr][nc] == 0) {
                //사과가 없고, 뱀도 없는 자리
                int[] tail = snake.pollLast();
                board[tail[0]][tail[1]] = 0;//꼬리 줄이기
                //뱀이 지나갔음
                board[nr][nc] = 1;
            } else if (board[nr][nc] == 1) {
                //자기 자신과 부딪히는 경우
                curTime++;
                break;
            }

            //r,c 좌표 갱신
            r = nr;
            c = nc;
            ++curTime;
        }

        System.out.println(curTime);
    }

    static int rotateHead(int curD, char leftOrRight) {
        if (leftOrRight == 'L') {
            return (curD + 1) % 4;
        } else {
            return (curD + 3) % 4;
        }
    }
}
