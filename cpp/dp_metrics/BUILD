load("@rules_cc//cc:defs.bzl", "cc_binary")

cc_binary(
    name = "dp_metrics",
    srcs = ["dp_metrics.cc"],
)

cc_binary(
    name = "bard",
    srcs = ["bard.cc"],
    deps = [
      "@google-differential-privacy//cc/algorithms:algorithm",
    ]
    
)


