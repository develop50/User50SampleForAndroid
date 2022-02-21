package user50.sample.function.init;

import android.graphics.Color;
import android.os.AsyncTask;

import java.io.InputStream;

/**
 *
 */
public class AsyncTask_Init extends AsyncTask<Void, Integer, Integer> {

    //////////////
    // Constant //
    //////////////
    /** 로그 태그 */
    private final String LOG_TAG = AsyncTask_Init.class.getSimpleName();

    private static final int INIT_DEVICE_INFO = 1;
    private static final int INIT_DEVICE_INFO_CHECK_DEVICE_TYPE = 2;
    private static final int INIT_DEVICE_INFO_ALERT_DEVICE_TYPE = 3;
    private static final int INIT_SUCCESS = 98;
    private static final int INIT_ERROR = 99;

    private static final int DEVICE_TYPE_UNKNOWN = -1;
    private static final int DEVICE_TYPE_SOMETHING = 0;
    private static final int DEVICE_TYPE_SMART_PHONE = 1;
    private static final int DEVICE_TYPE_TABLET = 2;

    ////////////
    // Layout //
    ////////////

    //////////////
    // Variable //
    //////////////

    private Activity_Init mActivity;
    /**
     * 오류 메시지.
     */
    private String mErrorMessage;

    /**
     * 디바이스가 태블릿인지 여부.
     */
    private int mDeviceType;

    /**
     * 생성자.
     */
    public AsyncTask_Init(Activity_Init activity){

        this.mActivity = activity;

        this.mErrorMessage = null;

        this.mDeviceType = DEVICE_TYPE_UNKNOWN;

    }

    ////////////////////
    // Extends Method //
    ////////////////////
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.mActivity.setDetailMessage("시스템 초기화 준비 중");

    }

    @Override
    protected Integer doInBackground(Void... voids) {

        initDeviceInfo();

        if(this.mErrorMessage == null) {
            return INIT_SUCCESS;
        }else{
            return INIT_ERROR;
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if(this.mErrorMessage != null){
            return;
        }

        final int initWork = values[0].intValue();
        switch (initWork){
            case INIT_DEVICE_INFO:

                this.mActivity.setDetailMessage("디바이스 정보 확인");

                break;
            case INIT_DEVICE_INFO_CHECK_DEVICE_TYPE:

                this.mActivity.setDetailMessage("디바이스 타입 확인 중");

                break;
            case INIT_DEVICE_INFO_ALERT_DEVICE_TYPE:

                String deviceTypeToString = null;
                if(this.mDeviceType == DEVICE_TYPE_UNKNOWN){
                    deviceTypeToString = "알 수 없음";
                }else if(this.mDeviceType == DEVICE_TYPE_SMART_PHONE){
                    deviceTypeToString = "스마트폰";
                }else if(this.mDeviceType == DEVICE_TYPE_TABLET){
                    deviceTypeToString = "태블릿";
                }else if(this.mDeviceType == DEVICE_TYPE_SOMETHING){
                    deviceTypeToString = "안드로이드 디바이스";
                }
                this.mActivity.setDetailMessage("디바이스 타입 : " + deviceTypeToString, Color.GREEN);

                break;
        }

    }

    @Override
    protected void onPostExecute(Integer value) {
        super.onPostExecute(value);

        if(value == INIT_SUCCESS){

            this.mActivity.setDetailMessage("시스템 초기화 완료");
            this.mActivity.setDetailMessage("다음 버튼을 눌러주시기 바랍니다.");
            this.mActivity.setNextViewEnabled();

        }else{
            this.mActivity.showErrorDialog(this.mErrorMessage);
        }

    }

    ///////////////////////
    // Implements Method //
    ///////////////////////

    ////////////////////
    // Private Method //
    ////////////////////
    private void initDeviceInfo(){

        publishProgress(INIT_DEVICE_INFO);

        publishProgress(INIT_DEVICE_INFO_CHECK_DEVICE_TYPE);

        InputStream is = null;
        try {

            is = Runtime.getRuntime().exec("getprop ro.build.characteristics").getInputStream();
            byte[] bts = new byte[32];
            is.read(bts);
            is.close();

            String readString = new String(bts);

            if(readString.toUpperCase().contains("PHONE")) {
                this.mDeviceType = DEVICE_TYPE_SMART_PHONE;
            }else if(readString.toUpperCase().contains("TABLET")){
                this.mDeviceType = DEVICE_TYPE_TABLET;
            }else{
                this.mDeviceType = DEVICE_TYPE_SOMETHING;
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.mErrorMessage = e.getMessage();
        } finally {

            try {

                is.close();

            }catch (Exception e){
                // ignore
            }

        }

        publishProgress(INIT_DEVICE_INFO_ALERT_DEVICE_TYPE);

    }

    //////////////////////
    // Protected Method //
    //////////////////////

    ///////////////////
    // Public Method //
    ///////////////////

}