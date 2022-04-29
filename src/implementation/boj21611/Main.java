package implementation.boj21611;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    //https://codybuilder.com/48
    static BufferedReader br;
    static StringTokenizer st;
    static int n, m, sr, sc;
    static int map[], marble[];
    static int[] dr = {1, 0, -1, 0}; // 하, 우, 상, 좌
    static int[] dc = {0, 1, 0, -1};
    static int[][] bzIdx; // 블리자드 각 방향별 인덱스 좌, 하, 상, 우
    static int[] bzConvert = {0, 3, 1, 0, 2}; // 입력값 d를 알맞게 변환 ex) d=1 -> bzConvert[1]=3 -> bzIdx[3] 참조

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[][] input = new int[n][n];

        map = new int[n * n];
        marble = new int[4];
        bzIdx = new int[4][n / 2];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < n; j++)
                input[i][j] = Integer.parseInt(st.nextToken());
        }

        makeBzIdx(); // 블리자드 각 방향별로 1차원에서의 인덱스 생성
        to1D(input); // 2차원 입력을 1차원 map으로 변환

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            blizzard(bzConvert[d], s); // 블리자드 마법
            move(); // 구슬 이동

            // 터뜨릴거 없을 때 까지 구슬 폭발
            while (bomb()) {
            }

            change(); // 구슬 변화
        }
        System.out.println(1 * marble[1] + 2 * marble[2] + 3 * marble[3]);
    }

    // makeBzIdx: 1차원 map에서 블리자드 쏠 수 있게 인덱스 생성 (왼쪽, 아래, 오른쪽, 위 순)
    // ex) 왼쪽: 1, 10, 27 ..., 아래: 3, 14, 33...
    static void makeBzIdx() {
        int cnt = 1;
        int level = 1;
        for (int j = 0; j < (n / 2) / 1; j++) {
            for (int i = 0; i < 4; i++) {
                bzIdx[i][j] = cnt;

                if (i == 3)
                    cnt += 2 * level + 1;
                else
                    cnt += level * 2;
            }
            level++;
        }
    }

    // to1D: 2차원 input을 달팽이 모양으로 탐색하여 1차원 map으로 변환
    // 시작점 좌표를 각각 -1씩 하고 2칸씩 한바퀴, 4칸씩 한바퀴, 6칸씩 한바퀴... 접근
    static void to1D(int[][] input) {
        int r = n / 2; // 상어좌표 r,c
        int c = n / 2;
        int idx = 1; // 상어 위치 제외 1부터 시작

        for (int level = 1; level <= n / 2; level++) {
            r -= 1;
            c -= 1;
            for (int d = 0; d < 4; d++)
                for (int i = 0; i < level * 2; i++) {
                    r += dr[d];
                    c += dc[d];

                    map[idx++] = input[r][c];
                }
        }
    }

    // blizzard: bzdIdx를 참고하여 s만큼 0으로 만들기
    static void blizzard(int d, int s) {
        for (int i = 0; i < s; i++)
            map[bzIdx[d][i]] = 0;
    }

    // move: 구슬들 왼쪽으로 밀기
    static void move() {
        Queue<Integer> q = new ArrayDeque<Integer>();

        // map 탐색하여 0 아닌 값들 큐에 저장
        for (int i = 1; i < map.length; i++)
            if (map[i] != 0)
                q.offer(map[i]);

        // map 초기화 후 큐에서 뽑아 1번부터 채우기
        Arrays.fill(map, 0);
        for (int i = 1; i < map.length; i++) {
            if (q.isEmpty())
                break;
            map[i] = q.poll();
        }
    }

    // bomb: 연속된 구슬들 폭발
    static boolean bomb() {
        Deque<Integer> dq = new ArrayDeque<Integer>();
        int cnt = 1;
        boolean flag = false; // 한번이라도 터뜨렸는지 flag

        for (int i = 1; i < map.length; i++) {
            // 구슬 끝났으면 종료
            if (map[i] == 0)
                break;

            // 2번째 구슬부터 앞 구슬과 비교하여 카운트
            if (i != 1) {
                // 앞과 같으면 카운트
                if (map[i - 1] == map[i])
                    cnt++;

                    // 앞과 다르면
                else {
                    // cnt가 4이상일 때만 cnt만큼 덱에서 뽑아가며 구슬 터뜨리기
                    if (cnt >= 4) {
                        flag = true;

                        for (int c = 0; c < cnt; c++)
                            marble[dq.pollLast()]++;
                    }
                    cnt = 1;
                }
            }
            // 현재 구슬번호 덱에 삽입
            dq.offerLast(map[i]);
        }

        // 끝에 남은 연속된 구슬 처리
        if (cnt >= 4) {
            flag = true;
            for (int c = 0; c < cnt; c++)
                marble[dq.pollLast()]++;
        }

        // map 초기화 후 덱 앞에서부터 뽑아 1부터 저장
        Arrays.fill(map, 0);
        for (int i = 1; i < map.length; i++) {
            if (dq.isEmpty())
                break;
            map[i] = dq.pollFirst();
        }
        return flag; // 터뜨림 여부 반환
    }

    // change: 구슬 변화
    static void change() {
        // 모든 구슬 0이면 종료
        if (map[1] == 0)
            return;

        Queue<Integer> q = new ArrayDeque<Integer>();
        int cnt = 1;
        int i;

        // 2번 구슬부터 탐색
        for (i = 2; i < map.length; i++) {
            if (map[i] == 0)
                break;

            // 앞 구슬과 같으면 카운트
            if (map[i - 1] == map[i])
                cnt++;

                // 다르면 카운트와 앞 구슬 번호 큐에 삽입
            else {
                q.offer(cnt);
                q.offer(map[i - 1]);
                cnt = 1;
            }
        }

        // 마지막 구슬 처리
        q.offer(cnt);
        q.offer(map[i - 1]);

        // map 초기화 후 큐에서 뽑아 map에 1부터 저장
        Arrays.fill(map, 0);
        for (i = 1; i < map.length; i++) {
            if (q.isEmpty())
                break;
            map[i] = q.poll();
        }
    }
}
