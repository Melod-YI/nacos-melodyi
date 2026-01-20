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
import com.alibaba.nacos.plugin.historycleanup.spi.HistoryConfigCleanerPluginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Default implementation of history config cleaner plugin service.
 *
 * @author xiweng.yy
 */
@SuppressWarnings("PMD.ServiceOrDaoClassShouldEndWithImplRule")
public class NacosDefaultHistoryConfigCleanerPluginService implements HistoryConfigCleanerPluginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosDefaultHistoryConfigCleanerPluginService.class);

    @Override
    public String getPluginName() {
        return Constants.DEFAULT_PLUGIN_NAME;
    }

    @Override
    public void doClean(HistoryCleanupContext context) {
        HistoryCleanupParameter parameter = context.getParameter();
        HistoryConfigCleanerDataAccessor dataAccessor = context.getDataAccessor();
        if (dataAccessor == null) {
            LOGGER.error("[NacosDefaultHistoryConfigCleanerPluginService] dataAccessor is null, skip cleaning.");
            return;
        }
        int retentionDays = parameter.getRetentionDays();
        int batchSize = parameter.getBatchSize();
        Timestamp beforeTime = getBeforeTimestamp(retentionDays);
        LOGGER.info("[NacosDefaultHistoryConfigCleanerPluginService] Start cleaning history config before {}, "
                + "retentionDays={}, batchSize={}", beforeTime, retentionDays, batchSize);
        dataAccessor.removeConfigHistoryByTime(beforeTime, batchSize);
        LOGGER.info("[NacosDefaultHistoryConfigCleanerPluginService] Finish cleaning history config.");
    }

    private Timestamp getBeforeTimestamp(int retentionDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -retentionDays);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(format.format(cal.getTime()));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
