package com.meta.core;

import java.util.List;

public class ModelOutput {

    /**
     * 模型code
     */
    private String modelCode;


    private List<String> identityCodes;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public List<String> getIdentityCodes() {
        return identityCodes;
    }

    public void setIdentityCodes(List<String> identityCodes) {
        this.identityCodes = identityCodes;
    }

}
