package com.lovecust.app.api;

import com.lovecust.app.R;
import com.lovecust.app.entity.AppProfileAvatar;
import com.lovecust.app.entity.UtilComment;
import com.lovecust.app.entity.EcustJwcNews;
import com.lovecust.app.ecust.wifi.SettingWifi;
import com.lovecust.app.entity.AppProfile;
import com.lovecust.app.entity.AppUpdateStatus;
import com.lovecust.app.entity.EcustLibraryStatus;
import com.lovecust.app.entity.Nothing;
import com.lovecust.app.entity.AppFeedback;
import com.lovecust.app.lovecust.AppContext;
import com.fisher.utils.AppUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.NetUtil;
import com.fisher.utils.TextUtil;


import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fisher on 5/22/2016 at 1:03
 */
public class ApiManager {
	private Api mApi;

	public static ApiManager getInstance ( ) {
		return new ApiManager();
	}

	public Observable< EcustLibraryStatus > ecustLibraryStatus ( ) {
		return mApi
				.ecustLibraryStatus()
//                .flatMap(urls -> Observable.from(urls.getData()))
//                .map(url -> url.getId())
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}

	public Observable< EcustJwcNews[] > ecustJwcList ( ) {
		return mApi
				.ecustJwcList()
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}

	public Observable< EcustJwcNews > ecustJwcFetch ( String md5 ) {
		return mApi
				.ecustJwcFetch( md5 )
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}

	public Observable< Nothing > ecustJwcCommentCreate ( UtilComment comment ) {
		return mApi
				.ecustJwcCommentCreate( comment )
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}

	public Observable< UtilComment[] > ecustJwcCommentList ( String fid ) {
		return mApi
				.ecustJwcCommentList( fid )
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}

	public Observable< Nothing > appFeedbackCreate ( String nick, String value ) {
		return mApi
				.appFeedbackCreate( nick, value )
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}
	public Observable< AppFeedback[] > appFeedbackFetch ( ) {
		return mApi
				.appFeedbackFetch(  )
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}

	public Observable< Nothing > appProfileUpdate ( AppProfile profile ) {
		return mApi
				.appProfileUpdate( profile )
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}
	public Observable< AppProfileAvatar > appProfileAvatar( File avatar ) {
		return mApi
				.appProfileAvatar( getPartFromFile(avatar, "avatar") )
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}

	public Observable< AppUpdateStatus > appUpdateStatus ( ) {
		return mApi
				.appUpdateStatus()
				.subscribeOn( Schedulers.io() )
				.observeOn( AndroidSchedulers.mainThread() );
	}

	private ApiManager ( ) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl( Methods.server )
				.addCallAdapterFactory( RxJavaCallAdapterFactory.create() )
				.addConverterFactory( GsonConverterFactory.create() )
				.client( genericClient() )
				.build();
		mApi = retrofit.create( Api.class );
	}

	private ApiManager ( String text ) {
		Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory( GsonConverterFactory.create() )
				.addConverterFactory( new Converter.Factory() {
					@Override
					public Converter< ResponseBody, ? > responseBodyConverter ( Type type, Annotation[] annotations, Retrofit retrofit ) {
						return super.responseBodyConverter( type, annotations, retrofit );
					}

					@Override
					public Converter< ?, RequestBody > requestBodyConverter ( Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit ) {
						return super.requestBodyConverter( type, parameterAnnotations, methodAnnotations, retrofit );
					}

					@Override
					public Converter< ?, String > stringConverter ( Type type, Annotation[] annotations, Retrofit retrofit ) {
						return super.stringConverter( type, annotations, retrofit );
					}
				} )
				.callFactory( new Call.Factory() {
					@Override
					public Call newCall ( Request request ) {
						return null;
					}
				} )
				.callbackExecutor( new Executor() {
					@Override
					public void execute ( Runnable command ) {

					}
				} ).baseUrl( Methods.server )
				.addCallAdapterFactory( RxJavaCallAdapterFactory.create() )
				.addConverterFactory( GsonConverterFactory.create() )
				.client( genericClient() )
				.build();
		mApi = retrofit.create( Api.class );
	}

	private OkHttpClient genericClient ( ) {
		OkHttpClient httpClient = new OkHttpClient.Builder()
				.addInterceptor( chain -> {
					Request request = chain.request()
							.newBuilder()
							.addHeader( "lovecust", getHeader() )
							.addHeader( "agent", getAgent() )
							.build();
					return chain.proceed( request );
				} )
				.readTimeout( 20, TimeUnit.SECONDS )
				.build();
		return httpClient;
	}

	public static String getAgent ( ) {
		String header = AppUtil.getAndroidInfo();
		ConsoleUtil.console( "header.agent: " + header );
		return header;
	}

	public static String getHeader ( ) {
		String uid = AppProfile.getInstance().getUid();
		if ( "".equals( uid ) ) {
			uid = SettingWifi.getInstance().getUsername();
			if ( "".equals( uid ) )
				uid = AppContext.getContext().getString( R.string.hint_profile_uid );
		}
		String header = AppContext.mAppVersion + "&" + AppContext.mApiVersion + "&ap." + TextUtil.md5( NetUtil.mac ) + "&" + uid;
		ConsoleUtil.console( "header.lovecust: " + header );
		return header;
	}

	public static MultipartBody.Part getPartFromFile(File file, String name){
		// create RequestBody instance from file
//		RequestBody requestFile = RequestBody.create( MediaType.parse( "multipart/form-data" ), file );
		RequestBody requestFile = RequestBody.create( MediaType.parse( "image/png" ), file );

		// MultipartBody.Part is used to send also the actual file name
		MultipartBody.Part body = MultipartBody.Part.createFormData( name, "local-"+file.getName(), requestFile );

		return body;
	}
}
