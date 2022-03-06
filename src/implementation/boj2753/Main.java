package implementation.boj2753;

import java.util.Scanner;

public class Main {

    /**
     * 연도가 주어졌을 때, 윤년이면 1, 아니면 0을 출력
     * 윤년은 연도가 4의 배수이면서, 100의 배수가 아닐 때 또는 400의 배수일 때
     */
    static Scanner scanner;
    static int year;

    static void input() {
        scanner = new Scanner(System.in);

        year = scanner.nextInt();
    }

    static int isLeafYear(int year) {

        boolean flag = ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));

        return flag == true ? 1 : 0;//윤년이면 1
    }

    static void pro(){
        input();
        System.out.println(isLeafYear(year));
    }

    public static void main(String[] args) {
        pro();
    }
}
