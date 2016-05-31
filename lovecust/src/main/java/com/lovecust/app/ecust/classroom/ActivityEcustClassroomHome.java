package com.lovecust.app.ecust.classroom;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.R;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class ActivityEcustClassroomHome extends AlphaActivity {

	private LinearLayout layoutClassroomHolder;
	private ToggleButton[] buildings;
	private ToggleButton[] courses;
	private ToggleButton lastCheckedBuilding;
	private ToggleButton lastCheckedCourse;
	private LinearLayout layoutPanelBuildings;
	private LinearLayout layoutPanelCourses;
	private ClassroomBuilding classroom;
	private TextView textGeneralInfo;
	private SettingClassroom setting;


	@Override
	public int getLayout() {
		return R.layout.activity_ecust_classroom_home;
	}

	@Override
	public void init () {
		setOnBackListener().setTitle( R.string.title_ecust_classroom );
		layoutPanelBuildings = (LinearLayout) findViewById( R.id.layoutPanelBuildings );
		layoutPanelCourses = (LinearLayout) findViewById( R.id.layoutPanelCourses );
		layoutClassroomHolder = (LinearLayout) findViewById( R.id.layoutClassroomHolder );
		textGeneralInfo = (TextView) findViewById( R.id.text );
		buildings = new ToggleButton[ 5 ];
		courses = new ToggleButton[ 6 ];
		setListener();
		setting = SettingClassroom.getInstance();
		buildings[ setting.getLastBuilding() ].setChecked( true );
		courses[ setting.getLastCourse() ].setChecked( true );
	}

	private void setListener () {
		for ( int i = 0; i < buildings.length; i++ ) {
			buildings[ i ] = (ToggleButton) View.inflate( this, R.layout.item_ecust_classroom_toggle_building, null );
			buildings[ i ].setLayoutParams( new LinearLayout.LayoutParams( 0, ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
			String text = (char) ( 'A' + i ) + "";
			buildings[ i ].setText( text );
			buildings[ i ].setTextOff( text );
			buildings[ i ].setTextOn( text );
			final int finalI = i;
			buildings[ i ].setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged ( CompoundButton buttonView, boolean isChecked ) {
					if ( isChecked ) {
						setting.setLastBuilding( finalI );

						buttonView.setClickable( false );
						if ( null != lastCheckedBuilding ) {
							lastCheckedBuilding.setClickable( true );
							lastCheckedBuilding.setChecked( false );
						}
						lastCheckedBuilding = (ToggleButton) buttonView;
						if ( null == lastCheckedCourse )
							return;
					}
				}
			} );
			layoutPanelBuildings.addView( buildings[ i ] );
		}

		for ( int i = 0; i < courses.length; i++ ) {
			courses[ i ] = (ToggleButton) View.inflate( this, R.layout.item_ecust_classroom_toggle_course, null );
			courses[ i ].setLayoutParams( new LinearLayout.LayoutParams( 0, ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
			if ( i == 0 ) {
				courses[ i ].setChecked( true );
				lastCheckedCourse = courses[ i ];
			}
			String text = ( i + i + 1 ) + "-" + ( i + i + 2 );
			courses[ i ].setText( text );
			courses[ i ].setTextOff( text );
			courses[ i ].setTextOn( text );
			final int finalI = i;
			courses[ i ].setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged ( CompoundButton buttonView, boolean isChecked ) {
					if ( isChecked ) {
						setting.setLastCourse( finalI );

						buttonView.setClickable( false );
						if ( null != lastCheckedCourse ) {
							lastCheckedCourse.setClickable( true );
							lastCheckedCourse.setChecked( false );
						}
						lastCheckedCourse = (ToggleButton) buttonView;
						if ( null == lastCheckedBuilding )
							return;

					}
				}
			} );
			layoutPanelCourses.addView( courses[ i ] );
		}
	}

	private void setGeneralText () {
		int course = Integer.valueOf( classroom.getCourse() );
		String text = classroom.getBuilding() + " " + ( course + "-" + ( course + 1 ) ) + "\n";
		text += getString( R.string.text_ecust_classroom_available_room_amount ) + classroom.getAmount_available() + "\n";
		text += getString( R.string.text_ecust_classroom_occupied_room_amount ) + classroom.getAmount_occupied();
		textGeneralInfo.setText( text );
	}

	private void flush (  ) {
//		classroom = new Gson().fromJson( response.response, ClassroomBuilding.class );
		if ( null == classroom ) {
			log( toast( "Server error!" ) );
			return;
		}
		setGeneralText();

		layoutClassroomHolder.removeAllViews();

		String[] available = classroom.getAvailable();
		String[] occupiedRooms = classroom.getOccupied();
		// true means the room is occupied
		HashMap< String, Boolean > rooms = new HashMap<>();
		for ( int i = 0; i < available.length; i++ ) {
			rooms.put( available[ i ], false );
		}
		for ( int i = 0; i < occupiedRooms.length; i++ ) {
			rooms.put( occupiedRooms[ i ], true );
		}
		Map< String, Boolean > room = sortMapByKey( rooms );

		Iterator< Map.Entry< String, Boolean > > iterator = room.entrySet().iterator();

		flush( iterator );
	}

	private void flush ( Iterator< Map.Entry< String, Boolean > > iterator ) {
		int i = 0;
		TextView textView;
		LinearLayout layout = null;
		String index = "1";
		while ( iterator.hasNext() ) {
			Map.Entry< String, Boolean > entry = iterator.next();
			textView = (TextView) View.inflate( this, R.layout.item_ecust_classroom_room, null );
			textView.setText( classroom.getBuilding() + entry.getKey() );
			if ( entry.getValue() ) {
				textView.setTextColor( Color.GRAY );
			} else {
				textView.setTextColor( Color.BLUE );
			}
			if ( i % 8 == 0 || !index.equals( entry.getKey().substring( 0, 1 ) ) ) {
				i = 1;
				layout = new LinearLayout( this );
				layout.setLayoutParams( new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
				index = entry.getKey().substring( 0, 1 );
				layoutClassroomHolder.addView( layout );
			}
			layout.addView( textView );
			i++;
		}
	}

	public Map< String, Boolean > sortMapByKey ( Map< String, Boolean > oriMap ) {
		if ( oriMap == null || oriMap.isEmpty() ) {
			return null;
		}
		Map< String, Boolean > sortedMap = new TreeMap<>( new Comparator< String >() {
			public int compare ( String key1, String key2 ) {
				int intKey1 = 0;
				int intKey2 = 0;
				try {
					intKey1 = Integer.valueOf( key1 );
					intKey2 = Integer.valueOf( key2 );
				} catch ( Exception e ) {
					intKey1 = 0;
					intKey2 = 0;
				}
				return intKey1 - intKey2;
			}
		} );
		sortedMap.putAll( oriMap );
		return sortedMap;
	}

}
