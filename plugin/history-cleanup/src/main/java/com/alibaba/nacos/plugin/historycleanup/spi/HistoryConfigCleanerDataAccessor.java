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

package com.alibaba.nacos.plugin.historycleanup.spi;

import com.alibaba.nacos.plugin.historycleanup.model.ConfigIdentifier;

import java.sql.Timestamp;
import java.util.List;

/**
 * Data accessor interface for history config cleanup operations.
 *
 * @author xiweng.yy
 */
public interface HistoryConfigCleanerDataAccessor {

    /**
     * Remove config history records before the specified time.
     *
     * @param beforeTime the timestamp before which records should be removed
     * @param batchSize  the maximum number of records to remove in one batch
     */
    void removeConfigHistoryByTime(Timestamp beforeTime, int batchSize);

    /**
     * Count config history records before the specified time.
     *
     * @param beforeTime the timestamp before which records should be counted
     * @return the count of history records
     */
    int countConfigHistoryByTime(Timestamp beforeTime);

    /**
     * Count config history records for a specific config.
     *
     * @param dataId the data id of the config
     * @param group  the group of the config
     * @param tenant the tenant of the config
     * @return the count of history records for this config
     */
    int countConfigHistory(String dataId, String group, String tenant);

    /**
     * Remove config history records keeping only the specified count.
     *
     * @param dataId    the data id of the config
     * @param group     the group of the config
     * @param tenant    the tenant of the config
     * @param keepCount the number of history records to keep
     */
    void removeConfigHistoryByCountKeep(String dataId, String group, String tenant, int keepCount);

    /**
     * Get all config identifiers.
     *
     * @return list of all config identifiers
     */
    List<ConfigIdentifier> getAllConfigIdentifiers();

    /**
     * Get config identifiers by tenant.
     *
     * @param tenant the tenant to filter by
     * @return list of config identifiers for the specified tenant
     */
    List<ConfigIdentifier> getConfigIdentifiersByTenant(String tenant);
}
