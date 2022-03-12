package bfs.boj23314;

import java.util.*;

public class Main {

    static class Plot{
        int x;
        int y;

        public Plot(int x, int y) {
            this.x = x;
            this.y = y;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Plot plot = (Plot) o;
            return x == plot.x && y == plot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Plot{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

    }

    static Scanner scanner;
    static int N,M,Q;
    //연결정보만 저장
    static Map<Integer,ArrayList<Integer>> list;
    //가중치까지 저장
    static Map<Plot,Integer> graph;
    static Map<Plot,Integer> query;
    static int MIN=0;
    static StringBuilder sb;

    static void pro(){
        scanner= new Scanner(System.in);

        N= scanner.nextInt();
        M= scanner.nextInt();
        Q= scanner.nextInt();

        list = new HashMap<>();
        graph = new HashMap<>();
        query = new HashMap<>();
        sb=new StringBuilder();
        //간선 입력받기
        for(int i=0;i<M;i++){
            int first = scanner.nextInt();
            int second = scanner.nextInt();
            int weight = scanner.nextInt();

            graph.put(new Plot(first,second),weight);

            boolean flag = list.containsKey(first);

            if(flag){
                ArrayList<Integer> value=list.get(first);
                value.add(second);
                list.put(first,value);
            }else{
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(second);
                list.put(first,temp);
            }
        }

        //처음 상태에서 최소거리 구하기
        for(int i = 1 ; i <= N; i++){
            bfs(i);
        }

        sb.append(MIN).append('\n');

        //쿼리 입력받기
        for(int i=0;i<Q;i++){
            int first = scanner.nextInt();
            int second = scanner.nextInt();
            int weight = scanner.nextInt();

            Plot p1= new Plot(first,second);
            Plot p2= new Plot(second,first);
            boolean flag = graph.containsKey(p1);

            if(!flag){
                flag = graph.containsKey(p2);
                if(flag){
                    graph.put(p2,weight);
                }else{
                    graph.put(p1,weight);
                }
            }else{
                graph.put(p1,weight);
            }

            //bfs실행해보기
            for(int j = 1 ; j <= N; j++){
                bfs(j);
            }
            sb.append(MIN).append('\n');
        }

        System.out.print(sb);
    }

    //bfs 최솟값 구하기
    static void bfs(int start){
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[N+1];
        int dist=0;

        queue.add(start);
        visited[start]=true;

        while(!queue.isEmpty()){
            int cur = queue.poll();
            if(cur<=0 || cur>N) continue;
            if(visited[cur]) continue;
            visited[cur]=true;
            //연결된 모든 노드 방문
            for(int i = 0; i < list.get(cur).size();i++){
                int next = list.get(cur).get(i);

                if(next<=0 || next>N) continue;
                if(visited[next]) continue;

                queue.add(next);
                visited[next]=true;

                /**
                 * (cur,next) 혹은 (next,cur)로 된 plot의 가중치를 더해주기
                 */
                Plot chk1 = new Plot(cur,next);
                Plot chk2 = new Plot(next,cur);

                if(graph.containsKey(chk1)){
                    dist+= graph.get(chk1);
                }else if(graph.containsKey(chk2)){
                    dist+= graph.get(chk2);
                }
                //현재 위치 갱신
                cur = next;
            }
            //최솟값 갱신
            MIN = Math.min(MIN,dist);
        }
    }

    public static void main(String[] args) {
        pro();
    }
}
