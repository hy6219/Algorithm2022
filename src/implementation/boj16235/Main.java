package implementation.boj16235;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static class Tree {
        private int r;
        private int c;
        //나무 나이
        private int age;

        public Tree(int r, int c, int age) {
            this.r = r;
            this.c = c;
            this.age = age;
        }

    }

    static BufferedReader br;
    static StringTokenizer st;
    static int n, m, k;
    static int[][] board;//현재 양분상태 기록
    static int[][] addYangboon;
    static int[][] dir = {
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, -1},
            {0, 1},
            {1, -1},
            {1, 0},
            {1, 1}
    };
    //죽은 나무 기록
    static Queue<Tree> dead;
    //나무들 기록<-방안에 나무리스트가 있는 것으로 기록하고 관리하면 3~4중 반복문을 수행하게 됨
    static LinkedList<Tree> trees;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        board = new int[n + 1][n + 1];
        addYangboon = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 1; j <= n; j++) {
                addYangboon[i][j] = Integer.parseInt(st.nextToken());
                board[i][j] = 5;
            }
        }

        trees = new LinkedList<>();

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int age = Integer.parseInt(st.nextToken());

            trees.add(new Tree(r, c, age));
        }

        int yr = 0;
        dead = new LinkedList<>();

        while (yr < k) {
            //봄
            spring();
            //여름
            summer();
            //가을
            fall();
            //겨울
            winter();

            yr++;
        }

        System.out.println(trees.size());
    }

    static void spring() {
        /*
        나무가 자신의 나이만큼 양분을 먹고, 나이가 1 증가한다.
        각각의 나무는 나무가 있는 1×1 크기의 칸에 있는 양분만 먹을 수 있다.
        하나의 칸에 여러 개의 나무가 있다면, 나이가 어린 나무부터 양분을 먹는다.
        만약, 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무는 양분을 먹지 못하고 즉시 죽는다.
         */
        Iterator iterator = trees.iterator();

        while (iterator.hasNext()) {
            Tree tree = (Tree) iterator.next();
            int r = tree.r;
            int c = tree.c;
            int age = tree.age;

            if (age <= board[r][c]) {
                //양분을 먹을 수 있음
                //양분은 나이만큼 줄어들고, 나이 1 증가
                board[r][c] -= age;
                ++tree.age;
            } else {
                //양분을 먹을 수 없음==>나무 죽음
                dead.add(tree);
                iterator.remove();
            }
        }
    }

    static void summer() {
         /*
                봄에 죽은 나무가 양분으로 변함
                 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다.
                  소수점 아래는 버린다.
                 */
        while (!dead.isEmpty()) {
            Tree tree = dead.poll();
            board[tree.r][tree.c] += tree.age / 2;
        }
    }

    static void fall() {
        /*
        가을에는 나무가 번식한다. 번식하는 나무는 나이가 5의 배수이어야 하며,
        인접한 8개의 칸에 나이가 1인 나무가 생긴다. 어떤 칸 (r, c)와 인접한 칸은
        (r-1, c-1), (r-1, c), (r-1, c+1), (r, c-1), (r, c+1), (r+1, c-1),
        (r+1, c), (r+1, c+1) 이다. 상도의 땅을 벗어나는 칸에는 나무가 생기지 않는다.
         */
        Queue<Tree> temp = new LinkedList<>();

        for (Tree tree : trees) {
            int r = tree.r;
            int c = tree.c;
            int age = tree.age;

            for (int k = 0; k < 8; k++) {
                int nr = r + dir[k][0];
                int nc = c + dir[k][1];

                if (age % 5 != 0) continue;
                if (nr < 1 || nc < 1 || nr > n || nc > n) continue;

                temp.add(new Tree(nr, nc, 1));
            }
        }

        trees.addAll(0, temp);
    }

    static void winter() {
        /*
         S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다. 각 칸에 추가되는 양분의 양은 A[r][c]이고, 입력으로 주어진다.
         */

        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++) {
                board[r][c] += addYangboon[r][c];
            }
        }
    }
}
