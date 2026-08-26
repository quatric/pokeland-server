package com.amazon.device.iap.model;

import com.amazon.device.iap.internal.model.ProductDataResponseBuilder;
import com.amazon.device.iap.internal.util.C0245d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class ProductDataResponse {
    private static final String PRODUCT_DATA = "productData";
    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_STATUS = "requestStatus";
    private static final String TO_STRING_FORMAT = "(%s, requestId: \"%s\", unavailableSkus: %s, requestStatus: \"%s\", productData: %s)";
    private static final String UNAVAILABLE_SKUS = "UNAVAILABLE_SKUS";
    private final Map<String, Product> productData;
    private final RequestId requestId;
    private final RequestStatus requestStatus;
    private final Set<String> unavailableSkus;

    public enum RequestStatus {
        SUCCESSFUL,
        FAILED,
        NOT_SUPPORTED
    }

    public ProductDataResponse(ProductDataResponseBuilder productDataResponseBuilder) {
        C0245d.m408a(productDataResponseBuilder.getRequestId(), REQUEST_ID);
        C0245d.m408a(productDataResponseBuilder.getRequestStatus(), REQUEST_STATUS);
        if (productDataResponseBuilder.getUnavailableSkus() == null) {
            productDataResponseBuilder.setUnavailableSkus(new HashSet());
        }
        if (RequestStatus.SUCCESSFUL == productDataResponseBuilder.getRequestStatus()) {
            C0245d.m408a(productDataResponseBuilder.getProductData(), PRODUCT_DATA);
        } else {
            productDataResponseBuilder.setProductData(new HashMap());
        }
        this.requestId = productDataResponseBuilder.getRequestId();
        this.requestStatus = productDataResponseBuilder.getRequestStatus();
        this.unavailableSkus = productDataResponseBuilder.getUnavailableSkus();
        this.productData = productDataResponseBuilder.getProductData();
    }

    public Map<String, Product> getProductData() {
        return this.productData;
    }

    public RequestId getRequestId() {
        return this.requestId;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public Set<String> getUnavailableSkus() {
        return this.unavailableSkus;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(REQUEST_ID, this.requestId);
        jSONObject.put(UNAVAILABLE_SKUS, this.unavailableSkus);
        jSONObject.put(REQUEST_STATUS, this.requestStatus);
        JSONObject jSONObject2 = new JSONObject();
        Map<String, Product> map = this.productData;
        if (map != null) {
            for (String str : map.keySet()) {
                jSONObject2.put(str, this.productData.get(str).toJSON());
            }
        }
        jSONObject.put(PRODUCT_DATA, jSONObject2);
        return jSONObject;
    }

    public String toString() {
        Object[] objArr = new Object[5];
        objArr[0] = super.toString();
        objArr[1] = this.requestId;
        Set<String> set = this.unavailableSkus;
        objArr[2] = set != null ? set.toString() : "null";
        RequestStatus requestStatus = this.requestStatus;
        objArr[3] = requestStatus != null ? requestStatus.toString() : "null";
        Map<String, Product> map = this.productData;
        objArr[4] = map != null ? map.toString() : "null";
        return String.format(TO_STRING_FORMAT, objArr);
    }
}
