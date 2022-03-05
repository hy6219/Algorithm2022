package dfs.prgms43165;

public class Solution {

    /**
     * +로도, - 계산
     */
    static int cnt;

    public static int solution(int[] numbers, int target) {
        cnt = 0;
        dfs(numbers, 0, target, 0);
        return cnt;
    }

    static void dfs(int[] numbers, int depth, int target, int calc) {
        //모든 입력배열을 다 돌때까지 진행
        if (depth == numbers.length) {
            if (calc == target) {
                //계산값 일치
                cnt++;
            }
            return;
        }
        dfs(numbers, depth + 1, target, calc + numbers[depth]);
        dfs(numbers, depth + 1, target, calc - numbers[depth]);
    }

    public static void main(String[] args) {
        int[] numbers1 = {1, 1, 1, 1, 1};
        int[] numbers2 = {4, 1, 2, 1};

        System.out.println(solution(numbers1,3));
        System.out.println(solution(numbers2,4));
    }
}
