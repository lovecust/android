package com.lovecust.app.library;


import org.litepal.crud.DataSupport;

import java.util.List;

public class BooksManager {

	public static Book getBook ( long id ) {
		return DataSupport.find( Book.class, id );
	}

	public static List< Book > getBooksByTag ( long id ) {
		return DataSupport.order( "ltime  desc" ).where( "tag=?", "" + id ).find( Book.class );
	}

	public static List< Book > getFavoriteBooks () {
		return DataSupport.order( "ltime desc" ).where( "favorite=true" ).find( Book.class );
	}

	public static List< Book > getRecentBooks () {
		return DataSupport.order( "ltime desc" ).find( Book.class );
	}
}
