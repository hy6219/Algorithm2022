package bruteforce.boj14888;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String nextLine() {
            String str = "";

            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return str;
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    static FastReader scan;
    static int N;
    static int[] numbers;
    static int[] operators;//연산자 0:+ 1: - 2:* 3: /
    //연산자 기록 배열
    static int[] recordOps;
    //최솟값, 최댓값
    static int MIN = Integer.MAX_VALUE;
    static int MAX = Integer.MIN_VALUE;
    static StringBuilder sb;

    static void input() {
        scan = new FastReader();
        sb = new StringBuilder();

        N = scan.nextInt();
        numbers = new int[N + 1];
        operators = new int[4];
        recordOps = new int[N - 1];

        for (int i = 0; i < N; i++) {
            numbers[i] = scan.nextInt();
        }

        for (int i = 0; i < 4; i++) {
            operators[i] = scan.nextInt();
        }

        //초기화
        Arrays.fill(recordOps, -1);
    }

    static void rec_func(int k) {
        //연산자는 총 N-1개 뽑아서 진행할 것
        if (k == N - 1) {
            //다뽑았다면, 계산해서 최솟값, 최댓값 갱신
            int cal = calculate();
            MIN = Math.min(MIN, cal);
            MAX = Math.max(MAX, cal);
        } else {
            //연산자 선택
            for (int cand = 0; cand < 4; cand++) {
                //뽑을 연산자 갯수가 없으면 사용 못함
                if (operators[cand] < 1) continue;

                //연산자 기록하고, 사용한 연산자 갯수 감소
                recordOps[k] = cand; //어떤 연산자인지 인덱스 기록
                operators[cand]--;

                rec_func(k + 1);

                //원상복구
                recordOps[k] = -1;
                operators[cand]++;
            }
        }
    }

    static int calculate() {
        int first = numbers[0];

        //숫자가 N개면 연산자는 N-1개 (1+2+3)
        for (int i = 0; i < N - 1; i++) {

            if (recordOps[i] == 0) {
                //더하기
                first += numbers[i + 1];
            } else if (recordOps[i] == 1) {
                //빼기
                first -= numbers[i + 1];
            } else if (recordOps[i] == 2) {
                //곱하기
                first *= numbers[i + 1];
            } else {
                //나누기
                first /= numbers[i + 1];//연산자 뒤 숫자를 적용
            }
        }
        return first;
    }

    static void pro() {
        input();
        rec_func(0);
        sb.append(MAX).append('\n').append(MIN);
        System.out.println(sb);
    }

    public static void main(String[] args) {
        pro();
    }
}
