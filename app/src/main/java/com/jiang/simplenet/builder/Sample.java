package com.jiang.simplenet.builder;

/**
 * Created by knowing on 2017/11/22.
 * <p>
 * How to use ?
 * <p>
 * Sample sample = new Sample.Builder()
 * .setTest(2)
 * .build();
 */

public class Sample {

    final int test;


    Sample(Builder builder) {
        this.test = builder.buildTest;
    }

    public static final class Builder {
        int buildTest;

        public Builder() {
            buildTest = 1;
        }

        public Builder setTest(int a) {
            this.buildTest = a;
            return this;
        }

        public Sample build() {
            return new Sample(this);
        }
    }
}
