package com.camunda.bpm.demo.bpmn_xpath.nonarquillian;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.impl.util.LogUtil;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.spin.impl.xml.dom.SpinXPathFactoryInjector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.camunda.bpm.demo.bpmn_xpath.ArchiveServiceDelegateMock;
import com.camunda.bpm.demo.bpmn_xpath.LoggerDelegate;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  private static final String PROCESS_DEFINITION_KEY = "handle-invoice";
  private static final String BPMN_FILE = "4_Trisotech_handle-invoice.bpmn";

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
  @Deployment(resources = {BPMN_FILE, "xsdTypes.xsd"})
  public void testParsingAndDeployment() {
    // nothing is done here, as we just want to check for exceptions during deployment
  }

  @Test
  @Deployment(resources = {BPMN_FILE, "xsdTypes.xsd"})
  public void testExecution() {
//    SpinXPathFactoryInjector.injectBpmnXPathFunctionResolver();
    ArchiveServiceDelegateMock archiveService = new ArchiveServiceDelegateMock();
    Mocks.register("archiveService", archiveService);

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
    
    assertThat(processInstance)
      .isStarted()
      .task("assignApprover")
      .isAssignedTo("demo");
    
    complete(task(), withVariables("approver", "john"));
    
    assertThat(task("approveInvoice")).isAssignedTo("john");

    complete(task(), withVariables("approved", false));
    
    assertThat(task("reviewInvoice")).isAssignedTo("demo");
    
    complete(task(), withVariables("clarified", "yes"));

    assertThat(task("approveInvoice")).isAssignedTo("john");

    complete(task(), withVariables("approved", true));
    
    assertThat(task("prepareBankTransfer")).hasCandidateGroup("accounting");
    
    claim(task(), "Jane");
    
    complete(task());
    
    assertTrue(archiveService.isCalled());

    assertThat(processInstance).isEnded();
  }

}
