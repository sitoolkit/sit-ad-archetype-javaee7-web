package org.sitoolkit.ad.archetype.tips.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCaseLog implements TestRule {

    private Logger log;

    private static final Logger rawLog = LoggerFactory.getLogger("raw");

    public TestCaseLog(Class<?> type) {
        super();
        this.log = LoggerFactory.getLogger(type);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                rawLog.info("\n\n\n");
                log.info("===== start {} =====", description.getMethodName());

                base.evaluate();
                log.info("===== end {} =====", description.getMethodName());
            }
        };
    }

}
