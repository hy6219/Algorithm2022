package implementation.boj19238;

import java.util.*;

public class Main {

    static class Taxi {
        private int r;
        private int c;
        private int fuel;

        public Taxi(int r, int c, int fuel) {
            this.r = r;
            this.c = c;
            this.fuel = fuel;
        }
    }

    static class Passenger implements Comparable<Passenger> {
        private int r;
        private int c;

        private int destR;
        private int destC;

        public Passenger(int r, int c, int destR, int destC) {
            this.r = r;
            this.c = c;
            this.destR = destR;
            this.destC = destC;
        }

        @Override
        public int compareTo(Passenger o) {
            if (r == o.r) {
                //행번호가 같으면 열 번호 순
                if (c < o.c) {
                    return 0;
                } else {
                    return 1;//지금 객체가 뒤로 가야 함
                }
            }
            return r - o.r;//그 외에는 행번호가 작은 것이 먼저!
        }
    }

    static Scanner scanner;
    static int n;//격자크기
    //승객수
    static int m;
    //초기 연료양
    static int initFuel;
    static int[][] dir = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {-1, 0}
    };
    static Taxi taxi;
    static ArrayList<Passenger> passengers;
    static int[][] map;
    static boolean failed;

    static void input() {
        scanner = new Scanner(System.in);

        n = scanner.nextInt();
        m = scanner.nextInt();
        initFuel = scanner.nextInt();

        map = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = scanner.nextInt();
            }
        }

        taxi = new Taxi(scanner.nextInt(), scanner.nextInt(), initFuel);

        passengers = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            passengers.add(new Passenger(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }
    }

    static void drive() {
        while (!passengers.isEmpty()) {
            //승객 태우기
            Passenger passenger = getNearPassenger(taxi.r, taxi.c);

            //bfs로 목적지까지 이동
            bfs(passenger.r, passenger.c, passenger.destR, passenger.destC);

            //연료소모량 확인 후 성패 결정
            if(taxi.fuel <= 0){
                failed = true;
                System.out.println(-1);
                return;
            }else{
                failed = false;
            }

            //연료 충전
            taxi.fuel += (initFuel - taxi.fuel) * 2;
            initFuel = taxi.fuel;

            passengers.remove(passenger);
        }

        //택시 남은 연료 출력
        System.out.println(taxi.fuel);
    }

    /**
     * 현재 택시 위치에서 최단거리에 위치->행번호->열번호 순 승객 찾기
     */
    static Passenger getNearPassenger(int r, int c) {
        //최단거리 순으로 키-밸류 관리(이렇게 treemap을 사용하면 dist에 대해서 오름차순 정렬될 수 있음)
        Map<Integer, ArrayList<Passenger>> map = new TreeMap<>();

        for (Passenger passenger : passengers) {
            int dist = (int) Math.sqrt(Math.pow(Math.abs(passenger.r - r), 2) + Math.pow(Math.abs(passenger.c - c), 2));
            boolean flag = map.containsKey(dist);
            ArrayList<Passenger> list;

            if (!flag) {
                list = new ArrayList<>();
            } else {
                list = map.get(dist);
            }

            //행->열 순으로 정렬
            Collections.sort(list);

            list.add(passenger);
            map.put(dist, list);
        }

        List<Map.Entry<Integer, ArrayList<Passenger>>> list = new ArrayList<>(map.entrySet());

        Passenger passenger = null;

        if (!list.isEmpty()) {
            passenger = list.get(0).getValue().get(0);
            list.remove(0);
        }

        return passenger;
    }

    static void bfs(int startR, int startC, int destR, int destC) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];

        visited[startR][startC] = true;
        queue.add(new int[]{startR, startC});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            //택시 이동중
            taxi.r = r;
            taxi.c = c;

            for (int i = 0; i < 4; i++) {
                int nr = r + dir[i][0];
                int nc = c + dir[i][1];

                if (nr < 0 || nc < 0 || nr >= n || nc >= n) continue;
                if (map[nr][nc] == 1) continue;//벽
                if (visited[nr][nc]) continue;

                visited[nr][nc] = true;
                queue.add(new int[]{nr, nc});
                taxi.r = nr;
                taxi.c = nc;//택시 이동중
                //연료 1씩 감소
                taxi.fuel--;
            }
        }
    }

    public static void main(String[] args) {
        input();
        drive();
    }
}
