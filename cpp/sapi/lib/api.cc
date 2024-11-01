#include <iostream>
#include "api.h"
#include <thread>
#include <chrono>

void print_message(std::string message) {
    using namespace std::chrono_literals;
  
    std::cout << "working for thread: "<<std::this_thread::get_id()<<"\n";
    std::this_thread::sleep_for(2000ms);
    std::cout << message << "\n";
    std::cout << "done working for thread: "<<std::this_thread::get_id()<<"\n";
}

std::thread multi_threaded_print_message(std::string message) {
  std::thread t(print_message, message);
  return t;
}
