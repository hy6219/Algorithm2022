package floidWashall.boj9205;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner;
    static int T;
    static int N;
    //플로이드 와샬 알고리즘에서 i->j->k 즉 i->k로 이동이 가능한지 기록
    static boolean[][] isAvailable;
    //상근이네집, 편의점, 락페스티벌 좌표 기록
    static List<int[]> points;
    //맥주는 최대 20개이고, 50미터마다 마실 수 있으므로 거리가 50*20 이내이면 isAvailable=true로 마킹
    static int[][] dist;
    static StringBuilder sb;

    static void pro(){
        scanner = new Scanner(System.in);

        T = scanner.nextInt();
        sb = new StringBuilder();

        for(int i = 0 ; i < T; i++){
            N = scanner.nextInt();

            dist = new int[N+2][N+2];//좌표는 N+상근이네+락페
            isAvailable = new boolean[N+2][N+2];
            points = new ArrayList<>();

            //좌표 기록
            for(int j = 0 ; j < N+2; j++){
                points.add(new int[]{scanner.nextInt(), scanner.nextInt()});
            }

            //(처음에 일단 맥주 하나 마심)1000미터마다 맥주 마셔야 할 지 확인
            for(int j = 0 ; j < N+2; j++){
                for(int k = 0 ; k < N+2;k++){
                    int[] p1 = points.get(j); int[] p2 = points.get(k);
                    //j->k 거리기록
                    dist[j][k] = Math.abs(p1[0]-p2[0]) + Math.abs(p1[1]-p2[1]);

                    //1000미터마다 맥주 남았는지 확인
                    if(dist[j][k] <= 1000) isAvailable[j][k] = true;
                }
            }

            //k->j,j->l로 접점 이동시키면서 맥주 남았는지 확인
            for(int j = 0 ; j < N+2; j++){
                for(int k = 0 ; k < N+2; k++){
                    for(int l = 0 ; l < N+2; l++){
                        if(isAvailable[k][j] && isAvailable[j][l]) isAvailable[k][l] = true;
                    }
                }
            }

            //시작지점은 0, 마지막은 n+1에 기록해두었으니 이 점을 참고해서 접근하면 됨
            sb.append(isAvailable[0][N+1]?"happy":"sad").append('\n');
        }

        System.out.print(sb.toString().strip());
    }

    public static void main(String[] args) {
        pro();
    }
}
