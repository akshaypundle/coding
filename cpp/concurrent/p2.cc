#include <iostream>
#include <thread>
#include <vector>
#include <sched.h>

void callable(int i) {
  for(int j=0;j< 1000000; j++) {
    std::cout << "called with " << i << " running on core "<<sched_getcpu()<< "\n";
  }
}

int main() {
  std::vector<std::thread> threads;
  
  for(int i=0;i<10;i++) {
    std::thread t1(callable, i);
    threads.push_back(std::move(t1));
  }
  for (int i = 0; i < 10; i++) {
    threads[i].join();
    std::cout << "Joined thread n'" << i << "\n";
  }
}
