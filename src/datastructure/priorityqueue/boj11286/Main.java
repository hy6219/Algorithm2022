package datastructure.priorityqueue.boj11286;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    static Scanner scanner;
    static int N;
    static PriorityQueue<Integer> values;
    static StringBuilder sb;

    static void pro() {
        scanner = new Scanner(System.in);
        sb = new StringBuilder();
        N = scanner.nextInt();
        values = new PriorityQueue<>((o1, o2) -> {
            int abs1 = Math.abs(o1);
            int abs2 = Math.abs(o2);

            if (abs1 == abs2) {
                return o1 > o2 ? 1 : -1;
            }

            return abs1 - abs2;
        });

        for (int i = 0; i < N; i++) {
            int num = scanner.nextInt();

            if (num == 0) {
                if (values.isEmpty()) {
                    sb.append(0).append('\n');
                } else {
                    sb.append(values.poll()).append('\n');
                }
            } else {
                values.add(num);
            }
        }
        System.out.println(sb.toString().strip());
    }

    public static void main(String[] args) {
        pro();
    }
}
