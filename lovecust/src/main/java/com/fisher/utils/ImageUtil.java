package com.fisher.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;


public class ImageUtil {


	// Get bitmap by view, in another word, you can get the screen cut by view.
	public static Bitmap fGetBitmapByView ( View view ) {
		view.destroyDrawingCache();
		view.measure( View.MeasureSpec.makeMeasureSpec( 0, View.MeasureSpec.UNSPECIFIED ), View.MeasureSpec.makeMeasureSpec( 0, View.MeasureSpec.UNSPECIFIED ) );
		view.layout( 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() );
		view.setDrawingCacheEnabled( true );
		return view.getDrawingCache( true );
	}


	public static Bitmap fHandleImageEffect ( Bitmap bm, float hue, float saturation, float lum ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888 );
		Canvas canvas = new Canvas( bmp );
		Paint paint = new Paint( Paint.ANTI_ALIAS_FLAG );

		ColorMatrix hueMatrix = new ColorMatrix();
		hueMatrix.setRotate( 0, hue );
		hueMatrix.setRotate( 1, hue );
		hueMatrix.setRotate( 2, hue );

		ColorMatrix saturationMatrix = new ColorMatrix();
		saturationMatrix.setSaturation( saturation );

		ColorMatrix lumMatrix = new ColorMatrix();
		lumMatrix.setScale( lum, lum, lum, 1 );

		ColorMatrix imageMatrix = new ColorMatrix();
		imageMatrix.postConcat( hueMatrix );
		imageMatrix.postConcat( saturationMatrix );
		imageMatrix.postConcat( lumMatrix );

		paint.setColorFilter( new ColorMatrixColorFilter( imageMatrix ) );
		canvas.drawBitmap( bm, 0, 0, paint );

		return bmp;
	}

	public static Bitmap toNegativeEffect ( Bitmap bm ) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color;
		int r, g, b, a;

		Bitmap bmp = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];
		bm.getPixels( oldPx, 0, width, 0, 0, width, height );

		for ( int i = 0; i < width * height; i++ ) {
			color = oldPx[i];
			r = Color.red( color );
			g = Color.green( color );
			b = Color.blue( color );
			a = Color.alpha( color );

			r = 255 - r;
			g = 255 - g;
			b = 255 - b;

			if ( r > 255 ) {
				r = 255;
			} else if ( r < 0 ) {
				r = 0;
			}
			if ( g > 255 ) {
				g = 255;
			} else if ( g < 0 ) {
				g = 0;
			}
			if ( b > 255 ) {
				b = 255;
			} else if ( b < 0 ) {
				b = 0;
			}
			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}

	public static Bitmap toOldPhotoEffect ( Bitmap bm ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color = 0;
		int r, g, b, a, r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );
		for ( int i = 0; i < width * height; i++ ) {
			color = oldPx[i];
			a = Color.alpha( color );
			r = Color.red( color );
			g = Color.green( color );
			b = Color.blue( color );

			r1 = ( int ) ( 0.393 * r + 0.769 * g + 0.189 * b );
			g1 = ( int ) ( 0.349 * r + 0.686 * g + 0.168 * b );
			b1 = ( int ) ( 0.272 * r + 0.534 * g + 0.131 * b );

			if ( r1 > 255 ) {
				r1 = 255;
			}
			if ( g1 > 255 ) {
				g1 = 255;
			}
			if ( b1 > 255 ) {
				b1 = 255;
			}

			newPx[i] = Color.argb( a, r1, g1, b1 );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}

	public static Bitmap toStatueEffect ( Bitmap bm ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color, colorBefore;
		int a, r, g, b;
		int r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );

		for ( int i = 1; i < width * height; i++ ) {
			colorBefore = oldPx[i - 1];
			a = Color.alpha( colorBefore );
			r = Color.red( colorBefore );
			g = Color.green( colorBefore );
			b = Color.blue( colorBefore );

			color = oldPx[i];
			r1 = Color.red( color );
			g1 = Color.green( color );
			b1 = Color.blue( color );

			r = ( r - r1 + 127 );
			g = ( g - g1 + 127 );
			b = ( b - b1 + 127 );
			if ( r > 255 ) {
				r = 255;
			}
			if ( g > 255 ) {
				g = 255;
			}
			if ( b > 255 ) {
				b = 255;
			}
			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}

	public static Bitmap toStatueEffectB ( Bitmap bm ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color, colorBefore;
		int a, r, g, b;
		int r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );

		for ( int i = 1; i < width * height; i++ ) {
			colorBefore = oldPx[i - 1];
			a = Color.alpha( colorBefore );
			r = Color.red( colorBefore );
			g = Color.green( colorBefore );
			b = Color.blue( colorBefore );

			color = oldPx[i];
			r1 = Color.red( color );
			g1 = Color.green( color );
			b1 = Color.blue( color );

			r = ( r - r1 + 127 );
			g = ( g - g1 + 127 );
			b = ( b - b1 + 127 );

			if ( r > 255 ) {
				r = 255;
			} else if ( r < 0 ) {
				r = 0;
			}
			if ( g > 255 ) {
				g = 255;
			} else if ( g < 0 ) {
				g = 0;
			}
			if ( b > 255 ) {
				b = 255;
			} else if ( b < 0 ) {
				b = 0;
			}

			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}


	public static Bitmap toStatueEffectReverse ( Bitmap bm ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color, colorBefore;
		int a, r, g, b;
		int r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );

		for ( int i = 1; i < width * height; i++ ) {
			colorBefore = oldPx[i - 1];
			a = Color.alpha( colorBefore );
			r = Color.red( colorBefore );
			g = Color.green( colorBefore );
			b = Color.blue( colorBefore );

			color = oldPx[i];
			r1 = Color.red( color );
			g1 = Color.green( color );
			b1 = Color.blue( color );

			r = ( r1 - r + 127 );
			g = ( g1 - g + 127 );
			b = ( b1 - b + 127 );
			if ( r > 255 ) {
				r = 255;
			}
			if ( g > 255 ) {
				g = 255;
			}
			if ( b > 255 ) {
				b = 255;
			}
			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}
	public static Bitmap toStatueEffectReverseB ( Bitmap bm ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color, colorBefore;
		int a, r, g, b;
		int r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );

		for ( int i = 1; i < width * height; i++ ) {
			colorBefore = oldPx[i - 1];
			a = Color.alpha( colorBefore );
			r = Color.red( colorBefore );
			g = Color.green( colorBefore );
			b = Color.blue( colorBefore );

			color = oldPx[i];
			r1 = Color.red( color );
			g1 = Color.green( color );
			b1 = Color.blue( color );

			r = ( r - r1 + 127 );
			g = ( g - g1 + 127 );
			b = ( b - b1 + 127 );
			if ( r > 255 ) {
				r = 255;
			} else if ( r < 0 ) {
				r = 0;
			}
			if ( g > 255 ) {
				g = 255;
			} else if ( g < 0 ) {
				g = 0;
			}
			if ( b > 255 ) {
				b = 255;
			} else if ( b < 0 ) {
				b = 0;
			}
			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}


	public static Bitmap toSketchEffect ( Bitmap bm ) {
		int iDivider = 35;

		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color, colorBefore;
		int a, r, g, b;
		int r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );

		for ( int i = 1; i < width * height; i++ ) {
			colorBefore = oldPx[i - 1];
			a = Color.alpha( colorBefore );
			r = Color.red( colorBefore );
			g = Color.green( colorBefore );
			b = Color.blue( colorBefore );

			color = oldPx[i];
			r1 = Color.red( color );
			g1 = Color.green( color );
			b1 = Color.blue( color );

			r = Math.abs( r - r1 );
			g = Math.abs( g - g1 );
			b = Math.abs( b - b1 );


			if ( r > iDivider ) {
				r = 0;
			} else {
				r = 255;
			}
			if ( g > iDivider ) {
				g = 0;
			} else {
				g = 255;
			}
			if ( b > iDivider ) {
				b = 0;
			} else {
				b = 255;
			}

			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}
	public static Bitmap toSketchEffect ( Bitmap bm, int iDivider ) {
		if ( iDivider < 0 ) {
			iDivider = 0;
		} else if ( iDivider > 255 )
			iDivider = 255;

		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color, colorBefore;
		int a, r, g, b;
		int r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );

		for ( int i = 1; i < width * height; i++ ) {
			colorBefore = oldPx[i - 1];
			a = Color.alpha( colorBefore );
			r = Color.red( colorBefore );
			g = Color.green( colorBefore );
			b = Color.blue( colorBefore );

			color = oldPx[i];
			r1 = Color.red( color );
			g1 = Color.green( color );
			b1 = Color.blue( color );

			r = Math.abs( r - r1 );
			g = Math.abs( g - g1 );
			b = Math.abs( b - b1 );


			if ( r > iDivider ) {
				r = 0;
			} else {
				r = 255;
			}
			if ( g > iDivider ) {
				g = 0;
			} else {
				g = 255;
			}
			if ( b > iDivider ) {
				b = 0;
			} else {
				b = 255;
			}

			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}

	public static Bitmap toReverseEffect ( Bitmap bm ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color = 0;
		int r, g, b, a, r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );
		for ( int i = 0; i < width * height; i++ ) {
			color = oldPx[i];
			a = Color.alpha( color );
			r = Color.red( color );
			g = Color.green( color );
			b = Color.blue( color );

			r = Math.abs( r - 128 ) * 2;
			g = Math.abs( r - 128 ) * 2;
			b = Math.abs( r - 128 ) * 2;

			if ( r > 205 ) {
				r = 0;
			} else {
				r = 255;
			}
			if ( g > 205 ) {
				g = 0;
			} else {
				g = 255;
			}
			if ( b > 205 ) {
				b = 0;
			} else {
				b = 255;
			}

			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}

	public static Bitmap toReverseEffectB ( Bitmap bm ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color = 0;
		int r, g, b, a, r1, g1, b1;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );
		for ( int i = 0; i < width * height; i++ ) {
			color = oldPx[i];
			a = Color.alpha( color );
			r = Color.red( color );
			g = Color.green( color );
			b = Color.blue( color );

			r = Math.abs( r - 128 ) * 3;
			g = Math.abs( r - 128 ) * 3;
			b = Math.abs( r - 128 ) * 3;

			if ( r > 205 ) {
				r = 0;
			} else {
				r = 255;
			}
			if ( g > 205 ) {
				g = 0;
			} else {
				g = 255;
			}
			if ( b > 205 ) {
				b = 0;
			} else {
				b = 255;
			}

			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}

	public static Bitmap toReverseEffectC ( Bitmap bm ) {
		Bitmap bmp = Bitmap.createBitmap( bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888 );
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color = 0;
		int r, g, b, a;

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];

		bm.getPixels( oldPx, 0, bm.getWidth(), 0, 0, width, height );
		for ( int i = 0; i < width * height; i++ ) {
			color = oldPx[i];
			a = Color.alpha( color );
			r = Color.red( color );
			g = Color.green( color );
			b = Color.blue( color );

			r = Math.abs( r - 128 ) * 3;
			g = Math.abs( r - 128 ) * 3;
			b = Math.abs( r - 128 ) * 3;

			if ( r > 255 ) {
				r = 255;
			} else if ( r < 0 ) {
				r = 0;
			}
			if ( g > 255 ) {
				g = 255;
			} else if ( g < 0 ) {
				g = 0;
			}
			if ( b > 255 ) {
				b = 255;
			} else if ( b < 0 ) {
				b = 0;
			}

			newPx[i] = Color.argb( a, r, g, b );
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}


	public static Bitmap getImageByMatrix ( Bitmap bitmap, ColorMatrix colorMatrix ) {
		Bitmap bmp = Bitmap.createBitmap( bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888 );

		Canvas canvas = new Canvas( bmp );
		Paint paint = new Paint( Paint.ANTI_ALIAS_FLAG );
		paint.setColorFilter( new ColorMatrixColorFilter( colorMatrix ) );
		canvas.drawBitmap( bitmap, 0, 0, paint );

		return bmp;
	}

	public static Bitmap getImageByText ( Bitmap bitmap, String text ) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap bmp = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );

		Canvas canvas = new Canvas( bmp );
		Paint paint = new Paint( Paint.ANTI_ALIAS_FLAG );
		canvas.drawBitmap( bitmap, 0, 0, paint );

		if ( true ) {
			if ( true ) {
				paint.setColor( Color.YELLOW );
				paint.setTypeface( Typeface.SERIF );
				text="Typeface.SERIF";
			}
			else {
				paint.setColor( Color.BLACK );
				paint.setTypeface( Typeface.SANS_SERIF );
				text="Typeface.SANS_SERIF";
			}
		} else {
			if ( true )
				paint.setColor( 0x99CC33AA );
			else
				paint.setColor( 0xCC6633AA );
		}
		paint.setTextSize( 22f );

		canvas.drawText( text, width / 3, height / 2, paint );

		return bmp;
	}




	/*
	* 16-04-01 16:05
	* set bitmap color where alpha is none
	* */
	public static Bitmap changeBitmapColor ( Bitmap bm , int color) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		int c;
		int r, g, b, a;

		Bitmap bmp = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );

		int[] oldPx = new int[width * height];
		int[] newPx = new int[width * height];
		bm.getPixels( oldPx, 0, width, 0, 0, width, height );

		for ( int i = 0; i < width * height; i++ ) {
			c = oldPx[i];
			a = Color.alpha( c );
			if(a!=0){
				newPx[i]=color;
			}
		}
		bmp.setPixels( newPx, 0, width, 0, 0, width, height );
		return bmp;
	}
}





