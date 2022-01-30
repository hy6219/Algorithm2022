package binary_search.boj2110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

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
    static int N, C;
    static int[] arr;

    static void input() {
        scan = new FastReader();

        N = scan.nextInt();
        C = scan.nextInt();

        arr = new int[N+1];

        for (int i = 1; i <= N; i++) {
            arr[i] = scan.nextInt();
        }
    }

    static boolean standard(int dist){
        /*
        첫번째 집부터 최대한 많이 설치하기
        x0---x1---x2
        |x1-x0|<=|x1-x2| 이어야 설치 가능해서
        두 지점 간 사이 거리로 설치가능한지 확인
         */
        int cnt =1;//설치가능한 갯수 세기(시작부터 세기)
        int last = arr[1];//처음에는 마지막집을 첫번째 집으로 잡기

        for(int i = 2; i <= N; i++){
            //설치가능하다면 설치갯수와 마지막집을 갱신
            if(arr[i] - last >= dist){
                cnt++;
                last = arr[i];
            }
        }
        return cnt >= C;//C개 이상 설치할 수 있는지 확인
    }

    static void binarySearch(){
        /*
        1. 정렬
        2. C개 이상의 공유기를 설치할 수 있는지 확인
        -> o<=> 공유기수를 줄여야 함<=> 간격넓히기
        ->x <=> 공유기수를 늘여야 함<=>간격좁히기
         */
        int L, R,mid, ans;//L:왼쪽지점, R: 오른쪽지점, mid: 중간, ans:가장인접한 두 공유기 사이의 최대거리
        Arrays.sort(arr,1,N+1);

        //xi (0 ≤ xi ≤ 1,000,000,000)
        L = 1; R =1000000000;
        ans =0;
        while(L<=R){
            mid = (L+R)/2;

            if(standard(mid)){
                //C개이상의 공유기 설치 가능
                //공유기 수 줄이기
                ans = mid;//현재 거리 기록
                //오른쪽보기
                L = mid+1;
            }else{
                //C개 미만
                //공유기 수 늘이기
                //왼쪽 보기
                R = mid-1;
            }
        }
        System.out.print(ans);
    }

    static void pro(){
        input();
        binarySearch();
    }

    public static void main(String[] args) {
        pro();
    }
}
