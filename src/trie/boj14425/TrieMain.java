package trie.boj14425;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TrieMain {
/*
트라이 알고리즘 - 가장 긴 문자열 길이가 M일때 --O(M)
N개 문자열을 비교(O(logN))하게 된다면 O(MlogN) 시간복잡도를 가짐
 */
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

    static class TrieNode{
        //자식노드
        Map<Character,TrieNode> childNode = new HashMap<>();
        //단말노드인지 확인
        boolean isTerminal;
    }
    static class Trie{
        TrieNode root;
        Trie(){
            root = new TrieNode();
        }

        //삽입
        void insert(String word){
            TrieNode trieNode = this.root;

            for(int i = 0 ; i <word.length();i++){
                char c = word.charAt(i);

                //문자c가 없으면 추가
                trieNode.childNode.computeIfAbsent(c,val->new TrieNode());
                trieNode = trieNode.childNode.get(c);
            }
            trieNode.isTerminal = true;
        }

        //특정 문자열을 갖는지 탐색
        boolean search(String word){
            TrieNode trieNode = this.root;

            //루트노드는 비워두고 자식노드들에 값을 넣어두기 때문에 자식노드들에 대해서 확인
            for(int i = 0; i < word.length();i++){
                char c = word.charAt(i);
                TrieNode node = trieNode.childNode.get(c);

                if(node == null){
                    //다음문자가 없는 경우-->불일치
                    return false;
                }
                trieNode = node;
            }
            //해당 단어로 종료되는 단어가 존재한다면 true
            return trieNode.isTerminal;
        }

    }

    static FastReader scan;
    static int N,M;
    static Trie trie;
    static int ans;

    static void input(){
        scan = new FastReader();
        N = scan.nextInt();
        M = scan.nextInt();
        trie = new Trie();
        ans = 0;

        //트라이 구조에 단어 넣기
        for(int i = 0 ; i < N; i++){
            String word = scan.nextLine();
            trie.insert(word);
        }
    }

    static void check(){
        //문자열을 하나씩 입력받으면서 트라이 구조에 저장되어 있는지 확인해서 카운트
        for(int i = 0 ; i < M;i++){
            String cur = scan.nextLine();

            if(trie.search(cur)) ans++;
        }
        System.out.print(ans);
    }

    static void pro(){
        input();
        check();
    }

    public static void main(String[] args) {
        pro();
    }
}
