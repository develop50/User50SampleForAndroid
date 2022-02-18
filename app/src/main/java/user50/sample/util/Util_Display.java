package user50.sample.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by geomex on 2017-06-21.
 */
public class Util_Display {

    //////////
    // 단위 //
    //////////
    /**
     * Density Independent Pixel.
     * 실제 픽셀에 독립적인 단위.
     * 안드로이드 폰의 다양한 해상도를 지원하기 위해 만든 단위.
     * 즉, 화면이 작은 폰에서 10원짜리 만하게 나타난다면 화면이 큰 폰에서도 10원짜리 만하게 나타나도록 만듬.
     *
     * dp = px / (160 / dpi)
     * dp = px / density
     *
     * density = dpi/160
     */
    public static final int DP = 0;
    /**
     * {@link #DP}와 동일.
     */
    public static final int DiP = DP;

    /**
     * Dot Per Inch.
     * 1인치당 픽셀 수.
     * 물리적 단위인 1인치(2.54cm)에 몇 픽셀이 들어가는가를 나타내는 단위.
     */
    public static final int DPI = 1;

    /**
     * Pixel.
     * 스크린의 실제 픽셀 단위.
     * 실제 디바이스 크기나 밀도와 상관 없음.
     * mdpi(160dpi)에서 1dp = 1px.
     *
     * ldpi : 1dp = 0.75px
     * mdpi : 1dp = 1px
     * hdpi : 1dp = 1.5px
     * xdpi : 1dp = 2px
     *
     * px = dp * (160 / dpi) = dp * density
     */
    public static final int PX = 2;

    /////////
    // dpi //
    /////////
    public static final int L_DPI = 120;
    public static final int M_DPI = 160;
    public static final int H_DPI = 240;
    public static final int XH_DPI = 320;
    public static final int XXH_DPI = 480;
    public static final int XXXH_DPI = 640;

    /**
     * 디바이스 가로, 세로 해상도 크기 반환.
     * @param context Context
     * @return int[0] : 가로 픽셀 수
     *         int[1] : 세로 픽셀 수
     */
    public static int[] getDeviceResolutionSize(Context context){

        WindowManager window_manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        Display dis = window_manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        dis.getMetrics(metrics);

        return new int[]{metrics.widthPixels, metrics.heightPixels};

    }

    /**
     * 디바이스 가로, 세로 cm 크기 반환.
     * @param context {@link Context}
     * @return float[0] : 가로 cm 크기
     *         float[1] : 세로 cm 크기
     */
    public static float[] getDeviceSizeToCm(Context context){

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);

        float widthPixelPerCm = metrics.xdpi * 0.3937007874015748f; // 0.3937007874015748 값은 1cm당 인치 값
        float heightPixelPerCm = metrics.ydpi * 0.3937007874015748f; // 0.3937007874015748 값은 1cm당 인치 값

        double n2 = Math.pow(10.0, 2);

        return new float[]{(float) ((int)(metrics.widthPixels / widthPixelPerCm * n2) / n2), (float) ((int)(metrics.heightPixels / heightPixelPerCm * n2) / n2)};

    }

    /**
     * 디바이스 대각선 inch 크기 반환.
     * 나온 값을 소수점 1자리까지 반올림해서 사용해야 함.
     * @param context {@link Context}
     * @return 디바이스 대각선 inch 크기
     */
    public static float getDeviceSizeToInch(Context context){

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);

        return (float)Math.sqrt(x + y);

    }

    /**
     * Return status bar height
     * @param context Context
     * @return Status bar height
     */
    public static int getStatusbarHeight0(Context context){

        WindowManager window_manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        Display dis = window_manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        dis.getMetrics(metrics);

        int statusbar_height = -1;

        switch (metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                statusbar_height = 19;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                statusbar_height = 25;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                statusbar_height = 38;
                break;
            default:
                statusbar_height = metrics.densityDpi;
                break;
        }

        return statusbar_height;

    }

    /**
     * Return status bar height
     * @param context Context
     * @return Status bar height
     * @deprecated this is old code
     */
    @Deprecated
    public static int getStatudbarHeight1(Context context){

        Rect rectangle = new Rect();
        Window window = ((Activity)context).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;

        return statusBarHeight;

    }

    /**
     * Return status bar height
     * @param context Context
     * @return Status bar height
     */
    public static int getStatudbarHeight2(Context context){

        Rect rectangle = new Rect();
        Window window = ((Activity)context).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;

        return statusBarHeight;

    }

    /**
     * Return action bar height
     * @param context Context
     * @return
     */
    public static int getActionbarHeight(Context context){

        Rect rectangle = new Rect();
        Window window = ((Activity)context).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();

        return contentViewTop - getStatudbarHeight2(context);

    }

    /**
     * dp 값을 pixel 값으로 변환하여 반환
     * @param context {@link Context}
     * @param dp 변환 할 dp 값
     * @return 변환 된 pixel 값
     */
    public static float transDpToPixel(Context context, float dp){

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);

        // 방법 1
        return dp * metrics.density;

        // 방법 2
//        return dp * metrics.densityDpi / 160;

    }

    /**
     * Pixel 값을 dp 값으로 변환하여 반환.
     * @param context {@link Context}
     * @param pixel 변환 할 pixel 값
     * @return 변환 된 dp 값
     */
    public static float transPixelToDp(Context context, float pixel){

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);

        // 방법 1
        return pixel / metrics.density;

        // 방법 2
//        return pixel * 160 / metrics.densityDpi;

    }

    /**
     * 1cm에 해당하는 가로, 세로 pixel 반환.
     * @param context {@link Context}
     * @return float[0] : 1cm에 해당하는 가로 pixel
     *         float[1] : 1cm에 해당하는 세로 pixel
     */
    public static float[] getPixelPerCm(Context context){

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);

        return new float[]{metrics.xdpi * 0.3937007874015748f, metrics.ydpi * 0.3937007874015748f};

    }

    /**
     *
     * @param context
     * @return
     */
    public static float[] getPixelPerInch(Context context){

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);

        return new float[]{metrics.xdpi, metrics.ydpi};

    }

    /**
     * 물리적인 디바이스 화면에 대한 정보를 담은 {@link DisplayMetrics} 반환.
     * @param context {@link Context}
     * @return 물리적인 디바이스 화면에 대한 정보를 담은 {@link DisplayMetrics}
     */
    public static DisplayMetrics getRealScreenInfo(Context context){

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getRealMetrics(metrics);

        return metrics;

    }

    /**
     * 실제 표출되는 화면에 대한 정보를 담은 {@link DisplayMetrics} 반환.
     * @param context {@link Context}
     * @return 실제 표출되는 화면에 대한 정보를 담은 {@link DisplayMetrics}
     */
    public static DisplayMetrics getScreenInfo(Context context){

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);

        return metrics;

    }

    /**
     *
     * @return
     */
    public static boolean hasNavigationBar(Context applicationContext) {
        //메뉴버튼 유무
        boolean hasMenuKey = ViewConfiguration.get(applicationContext).hasPermanentMenuKey();

        //뒤로가기 버튼 유무
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            return true;
        } else {
            return false;
        }
    }

    public static int getNavigationBarHeight(Context context) {

        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int deviceHeight = 0;

        if (resourceId > 0) {
            deviceHeight = resources.getDimensionPixelSize(resourceId);
        }

        return deviceHeight;

    }

}
