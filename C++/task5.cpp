#include <iostream>

using namespace std;

long pow_mod(long a, int x, int p) {
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

int main() {
    int a, b, c;
    cout << "Введите этажи нужного числа: ";
    cin >> a >> b >> c;
    int expon = b * c;
    int result = pow_mod(a, expon, 10);
    cout << "Последняя цифра: " << result << endl;
    return 0;
}