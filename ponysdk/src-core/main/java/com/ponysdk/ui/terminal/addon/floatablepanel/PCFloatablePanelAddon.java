/*
 * Copyright (c) 2011 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *	Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *	Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
 *  
 *  WebSite:
 *  http://code.google.com/p/pony-sdk/
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ponysdk.ui.terminal.addon.floatablepanel;

import com.ponysdk.ui.terminal.Addon;
import com.ponysdk.ui.terminal.PonyAddOn;
import com.ponysdk.ui.terminal.Property;
import com.ponysdk.ui.terminal.PropertyKey;
import com.ponysdk.ui.terminal.UIService;
import com.ponysdk.ui.terminal.instruction.Create;
import com.ponysdk.ui.terminal.instruction.Update;
import com.ponysdk.ui.terminal.ui.PTScrollPanel;
import com.ponysdk.ui.terminal.ui.PTSimplePanel;
import com.ponysdk.ui.terminal.ui.PTWidget;

@PonyAddOn
public class PCFloatablePanelAddon extends PTSimplePanel implements Addon {

    public static final String SIGNATURE = "com.ponysdk.ui.terminal.addon.floatablepanel.PCFloatablePanelAddon";

    private PCFloatablePanel floatablePanel;

    @Override
    public void create(Create create, UIService uiService) {
        init(floatablePanel = new PCFloatablePanel());
    }

    @Override
    public void update(Update update, UIService uiService) {

        final Property mainProperty = update.getMainProperty();
        for (final Property property : mainProperty.getChildProperties().values()) {
            final PropertyKey propertyKey = property.getKey();
            if (PropertyKey.REFERENCE_SCROLL_PANEL.equals(propertyKey)) {
                final PTScrollPanel scrollPanel = (PTScrollPanel) uiService.getUIObject(property.getLongValue());
                floatablePanel.setLinkedScrollPanel(scrollPanel.cast());
            } else if (PropertyKey.CORRECT_DIMENSION.equals(propertyKey)) {
                floatablePanel.update();
                break;
            }
        }

    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }

    @Override
    public PTWidget asPTWidget() {
        return this;
    }

}