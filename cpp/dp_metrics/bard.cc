#include <iostream>
#include <algorithm>
#include <random>

#include "google/differential_privacy/core/status.h"
#include "google/differential_privacy/core/differential_privacy.h"
#include "google/differential_privacy/algorithms/laplace_mechanism.h"

using namespace google::differential_privacy;

int main() {
  // Create a random number generator.
  mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());

  // Set the privacy budget.
  double eps = 0.1;

  // Create a Laplace mechanism.
  LaplaceMechanism laplace_mechanism(eps);

  // Create a list of data.
  std::vector<double> data = {1.0, 2.0, 3.0, 4.0, 5.0};

  // Generate noisy values for the data.
  std::vector<double> noisy_data;
  for (double value : data) {
    noisy_data.push_back(laplace_mechanism.AddNoise(value));
  }

  // Print the noisy data.
  for (double noisy_value : noisy_data) {
    std::cout << "Noisy value: " << noisy_value << std::endl;
  }

  return 0;
}
