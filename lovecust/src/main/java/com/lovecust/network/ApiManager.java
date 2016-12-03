package com.lovecust.network;

import com.lovecust.app.R;
import com.lovecust.constants.NetworkConstant;
import com.lovecust.entities.AppProfileAvatar;
import com.lovecust.entities.UtilComment;
import com.lovecust.entities.EcustJwcNews;
import com.lovecust.modules.ecust.wifi.SettingWifi;
import com.lovecust.entities.AppProfile;
import com.lovecust.entities.AppUpdateStatus;
import com.lovecust.entities.EcustLibraryStatus;
import com.lovecust.entities.Nothing;
import com.lovecust.entities.AppFeedback;
import com.lovecust.app.AppContext;
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

/**
 * Created by Fisher on 5/22/2016 at 1:03
 */
public class ApiManager {

	public static ApiManager mManager = null;
	private ApiInterface mApiInterface;

	public static ApiManager getInstance() {
		if (null == mManager) {mManager = new ApiManager();}
		return mManager;
	}

	public Observable<EcustLibraryStatus> ecustLibraryStatus() {
		return ensure(mApiInterface.ecustLibraryStatus());
	}

	public Observable<EcustJwcNews[]> ecustJwcList() {
		return ensure(mApiInterface.ecustJwcList());
	}

	public Observable<EcustJwcNews> ecustJwcFetch(String md5) {
		return ensure(mApiInterface.ecustJwcFetch(md5));
	}

	public Observable<Nothing> ecustJwcCommentCreate(UtilComment comment) {
		return ensure(mApiInterface.ecustJwcCommentCreate(comment));
	}

	public Observable<UtilComment[]> ecustJwcCommentList(String fid) {
		return ensure(mApiInterface.ecustJwcCommentList(fid));
	}

	public Observable<Nothing> appFeedbackCreate(String nick, String value) {
		return ensure(mApiInterface.appFeedbackCreate(nick, value));
	}

	public Observable<AppFeedback[]> appFeedbackFetch() {
		return ensure(mApiInterface.appFeedbackFetch());
	}

	public Observable<Nothing> appProfileUpdate(AppProfile profile) {
		return ensure(mApiInterface.appProfileUpdate(profile));
	}

	public Observable<AppProfileAvatar> appProfileAvatar(File avatar) {
		return ensure(mApiInterface.appProfileAvatar(getPartFromFile(avatar, "avatar")));
	}

	public Observable<AppUpdateStatus> appUpdateStatus() {
		return ensure(mApiInterface.appUpdateStatus());
	}

	private ApiManager() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(NetworkConstant.server)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.client(genericClient())
				.build();
		mApiInterface = retrofit.create(ApiInterface.class);
	}

	private ApiManager(String text) {
		Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create())
				.addConverterFactory(new Converter.Factory() {
					@Override
					public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
						return super.responseBodyConverter(type, annotations, retrofit);
					}

					@Override
					public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
						return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
					}

					@Override
					public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
						return super.stringConverter(type, annotations, retrofit);
					}
				})
				.callFactory(new Call.Factory() {
					@Override
					public Call newCall(Request request) {
						return null;
					}
				})
				.callbackExecutor(new Executor() {
					@Override
					public void execute(Runnable command) {

					}
				}).baseUrl(NetworkConstant.server)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.client(genericClient())
				.build();
		mApiInterface = retrofit.create(ApiInterface.class);
	}

	private OkHttpClient genericClient() {
		OkHttpClient httpClient = new OkHttpClient.Builder()
				.addInterceptor(chain -> {
					Request request = chain.request()
							.newBuilder()
							.addHeader("lovecust", getHeader())
							.addHeader("agent", getAgent())
							.build();
					return chain.proceed(request);
				})
				.readTimeout(20, TimeUnit.SECONDS)
				.build();
		return httpClient;
	}

	public static String getAgent() {
		String header = AppUtil.getAndroidInfo();
		ConsoleUtil.console("header.agent: " + header);
		return header;
	}

	public static String getHeader() {
		String uid = AppProfile.getInstance().getUid();
		if ("".equals(uid)) {
			uid = SettingWifi.getInstance().getUsername();
			if ("".equals(uid))
				uid = AppContext.getContext().getString(R.string.hint_profile_uid);
		}
		String header = AppContext.mAppVersion + "&" + AppContext.mApiVersion + "&ap." + TextUtil.md5(NetUtil.mac) + "&" + uid;
		ConsoleUtil.console("header.lovecust: " + header);
		return header;
	}

	/**
	 * Get part from file for form-data http protocol.
	 *
	 * @param file File to be used.
	 * @param name File name.
	 * @return Form-data part.
	 */
	public static MultipartBody.Part getPartFromFile(File file, String name) {
		return NetworkUtil.getPartFromFile(file, name);
	}

	/**
	 * Make sure the return observable run on the main thread.
	 *
	 * @param obj observable object.
	 * @param <T> Api Response.
	 * @return Processed observable object.
	 */
	private <T> Observable<T> ensure(Observable<T> obj) {
		return NetworkUtil.ensure(obj);
	}
}
