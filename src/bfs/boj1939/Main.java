package bfs.boj1939;

import java.util.*;

public class Main {

    static class Island{
        private int point;
        private int weight;

        public Island(int point, int weight) {
            this.point = point;
            this.weight = weight;
        }
    }

    static Scanner scanner;
    static int N, M;
    static ArrayList<ArrayList<Island>> islands;
    static int start, dest;
    static int min = 0;
    static int max = 0;
    static boolean[] visit;

    static void input() {
        scanner = new Scanner(System.in);

        N = scanner.nextInt();
        M = scanner.nextInt();
        islands = new ArrayList<>();

        for(int i = 0 ; i < N+1;i++){
            islands.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();

            max = Math.max(max,c);

            islands.get(a).add(new Island(b,c));
            islands.get(b).add(new Island(a,c));
        }


        start = scanner.nextInt();
        dest = scanner.nextInt();
    }

    static void binarySearch(){
        while(min <= max){
            int mid = (min+max)/2;
            //매번 확인
            visit= new boolean[N+1];

            //중량확인
            if(bfs(mid)){
                //옮길수 있음
                min = mid+1;//오른쪽으로 관찰
            }else{
               //옮길수 없음
                max = mid-1;
            }
        }

        System.out.print(max);//통과된 최대중량
    }

    static boolean bfs(int mid){
        /*
        방문하지 않았고, mid보다 제한이 크면 계속 탐색
         */
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visit[start] = true;

        while(!queue.isEmpty()){
            int cur = queue.poll();

            if(cur == dest) return true;//도달한것

            for(int i = 0 ; i < islands.get(cur).size();i++){
                int next = islands.get(cur).get(i).point;
                int nextCost = islands.get(cur).get(i).weight;

                if(visit[next]) continue;
                if(mid > nextCost) continue;//중량 초과

                visit[next] = true;
                queue.add(next);
            }
        }
        return false;//도달하지 못함
    }

    static void pro(){
        input();
        binarySearch();
    }

    public static void main(String[] args) {
        pro();
    }
}
