package implementation.boj23289;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int r, c, k, w;
    static int chocolate;
    static Room[][] board;
    static ArrayList<Heater> heaters;
    static ArrayList<Room> testRooms;
    static final int up = 0, right = 1, down = 2, left = 3;
    static int[] dx = {-1, 0, 1, 0}; //[위, 오, 아래, 왼]
    static int[] dy = {0, 1, 0, -1};

    public static void activateHeater() {
        for (Heater heater : heaters) {
            heater.wind();
        }
    }


    public static void adjustHeat() {
        int[][] saveHeat = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Room room = board[i][j];
                for (int dir = 0; dir < 4; dir++) {
                    int nx = i + dx[dir];
                    int ny = j + dy[dir];
                    if(valid(nx, ny) && !room.isWalled[dir]) {
                        Room nextRoom = board[nx][ny];
                        int adjust = (room.hot - nextRoom.hot)/4;
                        if(adjust > 0) {
                            saveHeat[i][j] -= adjust;
                            saveHeat[nx][ny] += adjust;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                board[i][j].hot += saveHeat[i][j];
            }
        }
    }


    public static void decreaseHeat() {
        for (int j = 0; j < c-1; j++) {
            if(board[0][j].hot > 0) board[0][j].hot--;
        }
        for (int i = 0; i < r-1; i++) {
            if(board[i][c-1].hot > 0) board[i][c-1].hot--;
        }
        for (int j = c-1; j >= 1; j--) {
            if(board[r-1][j].hot > 0) board[r-1][j].hot--;
        }
        for (int i = r-1; i >= 1; i--) {
            if(board[i][0].hot > 0) board[i][0].hot--;
        }
    }


    public static boolean testPass() {
        for (Room room : testRooms) {
            if(room.hot >= k) continue;
            else return false;
        }
        return true;
    }


    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = pi(st.nextToken());
        c = pi(st.nextToken());
        k = pi(st.nextToken());

        chocolate = 0;
        board = new Room[r][c];
        heaters = new ArrayList<>();
        testRooms = new ArrayList<>();

        for(int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                board[i][j] = new Room();
                int num = pi(st.nextToken());
                if (num != 0) {
                    if (num < 5) {
                        int dir = getDir(num);
                        heaters.add(new Heater(i, j, dir));
                    } else {
                        testRooms.add(board[i][j]);
                    }
                }
            }
        }

        w = pi(br.readLine());
        for (int i = 0; i < w; i++) {
            st = new StringTokenizer(br.readLine());
            setWall(pi(st.nextToken())-1, pi(st.nextToken())-1, pi(st.nextToken()));
        }
    }

    public static int getDir(int num) {
        if(num == 1) return right;
        else if(num == 2) return left;
        else if(num == 3) return up;
        else return down;
    }

    public static void setWall(int x, int y, int t) {
        if(t == 0) {
            board[x-1][y].isWalled[down] = true;
            board[x][y].isWalled[up] = true;
        } else {
            board[x][y].isWalled[right] = true;
            board[x][y+1].isWalled[left] = true;
        }
    }

    public static int pi(String str) {
        return Integer.parseInt(str);
    }

    public static boolean valid(int x, int y) {
        if(x < 0 || x >= r || y < 0 || y >= c) return false;
        else return true;
    }

    public static void print() {
        for(int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(board[i][j].hot + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static class Room{
        int hot; //temp는 임시같고, temperature는 너무 길고..
        boolean[] isWalled;

        public Room() {
            hot = 0;
            isWalled = new boolean[4]; // [위, 오, 아래, 왼]
        }
    }

    static class Heater{
        int x, y;
        int dir;

        public Heater(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        public void wind() {
            int forward = dir;
            int forLeft = (dir+3)%4;
            int forRight = (dir+1)%4;

            boolean[][] visited = new boolean[r][c];
            visited[x + dx[dir]][y + dy[dir]] = true;
            Queue<Node> queue = new LinkedList<>();
            queue.add(new Node(x + dx[dir], y + dy[dir], 5));

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                if(node.remain == 0) continue;

                board[node.x][node.y].hot += node.remain;

                //직진 방향 체크
                int nx = node.x + dx[forward];
                int ny = node.y + dy[forward];
                if(valid(nx, ny) && !visited[nx][ny] && !board[node.x][node.y].isWalled[forward]) {
                    visited[nx][ny] = true;
                    queue.add(new Node(nx, ny, node.remain - 1));
                }

                int nx2, ny2;//nx, nx2에 대한 설명은 아래 코드 해설의 그림 참조
                nx = node.x + dx[forLeft]; nx2 = nx + dx[forward];
                ny = node.y + dy[forLeft]; ny2 = ny + dy[forward];
                if(valid(nx, ny) && valid(nx2, ny2) && !visited[nx2][ny2]) {//가려는 좌표가 모두 유효한 좌표인지 체크 + 이미 바람이 전파된 곳인지 체크
                    if(!board[node.x][node.y].isWalled[forLeft] && !board[nx][ny].isWalled[forward]) {//벽 쳐져있는지 여부 체크
                        visited[nx2][ny2] = true;
                        queue.add(new Node(nx2, ny2, node.remain - 1));
                    }
                }

                nx = node.x + dx[forRight]; nx2 = nx + dx[forward];
                ny = node.y + dy[forRight]; ny2 = ny + dy[forward];
                if(valid(nx, ny) && valid(nx2, ny2) && !visited[nx2][ny2]) {
                    if(!board[node.x][node.y].isWalled[forRight] && !board[nx][ny].isWalled[forward]) {
                        visited[nx2][ny2] = true;
                        queue.add(new Node(nx2, ny2, node.remain - 1));
                    }
                }
            }
        }
    }

    static class Node {
        int x, y, remain;

        public Node(int x, int y, int remain) {
            this.x = x;
            this.y = y;
            this.remain = remain;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        while(chocolate <= 100) {
            activateHeater(); // step 1

            adjustHeat(); // step 2

            decreaseHeat(); // step 3

            chocolate++; // step 4
            if(testPass()) { // step 5
                System.out.println(chocolate);
                return;
            }
        }

        System.out.println(101);
    }

}