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

import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFunctionResolver;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.impl.javax.el.ELContext;

import bpmn.conformer.xpath.BpmnXPathFunctionResolver;


/**
 * <p>
 * Central manager for XPath expressions.
 * </p>
 * <p>
 * Process parsers will use this to build expression objects that are stored in
 * the process definitions.
 * </p>
 * <p>
 * Then also this class is used as an entry point for runtime evaluation of the
 * expressions.
 * </p>
 * 
 * @author Tim Stephenson
 * @author Falko Menge (Camunda)
 */
public class XPathExpressionManager extends ExpressionManager {

  protected XPathFunctionResolver xpathFunctionResolver;
  protected XPathFactory expressionFactory;
  protected Map<Object, Object> beans;


  public XPathExpressionManager() {
	    this(null);
  }

  public XPathExpressionManager(Map<Object, Object> beans) {
	  initFactory();
	  this.beans = beans;
  }

  protected void initFactory() {
    expressionFactory = XPathFactory.newInstance();
    expressionFactory.setXPathFunctionResolver(getXPathFunctionResolver());
  }

  protected void setXpathFunctionResolver(XPathFunctionResolver functionResolver) {
    this.xpathFunctionResolver = functionResolver;
  }

  protected XPathFunctionResolver getXPathFunctionResolver() {
    if (xpathFunctionResolver ==null) {
      xpathFunctionResolver = new BpmnXPathFunctionResolver();
    }
    return xpathFunctionResolver;
  }

  public Expression createExpression(String expression) {
    if (expression.startsWith("${") || expression.startsWith("#{")) {
      return (Expression) new ExpressionManager().createExpression(expression);
    }
    try {
      XPath xpath = expressionFactory.newXPath();
      xpath.setXPathFunctionResolver(getXPathFunctionResolver());
      return new XPathExpression(xpath.compile(expression), expression.trim());
    } catch (XPathExpressionException e) {
//      throw new ProcessEngineException(String.format("Unable to compile the expression %1$s", expression));
      return (Expression) new ExpressionManager().createExpression(expression);
    }
  }

  public void setExpressionFactory(javax.xml.xpath.XPathFactory expressionFactory) {
    this.expressionFactory = expressionFactory;
  }

  public ELContext getElContext(VariableScope variableScope) {
    return new ExpressionManager().getElContext(variableScope);
  }

	public Map<Object, Object> getBeans() {
		return beans;
	}

	public void setBeans(Map<Object, Object> beans) {
		this.beans = beans;
	}
  
}
