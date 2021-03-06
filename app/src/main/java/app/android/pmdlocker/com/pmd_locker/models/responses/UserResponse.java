package app.android.pmdlocker.com.pmd_locker.models.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.android.pmdlocker.com.pmd_locker.models.objects.HUser;
import app.android.pmdlocker.com.pmd_locker.models.objects.HUserRegister;


/**
 * Created by tuanhoang on 3/31/17.
 */

public class UserResponse implements Serializable{
    @SerializedName("success")
    private final Boolean success;
    @SerializedName("message")
    private final String message;
    @SerializedName("data")
    private final HUserRegister data;
    @SerializedName("error")
    private final Integer errorCode;

    public UserResponse(Boolean success, String message, HUserRegister data, Integer error) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errorCode = error;
    }

    public Boolean getSuccess()
    {
        return success;
    }
    public String getMessage()
    {
        return message;
    }
    public HUserRegister getData()
    {
        return data;
    }
    public Integer getErrorCode()
    {
        return errorCode;
    }

}
