package implementation.boj17779;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n;
    static int[][] A;
    static int MAX = Integer.MIN_VALUE;
    static int MIN = Integer.MAX_VALUE;
    static int ans = Integer.MAX_VALUE;
    static int one, two, three, four, five;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        A = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {

            st = new StringTokenizer(br.readLine());

            for (int j = 1; j <= n; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {

                // 1 ≤ x < x+d1+d2 ≤ N,  1 ≤ y-d1 < y < y+d2 ≤ N
                for (int d1 = 1; x + d1 + 1 <= n && y - d1 >= 1; d1++) {
                    // 1 ≤ x < x+d1+d2 ≤ N,1 ≤ y-d1 < y < y+d2 ≤ N
                    for (int d2 = 1; x + d1 + d2 <= n && y + d2 <= n; d2++) {
                        boolean[][] visited = new boolean[n + 1][n + 1];
                        one = 0;
                        two = 0;
                        three = 0;
                        four = 0;
                        five = 0;

                        //2.경계선 설정
                        //1,4조건
                        for (int i = 0; i <= d1; i++) {
                            visited[x + i][y - i] = true;//1
                            visited[x + d2 + i][y + d2 - i] = true;//4
                        }

                        //2,3 조건
                        for (int i = 0; i <= d2; i++) {
                            visited[x + i][y + i] = true;//2
                            visited[x + d1 + i][y - d1 + i] = true;//3
                        }

                        //3.경계선과 경계선의 안에 포함되어있는 곳은 5번 선거구이다.
                        //내부 체크(모든 행에 대해서, 열 (x,x+d1+d2) 확인
                        for (int i = x + 1; i < x + d1 + d2; i++) {
                            //모든 행
                            for (int j = 1; j <= n; j++) {
                                //경계선 찾음
                                if (visited[i][j]) {
                                    //내부 마킹
                                    while (++j <= n && !visited[i][j]) visited[i][j] = true;
                                }
                            }
                        }

                        //4.각 선거구 인구합 구하기
                        //5선거구
                        for (int i = 1; i <= n; i++) {
                            for (int j = 1; j <= n; j++) {
                                if (visited[i][j]) {
                                    five += A[i][j];
                                }
                            }
                        }

                        MAX = five;
                        MIN = five;

                        //1선거구
                        for (int i = 1; i < x + d1; i++) {
                            for (int j = 1; j <= y; j++) {
                                if (!visited[i][j]) {
                                    one += A[i][j];
                                }
                            }
                        }

                        MAX = Math.max(MAX, one);
                        MIN = Math.min(MIN, one);

                        //2선거구
                        for (int i = 1; i <= x + d2; i++) {
                            for (int j = y + 1; j <= n; j++) {
                                if (!visited[i][j]) {
                                    two += A[i][j];
                                }
                            }
                        }

                        MAX = Math.max(MAX, two);
                        MIN = Math.min(MIN, two);

                        //3선거구
                        for (int i = x + d1; i <= n; i++) {
                            for (int j = 1; j < y - d1 + d2; j++) {
                                if (!visited[i][j]) {
                                    three += A[i][j];
                                }
                            }
                        }

                        MAX = Math.max(MAX, three);
                        MIN = Math.min(MIN, three);

                        //4선거구
                        for (int i = x + d2 + 1; i <= n; i++) {
                            for (int j = y - d1 + d2; j <= n; j++) {
                                if (!visited[i][j]) {
                                    four += A[i][j];
                                }
                            }
                        }

                        MAX = Math.max(MAX, four);
                        MIN = Math.min(MIN, four);
                        ans = Math.min(ans, MAX - MIN);
                    }
                }
            }
        }

        System.out.println(ans);
    }
}
