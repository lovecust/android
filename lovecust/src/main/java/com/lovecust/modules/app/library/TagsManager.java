package com.lovecust.modules.app.library;


import org.litepal.crud.DataSupport;

import java.util.List;

public class TagsManager {

	public static String[] getTagStrings (){
		List< Tag > tags = DataSupport.findAll( Tag.class );
		String[] strings = new String[tags.size()];
		for ( int i=0; i<tags.size(); i++ ){
			strings[i]=tags.get( i ).getTag();
		}
		return strings;
	}

	public static List< Tag > getTags () {
		return  DataSupport.findAll( Tag.class );
	}

	public static Tag getTag (long id) {
		return DataSupport.find( Tag.class, id );
	}
}
