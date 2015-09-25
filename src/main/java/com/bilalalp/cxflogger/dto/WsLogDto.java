package com.bilalalp.cxflogger.dto;

import com.bilalalp.cxflogger.constant.CxfLoggerWsLogDtoConstant;
import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WsLogDto implements Serializable {

    private static final String EMPTY_STRING = "";

    private transient Map<String, Object> parameters;

    public WsLogDto() {
        parameters = new HashMap<>();
    }

    public Object put(final String key, final Collection<Object> collection) {
        if (collection == null || collection.isEmpty()) {
            return parameters.put(key, EMPTY_STRING);
        } else if (collection.size() == 1) {
            final Object object = collection.iterator().next();
            return parameters.put(key, (object == null) ? EMPTY_STRING : object);
        } else {
            return parameters.put(key, collection);
        }
    }

    public Object put(final String key, final Object object) {
        if (object instanceof Collection) {
            return this.put(key, (Collection<Object>) object);
        }
        return parameters.put(key, object);
    }

    public void setRequestUrl(final String requestUrl) {
        this.put(CxfLoggerWsLogDtoConstant.REQUEST_URL_PARAM, requestUrl);
    }

    public void setMethod(final Object method) {
        this.put(CxfLoggerWsLogDtoConstant.METHOD_PARAM, method);
    }

    public String getEncoding() {
        final Object encoding = this.parameters.get(CxfLoggerWsLogDtoConstant.ENCODING_PARAM);

        if (encoding == null) {
            return CxfLoggerWsLogDtoConstant.UTF8_ENCODING;
        }
        return String.valueOf(encoding);
    }

    public String getRequest() {
        return String.valueOf(this.parameters.get(CxfLoggerWsLogDtoConstant.REQUEST_PARAM));
    }

    public void setRequest(final String request) {
        this.put(CxfLoggerWsLogDtoConstant.REQUEST_PARAM, request);
    }

    public String getResponse() {
        return String.valueOf(this.parameters.get(CxfLoggerWsLogDtoConstant.RESPONSE_PARAM));
    }

    public void setResponse(final String response) {
        this.put(CxfLoggerWsLogDtoConstant.RESPONSE_PARAM, response);
    }

    public String getRequestURL() {
        return String.valueOf(this.parameters.get(CxfLoggerWsLogDtoConstant.REQUEST_URL_PARAM));
    }

    public String getResponseCode() {
        return String.valueOf(this.parameters.get(CxfLoggerWsLogDtoConstant.RESPONSE_CODE_PARAM));
    }

    public void setResponseCode(final Object responseCode) {
        this.put(CxfLoggerWsLogDtoConstant.RESPONSE_CODE_PARAM, responseCode);
    }

    public String getSoapAction() {
        final Map<String, Object> headerInformationMap = (Map<String, Object>) this.parameters.get(CxfLoggerWsLogDtoConstant.HEADER_INFORMATIONS_PARAM);
        if (MapUtils.isEmpty(headerInformationMap)) {
            return null;
        }
        final List<String> stringList = (List<String>) headerInformationMap.get(CxfLoggerWsLogDtoConstant.SOAP_ACTION_PARAM);
        return stringList.get(0);
    }

    public Map<String, Object> getHeaderInformations() {
        return (Map<String, Object>) this.parameters.get(CxfLoggerWsLogDtoConstant.HEADER_INFORMATIONS_PARAM);
    }

    public Boolean getFault() {
        return (Boolean) this.parameters.get(CxfLoggerWsLogDtoConstant.FAULT_PARAM);
    }

    public void setFault(final boolean fault) {
        this.put(CxfLoggerWsLogDtoConstant.FAULT_PARAM, fault);
    }
}