package prgms.prgms12950;

import java.util.Arrays;

public class Solution {
    public static int[][] solution(int[][] arr1, int[][] arr2) {
        int ROW = arr1.length;
        int COL = arr1[0].length;
        int[][] answer = new int[ROW][COL];

        for(int i = 0 ; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                answer[i][j] = arr1[i][j] + arr2[i][j];
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[][] arr1 = {
                {1,2},
                {2,3}
        };

        int[][] arr2 = {
                {3,4},
                {5,6}
        };

        int[][] arr3 ={
                {1},{2}
        };

        int[][] arr4 ={
                {3},{4}
        };

        System.out.println(Arrays.deepToString(solution(arr1,arr2)));
        System.out.println(Arrays.deepToString(solution(arr3,arr4)));
    }
}
