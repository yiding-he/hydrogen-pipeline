package com.hyd.pipeline;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Pipeline<C extends Context> {

    private final LinkedList<Stage<C>> stageList = new LinkedList<>();

    public void addStage(Stage<C> stage) {
        this.stageList.add(stage);
    }

    public void process(C context) {
        if (stageList.isEmpty()) {
            return;
        }

        // prepare
        for (Stage<C> cStage : stageList) {
            cStage.prepare(context);
        }

        // plan
        Iterator<Stage<C>> iterator = stageList.descendingIterator();
        Plan<C> plan = new Plan<>(iterator.next());
        int i = 0;
        do {
            PlanResult planResult = plan.getStage(i).plan(context);
            if (planResult == PlanResult.Default) {
                plan.addStage(iterator.next());
            }
            i++;
        } while (i < plan.size() && iterator.hasNext());
        plan.reverse();

        log.info("Created plan: {}", plan);

        // process
        List<Stage<C>> planStages = plan.getStages();
        for (Stage<C> planStage : planStages) {
            planStage.process(context);
        }
    }
}
