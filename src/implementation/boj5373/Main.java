package implementation.boj5373;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static class Cube {
        //윗면
        private char[][] up = new char[3][3];
        //밑면
        private char[][] down = new char[3][3];
        //왼쪽
        private char[][] left = new char[3][3];
        //오른쪽
        private char[][] right = new char[3][3];
        //앞면
        private char[][] front = new char[3][3];
        //뒷면
        private char[][] back = new char[3][3];

        public Cube() {
            //3행
            for (int i = 0; i < 3; i++) {
                //윗면은 초기에 흰색
                Arrays.fill(up[i], 'w');
                //아랫면은 노란색
                Arrays.fill(down[i], 'y');
                //앞면은 빨간색
                Arrays.fill(front[i], 'r');
                //뒷면은 오렌지색
                Arrays.fill(back[i], 'o');
                //왼쪽은 초록색
                Arrays.fill(left[i], 'g');
                //오른쪽은 파란색
                Arrays.fill(right[i], 'b');
            }

        }

        public void turn(char plane, char clockOrReverse) {
            if (clockOrReverse == '+') {
                //시계방향
                turnClock(plane);
            } else {
                //시계반대방향
                turnReverse(plane);
            }
        }

        //시계방향으로 돌리기
        public void turnClock(char plane) {
            /*(윗면)
            윗면과 인접한 왼쪽, 앞쪽, 뒷쪽, 오른쪽이 돌아가게 됨
            (시계)- 앞쪽->왼쪽, 오른쪽->뒷쪽, 뒷쪽->오른쪽, 왼쪽->뒷쪽
            -윗면:
            (0,0) (0,1) (0,2)     (2,0) (1,0) (0,0)
            (1,0) (1,1) (1,2) ➡  (2,1) (1,1) (0,1)
            (2,0) (2,1) (2,2)     (2,2) (1,2) (0,2)
             */
            if (plane == 'U') {
                char[][] temp = new char[3][3];

                //윗면 위치 변경
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        temp[i][j] = up[2-j][i];
                    }
                }

                for(int i = 0 ; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        up[i][j] = temp[i][j];
                    }
                }

                //앞쪽->왼쪽, 오른쪽->뒷쪽, 뒷쪽->오른쪽, 왼쪽->뒷쪽
                char[] temp2 = new char[3];
                for(int i = 0 ; i < 3; i++){
                    temp2[i] = front[0][i];
                }

                for (int i = 0; i < 3; i++) {
                    front[0][i] = right[0][i];
                    right[0][i] = back[0][i];
                    back[0][i] = left[0][i];
                    left[0][i] = temp2[i];
                }

            } else if (plane == 'D') {
                /*(아랫면)
            아랫면과 인접한 왼쪽, 앞쪽, 뒷쪽, 오른쪽이 돌아가게 됨
            (시계)- 앞쪽->오른쪽, 오른쪽->뒷쪽, 뒷쪽->왼쪽, 왼쪽->앞쪽
            -아랫면:
            (0,0) (0,1) (0,2)     (2,0) (1,0) (0,0)
            (1,0) (1,1) (1,2) ➡  (2,1) (1,1) (0,1)
            (2,0) (2,1) (2,2)     (2,2) (1,2) (0,2)
             */
                char[][] temp = new char[3][3];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        temp[i][j] = down[2-j][i];
                    }
                }

                for(int i = 0 ; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        down[i][j] = temp[i][j];
                    }
                }


                //앞쪽->오른쪽, 오른쪽->뒷쪽, 뒷쪽->왼쪽, 왼쪽->앞쪽
                char[] temp2 = new char[3];
                for(int i = 0 ; i < 3; i++){
                    temp2[i] = front[2][i];
                }

                for (int i = 0; i < 3; i++) {
                    front[2][i] = left[2][i];
                    left[2][i] = back[2][i];
                    back[2][i] = right[2][i];
                    right[2][i] = temp2[i];
                }

            } else if (plane == 'F') {
                    /*(앞면)
            앞면과 인접한 왼쪽, 윗쪽, 아랫쪽, 오른쪽이 돌아가게 됨
            (시계)- 왼쪽->윗쪽, 오른쪽->밑쪽, 윗쪽->오른쪽, 아랫쪽->왼쪽
            -앞면:
            (0,0) (0,1) (0,2)     (2,0) (1,0) (0,0)
            (1,0) (1,1) (1,2) ➡  (2,1) (1,1) (0,1)
            (2,0) (2,1) (2,2)     (2,2) (1,2) (0,2)
             */
                char[][] temp = new char[3][3];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        temp[i][j] = front[2-j][i];
                    }
                }

                for(int i = 0 ; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        front[i][j] = temp[i][j];
                    }
                }

                //왼쪽->윗쪽, 오른쪽->밑쪽, 윗쪽->오른쪽, 아랫쪽->왼쪽
                char[] temp2 = new char[3];
                for(int i = 0 ; i < 3; i++){
                    temp2[i] = up[2][i];
                }

                for (int i = 0; i < 3; i++) {
                    up[2][i] = left[2-i][2];
                    left[2-i][2] = down[0][2-i];
                    down[0][2-i] = right[i][0];
                    right[i][0] = temp2[i];
                }

            } else if (plane == 'B') {
                  /*(뒷면)
            뒷면과 인접한 윗쪽, 아랫쪽, 왼쪽, 오른쪽이 돌아가게 됨
            (시계)- 윗쪽 ➡ 왼쪽, 아랫쪽➡오른쪽, 왼쪽➡아랫쪽, 오른쪽➡윗쪽
            -뒷면:
            (0,0) (0,1) (0,2)     (2,0) (1,0) (0,0)
            (1,0) (1,1) (1,2) ➡  (2,1) (1,1) (0,1)
            (2,0) (2,1) (2,2)     (2,2) (1,2) (0,2)
             */
                char[][] temp = new char[3][3];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        temp[i][j] = back[2-j][i];
                    }
                }

                for(int i = 0 ; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        back[i][j] = temp[i][j];
                    }
                }


                //윗쪽 ➡ 왼쪽, 아랫쪽➡오른쪽, 왼쪽➡아랫쪽, 오른쪽➡윗쪽
                char[] temp2 = new char[3];
                for(int i = 0 ; i < 3; i++){
                    temp2[i] = up[0][i];
                }

                for (int i = 0; i < 3; i++) {
                    up[0][i] = right[i][2];//right(0,2)->up(0,2), right(1,2)->up(0,1), right(2,2)->up(0,0)
                    right[i][2] = down[2][2 - i];//down(2,0)->right(0,2), down(2,1)->right(1,2)
                    down[2][2-i] = left[2-i][0];//left(0,0)->down(2,0), left(1,0)->down(2,1), left(2,0)->down(2,2)
                    left[2-i][0] = temp2[i];//up(0,0)->left(2,0), up(0,1)->left(1,0), up(0,2)->left(0,0)
                }
            }else if(plane == 'L'){
                 /*(왼쪽면)
            뒷면과 인접한 윗쪽, 아랫쪽, 뒷면,앞면이 돌아가게 됨
            (시계)- 윗쪽 ➡ 앞쪽, 아랫쪽➡뒷쪽, 뒷쪽➡윗쪽, 앞쪽➡아랫쪽
            -뒷면:
            (0,0) (0,1) (0,2)     (2,0) (1,0) (0,0)
            (1,0) (1,1) (1,2) ➡  (2,1) (1,1) (0,1)
            (2,0) (2,1) (2,2)     (2,2) (1,2) (0,2)
             */
                char[][] temp = new char[3][3];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        temp[i][j] = left[2-j][i];
                    }
                }

                for(int i = 0 ; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        left[i][j] = temp[i][j];
                    }
                }


                //윗쪽 ➡ 앞쪽, 앞쪽➡아랫쪽, 아랫쪽➡뒷쪽, 뒷쪽➡윗쪽,
                char[] temp2 = new char[3];
                for(int i = 0 ; i < 3; i++){
                    temp2[i] = up[i][0];
                }

                for(int i = 0 ; i < 3; i++){
                    //back(0,2)->up(2,0), back(1,2)->up(1,0), back(2,2)->up(0,0)
                    up[i][0] = back[2-i][2];
                    //down(0,0)->back(2,2), down(1,0)->back(1,2), down(2,0)->back(0,2)
                    back[2-i][2] = down[i][0];
                    //front(0,0)->down(2,0), front(1,0)->down(1,0) , front(2,0)->down(0,0)
                    down[i][0] = front[i][0];
                    //up(0,0)->front(0,0), up(1,0)->front(1,0), up(2,0)->front(2,0)
                    front[i][0] = temp2[i];

                }

            }else if(plane == 'R'){
                /*(오른쪽면)
            오른쪽면과 인접한 윗쪽, 아랫쪽, 앞쪽, 뒷쪽이 돌아가게 됨
            (시계)- 윗쪽 ➡ 뒷쪽, 뒤쪽➡위쪽, 아랫쪽➡앞쪽, 앞쪽➡아래쪽,
            -뒷면:
            (0,0) (0,1) (0,2)     (2,0) (1,0) (0,0)
            (1,0) (1,1) (1,2) ➡  (2,1) (1,1) (0,1)
            (2,0) (2,1) (2,2)     (2,2) (1,2) (0,2)
             */
                //오른쪽 면 이동
                char[][] temp = new char[3][3];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        temp[i][j] = right[2-j][i];
                    }
                }

                for(int i = 0 ; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        right[i][j] = temp[i][j];
                    }
                }


                //윗쪽 ➡ 뒷쪽, 뒤쪽➡위쪽, 아랫쪽➡앞쪽, 앞쪽➡아래쪽,
                char[] temp2 = new char[3];
                for(int i = 0 ; i < 3; i++){
                    temp2[i] = up[i][2];
                }

                for(int i = 0 ; i < 3; i++){
                    //up(0,2)<-front(0,2), up(1,2)<-front(1,2), up(2,2)<-front(2,2)
                    up[i][2] = front[i][2];
                    //down(0,2)->front(0,2), down(1,2)->front(1,2), down(2,2)->front(2,2)
                    front[i][2] = down[i][2];
                    //back(0,0)->down(2,2), back(1,0)->down(1,2), back(2,0)->down(0,2)
                    down[i][2] = back[2-i][0];
                    //back(0,0)->front(2,0), back(1,0)->front(1,0),back(2,0)->front(0,0)
                    back[2-i][0] = temp2[i];
                }
            }
        }

        //반시계방향으로 돌리기
        public void turnReverse(char plane) {
            /**
             * 반시계는 시계방향을 3번 돌린것과 마찬가지!
             */
            for(int i = 0; i < 3; i++){
                turnClock(plane);
            }
        }

        public String getUpper(){
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    sb.append(up[i][j]);
                }
                sb.append('\n');
            }

            return sb.toString();
        }
    }

    static Scanner scanner;
    static int T,K;

    static void pro(){
        scanner = new Scanner(System.in);
        T = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i <T; i++){
            K = scanner.nextInt();

            Cube cube = new Cube();

            for(int j =0 ; j < K; j++){
                String cmd = scanner.next();
                char plane = cmd.charAt(0);
                char clk = cmd.charAt(1);

                cube.turn(plane,clk);
            }
            sb.append(cube.getUpper());
        }
        System.out.print(sb);
    }

    public static void main(String[] args) {
        pro();
    }
}