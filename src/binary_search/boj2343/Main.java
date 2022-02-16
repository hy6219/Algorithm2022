package binary_search.boj2343;

import java.util.Scanner;

public class Main {
    /**
     * 1.정렬x-->순서바뀌면 안됨! https://www.acmicpc.net/board/view/65891
     * 2.임의의 블루레이 크기(mid) 지정(https://www.acmicpc.net/board/view/64117
     * ->첫째 줄에 강의의 수 N (1 ≤ N ≤ 100,000)과 M (1 ≤ M ≤ N)이 주어진다. 다음 줄에는 강토의 기타 강의의 길이가 강의 순서대로 분 단위로(자연수)로 주어진다. 각 강의의 길이는 10,000분을 넘지 않는다.
     * ->최대 10그룹이 될 수있고, 최대 10000분일때 최대합은 1000000000 )
     * 3.mid보다 작은 갯수 구하기
     * 4.구한갯수<= M-> 갯수 부족, 따라서 우측 범위를 줄이기, 구한갯수>M ->왼쪽범위 늘리기
     * @param args
     */
    static Scanner scanner;
    static int N,M;
    static int[] lectures;
    //왼쪽끝
    static int left=0;
    //오른쪽 끝
    static int right=1000000000;

    static void input(){
        scanner = new Scanner(System.in);
        N = scanner.nextInt();
        M = scanner.nextInt();

        lectures = new int[N];

        for(int i = 0 ; i <N; i++){
            lectures[i]= scanner.nextInt();
            //최솟값으로
            left=left<lectures[i]?lectures[i]:left;
        }

    }

    static void search(){
        while(left<=right){
            int mid = (left+right)/2;
            int sum = 0;//mid보다 작은 경우 찾기
            int cnt = 0;//그룹카운트

            for(int i = 0; i<N; i++){
                if(sum+lectures[i] >mid){
                    sum = 0;//새로운 그룹
                    cnt++;
                }
                sum += lectures[i];//그룹형성중
            }
            //블루레이크기인 mid보다 작은 경우 중 , sum이 0이 아닌 경우
            if(sum!=0) cnt++;

            if(cnt<=M){
                //구현갯수 부족
                //->우측 줄이기
                right=mid-1;
            }else{
                //구현갯수가 너무 많음
                //->왼쪽 높여주기
                left=mid+1;
            }
        }
        System.out.print(left);
    }

    static void pro(){
        input();
        search();
    }

    public static void main(String[] args) {
        pro();
    }
}
