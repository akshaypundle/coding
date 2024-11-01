#include <iostream>
#include <thread>
#include <vector>
#include <sched.h>
#include <stdio.h>
#include <unistd.h> // for fork()

#include <sys/mman.h> // for shared memory created
#include <sys/stat.h> // for mode constants
#include <fcntl.h> // for O_* constant
#include <unistd.h> // for fork()
#include <stdio.h> // for printf
#include <stdlib.h> // for exit()
#include <sys/socket.h>
#include <sys/un.h> // socket in Unix

// for print error message
#include <string.h>
#include <errno.h>

#define SERVER_SOCK_PATH "af_unix.server"
#define CLIENT_SOCK_PATH "af_unix.client"
#define SERVER_MSG "HELLO FROM SERVER"
#define CLIENT_MSG "HELLO FROM CLIENT"

int main() {
  struct sockaddr_un server_addr;
  struct sockaddr_un client_addr;
  memset(&server_addr, 0, sizeof(server_addr));
  memset(&client_addr, 0, sizeof(client_addr));

  int rc;

  pid_t pid = fork();
  if(pid == 0) {
    std::cout << "parent \n";
  } else {
    std::cout << "child with pid "<< pid << "\n"; 
  }
}
