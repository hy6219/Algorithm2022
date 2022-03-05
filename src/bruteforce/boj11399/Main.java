package bruteforce.boj11399;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    /**
     * 정렬 후 누적합 구하면 최소!
     */
    static Scanner sc;
    static int N;
    static int[] people;

    static void input() {
        sc = new Scanner(System.in);

        N = sc.nextInt();
        people = new int[N];

        for (int i = 0; i < N; i++) {
            people[i] = sc.nextInt();
        }
    }

    static void getAcc() {
        Arrays.sort(people);

        //이전사람까지의 누적합
        int prev = 0;
        //모든 사람들의 누적합의 누적합
        int sum = 0;

        for (int i = 0; i < people.length; i++) {
            sum += prev + people[i];//지금 사람먼저 계산해둔 후 이전사람
            // (이제는 다음번 사람을 위해서 prev를 지금 사람으로 갱신해주어야 함)을 갱신해야 다음번 누적에 영향줄수 있음

            prev += people[i];
        }

        System.out.println(sum);
    }

    static void pro() {
        input();
        getAcc();
    }

    public static void main(String[] args) {
        pro();
    }
}
