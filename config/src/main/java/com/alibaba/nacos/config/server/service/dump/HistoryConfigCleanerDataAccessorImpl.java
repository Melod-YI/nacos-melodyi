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

package com.alibaba.nacos.config.server.service.dump;

import com.alibaba.nacos.config.server.service.repository.HistoryConfigInfoPersistService;
import com.alibaba.nacos.plugin.historycleanup.model.ConfigIdentifier;
import com.alibaba.nacos.plugin.historycleanup.spi.HistoryConfigCleanerDataAccessor;

import java.sql.Timestamp;
import java.util.List;

/**
 * Implementation of HistoryConfigCleanerDataAccessor for config module.
 *
 * @author xiweng.yy
 */
public class HistoryConfigCleanerDataAccessorImpl implements HistoryConfigCleanerDataAccessor {

    private final HistoryConfigInfoPersistService historyConfigInfoPersistService;

    public HistoryConfigCleanerDataAccessorImpl(HistoryConfigInfoPersistService historyConfigInfoPersistService) {
        this.historyConfigInfoPersistService = historyConfigInfoPersistService;
    }

    @Override
    public void removeConfigHistoryByTime(Timestamp beforeTime, int batchSize) {
        historyConfigInfoPersistService.removeConfigHistory(beforeTime, batchSize);
    }

    @Override
    public int countConfigHistoryByTime(Timestamp beforeTime) {
        return historyConfigInfoPersistService.findConfigHistoryCountByTime(beforeTime);
    }

    @Override
    public int countConfigHistory(String dataId, String group, String tenant) {
        return historyConfigInfoPersistService.findConfigHistory(dataId, group, tenant, 1, 1).getTotalCount();
    }

    @Override
    public void removeConfigHistoryByCountKeep(String dataId, String group, String tenant, int keepCount) {
        // This method is reserved for future implementations that need to delete history records
        // based on count limit per config. The default implementation does not use this method.
        // Custom plugins can implement this feature.
    }

    @Override
    public List<ConfigIdentifier> getAllConfigIdentifiers() {
        // This method is reserved for future implementations that need to iterate over all configs.
        // The default implementation does not use this method.
        // Custom plugins can implement this feature.
        return List.of();
    }

    @Override
    public List<ConfigIdentifier> getConfigIdentifiersByTenant(String tenant) {
        // This method is reserved for future implementations that need to get configs by tenant.
        // The default implementation does not use this method.
        // Custom plugins can implement this feature.
        return List.of();
    }
}
