package user50.sample.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import user50.sample.BuildConfig;

import static android.content.Context.ACTIVITY_SERVICE;

public class Util {

	private static final String LOG_TAG = "Utill";

	private static final long MILLI_SECOND = 1000;
	private static final long MILLI_MINUTE = 60000;
	private static final long MILLI_HOUR = 3600000;
	private static final long MILLI_DAY = 86400000;
//	private static final long MILLI_WEEK = 604800000;
//	private static final long MILLI_MONTH_28 = 17971200000;
//	private static final long MILLI_MONTH_30 = 18144000000;
//	private static final long MILLI_MONTH_31 = 18230400000;
//	private static final long MILLI_YEAR = 31536000000;

	public static final int BOOLEAN_BIT_SIZE = 1;
	public static final int BYTE_BIT_SIZE = Byte.SIZE;
	public static final int CHAR_BIT_SIZE = Character.SIZE;
	public static final int SHORT_BIT_SIZE = Short.SIZE;
	public static final int INT_BIT_SIZE = Integer.SIZE;
	public static final int LONG_BIT_SIZE = Long.SIZE;
	public static final int FLOAT_BIT_SIZE = Float.SIZE;
	public static final int DOUBLE_BIT_SIZE = Double.SIZE;

	public static final int BOOLEAN_BYTE_SIZE = BOOLEAN_BIT_SIZE;
	public static final int BYTE_BYTE_SIZE = BYTE_BIT_SIZE / 8;
	public static final int CHAR_BYTE_SIZE = CHAR_BIT_SIZE / 8;
	public static final int SHORT_BYTE_SIZE = SHORT_BIT_SIZE / 8;
	public static final int INT_BYTE_SIZE = INT_BIT_SIZE / 8;
	public static final int LONG_BYTE_SIZE = LONG_BIT_SIZE / 8;
	public static final int FLOAT_BYTE_SIZE = FLOAT_BIT_SIZE / 8;
	public static final int DOUBLE_BYTE_SIZE = DOUBLE_BIT_SIZE / 8;

	/**
	 * Show simple one button alert dialog
	 * @param context Context
	 * @param title Dialog title
	 * @param msg Dialog message
	 */
	public static void showSimpleDialog(Context context, String title, String msg){

		if(context == null){
			throw new NullPointerException("Can not show simple dialog. Cause context is null");
		}

		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setTitle(title);
		ab.setMessage(msg);
		ab.setNegativeButton("확인", null);
		ab.create();
		ab.show();

	}

	/**
	 * Show simple toast
	 * @param context Context
	 * @param msg Toast message
	 * @param isShowShort Is show short toast?
	 */
	public static void showToast(Context context, String msg, boolean isShowShort){

		if(context == null){
			throw new NullPointerException("Can not show toast. Cause context is null");
		}

		Toast.makeText(context, msg, (isShowShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)).show();

	}

	/**
	 * Return character string
	 * @param str find string
	 * @return character string
	 */
	public static String getCharacterString(String str){

		String character_string = str.replaceAll("[0-9]", "");

		if(character_string.length() <= 0){
			return null;
		}else{
			return character_string;
		}

	}

	/**
	 * Return number string
	 * @param str find string
	 * @return number string
	 */
	public static String getNumberString(String str){

		String number_string = str.replaceAll("[^0-9]", "");
//		String number_string = str.replaceAll("[^\\d]", ""); // Can using this code
//		String number_string = str.replaceAll("\\D", ""); // Can using this code

		if(number_string.length() <= 0){
			return null;
		}else{
			return number_string;
		}

	}

	/**
	 * Return special string.
	 * @param str find string
	 * @return special string
	 */
	public static String getSpecialString(String str){

		String number_string = str.replaceAll("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*", "");

		return number_string.length() <= 0 ? null : number_string;

	}

	/**
	 * Return if device type is tablet.
	 * @return if true is tablet, if false is smartphone
	 */
	public static boolean isTablet() {

		try {

			InputStream is = Runtime.getRuntime().exec("getprop ro.build.characteristics").getInputStream();
			byte[] bts = new byte[1024];
			is.read(bts);
			is.close();

			return new String(bts).toLowerCase().contains("tablet");

		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}

	}

	/**
	 * Return device android version code.
	 * @return device android version code.
	 */
	public static int getAndroidVersionToCode(){
		return Build.VERSION.SDK_INT;
	}

	/**
	 * Return device android version name.
	 * @return device android version name.
	 */
	public static String getAndroidVersionToName(){

		int version_code = Build.VERSION.SDK_INT;
		String version_name = "";

		switch (version_code) {
			case Build.VERSION_CODES.BASE: // 1.0
				version_name = "BASE";
				break;
			case Build.VERSION_CODES.BASE_1_1: // 1.1
				version_name = "BASE_1_1";
				break;
			case Build.VERSION_CODES.CUPCAKE: // 1.5
				version_name = "CUPCAKE";
				break;
			case Build.VERSION_CODES.CUR_DEVELOPMENT: // 개발 버젼
				version_name = "CUR_DEVELOPMENT";
				break;
			case Build.VERSION_CODES.DONUT: // 1.6
				version_name = "DONUT";
				break;
			case Build.VERSION_CODES.ECLAIR: // 2.0
				version_name = "ECLAIR";
				break;
			case Build.VERSION_CODES.ECLAIR_0_1: // 2.0.1
				version_name = "ECLAIR_0_1";
				break;
			case Build.VERSION_CODES.ECLAIR_MR1: // 2.1
				version_name = "ECLAIR_MR1";
				break;
			case Build.VERSION_CODES.FROYO: // 2.2
				version_name = "FROYO";
				break;
			case Build.VERSION_CODES.GINGERBREAD: // 2.3.1
				version_name = "GINGERBREAD";
				break;
			case Build.VERSION_CODES.GINGERBREAD_MR1: // 2.3.3
				version_name = "GINGERBREAD_MR1";
				break;
			case Build.VERSION_CODES.HONEYCOMB: // 3.0
				version_name = "HONEYCOMB";
				break;
			case Build.VERSION_CODES.HONEYCOMB_MR1: // 3.1
				version_name = "HONEYCOMB_MR1";
				break;
			case Build.VERSION_CODES.HONEYCOMB_MR2: // 3.2
				version_name = "HONEYCOMB_MR2";
				break;
			case Build.VERSION_CODES.ICE_CREAM_SANDWICH: // 4.0
				version_name = "ICE_CREAM_SANDWICH";
				break;
			case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1: // 4.0.3
				version_name = "ICE_CREAM_SANDWICH_MR1";
				break;
			case Build.VERSION_CODES.JELLY_BEAN: // 4.1.2
				version_name = "JELLY_BEAN";
				break;
			case Build.VERSION_CODES.JELLY_BEAN_MR1: // 4.2.2
				version_name = "JELLY_BEAN_MR1";
				break;
			case Build.VERSION_CODES.JELLY_BEAN_MR2: // 4.3.1
				version_name = "JELLY_BEAN_MR2";
				break;
			case Build.VERSION_CODES.KITKAT: // 4.4.2
				version_name = "KITKAT";
				break;
			case Build.VERSION_CODES.KITKAT_WATCH: // 4.4W.2
				version_name = "KITKAT_WATCH";
				break;
			case Build.VERSION_CODES.LOLLIPOP: // 5.0
				version_name = "LOLLIPOP";
				break;
			case Build.VERSION_CODES.LOLLIPOP_MR1: // 5.1
				version_name = "LOLLIPOP_MR1";
				break;
			case Build.VERSION_CODES.M: // 6.0
				version_name = "MASHMALLOW";
				break;
			case Build.VERSION_CODES.N: // 7.0
				version_name = "NOUGAT";
				break;
			case Build.VERSION_CODES.N_MR1: // 7.1.1
				version_name = "NOUGAT_MR1";
				break;
			case Build.VERSION_CODES.O: // 8.0
				version_name = "OREO";
				break;
			case Build.VERSION_CODES.O_MR1:N: // 8.1
				version_name = "OREO_MR1";
				break;
			case Build.VERSION_CODES.P: // 9.0
				version_name = "PIE";
				break;
			case Build.VERSION_CODES.Q: // 10.0
					version_name = "QEEN CAKE";
				break;
			case Build.VERSION_CODES.R: // 11.0
					version_name = "RED VELVET CAKE";
				break;
			case Build.VERSION_CODES.S: // 12.0
					version_name = "SNOW CONE";
				break;
//			case Build.VERSION_CODES.S_V2: // 12L
//					version_name = "SNOW CONE_V2";
//				break;
		}

		return version_name;

	}

	/**
	 * 앱 버전 코드 반환.
	 * @param context {@link Context}
	 * @return -1 : 오류 발생시 반환, 앱 버전 코드 : 정상 반환
	 */
	public static int getApplicationVersionToCode(@NonNull  Context context){

		int result = -1;

		PackageInfo pi = null;
		try {

			pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			result = pi.versionCode;

		} catch (PackageManager.NameNotFoundException e) {
			result = -1;
		} catch (Exception e){
			result = -1;
		}

		return result;

	}

	/**
	 * 앱 버전 명 반환.
	 * @param context {@link Context}
	 * @return null : 오류 발생시 반환, 앱 버전 명 : 정상 반환
	 */
	public static String getApplicationVersionToName(Context context){

		String result = null;

		PackageInfo pi = null;
		try {

			pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			result = pi.versionName;

		} catch (PackageManager.NameNotFoundException e) {
			result = null;
		} catch (Exception e){
			result = null;
		}

		return result;

	}

    /**
     * 데이터 네트워크 또는 WIFI로 네트워크 연결 여부 반환.
	 * @param context {@link Context}
     * @return true : 데이터 네트워크 또는 WIFI로 네트워크 연결 중이거나 연결 됨, false : 네트워크 연결 안됨
     */
	@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
	public static boolean isConnectNetwork(Context context){

		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			Network n = cm.getActiveNetwork();
			if(n != null) {

				NetworkInfo ni = cm.getNetworkInfo(n);
				if(ni != null) {

					int type = ni.getType();

					switch (type) {
						case ConnectivityManager.TYPE_MOBILE:
							return true;
						case ConnectivityManager.TYPE_WIFI:
							return true;
						case ConnectivityManager.TYPE_WIMAX:
							return false;
						case ConnectivityManager.TYPE_ETHERNET:
							return false;
						case ConnectivityManager.TYPE_BLUETOOTH:
							return false;
						default:
							return false;
					}

				}else{
					return false;
				}

			}else{
				return false;
			}

		}else {

			// wifi 연결 상태 확인
			NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if(wifiInfo != null) {

				NetworkInfo.State wifi = wifiInfo.getState();
				if (wifi != null) {

					if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
						return true;
					}

				}

			}

			// 데이터네트워크 연결 상태 확인
			NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if(mobileInfo != null) {

				NetworkInfo.State mobile = mobileInfo.getState();
				if (mobile != null) {

					if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
						return true;
					}

				}

			}

			return false;

		}

    }

	/**
	 * WIFI로 네트워크 연결 여부 반환.
	 * @param context {@link Context}
	 * @return true : WIFI로 네트워크 연결 중이거나 연결 됨, false : WIFI로 네트워크 연결 안됨
	 */
	@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
	public static boolean isConnectWifi(Context context){

		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			Network n = cm.getActiveNetwork();
			if(n != null) {

				NetworkInfo ni = cm.getNetworkInfo(n);
				if(ni != null) {

					int type = ni.getType();

					switch (type) {
						case ConnectivityManager.TYPE_MOBILE:
							return false;
						case ConnectivityManager.TYPE_WIFI:
							return true;
						case ConnectivityManager.TYPE_WIMAX:
							return false;
						case ConnectivityManager.TYPE_ETHERNET:
							return false;
						case ConnectivityManager.TYPE_BLUETOOTH:
							return false;
						default:
							return false;
					}

				}else{
					return false;
				}

			}else{
				return false;
			}

		}else {

			try {

				// wifi 연결 상태 확인
				NetworkInfo.State wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
				if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
					return true;
				}

				return false;

			} catch (NullPointerException e) {
				return false;
			}

		}

	}

	/**
	 * 데이터 네트워크로 네트워크 연결 여부 반환.
	 * @param context {@link Context}
	 * @return true : 데이터 네트워크로 네트워크 연결 중이거나 연결 됨, false : 데이터 네트워크로 네트워크 연결 안됨
	 */
	@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
	public static boolean isConnectMobileData(Context context){

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			Network n = cm.getActiveNetwork();
			if(n != null) {

				NetworkInfo ni = cm.getNetworkInfo(n);
				if(ni != null) {

					int type = ni.getType();

					switch (type) {
						case ConnectivityManager.TYPE_MOBILE:
							return true;
						case ConnectivityManager.TYPE_WIFI:
							return false;
						case ConnectivityManager.TYPE_WIMAX:
							return false;
						case ConnectivityManager.TYPE_ETHERNET:
							return false;
						case ConnectivityManager.TYPE_BLUETOOTH:
							return false;
						default:
							return false;
					}

				}else{
					return false;
				}

			}else{
				return false;
			}

		}else {

			try {

				// 데이터네트워크 연결 상태 확인
				NetworkInfo.State mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
				if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
					return true;
				}

				return false;

			} catch (NullPointerException e) {
				return false;
			}

		}

	}

	/**
	 * Return supported charset decode string.
	 * @param str decode string
	 * @return
	 * <li>{@code ArrayList<String>.get(0) = decode string}
	 * <li>{@code ArrayList<String>.get(1) = decode string}
	 * <li>{@code ArrayList<String>.get(2) = decode string}
	 * <br>...
	 */
	public static ArrayList<String> checkCharset(String str){

		ArrayList<String> array = new ArrayList<String>();

		for (Map.Entry<String, Charset> entry : Charset.availableCharsets().entrySet()) {

			String value = new String(str.getBytes(), entry.getValue());
			array.add(value);
			Log.i(LOG_TAG, "Using charset : " + entry.getValue() + ", decode unknown str : " + value);

		}

		return array;

	}

	/**
	 * Drawable을 Bitmap으로 변환해서 반환.
	 * @param context {@link Context}
	 * @param drawableId Drawable id
	 * @return 변환 된 {@link Bitmap}
	 */
	public static Bitmap transDrawableToBitmap(Context context, @DrawableRes int drawableId){

		BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(drawableId);
		return drawable.getBitmap();

	}

	/**
	 * Bitmap을 Drawable로 변환해서 반환.
	 * @param bitmap 변환 할 {@link Bitmap}
	 * @return 변환 된 {@link Drawable}
	 */
	public static Drawable transBitmapToDrawable(Bitmap bitmap){
		return new BitmapDrawable(bitmap);
	}

	/**
	 * 무작위 차량 번호를 생성하여 반환.
	 * @return 생성 된 차량 번호
	 */
	public static String createRandomCarNumber(){

		Random r = new Random();

		int tmpFrontNumber = r.nextInt(99);
		while (tmpFrontNumber < 10){
			tmpFrontNumber = r.nextInt(99);
		}
		int tmpBackNumber = r.nextInt(9999);
		while (tmpBackNumber < 1000){
			tmpBackNumber = r.nextInt(9999);
		}

		return String.valueOf(tmpFrontNumber) + "허" + String.valueOf(tmpBackNumber);

	}

	/**
	 * 무작위 위경도를 생성하여 반환.
	 * @param latMinRange 0.0 ~ 1.0
	 * @param latMaxRange 0.0 ~ 1.0
	 * @param lonMinRange 0.0 ~ 1.0
	 * @param lonMaxRange 0.0 ~ 1.0
	 * @return 생성 된 위경도 좌표
	 *         double[0] : 위도(37.xxxxxx)
	 *         double[1] : 경도(127.xxxxxx)
	 */
	public static double[] createRandomLatLon(double latMinRange, double latMaxRange, double lonMinRange, double lonMaxRange){

		double lat = 0.0d;
		boolean isFindRandomLat = false;
		while (!isFindRandomLat) {

			lat = Math.random();
			if (lat >= latMinRange && lat < latMaxRange) {
				lat = lat + 37;
				isFindRandomLat = true;
			}

		}

		double lon = 0.0d;
		boolean isFindRandomLon = false;
		while (!isFindRandomLon) {

			lon = Math.random();
			if (lon >= lonMinRange && lon <= lonMaxRange) {
				lon = lon + 127;
				isFindRandomLon = true;
			}

		}

		return new double[]{lat, lon};

	}

	/**
	 * 무작위 색상값을 생성하여 반환.
	 * @return 무작위 색상값
	 */
	public static int createRandomColor(){
		Random rnd = new Random();
		return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
	}

	/**
	 * 테스트 필요.
	 * 내부 메모리 사용 가능 용량 반환.
	 * @return 내부 메모리 사용 가능 용량
	 */
	public static long getInternalStorageRemainSize() {

		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = (long)stat.getBlockSize();
		long availableBlocks = (long)stat.getAvailableBlocks();

		return availableBlocks * blockSize;

	}

	/**
	 * 테스트 필요.
	 * 내부 메모리 전체 용량 반환.
	 * @return 내부 메모리 전체 용량
	 */
	public static long getInternalStorageTotalSize() {

		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = (long)stat.getBlockSize();
		long totalBlocks = (long)stat.getBlockCount();

		return totalBlocks * blockSize;

	}

	/**
	 * 테스트 필요.
	 * 외부 메모리 사용 가능 용량 반환.
	 * @return 외부 메모리 사용 가능 용량
	 */
	public static long getExternalStorageRemainSize() {

		if(Environment.getExternalStorageState().equals("mounted")) {

			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = (long)stat.getBlockSize();
			long availableBlocks = (long)stat.getAvailableBlocks();

			return availableBlocks * blockSize;

		} else {
			return -1L;
		}

	}

	/**
	 * 테스트 필요.
	 * 외부 메모리 전체 용량 반환.
	 * @return 외부 메모리 전체 용량
	 */
	public static long getExternalStorageTotalSize() {

		if(Environment.getExternalStorageState().equals("mounted")) {

			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = (long)stat.getBlockSize();
			long totalBlocks = (long)stat.getBlockCount();

			return totalBlocks * blockSize;

		} else {
			return -1L;
		}

	}

	/**
	 * 테스트 필요.
	 * 비행기 모드 켜져있는지 여부 반환.
	 * @return true : 비행기 모드 켜져 있음, false : 비행기 모드 꺼져 있음
	 */
	public static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
	}

    /**
     * assets 폴더에 있는 파일 저장소에 넣기.
     * @param context {@link Context}
     * @param assetFileName 복사 할 asset 파일 명
     * @param pastePath 붙여 넣을 경로
     * @return true : 성공, false : 실패
     */
	public static boolean getAssetFile(Context context, String assetFileName, String pastePath) {

		boolean isOk = false;

		if (context == null) {
			Log.e(LOG_TAG, "Copy failed. Cause context is null.");
			isOk = false;
		}

		if (assetFileName == null || assetFileName.length() <= 0) {
			Log.e(LOG_TAG, "Copy failed. Cause asset file name is null.");
			isOk = false;
		}

		if (pastePath == null || pastePath.length() <= 0) {
			Log.e(LOG_TAG, "Copy failed. Cause paste path is null.");
			isOk = false;
		}

		File outFile = new File(pastePath, assetFileName);
		if (!outFile.getParentFile().exists()) {
			Log.e(LOG_TAG, "Copy failed. Cause paste path is not exists.");
			isOk = false;
		}

		if (isOk) {

			InputStream in = null;
			OutputStream out = null;

			try {

				in = context.getResources().getAssets().open(assetFileName);

				out = new FileOutputStream(outFile);

				byte[] buffer = new byte[1024];
				int read;
				while ((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}

			} catch (IOException e) {
				Log.e(LOG_TAG, "Asset file copy and paste failed. Cause " + e.getMessage());
				isOk = false;
			} finally {

				try {

					if (in != null) {
						in.close();
					}

					if (out != null) {
						out.close();
					}

				} catch (Exception e) {
					// not catch
				}

			}

		}

		return isOk;

    }

	/**
	 * JSON 객체에 들어있는 데이터를 HashMap으로 변환하여 반환.
	 * @param json
	 * @return 변환 된 HashMap
	 * @throws JSONException
	 * @see {@link #toMap}, {@link #toList(JSONArray)}
	 */
	public static HashMap<String, Object> jsonToMap(JSONObject json) throws JSONException {
		HashMap<String, Object> retMap = new HashMap<String, Object>();

		if(json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}

	/**
	 *
	 * @param object
	 * @return
	 * @throws JSONException
	 * @see {@link #jsonToMap(JSONObject)}, {@link #toList(JSONArray)}
	 */
	private static HashMap<String, Object> toMap(JSONObject object) throws JSONException {

		HashMap<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while(keysItr.hasNext()) {

			String key = keysItr.next();
			Object value = object.get(key);

			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if(value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}

			map.put(key, value);

		}

		return map;

	}

	/**
	 *
	 * @param array
	 * @return
	 * @throws JSONException
	 * @see {@link #jsonToMap(JSONObject)}, {@link #toMap(JSONObject)}
	 */
	private static ArrayList<Object> toList(JSONArray array) throws JSONException {

		ArrayList<Object> list = new ArrayList<Object>();

		for(int i = 0; i < array.length(); i++) {

			Object value = array.get(i);

			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if(value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}

			list.add(value);

		}

		return list;

	}

	/**
	 * 백그라운드 태스크에 있는 앱 중 패키지명과 일치하는 앱의 액티비티를 보여 줌.
	 * @param context {@link Context}
	 * @param packageName 패키지명
	 */
	@RequiresPermission(Manifest.permission.REORDER_TASKS)
	public static void showPauseActivity(Context context, String packageName){

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(Integer.MAX_VALUE);
		if (!tasks.isEmpty()) {

			int tasksSize = tasks.size();

			for (int i = 0; i < tasksSize; i++) {

				ActivityManager.RunningTaskInfo taskinfo = tasks.get(i);

				if (taskinfo.topActivity.getPackageName().equals(packageName)) {
					am.moveTaskToFront(taskinfo.id, 0);
				}

			}

		}

	}

	/**
	 * 코드 리스트에 있는 코드를 치환하여 반환.
	 * @param codeList 코드 리스트
	 * @param code 치환 할 코드
	 * @return 치환 한 코드
	 */
	private static String transCodeToName(HashMap<String, String> codeList, String code){

		Set<String> set = codeList.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {

			String codeListCode = (String) iterator.next();

			if(code.equals(codeListCode)){
				return codeList.get(codeListCode);
			}

		}

		return null;

	}

	/**
	 * 소프트 키보드 내리기.
	 * @param context {@link Context}
	 * @param editText 소프트 키보드를 내리고자 하는 {@link EditText}
	 */
	public static void hideKeyboard(Context context, EditText editText){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 소프트 키보드 올리기.
	 * @param context {@link Context}
	 * @param editText 소프트 키보드를 올리고자 하는 {@link EditText}
	 */
	public static void showKeyboard(Context context, EditText editText){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, 0);
	}

	/**
	 * 미디어 볼륨 조절.
	 * @param context {@link Context}
	 * @param volume
	 */
	public static void setMediaVolume(Context context, int volume){

		int setVolume = volume;
		if(setVolume < 0){
			setVolume = 0;
		}

		AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

		int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if(setVolume > maxVolume){
			setVolume = maxVolume;
		}

		am.setStreamVolume(AudioManager.STREAM_MUSIC, setVolume, AudioManager.FLAG_PLAY_SOUND);

	}

	/**
	 * 01012345678 형식의 디바이스 전화번호 반환.
     * isUseDash가 true인 경우 010-1234-5678 형식으로 반환.
	 * {@link Manifest.permission#READ_PHONE_STATE} 권한 필요.
	 * @param context {@link Context}
     * @param isUseDash 대시를 넣어서 반환받을지 여부
	 * @return 디바이스 전화번호 또는 디바이스에 전화번호가 없을 경우 빈 값
	 */
	@RequiresPermission(anyOf = {
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.READ_SMS,
			Manifest.permission.READ_PHONE_NUMBERS
	})
	public static String getPhoneNumber(Context context, boolean isUseDash){

		String phoneNumber = null;

		TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		try {

			String tmpPhoneNumber = mgr.getLine1Number();
			phoneNumber = tmpPhoneNumber.replace("+82", "0");
			if(isUseDash) {
                phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber);
            }

		} catch (Exception e) {
			phoneNumber = null;
		}

		return phoneNumber;

	}

    /**
     * 전화 상태 반환.
     * @param context {@link Context}
     * @return {@link TelephonyManager#CALL_STATE_IDLE},
     *          {@link TelephonyManager#CALL_STATE_RINGING},
     *          {@link TelephonyManager#CALL_STATE_OFFHOOK}
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
	public static int getPhoneState(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getCallState();
    }

    /**
     * 네트워크 상태 반환.
     * @param context {@link Context}
     * @return {@link TelephonyManager#DATA_DISCONNECTED},
     *          {@link TelephonyManager#DATA_CONNECTING},
     *          {@link TelephonyManager#DATA_CONNECTED},
     *          {@link TelephonyManager#DATA_SUSPENDED}
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static int getPhoneNetworkState(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDataState();
    }

    /**
     * 네트워크 타입 반환.
     * @param context {@link Context}
     * @return {@link TelephonyManager#NETWORK_TYPE_UNKNOWN},
     *          {@link TelephonyManager#NETWORK_TYPE_GPRS},
     *          {@link TelephonyManager#NETWORK_TYPE_EDGE},
     *          {@link TelephonyManager#NETWORK_TYPE_UMTS},
     *          {@link TelephonyManager#NETWORK_TYPE_HSDPA},
     *          {@link TelephonyManager#NETWORK_TYPE_HSUPA},
     *          {@link TelephonyManager#NETWORK_TYPE_HSPA},
     *          {@link TelephonyManager#NETWORK_TYPE_CDMA},
     *          {@link TelephonyManager#NETWORK_TYPE_EVDO_0},
     *          {@link TelephonyManager#NETWORK_TYPE_EVDO_A},
     *          {@link TelephonyManager#NETWORK_TYPE_EVDO_B},
     *          {@link TelephonyManager#NETWORK_TYPE_1xRTT},
     *          {@link TelephonyManager#NETWORK_TYPE_IDEN},
     *          {@link TelephonyManager#NETWORK_TYPE_LTE},
     *          {@link TelephonyManager#NETWORK_TYPE_EHRPD},
     *          {@link TelephonyManager#NETWORK_TYPE_HSPAP}
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static int getPhoneNetworkType(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDataNetworkType();
    }

	/**
	 * 권한 허용 여부 반환.
	 * @param context {@link Context}
	 * @param permissions 허용 여부를 확인 할 권한들.
	 *                    {@link Manifest.permission}
	 * @return true : 모든 권한 허용 됨, false : 확인 하는 권한들 중 하나라도 허용 되어 있지 않을 경우
	 */
	public static boolean isGrantedPermission(Context context, String[] permissions) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			for (String permission : permissions) {

				if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
					return false;
				}

			}

		}

		return true;

	}

	/**
	 * 배터리 잔량 퍼센티지로 반환.
	 * @param context {@link Context}
	 * @return 배터리 잔량(0 ~ 100)
	 */
	public static int getBatteryRemain(Context context) {

		Intent intentBattery = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int level = intentBattery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = intentBattery.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		float batteryPct = level / (float)scale;

		return (int)(batteryPct * 100);

	}

	/**
	 * 배터리 충전 중 여부 반환.
	 * @param context {@link Context}
	 * @return true : 충전 중, false : 충전 중 아님
	 */
	public static boolean isBatteryCharging(Context context){

		Intent intentBattery = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int status = intentBattery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

		boolean isCharging = false;
		if(status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL){
			isCharging = true;
		}else if(status == BatteryManager.BATTERY_STATUS_NOT_CHARGING || status == BatteryManager.BATTERY_STATUS_DISCHARGING){
			isCharging = false;
		}

		return isCharging;

	}

	/**
	 * 테스트 필요.
	 * 배터리 충전 중인 경우 충전 타입 반환.
	 * @param context {@link Context}
	 * @return -1 : 충전 중 아님,
	 *         {@link BatteryManager#BATTERY_PLUGGED_USB} : USB 충전,
	 *         {@link BatteryManager#BATTERY_PLUGGED_AC} : 어뎁터 충전,
	 *         {@link BatteryManager#BATTERY_PLUGGED_WIRELESS} : 무선 충전
	 */
	public static int getBatteryChargingType(Context context){

		Intent intentBattery = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int chargePlug = intentBattery.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		int status = intentBattery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

		if(status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL){
			return -1;
		}

//        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
//        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
//        boolean wirelessCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;

		return chargePlug;

	}

	/**
	 * 숫자형 문자열을 원하는 타입으로 형변환하여 {@link Object}로 반환.
	 * @param parseType 원하는 형변환 타입 : short, integer, long, float, double
	 * @param value 형변환 할 문자열
	 * @return Object : 형변환 된 객체, null : 형변환 실패
	 */
	public static Object castStrToNumber(String parseType, String value){

		if(parseType == null || parseType.length() < 4){
			return  null;
		}

		try{

			if("SHORT".equalsIgnoreCase(parseType)){
				return Short.parseShort(value);
			}else if("INTEGER".equalsIgnoreCase(parseType)){
				return Integer.parseInt(value);
			}else if("LONG".equalsIgnoreCase(parseType)){
				return Long.parseLong(value);
			}else if("FLOAT".equalsIgnoreCase(parseType)){
				return Float.parseFloat(value);
			}else if("DOUBLE".equalsIgnoreCase(parseType)){
				return Double.parseDouble(value);
			}

			return null;

		}catch (NumberFormatException e){
			return null;
		}

	}

	/**
	 * 디바이스 고유 값 반환.
	 *
	 * 해외에서 알려져 있는 방법.
	 * 고유 값으로 사용하기에 디바이스가 최초 Boot 될때 생성 되는 64-bit 값인 ANDROID_ID값을 이용하는게 가장 명확하나
	 * 안드로이드 버전 Proyo(2.2) 이전 에는 100% 디바이스 고유 번호를 획득 한다고는 보장 할 수 없고,
	 * 몇몇 Vendor 에서 출하된 디바이스에서는 동일한 고유 번호(9774d56d682e549c)가 획득되는 현상이 있음.
	 * 그래서 초기 ANDROID_ID 값이 버그 값일 경우 TelephonyManager를 이용한 DeviceId 값을 고유값으로 하여 반환하며
	 * 이 후 호출시 {@link SharedPreferences}를 사용하여 반환 되는 고유 값이 변경되지 않도록 함.
	 * @param context {@link Context}
	 * @param isUseHyphen true : 고유 값에 하이픈(-)을 포함하여 반환, false : 고유 값에 하이픈(-)을 제거하여 반환
	 * @return 디바이스 고유 값, null : 권한 문제 발생
	 */
	@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
	public static synchronized String getDeviceUUID(Context context, boolean isUseHyphen) {

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
				return null;
			}

		}

		final SharedPreferences pref = context.getSharedPreferences("UNIQUE_DEVICE_ID_PREF.xml", Context.MODE_PRIVATE);
		final String id = pref.getString("UNIQUE_DEVICE_ID", null);

		UUID uuid = null;
		if (id != null) {
			uuid = UUID.fromString(id);
		} else {

			final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
			try {

				if ("9774d56d682e549c".equals(androidId)) { // 버그 값일 경우
					final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
					uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
				} else { // 버그 값이 아닐 경우
					uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
				}

			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}

			pref.edit().putString("UNIQUE_DEVICE_ID", uuid.toString()).commit();

		}

		return isUseHyphen ? uuid.toString() : uuid.toString().replaceAll("-", "");

	}

	/**
	 * 현재 시간을 포맷에 맞게 반환.
	 *
	 * 포맷 문자열
	 * 문자열     설명                                표시                 예시
	 * =============================================================================================
	 * G        | 기원                              | Text               | AD
	 * ---------------------------------------------------------------------------------------------
	 * y        | 년                                | 년                 | 1996; 96
	 * ---------------------------------------------------------------------------------------------
	 * M        | 월                                | 월                 | July; Jul; 07
	 * ---------------------------------------------------------------------------------------------
	 * w        | 해에 있어서의 주                  | 수치               | 27
	 * ---------------------------------------------------------------------------------------------
	 * W        | 달에 있어서의 주                  | 수치               | 2
	 * ---------------------------------------------------------------------------------------------
	 * D        | 해에 있어서의 날                  | 수치               | 189
	 * ---------------------------------------------------------------------------------------------
	 * d        | 달에 있어서의 날                  | 수치               | 10
	 * ---------------------------------------------------------------------------------------------
	 * F        | 달에 있어서의 요일                | 수치               | 2
	 * ---------------------------------------------------------------------------------------------
	 * E        | 요일                              | Text               | Tuesday; Tue
	 * ---------------------------------------------------------------------------------------------
	 * a        | 오전/오후                         | Text               | PM
	 * ---------------------------------------------------------------------------------------------
	 * H        | 하루에 있어서의 때 (0 ~ 23)       | 수치               | 0
	 * ---------------------------------------------------------------------------------------------
	 * k        | 하루에 있어서의 때 (1 ~ 24)       | 수치               | 24
	 * ---------------------------------------------------------------------------------------------
	 * K        | 오전/오후때 (0 ~ 11)              | 수치               | 0
	 * ---------------------------------------------------------------------------------------------
	 * h        | 오전/오후때 (1 ~ 12)              | 수치               | 12
	 * ---------------------------------------------------------------------------------------------
	 * m        | 분                                | 수치               | 30
	 * ---------------------------------------------------------------------------------------------
	 * s        | 초                                | 수치               | 55
	 * ---------------------------------------------------------------------------------------------
	 * S        | 밀리 세컨드                       | 수치               | 978
	 * ---------------------------------------------------------------------------------------------
	 * z        | 타임 존                           | 일반적인 타임 존   | Pacific Standard Time; PST; GMT-08:00
	 * ---------------------------------------------------------------------------------------------
	 * Z        | 타임 존                           | RFC 822 타임 존    | -0800
	 * ---------------------------------------------------------------------------------------------
	 * @param format 반환 받을 포맷
	 * @return 현재 시간
	 */
	public static String getCurrentTime(String format){
		return new SimpleDateFormat(format).format(System.currentTimeMillis());
	}

	/**
	 * 앱 실행 중 여부 반환.
	 * 테스트 필요.
	 * @param context {@link Context}
	 * @param packageName 확인 할 앱 패키지명
	 * @return true : 실행 중, false : 실행 중 아님
	 */
	public static boolean isRunningApp(Context context, String packageName){

		boolean isRunningApp = false;

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcessInfo : activityManager.getRunningAppProcesses()) {
			Log.i(LOG_TAG, "check is running app find app process name : " + appProcessInfo.processName);

			if(packageName.equals(appProcessInfo.processName)){
				isRunningApp = true;
				break;
			}

		}

		return isRunningApp;

	}

	/**
	 * 서비스 실행 중 여부 반환.
	 * @param context {@link Context}
	 * @param packageName 확인 할 서비스 패키지명
	 * @param serviceClassName 확인 할 서비스 클래스명
	 * @return true : 서비스 실행 중, false : 서비스 실행 중 아님
	 */
	public static boolean isRunningService(Context context, String packageName, String serviceClassName){

		boolean isRunningService = false;

		String target = packageName + "." + serviceClassName;

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {

			if (target.equals(serviceInfo.service.getClassName())) {
				isRunningService = true;
				break;
			}

		}

		return isRunningService;

	}

	/**
	 * captureView로 넘긴 뷰를 캡쳐.
	 * @param captureView 캡쳐 할 뷰
	 * @param savePath 캡쳐 파일 저장 경로
	 * @param saveName 캡쳐 파일명(확장자 미포함)
	 * @param saveFormat 캡쳐 파일 포맷
	 * @return true : 캡쳐 성공, false : 캡쳐 실패(저장 경로 존재하지 않을 경우, 기타 오류)
	 */
	public static boolean screenCapture(View captureView, String savePath, String saveName, Bitmap.CompressFormat saveFormat){

		boolean isOk = false;

		boolean originIsDrawingCache = captureView.isDrawingCacheEnabled();

		if(!originIsDrawingCache) {
			captureView.setDrawingCacheEnabled(true);
		}
		Bitmap drawingCache = captureView.getDrawingCache();

		File file = new File(savePath, saveName + "." + saveFormat.name().toLowerCase());
		FileOutputStream fos = null;
		try{

			fos = new FileOutputStream(file);
			drawingCache.compress(saveFormat, 100, fos);

			isOk = true;

		}catch (IOException e){
			isOk = false;
		}finally {

			if(fos != null){

				try {

					fos.close();

				} catch (IOException e) {
					// not catch
				}

			}

		}

		captureView.setDrawingCacheEnabled(originIsDrawingCache);

		return isOk;

	}

	/**
	 * APK 설치 화면 보여주기.
	 * support-v4 필요.
	 *
	 * AndroidManifest.xml에 아래 정의 필요.
	 * <provider
	 *     android:name="android.support.v4.content.FileProvider"
	 *     android:authorities="${applicationId}.provider"
	 *     android:exported="false"
	 *     android:grantUriPermissions="true">
	 * <meta-data
	 *     android:name="android.support.FILE_PROVIDER_PATHS"
	 *     android:resource="@xml/file_paths"/>
	 * </provider>
	 *
	 * res - xml 폴더에 file_paths.xml 파일 생성 및 아래 정의 필요.
	 * xml 파일명 변경시 AndroidManifest.xml - provider - meta-data - android:resource도 동일하게 맞춰야 함.
	 * <?xml version="1.0" encoding="utf-8"?>
	 * <paths>
	 *     <external-path
	 *         name="storage"
	 *         path="."/>
	 * </paths>
	 * @param context {@link Context}
	 * @param apkPath apk 파일명과 확장자를 포함한 경로
	 * @exception FileNotFoundException apkPath로 받은 apk가 파일이 없는 경우 발생
	 */
	public static void showApkInstaller(Context context, String apkPath) throws FileNotFoundException{

		File f = new File(apkPath);
		if(!f.exists()){
			throw new FileNotFoundException("Can not find apk file.");
		}

		Intent i = new Intent(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // 추가
			i.setData(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", f)); // 추가
			context.startActivity(i);

		} else {

			i.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);

		}

	}

	/**
	 * Activity 떠있는지 여부 반환.
	 * 테스트 필요.
	 *
	 * @param context
	 * @param cls 확인 할 액티비티 클래스
	 * @return true : 떠 있음, false : 안 떠있음
	 */
	public static boolean isForegroundActivity(Context context, Class<?> cls) {

		if (cls == null) {
			return false;
		}

		ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
		ActivityManager.RunningTaskInfo running = info.get(0);
		ComponentName componentName = running.topActivity;

		return cls.getName().equals(componentName.getClassName());

	}

	/**
	 * Activity 떠있는지 여부 반환.
	 * 테스트 필요.
	 *
	 * @param context
	 * @return
	 */
	public static boolean isActivityTop(Context context) {

		ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(9999);

		if (info.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 앱 설치, 삭제, 업데이트 되었을 때 이벤트 받기.
	 * 테스트 필요.
	 * 참고 : http://ccdev.tistory.com/29
	 *
	 * AndroidManifest.xml에 아래 정의 필요.
	 * <receiver android:name=".PackageReceiver">
	 *     <intent-filter>
	 *         <action android:name="android.intent.action.PACKAGE_ADDED"/>
	 *         <action android:name="android.intent.action.PACKAGE_REMOVED"/>
	 *         <action android:name="android.intent.action.PACKAGE_REPLACED"/>
	 *         <data android:scheme="package"/>
	 *     </intent-filter>
	 * </receiver>
	 */
	public static void appModifyBroadcastReceiver(Context context){

		// 이벤트 발생시 이벤트를 받을 브로드캐스트리시버 정의
		BroadcastReceiver receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				String action = intent.getAction();
				String packageName = intent.getData().getSchemeSpecificPart();

				if(action.equals(Intent.ACTION_PACKAGE_ADDED)){ // // 앱이 설치되었을 때

				} else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)){ // 앱이 삭제되었을 때

				} else if(action.equals(Intent.ACTION_PACKAGE_REPLACED)){ // 앱이 업데이트 되었을 때

				}

			}

		};

		// 브로드캐스트리시버 등록.
		// filter에 사용 될 수 있는 액션이 추가적으로 더 있음.
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_ADDED);
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		filter.addDataScheme("package");
		context.registerReceiver(receiver, filter);

	}

	/**
	 * 숫자를 포맷형태의 문자열로 변환하여 반환.
	 * @param number 변환 할 실수
	 * @param format 변환 할 문자열 포맷
	 * @return 변환 된 문자열
	 */
	public static String transNumberToString(double number, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(number);
	}

	/**
	 * 배열을 ArrayList로 변환하여 반환.
	 * 자꾸 까먹어서 적어둠.
	 * @param array 배열
	 * @return ArrayList
	 */
	private ArrayList<String> transArrayToArrayList(String[] array){

		// 방법 1
		ArrayList<String> arrayList1 = new ArrayList<String>();
		for(String data : array){
			arrayList1.add(data);
		}

		// 방법 2
		ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(array));

		return arrayList1;

	}

	/**
	 * ArrayList를 배열로 변환하여 반환.
	 * 자꾸 까먹어서 적어둠.
	 * @param arrayList ArrayList
	 * @return 배열
	 */
	private String[] transArrayListToArray(ArrayList<String> arrayList){

		if(arrayList == null){
			return null;
		}

		// 방법 1
		String[] array1 = new String[arrayList.size()];
		int size = 0;
		for (String data : arrayList) {
			array1[size++] = data;
		}

		// 방법 2
		String[] array2 = arrayList.toArray(new String[arrayList.size()]);

		return array1;

	}

	/**
	 * float의 소수점을 원하는 자리까지 잘라 반환.
	 * @param num 잘라낼 float 숫자
	 * @param splitDecimalPosition 잘라낼 자리 수
	 * @return 원하는 자리까지 잘라낸 float 숫자 반환.
	 */
	public static float cutFloatDecimal(float num, int splitDecimalPosition){

		double n2 = Math.pow(10.0, splitDecimalPosition);

		return (float) ((int)(num * n2) / n2);

	}

	/**
	 * double의 소수점을 원하는 자리까지 잘라 반환.
	 * @param num 잘라낼 double 숫자
	 * @param splitDecimalPosition 잘라낼 자리 수
	 * @return 원하는 자리까지 잘라낸 double 숫자 반환.
	 */
	public static double cutDoubleDecimal(double num, int splitDecimalPosition){

		double n2 = Math.pow(10.0, splitDecimalPosition);

		return (double) ((int)(num * n2) / n2);

	}

	/**
	 * 배열 크기 늘린 후 반환.
	 * @param array 크기를 늘릴 배열
	 * @param newLength 늘릴 배열의 최종적인 크기
	 * @return 크기를 늘린 배열
	 */
	public static <T> T[] addArrayLength(T[] array, int newLength){
		
		if(array == null){
			return null;
		}
		
		if(array.length > newLength){
			Log.w(LOG_TAG, "Can not add array length. Cause new length is less than array length.");
			return array;
		}

		if(array.length == newLength){
			return array;
		}
		
		return (T[]) Arrays.copyOf(array, newLength, array.getClass());

	}

    /**
     * 양수를 음수로, 음수를 양수로 변환하여 반환.
     * 0인 경우 0을 반환.
     * @param number 변환 할 숫자
     * @return 변환 된 숫자
     */
    public static Object transNumberToReverse(Object number) {

        if (number instanceof Byte) {
            return -(byte) number;
        } else if (number instanceof Short) {
            return -(short) number;
        } else if (number instanceof Integer) {
            return -(int) number;
        } else if (number instanceof Long) {
            return -(long) number;
        } else if (number instanceof Float) {
            return -(float) number;
        } else if (number instanceof Double) {
            return -(double) number;
        }

        return number;

    }

    /**
     * 반전 색상 반환.
     * @param color 반전 할 색상
     * @return 반전 색상
     */
    public static int transColorToInvertColor(int color){
        return Color.
				rgb((255 - Color.red(color)), (255 - Color.green(color)), (255 - Color.blue(color)));
    }

	/**
	 *
	 * ex) rgba : 0,51,102,255, hex : #003366ff
	 * @param red 0 ~ 255
	 * @param green 0 ~ 255
	 * @param blue 0 ~ 255
	 * @param alpha 0 ~ 255
	 * @return #00000000 ~ #ffffffff
	 */
    public static String transRGBAToHexString(int red, int green, int blue, int alpha){
		return "#" + Integer.toHexString((red & 255) << 24 | (green & 255) << 16 | (blue & 255) << 8 | (alpha & 255) << 0);
	}

	/**
	 *
	 * ex) rgba : 0,51,102,255, hex : #ff003366
	 * @param red 0 ~ 255
	 * @param green 0 ~ 255
	 * @param blue 0 ~ 255
	 * @param alpha 0 ~ 255
	 * @return #00000000 ~ #ffffffff
	 */
	public static String transRGBAToARGBHexString(int red, int green, int blue, int alpha){
		return "#" + Integer.toHexString((alpha & 255) << 24 | (red & 255) << 16 | (green & 255) << 8 | (blue & 255) << 0);
	}

	/**
	 * 앱 삭제 요청.
	 * @param context {@link Context}
	 * @param packageName 삭제 요청 할 앱 패키지명
	 */
	public static void requestApplicationDelete(Context context, String packageName){

		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		context.startActivity(uninstallIntent);

	}

	/**
	 * 텍스트 파일 생성.
	 * @param filePath 생성 파일 경로
	 * @param fileName 생성 파일 명
	 * @param encoding 생성 할 인코딩
	 * @param text 문자열
	 * @param isOverWrite true : 파일이 존재할 경우 새 문자열로 내용 변경, false : 파일이 존재할 경우 새 문자열 내용 추가
	 * @return true : 파일 생성 성공, false : 파일 생성 실패
	 */
	public static boolean writeTextFile(String filePath, String fileName, String encoding, String text, boolean isOverWrite) throws IOException{

		if(filePath == null || filePath.length() <= 0 ||
				fileName == null || fileName.length() <= 0){
			return false;
		}

		File textFileFolder = new File(filePath);
		if(!textFileFolder.exists()){
			textFileFolder.mkdirs();
		}

		File textFile = new File(filePath, fileName);

		BufferedWriter bw = null;
		try {

			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(textFile, !isOverWrite), Charset.forName(encoding)));
			bw.write(text);
			bw.flush();

		} catch (Exception e){
			throw new IOException(e);
		} finally {

			try{

				bw.close();

			}catch (Exception e){
				// ignore
			}

		}

		return true;

	}

	/**
	 * 텍스트 파일 읽어서 반환.
	 * @param filePath 읽을 파일 경로
	 * @param fileName 읽을 파일 명
	 * @param encoding 읽어 드릴 인코딩
	 * @return null : 경로 없음, 파일 없음, 오류 발생, 문자열 : 정상 반환
	 */
	public static String readTextFile(String filePath, String fileName, String encoding){

		if(filePath == null || filePath.length() <= 0 ||
				fileName == null || fileName.length() <= 0){
			return null;
		}

		File textFile = new File(filePath, fileName);
		if(!textFile.exists()){
			return null;
		}

		StringBuilder result = new StringBuilder();
		BufferedReader br = null;
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), Charset.forName(encoding)));
			String readLine = "";
			while (((readLine = br.readLine()) != null)) {
				result.append(readLine + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

			try {

				br.close();

			} catch (Exception e) {
				// ignore
			}

		}

		return result.toString();

	}

	/**
	 * 참고 URL : https://mantdu.tistory.com/797
	 * 코드 잊어 버릴까봐 적어놓음.
	 *
	 * 테스트 필요.
	 *
	 * {@link UsbManager}에서 숨겨놓은 상수 값을 사용해
	 * USB 연결 상태 확인 할 수 있는 BroadcastReceiver 등록.
	 */
	public static BroadcastReceiver registerUsbStateCheckBroadcastReceiver() {

		final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";

		final String USB_CONNECTED = "connected"; // USB 연결 여부
		final String USB_HOST_CONNECTED = "host_connected"; // USB가 호스트로 연결 여부.
		final String USB_CONFIGURED = "configured"; // USB의 구성 여부. 0 : 구성 되지 않음, 1 : 구성 됨
		final String USB_FUNCTION_ADB = "adb"; // adb 함수(또는 기능) 사용 가능 여부
		final String USB_FUNCTION_RNDIS = "rndis"; // RNDIS 이더넷 기능 사용 가능 여부
		final String USB_FUNCTION_MTP = "mtp"; // MTP 기능 사용 가능 여부
		final String USB_FUNCTION_PTP = "ptp"; // PTP 기능 사용 가능 여부
		final String USB_FUNCTION_ACCESSORY = "accessory"; // accessory 함수(또는 기능) 사용 가능 여부
		final String USB_FUNCTION_AUDIO_SOURCE = "audio_source"; // 오디오 소스 함수(또는 기능) 사용 가능 여부
		final String USB_FUNCTION_MIDI = "midi"; // MIDI 기능 사용 가능 여부

		// USB가 구성이 되지 않았으면, MTP 기능은 활성화되고 그 외 기능들은 비활성화 됨

		return new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				String action = intent.getAction();

				if (action.equals(ACTION_USB_STATE)) {

					Bundle extras = intent.getExtras();
					boolean usbConnected = extras.getBoolean(USB_CONNECTED);
					String usbState = (usbConnected ? "connected" : "disconnected");

					Toast.makeText(context, "USB state : " + usbState, Toast.LENGTH_SHORT).show();

				}

			}

		};

	}
	
	/**
     * 네트워크 URL을 문자열 배열로 만들어 반환.
     * 
     * 예시 URL
     * https://www.redmine.org/projects/redmine/boards/1
     * https://10.1.73.10:8443/redmine/issues/13747/time_entries
     * https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=101&oid=005&aid=0001430382
     * @param url 네트워크 URL
     * @return
     * String[0] = 프로토콜
     * String[1] = IP 또는 도메인 주소
     * String[2] = PORT 또는 도메인 주소인 경우 -1
     * String[3] = 컨텍스트
     */
    public static String[] urlToArray(String url){

        if(url == null){
            return null;
        }

        URI uri = URI.create(url);

        String[] result = new String[4];
        result[0] = uri.getScheme();
        result[1] = uri.getHost();
        result[2] = String.valueOf(uri.getPort());
        String uriPath = uri.getPath();
        if(uriPath == null || uriPath.length() <= 1){
            result[3] = null;
        }else{
            String noneFirstSlashPath = uriPath.substring(1);
            result[3] = noneFirstSlashPath.substring(0, (noneFirstSlashPath.indexOf("/") == -1 ? noneFirstSlashPath.length() : noneFirstSlashPath.indexOf("/")));
        }

        return result;

    }

}