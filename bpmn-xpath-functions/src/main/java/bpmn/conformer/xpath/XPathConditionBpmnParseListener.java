package bpmn.conformer.xpath;

import org.camunda.bpm.engine.impl.Condition;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParse;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

public class XPathConditionBpmnParseListener extends AbstractBpmnParseListener {

  @Override
  public void parseSequenceFlow(Element sequenceFlowElement, ScopeImpl scopeElement, TransitionImpl transition) {
    parseSequenceFlowConditionExpression(sequenceFlowElement, transition);
  }

  /**
   * Parses a condition expression on a sequence flow.
   *
   * @param seqFlowElement
   *          The 'sequenceFlow' element that can contain a condition.
   * @param seqFlow
   *          The sequenceFlow object representation to which the condition must
   *          be added.
   */
  public void parseSequenceFlowConditionExpression(Element seqFlowElement, TransitionImpl seqFlow) {
    Element conditionExprElement = seqFlowElement.element("conditionExpression");
    if (conditionExprElement != null) {
      String expression = conditionExprElement.getText().trim();
      String language = conditionExprElement.attribute("language");

      Condition condition = null;
      if (language == null) {
        condition = new XPathCondition(expression);
      }
      seqFlow.setProperty(BpmnParse.PROPERTYNAME_CONDITION, condition);
    }
  }

}
