package bfs.boj1326;

import java.util.*;

/**
 *
 * https://www.acmicpc.net/board/view/588
 * ->개구리는 양, 음의 방향으로 움직일 수 있다
 * 5
 * 1 5 2 1 2
 * 2 5
 *
 * >>-1
 *
 * 양의 방향, 음의방향으로 움직이면서 방문 체크하고
 * 이런 과정은 목적지에 도착하기 전까지 진행하지만
 * 결국 큐도 비게 된다는 것은 목적지에 도달할 수 없다는 것이므로 -1을 반환
 */
public class Main {
    static class Node {
        private int ord;//몇번째 노드인지
        private int iter;//횟수

        public Node(int ord, int iter) {
            this.ord = ord;
            this.iter = iter;
        }
    }

    static Scanner scan;
    //배수값 저장
    static int[] baesu;
    static int n, a, b;

    static void input() {
        scan = new Scanner(System.in);

        n = scan.nextInt();

        baesu = new int[n+1];

        for (int i = 1; i <= n; i++) {
            baesu[i] = scan.nextInt();
        }

        a = scan.nextInt();
        b = scan.nextInt();
    }

    static void bfs(){
        Queue<Node> nodes = new LinkedList<>();
        boolean[] visited = new boolean[n+1];

        //시작점 방문처리
        visited[a] =true;
        nodes.add(new Node(a,0));

        while (!nodes.isEmpty()){
            //하나 꺼내기
            Node first= nodes.poll();
            //현재 노드번호
            int num = first.ord;
            //현재 이동횟수
            int iter = first.iter;

            //현재 위치가 목적지인지 확인
            if(num == b){
                System.out.println(iter);
                return;
            }

            //아직 도착안했다면 계속 이동
            //양의 방향
            for(int gop=1;num+(baesu[num]*gop)<=n; gop++){
                //다음배수
                int nextNum = num+(baesu[num]*gop);
                //방문한적 있는지 확인
                if(visited[nextNum]) continue;

                //첫방문
                //방문체크
                visited[nextNum] = true;
                nodes.add(new Node(nextNum,iter+1));
            }
            //음의 방향
            for(int neg=1;num-(baesu[num]*neg)>=1;neg++){
                //다음징검다리
                int negNextNum= num-(baesu[num]*neg);

                //방문한적 있는지 확인
                if(visited[negNextNum]) continue;

                visited[negNextNum] = true;
                nodes.add(new Node(negNextNum,iter+1));
            }
        }
        System.out.println(-1);
    }


    static void pro() {
        input();
        bfs();
    }

    public static void main(String[] args) {
        pro();
    }
}
