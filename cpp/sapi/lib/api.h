#ifndef LIB_API
#define LIB_API
#include <iostream>
#include <thread>
void print_message(std::string message);
std::thread multi_threaded_print_message(std::string message);
#endif
