package com.hyd.pipeline;

public interface Stage<C extends Context> {

    default void prepare(C context) {

    };

    default PlanResult plan(Plan<C> plan, C context) {
        return PlanResult.Default;
    };

    void process(C context);
}
