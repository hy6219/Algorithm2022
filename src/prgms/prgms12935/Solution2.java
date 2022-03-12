package prgms.prgms12935;

import java.util.Arrays;

//https://programmers.co.kr/learn/courses/30/lessons/12935/solution_groups?language=java 로 도전!
public class Solution2 {

    public static int[] solution(int[] arr) {
        if (arr.length <= 1) return new int[]{-1};
        int min = Arrays.stream(arr).min().getAsInt();
        return Arrays.stream(arr).filter(ele -> ele != min).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{4, 3, 2, 1})));
        System.out.println(Arrays.toString(solution(new int[]{10})));
    }
}
