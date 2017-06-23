/*
* Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.wso2.carbon.apimgt.rest.api.store.mappings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.apimgt.core.models.APIKey;
import org.wso2.carbon.apimgt.core.util.KeyManagerConstants;
import org.wso2.carbon.apimgt.rest.api.store.dto.ApplicationKeysDTO;

import java.util.List;
import java.util.Map;

public class ApplicationKeyMappingUtil {

    private static final Logger log = LoggerFactory.getLogger(ApplicationKeyMappingUtil.class);

    public static ApplicationKeysDTO fromApplicationKeysToDTO(APIKey apiKey) {
        ApplicationKeysDTO applicationKeyDTO = new ApplicationKeysDTO();
        applicationKeyDTO.setKeyType(ApplicationKeysDTO.KeyTypeEnum.valueOf(apiKey.getType()));
        applicationKeyDTO.setConsumerKey(apiKey.getConsumerKey());
        applicationKeyDTO.setConsumerSecret(apiKey.getConsumerSecret());
        applicationKeyDTO.setSupportedGrantTypes(null); //this is not supported by impl yet
        return applicationKeyDTO;
    }

    public static ApplicationKeysDTO fromApplicationKeysToDTO(Map<String, Object> keyDetails,
                                                              String applicationKeyType) {
        ApplicationKeysDTO applicationKeyDTO = new ApplicationKeysDTO();
        applicationKeyDTO.setConsumerKey((String) keyDetails.get(KeyManagerConstants.KeyDetails.CONSUMER_KEY));
        applicationKeyDTO.setConsumerSecret((String) keyDetails.get(KeyManagerConstants.KeyDetails.CONSUMER_SECRET));
        applicationKeyDTO.setSupportedGrantTypes(
                ((List<String>) keyDetails.get(KeyManagerConstants.KeyDetails.SUPPORTED_GRANT_TYPES)));
        String appDetailsString = (String) keyDetails.get(KeyManagerConstants.KeyDetails.APP_DETAILS);
        if (appDetailsString != null) {
            JsonObject appDetailsJsonObj = (JsonObject) new JsonParser().parse(appDetailsString);
            if (appDetailsJsonObj != null) {
                applicationKeyDTO.setKeyType(ApplicationKeysDTO.KeyTypeEnum.valueOf(applicationKeyType));
            }
        }
        return applicationKeyDTO;
    }
}
