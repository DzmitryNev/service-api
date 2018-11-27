/*
 * Copyright 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.ws.rabbit;

import com.epam.ta.reportportal.auth.ReportPortalUser;
import com.epam.ta.reportportal.auth.basic.DatabaseUserDetailsService;
import com.epam.ta.reportportal.core.item.FinishTestItemHandler;
import com.epam.ta.reportportal.core.item.StartTestItemHandler;
import com.epam.ta.reportportal.util.ProjectExtractor;
import com.epam.ta.reportportal.ws.handler.StartTestItemAsyncHandler;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.epam.ta.reportportal.commons.EntityUtils.normalizeId;
import static com.epam.ta.reportportal.core.configs.RabbitMqConfiguration.QUEUE_START_ITEM;

/**
 * @author Pavel Bortnik
 */
@Component
@Transactional
public class TestReporterConsumer {

    private DatabaseUserDetailsService userDetailsService;

    private StartTestItemAsyncHandler startTestItemHandler;

    private FinishTestItemHandler finishTestItemHandler;

    @Autowired
    public TestReporterConsumer(DatabaseUserDetailsService userDetailsService, StartTestItemAsyncHandler startTestItemHandler,
                                FinishTestItemHandler finishTestItemHandler) {
        this.userDetailsService = userDetailsService;
        this.startTestItemHandler = startTestItemHandler;
        this.finishTestItemHandler = finishTestItemHandler;
    }

    @RabbitListener(queues = QUEUE_START_ITEM)
    public void onStartItem(@Header(name = MessageHeaders.PARENT_ID, required = false) Long parentId,
                            @Header(MessageHeaders.ITEM_ID) Long itemId,
                            @Payload StartTestItemRQ rq) {
        if (parentId == null) {
            startTestItemHandler.startRootItem(rq, itemId);
        } else {
            startTestItemHandler.startChildItem(rq, itemId, parentId);
        }
    }

    @RabbitListener(queues = "#{ @finishItemQueue.name }")
    public void onFinishItem(@Header(MessageHeaders.USERNAME) String username, @Header(MessageHeaders.PROJECT_NAME) String projectName,
                             @Header(MessageHeaders.ITEM_ID) Long itemId, @Payload FinishTestItemRQ rq) {
        ReportPortalUser user = (ReportPortalUser) userDetailsService.loadUserByUsername(username);
        finishTestItemHandler.finishTestItem(user, ProjectExtractor.extractProjectDetails(user, normalizeId(projectName)), itemId, rq);
    }

}
