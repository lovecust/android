package com.lovecust.app.api;

import com.lovecust.app.entity.AppProfileAvatar;
import com.lovecust.app.entity.UtilComment;
import com.lovecust.app.entity.EcustJwcNews;
import com.lovecust.app.entity.AppProfile;
import com.lovecust.app.entity.AppUpdateStatus;
import com.lovecust.app.entity.EcustLibraryStatus;
import com.lovecust.app.entity.Nothing;
import com.lovecust.app.entity.AppFeedback;

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
public interface Api {
	/* ecust apis */
	@POST( Methods.ECUST_LIBRARY_STATUS )
	Observable< EcustLibraryStatus > ecustLibraryStatus ( );

	@POST( Methods.ECUST_JWC_LIST )
	Observable< EcustJwcNews[] > ecustJwcList ( );

	@POST( Methods.ECUST_JWC_COMMENT_CREATE )
	Observable< Nothing > ecustJwcCommentCreate ( @Body UtilComment comment );

	@FormUrlEncoded
	@POST( Methods.ECUST_JWC_COMMENT_LIST )
	Observable< UtilComment[] > ecustJwcCommentList ( @Field( "fid" ) String fid );

	@FormUrlEncoded
	@POST( Methods.ECUST_JWC_FETCH )
	Observable< EcustJwcNews > ecustJwcFetch ( @Field( "id" ) String md5 );

	/* app apis */
	@POST( Methods.APP_UPDATE_STATUS )
	Observable< AppUpdateStatus > appUpdateStatus ( );

	@POST( Methods.APP_PROFILE_UPDATE )
	Observable< Nothing > appProfileUpdate ( @Body AppProfile profile );

	@Multipart
	@POST( Methods.APP_PROFILE_AVATAR )
	Observable< AppProfileAvatar > appProfileAvatar ( @Part() MultipartBody.Part avatar );

	@FormUrlEncoded
	@POST( Methods.APP_FEEDBACK_CREATE )
	Observable< Nothing > appFeedbackCreate (
			@Field( "nick" ) String nick
			, @Field( "value" ) String value );

	@POST( Methods.APP_FEEDBACK_FETCH )
	Observable< AppFeedback[] > appFeedbackFetch ( );


}
