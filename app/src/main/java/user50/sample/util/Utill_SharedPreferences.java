package user50.sample.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <pre>
 * SharedPreferences Class
 * <strong>MODE_PRIVATE</strong> : Just for this application can read, write preferences
 * <strong>MODE_WORLD_READABLE</strong> : Another application can read preferences
 * <strong>MODE_WORLD_WRITEABLE</strong> : Another application can write preferences
 * <strong>MODE_MULTI_PROCESS</strong> : Another application can read, write preferences
 * 
 * @author abyser (abyser@naver.com)
 * @since 2016-04-14 ~
 * @revisionHistory
 * <strong>2016-04-14 First write</strong>
 * <strong>2017-01-09 Modify comment</strong>
 * <strong>2018-04-27 Add method</strong>
 * └ {@link #putDoubleValue(String, double)}, {@link #getDoubleValue(String, double)}
 * </pre>
 */
public class Utill_SharedPreferences {

	/** Default preference name */
	private static final String DEFAULT_PREF_NAME = "DEFAULT_PREF_NAME";

	/** Preference */
	private SharedPreferences pref;

	/** Preference name */
	private String pref_name;

	// Constructor parameter
	private Context context;

	/**
	 * Constructor
	 * @param context
	 */
	public Utill_SharedPreferences(Context context, String pref_name) throws NullPointerException {
		
		if(context == null){
			throw new NullPointerException("context is null");
		}

		this.context = context;
		if(pref_name == null || pref_name.length() <= 0){
			this.pref_name = DEFAULT_PREF_NAME;
		}else{
			this.pref_name = pref_name;	
		}

		pref = context.getSharedPreferences(this.pref_name, Context.MODE_PRIVATE);

	}

	/**
	 * <pre>
	 * Save String value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 2:16:25
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param value preference item value
	 * @return <strong>true</strong> success <strong>false</strong> fail
	 */
	public boolean putStringValue(String key, String value) {
		
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		return editor.commit();

	}

	/**
	 * <pre>
	 * Save Int value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 2:18:48
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param value preference item value
	 * @return <strong>true</strong> success <strong>false</strong> fail
	 */
	public boolean putIntValue(String key, int value) {
		
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(key, value);
		return editor.commit();

	}
	
	/**
	 * <pre>
	 * Save Long value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 2:18:54
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param value preference item value
	 * @return <strong>true</strong> success <strong>false</strong> fail
	 */
	public boolean putLongValue(String key, long value) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(key, value);
		return editor.commit();

	}
	
	/**
	 * <pre>
	 * Save Float value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 2:19:01
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param value preference item value
	 * @return <strong>true</strong> success <strong>false</strong> fail
	 */
	public boolean putFloatValue(String key, float value) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putFloat(key, value);
		return editor.commit();

	}

	/**
	 * <pre>
	 * Save Double value to preference
	 * </pre>
	 * @since
	 * <pre>
	 * Create : 2018. 04. 27. 오전 11:37:00
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param value preference item value
	 * @return <strong>true</strong> success <strong>false</strong> fail
	 */
	public boolean putDoubleValue(String key, double value) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(key, Double.doubleToRawLongBits(value));
		return editor.commit();

	}

	/**
	 * <pre>
	 * Save Boolean value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 2:19:06
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param value preference item value
	 * @return <strong>true</strong> success <strong>false</strong> fail
	 */
	public boolean putBooleanValue(String key, boolean value) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(key, value);
		return editor.commit();

	}

	/**
	 * <pre>
	 * Get String value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 3:11:15
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param default_value default value
	 * @return value or default_value
	 */
	public String getStringValue(String key, String default_value) {

		try {
			return pref.getString(key, default_value);
		} catch (Exception e) {
			return default_value;
		}

	}

	/**
	 * <pre>
	 * Get Int value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 3:11:34
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param default_value default value
	 * @return value or default_value
	 */
	public int getIntValue(String key, int default_value) {

		try {
			return pref.getInt(key, default_value);
		} catch (Exception e) {
			return default_value;
		}

	}

	/**
	 * <pre>
	 * Get Long value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 3:11:37
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param default_value default value
	 * @return value or default_value
	 */
	public long getLongValue(String key, long default_value) {

		try {
			return pref.getLong(key, default_value);
		} catch (Exception e) {
			return default_value;
		}

	}
	
	/**
	 * <pre>
	 * Get Boolean value to preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 3:12:03
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param default_value default value
	 * @return value or default_value
	 */
	public float getFloatValue(String key, float default_value) {

		try {
			return pref.getFloat(key, default_value);
		} catch (Exception e) {
			return default_value;
		}

	}

	/**
	 * <pre>
	 * Get Double value to preference
	 * </pre>
	 * @since
	 * <pre>
	 * Create : 2018. 04. 27. 오전 11:44:00
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param default_value default value
	 * @return value or default_value
	 */
	public double getDoubleValue(String key, double default_value) {

		try {
			return Double.longBitsToDouble(pref.getLong(key, Double.doubleToLongBits(default_value)));
		} catch (Exception e) {
			return default_value;
		}

	}

	/**
	 * <pre>
	 * Get boolean value preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 2:20:52
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @param default_value default value
	 * @return value or default_value
	 */
	public boolean getBooleanValue(String key, boolean default_value) {

		try {
			return pref.getBoolean(key, default_value);
		} catch (Exception e) {
			return default_value;
		}

	}

	/**
	 * <pre>
	 * Delete one preference
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 2:19:21
	 * </pre>
	 * @author abyser
	 * @param key preference item key
	 * @return <strong>true</strong> success <strong>false</strong> fail
	 */
	public boolean remove(String key) {

		SharedPreferences.Editor editor = pref.edit();
		editor.remove(key);
		return editor.commit();

	}

	/**
	 * <pre>
	 * Delete all preferences
	 * </pre>
	 * @since 
	 * <pre>
	 * Create : 2017. 1. 9. 오전 2:19:35
	 * </pre>
	 * @author abyser
	 * @return <strong>true</strong> success <strong>false</strong> fail
	 */
	public boolean removeAll() {

		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		return editor.commit();

	}
	
}