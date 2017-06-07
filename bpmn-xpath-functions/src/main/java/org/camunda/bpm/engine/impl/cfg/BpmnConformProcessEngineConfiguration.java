package org.camunda.bpm.engine.impl.cfg;

import org.camunda.bpm.engine.impl.bpmn.deployer.BpmnDeployer;
//import org.camunda.bpm.container.impl.jboss.config.ManagedJtaProcessEngineConfiguration

public class BpmnConformProcessEngineConfiguration extends /*Managed*/JtaProcessEngineConfiguration {

  @Override
  protected BpmnDeployer getBpmnDeployer() {
    bpmnParseFactory = new BpmnConformParseFactory();
    return super.getBpmnDeployer();
  }
  
}
