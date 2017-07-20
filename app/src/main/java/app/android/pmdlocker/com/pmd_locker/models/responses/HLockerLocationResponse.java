package app.android.pmdlocker.com.pmd_locker.models.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.android.pmdlocker.com.pmd_locker.models.objects.HLockerLocation;
import app.android.pmdlocker.com.pmd_locker.models.objects.HUserRegister;


/**
 * Created by tuanhoang on 3/31/17.
 */

public class HLockerLocationResponse implements Serializable{
    @SerializedName("success")
    private final Boolean success;
    @SerializedName("message")
    private final String message;
    @SerializedName("data")
    private final List<HLockerLocation> data;
    @SerializedName("error")
    private final Integer errorCode;

    public HLockerLocationResponse(Boolean success, String message, List<HLockerLocation> data, Integer error) {
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
    public List<HLockerLocation> getData()
    {
        return data;
    }
    public Integer getErrorCode()
    {
        return errorCode;
    }

}
