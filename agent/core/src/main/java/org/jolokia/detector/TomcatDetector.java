/*
 * Copyright 2009-2010 Roland Huss
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

package org.jolokia.detector;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector for Tomcat
 *
 * @author roland
 * @since 05.11.10
 */
public class TomcatDetector extends AbstractServerDetector {

    private final static Pattern SERVER_INFO_PATTERN = Pattern.compile("^\\s*([^/]+)\\s*/\\s*([\\d\\.]+)");


    public ServerInfo detect(Set<MBeanServer> pMbeanServers) {
        Set<ObjectName> serverMBeanNames = searchMBeans(pMbeanServers,"*:type=Server");
        if (serverMBeanNames == null || serverMBeanNames.size() == 0) {
            return null;
        }
        Set<String> serverVersions = new HashSet<String>();
        for (ObjectName oName : serverMBeanNames) {
            Object val = getAttributeValue(pMbeanServers,oName,"serverInfo");
            if (val != null) {
                serverVersions.add(val.toString());
            }
        }
        if (serverVersions.size() == 0 || serverVersions.size() > 1) {
            return null;
        }
        String serverInfo = serverVersions.iterator().next();
        Matcher matcher = SERVER_INFO_PATTERN.matcher(serverInfo);
        if (matcher.matches()) {
            String product = matcher.group(1);
            String version = matcher.group(2);
            // TODO: Extract access URL
            if (product.toLowerCase().contains("tomcat")) {
                return new ServerInfo("Apache","tomcat",version,null,null);
            }
        }
        return null;
    }

    public int getPopularity() {
        return 95;
    }
}
