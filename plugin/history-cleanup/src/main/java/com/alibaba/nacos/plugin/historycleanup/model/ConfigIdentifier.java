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

import java.util.Map;
import java.util.Objects;

/**
 * Config identifier for history cleanup.
 *
 * @author xiweng.yy
 */
public class ConfigIdentifier {

    private String dataId;

    private String group;

    private String tenant;

    private Map<String, String> tags;

    public ConfigIdentifier() {
    }

    public ConfigIdentifier(String dataId, String group, String tenant) {
        this.dataId = dataId;
        this.group = group;
        this.tenant = tenant;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConfigIdentifier that = (ConfigIdentifier) o;
        return Objects.equals(dataId, that.dataId) && Objects.equals(group, that.group)
                && Objects.equals(tenant, that.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataId, group, tenant);
    }

    @Override
    public String toString() {
        return "ConfigIdentifier{" + "dataId='" + dataId + '\'' + ", group='" + group + '\'' + ", tenant='" + tenant
                + '\'' + '}';
    }
}
