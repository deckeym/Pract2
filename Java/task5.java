import java.util.Scanner;

public class Main {
    public static long powMod(long a, int x, int p) {
        long result = 1;
        a = a % p;
        while (x > 0) {
            if (x % 2 == 1) {
                result = (result * a) % p;
            }
            a = (a * a) % p;
            x /= 2;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите этажи нужного числа: ");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int expon = b * c;
        int result = (int)powMod(a, expon, 10);
        System.out.println("Последняя цифра: " + result);
        scanner.close();
    }
}
