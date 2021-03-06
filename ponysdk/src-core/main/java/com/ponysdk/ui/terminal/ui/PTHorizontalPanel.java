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

package com.ponysdk.ui.terminal.ui;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.ponysdk.ui.terminal.Dictionnary.PROPERTY;
import com.ponysdk.ui.terminal.UIService;
import com.ponysdk.ui.terminal.basic.PHorizontalAlignment;
import com.ponysdk.ui.terminal.basic.PVerticalAlignment;
import com.ponysdk.ui.terminal.instruction.PTInstruction;

public class PTHorizontalPanel extends PTCellPanel<HorizontalPanel> {

    @Override
    public void create(final PTInstruction create, final UIService uiService) {
        init(create, uiService, new HorizontalPanel());
    }

    @Override
    public void add(final PTInstruction add, final UIService uiService) {
        uiObject.insert(asWidget(add.getObjectID(), uiService), add.getInt(PROPERTY.INDEX));
    }

    @Override
    public void update(final PTInstruction update, final UIService uiService) {
        if (update.containsKey(PROPERTY.BORDER_WIDTH)) {
            uiObject.setBorderWidth(update.getInt(PROPERTY.BORDER_WIDTH));
        } else if (update.containsKey(PROPERTY.SPACING)) {
            uiObject.setSpacing(update.getInt(PROPERTY.SPACING));
        } else if (update.containsKey(PROPERTY.HORIZONTAL_ALIGNMENT)) {
            final PHorizontalAlignment horizontalAlignment = PHorizontalAlignment.values()[update.getInt(PROPERTY.HORIZONTAL_ALIGNMENT)];
            switch (horizontalAlignment) {
                case ALIGN_LEFT:
                    uiObject.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                    break;
                case ALIGN_CENTER:
                    uiObject.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
                    break;
                case ALIGN_RIGHT:
                    uiObject.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
                    break;
                default:
                    break;
            }
        } else if (update.containsKey(PROPERTY.VERTICAL_ALIGNMENT)) {
            final PVerticalAlignment verticalAlignment = PVerticalAlignment.values()[update.getInt(PROPERTY.VERTICAL_ALIGNMENT)];
            switch (verticalAlignment) {
                case ALIGN_TOP:
                    uiObject.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
                    break;
                case ALIGN_MIDDLE:
                    uiObject.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
                    break;
                case ALIGN_BOTTOM:
                    uiObject.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
                    break;
                default:
                    break;
            }
        } else {
            super.update(update, uiService);
        }

    }

}
