package user50.sample.network;

import android.util.Log;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Network_Method {

    /** 로그 태그 */
    private static final String LOG_TAG = Network_Method.class.getSimpleName();

    /** 네트워크 연결 매니저 */
    private Network_Connector mNetworkConnector;

    private boolean mIsShowLog;

    /**
     * 생성자.
     * @see {@link #setUrl(String)}
     */
    public Network_Method(){
        this(null);
    }
    /**
     * 생성자.
     * @param url 연결 URL
     */
    public Network_Method(String url){

        this.mNetworkConnector = new Network_Connector(url);

        this.mIsShowLog = false;

    }

    ////////////////////
    // Private Method //
    ////////////////////
    /**
     * 네트워크 연결.
     * @return 네트워크 연결 후 결과 값.
     */
    private Network_Result connect(){

        if(this.mIsShowLog){
            connectLog(this.mNetworkConnector.getUrl(), this.mNetworkConnector.getParam());
        }

        long connectStartTime = System.currentTimeMillis();
        Network_Result result = this.mNetworkConnector.connect();
        long connectFinishTime = System.currentTimeMillis();
        // FIXME : URL 패턴 매칭해서 오류시 *로 표시 하게끔 수정 필요.
//        if(getResultType() != INetListener.RESULT_SUCCESS) {
//
//            if (result.indexOf(Const_Net.AVL_IP) > -1 || result.indexOf(Const_Net.GMX_IP) > -1) {
//                result = result.replaceAll(Const_Net.AVL_IP, "***.***.***.***");
//                result = result.replaceAll(Const_Net.GMX_IP, "***.***.***.***");
//            }
//
//        }

        if(this.mIsShowLog) {

            if(result.code == HttpURLConnection.HTTP_OK){
                Log.i(LOG_TAG, "CON RESULT : " + result.result);
            }else{
                Log.w(LOG_TAG, "CON RESULT : " + result.result);
            }
            Log.i(LOG_TAG, "CON TIME : " + String.valueOf(connectFinishTime - connectStartTime) + "ms");

        }

        return result;

    }

    /**
     * 네트워크 연결 URL과 파라미터 로그로 보여주기.
     * @param url 연결 할 URL
     * @param parameter 보낼 파라미터
     */
    private void connectLog(String url, HashMap<String, String> parameter){

        Log.i(LOG_TAG, "CON URL : " + url);

        StringBuilder sb = new StringBuilder();
        boolean isFirstParam = true;

        Set<String> set = parameter.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {

            String param_key = (String) iterator.next();
            String param_value = parameter.get(param_key);

            if (isFirstParam) {
                isFirstParam = false;
                sb.append(param_key + "=" + param_value);
            } else {
                sb.append("&" + param_key + "=" + param_value);
            }

        }
        Log.i(LOG_TAG, "CON PARAM : " + sb.toString());

    }

    ///////////////////
    // Public Method //
    ///////////////////
    public void setShowLog(boolean isShowLog){
        this.mIsShowLog = isShowLog;
    }

    /**
     * 연결 URL 설정.
     * @param url 연결 URL
     */
    public void setUrl(String url){
        this.mNetworkConnector.setUrl(url);
    }

    /**
     * 연결 URL 반환.
     * @return 연결 URL
     */
    public String getUrl(){
        return this.mNetworkConnector.getUrl();
    }

    ////////////////////////
    // User Define Method //
    ////////////////////////
    public Network_Result doStandard(HashMap<String, String> parameter, boolean usePostMethod){

        this.mNetworkConnector.setRequestMethod(usePostMethod ? Network_Connector.Net_Method.POST : Network_Connector.Net_Method.GET);
        this.mNetworkConnector.setParam(parameter);

        return connect();

    }

    public Network_Result doGet(String sampleParam1, String sampleParam2, String sampleParam3){

        this.mNetworkConnector.setRequestMethod(Network_Connector.Net_Method.GET);
        this.mNetworkConnector.addParam("param1", sampleParam1);
        this.mNetworkConnector.addParam("param2", sampleParam2);
        this.mNetworkConnector.addParam("param3", sampleParam3);

        return connect();

    }

    public Network_Result doPost(String sampleParam1, String sampleParam2, String sampleParam3){

        this.mNetworkConnector.setRequestMethod(Network_Connector.Net_Method.POST);
        this.mNetworkConnector.addParam("param1", sampleParam1);
        this.mNetworkConnector.addParam("param2", sampleParam2);
        this.mNetworkConnector.addParam("param3", sampleParam3);

        return connect();

    }

    public Network_Result doDownloadFile(String downloadPath, String downloadName, String sampleParam1, String sampleParam2, String sampleParam3){

        this.mNetworkConnector.setRequestMethod(Network_Connector.Net_Method.POST);
        this.mNetworkConnector.setDownloadFile(true);
        this.mNetworkConnector.setDownloadFileInfo(downloadPath, downloadName);
        this.mNetworkConnector.addParam("fileName", downloadName);
        this.mNetworkConnector.addParam("param1", sampleParam1);
        this.mNetworkConnector.addParam("param2", sampleParam2);
        this.mNetworkConnector.addParam("param3", sampleParam3);

        return connect();

    }

    public Network_Result doUploadFile(String uploadPath, String uploadName, String sampleParam1, String sampleParam2, String sampleParam3){

        this.mNetworkConnector.setRequestMethod(Network_Connector.Net_Method.POST); // 무조건 POST만 가능
        this.mNetworkConnector.setUploadFile(true);
        this.mNetworkConnector.setUploadFileInfo(uploadPath, uploadName);
        this.mNetworkConnector.addParam("fileName", uploadName);
        this.mNetworkConnector.addParam("param1", sampleParam1);
        this.mNetworkConnector.addParam("param2", sampleParam2);
        this.mNetworkConnector.addParam("param3", sampleParam3);

        return connect();

    }

    public Network_Result doSampleMethod(String sampleParam1, String sampleParam2, String sampleParam3){

        this.mNetworkConnector.setRequestMethod(Network_Connector.Net_Method.POST);
        this.mNetworkConnector.addParam("param1", sampleParam1);
        this.mNetworkConnector.addParam("param2", sampleParam2);
        this.mNetworkConnector.addParam("param3", sampleParam3);

        return connect();

    }

}
