package com.lovecust.network;

import com.lovecust.constants.NetworkConstant;
import com.lovecust.entities.AppProfileAvatar;
import com.lovecust.entities.UtilComment;
import com.lovecust.entities.EcustJwcNews;
import com.lovecust.entities.AppProfile;
import com.lovecust.entities.AppUpdateStatus;
import com.lovecust.entities.EcustLibraryStatus;
import com.lovecust.entities.Nothing;
import com.lovecust.entities.AppFeedback;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Fisher on 5/22/2016 at 1:03
 */
public interface ApiInterface {
	/* ecust apis */
	@POST(NetworkConstant.ECUST_LIBRARY_STATUS)
	Observable<EcustLibraryStatus> ecustLibraryStatus();

	@POST(NetworkConstant.ECUST_JWC_LIST)
	Observable<EcustJwcNews[]> ecustJwcList();

	@POST(NetworkConstant.ECUST_JWC_COMMENT_CREATE)
	Observable<Nothing> ecustJwcCommentCreate(@Body UtilComment comment);

	@FormUrlEncoded
	@POST(NetworkConstant.ECUST_JWC_COMMENT_LIST)
	Observable<UtilComment[]> ecustJwcCommentList(@Field("fid") String fid);

	@FormUrlEncoded
	@POST(NetworkConstant.ECUST_JWC_FETCH)
	Observable<EcustJwcNews> ecustJwcFetch(@Field("id") String md5);

	/* app apis */
	@POST(NetworkConstant.APP_UPDATE_STATUS)
	Observable<AppUpdateStatus> appUpdateStatus();

	@POST(NetworkConstant.APP_PROFILE_UPDATE)
	Observable<Nothing> appProfileUpdate(@Body AppProfile profile);

	@Multipart
	@POST(NetworkConstant.APP_PROFILE_AVATAR)
	Observable<AppProfileAvatar> appProfileAvatar(@Part() MultipartBody.Part avatar);

	@FormUrlEncoded
	@POST(NetworkConstant.APP_FEEDBACK_CREATE)
	Observable<Nothing> appFeedbackCreate(
			@Field("nick") String nick
			, @Field("value") String value);

	@POST(NetworkConstant.APP_FEEDBACK_FETCH)
	Observable<AppFeedback[]> appFeedbackFetch();


}
