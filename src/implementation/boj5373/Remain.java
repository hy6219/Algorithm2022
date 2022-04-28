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
            for(int i = 0; i < 3; i++){
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

            char[][] temp = new char[3][3];

            if (plane == 'F') {
                //앞면 회전
                for (int i = 0; i < 3; i++) {
                    temp[i] = up[i].clone();
                }

                for (int i = 0; i < 3; i++) {
                    up[2][i] = left[2-i][2];
                    left[2-i][2] = down[0][2-i];
                    down[0][2-i] = right[i][0];
                    right[i][0] = temp[2][i];
                }
            } else if (plane == 'B') {
                //뒷면 회전
                for (int i = 0; i < 3; i++) {
                    temp[i] = up[i].clone();
                }

                for (int i = 0; i < 3; i++) {
                    up[0][i] = right[i][2];
                    right[i][2] = down[2][2-i];
                    down[2][2-i] = left[2-i][0];
                    left[2-i][0] = temp[0][i];
                }
            } else if (plane == 'R') {
                //우쪽 회전
                for (int i = 0; i < 3; i++) {
                    temp[i] = up[i].clone();
                }

                for (int i = 0; i < 3; i++) {
                    up[i][2] = front[i][2];
                    front[i][2] = down[i][2];
                    down[i][2] = back[2-i][0];
                    back[2-i][0] = temp[i][2];
                }
            } else if (plane == 'L') {
                //왼쪽 회전
                for (int i = 0; i < 3; i++) {
                    temp[i] = up[i].clone();
                }

                for (int i = 0; i < 3; i++) {
                    up[i][0] = back[2 - i][2];
                    back[2 - i][2] = down[i][0];
                    down[i][0] = front[i][0];
                    front[i][0] = temp[i][0];
                }
            } else if (plane == 'U') {
                //윗면 회전
                for (int i = 0; i < 3; i++) {
                    temp[i] = front[i].clone();
                }

                for (int i = 0; i < 3; i++) {
                    front[0][i] = right[0][i];
                    right[0][i] = back[0][i];
                    back[0][i] = left[0][i];
                    left[0][i] = temp[0][i];
                }
            } else {
                //밑면 회전
                for (int i = 0; i < 3; i++) {
                    temp[i] = front[i].clone();
                }

                for (int i = 0; i < 3; i++) {
                    front[2][i] = left[2][i];
                    left[2][i] = back[2][i];
                    back[2][i] = right[2][i];
                    right[2][i] = temp[2][i];
                }
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

        public String getUpperPlane(){
            StringBuilder sb = new StringBuilder();

            for(int i = 0 ; i < 3; i++){
                for(int j = 0 ; j < 3; j++){
                    sb.append(up[i][j]);
                }
                sb.append('\n');
            }
            return sb.toString();
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
