package phase.boj2252;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static Scanner scanner;
    //인접리스트
    static ArrayList<ArrayList<Integer>> list;
    /*
    연결 간선수

    순서가 지금 1->3, 2->3순인 것을 그래프로 연결지어서
    "3", 즉 뒤에 오는 입력의 간선수를 증감시키기
     */
    static int[] wire;
    static int N,M;

    static void input(){
        scanner = new Scanner(System.in);
        N = scanner.nextInt();
        M = scanner.nextInt();
        list = new ArrayList<>();
        wire = new int[N+1];

        //연결리스트 초기화
        for(int i = 0; i <N+1; i++){
            list.add(new ArrayList<>());
        }

        //간선정보
        for(int i = 0; i < M; i++){
            int first = scanner.nextInt();
            int second = scanner.nextInt();

            //연결리스트 만들기(first->second)
            list.get(first).add(second);
            //second에 연결된 간선수 증가
            wire[second]++;
        }

    }

    static void phaseSort(){
        //큐를 이용
        Queue<Integer> queue = new LinkedList<>();

        //1. 진입차수가 0인 모든 정점을 선택해서 넣어주기 like multisource bfs
        for(int i = 1; i <= N; i++){
            if(wire[i]==0){
                queue.add(i);
            }
        }

        //2.모든 정점에 대해서 진행
        for(int i = 0 ; i < N;i++){
            //2-1. 정점 하나 추출
            int node= queue.poll();
            System.out.print(node+" ");
            //2-2.정점과 연결된 모든 정점들에 대해서 간선수 감소
            for(int next:list.get(node)){
                //간선수감소
                wire[next]--;

                //연결된 그 노드의 간선수가 0이라면 담기
                if(wire[next]==0) queue.add(next);
            }
        }
    }


    static void pro(){
        input();
        //위상정렬
        phaseSort();
    }

    public static void main(String[] args) {
        pro();
    }
}
