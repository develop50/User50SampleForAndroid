package user50.sample.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.HashMap;

import user50.sample.util.Util_File;

public class Network_AsyncTask extends AsyncTask<Void, Void, Network_Result> {

    //////////////////
    // Static Final //
    //////////////////
    /** 로그 태그 */
    private static final String LOG_TAG = Network_AsyncTask.class.getSimpleName();

    public static final int CONNECT_TYPE_UNDEFINE = -1;
    public static final int CONNECT_TYPE_STANDARD = 0;
    public static final int CONNECT_TYPE_GET = 1;
    public static final int CONNECT_TYPE_POST = 2;
    public static final int CONNECT_TYPE_DOWNLOAD = 3;
    public static final int CONNECT_TYPE_UPLOAD = 4;
    public static final int CONNECT_TYPE_SAMPLE_5 = 5;

    ////////////
    // Static //
    ////////////

    //////////////
    // Variable //
    //////////////
    private Context mContext;
    private int mConnectType;
    private INetworkListener mListener;
    private String mUrl;
    private HashMap<String, String> mParameter;

    private Network_Method mMethod;
    private boolean mIsUsePostMethod;

    private ProgressDialog mProgressDialog;

    /**
     * 생성자.
     * @param context {@link Context}
     * @param connectType 연결 요청 타입
     * @param listener {@link INetworkListener}
     */
    public Network_AsyncTask(Context context, int connectType, INetworkListener listener) {
        this(context, connectType, listener, null);
    }

    /**
     * 생성자.
     * @param context {@link Context}
     * @param connectType 연결 요청 타입
     * @param url 연결 URL
     * @param listener {@link INetworkListener}
     */
    public Network_AsyncTask(Context context, int connectType, INetworkListener listener, String url) {
        this(context, connectType, listener, url, null);
    }

    /**
     * 생성자.
     * @param context {@link Context}
     * @param connectType 연결 요청 타입
     * @param url 연결 URL
     * @param listener {@link INetworkListener}
     */
    public Network_AsyncTask(Context context, int connectType, INetworkListener listener, String url, HashMap<String, String> parameter) {

        this.mContext = context;
        this.mConnectType = connectType;
        this.mListener = listener;
        this.mUrl = url;
        this.mParameter = parameter;
        this.mIsUsePostMethod = true;

        this.mProgressDialog = null;

    }

    ////////////////////
    // Extends Method //
    ////////////////////
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if(this.mProgressDialog != null){
            this.mProgressDialog.show();
        }

        mMethod = new Network_Method();
        mMethod.setShowLog(true);

    }

    @Override
    protected Network_Result doInBackground(Void... params) {

        Network_Result result = null;
        try {

            switch (this.mConnectType) {
                case CONNECT_TYPE_STANDARD: {

                    this.mMethod.setUrl(this.mUrl);
                    result = this.mMethod.doStandard(this.mParameter, this.mIsUsePostMethod);

                }
                    break;
                case CONNECT_TYPE_GET: {

                    String sampleParam1 = "test parameter 1";
                    String sampleParam2 = "테스트 파라미터 2";
                    String sampleParam3 = "Test 파라미터 3";

                    this.mMethod.setUrl(mUrl);
                    result = this.mMethod.doGet(sampleParam1, sampleParam2, sampleParam3);

                }
                    break;
                case CONNECT_TYPE_POST: {

                    String sampleParam1 = "test parameter 1";
                    String sampleParam2 = "테스트 파라미터 2";
                    String sampleParam3 = "Test 파라미터 3";

                    this.mMethod.setUrl(mUrl);
                    result = this.mMethod.doPost(sampleParam1, sampleParam2, sampleParam3);

                }
                break;
                case CONNECT_TYPE_DOWNLOAD: {

                    String downloadPath = null;
                    try {

                        downloadPath = Util_File.getStoragePath(mContext, false);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String downloadName = "free-hd-wallpaper-1920x1080.jpg";
                    String sampleParam1 = "test parameter 1";
                    String sampleParam2 = "테스트 파라미터 2";
                    String sampleParam3 = "Test 파라미터 3";

                    this.mMethod.setUrl(mUrl);
                    result = this.mMethod.doDownloadFile(downloadPath, downloadName, sampleParam1, sampleParam2, sampleParam3);

                }
                    break;
                case CONNECT_TYPE_UPLOAD: {

                    String uploadPath = null;
                    try {

                        uploadPath = Util_File.getStoragePath(mContext, false);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String uploadName = "free-hd-wallpaper-1920x1080.jpg";
                    String sampleParam1 = "test parameter 1";
                    String sampleParam2 = "테스트 파라미터 2";
                    String sampleParam3 = "Test 파라미터 3";

                    this.mMethod.setUrl(mUrl);
                    result = this.mMethod.doUploadFile(uploadPath, uploadName, sampleParam1, sampleParam2, sampleParam3);

                }
                break;
                case CONNECT_TYPE_SAMPLE_5: {

                    String sampleParam1 = "test parameter 1";
                    String sampleParam2 = "테스트 파라미터 2";
                    String sampleParam3 = "Test 파라미터 3";

                    this.mMethod.setUrl(this.mUrl);
                    result = this.mMethod.doSampleMethod(sampleParam1, sampleParam2, sampleParam3);

                }
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    protected void onPostExecute(Network_Result result) {
        super.onPostExecute(result);

        if(this.mProgressDialog != null && this.mProgressDialog.isShowing()){
            this.mProgressDialog.dismiss();
        }

        if(mListener != null){

            int resultCode;
            if (result.code == -1 || result.code == -2) {
                resultCode = INetworkListener.RESULT_APP_ERROR;
            } else if (result.code == HttpURLConnection.HTTP_OK) {
                resultCode = INetworkListener.RESULT_SUCCESS;
            } else {
                resultCode = INetworkListener.RESULT_SERVER_ERROR;
            }

            mListener.onNetworkResult(this.mConnectType, resultCode, result);

        }

    }

    ///////////////////////
    // Implements Method //
    ///////////////////////

    ////////////////////
    // Private Method //
    ////////////////////

    //////////////////////
    // Protected Method //
    //////////////////////

    ///////////////////
    // Public Method //
    ///////////////////
    /**
     * 네트워크 연결시 보일 프로그래스 다이얼로그 설정.
     * @param context {@link Context}
     * @param title 프로그래스 다이얼로그 제목
     * @param message 프로그래스 다이얼로그 내용
     */
    public void setShowProgressDialog(Context context, String title, String message){

        this.mProgressDialog = new ProgressDialog(context);
        this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.mProgressDialog.setTitle(title);
        this.mProgressDialog.setMessage(message);
        this.mProgressDialog.setCancelable(false);

    }

    public void setUrl(String url){
        this.mUrl = url;
    }

    /**
     * 네트워크 연결시 보낼 파라미터 추가.
     * 파라미터 키 값이 이미 있는 경우 추가 불가.
     * @param key 파라미터 키 값
     * @param value 파라미터 값
     */
    public void addParameter(String key, String value){

        if(this.mParameter.containsKey(key)){
            Log.w(LOG_TAG, "Can not add network parameter. Cause already set.");
            return;
        }

        this.mParameter.put(key, value);

    }

    public void setParameter(HashMap<String, String> parameter){
        this.mParameter = parameter;
    }

    public void setUsePostMethod(boolean isUsePostMethod){
        this.mIsUsePostMethod = isUsePostMethod;
    }

}