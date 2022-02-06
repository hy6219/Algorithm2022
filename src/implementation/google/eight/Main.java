package implementation.google.eight;

public class Main {
    static int cnt;

    static void doSomething(){
        cnt = 0;

        for(int value = 1; value <=10000; value++){
            searchEight(value);
        }
        System.out.println(cnt);
    }
    //문제 출처 : https://wonderbout.tistory.com/186
    static void searchEight(int val){
        if(val%10==8) cnt++;//1의자리가 8인 경우
        if(val > 10) searchEight(val/10);//10으로 나눈 값으로 다시 생각해보기
    }

    public static void main(String[] args) {
        doSomething();
    }
}
