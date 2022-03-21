package implementation.boj23288;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 초기 주사위 상태
 * ->윗면: 1
 * 왼쪽:4
 * 오른쪽:3
 * 앞면:5
 * 뒷면:2
 * 밑면:6
 */
public class Main {

    static class Dice implements Cloneable {
        private int up;//윗면
        private int down;//밑면
        private int left;//왼쪽
        private int right;//오른쪽
        private int front;//앞면
        private int back;//뒷면
        private int curDir;//현재 이동방향 0:북 1:동 2:남 3:서 (시계방향으로 인덱스 생각했음)
        //초기 주사위 위치
        private int r;
        private int c;
        //주사위 점수
        private int score;

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getUp() {
            return up;
        }

        public void setUp(int up) {
            this.up = up;
        }

        public int getDown() {
            return down;
        }

        public void setDown(int down) {
            this.down = down;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public int getFront() {
            return front;
        }

        public void setFront(int front) {
            this.front = front;
        }

        public int getBack() {
            return back;
        }

        public void setBack(int back) {
            this.back = back;
        }

        public int getCurDir() {
            return curDir;
        }

        public void setCurDir(int curDir) {
            this.curDir = curDir;
        }

        public void init() {
            this.up = 1;
            this.down = 6;
            this.left = 4;
            this.right = 3;
            this.front = 5;
            this.back = 2;
            this.curDir = 1;
            this.r = 1;
            this.c = 1;
        }

        /**
         * 시계 방향 이동 - 북 0 -> 동1
         * 동1->남2
         * 남2->서3
         * 서3->북0
         */
        public void clockDir() {
            this.curDir = (this.curDir + 1) % 4;
        }

        /**
         * 반시계 방향 이동 - 북 0 -> 서3
         * 동1->북0
         * 남2->동1
         * 서3->남2
         */
        public void clockReverseDir() {
            this.curDir = (this.curDir + 3) % 4;
        }

        /**
         * =>동서쪽으로 회전할 때에는 앞/뒷면은 변경없음
         * <p>
         * =>북/남쪽 회전은 왼쪽/오른쪽이 변경되지 않음
         */
        public void rotateDice() throws CloneNotSupportedException {
            int dir = this.getCurDir();

            Dice copied = (Dice) this.clone();
            if (dir == 1) {
                //동
                //앞뒤x
                /**
                 * 1) 동쪽으로 회전
                 * 윗면: 1 -> 오른쪽
                 * 왼쪽:4 -> 윗쪽
                 * 오른쪽:3 ->밑면
                 * 앞면:5 -> 앞면
                 * 뒷면:2 -> 뒷면
                 * 밑면:6 -> 왼쪽
                 */

                this.setUp(copied.getLeft());
                this.setDown(copied.getRight());
                this.setLeft(copied.getDown());
                this.setRight(copied.getUp());
            } else if (dir == 3) {
                //서
                //앞뒤x
                /**
                 * 2) 서쪽으로 회전
                 * 윗면: 1 -> 왼쪽
                 * 왼쪽:4 -> 밑면
                 * 오른쪽:3 ->윗면
                 * 앞면:5 -> 앞면
                 * 뒷면:2 -> 뒷면
                 * 밑면:6 -> 오른쪽
                 */
                this.setUp(copied.getRight());
                this.setDown(copied.getLeft());
                this.setLeft(copied.getUp());
                this.setRight(copied.getDown());
            } else if (dir == 0) {
                /**
                 * 4) 북쪽으로 회전
                 * 윗면: 1 -> 뒷면
                 * 왼쪽:4 -> 왼쪽
                 * 오른쪽:3 ->오른쪽
                 * 앞면:5 -> 윗면
                 * 뒷면:2 -> 밑면
                 * 밑면:6 -> 앞면
                 */
                //왼오x
                this.setUp(copied.getFront());
                this.setDown(copied.getBack());
                this.setFront(copied.getDown());
                this.setBack(copied.getUp());
            } else {
                /*
                 * 3) 남쪽으로 회전
                 * 윗면: 1 -> 앞면
                 * 왼쪽:4 -> 왼쪽
                 * 오른쪽:3 ->오른쪽
                 * 앞면:5 -> 밑면
                 * 뒷면:2 -> 윗면
                 * 밑면:6 -> 뒷쪽
                 */
                //왼오x
                this.setUp(copied.getBack());
                this.setDown(copied.getFront());
                this.setFront(copied.getUp());
                this.setBack(copied.getDown());
            }
        }

        //점수 더하기
        public void addScore(int score) {
            this.setScore(this.getScore() + score);
        }

    }

    static Scanner scanner;
    static int N, M, K;
    static int[][] map;
    static Dice dice;
    static int[][] dir = {
            //북,동,남,서
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    static void input() {
        scanner = new Scanner(System.in);
        N = scanner.nextInt();
        M = scanner.nextInt();
        K = scanner.nextInt();
        dice = new Dice();

        map = new int[N + 1][M + 1];

        dice.init();//주사위 초기화

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                map[i][j] = scanner.nextInt();
            }
        }
    }

    static void rotateAndMove() throws CloneNotSupportedException {
        int ord = 0;
        int curR;
        int curC;
        int direction;

        while (ord < K) {
            //1.주사위가 이동 방향으로 한 칸 굴러간다.
            // 만약, 이동 방향에 칸이 없다면, 이동 방향을 반대로 한 다음 한 칸 굴러간다.
            curR = dice.getR();
            curC = dice.getC();
            direction = dice.getCurDir();

            int nr = curR + dir[direction][0];
            int nc = curC + dir[direction][1];

            if (nr <= 0 || nc <= 0 || nr > N || nc > M) {
                direction = (direction + 2) % 4;
                nr = curR + dir[direction][0];
                nc = curC + dir[direction][1];
            }

            curR = nr;
            curC = nc;

            //한칸 굴러가기
            dice.setR(curR);
            dice.setC(curC);
            dice.setCurDir(direction);
            dice.rotateDice();
            //2.주사위가 도착한 칸 (x, y)에 대한 점수를 획득
            /*
            칸 (x, y)에 대한 점수는 다음과 같이 구할 수 있다. (x, y)에 있는 정수를 B라고 했을때, (x, y)에서
             동서남북 방향으로 연속해서 이동할 수 있는 칸의 수 C를 모두 구한다.
             이때 이동할 수 있는 칸에는 모두 정수 B가 있어야 한다. 여기서 점수는 B와 C를 곱한 값이다.
             */
            dice.addScore(getRoomScore(nr, nc));

            //3.주사위의 아랫면에 있는 정수 A와 주사위가 있는 칸 (x, y)에 있는 정수 B를 비교해 이동 방향을 결정
            /**
             * A > B인 경우 이동 방향을 90도 시계 방향으로 회전시킨다.
             * A < B인 경우 이동 방향을 90도 반시계 방향으로 회전시킨다.
             * A = B인 경우 이동 방향에 변화는 없다.
             */
            //a
            int a = dice.getDown();
            //b
            int b = map[curR][curC];

            if (a > b) {
                //시계방향 회전
                dice.clockDir();
            } else if (a < b) {
                //반시계방향 회전
                dice.clockReverseDir();
            }
            ord++;
        }

        //점수 구하기
        System.out.println(dice.getScore());
    }

    public static int getRoomScore(int r, int c) {
        int score = 1;
        int comp = map[r][c];
        boolean[][] visited = new boolean[N + 1][M + 1];
        Queue<int[]> queue = new LinkedList<>();

        visited[r][c] = true;
        queue.add(new int[]{r, c});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int rr = point[0];
            int cc = point[1];

            for (int i = 0; i < 4; i++) {
                int nr = rr + dir[i][0];
                int nc = cc + dir[i][1];

                if (nr <= 0 || nc <= 0 || nr > N || nc > M) continue;
                if (visited[nr][nc]) continue;
                if (map[nr][nc] != comp) continue;

                visited[nr][nc] = true;
                queue.add(new int[]{nr, nc});
                score++;
            }
        }

        return score * comp;//c * b
    }

    static void pro() throws CloneNotSupportedException {
        input();
        rotateAndMove();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        pro();
    }
}
