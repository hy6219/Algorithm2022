package prgms.prgms12935;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static int[] solution(int[] arr) {
        //java 8+부터는 asList는 int[], double[]과 같은 primitive 타입 배열지원x(wrapper/참조형 변수 배열 지원!!)
        //https://defacto-standard.tistory.com/20
        //https://stackoverflow.com/questions/880581/how-can-i-convert-int-to-integer-in-java

        //UnsupportedOperationException => asList로 생성된 리스트는 제거remove가 어려움!=>new arraylist로 감싸주어야!
        List<Integer> list = new ArrayList<>(Arrays.asList(Arrays.stream(arr).boxed().toArray(Integer[]::new)));
        int min = list.stream().min((e1,e2)->{
            return e1-e2;
        }).get();

        if(list.size() == 1){
            list.set(0,-1);
        }else{
            list.remove(list.indexOf(min));
        }

        int[] answer = list.stream().mapToInt(Integer::intValue).toArray();

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{4,3,2,1})));
        System.out.println(Arrays.toString(solution(new int[]{10})));
    }
}
