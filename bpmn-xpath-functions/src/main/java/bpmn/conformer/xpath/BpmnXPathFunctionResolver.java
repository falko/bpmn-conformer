package bpmn.conformer.xpath;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionResolver;

public class BpmnXPathFunctionResolver implements XPathFunctionResolver {

  @Override
  public XPathFunction resolveFunction(QName functionName, int arity) {
    if ("getDataObject".equals(functionName.getLocalPart())) {
      return new GetDataObjectXPathFunction();
    }
    return null;
  }

}
