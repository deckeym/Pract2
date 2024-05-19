import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите a: ");
        String astr = scanner.nextLine();
        if (!isIntOrNot(astr)) {
            return;
        }
        int a = Integer.parseInt(astr);

        System.out.print("Введите x: ");
        String xstr = scanner.nextLine();
        if (!isIntOrNot(xstr)) {
            return;
        }
        int x = Integer.parseInt(xstr);

        System.out.print("Введите b: ");
        String bstr = scanner.nextLine();
        if (!isIntOrNot(bstr)) {
            return;
        }
        int b = Integer.parseInt(bstr);

        System.out.print("Введите y: ");
        String ystr = scanner.nextLine();
        if (!isIntOrNot(ystr)) {
            return;
        }
        int y = Integer.parseInt(ystr);

        System.out.print("Введите p: ");
        String pstr = scanner.nextLine();
        if (!isIntOrNot(pstr)) {
            return;
        }
        int p = Integer.parseInt(pstr);

        if (!isPrime(p)) {
            if (!isEuler(a, x, p)) {
                System.out.println("Для " + a + "^" + x + " mod " + p + " теорема Эйлера не выполняется");
            }
            if (!isEuler(b, y, p)) {
                System.out.println("Для " + b + "^" + y + " mod " + p + " теорема Эйлера не выполняется");
            }
            if (isEuler(a, x, p) && isEuler(b, y, p)) {
                if (powMod(a, x % euler(p), p) == powMod(b, y % euler(p), p)) {
                    System.out.println("(" + a + "^" + x + " mod " + p + " = " + powMod(a, x % euler(p), p) + ") = (" + b + "^" + y + " mod " + p + " = " + powMod(b, y % euler(p), p) + ")");
                } else {
                    System.out.println("(" + a + "^" + x + " mod " + p + " = " + powMod(a, x % euler(p), p) + ") != (" + b + "^" + y + " mod " + p + " = " + powMod(b, y % euler(p), p) + ")");
                }
            } else {
                if (powMod(a, x, p) == powMod(b, y, p)) {
                    System.out.println("(" + a + "^" + x + " mod " + p + " = " + powMod(a, x, p) + ") = (" + b + "^" + y + " mod " + p + " = " + powMod(b, y, p) + ")");
                } else {
                    System.out.println("(" + a + "^" + x + " mod " + p + " = " + powMod(a, x, p) + ") != (" + b + "^" + y + " mod " + p + " = " + powMod(b, y, p) + ")");
                }
            }
        } else {
            if (!isEuler(a, x, p)) {
                System.out.println("Для " + a + "^" + x + " mod " + p + " теорема Ферма не выполняется");
            }
            if (!isEuler(b, y, p)) {
                System.out.println("Для " + b + "^" + y + " mod " + p + " теорема Ферма не выполняется");
            }
            if (isEuler(a, x, p) && isEuler(b, y, p)) {
                if (powMod(a, x % (p - 1), p) == powMod(b, y % (p - 1), p)) {
                    System.out.println("(" + a + "^" + x + " mod " + p + " = " + powMod(a, x % (p - 1), p) + ") = (" + b + "^" + y + " mod " + p + " = " + powMod(b, y % (p - 1), p) + ")");
                } else {
                    System.out.println("(" + a + "^" + x + " mod " + p + " = " + powMod(a, x % (p - 1), p) + ") != (" + b + "^" + y + " mod " + p + " = " + powMod(b, y % (p - 1), p) + ")");
                }
            } else {
                if (powMod(a, x, p) == powMod(b, y, p)) {
                    System.out.println("(" + a + "^" + x + " mod " + p + " = " + powMod(a, x, p) + ") = (" + b + "^" + y + " mod " + p + " = " + powMod(b, y, p) + ")");
                } else {
                    System.out.println("(" + a + "^" + x + " mod " + p + " = " + powMod(a, x, p) + ") != (" + b + "^" + y + " mod " + p + " = " + powMod(b, y, p) + ")");
                }
            }
        }
    }

    public static boolean isPrime(int p) {
        if (p <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(p); i++) {
            if (p % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int NOD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return NOD(b, a % b);
    }

    public static boolean isEuler(int a, int x, int p) {
        return NOD(a, p) == 1;
    }

    public static int euler(int p) {
        int result = p;
        for (int i = 2; i * i <= p; i++) {
            if (p % i == 0) {
                while (p % i == 0) {
                    p /= i;
                }
                result -= result / i;
            }
        }
        if (p > 1) {
            result -= result / p;
        }
        return result;
    }

    public static boolean isIntOrNot(String symb) {
        if (symb.charAt(0) == '-') {
            symb = symb.substring(1);
        }
        return Pattern.matches("\\d+", symb);
    }

    public static int powMod(int a, int x, int p) {
        int result = 1;
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
}
