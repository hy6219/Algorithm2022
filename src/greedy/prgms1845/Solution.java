package greedy.prgms1845;

import java.util.*;

public class Solution {
    /**
     * n마리 중 n/2마리 뽑을 것인데 종류가 가장 많아야 함!(직접 가짓수 계산x)
     * 1.n/2계산
     * 2.중복제거해서 map 크기와 비교
     */

    public static int solution(int[] nums) {
        int answer = 0;

        //1.n/2
        int LIMIT = nums.length / 2;
        //map구조
        Map<Integer, Integer> mon = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            //특이하게, 기존 마리수를 계산하는 것이 아니고 종류만 카운트하므로 무조건! 1로 값을 넣어주면 됨
            mon.put(nums[i], 1);
        }


        /**
         * 고른 갯수와 "선택가능한 종류수"를 비교해서 고른 갯수< 선택가능한 종류수 이면, 고른갯수
         * 그 외에는 "선택 가능한 종류수"가 최대 종류수
         *
         * ==>if(고른갯수 LIMIT < 선택가능한 종류수 mon.size()){
         *      return LIMIT;
         * }else{
         *      return mon.size();
         * }
         * ==>min!!
         */

        answer = Math.min(LIMIT,mon.size());

        return answer;
    }


    public static void main(String[] args) {
        int[] n1 = {3, 1, 2, 3};
        int[] n2 = {3, 3, 3, 2, 2, 4};
        int[] n3 = {3, 3, 3, 2, 2, 2};

        int a1 = solution(n1);
        int a2 = solution(n2);
        int a3 = solution(n3);

        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
    }
}
