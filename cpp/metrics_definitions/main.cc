#include "stdio.h"
#include "stdlib.h"
#include "string.h"
#include "sys/sysinfo.h"
#include "sys/times.h"
#include "sys/types.h"
#include <iostream>
#include <unistd.h>
#include <iostream>
#include <cstdlib>

void ActualLogScalar(int val) { std::cout << "Int val "<<val<<"\n"; }

void ActualLogScalar(double val) { std::cout << "Double val "<<val<<"\n"; }

template<typename VALUE_TYPE> class MeterWrapper {};

template<> class MeterWrapper<double> {
  public:
  static void getCounter(double val) {ActualLogScalar(val);};
};

template<> class MeterWrapper<int> {
  public:
  static void getCounter(int val) {ActualLogScalar(val);};
};

template <typename VALUE_TYPE> class AbstractMetricDefinition {
  std::string name;
};

template <typename VALUE_TYPE> class ScalarMetric : public AbstractMetricDefinition<VALUE_TYPE> {
};


template <typename VALUE_TYPE, typename PARTITION_TYPE> class HistogramMetric : public AbstractMetricDefinition<VALUE_TYPE> {
};

template <class T> void LogScalarMetric(ScalarMetric<T> m, T value) {
  MeterWrapper<T>::getCounter(value);
}

template <class T, class U> void LogHistogramMetric(HistogramMetric<T, U> m, T value, U partition) {
}


ScalarMetric CPU = ScalarMetric<double>();
ScalarMetric RAM = ScalarMetric<int>();
HistogramMetric CPU_BY_SOMETHING = HistogramMetric<double, std::string>();



int main(void) {
  LogScalarMetric(CPU, 1.0);
  LogScalarMetric(RAM, 1);
  LogHistogramMetric(CPU_BY_SOMETHING, 1.0, std::string("1"));
    
  return 0;

}
