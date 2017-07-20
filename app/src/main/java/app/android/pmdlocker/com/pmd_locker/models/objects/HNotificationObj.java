package app.android.pmdlocker.com.pmd_locker.models.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ca. Phan Thanh on 6/14/2017.
 */

public class HNotificationObj {
    public String title;
    public String message;
    public String link;
    public Integer type;

    public static HNotificationObj parseNotification(String message) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(message);
        HNotificationObj hNotificationObj = new HNotificationObj();
        hNotificationObj.title = jsonObject.getString("title");
        hNotificationObj.link = jsonObject.optString("link");
        hNotificationObj.type = jsonObject.optInt("type");
        hNotificationObj.message = jsonObject.getString("message");
        return hNotificationObj;
    }

}
