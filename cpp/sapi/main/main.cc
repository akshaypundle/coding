#include "lib/api.h"
#include <iostream>
#include <string>
#include <thread>

void task1(std::string msg) {
    std::cout << "task1 says: " << msg;
}

int main(int argc, char** argv) {
  std::string message = "hello world";
  if (argc > 1) {
    message = argv[1];
  }
  std::thread t1 = multi_threaded_print_message("1");

  t1.join();
  return 0;
}
