package greedy.prms92334;

import java.util.*;

public class Solution {
    /**
     * 1.신고map<신고자,신고대상자 list>
     * 2.신고대상자 map<신고대상자, 신고수>
     * 3.이메일 map<신고대상자, 신고수>
     */

    public static int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];

        Map<String, ArrayList<String>> notify = new HashMap<>();
        Map<String, Integer> notified = new HashMap<>();
        Map<String, Integer> email = new HashMap<>();

        //1.초기화
        for (int i = 0; i < id_list.length; i++) {
            String key = id_list[i];
            notify.put(key, new ArrayList<>());
            notified.put(key, 0);
            email.put(key, 0);
        }



        //2.report 배열을 돌면서 신고자-신고대상자, 신고대상자-신고수 갱신
        StringTokenizer st;

        for (int i = 0; i < report.length; i++) {
            st = new StringTokenizer(report[i]);

            String host = st.nextToken();
            String sub = st.nextToken();

            //중요!! 유저1->유저2일때가 여러번이면 1번으로 간주!
            //신고map 갱신
            ArrayList<String> list = notify.get(host);
            int notifiedCnt = notified.get(sub);

            if(!list.contains(sub)) {
                list.add(sub);
                notifiedCnt++;
            }
            notify.put(host, list);
            //신고수map(신고대상자map) 갱신
            notified.put(sub, notifiedCnt);
        }

        //3. "신고수map(신고대상자map)에서 신고수>=k인 대상자"가 value로 포함되어 있는지 신고map에서 확인 후 이메일map 갱신
        Iterator<Map.Entry<String, Integer>> iterator = notified.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> me = iterator.next();
            String sub = me.getKey();
            int notifiedCnt = me.getValue();

            if (notifiedCnt >= k) {
                //신고map에 해당 sub가 value로 있는 경우가 있었는지 확인
                // (굳이 map에 대해서 iterator를 또 만들지 않고! report배열을 돌면 됨)
                for (int i = 0; i < report.length; i++) {
                    String line = report[i];
                    //신고당한 사람의 인덱스는 0으로 시작하지 않을것! 임을 이용
                    int idx = line.indexOf(sub);
                    //신고한 사람 == sub 이거나/ 해당값이 없으면 스킵
                    if (idx <= 0) continue;
                    //신고 당한게 맞음
                    //신고한 사람
                    String host = line.substring(0, idx - 1);//공백까지 고려([0,idx-1)==[0,idx-2])
                    int val = email.get(host);
                    val++;
                    email.put(host, val);
                }
            }
        }

        //4.마지막으로 id_list 순서대로 email맵의 값을 담아주기
        for(int i = 0 ; i < id_list.length; i++){
            String host = id_list[i];
            int cnt = email.get(host);
            answer[i] = cnt;
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
