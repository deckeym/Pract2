#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include <cmath>
#include <iomanip>
#include <vector>
#include <random>
#include <algorithm>

using namespace std;

// Функция для возведения числа в степень по модулю
long long modPow(long long base, long long exponent, long long modulus) 
{
    if (modulus == 1) return 0; // Если модуль равен 1, возвращаем 0
    long long result = 1;
    base %= modulus; // Приводим base к диапазону [0, modulus-1]
    while (exponent > 0) 
    { // Пока степень больше 0
        if (exponent % 2 == 1) 
        { // Если степень нечетная
            result = (result * base) % modulus; // Умножаем результат на base по модулю
        }
        base = (base * base) % modulus; // Возводим base в квадрат по модулю
        exponent /= 2; // Делим степень на 2
    }
    return result; // Возвращаем результат
}

// Функция для проверки числа на простоту
bool isPrime(long long n) 
{
    if (n < 2) 
    {
        return false; // Числа меньше 2 не являются простыми
    }
    for (long long i = 2; i * i <= n; i++) 
    {
        if (n % i == 0) 
        { // Если число делится на i, оно не простое
            return false;
        }
    }
    return true; // Если не нашлось делителей, число простое
}

// Функция для поиска простого числа в заданном диапазоне
long long findPrime(long long min, long long max) 
{
    for (long long i = min; i <= max; ++i) 
    {
        if (isPrime(i)) return i; // Если найдено простое число, возвращаем его
    }
    return -1; // Если простое число не найдено, возвращаем -1
}

// Функция для нахождения обратного элемента по модулю
long long modInverse(long long a, long long m) 
{
    long long m0 = m, t, q;
    long long x0 = 0, x1 = 1;
    if (m == 1) return 0; // Если модуль равен 1, обратного элемента не существует
    while (a > 1) 
    {
        q = a / m; // Частное от деления a на m
        t = m;
        m = a % m; // Остаток от деления a на m
        a = t;
        t = x0;
        x0 = x1 - q * x0; // Обновление x0 и x1
        x1 = t;
    }
    if (x1 < 0) x1 += m0; // Приведение x1 к положительному значению
    return x1; // Возвращаем обратный элемент
}

// Функция для кодирования строки
string encodeString(const string& input, long long k) 
{
    string encoded = input; // Копируем входную строку
    for (size_t i = 0; i < input.length(); ++i) 
    {
        encoded[i] = input[i] ^ k; // Кодируем каждый символ путем XOR с k
    }
    return encoded; // Возвращаем закодированную строку
}

// Функция для декодирования строки (та же функция, что и для кодирования)
string decodeString(const string& encoded, long long k) 
{
    return encodeString(encoded, k); // Декодируем строку путем вызова encodeString
}

// Функция для вывода строки в шестнадцатеричном формате
void printHex(const string& str) 
{
    for (char c : str) 
    {
        cout << setw(2) << setfill('0') << hex << static_cast<int>(static_cast<unsigned char>(c)) << " ";
    }
    cout << endl; // Переход на новую строку после вывода всех символов
}

// Главная функция
int main() 
{
    const long long gMin = 2;
    const long long gMax = 10000;
    const long long nMin = 10000;
    const long long nMax = 100000;

    // Инициализация генератора случайных чисел
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<long long> dis_g(gMin, gMax);
    uniform_int_distribution<long long> dis_n(nMin, nMax - 1);

    long long g, n, x, k;
    n = findPrime(nMin, nMax); // Поиск простого числа в заданном диапазоне
    if (n == -1) 
    {
        cout << "Простое число в заданном диапазоне не найдено" << endl;
        return 1; // Завершаем программу, если простое число не найдено
    }

    do 
    {
        g = dis_g(gen); // Генерация случайного числа g
        x = dis_n(gen) + 1; // Генерация случайного числа x
        k = modPow(g, x, n); // Вычисление k = g^x mod n

        if (k != 1 && k != 0) 
        {
            break; // Прерываем цикл, если k не равно 1 и не равно 0
        }
    } while (true);

    cout << "Выбранное значение g: " << g << endl;
    cout << "Выбранное значение n: " << n << endl;
    cout << "Выбранное значение x: " << x << endl;

    uniform_int_distribution<long long> dis_y(1, n - 2);
    long long y = dis_y(gen); // Генерация случайного числа y
    cout << "Выбранное значение y: " << y << endl;

    long long Y = modPow(g, y, n); // Вычисление Y = g^y mod n
    long long X = modPow(Y, x, n); // Вычисление X = Y^x mod n
    long long z = modInverse(y, n - 1); // Вычисление обратного элемента z
    if (z == -1)
    {
        cout << "Ошибка: обратный элемент для y не существует" << endl;
        return 1; // Завершаем программу, если обратный элемент не найден
    }

    long long kPrime = modPow(X, z, n); // Вычисление k' = X^z mod n
    if (k == kPrime) 
    {
        cout << "Закрытый ключ k = " << k << endl;
        string originalString = "Hello, world!"; // Исходная строка
        cout << "Исходная строка: " << originalString << endl;

        string encodedString = encodeString(originalString, k); // Кодирование строки
        cout << "Закодированная строка (hex): ";
        printHex(encodedString); // Вывод закодированной строки в hex формате

        string decodedString = decodeString(encodedString, k); // Декодирование строки
        cout << "Декодированная строка: " << decodedString << endl;
    } 
    else 
    {
        cout << "Произошла ошибка при вычислении закрытого ключа" << endl;
    }

    return 0; // Завершаем программу
}
