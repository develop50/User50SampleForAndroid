package user50.sample.function.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;

import user50.sample.R;
import user50.sample.network.INetworkListener;
import user50.sample.network.Network_AsyncTask;
import user50.sample.network.Network_Result;

/**
 * <pre>
 *
 * </pre>
 * Created by abyser on 2018-07-05.
 */

public class Activity_Network extends Activity implements View.OnClickListener, INetworkListener {

    private static final String LOG_TAG = Activity_Network.class.getSimpleName();

    //////////////////
    // Static Final //
    //////////////////
    public static final String IP = "10.1.74.156";
    public static final String PORT = "8080";
    public static final String CONTEXT = "argisserver";

    ///////////
    // Final //
    ///////////

    ////////////
    // Static //
    ////////////

    ////////////
    // Object //
    ////////////

    ////////////
    // Layout //
    ////////////
    private EditText edit_root_url;
    private EditText edit_context_url;

    private Button btn_download;
    private Button btn_upload;

    private ListView lv_parameter;
    private Adapter_Network mParameterAdapter;
    private Button btn_add;
    private Button btn_clear;

    private RadioGroup rg_method;
    private Button btn_run;
    private ScrollView sv_result;
    private TextView tv_result;

    private String mRootUrl;
    private String mContextUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        initValue();
        initLayout();

    }

    private void initValue() {

        this.mRootUrl = "http://" + IP + ":" + PORT;
        this.mContextUrl = "/" + CONTEXT + "/rest/QrInsertImage";

//        this.mRootUrl = " http://172.16.1.29:12000";
//        this.mContextUrl = "/MDTDataFileServer/vsi/getVsiList";
//
//        this.mContextUrl = "/MDTDataFileServer/vsi/setVsiItem";
//
//        this.mContextUrl = "/MDTDataFileServer/vsi/deleteVsiItem";
//
//        this.mContextUrl = "/GWAVL/Service.jsp";

    }

    private void initLayout() {

        edit_root_url = (EditText)findViewById(R.id.network_edit_root_url);
        edit_context_url = (EditText)findViewById(R.id.network_edit_context_url);

        btn_download = (Button)findViewById(R.id.network_btn_download);
        btn_upload = (Button)findViewById(R.id.network_btn_upload);

        lv_parameter = (ListView)findViewById(R.id.network_lv_parameter);
        btn_add = (Button)findViewById(R.id.network_btn_add);
        btn_clear = (Button)findViewById(R.id.network_btn_clear);

        rg_method = (RadioGroup) findViewById(R.id.network_rg_method);
        btn_run = (Button)findViewById(R.id.network_btn_run);
        sv_result = (ScrollView)findViewById(R.id.network_sv_result);
        tv_result = (TextView) findViewById(R.id.network_tv_result);

        // 초기 값
        edit_root_url.setText(this.mRootUrl);
        edit_context_url.setText(this.mContextUrl);

        mParameterAdapter = new Adapter_Network(this);
        lv_parameter.setAdapter(mParameterAdapter);

        // 이벤트
        btn_download.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        btn_add.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_run.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.network_btn_download: {

                String rootUrl = edit_root_url.getText().toString().trim();
                String contextUrl = edit_context_url.getText().toString().trim();

                if (rootUrl == null || rootUrl.length() <= 0) {
                    Toast.makeText(this, "ROOT URL을 입력 후 다시 시도해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String realUrl = rootUrl + contextUrl;

                Network_AsyncTask async = new Network_AsyncTask(this, Network_AsyncTask.CONNECT_TYPE_DOWNLOAD, this);
                async.setUrl(realUrl);
                async.setUsePostMethod(rg_method.getCheckedRadioButtonId() == R.id.network_rb_get ? false : true);
                async.setShowProgressDialog(this, "네트워크 통신 중", "네트워크 통신 중 입니다.\n잠시만 기다려 주시기 바랍니다.");
                async.execute();

            }
                break;
            case R.id.network_btn_upload: {

                rg_method.check(R.id.network_rb_post);
                String rootUrl = edit_root_url.getText().toString().trim();
                String contextUrl = edit_context_url.getText().toString().trim();

                if(rootUrl == null || rootUrl.length() <= 0){
                    Toast.makeText(this, "ROOT URL을 입력 후 다시 시도해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String realUrl = rootUrl + contextUrl;

                Network_AsyncTask async = new Network_AsyncTask(this, Network_AsyncTask.CONNECT_TYPE_UPLOAD, this);
                async.setUrl(realUrl);
                async.setUsePostMethod(true);
                async.setShowProgressDialog(this, "네트워크 통신 중", "네트워크 통신 중 입니다.\n잠시만 기다려 주시기 바랍니다.");
                async.execute();

            }
                break;
            case R.id.network_btn_add:

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_network_parameter, null);
                final EditText edit_key = (EditText)dialogView.findViewById(R.id.dialog_network_parameter_edit_key);
                final EditText edit_value = (EditText)dialogView.findViewById(R.id.dialog_network_parameter_edit_value);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("파라미터 추가");
                builder.setView(dialogView);
                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String addKey = edit_key.getText().toString().trim();
                        String addValue = edit_value.getText().toString().trim();
                        if(addKey == null || addKey.length() <= 0){
                            Toast.makeText(Activity_Network.this, "키 값이 없는 상태로 추가를 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Pair<String, String> parameter = mParameterAdapter.getItem(addKey);
                        if(parameter != null){
                            Toast.makeText(Activity_Network.this, "이미 존재하는 키 값으로 추가 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dialog.dismiss();

                        mParameterAdapter.add(addKey, addValue);

                        Toast.makeText(Activity_Network.this, "파라미터를 추가하였습니다.", Toast.LENGTH_SHORT).show();

                    }

                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }

                });
                builder.create();
                builder.show();

                break;
            case R.id.network_btn_clear:

                mParameterAdapter.clear();

                break;
            case R.id.network_btn_run:

//                new AsyncTask<Void, Void, Void>(){
//
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//
////                        String URL1 = "http://210.101.103.91:6060/xeus/ws/addEvent.json"
////                        String PARAM = "json={\"statEvetTypCd\":\"화재\",\"statMsgTypCd\":\"\",\"outbPosNm\":\"강릉시청 부근\",\"statEvetNm\":\"소방차량출동\",\"statEvetClrDtm\":\"\",\"statEvetCntn\":\"강릉시청 휴게공원 부근 화재 발생\",\"statEvetType\":\"사회재난\",\"outbPos\":[{\"x\":\"127.032638\",\"y\":\"37.483496\"}],\"statEvetOutbDtm\":\"20191211143135\",\"statEvetActnCntn\":\"\",\"procSt\":\"10\",\"isTest\":\"Y\",\"uSvcOutbId\":\"\",\"statEvetActnMn\":\"\",\"statEvetActnDtm\":\"\",\"statEvetSvcTyp\":\"\",\"etcCntn\":\"{\\\"fileNm\\\":\\\"SampleVideo_1280x720_1mb.mp4\\\"}\",\"outbPosX\":\"127.032638\",\"outbPosY\":\"37.483496\",\"tmx\":\"\",\"tmy\":\"\"}";
//
//                            final String boundaryName = "CUSTOM_BOUNDARY_NAME";
//                            final String charset = "UTF-8";
//
//                            HttpURLConnection huc = null;
//                            BufferedOutputStream bos = null;
//                            String result = "";
//                            try{
//
//                                // 연결
//                                String uploadFilePath = Util.getStoragePath(Net_Sample_Activity.this, false);
//                                String uploadFileName = "SampleVideo_1280x720_1mb.mp4";
//                                String ip = "210.101.103.91";
//                                String port = "6060";
//                                String urlToString = "http://" + ip + ":" + port + "/xeus/ws/addEvent.json";
//                                String param = "json={\"statEvetTypCd\":\"화재\",\"statMsgTypCd\":\"\",\"outbPosNm\":\"강릉시청 부근\",\"statEvetNm\":\"소방차량출동\",\"statEvetClrDtm\":\"\",\"statEvetCntn\":\"강릉시청 휴게공원 부근 화재 발생\",\"statEvetType\":\"사회재난\",\"outbPos\":[{\"x\":\"127.032638\",\"y\":\"37.483496\"}],\"statEvetOutbDtm\":\"20191211143135\",\"statEvetActnCntn\":\"\",\"procSt\":\"10\",\"isTest\":\"Y\",\"uSvcOutbId\":\"\",\"statEvetActnMn\":\"\",\"statEvetActnDtm\":\"\",\"statEvetSvcTyp\":\"\",\"etcCntn\":\"{\\\"fileNm\\\":\\\"" + uploadFileName + "\\\"}\",\"outbPosX\":\"127.032638\",\"outbPosY\":\"37.483496\",\"tmx\":\"\",\"tmy\":\"\"}";
//
//                                URL url = new URL(urlToString);
//                                huc = (HttpURLConnection) url.openConnection();
//                                huc.setRequestMethod("POST");
//                                huc.setUseCaches(false);
//                                huc.setDoOutput(true);
//                                huc.setDoInput(true);
//                                huc.setConnectTimeout(10000);
//                                huc.setReadTimeout(10000);
//                                huc.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryName);
//
//                                // 파라미터 쓰기
//                                byte[] paramBytes = param.getBytes(charset);
//                                bos = new BufferedOutputStream(huc.getOutputStream());
//                                bos.write(paramBytes);
//                                bos.flush();
//
//                                // 파일 전송
//                                File uploadFile = new File(uploadFilePath, uploadFileName);
//                                String uploadFileParamName = "files";
//                                String uploadFileInfo = "";
//                                uploadFileInfo += "--" + boundaryName + "\r\n";
//                                uploadFileInfo += "Content-Disposition: form-data; name=\"" + uploadFileParamName + "\"; filename=\"" + uploadFile.getName()/* URLEncoder.encode(this.mUploadFileName, this.mRequestCharset) */ + "\"\r\n";
//                                uploadFileInfo += "Content-Type: " + HttpURLConnection.guessContentTypeFromName(uploadFile.getName()) + "\r\n";
//                                uploadFileInfo += "Content-Transfer-Encoding: binary";
//                                uploadFileInfo += "\r\n\r\n";
//
//                                byte[] uploadFileInfoBytes = uploadFileInfo.getBytes(charset);
//                                bos.write(uploadFileInfoBytes);
//                                bos.flush();
//
//                                FileInputStream fis = new FileInputStream(uploadFile);
//                                byte[] buffer = new byte[4096];
//                                int bytesRead = -1;
//                                while ((bytesRead = fis.read(buffer)) != -1) {
//                                    bos.write(buffer, 0, bytesRead);
//                                    bos.flush();
//                                }
//                                fis.close();
//
//                                String last = "\r\n\r\n";
//                                last += "--" + boundaryName + "--\r\n";
//
//                                byte[] lastBytes = last.getBytes(charset);
//                                bos.write(lastBytes);
//                                bos.flush();
//                                bos.close();
//
//                                // 결과 읽기
//                                int responseCode = huc.getResponseCode();
//                                if(responseCode == HttpURLConnection.HTTP_OK){
//                                    result = "Success.";
//                                }else{
//                                    result = "Error occurred.";
//                                }
//
//                            } catch (Exception e) {
//                                result = e.getMessage();
//                            } finally {
//
//                                try {
//
//                                    if(bos != null){
//                                        bos.close();
//                                    }
//
//                                } catch (Exception e2) {
//                                    // not catch
//                                }
//
//                                try {
//
//                                    if(huc != null){
//                                        huc.disconnect();
//                                    }
//
//                                } catch (Exception e2) {
//                                    // not catch
//                                }
//
//                            }
//
//                        Log.w(LOG_TAG, "check reuslt : " + result);
//
//                            return null;
//
//                    }
//
//                }.execute();

                String rootUrl = edit_root_url.getText().toString().trim();
                String contextUrl = edit_context_url.getText().toString().trim();

                if(rootUrl == null || rootUrl.length() <= 0){
                    Toast.makeText(this, "ROOT URL을 입력 후 다시 시도해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String realUrl = rootUrl + contextUrl;

                Network_AsyncTask async = new Network_AsyncTask(this, Network_AsyncTask.CONNECT_TYPE_STANDARD, this);
                async.setUrl(realUrl);
                async.setParameter(mParameterAdapter.getItemListToHashMap());
                async.setUsePostMethod(rg_method.getCheckedRadioButtonId() == R.id.network_rb_get ? false : true);
                async.setShowProgressDialog(this, "네트워크 통신 중", "네트워크 통신 중 입니다.\n잠시만 기다려 주시기 바랍니다.");
                async.execute();

                break;
        }

    }

    @Override
    public void onNetworkResult(int event, int resultType, Network_Result result) {

        String resultTypeToString = null;
        switch (resultType){
            case INetworkListener.RESULT_SUCCESS:
                resultTypeToString = "SUCCESS";

                if(result == null){
                    Log.e(LOG_TAG, "check result is null");
                    return;
                }

                // TODO

                break;
            case INetworkListener.RESULT_APP_ERROR:
                resultTypeToString = "APP ERROR";
                break;
            case INetworkListener.RESULT_SERVER_ERROR:
                resultTypeToString = "SERVER ERROR";
                break;
        }

        Log.i(LOG_TAG, "check connect result : [" + resultTypeToString + "] " + (result == null ? "result is null" : result.result));

        String time = new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
        String logMsg = "\n" + time + " : " + resultTypeToString + ".\n" + (result == null ? "Result is null." : result.result);

        SpannableStringBuilder builder = new SpannableStringBuilder(logMsg);
        int msgColor = Color.parseColor("#ffffff");
        builder.setSpan(new ForegroundColorSpan(msgColor), 0, logMsg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_result.setText(builder);
        sv_result.fullScroll(ScrollView.FOCUS_DOWN);

    }

    private String parseXmlTagValue(Element element, String tagName){

        NodeList tagList = element.getElementsByTagName(tagName);
        if(tagList.item(0).getChildNodes().item(0) != null){
            return tagList.item(0).getChildNodes().item(0).getNodeValue();
        }

        return null;

    }

}