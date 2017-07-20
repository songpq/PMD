package app.android.pmdlocker.com.pmd_locker.models.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tuanhoang on 3/27/17.
 */

public class HStatus {
    @SerializedName("error_code")
    private final String errorCode;
    @SerializedName("error_msg")
    private final String errorMessage;

    public HStatus(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return Integer.parseInt(errorCode);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
