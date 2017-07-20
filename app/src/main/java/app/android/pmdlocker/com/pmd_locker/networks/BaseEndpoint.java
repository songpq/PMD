package app.android.pmdlocker.com.pmd_locker.networks;

import app.android.pmdlocker.com.pmd_locker.BuildConfig;
import app.android.pmdlocker.com.pmd_locker.models.responses.HLockerLocationResponse;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserOTPResponse;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserRegisterResponse;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by tuanhoang on 3/23/17.
 */

public interface BaseEndpoint {
    public static final String HOST = String.format("%s", BuildConfig.HOST);

    public static final String REGISTER_URL = "customers/register";
    public static final String VERIFY_OTP_URL = "customers/verify_otp";
    public static final String LOGIN_URL = "customers/login";
    public static final String LOGOUT_URL = "customers/logout";
    public static final String LOGIN_FACEBOOK_URL = "customers/login_via_fb";
    public static final String FORGOT_PASS_URL = "customers/sent_otp_lost_password";
    public static final String VERIFY_OTP_LOST_PASS_URL = "customers/verify_otp_otp_lost_password";
    public static final String RESET_PASS_BY_PHONE_URL = "customers/reset_password_by_phone";

    public static final String  PROFILE_URL = "customers/profile";

    public static final String  UPDATE_CUSTOM_PROFILE_URL = "customers/update_customer_profile";
    public static final String  UPDATE_CUSTOM_PASSWORD_URL = "customers/update_customer_password";
    public static final String  LIST_KIOSK_URL = "kiosks/list_kiosk";



    @GET(value = LIST_KIOSK_URL)
    Call<HLockerLocationResponse> getListKiosk();

    @POST(value = REGISTER_URL)
    @FormUrlEncoded
    Call<UserRegisterResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("device_token") String deviceToken
    );

    @POST(value = VERIFY_OTP_URL)
    @FormUrlEncoded
    Call<UserOTPResponse> verifyOTP(
            @Field("phone") String phone,
            @Field("otp_code") String otpCode

    );

    @POST(value = FORGOT_PASS_URL)
    @FormUrlEncoded
    Call<UserOTPResponse> sentOTPLostPass(
            @Field("phone") String phone
    );

    @POST(value = VERIFY_OTP_LOST_PASS_URL)
    @FormUrlEncoded
    Call<UserOTPResponse> verifyOTPLostPass(
            @Field("phone") String phone,
            @Field("reset_password_token") String OTPCode
    );
    @POST(value = RESET_PASS_BY_PHONE_URL)
    @FormUrlEncoded
    Call<UserResponse> resetPassByPhone(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("confirmation_password") String confirmPass
    );

    @POST(value = LOGIN_URL)
    @FormUrlEncoded
    Call<UserResponse> loginUserName(
            @Field("username") String userName,
            @Field("password") String password,
            @Field("device_token") String deviceToken

    );
    @POST(value = LOGOUT_URL)
    @FormUrlEncoded
    Call<UserResponse> logoutUser(
        @Field("username") String userName
    );
    @POST(value = LOGIN_FACEBOOK_URL)
    @FormUrlEncoded
    Call<UserResponse> loginViaFacebook(
            @Field("fb_id") String fb_id,
            @Field("fb_email") String fb_email
    );


    @PUT(value = UPDATE_CUSTOM_PROFILE_URL)
    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
    Call<UserResponse> updateCustomUpdateProfile(
            @Field("access_token") String accessToken,
            @Field("preferred_locker_location") String location,
            @Field("preferred_locker_location_name") String locationName,
            @Field("usage_duration") Integer duration,
            @Field("usage_locker") String locker
    );
    @PUT(value = UPDATE_CUSTOM_PASSWORD_URL)
    @FormUrlEncoded
    Call<UserResponse> updateCustomPassword(
            @Field("access_token") String accessToken,
            @Field("current_password") String currentPassword,
            @Field("password") String password,
            @Field("password_confirmation") String confirm_password

    );
    @PUT(value = UPDATE_CUSTOM_PROFILE_URL)
    @FormUrlEncoded
    Call<UserResponse> updateCustomProfile(
            @Field("access_token") String accessToken,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName


    );


//    public static final String HOME_URL = "load-home-screen";
//    public static final String CATEGORY_URL = "category";
//    public static final String COLLECTION_URL = "collection";
//    public static final String APP_DETAIL_URL = "app/detail";
//    public static final String REGISTER_ACCOUNT_URL = "account/register";
//    public static final String LOGIN_ACCOUNT_URL = "account/login-account";
//    public static final String LOGIN_FACEBOOK = "account/login-facebook";
//    public static final String MENU_CATEGORIES_URL = "categories";
//    public static final String MOVIES_BY_CATEGORY_URL = "movie/category";
//    public static final String MOVIE_BY_ID_URL = "movie";
//    public static final String LOAD_MOVIES_SCREEN_URL = "load-movie-screen";
//    public static final String COMIC_DETAIL_URL = "comic";
//    public static final String COMIC_BY_CATEGORY_URL = "comic/category";
//    public static final String COMIC_CATEGORIES_URL = "comic/categories";
//    public static final String COMMENTS_BY_APP_URL = "comments";
//    public static final String GET_MENU_BY_CATEGORY_URL = "get-menu-category";
//    public static final String SEARCH_URL = "search";
//    public static final String GET_MENU_BY_SEARCH_URL = "get-menu-search";
//    public static final String SUGGEST_SEARCH_URL = "suggest-search";
//    public static final String REQUEST_APP_CONTENT_URL = "request-app/content";
//    public static final String REQUEST_APP_IMAGES_URL = "request-app/image";
//    public static final String REQUEST_APP_LINK_URL = "request-app/link";
//    public static final String LOGOUT_ACCOUNT_URL = "account/logout";
//    public static final String EVENTS_URL = "events";
//    public static final String GIFTS_BY_APP_URL = "gifts";
//    public static final String GIFTS_BY_USER_URL = "account/gifts";
//    public static final String STORE_CHECK_UPDATE_APP_URL = "store/check-update-app";
//    public static final String STORE_CHECK_UPDATE_LANG_URL = "store/check-update-language";
//    public static final String STORE_CHECK_UPDATE_APP_URL = "store/check-update";
//    public static final String GET_FAVORITE_APP_URL = "favourite";
//    public static final String GET_FAVORITE_MENU_URL = "get-menu-favourite";
//    public static final String APP_CHECK_UPDATE_URL = "app/check-update";
//    public static final String RECEIVED_GIFT_CODE_URL = "receive-gift-code";

//    @GET(value = HOME_URL)
//    Call<HomeResponse> getHome();
//
//    @GET(value = CATEGORY_URL)
//    Call<CategoryResponse> getCategoryById(
//            @Query("id") Integer categoryId,
//            @Query("p") Integer pageIndex,
//            @Query("sort") String sortType
//    );
//
//    @GET
//    Call<CategoryResponse> getCategoryByURL(
//            @Url String url
//    );
//
//    @GET(value = APP_DETAIL_URL)
//    Call<AppResponse> getAppById(
//            @Query("id") Integer appId
//    );
//
//    @GET(value = COLLECTION_URL)
//    Call<CollectionResponse> getCollectionById(
//            @Query("id") Integer collectionId,
//            @Query("page") Integer page
//    );
//
//    @POST(value = REGISTER_ACCOUNT_URL)
//    @FormUrlEncoded
//    Call<UserRegisterResponse> registerUser(
//            @Field("email") String email,
//            @Field("username") String username,
//            @Field("password") String password,
//            @Field("full_name") String fullName
//    );
//
//    @POST(value = LOGIN_ACCOUNT_URL)
//    @FormUrlEncoded
//    Call<UserRegisterResponse> loginUser(
//            @Field("username") String username,
//            @Field("password") String password
//
//    );
//    @POST(value = LOGIN_FACEBOOK)
//    @FormUrlEncoded
//    Call<UserRegisterResponse> loginFacebook(
//            @Field("fb_token") String accessToken
//
//    );
//
//    @GET(value = MOVIES_BY_CATEGORY_URL)
//    Call<MCategoryResponse> getMoviesByCategory(
//            @Query("id") Integer categoryId
//    );
//
//    @GET(value = MOVIE_BY_ID_URL)
//    Call<MovieResponse> getMovieById(
//            @Query("id") Integer movieId
//    );
//
//    @GET(value = LOAD_MOVIES_SCREEN_URL)
//    Call<MCategoriesResponse> getMovieCategories();
//
//
//    @GET(value = MENU_CATEGORIES_URL)
//    Call<HCategoriesResponse> getCategories(
//            @Query("parent") Integer parentId,
//            @Query("id") Integer id
//    );
//
//    @GET
//    Call<HCategoriesResponse> getCategoriesByURL(
//            @Url String url
//    );
//
//    @GET(value = COMIC_DETAIL_URL)
//    Call<ComicResponse> getComicById(
//            @Query("id") Integer id
//    );
//
//    @GET(value = COMIC_BY_CATEGORY_URL)
//    Call<CCategoryResponse> getComicsByCategory(
//            @Query("id") Integer categoryId
//    );
//
//    @GET(value = COMIC_CATEGORIES_URL)
//    Call<CCategoriesResponse> getComicCategories();
//
//    @GET(value = COMMENTS_BY_APP_URL)
//    Call<CommentsResponse> getCommentsByAppId(
//            @Query("app_id") Integer appId,
//            @Query("p") Integer page
//    );
//
//    @POST(value = COMMENTS_BY_APP_URL)
//    @FormUrlEncoded
//    Call<DefaultResponse> postCommentByUser(
//            @Field("app_id") Integer appId,
//            @Field("rate") Integer rate,
//            @Field("content") String content,
//            @Field("user_id") Integer userId
//    );
//
//    @GET(value = GET_MENU_BY_CATEGORY_URL)
//    Call<MenusResponse> getMenusByCategory(
//            @Query("type") Integer type,
//            @Query("parent_id") Integer parentId
//    );
//
//    @GET(value = SEARCH_URL)
//    Call<CategoriesResponse> searchByKeywords(
//            @Query("q") String keyword,
//            @Query("p") Integer page,
//            @Query("c") String categories
//    );
//
//    @GET(value = GET_MENU_BY_SEARCH_URL)
//    Call<MenusResponse> getMenusBySearchingKeywords(
//            @Query("q") String keyword
//    );
//
//    @GET(value = SUGGEST_SEARCH_URL)
//    Call<ResultsResponse> suggestSearchByKeywords(
//            @Query("q") String keyword,
//            @Query("limit") Integer limit
//    );
//
//    @GET
//    Call<CategoriesResponse> searchByURL(
//            @Url String url,
//            @Query("type") Integer type
//    );
//
//    @GET
//    Call<CategoriesResponse> favoriteByURL(
//            @Url String url
//
//    );
//
//
//
//    @POST(value = REQUEST_APP_CONTENT_URL)
//    @FormUrlEncoded
//    Call<DefaultResponse> requestAppByContent(
//            @Field("content") String content,
//            @Field("device_id") String deviceId
//    );
//
//    @POST(value = REQUEST_APP_IMAGES_URL)
//    @Multipart
//    Call<DefaultResponse> requestAppByImage(
//            @Part("device_id") String deviceId,
//            @Part MultipartBody.Part image
//    );
//
//    @POST(value = REQUEST_APP_LINK_URL)
//    @FormUrlEncoded
//    Call<DefaultResponse> requestAppByLinks(
//            @Field("link") String link,
//            @Field("device_id") String deviceId
//    );
//
//    @GET(value = LOGOUT_ACCOUNT_URL)
//    Call<DefaultResponse> logout();
//
//    @GET(value = EVENTS_URL)
//    Call<EventsResponse> eventsByAppId(
//            @Query("app_id") Integer appId
//    );
//
//    @GET(value = GIFTS_BY_APP_URL)
//    Call<GiftsResponse> giftsByAppId(
//            @Query("app_id") Integer appId
//    );
//
//    @GET(value = GIFTS_BY_USER_URL)
//    Call<GiftsResponse> giftsByUser(
//            @Query("p") Integer page
//    );
//
//    @POST(value = STORE_CHECK_UPDATE_APP_URL)
//    @FormUrlEncoded
//    Call<HUpdateStoreResponse> storeCheckUpdateApp(
//            @Field("version_store") Integer versionStore,
//            @Field("version_language") Integer versionLanguage
//    );
//
////    @GET(value = STORE_CHECK_UPDATE_LANG_URL)
////    Call<StoreResponse> storeCheckUpdateLanguage(
////        @Query("v") Integer version
////    );
//
//    @GET(value = GET_FAVORITE_MENU_URL)
//    Call<MenusResponse> getMenuFavorite(
//
//    );
//
//    @GET(value = GET_FAVORITE_APP_URL)
//    Call<CategoriesResponse> getFavoriteApps(
//            @Query("c") String category,
//            @Query("p") Integer page,
//            @Query("limit") Integer limit
//    );
//
//    @POST(value = GET_FAVORITE_APP_URL)
//    @FormUrlEncoded
//    Call<CategoriesResponse> addFavoriteApps(
//            @Field("app_id") Integer appId
//
//    );
//
//
//    @FormUrlEncoded
//    @HTTP(method = "DELETE",path = BaseEndpoint.GET_FAVORITE_APP_URL ,hasBody = true)
//    Call<CategoriesResponse> deleteFavoriteApps(
//            @Field("app_id") Integer appId
//    );
//
//    @POST(value = APP_CHECK_UPDATE_URL)
//    @FormUrlEncoded
//    Call<StoresResponse> checkAppUpdate(
//            @Field("payload") String payload
//    );
//
//    @POST(value = RECEIVED_GIFT_CODE_URL)
//    @FormUrlEncoded
//    Call<DefaultResponse> receivedGiftCode(
//            @Field("id") Integer id
//    );
}
