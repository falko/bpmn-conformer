/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.engine.impl.el;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * Expression implementation backed by XPath {@link javax.xml.xpath.XPathExpression}.
 *
 * @author Tim Stephenson
 */
public class XPathExpression implements Expression {

  protected String expressionText;
  protected javax.xml.xpath.XPathExpression valueExpression;
  protected DocumentBuilder docBuilder;

  public XPathExpression(javax.xml.xpath.XPathExpression valueExpression, String expressionText) {
    this.valueExpression = valueExpression;
    this.expressionText = expressionText;
  }

  public Object getValue(VariableScope variableScope) {
    try {
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      try {
        docBuilder = docBuilderFactory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
        throw new ProcessEngineException(e.getMessage(), e);
      }
      Document doc = docBuilder.newDocument();
      // TODO this is insufficient for all but simple data
      for(String varName : variableScope.getVariableNames()) {
        Element el = doc.createElement(varName);
        el.setTextContent(variableScope.getVariable(varName).toString());
      }
      Node node = doc;
      return valueExpression.evaluate(node);
    } catch (XPathExpressionException e) {
      throw new ProcessEngineException("Error while evaluating XPath expression: " + expressionText, e);
    }
  }
    
  public void setValue(Object value, VariableScope variableScope) {
    throw new RuntimeException("Not yet implemented");
  }
  
  @Override
  public String toString() {
    return getExpressionText();
  }
  
  public String getExpressionText() {
    return expressionText;
  }
}
