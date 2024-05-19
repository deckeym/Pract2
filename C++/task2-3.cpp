#include <iostream>
#include <vector>
#include <tuple>

using namespace std;

int NODwithCoefs(int c, int m, int& x, int& y) 
{
    if (m == 0) 
    {
        x = 1;
        y = 0;
        return c;
    }

    int x1, y1;
    int nod = NODwithCoefs(m, c % m, x1, y1);

    x = y1;
    y = x1 - (c / m) * y1;

    return nod;
}

int ReversedNum(int c, int m) 
{
    int x, y;
    int gcd = NODwithCoefs(c, m, x, y);
    if (gcd != 1) 
    {
        return -1;
    } 
    else 
    {
        return (x % m + m) % m;
    }
}

int main() {
    int c, d, m;
    cout << "Введите c и m через пробел" << endl;
    cin >> c >> m;
    if (ReversedNum(c, m) != -1) 
    {
        d = ReversedNum(c, m);
        cout << "Обратное число " << c << "^(-1) mod " << m << " = " << d << endl;

        cout << "Остаток (r)\tX\t\tY\tЧастное (q)" << endl;
        vector<tuple<int, int, int, int>> table;
        int a = c, b = m;
        while (b != 0) 
        {
            int q = a / b;
            int r = a % b;
            int x, y;
            NODwithCoefs(a, b, x, y);
            table.push_back({r, x, y, q});
            a = b;
            b = r;
        }
        for (auto n : table) 
        {
            cout << get<0>(n) << "\t\t" << get<1>(n) << "\t\t" << get<2>(n) << "\t\t" << get<3>(n) << endl;
        }
    }
    else 
    {
        cout << "Для данных значений обратного числа d по модулю не существует." << endl;
    }
}