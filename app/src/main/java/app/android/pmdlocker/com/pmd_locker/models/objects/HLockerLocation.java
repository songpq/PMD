package app.android.pmdlocker.com.pmd_locker.models.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tuanhoang on 3/27/17.
 */

public class HLockerLocation implements Serializable {
    @SerializedName("id")
    private final Integer id;

    @SerializedName("location")
    private final String location;

    @SerializedName("longitude")
    private final String longitude;

    @SerializedName("latitude")
    private final String latitude;

    @SerializedName("status")
    private final String status;

    public HLockerLocation(Integer id,String location,String longitude,String latitude,String status) {
        this.id = id;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    public Integer getId()
    {
        return id;
    }
    public String getLocation()
    {
        return location;
    }
    public String getLongitude()
    {
        return this.longitude;
    }
    public String getLatitude()
    {
        return this.latitude;
    }
}
