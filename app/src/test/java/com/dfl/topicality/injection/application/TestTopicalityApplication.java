package com.dfl.topicality.injection.application;

import com.dfl.topicality.TopicalityApplication;
import com.dfl.topicality.injection.factories.ComponentsFactory;
import com.dfl.topicality.injection.factories.ModulesFactory;
import com.dfl.topicality.injection.factories.TestComponentsFactory;

public class TestTopicalityApplication extends TopicalityApplication {

    private ComponentsFactory componentsFactory;

    @Override
    public ComponentsFactory getComponentsFactory() {
        if (componentsFactory == null) {
            componentsFactory = new TestComponentsFactory(this, new ModulesFactory());
        }
        return componentsFactory;
    }
}
