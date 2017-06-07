package bpmn.conformer.imp0rt;

import java.util.List;

import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParser;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.util.xml.Element;

public class ImportBpmnParseListener extends AbstractBpmnParseListener {
  
  public ImportBpmnParseListener() {
  }
  
  @Override
  public void parseRootElement(Element rootElement, List<ProcessDefinitionEntity> processDefinitions) {
    List<Element> imports = rootElement.elementsNS(BpmnParser.BPMN20_NS, "import");
    for (Element theImport : imports) {
      String importType = theImport.attribute("importType");
      if (!importType.equals("http://schemas.xmlsoap.org/wsdl/")) {
        rootElement.elements().remove(theImport);
      }
    }
  }

}
