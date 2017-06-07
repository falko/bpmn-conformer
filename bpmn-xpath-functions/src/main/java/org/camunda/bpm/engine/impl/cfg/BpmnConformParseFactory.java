package org.camunda.bpm.engine.impl.cfg;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParse;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParser;

import bpmn.conformer.imp0rt.BpmnConformParse;

public class BpmnConformParseFactory implements BpmnParseFactory {

  @Override
  public BpmnParse createBpmnParse(BpmnParser bpmnParser) {
    return new BpmnConformParse(bpmnParser);
  }

}