package com.camunda.bpm.demo.bpmn_xpath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named("archiveService")
@ApplicationScoped
public class ArchiveServiceDelegateMock implements JavaDelegate {
  
  private Expression text0;
  private Expression text1;
  private Expression text2;
  private Expression text3;
  private Expression text4;

	private boolean called = false;
	
	public void execute(DelegateExecution execution) throws Exception {
		called = true;
	}
	
	public void reset() {
		called = false;
	}
	
	public boolean isCalled() {
		return called;
	}
}
