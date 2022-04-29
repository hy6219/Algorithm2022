package implementation.boj5373;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Remain {

    static class Dice {
        private char[][] up = new char[3][3];
        private char[][] down = new char[3][3];
        private char[][] front = new char[3][3];
        private char[][] back = new char[3][3];
        private char[][] left = new char[3][3];
        private char[][] right = new char[3][3];

        public void init() {
            for (int i = 0; i < 3; i++) {
                Arrays.fill(up[i], 'w');
                Arrays.fill(down[i], 'y');
                Arrays.fill(front[i], 'r');
                Arrays.fill(back[i], 'o');
                Arrays.fill(left[i], 'g');
                Arrays.fill(right[i], 'b');
            }
        }

        //반시계인지 시계인지

        /**
         * @param plane 면
         * @param dir   시계/반시계 방향
         */
        public void rotateDir(char plane, char dir) {
            if (dir == '+') {
                rotateClock(plane);
            } else {
                rotateAntiClock(plane);
            }
        }

        /**
         * 시계방향 회전
         *
         * @param plane
         */
        private void rotateClock(char plane) {
            if (plane == 'U') {
                char[] temp = {back[2][2], back[2][1], back[2][0]};

                up = rotateThisPlane(up);

                back[2][2] = left[0][0];
                back[2][1] = left[0][1];
                back[2][0] = left[0][2];

                left[0][0] = front[0][0];
                left[0][1] = front[0][1];
                left[0][2] = front[0][2];

                front[0][0] = right[0][0];
                front[0][1] = right[0][1];
                front[0][2] = right[0][2];

                right[0][0] = temp[0];
                right[0][1] = temp[1];
                right[0][2] = temp[2];
            } else if (plane == 'D') {
                char[] temp = {front[2][2], front[2][1], front[2][0]};

                down = rotateThisPlane(down);

                front[2][2] = left[2][2];
                front[2][1] = left[2][1];
                front[2][0] = left[2][0];

                left[2][2] = back[0][0];
                left[2][1] = back[0][1];
                left[2][0] = back[0][2];

                back[0][0] = right[2][2];
                back[0][1] = right[2][1];
                back[0][2] = right[2][0];

                right[2][2] = temp[0];
                right[2][1] = temp[1];
                right[2][0] = temp[2];
            } else if (plane == 'F') {
                char[] temp = {up[2][2], up[2][1], up[2][0]};

                /**
                 * 앞면도 돌아감
                 */
                front = rotateThisPlane(front);

                up[2][2] = left[0][2];
                up[2][1] = left[1][2];
                up[2][0] = left[2][2];

                left[0][2] = down[0][0];
                left[1][2] = down[0][1];
                left[2][2] = down[0][2];

                down[0][0] = right[2][0];
                down[0][1] = right[1][0];
                down[0][2] = right[0][0];

                right[2][0] = temp[0];
                right[1][0] = temp[1];
                right[0][0] = temp[2];
            } else if (plane == 'B') {
                char[] temp = {down[2][2], down[2][1], down[2][0]};

                /*
                 * 뒷면도 돌아감
                 */
                back = rotateThisPlane(back);

                down[2][2] = left[2][0];
                down[2][1] = left[1][0];
                down[2][0] = left[0][0];

                left[2][0] = up[0][0];
                left[1][0] = up[0][1];
                left[0][0] = up[0][2];

                up[0][0] = right[0][2];
                up[0][1] = right[1][2];
                up[0][2] = right[2][2];

                right[0][2] = temp[0];
                right[1][2] = temp[1];
                right[2][2] = temp[2];

            } else if (plane == 'L') {
                char[] temp = {back[0][0], back[1][0], back[2][0]};

                left = rotateThisPlane(left);

                back[0][0] = down[0][0];
                back[1][0] = down[1][0];
                back[2][0] = down[2][0];

                down[0][0] = front[0][0];
                down[1][0] = front[1][0];
                down[2][0] = front[2][0];

                front[0][0] = up[0][0];
                front[1][0] = up[1][0];
                front[2][0] = up[2][0];

                up[0][0] = temp[0];
                up[1][0] = temp[1];
                up[2][0] = temp[2];
            } else if (plane == 'R') {
                char[] temp = {up[0][2], up[1][2], up[2][2]};

                right = rotateThisPlane(right);

                up[0][2] = front[0][2];
                up[1][2] = front[1][2];
                up[2][2] = front[2][2];

                front[0][2] = down[0][2];
                front[1][2] = down[1][2];
                front[2][2] = down[2][2];

                down[0][2] = back[0][2];
                down[1][2] = back[1][2];
                down[2][2] = back[2][2];

                back[0][2] = temp[0];
                back[1][2] = temp[1];
                back[2][2] = temp[2];
            }
        }

        /**
         * 반시계 방향 회전 == 시계방향*3번 회전
         *
         * @param plane
         */
        private void rotateAntiClock(char plane) {
            for (int i = 0; i < 3; i++) {
                rotateClock(plane);
            }
        }

        public String getUpperPlane() {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(up[i][j]);
                }
                sb.append('\n');
            }
            return sb.toString();
        }

        /**
         * 해당 면 좌표 시계방향 90도 회전
         */
        static char[][] rotateThisPlane(char[][] arr) {

            char[][] temp = new char[3][3];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp[i][j] = arr[2 - j][i];
                }
            }

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    arr[r][c] = temp[r][c];
                }
            }

            return arr;
        }
    }

    static BufferedReader br;
    static StringTokenizer st;
    static int t;//테스트 케이스 갯수
    static int n;//큐브 돌린 횟수
    static Dice dice;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        t = Integer.parseInt(st.nextToken());

        dice = new Dice();
        sb = new StringBuilder();

        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());

            n = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());

            dice.init();
            for (int j = 0; j < n; j++) {
                String cmd = st.nextToken();
                dice.rotateDir(cmd.charAt(0), cmd.charAt(1));
            }

            //윗면 출력
            sb.append(dice.getUpperPlane());

        }
        System.out.print(sb);
    }
}
