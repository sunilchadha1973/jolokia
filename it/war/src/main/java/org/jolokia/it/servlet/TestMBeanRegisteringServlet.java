package org.jolokia.it.servlet;


/*
 * Copyright 2009-2013 Roland Huss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;

import org.jolokia.it.core.ItSetup;


public class TestMBeanRegisteringServlet extends HttpServlet {

    ItSetup itSetup;

    @Override
    public void init(ServletConfig config) {
        itSetup = new ItSetup();
        itSetup.start();
    }

    @Override
    public void destroy() {
        itSetup.stop();
    }

}
