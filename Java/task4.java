import java.util.Random;
import java.math.BigInteger;

public class Main {

    // Функция для возведения числа в степень по модулю
    public static BigInteger modPow(BigInteger base, BigInteger exponent, BigInteger modulus) {
        return base.modPow(exponent, modulus);
    }

    // Функция для проверки числа на простоту
    public static boolean isPrime(BigInteger n) {
        return n.isProbablePrime(100); // Используем встроенную функцию проверки на простоту с вероятностью 1-2^-100
    }

    // Функция для поиска простого числа в заданном диапазоне
    public static BigInteger findPrime(BigInteger min, BigInteger max) {
        for (BigInteger i = min; i.compareTo(max) <= 0; i = i.add(BigInteger.ONE)) {
            if (isPrime(i)) {
                return i;
            }
        }
        return BigInteger.valueOf(-1);
    }

    // Функция для нахождения обратного элемента по модулю
    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        return a.modInverse(m);
    }

    // Функция для кодирования строки
    public static String encodeString(String input, BigInteger k) {
        StringBuilder encoded = new StringBuilder(input);
        for (int i = 0; i < input.length(); i++) {
            encoded.setCharAt(i, (char) (input.charAt(i) ^ k.intValue()));
        }
        return encoded.toString();
    }

    // Функция для декодирования строки (та же функция, что и для кодирования)
    public static String decodeString(String encoded, BigInteger k) {
        return encodeString(encoded, k);
    }

    // Функция для вывода строки в шестнадцатеричном формате
    public static void printHex(String str) {
        for (char c : str.toCharArray()) {
            System.out.printf("%02x ", (int) c);
        }
        System.out.println();
    }

    // Главная функция
    public static void main(String[] args) {
        final BigInteger gMin = BigInteger.valueOf(2);
        final BigInteger gMax = BigInteger.valueOf(10000);
        final BigInteger nMin = BigInteger.valueOf(10000);
        final BigInteger nMax = BigInteger.valueOf(100000);

        // Инициализация генератора случайных чисел
        Random rand = new Random();
        BigInteger g, n, x, k;
        n = findPrime(nMin, nMax); // Поиск простого числа в заданном диапазоне
        if (n.equals(BigInteger.valueOf(-1))) {
            System.out.println("Простое число в заданном диапазоне не найдено");
            return; // Завершаем программу, если простое число не найдено
        }

        do {
            g = new BigInteger(gMax.bitLength(), rand).mod(gMax.subtract(gMin)).add(gMin); // Генерация случайного числа g
            x = new BigInteger(nMax.bitLength(), rand).mod(nMax.subtract(nMin)).add(BigInteger.ONE); // Генерация случайного числа x
            k = modPow(g, x, n); // Вычисление k = g^x mod n

            if (!k.equals(BigInteger.ONE) && !k.equals(BigInteger.ZERO)) {
                break; // Прерываем цикл, если k не равно 1 и не равно 0
            }
        } while (true);

        System.out.println("Выбранное значение g: " + g);
        System.out.println("Выбранное значение n: " + n);
        System.out.println("Выбранное значение x: " + x);

        BigInteger y;
        do {
            y = new BigInteger(n.subtract(BigInteger.ONE).bitLength(), rand).add(BigInteger.ONE); // Генерация случайного числа y
        } while (!y.gcd(n.subtract(BigInteger.ONE)).equals(BigInteger.ONE)); // Проверка взаимной простоты с модулем

        System.out.println("Выбранное значение y: " + y);

        BigInteger Y = modPow(g, y, n); // Вычисление Y = g^y mod n
        BigInteger X = modPow(Y, x, n); // Вычисление X = Y^x mod n
        BigInteger z = modInverse(y, n.subtract(BigInteger.ONE)); // Вычисление обратного элемента z

        BigInteger kPrime = modPow(X, z, n); // Вычисление k' = X^z mod n
        if (k.equals(kPrime)) {
            System.out.println("Закрытый ключ k = " + k);
            String originalString = "Hello, world!"; // Исходная строка
            System.out.println("Исходная строка: " + originalString);

            String encodedString = encodeString(originalString, k); // Кодирование строки
            System.out.print("Закодированная строка (hex): ");
            printHex(encodedString); // Вывод закодированной строки в hex формате

            String decodedString = decodeString(encodedString, k); // Декодирование строки
            System.out.println("Декодированная строка: " + decodedString);
        } else {
            System.out.println("Произошла ошибка при вычислении закрытого ключа");
        }
    }
}
