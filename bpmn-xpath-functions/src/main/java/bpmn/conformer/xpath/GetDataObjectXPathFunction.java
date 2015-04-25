package bpmn.conformer.xpath;

import java.util.List;

import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;

import org.camunda.bpm.engine.impl.context.Context;

public class GetDataObjectXPathFunction implements XPathFunction {

  @Override
  public Object evaluate(List args) throws XPathFunctionException {
    if (args.size() == 1) {
      String variableName = (String) args.get(0);
      return Context.getBpmnExecutionContext().getExecution().getVariable(variableName);
    } else {
      throw new RuntimeException("The XPath function getDataObject need one variable name as an input, but the number of inputs was " + args.size() + ".");
    }
  }

}
