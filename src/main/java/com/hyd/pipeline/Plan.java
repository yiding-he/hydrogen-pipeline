package com.hyd.pipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Plan<C extends Context> {

    private final List<Stage<C>> stages = new ArrayList<>();

    public Plan(Stage<C> lastStage) {
        this.stages.add(lastStage);
    }

    public List<Stage<C>> getStages() {
        return this.stages;
    }

    public Stage<C> getStage(int index) {
        return this.stages.get(index);
    }

    public int size() {
        return this.stages.size();
    }

    public void addStage(Stage<C> prev) {
        this.stages.add(prev);
    }

    public void reverse() {
        Collections.reverse(this.stages);
    }

    @Override
    public String toString() {
        return "Plan{" +
            "stages=" + stages.stream().map(s -> s.getClass().getSimpleName()).collect(Collectors.toList()) +
            '}';
    }
}
