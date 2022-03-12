package greedy.boj1541;

import java.util.Scanner;

/**
 * 1."-"를 기준으로 split(값-최댓값으로 최솟값을 만들어주기 위함)
 * 2.1로 그룹화된 unit들 간에서 +로 계산
 * 3.2를 "-"로 연결
 */
public class Main {

    static Scanner scanner;
    static String[] line;
    static boolean isFirst = true;

    static void input(){
        scanner = new Scanner(System.in);
        line = scanner.nextLine().split("-");
    }

    static void bracelet(){
        int ans = 0;

        for(int i = 0; i < line.length; i++){
            int result = 0;

            //+로 구분(괄호)-+가 없으면 그대로 넘겨질것!
            String[] plus = line[i].split("\\+");

            for (String s : plus) {
                result += Integer.parseInt(s);
            }

            //식의 처음인지 구별해서 -로 연결
            if(isFirst){
                //시작~
                ans = result;
                //시작이 끝남!!
                isFirst = false;
            }else{
                ans -= result;//-로 연결
            }
        }

        System.out.println(ans);
    }

    static void pro(){
        input();
        bracelet();
    }

    public static void main(String[] args) {
        pro();
    }
}
