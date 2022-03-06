package implementation.boj21608;

import java.util.*;

public class Main {

    static class Student {
        private int[] friends;//친구들 번호
        private int r;
        private int c;

        public Student(int[] friends, int r, int c) {
            this.friends = friends;
            this.r = r;
            this.c = c;
        }
    }

    static Scanner scanner;
    static int N;
    static Map<Integer, Student> students;
    static int limit;
    static int[][] map;
    //빈좌석
    static int[][] empty;
    static int[][] dir = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    static void pro() {
        scanner = new Scanner(System.in);

        N = scanner.nextInt();

        students = new HashMap<>();

        limit = (int) Math.pow(N, 2);
        map = new int[N + 1][N + 1];

        //점수 초기화
        emptySeat();

        for (int i = 1; i <= limit; i++) {
            int no = scanner.nextInt();
            int[] friends = new int[4];

            for (int j = 0; j < 4; j++) {
                friends[j] = scanner.nextInt();
            }
            //친한친구들 자리배치
            fillSeat(no, friends);
        }

        //만족도 조사
        int sat = satisfyTest();
        System.out.println(sat);
    }

    //영역밖을 초기화
    static void emptySeat() {
        empty = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                int cnt = 4;//친구는 네명
                if (i == 1 || i == N) cnt--;
                if (j == 1 || j == N) cnt--;

                empty[i][j] = cnt;
            }
        }
    }

    /**
     * 자리 채우기
     *
     * @param me
     * @param friends
     */
    static void fillSeat(int me, int[] friends) {
        int[][] nearFriend = new int[N + 1][N + 1];
        //친한 친구인접 위치 점수 높이기
        for (int friend : friends) {
            if (students.containsKey(friend)) {
                Student student = students.get(friend);
                int r = student.r;
                int c = student.c;
                //친구들 근처 인접칸 점수 높이기
                for (int i = 0; i < 4; i++) {
                    int nr = r + dir[i][0];
                    int nc = c + dir[i][1];

                    if (nr <= 0 || nc <= 0 || nr > N || nc > N) continue;
                    //빈칸이 아닌 경우
                    if (map[nr][nc] != 0) continue;
                    nearFriend[nr][nc]++;
                }
            }
        }
        /**
         * 친구인접순 이후에는 빈자리가 가장 많은칸, 그다음에는 행번호 적은것, 열번호 적은것
         */
        int nearScoreMax = -1;//인접점수
        int emptyScoreMax = -1;//빈자리점수
        //행
        int scoreRow = -1;
        //열
        int scoreCol = -1;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                //이미 자리가 채워져 있으면 들어갈 수 없음
                if (map[i][j] != 0) continue;

                /**
                 * 저장된 인접점수보다 높다면 인접점수가 높은 칸을 차지해주기 위해서
                 * 인접점수를 갱신해주고
                 *
                 * 빈점수, 행, 열도 마찬가지 원리로 갱신
                 */
                if (nearScoreMax < nearFriend[i][j]) {
                    nearScoreMax = nearFriend[i][j];
                    emptyScoreMax = empty[i][j];
                    scoreRow = i;
                    scoreCol = j;
                } else if (nearScoreMax == nearFriend[i][j] && emptyScoreMax < empty[i][j]) {
                    //최대 인접점수가 같으면, 빈점수순
                    emptyScoreMax = empty[i][j];
                    //scoreRow,ScoreCol에 대한 처리는 emptyScoreMax==empty[i][j] 상황에서 반복되므로 더 수행해줄 필요없음
                    scoreRow = i;
                    scoreCol = j;
                }
            }
        }

        //선택된 r,c값에 학생 앉히기
        map[scoreRow][scoreCol] = me;
        //MAP 구조 완성
        students.put(me, new Student(friends, scoreRow, scoreCol));
        //선택한 자리 기준 빈자리 점수 낮춰주기
        for (int i = 0; i < 4; i++) {
            int nr = scoreRow + dir[i][0];
            int nc = scoreCol + dir[i][1];

            if (nr <= 0 || nc <= 0 || nr > N || nc > N) continue;
            if (map[nr][nc] != 0) continue;

            //빈칸인 경우만 걸러짐
            empty[nr][nc]--;
        }
    }

    /**
     * 만족도 조사 진행
     */
    static int satisfyTest() {
        int ans = 0;

        for (int i = 1; i <= limit; i++) {
            Student student = students.get(i);
            //인접한 학생수 카운트
            int near = 0;
            int r = student.r;
            int c = student.c;

            for (int friend : student.friends) {
                int r1 = students.get(friend).r;
                int c1 = students.get(friend).c;
                int d = (int) (Math.abs(r - r1) + Math.abs(c - c1));
                if (d == 1) near++;
            }

            //near갯수에 따른 더해질 점수는 10^(near-1)로 보여짐
            if (near > 0) ans += Math.pow(10, near - 1);
        }

        return ans;
    }

    public static void main(String[] args) {
        pro();
    }
}
