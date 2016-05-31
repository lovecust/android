package com.lovecust.app.library;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class Book extends DataSupport {

	@SerializedName( "objId" )
	private long id;
	private long ctime;
	private long ltime;
	private boolean favorite = false;
	private long tag = -1;

	@SerializedName( "id" )
	private String bid;
	@SerializedName( "isbn13" )
	@Column( unique = true )
	private String isbn;

	private String title;
	private String origin_title;
	private String subtitle;
	private String alt_title;

	private String[] author;
	private String[] translator;
	private String author_intro;

	private String url;
	private String alt;
	private String image;
	private Images images;

	private String publisher;
	private String pubdate;
//	private Rating rating;
//	private BookTag[] tags;
	private String binding;
	private String price;
//	private Series series;
	private String pages;
	private String summary;
	private String catalog;

	private String ebook_url;
	private String ebook_price;


	public Book () {
		this.ctime = ltime = System.currentTimeMillis();
	}


//	public class BookTag extends DataSupport {
//		private String name;
//		private String title;
//
//		public String getTitle () {
//			return title;
//		}
//
//		public void setTitle ( String title ) {
//			this.title = title;
//		}
//
//		public String getName () {
//			return name;
//		}
//
//		public void setName ( String name ) {
//			this.name = name;
//		}
//	}
//
//	public class Rating extends DataSupport {
//		private int max;
//		private int numRaters;
//		private String average;
//		private int min;
//
//		public int getMax () {
//			return max;
//		}
//
//		public void setMax ( int max ) {
//			this.max = max;
//		}
//
//		public int getNumRaters () {
//			return numRaters;
//		}
//
//		public void setNumRaters ( int numRaters ) {
//			this.numRaters = numRaters;
//		}
//
//		public String getAverage () {
//			return average;
//		}
//
//		public void setAverage ( String average ) {
//			this.average = average;
//		}
//
//		public int getMin () {
//			return min;
//		}
//
//		public void setMin ( int min ) {
//			this.min = min;
//		}
//	}
//
//	public class Series extends DataSupport {
//		private String id;
//		private String title;
//
//		public String getId () {
//			return id;
//		}
//
//		public void setId ( String id ) {
//			this.id = id;
//		}
//
//		public String getTitle () {
//			return title;
//		}
//
//		public void setTitle ( String title ) {
//			this.title = title;
//		}
//	}


	public long getId () {
		return id;
	}

	public void setId ( long id ) {
		this.id = id;
	}

	public long getCtime () {
		return ctime;
	}

	public void setCtime ( long ctime ) {
		this.ctime = ctime;
	}

	public long getLtime () {
		return ltime;
	}

	public void setLtime ( long ltime ) {
		this.ltime = ltime;
	}

	public boolean isFavorite () {
		return favorite;
	}

	public void setFavorite ( boolean favorite ) {
		this.favorite = favorite;
	}

	public long getTag () {
		return tag;
	}

	public void setTag ( long tag ) {
		this.tag = tag;
	}

	public String getBid () {
		return bid;
	}

	public void setBid ( String bid ) {
		this.bid = bid;
	}

	public String getIsbn () {
		return isbn;
	}

	public void setIsbn ( String isbn ) {
		this.isbn = isbn;
	}

	public String getTitle () {
		return title;
	}

	public void setTitle ( String title ) {
		this.title = title;
	}

	public String getOrigin_title () {
		return origin_title;
	}

	public void setOrigin_title ( String origin_title ) {
		this.origin_title = origin_title;
	}

	public String getSubtitle () {
		return subtitle;
	}

	public void setSubtitle ( String subtitle ) {
		this.subtitle = subtitle;
	}

	public String getAlt_title () {
		return alt_title;
	}

	public void setAlt_title ( String alt_title ) {
		this.alt_title = alt_title;
	}

	public String[] getAuthor () {
		return author;
	}

	public void setAuthor ( String[] author ) {
		this.author = author;
	}

	public String[] getTranslator () {
		return translator;
	}

	public void setTranslator ( String[] translator ) {
		this.translator = translator;
	}

	public String getAuthor_intro () {
		return author_intro;
	}

	public void setAuthor_intro ( String author_intro ) {
		this.author_intro = author_intro;
	}

	public String getUrl () {
		return url;
	}

	public void setUrl ( String url ) {
		this.url = url;
	}

	public String getAlt () {
		return alt;
	}

	public void setAlt ( String alt ) {
		this.alt = alt;
	}

	public String getImage () {
		return image;
	}

	public void setImage ( String image ) {
		this.image = image;
	}

	public Images getImages () {
		return images;
	}

	public void setImages ( Images images ) {
		this.images = images;
	}

	public String getPublisher () {
		return publisher;
	}

	public void setPublisher ( String publisher ) {
		this.publisher = publisher;
	}

	public String getPubdate () {
		return pubdate;
	}

	public void setPubdate ( String pubdate ) {
		this.pubdate = pubdate;
	}

	public String getBinding () {
		return binding;
	}

	public void setBinding ( String binding ) {
		this.binding = binding;
	}

	public String getPrice () {
		return price;
	}

	public void setPrice ( String price ) {
		this.price = price;
	}

	public String getPages () {
		return pages;
	}

	public void setPages ( String pages ) {
		this.pages = pages;
	}

	public String getSummary () {
		return summary;
	}

	public void setSummary ( String summary ) {
		this.summary = summary;
	}

	public String getCatalog () {
		return catalog;
	}

	public void setCatalog ( String catalog ) {
		this.catalog = catalog;
	}

	public String getEbook_url () {
		return ebook_url;
	}

	public void setEbook_url ( String ebook_url ) {
		this.ebook_url = ebook_url;
	}

	public String getEbook_price () {
		return ebook_price;
	}

	public void setEbook_price ( String ebook_price ) {
		this.ebook_price = ebook_price;
	}

	public String getTagString () {
		Tag TAG = TagsManager.getTag( tag );
		if ( null == TAG )
			return "";
		return TAG.getTag();
	}

	@Override
	public String toString () {
		return new Gson().toJson( this );
	}

}
