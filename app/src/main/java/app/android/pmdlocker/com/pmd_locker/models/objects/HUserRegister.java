package app.android.pmdlocker.com.pmd_locker.models.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class HUserRegister  implements Serializable {
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("username")
    private final String userName;
    @SerializedName("email")
    private final String email;
    @SerializedName("phone")
    private final String phone;
    @SerializedName("authentication_token")
    private final String authen_token;
    @SerializedName("fb_id")
    private final String fb_id;
    @SerializedName("fb_access_token")
    private final String fb_access_token;
    @SerializedName("status")
    private final String status;
    @SerializedName("usage_locker")
    private final String usageLocker;
    @SerializedName("preferred_locker_location")
    private final String preferredLockerLocation;
    @SerializedName("preferred_locker_location_name")
    private final String preferredLockerLocationName;
    @SerializedName("usage_duration")
    private final Integer usageDuration;
    @SerializedName("credit_balance")
    private final Integer walletAmount;

    private String password;

    public HUserRegister(String fistName, String lastName, String userName, String email, String phone
            , String authen_token, String fb_id, String fb_access_token, String status
            , String usageLocker,Integer usageDuration,String preferredLockerLocation,String preferredLockerLocationName,
                         Integer walletAmount) {
        this.firstName = fistName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.authen_token = authen_token;
        this.fb_id = fb_id;
        this.fb_access_token = fb_access_token;
        this.status = status;
        this.usageDuration = usageDuration;
        this.usageLocker = usageLocker;
        this.preferredLockerLocation = preferredLockerLocation;
        this.preferredLockerLocationName = preferredLockerLocationName;
        this.walletAmount =walletAmount;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return this.password;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public  void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public Integer getWalletAmount()
    {
        if(walletAmount==null)
            return 0;
        return walletAmount;
    }

    public String getAuthen_token() {
        return this.authen_token;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus()
    {
        return status;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public String getUserName()
    {
        return userName;
    }
    public String getEmail()
    {
        return email;
    }
    public String getFb_access_token()
    {
        return fb_access_token;
    }
    public String getFb_id()
    {
        return fb_id;
    }
    public String getUsageLocker()
    {
        return usageLocker;
    }
    public String getPreferredLockerLocation()
    {
        return preferredLockerLocation;
    }
    public String getPreferredLockerLocationName()
    {
        return preferredLockerLocationName;
    }
    public Integer getUsageDuration()
    {
        return usageDuration;
    }
}
