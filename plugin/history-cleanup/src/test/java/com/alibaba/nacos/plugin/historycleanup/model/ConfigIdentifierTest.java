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

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ConfigIdentifierTest {

    @Test
    void testConstructor() {
        ConfigIdentifier identifier = new ConfigIdentifier("dataId", "group", "tenant");
        assertEquals("dataId", identifier.getDataId());
        assertEquals("group", identifier.getGroup());
        assertEquals("tenant", identifier.getTenant());
    }

    @Test
    void testSettersAndGetters() {
        ConfigIdentifier identifier = new ConfigIdentifier();
        identifier.setDataId("dataId");
        identifier.setGroup("group");
        identifier.setTenant("tenant");
        Map<String, String> tags = new HashMap<>();
        tags.put("tag1", "value1");
        identifier.setTags(tags);

        assertEquals("dataId", identifier.getDataId());
        assertEquals("group", identifier.getGroup());
        assertEquals("tenant", identifier.getTenant());
        assertEquals(tags, identifier.getTags());
    }

    @Test
    void testEquals() {
        ConfigIdentifier identifier1 = new ConfigIdentifier("dataId", "group", "tenant");
        ConfigIdentifier identifier2 = new ConfigIdentifier("dataId", "group", "tenant");
        ConfigIdentifier identifier3 = new ConfigIdentifier("dataId2", "group", "tenant");

        assertEquals(identifier1, identifier2);
        assertNotEquals(identifier1, identifier3);
    }

    @Test
    void testHashCode() {
        ConfigIdentifier identifier1 = new ConfigIdentifier("dataId", "group", "tenant");
        ConfigIdentifier identifier2 = new ConfigIdentifier("dataId", "group", "tenant");

        assertEquals(identifier1.hashCode(), identifier2.hashCode());
    }

    @Test
    void testToString() {
        ConfigIdentifier identifier = new ConfigIdentifier("dataId", "group", "tenant");
        String toString = identifier.toString();
        
        assert toString.contains("dataId");
        assert toString.contains("group");
        assert toString.contains("tenant");
    }
}
