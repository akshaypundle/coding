#include <iostream>
extern "C" {
  extern int my_js();


int doit() {
  std::cout << "hello my friend\n";
  std::cout << my_js() << "\n";
  return 0;
}
}
