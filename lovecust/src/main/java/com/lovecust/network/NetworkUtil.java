package com.lovecust.network;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fisher at 11:32 PM on 12/3/16.
 * <p>
 * Network util.
 */

public class NetworkUtil {
	/**
	 * Get part from file for form-data http protocol.
	 *
	 * @param file File to be used.
	 * @param name File name.
	 * @return Form-data part.
	 */
	public static MultipartBody.Part getPartFromFile(File file, String name) {
		if (null == file) {return null;}
		// create RequestBody instance from file
		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
		// MultipartBody.Part is used to send also the actual file name
		MultipartBody.Part body = MultipartBody.Part.createFormData(name, file.getName(), requestFile);
		return body;
	}

	/**
	 * Make sure the return observable run on the main thread.
	 *
	 * @param obj observable object.
	 * @param <T> Api Response.
	 * @return Processed observable object.
	 */
	public static <T> Observable<T> ensure(Observable<T> obj) {
		return obj.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
