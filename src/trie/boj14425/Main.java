package trie.boj14425;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static class FastReader{
        BufferedReader br;
        StringTokenizer st;

        public FastReader(){
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String nextLine(){
            String str="";

            try {
                str = br.readLine();
            }catch (IOException e){
                e.printStackTrace();
            }

            return str;
        }

        String next(){
            while(st== null || !st.hasMoreTokens()){
                try {
                    st = new StringTokenizer(br.readLine());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt(){
            return Integer.parseInt(next());
        }
    }

    static FastReader scan;
    static int N,M;
    static HashSet<String> s;//group s
    static List<String> input;//LinkedList로 중간 연산 진행
    static int ans;//정답기록

    static void input(){
        scan = new FastReader();
        ans =0;
        N = scan.nextInt();
        M = scan.nextInt();

        s =new HashSet<>();
        input = new LinkedList<>();

        //집합 s 입력 받기
        for(int i = 0; i< N; i++){
            s.add(scan.nextLine());
        }

        //입력 문자열 받기
        for(int i =0; i<M; i++){
            input.add(scan.nextLine());
        }
    }

    static void check(){
       for(int i = 0 ; i < input.size(); i++){
           String cur = input.get(i);
           if(s.contains(cur)) ans++;
       }
    }


    static void pro(){
        input();
        check();
        System.out.print(ans);
    }

    public static void main(String[] args) {
        pro();
    }
}
