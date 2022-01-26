package hashing.boj11796;

import java.util.*;

public class Main {
    static Scanner scan;
    static int N,M;
    static ArrayList<Integer> arr;
    static String arrStr;
    static ArrayList<Integer> selected;
    //(길이,갯수)
    static Map<Integer,Integer> map;


    static void input(){
        scan=new Scanner(System.in);
        N = scan.nextInt();
        M = scan.nextInt();

        scan.nextLine();

        arr =new ArrayList<>();
        for(int i = 0 ; i < N; i++){
            arr.add(scan.nextInt());
        }

        arrStr = arr.toString().replace("[","");
        arrStr = arrStr.replace("]","");
        arrStr = arrStr.replaceAll(", ","");
        map = new TreeMap<>();
    }

    /**
     * @param ord : 몇개 까지 뽑았는지 확인
     * @param limit : limit 개 까지 구성
     */
    static void makeList(int ord, int limit){
        if(ord == limit){
            //다뽑은 경우
            //입력리스트에 포함되는지 확인
            String comp = selected.toString().replace("[","");
            comp = comp.replace("]","");
            comp = comp.replaceAll(", ","");
            if(!arrStr.contains(comp)){
                //포함되지 않는다면, (길이, 갯수) 맵을 갱신
                int size = selected.size();
                boolean lenKey = map.containsKey(size);
                //기존에 값이 있었다면
                if(lenKey){
                    //값 갱신
                    map.put(size,map.get(size)+1);
                }else{
                    //기존에 값이 없었다면 값을 새로이 넣어주기
                    map.put(size,1);
                }
                return;
            }

        }else{
            for(int cand = 1; cand <=M; cand++){
                selected.add(cand);
                makeList(ord+1,limit);
                selected.remove(selected.size()-1);
            }
        }
    }

    static void checkPossible(){
        for(int i = 1; i <=N; i++){
            selected = new ArrayList<>();//매번 초기화
            makeList(0,i);
        }
    }

    //최솟값 get
    static void checkMinValue(){

        List<Map.Entry<Integer,Integer>> mapList =
                new LinkedList<>(map.entrySet());
        StringBuilder sb= new StringBuilder();
        if(mapList.size()==0){
            sb.append("0 0");
            System.out.print(sb);
            return;
        }
        //get(0)
        Integer minKey = mapList.get(0).getKey();
        Integer minVal = mapList.get(0).getValue();
        //나눌값
        Integer nanul = (int)Math.pow(10,9)+7;
        minVal %= nanul;
        sb.append(minKey).append(" ").append(minVal);
        System.out.print(sb);
    }

    static void pro(){
        input();
        checkPossible();
        checkMinValue();
    }

    public static void main(String[] args) {
        pro();
    }
}
