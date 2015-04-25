package org.camunda.spin.impl.xml.dom;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFunctionResolver;

import bpmn.conformer.xpath.BpmnXPathFunctionResolver;

public class SpinXPathFactoryInjector {

  public static void inject(XPathFactory xPathFactory) {
    DomXmlElement.cachedXPathFactory = xPathFactory;
  }

  public static XPathFactory getOriginalXPathFactory() {
    return XPathFactory.newInstance();
  }

  public static void injectBpmnXPathFunctionResolver() {
    XPathFactory xPathFactory = SpinXPathFactoryInjector.getOriginalXPathFactory();
    XPathFunctionResolver resolver = new BpmnXPathFunctionResolver();
    xPathFactory.setXPathFunctionResolver(resolver);
    SpinXPathFactoryInjector.inject(xPathFactory);
  }

}
