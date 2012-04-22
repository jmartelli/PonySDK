
package com.ponysdk.ui.server.basic;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.ponysdk.core.PonySession;
import com.ponysdk.core.instruction.Update;
import com.ponysdk.ui.terminal.WidgetType;
import com.ponysdk.ui.terminal.instruction.Dictionnary.PROPERTY;

public abstract class PScript extends PObject {

    public interface ExecutionCallback {

        public void onFailure(String msg);

        public void onSuccess(String msg);
    }

    private static final String SCRIPT_KEY = PScript.class.getCanonicalName();

    private long executionID = 0;
    private final Map<Long, ExecutionCallback> callbacksByID = new HashMap<Long, PScript.ExecutionCallback>();

    private PScript() {}

    @Override
    protected WidgetType getWidgetType() {
        return WidgetType.SCRIPT;
    }

    public static PScript get() {
        PScript script = PonySession.getCurrent().getAttribute(SCRIPT_KEY);
        if (script == null) {
            script = new PScript() {};
            PonySession.getCurrent().setAttribute(SCRIPT_KEY, script);
        }
        return script;
    }

    public void execute(final String js) {
        final Update update = new Update(ID);
        update.put(PROPERTY.EVAL, js);
        update.put(PROPERTY.ID, (executionID++));
        PonySession.getCurrent().stackInstruction(update);
    }

    public void execute(final String js, final ExecutionCallback callback) {
        final long id = executionID++;
        callbacksByID.put(id, callback);

        final Update update = new Update(ID);
        update.put(PROPERTY.EVAL, js);
        update.put(PROPERTY.ID, id);
        PonySession.getCurrent().stackInstruction(update);
    }

    @Override
    public void onEventInstruction(final JSONObject instruction) throws JSONException {
        if (instruction.has(PROPERTY.ERROR_MSG)) {
            final ExecutionCallback callback = callbacksByID.remove(instruction.getLong(PROPERTY.ID));
            if (callback != null) callback.onFailure(instruction.getString(PROPERTY.ERROR_MSG));
        } else if (instruction.has(PROPERTY.RESULT)) {
            final ExecutionCallback callback = callbacksByID.remove(instruction.getLong(PROPERTY.ID));
            if (callback != null) callback.onSuccess(instruction.getString(PROPERTY.RESULT));
        } else {
            super.onEventInstruction(instruction);
        }
    }

}