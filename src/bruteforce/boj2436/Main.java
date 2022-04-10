package bruteforce.boj2436;

import java.util.Scanner;

public class Main {

    static Scanner scanner;
    static long g, l;
    static long mul;//곱한 수
    //a,b
    static long a, b;
    //a+b
    static long min = Long.MAX_VALUE;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        g = scanner.nextLong();
        l = scanner.nextLong();

        mul = l * g;

        a = g;
        b = l;

        //입력되는 두 자연수는 2 이상 100,000,000 이하 => a * b<=10,000,000,000,000,000,000
        for (long i = 2 * g; i * i <= mul; i += g) {
            //mul의 약수 인 i && mul/i의 공통 최대공배수가 g인지 확인
            if (mul % i == 0) {
                long t = mul / i;
                if (gcd(i, t) == g) {
                    long sum = i + t;

                    if (a + b > sum) {
                        //값 갱신
                        min = Math.min(min, sum);
                        a = i;
                        b = t;
                    }
                }
            }
        }
        System.out.print(a + " " + b);
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
