package prgms.prgms12940;

import java.util.Arrays;

public class Solution {
    public static int[] solution(int m, int n) {
        int[] answer = new int[2];
        answer[0] = gcd(m,n);
        answer[1] = lcd(m,n,answer[0]);
        return answer;
    }

    static int gcd(int m, int n) {
        /*
        1.입력으로 두 수 m,n(m>n)이 들어온다.
2.n이 0이라면, m을 출력하고 알고리즘을 종료한다.
3.m이 n으로 나누어 떨어지면, n을 출력하고 알고리즘을 종료한다.
4.그렇지 않으면, m을 n으로 나눈 나머지를 새롭게 m에 대입하고, m과 n을 바꾸고 3번으로 돌아온다.
         */
        if (n == 0) return m;//아래에서 바꿔주면 1,3 해결!
        return gcd(n, m % n);//그렇지 않으면, m을 n으로 나눈 나머지를 새롭게 m에 대입하고, m과 n을 바꾸고 3번으로 돌아온다.
    }

    static int lcd(int m, int n, int gcd) {
        return m * n / gcd;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(3,12)));
        System.out.println(Arrays.toString(solution(2,5)));
    }
}
