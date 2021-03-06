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

package com.ponysdk.sample.client.page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ponysdk.sample.client.event.DemoBusinessEvent;
import com.ponysdk.ui.server.basic.PDateBox;
import com.ponysdk.ui.server.basic.PDatePicker;
import com.ponysdk.ui.server.basic.PLabel;
import com.ponysdk.ui.server.basic.PNotificationManager;
import com.ponysdk.ui.server.basic.PVerticalPanel;
import com.ponysdk.ui.server.basic.event.PShowRangeEvent;
import com.ponysdk.ui.server.basic.event.PShowRangeHandler;
import com.ponysdk.ui.server.basic.event.PValueChangeEvent;
import com.ponysdk.ui.server.basic.event.PValueChangeHandler;

public class DatePickerPageActivity extends SamplePageActivity {

    private final SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private PDateBox dateBox;
    private PDatePicker datePicker;

    public DatePickerPageActivity() {
        super("Date Picker", "Widgets");
    }

    @Override
    protected void onFirstShowPage() {
        super.onFirstShowPage();

        final PVerticalPanel panel = new PVerticalPanel();
        panel.setSpacing(10);

        datePicker = new PDatePicker();
        datePicker.addStyleToDates("off", dates("12/25/2013", "01/01/2014", "04/26/2014"));
        datePicker.addValueChangeHandler(new PValueChangeHandler<Date>() {

            @Override
            public void onValueChange(final PValueChangeEvent<Date> event) {
                notifyDateChange("picker", event.getValue());
                dateBox.setDefaultMonth(datePicker.getValue());
            }
        });

        final Date middecember = dates("12/15/2013").get(0);
        datePicker.addShowRangeHandler(new PShowRangeHandler<Date>() {

            @Override
            public void onShowRange(final PShowRangeEvent<Date> event) {
                PNotificationManager.showTrayNotification("Range <" + event.getStart() + "," + event.getEnd() + ">");
                if (middecember.after(event.getStart()) && middecember.before(event.getEnd())) {
                    datePicker.setTransientEnabledOnDates(false, dates("12/21/2013", "12/22/2013", "12/23/2013", "12/24/2013"));
                }
            }
        });

        dateBox = new PDateBox();
        dateBox.addValueChangeHandler(new PValueChangeHandler<Date>() {

            @Override
            public void onValueChange(final PValueChangeEvent<Date> event) {
                notifyDateChange("datebox", event.getValue());
            }
        });

        panel.add(new PLabel("Permanent DatePicker:"));
        panel.add(datePicker);
        panel.add(new PLabel("DateBox with popup DatePicker:"));
        panel.add(dateBox);

        examplePanel.setWidget(panel);
    }

    protected void notifyDateChange(final String source, final Date value) {
        String date = "null";
        if (value != null) {
            date = dateTimeFormatter.format(value);
        }
        fireEvent(new DemoBusinessEvent("Date change from #" + source + ", new value #" + date));
    }

    private List<Date> dates(final String... d) {
        final List<Date> dates = new ArrayList<Date>();
        for (final String s : d) {
            try {
                dates.add(f.parse(s));
            } catch (final ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }
}
