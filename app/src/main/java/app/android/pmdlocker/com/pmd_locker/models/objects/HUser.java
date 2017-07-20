package app.android.pmdlocker.com.pmd_locker.models.objects;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tuanhoang on 3/31/17.
 */

public class HUser implements Serializable {
    @SerializedName("email")
    private final String email;
    @SerializedName("phone")
    private final String phone;
    @SerializedName("password")
    private final String password;
    @SerializedName("user_id")
    private final Double userId;
    @SerializedName("token")
    private final String token;
    @SerializedName("avatar")
    private final String avatar;
    @SerializedName("full_name")
    private final String fullName;
    @SerializedName("user_name")
    private final String userName;
    @SerializedName("facebook_id")
    private final String facebookId;
    @SerializedName("google_id")
    private final String googleId;

    public HUser(String email, String phone, String password, Double userId, String token, String avatar, String fullName, String userName, String facebookId, String googleId) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userId = userId;
        this.token = token;
        this.avatar = avatar;
        this.fullName = fullName;
        this.userName = userName;
        this.facebookId = facebookId;
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public Double getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }
}
