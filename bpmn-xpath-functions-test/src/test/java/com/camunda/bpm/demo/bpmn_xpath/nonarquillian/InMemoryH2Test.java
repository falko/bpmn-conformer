package com.camunda.bpm.demo.bpmn_xpath.nonarquillian;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.impl.util.LogUtil;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.spin.impl.xml.dom.SpinXPathFactoryInjector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  private static final String PROCESS_DEFINITION_KEY = "bpmn-xpath-functions-test";

  // enable more detailed logging
  static {
//    LogUtil.readJavaUtilLoggingConfigFromClasspath(); // process engine
//    LogFactory.useJdkLogging(); // MyBatis
  }
  
  @Before
  public void setup() {
	init(rule.getProcessEngine());
  }

  /**
   * Just tests if the process definition is deployable.
   */
  @Test
  @Deployment(resources = {"C.1.0_demo.W4.bpmn", "xsdTypes.xsd"})
  public void testParsingAndDeployment() {
    // nothing is done here, as we just want to check for exceptions during deployment
  }

  @Test
  @Deployment(resources = {"C.1.0_demo.W4.bpmn", "xsdTypes.xsd"})
  public void testExecution() {
    SpinXPathFactoryInjector.injectBpmnXPathFunctionResolver();

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("Bpmn_Process_qWsuYoXrEeSJyfDq3KCXkg", withVariables("approved", true, "clarified", "yes"));
    assertThat(processInstance).isStarted()
      .task();
    complete(task());
    complete(task());
  }

}
