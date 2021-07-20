package com.hyd.pipeline;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class PipelineTest {

    @Data
    static class MyContext implements Context {
        private String result;
    }

    static class MyStage1 implements Stage<MyContext> {

        @Override
        public void process(MyContext context) {
            log.info("Processing stage 1");
        }
    }

    static class MyStage2 implements Stage<MyContext> {

        @Override
        public PlanResult plan(Plan<MyContext> plan, MyContext context) {
            return PlanResult.OmitPrevStage;
        }

        @Override
        public void process(MyContext context) {
            log.info("Processing stage 2");
            context.setResult("ok");
        }
    }

    static class MyStage3 implements Stage<MyContext> {
        @Override
        public void process(MyContext context) {
            log.info("Processing stage 3");
        }
    }

    @Test
    public void testCreatePipeline() throws Exception {
        MyStage1 stage1 = new MyStage1();
        MyStage2 stage2 = new MyStage2();
        MyStage3 stage3 = new MyStage3();

        Pipeline<MyContext> pipeline = new Pipeline<>();
        pipeline.addStage(stage1);
        pipeline.addStage(stage2);
        pipeline.addStage(stage3);

        MyContext context = new MyContext();
        pipeline.process(context);

        assertEquals("ok", context.getResult());
    }
}
