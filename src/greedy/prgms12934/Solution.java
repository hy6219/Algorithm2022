package greedy.prgms12934;

public class Solution {
    public static long solution(long n) {
        long answer = 0;
        double sqrt = Math.sqrt(n);
        double under = sqrt  - (int)sqrt;
        boolean isExist = (under == 0)? true:false;

        answer = isExist? (long)Math.pow((((long)sqrt)+1L),2) : -1L;

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(121));
        System.out.println(solution(122));
        System.out.println(solution(3));
    }
}
