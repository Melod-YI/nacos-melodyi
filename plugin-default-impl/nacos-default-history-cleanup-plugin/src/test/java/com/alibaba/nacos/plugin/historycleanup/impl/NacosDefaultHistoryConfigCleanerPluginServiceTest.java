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

package com.alibaba.nacos.plugin.historycleanup.impl;

import com.alibaba.nacos.plugin.historycleanup.constant.Constants;
import com.alibaba.nacos.plugin.historycleanup.model.HistoryCleanupContext;
import com.alibaba.nacos.plugin.historycleanup.model.HistoryCleanupParameter;
import com.alibaba.nacos.plugin.historycleanup.spi.HistoryConfigCleanerDataAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NacosDefaultHistoryConfigCleanerPluginServiceTest {

    @Mock
    private HistoryConfigCleanerDataAccessor dataAccessor;

    private NacosDefaultHistoryConfigCleanerPluginService pluginService;

    @BeforeEach
    void setUp() {
        pluginService = new NacosDefaultHistoryConfigCleanerPluginService();
    }

    @Test
    void testGetPluginName() {
        assertEquals(Constants.DEFAULT_PLUGIN_NAME, pluginService.getPluginName());
    }

    @Test
    void testGetOrder() {
        assertEquals(0, pluginService.getOrder());
    }

    @Test
    void testDoClean() {
        HistoryCleanupParameter parameter = new HistoryCleanupParameter();
        parameter.setRetentionDays(30);
        parameter.setBatchSize(1000);

        HistoryCleanupContext context = new HistoryCleanupContext();
        context.setParameter(parameter);
        context.setDataAccessor(dataAccessor);

        pluginService.doClean(context);

        verify(dataAccessor, times(1)).removeConfigHistoryByTime(any(Timestamp.class), eq(1000));
    }

    @Test
    void testDoCleanWithNullDataAccessor() {
        HistoryCleanupParameter parameter = new HistoryCleanupParameter();
        HistoryCleanupContext context = new HistoryCleanupContext();
        context.setParameter(parameter);
        context.setDataAccessor(null);

        pluginService.doClean(context);
    }

    @Test
    void testDoCleanWithCustomRetentionDays() {
        HistoryCleanupParameter parameter = new HistoryCleanupParameter();
        parameter.setRetentionDays(15);
        parameter.setBatchSize(500);

        HistoryCleanupContext context = new HistoryCleanupContext();
        context.setParameter(parameter);
        context.setDataAccessor(dataAccessor);

        pluginService.doClean(context);

        verify(dataAccessor, times(1)).removeConfigHistoryByTime(any(Timestamp.class), eq(500));
    }
}
