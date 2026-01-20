/*
 * Copyright 1999-2024 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package com.alibaba.nacos.plugin.historycleanup;

import com.alibaba.nacos.plugin.historycleanup.model.HistoryCleanupContext;
import com.alibaba.nacos.plugin.historycleanup.spi.HistoryConfigCleanerPluginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoryConfigCleanerPluginManagerTest {

    @BeforeEach
    void setUp() {
        HistoryConfigCleanerPluginManager.join(new MockHistoryConfigCleanerPluginService());
    }

    @Test
    void testGetInstance() {
        HistoryConfigCleanerPluginManager instance = HistoryConfigCleanerPluginManager.getInstance();
        assertNotNull(instance);
    }

    @Test
    void testFindPluginServiceByName() {
        Optional<HistoryConfigCleanerPluginService> service = HistoryConfigCleanerPluginManager.getInstance()
                .findPluginServiceByName("mock");
        assertTrue(service.isPresent());
        assertEquals("mock", service.get().getPluginName());
    }

    @Test
    void testFindPluginServiceByNameNotFound() {
        Optional<HistoryConfigCleanerPluginService> service = HistoryConfigCleanerPluginManager.getInstance()
                .findPluginServiceByName("not-exist");
        assertFalse(service.isPresent());
    }

    @Test
    void testGetPluginServiceOrDefault() {
        HistoryConfigCleanerPluginService service = HistoryConfigCleanerPluginManager.getInstance()
                .getPluginServiceOrDefault("mock");
        assertNotNull(service);
        assertEquals("mock", service.getPluginName());
    }

    @Test
    void testGetPluginServiceOrDefaultNotFound() {
        HistoryConfigCleanerPluginService service = HistoryConfigCleanerPluginManager.getInstance()
                .getPluginServiceOrDefault("not-exist");
        assertNull(service);
    }

    @Test
    void testGetAllPlugins() {
        assertNotNull(HistoryConfigCleanerPluginManager.getInstance().getAllPlugins());
        assertTrue(HistoryConfigCleanerPluginManager.getInstance().getAllPlugins().containsKey("mock"));
    }

    static class MockHistoryConfigCleanerPluginService implements HistoryConfigCleanerPluginService {

        @Override
        public String getPluginName() {
            return "mock";
        }

        @Override
        public void doClean(HistoryCleanupContext context) {
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }
}
