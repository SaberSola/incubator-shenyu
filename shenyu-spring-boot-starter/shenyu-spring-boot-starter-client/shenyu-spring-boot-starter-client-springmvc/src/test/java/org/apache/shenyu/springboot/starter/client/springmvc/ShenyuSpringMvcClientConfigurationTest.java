/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.springboot.starter.client.springmvc;

import org.apache.shenyu.client.springmvc.init.ContextRegisterListener;
import org.apache.shenyu.client.springmvc.init.SpringMvcClientBeanPostProcessor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.assertNotNull;

/**
 * Test case for {@link ShenyuSpringMvcClientConfiguration}.
 */
@Configuration
@EnableConfigurationProperties
public class ShenyuSpringMvcClientConfigurationTest {

    private ApplicationContextRunner applicationContextRunner;

    @Before
    public void before() {
        applicationContextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ShenyuSpringMvcClientConfiguration.class))
            .withBean(ShenyuSpringMvcClientConfigurationTest.class)
            .withPropertyValues(
                "debug=true",
                "shenyu.register.registerType=http",
                "shenyu.register.serverLists=http://localhost:9095",
                "shenyu.client.http.props[contextPath]=/http",
                "shenyu.client.http.props[appName]=http",
                "shenyu.client.http.props[port]=8189"
            );
    }

    @Test
    public void testSpringMvcClientBeanPostProcessor() {
        applicationContextRunner.run(context -> {
            SpringMvcClientBeanPostProcessor processor = context.getBean("springHttpClientBeanPostProcessor", SpringMvcClientBeanPostProcessor.class);
            assertNotNull(processor);
        });
    }

    @Test
    public void testContextRegisterListener() {
        applicationContextRunner.run(context -> {
            ContextRegisterListener listener = context.getBean("contextRegisterListener", ContextRegisterListener.class);
            assertNotNull(listener);
        });
    }
}