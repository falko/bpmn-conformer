package com.camunda.bpm.demo.bpmn_xpath.nonarquillian;

import static org.camunda.spin.Spin.XML;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.camunda.spin.impl.xml.dom.SpinXPathFactoryInjector;
import org.camunda.spin.xml.SpinXmlElement;
import org.junit.Test;

public class XPathToSpinTest {

  @Test
  public void test() {
    // bpmn:getDataObject('approved')
    SpinXmlElement xml = XML("<emptyRootElement />");    
    assertTrue(xml.xPath("true()").bool());
    assertFalse(xml.xPath("false()").bool());
    assertTrue(xml.xPath(true + "()").bool());
    assertFalse(xml.xPath(false + "()").bool());
//    assertEquals("${XML('').xpath(approved + '()')}", "bpmn:getDataObject('approved')");
    // not(bpmn:getDataObject('approved'))
    assertFalse(xml.xPath("not(true())").bool());
    assertTrue(xml.xPath("not(false())").bool());
    assertFalse(xml.xPath("not(" + true + "()" + ")").bool());
    assertTrue(xml.xPath("not(" + false + "()" + ")").bool());
//    bpmn:getDataObject('clarified') = 'yes'
//    bpmn:getDataObject('clarified') = 'no'
    assertTrue(xml.xPath("'" + "yes" + "'" + " = 'yes'").bool());
    assertFalse(xml.xPath("'" + "no" + "'" + " = 'yes'").bool());

    // doesn't work anymore without process context...
//    SpinXPathFactoryInjector.injectBpmnXPathFunctionResolver();
//    assertTrue(xml.xPath("bpmn:getDataObject('approved')").bool());
  }

}
