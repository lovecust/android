package com.fisher.utils.app;

import android.app.Service;

import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.ToastUtil;


public abstract class BaseService extends Service {


	protected String toast( String msg ) {
		return ToastUtil.toast( this, msg );
	}

	protected String log( Object obj ) {
		return ConsoleUtil.console( obj );
	}
	protected String log( String msg ) {
		return ConsoleUtil.console( msg );
	}

}
