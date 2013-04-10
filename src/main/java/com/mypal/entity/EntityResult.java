package com.mypal.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class EntityResult {

    Object entity;

    Object responseMessage;

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public Object getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(Object responseMessage) {
        this.responseMessage = responseMessage;
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject result = new JSONObject();
        result.put("entity", this.getEntity());
        result.put("credit", this.getResponseMessage());
        return result;
    }
}
