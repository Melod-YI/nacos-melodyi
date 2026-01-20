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

import com.alibaba.nacos.common.spi.NacosServiceLoader;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.historycleanup.constant.Constants;
import com.alibaba.nacos.plugin.historycleanup.spi.HistoryConfigCleanerPluginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manager for history config cleaner plugins.
 *
 * @author xiweng.yy
 */
public class HistoryConfigCleanerPluginManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryConfigCleanerPluginManager.class);

    private static final Map<String, HistoryConfigCleanerPluginService> PLUGIN_SERVICE_MAP = new ConcurrentHashMap<>();

    private static final HistoryConfigCleanerPluginManager INSTANCE = new HistoryConfigCleanerPluginManager();

    private HistoryConfigCleanerPluginManager() {
        loadPluginServices();
    }

    private static void loadPluginServices() {
        Collection<HistoryConfigCleanerPluginService> pluginServices = NacosServiceLoader.load(
                HistoryConfigCleanerPluginService.class);
        for (HistoryConfigCleanerPluginService each : pluginServices) {
            if (StringUtils.isEmpty(each.getPluginName())) {
                LOGGER.warn(
                        "[HistoryConfigCleanerPluginManager] Load {}({}) plugin name is null/empty, skip loading.",
                        each.getClass().getName(), each.getClass());
                continue;
            }
            PLUGIN_SERVICE_MAP.put(each.getPluginName(), each);
            LOGGER.info("[HistoryConfigCleanerPluginManager] Load {}({}) plugin name({}) successfully.",
                    each.getClass().getName(), each.getClass(), each.getPluginName());
        }
    }

    public static HistoryConfigCleanerPluginManager getInstance() {
        return INSTANCE;
    }

    public Optional<HistoryConfigCleanerPluginService> findPluginServiceByName(String pluginName) {
        return Optional.ofNullable(PLUGIN_SERVICE_MAP.get(pluginName));
    }

    public HistoryConfigCleanerPluginService getPluginServiceOrDefault(String pluginName) {
        HistoryConfigCleanerPluginService pluginService = PLUGIN_SERVICE_MAP.get(pluginName);
        if (pluginService == null) {
            pluginService = PLUGIN_SERVICE_MAP.get(Constants.DEFAULT_PLUGIN_NAME);
        }
        return pluginService;
    }

    public Map<String, HistoryConfigCleanerPluginService> getAllPlugins() {
        return Collections.unmodifiableMap(PLUGIN_SERVICE_MAP);
    }

    /**
     * Register a plugin service dynamically.
     *
     * @param pluginService the plugin service to register
     */
    public static synchronized void join(HistoryConfigCleanerPluginService pluginService) {
        if (pluginService == null || StringUtils.isEmpty(pluginService.getPluginName())) {
            return;
        }
        PLUGIN_SERVICE_MAP.putIfAbsent(pluginService.getPluginName(), pluginService);
    }
}
