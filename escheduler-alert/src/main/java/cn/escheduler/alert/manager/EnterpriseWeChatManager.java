/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.escheduler.alert.manager;

import cn.escheduler.alert.utils.Constants;
import cn.escheduler.alert.utils.EnterpriseWeChatUtils;
import cn.escheduler.dao.model.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enterprise WeChat Manager
 */
public class EnterpriseWeChatManager {
    private static final Logger logger = LoggerFactory.getLogger(MsgManager.class);
    /**
     * Enterprise We Chat send
     * @param alert
     */
    public Map<String,Object> send(Alert alert, String token){
        Map<String,Object> retMap = new HashMap<>();
        retMap.put(Constants.STATUS, false);
        String agentId = EnterpriseWeChatUtils.enterpriseWeChatAgentId;
        String users = EnterpriseWeChatUtils.enterpriseWeChatUsers;
        List<String> userList = Arrays.asList(users.split(","));
        logger.info("send message {}",alert);

        List<String> alertMsgList = EnterpriseWeChatUtils.markdownByAlert(alert);

        try {
            for(String alertMsg:alertMsgList){
                String msg = EnterpriseWeChatUtils.makeUserSendMsg(userList, agentId,alertMsg);
                EnterpriseWeChatUtils.sendEnterpriseWeChat(Constants.UTF_8, msg, token);
            }

        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        retMap.put(Constants.STATUS, true);
        return retMap;
    }

}
