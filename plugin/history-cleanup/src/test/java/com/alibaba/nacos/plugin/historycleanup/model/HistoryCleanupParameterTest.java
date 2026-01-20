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

package com.alibaba.nacos.plugin.historycleanup.model;

import com.alibaba.nacos.plugin.historycleanup.constant.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class HistoryCleanupParameterTest {

    @Test
    void testDefaultValues() {
        HistoryCleanupParameter parameter = new HistoryCleanupParameter();
        assertEquals(Constants.DEFAULT_RETENTION_DAYS, parameter.getRetentionDays());
        assertEquals(Constants.DEFAULT_BATCH_SIZE, parameter.getBatchSize());
        assertNotNull(parameter.getExtendInfoMap());
    }

    @Test
    void testSetRetentionDays() {
        HistoryCleanupParameter parameter = new HistoryCleanupParameter();
        parameter.setRetentionDays(15);
        assertEquals(15, parameter.getRetentionDays());
    }

    @Test
    void testSetBatchSize() {
        HistoryCleanupParameter parameter = new HistoryCleanupParameter();
        parameter.setBatchSize(500);
        assertEquals(500, parameter.getBatchSize());
    }

    @Test
    void testExtendInfo() {
        HistoryCleanupParameter parameter = new HistoryCleanupParameter();
        parameter.putExtendInfo("key1", "value1");
        parameter.putExtendInfo("key2", 123);

        assertEquals("value1", parameter.getExtendInfo("key1"));
        assertEquals(123, parameter.getExtendInfo("key2"));
        assertNull(parameter.getExtendInfo("key3"));

        assertEquals("value1", parameter.getExtendInfo("key1", String.class));
        assertEquals(Integer.valueOf(123), parameter.getExtendInfo("key2", Integer.class));
    }
}
