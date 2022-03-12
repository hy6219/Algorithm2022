package greedy.prms92334;

import java.util.*;

public class Solution_Re {
    /**
     * 1.신고한 사람map<신고자,Set 신고된사람>
     * 2.신고된사람 map<신고대상자, 신고수>
     */

    public static int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];

        Map<String, HashSet<String>> notify = new HashMap<>();
        Map<String, Integer> notified = new HashMap<>();

        //1.초기화
        for (int i = 0; i < id_list.length; i++) {
            String key = id_list[i];
            notify.put(key, new HashSet<>());
            notified.put(key, 0);
        }



        //2.report 배열을 돌면서 신고자-신고대상자, 신고대상자-신고수 갱신
        StringTokenizer st;


        for (int i = 0; i < report.length; i++) {
            st = new StringTokenizer(report[i]);

            String host = st.nextToken();
            String sub = st.nextToken();

            //중요!! 유저1->유저2일때가 여러번이면 1번으로 간주!=>set으로!
            //신고map 갱신
            //신고하면, 신고된사람의 카운트도 갱신
            if(notify.get(host).add(sub)){
                notified.put(sub, notified.get(sub)+1);
            }
        }

        //3.신고수<K회 인 사람들은 신고map에서 제거
        for(int i = 0 ; i < report.length; i++){
            st = new StringTokenizer(report[i]);

            String host = st.nextToken();
            String sub = st.nextToken();

            if(notified.get(sub) < k){
                notify.get(host).remove(sub);//존재하면 제거할것(remove 메서드)
            }
        }

        //4.id_list 순서에 맞춰서 넣어줄것
        for(int i = 0 ; i < id_list.length; i++){
            String cur = id_list[i];
            answer[i] = notify.get(cur).size();
        }

        return answer;
    }

    public static void main(String[] args) {
        String[] id_list1= {"muzi", "frodo", "apeach", "neo"};
        String[] id_list2= {"con", "ryan"};

        String[] report1 = {"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"};
        String[] report2 = {"ryan con", "ryan con", "ryan con", "ryan con"};

        int k1 = 2, k2 = 3;

        int[] answer1 = solution(id_list1,report1,k1);
        int[] answer2 = solution(id_list2,report2,k2);

        System.out.println(Arrays.toString(answer1));
        System.out.println("---");
        System.out.println(Arrays.toString(answer2));
    }
}
