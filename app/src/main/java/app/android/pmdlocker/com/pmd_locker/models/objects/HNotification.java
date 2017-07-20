package app.android.pmdlocker.com.pmd_locker.models.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tuanhoang on 3/27/17.
 */

public class HNotification implements Serializable {
    @SerializedName("locker_icon")
    private final Integer icon;

    @SerializedName("locker_name")
    private final String name;

    public HNotification(Integer icon, String name) {
        this.icon = icon;
        this.name = name;

    }

    public Integer getIcon()
    {
        return icon;
    }
    public String getName()
    {
        return name;
    }
}
