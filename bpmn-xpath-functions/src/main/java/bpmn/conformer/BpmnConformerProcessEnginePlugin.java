package bpmn.conformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.BpmnConformParseFactory;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import bpmn.conformer.xpath.XPathConditionBpmnParseListener;

public class BpmnConformerProcessEnginePlugin extends AbstractProcessEnginePlugin {

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    setBpmnConformParseFactory(processEngineConfiguration);
    
    List<BpmnParseListener> postParseListeners = processEngineConfiguration.getCustomPostBPMNParseListeners();
    if(postParseListeners == null) {
      postParseListeners = new ArrayList<BpmnParseListener>();
      processEngineConfiguration.setCustomPostBPMNParseListeners(postParseListeners);
    }
    postParseListeners.add(new XPathConditionBpmnParseListener());
  }

  private static void setBpmnConformParseFactory(ProcessEngineConfigurationImpl processEngineConfiguration) {
    Class<? extends ProcessEngineConfigurationImpl> clazz = processEngineConfiguration.getClass();
    Field field = getField(clazz, "bpmnParseFactory");
    field.setAccessible(true);
    try {
      field.set(processEngineConfiguration, new BpmnConformParseFactory());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static Field getField(Class<?> clazz, String fieldName) {
    try {
      return clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      Class<?> superClass = clazz.getSuperclass();
      if (superClass == null) {
        throw new RuntimeException(e);
      } else {
        return getField(superClass, fieldName);
      }
    }
  }
}
