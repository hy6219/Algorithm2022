package implementation.boj23290;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static class Fish {
        private int r;
        private int c;
        private int d;
        private int smell;

        public Fish(int r, int c, int d, int smell) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.smell = smell;
        }

        @Override
        public String toString() {
            return "Fish{" +
                    "r=" + r +
                    ", c=" + c +
                    ", d=" + d +
                    ", smell=" + smell +
                    '}';
        }
    }


    static class Board {
        private List<Fish> fishes;
        private List<Integer> smells;

        public Board() {
        }

        public Board(List<Fish> fishes, List<Integer> smells) {
            this.fishes = fishes;
            this.smells = smells;
        }

        @Override
        public String toString() {
            return "Board{" +
                    "fishes=" + fishes +
                    ", smells=" + smells +
                    '}';
        }
    }

    //물고기수 m, 상어 마법 횟수 s
    static int m, s;
    //상어 위치
    static int sr, sc;

    static Board[][] map, copyMap;
    static int[][] dir = {
            {0, -1},
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, 1},
            {1, 1},
            {1, 0},
            {1, -1}
    };
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //입력받기
        m = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
        map = new Board[5][5];
        copyMap = new Board[5][5];
        visited = new boolean[5][5];

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                map[i][j] = new Board();
                copyMap[i][j] = new Board();
                map[i][j].fishes = new ArrayList<>();
                map[i][j].smells = new ArrayList<>();
                copyMap[i][j].fishes = new ArrayList<>();
                copyMap[i][j].smells = new ArrayList<>();
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            Fish fish = new Fish(r, c, d, 0);
            List<Fish> fishes = map[r][c].fishes;
            fishes.add(fish);

            List<Integer> smells = map[r][c].smells;
            smells.add(0);

            map[r][c] = new Board(fishes, smells);
        }


        st = new StringTokenizer(br.readLine());

        sr = Integer.parseInt(st.nextToken());
        sc = Integer.parseInt(st.nextToken());

        //마법시전시도횟수
        int trial = 0;

        while (trial <= s) {
            //1.복제마법시작 전 배열 복사
            copy(map, copyMap,trial);
            //2.모든 물고기 1칸 이동
            moveFishes();
            //3.상어3칸이동
            moveShark(trial);
            //4.현재연습-2의 냄새 제거
            removeSmell(trial);
            //5.copyMagic
            copyMagic();
            //6.copyMap결과 옮겨주기
            copy(copyMap, map,trial);
            trial++;
        }
        System.out.println("map: " + Arrays.deepToString(map));
        //격자에 남은 물고기수 구하기
        int cnt = 0;

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (map[i][j].fishes != null || !map[i][j].fishes.isEmpty()) {
                    cnt += map[i][j].fishes.size();
                }
            }
        }

        System.out.print(cnt);

    }

    //1.이동하기 전 카피!
    static void copy(Board[][] from, Board[][] to, int trial) {
        for (int i = 1; i < from.length; i++) {
            for (int j = 1; j < to.length; j++) {
                List<Fish> fishes = from[i][j].fishes;
                List<Fish> temp = new ArrayList<>();
                if (fishes == null || fishes.isEmpty()) continue;
                for (Fish fish : fishes) {
                    fish.smell = trial;
                    temp.add(fish);
                }
                to[i][j].fishes = temp;
                to[i][j].smells = from[i][j].smells;
            }
        }
    }


    //2.모든 물고기 1칸 이동
    static void moveFishes() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                int r = i;
                int c = j;
                List<Fish> fishes = copyMap[r][c].fishes;

                if(fishes == null) continue;
                for (int k = 1; k < fishes.size(); k++) {
                    Fish fish = fishes.get(k);

                    for (int l = 0; l < 8; l++) {
                        int nr = r + dir[l][0];
                        int nc = c + dir[l][1];

                        //격자 범위 외는 이동 못합
                        if (nr <= 0 || nc <= 0 || nr > 4 || nc > 4) continue;
                        //냄새가 있는 칸으로 이동 못함
                        if (copyMap[nr][nc].smells != null || !copyMap[nr][nc].smells.isEmpty() || copyMap[nr][nc].smells.size() > 0) continue;

                        //물고기 이동
                        fish.r = nr;
                        fish.c = nc;
                        fish.d = l;

                        if(copyMap[nr][nc].fishes == null){
                            List<Fish> fishList = new ArrayList<>();
                            fishList.add(fish);
                            copyMap[nr][nc].fishes =fishList;
                        }else{
                            copyMap[nr][nc].fishes.add(fish);
                        }

                        //탈출
                        break;
                    }
                    //이동 후 해당 칸에서 물고기 제거
                    fishes.remove(fish);
                }
            }
        }
    }

    //3.상어 3칸 이동
    static void moveShark(int trial) {
        int[][] d = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };


        for (int move = 0; move < 3; move++) {
            visited[sr][sc] = true;
            for (int i = 0; i < 4; i++) {
                int nr = sr + d[i][0];
                int nc = sc + d[i][1];

                if (nr < 1 || nc < 1 || nr > 4 || nc > 4) continue;

                //해당 칸에 물고기가 있다면 그 물고기는 격자에서 제거되고,냄새를 남김
                List<Fish> fishes = copyMap[nr][nc].fishes;

                //방문한 적이 있고, 그 칸에 물고기가 있다면 물고기를 먹으면 안됨
                //물고기를 먹은 칸에만 냄새를 남김
                if(!visited[nr][nc] && fishes != null && !fishes.isEmpty()){
                    copyMap[nr][nc].smells.add(trial);
                    copyMap[nr][nc].fishes.clear();
                }

                sr = nr;
                sc = nc;
            }
        }
    }

    //4.현재연습-2의 냄새 제거
    static void removeSmell(int trial) {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if(copyMap[i][j].smells == null || copyMap[i][j].smells.isEmpty()) continue;
                if (copyMap[i][j].smells.contains(trial - 2)) {
                    int idx = copyMap[i][j].smells.indexOf(trial-2);
                    copyMap[i][j].smells.remove(idx);
                }
            }
        }
    }

    //5. copyMagic 복제마법
    static void copyMagic() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                List<Fish> fishes = copyMap[i][j].fishes;
                if (fishes == null || fishes.isEmpty()) continue;
                copyMap[i][j].fishes.addAll(fishes);
            }
        }
    }

}
