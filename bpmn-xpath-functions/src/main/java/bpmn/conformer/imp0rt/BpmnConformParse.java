package bpmn.conformer.imp0rt;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParse;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParser;

public class BpmnConformParse extends BpmnParse {

  public BpmnConformParse(BpmnParser parser) {
    super(parser);
  }
  
  @Override
  protected void parseImports() {
    // ignore imports completely for now
  }

}
