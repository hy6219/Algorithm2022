package greedy.prgms12931;

public class Solution {
    public static int solution(int n) {
        int answer = 0;

        do {
            answer += (n % 10);
            n /= 10;
        } while (n > 0);

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(123));
        System.out.println(solution(987));
    }
}
