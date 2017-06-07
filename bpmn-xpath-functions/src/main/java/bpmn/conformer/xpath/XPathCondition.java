package bpmn.conformer.xpath;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.Condition;
import org.w3c.dom.Document;

public class XPathCondition implements Condition {

  private XPathExpression xPathExpression;

  public XPathCondition(String expression) {
    XPathFactory expressionFactory = XPathFactory.newInstance();
    expressionFactory.setXPathFunctionResolver(new BpmnXPathFunctionResolver());
    XPath xpath = expressionFactory.newXPath();
    try {
      xPathExpression = xpath.compile(expression);
    } catch (XPathExpressionException e) {
      throw new ProcessEngineException("Unable to compile XPath expression: " + expression, e);
    }
  }

  @Override
  public boolean evaluate(DelegateExecution execution) {
    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    Document doc;
    try {
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      doc = docBuilder.newDocument();
    } catch (ParserConfigurationException e) {
      throw new ProcessEngineException(e.getMessage(), e);
    }
    try {
      return Boolean.valueOf(xPathExpression.evaluate(doc));
    } catch (XPathExpressionException e) {
      throw new ProcessEngineException("Error while evaluating XPath expression: " + xPathExpression, e);
    }
  }

}
