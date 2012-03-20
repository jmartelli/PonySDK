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

package com.ponysdk.ui.server.basic;

import java.util.Arrays;
import java.util.Iterator;

import com.ponysdk.ui.server.basic.event.HasPWidgets;
import com.ponysdk.ui.terminal.Property;
import com.ponysdk.ui.terminal.PropertyKey;
import com.ponysdk.ui.terminal.WidgetType;
import com.ponysdk.ui.terminal.instruction.Add;

public class PDisclosurePanel extends PWidget implements HasPWidgets {

    private static final String CLOSED = "images/disclosure_closed.png";
    private static final String OPENNED = "images/disclosure_openned.png";

    private PWidget content;

    public PDisclosurePanel(final String headerText) {
        this(headerText, new PImage(OPENNED, 0, 0, 14, 14), new PImage(CLOSED, 0, 0, 14, 14));
    }

    public PDisclosurePanel(final String headerText, final PImage openImage, final PImage closeImage) {
        super();
        final Property mainProperty = new Property(PropertyKey.TEXT, headerText);
        mainProperty.setProperty(PropertyKey.DISCLOSURE_PANEL_OPEN_IMG, openImage.getID());
        mainProperty.setProperty(PropertyKey.DISCLOSURE_PANEL_CLOSE_IMG, closeImage.getID());
        setMainProperty(mainProperty);
    }

    @Override
    protected WidgetType getWidgetType() {
        return WidgetType.DISCLOSURE_PANEL;
    }

    public void setContent(final PWidget w) {
        // Validate
        if (w == content) { return; }

        // Detach new child.
        if (w != null) {
            w.removeFromParent();
        }

        // Remove old child.
        if (content != null) {
            remove(content);
        }

        // Logical attach.
        content = w;

        if (w != null) {
            // Physical attach.
            final Add add = new Add(w.getID(), getID());
            getPonySession().stackInstruction(add);

            adopt(w);
        }
    }

    public PWidget getContent() {
        return content;
    }

    @Override
    public Iterator<PWidget> iterator() {
        return Arrays.asList(content).iterator();
    }

    @Override
    public void add(final PWidget w) {
        if (this.getContent() == null) {
            setContent(w);
        } else {
            throw new IllegalStateException("A DisclosurePanel can only contain two Widgets.");
        }
    }

    @Override
    public void add(final IsPWidget w) {
        add(w.asWidget());
    }

    @Override
    public void clear() {
        setContent(null);
    }

    @Override
    public boolean remove(final PWidget w) {
        if (w == getContent()) {
            setContent(null);
            return true;
        }
        return false;
    }

    private final void adopt(final PWidget child) {
        assert (child.getParent() == null);
        child.setParent(this);
    }

}