import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static int NODwithCoefs(int c, int m, int[] xy) {
        if (m == 0) {
            xy[0] = 1;
            xy[1] = 0;
            return c;
        }
        
        int[] xy1 = new int[2];
        int nod = NODwithCoefs(m, c % m, xy1);
        
        xy[0] = xy1[1];
        xy[1] = xy1[0] - (c / m) * xy1[1];
        
        return nod;
    }
    
    public static int ReversedNum(int c, int m) {
        int[] xy = new int[2];
        int gcd = NODwithCoefs(c, m, xy);
        if (gcd != 1) {
            return -1;
        } else {
            return (xy[0] % m + m) % m;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите c и m через пробел");
        int c = scanner.nextInt();
        int m = scanner.nextInt();
        
        if (ReversedNum(c, m) != -1) {
            int d = ReversedNum(c, m);
            System.out.println("Обратное число " + c + "^(-1) mod " + m + " = " + d);
            
            System.out.println("Остаток (r)\tX\t\tY\tЧастное (q)");
            List<int[]> table = new ArrayList<>();
            int a = c, b = m;
            while (b != 0) {
                int q = a / b;
                int r = a % b;
                int[] xy = new int[2];
                NODwithCoefs(a, b, xy);
                table.add(new int[]{r, xy[0], xy[1], q});
                a = b;
                b = r;
            }
            for (int[] n : table) {
                System.out.println(n[0] + "\t\t" + n[1] + "\t\t" + n[2] + "\t\t" + n[3]);
            }
        } else {
            System.out.println("Для данных значений обратного числа d по модулю не существует.");
        }
    }
}
