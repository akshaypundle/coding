#include <initializer_list>
#include <vector>
#include <ranges>
#include <iostream>
using namespace std;
int main(int argc, char ** argv) {
  auto odd = [](int x) { return x % 2 == 0; };
  vector v = {2,4,6,8,20};
  for(int x: v | views::filter(odd) | views::take(3)) {
    std::cout << x << "\n";
   }
}
