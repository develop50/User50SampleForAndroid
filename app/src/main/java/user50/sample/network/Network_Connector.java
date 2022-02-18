package user50.sample.network;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 반드시 스레드에서 해당 클래스 사용가능.
 *
 * 응답코드(참고 : https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)
 * 1xx - 조건부 응답(HTTP 요청은 수신 됐고, 다음 처리를 계속해야함을 의미)
 * └ 100	진행 : 클라이언트로 부터 일부 요청을 받았으며 나머지 정보를 계속 요청함
 * └ 101	Switching Protocols : 요청자는 서버에게 프로토콜을 전환하도록 요청했으며 서버는 그렇게하기로 동의
 * └ 102	Processing : 서버가 요청을 처리했으나 아직 응답을 사용할 수 없음. 이럴 경우 시간 초과되어 요청이 손실되었다고 가정 할 수 없음
 * └ 103	Early Hints : 최종 HTTP 메시지 전에 일부 응답 헤더를 반환하는 데 사용
 * 2xx - 성공(서버가 요청을 성공적으로 처리했음을 의미)
 * └ 200	성공 : 서버가 요청을 제대로 처리했다.
 * └ 201	생성됨 : 요청이 성공했으며, 새로운 리소스를 만들었다.
 * └ 202	허용됨 : 요청을 받았으나, 아직 처리하지는 못했다.
 * └ 203	Non-authoritative information : 서버가 클라이언트 요구 중 일부만 전송
 * └ 204	컨텐츠 없음 : 요청을 처리했지만, 컨텐츠를 제공하지 않는다.
 * └ 205	컨텐츠 재 설정 : 요청을 처리했지만, 컨텐츠를 표시하지 않는다. 그리고 문서를 재 설정할 것을 요구한다.
 * └ 206	일부 성공 : 요청의 일부만 성공적으로 처리
 * └ 207	다중상태 :
 * 3xx - 리다이렉션(때때로 요청을 완료하기 위해서, 다른 페이지로 보내야 할 경우. 예) 로그인을 성공하고 나서 대문 페이지로 보낸다거나, 다운로드 페이지로 보내는 등의 용도로 사용)
 * └ 300	여러 선택 항목 :
 * └ 301	영구이동 : 요청한 페이지가 다른 위치로 영구이동 했다.
 * └ 302	임시이동 : 요청한 페이지가 다른 위치로 임시이동 했다. 요청자는 여전히 현재 페이지를 요청해야 한다.
 * └ 303	기타위치 보기 : 요청자가 다른 위치에 별도의 GET 요청을 하여 응답을 검색할 경우
 * └ 304	수정되지 않음 : 마지막 요청 이후 요청한 페이지가 수정되지 않았다. if-Modified-Sine 헤더에 지정된 날짜/시간 이래로 지정된 문서가 변경된 사실이 없는 경우 이 code로 응답한다.
 * └ 305	프록시 사용 : 요청자는 프록시를 사용하여 요청한 페이지만 접근할 수 있다.
 * 4xx - 클라이언트 오류(클라이언트 요청에 오류가 있음을 의미)
 * └ 400	잘못된 요청 : 주로 헤더 포멧이 HTTP 규약에 맞지 않을 경우
 * └ 401	권한 없음 : 인증을 필요로 하는 요청이다. Basic access authentication에 사용한다.
 * └ 403	금지 : 서버가 요청을 거부하고 있다.
 * └ 404	찾을 수 없음 : 요청한 자원이 서버에 존재하지 않는다.
 * └ 405	허용하지 않는 방법 : 요청에 지정한 방법을 사용할 수 없다.
 * └ 406	허용되지 않음 : 요청한 페이지를 콘텐츠 특성 때문에 응답할 수 없다.
 * └ 408	요청시간 초과 : 서버의 요청대기가 시간을 초과
 * └ 410	사라짐 : 요청한 자원이 삭제되었음. 404와 비슷하지만, 410은 과거에 있었으나 지금 없는 자원이다. 예컨데, 게시판에서 삭제한 포스트에 접근하는 경우
 * └ 411	길이필요 : 유효한 컨텐츠 길이를 명시해야 한다.
 * 5xx - 서버 오류
 * └ 500	Internal server error : 내부 서버 오류
 * └ 501	Not implemented : 웹 서버가 처리 할 수 없음
 * └ 503	Service unnailable : 서비스 제공 불가
 * └ 504	Gateway timeout : 게이트웨이 시간초과
 * └ 505	HTTP version not supported : 해당 http 버전 지원되지 않음
 *
 * Created by abyser on 2018-03-19.
 */

public class Network_Connector {

    /** 로그 태그 */
    private static final String LOG_TAG = Network_Connector.class.getSimpleName();

    private static final String UPLOAD_BOUNDARY = "User50AndroidBoundary";

    public static final String DEFAULT_DOWNLOAD_FILE_NAME = "DEFAULT_DOWNLOAD_FILE_NAME";
    public static final String DEFAULT_DOWNLOAD_FILE_EXTENSION = ".what";

    public enum Net_Method{
        GET,
        POST
    }

    private String mUrl;
    private long mConnectTimeoutTime;
    private long mReadTimeoutTime;
    private Net_Method mRequestMethod;
    private HashMap<String, String> mRequestHeader;
    private String mRequestCharset;
    private HashMap<String, String> mParam;
    private boolean mIsUploadFile;
    private String mUploadFileParamName;
    private String mUploadFilePath;
    private String mUploadFileName;
    private boolean mIsDownloadFile;
    private String mDownloadFilePath;
    private String mDownloadFileName;

    /**
     * 생성자.
     * {@link #setUrl(String)}를 통해 연결 URL과
     * {@link #addParam(String, String)} 또는 {@link #setParam(HashMap)}을 통해 파라미터 설정 필요.
     */
    public Network_Connector(){
        this(null);
    }

    /**
     * 생성자.
     * {@link #addParam(String, String)} 또는 {@link #setParam(HashMap)}을 통해 파라미터 설정 필요.
     * @param url 연결 URL
     */
    public Network_Connector(String url){
        this(url, new HashMap<String, String>());
    }

    /**
     * 생성자.
     * @param url 연결 URL
     * @param param 파라미터
     */
    public Network_Connector(String url, HashMap<String, String> param){

        this.mUrl = ((url == null) ? "" : url);
        this.mConnectTimeoutTime = 10000l;
        this.mReadTimeoutTime = 10000l;
        this.mParam = param;

        this.mRequestMethod = Net_Method.POST;
        this.mRequestHeader = new HashMap<String, String>();
        this.mRequestCharset = "UTF-8";
        this.mIsUploadFile = false;
        this.mUploadFileParamName = null;
        this.mUploadFilePath = null;
        this.mUploadFileName = null;
        this.mIsDownloadFile = false;
        this.mDownloadFilePath = null;
        this.mDownloadFileName = null;

    }

    ////////////////////////////////
    // Private Method For Request //
    ////////////////////////////////
    private String transParamToUrl(){

        if(this.mParam.size() <= 0){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        boolean isFirstSet = true;
        Set<String> set = this.mParam.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {

            String paramKey = (String) iterator.next();
            String paramValue = this.mParam.get(paramKey);

            if(Net_Method.POST.equals(this.mRequestMethod)){

                if(this.mIsUploadFile){

                    sb.append("--" + UPLOAD_BOUNDARY + "\r\n");
                    sb.append("Content-Disposition: form-data; name=\"" + paramKey + "\"\r\n");
                    sb.append("Content-Type: text/plain; charset=" + this.mRequestCharset + "\r\n");
                    sb.append("\r\n" + paramValue + "\r\n");

                }else {

                    if (isFirstSet) {
                        sb.append(paramKey + "=" + paramValue);
                        isFirstSet = false;
                    } else {
                        sb.append("&" + paramKey + "=" + paramValue);
                    }

                }

            }else if(Net_Method.GET.equals(this.mRequestMethod)){

                try {

                    if(isFirstSet){
                        sb.append("?" + paramKey + "=" + URLEncoder.encode(paramValue, this.mRequestCharset));
                        isFirstSet = false;
                    }else{
                        sb.append("&" + paramKey + "=" + URLEncoder.encode(paramValue, this.mRequestCharset));
                    }

                } catch (UnsupportedEncodingException e) {
                    // ignore
                }

            }

        }

        return sb.toString();

    }

    ///////////////////////////////
    // Public Method For Request //
    ///////////////////////////////
    /**
     * 연결 URL 설정.
     * @param url 연결 URL
     */
    public void setUrl(String url){

        if(url == null || url.length() <= 0){
            Log.w(LOG_TAG, "Can not set url. Cause url is null.");
           return;
        }

        this.mUrl = url;
    }

    /**
     * 연결 URL 반환.
     * @return 연결 URL
     */
    public String getUrl(){
        return this.mUrl;
    }

    /**
     * 서버 연결 대기 시간 설정.
     * @param timeoutTime 서버 연결 대기 시간(1000 = 1초)
     */
    public void setConnectTimeoutTime(long timeoutTime){

        if(timeoutTime <= 0){
            Log.w(LOG_TAG, "Can not set connect timeout time. Cause timeout time is negative.");
            return;
        }

        this.mConnectTimeoutTime = timeoutTime;

    }

    /**
     * 서버 연결 대기 시간 반환.
     * @return 서버 연결 대기 시간
     */
    public long getConnectTimeoutTime(){
        return this.mConnectTimeoutTime;
    }

    /**
     * 서버 응답 대기 시간 설정.
     * @param timeoutTime 서버 응답 대기 시간(1000 = 1초)
     */
    public void setReadTimeoutTime(long timeoutTime){

        if(timeoutTime <= 0){
            Log.w(LOG_TAG, "Can not set connect timeout time. Cause timeout time is negative.");
            return;
        }

        this.mReadTimeoutTime = timeoutTime;

    }

    /**
     * 서버 응답 대기 시간 반환.
     * @return 서버 응답 대기 시간
     */
    public long getReadTimeoutTime(){
        return this.mReadTimeoutTime;
    }

    /**
     * 연결 메소드 방식 설정.
     * @param method {@link Net_Method#GET},
     *               {@link Net_Method#POST}
     */
    public void setRequestMethod(Net_Method method){
        this.mRequestMethod = method;
    }

    /**
     * 설정 된 연결 메소드 방식 반환.
     * @return 설정 된 연결 메소드 방식
     */
    public Net_Method getRequestMethod(){
        return this.mRequestMethod;
    }

    /**
     * 요청 헤더 추가.
     * @param key 헤더 키
     * @param value 헤더 값
     */
    public void addRequestHeader(String key, String value){

        if(key == null || key.length() <= 0){
            Log.w(LOG_TAG, "Can not add request header. Cause key is null.");
            return;
        }

        this.mRequestHeader.put(key, value);

    }

    /**
     * 전체 요청 헤더 설정.
     * @param param 전체 헤더
     */
    public void setRequestHeader(HashMap<String, String> param){
        this.mRequestHeader = (param == null ? new HashMap<String, String>() : param);
    }

    /**
     * 요청 헤더 반환.
     * @param key 헤더 키
     * @return null : 헤더 키에 해당하는 값 없음, 값 : 헤더 키에 해당하는 값
     */
    public String getRequestHeader(String key){

        if(key == null || key.length() <= 0){
            return null;
        }

        return this.mRequestHeader.get(key);

    }

    /**
     * 전체 요청 헤더 반환.
     * @return 전체 헤더
     */
    public HashMap<String, String> getHeader(){
        return this.mRequestHeader;
    }

    /**
     * 연결 인코딩 방식 설정.
     * @param charsetName 설정 할 인코딩 방식
     */
    public void setRequestCharset(String charsetName){

        if(charsetName == null || charsetName.length() <= 0){
            Log.e(LOG_TAG, "Can not set request charset. Cause charset name is null.");
            return;
        }

        if(!Charset.isSupported(charsetName)){
            Log.e(LOG_TAG, "Can not set request charset. Cause charset name is not supported.");
            return;
        }

        this.mRequestCharset = charsetName;

    }

    /**
     * 설정 된 연결 인코딩 방식 반환.
     * @return 설정 된 연결 인코딩 방식
     */
    public String getRequestCharset(){
        return this.mRequestCharset;
    }

    /**
     * 설정 가능한 인코딩 방식 목록 반환.
     * @return
     */
    public SortedMap<String, Charset> getSupportedCharset(){
        return Charset.availableCharsets();
    }

    /**
     * 파라미터 추가.
     * @param key 파라미터 키
     * @param value 파라미터 값
     */
    public void addParam(String key, String value){

        if(key == null || key.length() <= 0){
            Log.w(LOG_TAG, "Can not add param. Cause key is null.");
            return;
        }

        if(this.mParam == null){
            this.mParam = new HashMap<String, String>();
        }

        this.mParam.put(key, value);

    }

    /**
     * 전체 파라미터 설정.
     * @param param 전체 파라미터
     */
    public void setParam(HashMap<String, String> param){
        this.mParam = (param == null ? new HashMap<String, String>() : param);
    }

    /**
     * 파라미터 반환.
     * @param key 파라미터 키
     * @return null : 파라미터 키에 해당하는 값 없음, 값 : 파라미터 키에 해당하는 값
     */
    public String getParam(String key){

        if(key == null || key.length() <= 0){
            return null;
        }

        return this.mParam.get(key);

    }

    /**
     * 전체 파라미터 반환.
     * @return 전체 파라미터
     */
    public HashMap<String, String> getParam(){
        return this.mParam;
    }

    /**
     * 파라미터 키 존재 여부 반환.
     * @param key 확인 할 파라미터 키
     * @return true : 존재 함, false : 존재 않함
     */
    public boolean hasParamKey(String key){

        if(this.mParam.size() <= 0){
            return false;
        }

        if(key == null || key.length() <= 0){
            Log.w(LOG_TAG, "Can not check has param key. Cause key is null.");
            return false;
        }

        return this.mParam.containsKey(key);

    }

    /**
     * 서버로 파일 전송 형식으로 요청할지 여부 설정.
     * @param isUploadFile 서버로 파일 전송 형식으로 요청 여부
     */
    public void setUploadFile(boolean isUploadFile){
        this.mIsUploadFile = isUploadFile;
    }

    /**
     * 서버로 파일 전송 형식으로 요청할지 여부 반환.
     * @return true : 파일 전송 형식으로 요청, false : 문자열 형식으로 요청
     */
    public boolean isUploadFile(){
        return this.mIsUploadFile;
    }

    /**
     * 서버로 파일 전송시 전송 할 파일경로, 파일명 설정.
     * @param uploadFilePath 파일경로
     * @param uploadFileName 파일명
     */
    public void setUploadFileInfo(String uploadFilePath, String uploadFileName){
        this.setUploadFileInfo(null, uploadFilePath, uploadFileName);
    }

    /**
     * 서버로 파일 전송시 전송 할 파일경로, 파일명 설정.
     * @param uploadFileParamName 서버에서 받아들일 파일 파라미터명
     * @param uploadFilePath 파일경로
     * @param uploadFileName 파일명
     */
    public void setUploadFileInfo(String uploadFileParamName, String uploadFilePath, String uploadFileName){

        this.mUploadFileParamName = uploadFileParamName;
        this.mUploadFilePath = uploadFilePath;
        this.mUploadFileName = uploadFileName;

    }

    public void setUploadFileParamName(String uploadFileParamName){
        this.mUploadFileParamName = uploadFileParamName;
    }

    public String getUploadFileParamName(){
        return this.mUploadFileParamName;
    }

    /**
     * 서버로 파일 전송시 전송 할 파일경로 설정.
     * @param uploadFilePath 파일경로
     */
    public void setUploadFilePath(String uploadFilePath){
        this.mUploadFilePath = uploadFilePath;
    }

    /**
     * 서버로 전송 할 파일의 경로 반환.
     * @return 파일경로
     */
    public String getUploadFilePath(){
        return this.mUploadFilePath;
    }

    /**
     * 서버로 파일 전송시 전송 할 파일명 설정.
     * @param uploadFileName 파일명
     */
    public void setUploadFileName(String uploadFileName){
        this.mUploadFileName = uploadFileName;
    }

    /**
     * 서버로 전송 할 파일명 반환.
     * @return 파일명
     */
    public String getUploadFileName(){
        return this.mUploadFileName;
    }

    /**
     * 서버로부터 파일 다운로드 형식으로 응답받을지 여부 설정.
     * @param isDownloadFile 서버로부터 파일 다운로드 형식으로 응답받을지 여부
     */
    public void setDownloadFile(boolean isDownloadFile){
        this.mIsDownloadFile = isDownloadFile;
    }

    /**
     * 서버로부터 파일 다운로드 형식으로 응답받을지 여부 반환.
     * @return true : 파일 다운로드 형식으로 응답, false : 문자열 형식으로 응답
     */
    public boolean isDownloadFile(){
        return this.mIsDownloadFile;
    }

    /**
     * 서버로부터 파일 다운로드시 저장 될 파일경로, 파일명 설정
     * @param downloadFilePath 파일경로
     * @param downloadFileName 파일명
     */
    public void setDownloadFileInfo(String downloadFilePath, String downloadFileName){

        this.mDownloadFilePath = downloadFilePath;
        this.mDownloadFileName = downloadFileName;

    }

    /**
     * 파일 다운로드시 저장 될 파일경로 설정.
     * @param downloadFilePath 파일경로
     */
    public void setDownloadFilePath(String downloadFilePath){
        this.mDownloadFilePath = downloadFilePath;
    }

    /**
     * 파일 다운로드시 저장 될 파일경로 반환.
     * @return 파일경로
     */
    public String getDownloadFilePath(){
        return this.mDownloadFilePath;
    }

    /**
     * 파일 다운로드시 저장 될 파일명 설정.
     * @param downloadFileName 파일명
     */
    public void setDownloadFileName(String downloadFileName){
        this.mDownloadFileName = downloadFileName;
    }

    /**
     * 파일 다운로드시 저장 될 파일명 반환.
     * @return 파일명
     */
    public String getDownloadFileName(){
        return this.mDownloadFileName;
    }

    /**
     * 연결 후 결과 반환.
     * @return null : 연결 URL 없음, 값 : 결과 값
     */
    public Network_Result connect(){

        Network_Result result = new Network_Result();

        if(this.mUrl == null || this.mUrl.length() <= 0){
            result.code = -2;
            return result;
        }

        if(this.mIsUploadFile){
            this.mRequestMethod = Net_Method.POST;
        }

        HttpURLConnection huc = null;
        BufferedOutputStream bos = null;
        try{

            // 연결
            String param = transParamToUrl();

            URL url = new URL(this.mUrl + (Net_Method.GET.equals(this.mRequestMethod) ? param : ""));
            huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod(this.mRequestMethod.name());
            huc.setUseCaches(false);
            huc.setDoOutput(Net_Method.GET.equals(this.mRequestMethod) ? false : true);
            huc.setDoInput(true);
            huc.setConnectTimeout((int)this.mConnectTimeoutTime);
            huc.setReadTimeout((int)this.mReadTimeoutTime);

            writeHeader(huc);

            if (Net_Method.POST.equals(this.mRequestMethod)) {

                if (this.mIsUploadFile) {

                    huc.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + UPLOAD_BOUNDARY);

                    if (param != null && param.length() > 0) {

                        byte[] paramBytes = param.getBytes(this.mRequestCharset);
                        bos = new BufferedOutputStream(huc.getOutputStream());
                        bos.write(paramBytes);
                        bos.flush();

                    }

                    writeForUpload(bos);

                }else{

                    if (param != null && param.length() > 0) {

                        byte[] paramBytes = param.getBytes(this.mRequestCharset);
                        bos = new BufferedOutputStream(huc.getOutputStream());
                        bos.write(paramBytes);
                        bos.flush();
                        bos.close();

                    }

                }

            }

            // 결과
            result.code = huc.getResponseCode();
            if(result.code == HttpURLConnection.HTTP_OK){

                if(this.mIsDownloadFile){
                    result.result = String.valueOf(resultForDownload(huc));
                }else{
                    result.result = resultForSuccess(huc);
                }

            }else{
                result.result = resultForError(huc);
            }

        } catch (Exception e) {
            result.code = -2;
            result.result = e.getMessage();
        } finally {

            try {

                if(bos != null){
                    bos.close();
                }

            } catch (Exception e2) {
                // not catch
            }

            try {

                if(huc != null){
                    huc.disconnect();
                }

            } catch (Exception e2) {
                // not catch
            }

        }

        return result;

    }

    private void writeHeader(HttpURLConnection httpURLConnection) throws IllegalStateException, NullPointerException{

        Set<String> set = this.mRequestHeader.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {

            String requestHeaderKey = (String) iterator.next();
            String requestHeaderValue = this.mRequestHeader.get(requestHeaderKey);

            httpURLConnection.setRequestProperty(requestHeaderKey, requestHeaderValue);

        }

    }

    private void writeForUpload(BufferedOutputStream bos) throws FileNotFoundException, IOException{

        File uploadFile = new File(this.mUploadFilePath, this.mUploadFileName);
        if(!uploadFile.exists()){
            throw new FileNotFoundException();
        }

        String uploadFileParamName = ((this.mUploadFileParamName == null || this.mUploadFileParamName.length() <= 0) ? "UPLOAD_FILE" : this.mUploadFileParamName);
        String uploadFileInfo = "";
        uploadFileInfo += "--" + UPLOAD_BOUNDARY + "\r\n";
        uploadFileInfo += "Content-Disposition: form-data; name=\"" + uploadFileParamName + "\"; filename=\"" + uploadFile.getName()/* URLEncoder.encode(this.mUploadFileName, this.mRequestCharset) */ + "\"\r\n";
        uploadFileInfo += "Content-Type: " + HttpURLConnection.guessContentTypeFromName(uploadFile.getName()) + "\r\n";
        uploadFileInfo += "Content-Transfer-Encoding: binary";
        uploadFileInfo += "\r\n\r\n";

        byte[] uploadFileInfoBytes = uploadFileInfo.getBytes(this.mRequestCharset);
        bos.write(uploadFileInfoBytes);
        bos.flush();

        FileInputStream fis = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
            bos.flush();
        }
        fis.close();

        String last = "\r\n\r\n";
        last += "--" + UPLOAD_BOUNDARY + "--\r\n";

        byte[] lastBytes = last.getBytes(this.mRequestCharset);
        bos.write(lastBytes);
        bos.flush();
        bos.close();

    }

    private String resultForSuccess(HttpURLConnection huc) throws IOException, Exception{

        StringBuilder response = new StringBuilder();
        BufferedReader br = null;
        try {

            String tmpReadLine = "";
            br = new BufferedReader(new InputStreamReader(huc.getInputStream()), 4096);
            while ((tmpReadLine = br.readLine()) != null) {
                response.append(tmpReadLine);
            }

        }catch (IOException e){
            throw e;
        }catch (Exception e){
            throw e;
        }finally {

            try {

                br.close();

            } catch (Exception e2) {
                // ignore
            }

        }

        return response.toString();

    }

    private boolean resultForDownload(HttpURLConnection huc) throws IOException, Exception{

        if(this.mDownloadFilePath != null && this.mDownloadFilePath.length() > 0){

            File downloadFolder = new File(this.mDownloadFilePath);
            if(downloadFolder.exists()) {

                // 다운로드 파일명 설정
                String realFileName = null;
                String realFileExtension = null;
                if (this.mDownloadFileName != null && this.mDownloadFileName.length() > 0) {

                    int extensionDotPostion = this.mDownloadFileName.lastIndexOf(".");
                    if (extensionDotPostion != -1) {
                        realFileName = this.mDownloadFileName.substring(0, extensionDotPostion);
                        realFileExtension = this.mDownloadFileName.substring(extensionDotPostion);
                    } else {
                        realFileName = this.mDownloadFileName;
                        realFileExtension = DEFAULT_DOWNLOAD_FILE_EXTENSION;
                    }

                } else {

                    String contentDisposition = huc.getHeaderField("Content-Disposition");
                    if (contentDisposition != null && contentDisposition.length() > 0) {

                        if (contentDisposition.length() >= 9) {

                            int fileNamePosition = contentDisposition.indexOf("filename=");
                            if (fileNamePosition != -1) {

                                String filename = contentDisposition.substring(fileNamePosition, contentDisposition.length());
                                int extensionDotPostion = filename.lastIndexOf(".");
                                if (extensionDotPostion != -1) {
                                    realFileName = filename.substring(0, extensionDotPostion);
                                    realFileExtension = this.mDownloadFileName.substring(extensionDotPostion);
                                } else {
                                    realFileName = filename;
                                    realFileExtension = DEFAULT_DOWNLOAD_FILE_EXTENSION;
                                }

                            }

                        }

                    }

                }

                if (realFileName == null) {
                    realFileName = DEFAULT_DOWNLOAD_FILE_NAME;
                    realFileExtension = DEFAULT_DOWNLOAD_FILE_EXTENSION;
                }

                // 다운로드 파일명 중복 확인 및 새로운 파일명 설정
                String downloadFileName = null;
                File duplicationCheckFile = new File(this.mDownloadFilePath, (realFileName + realFileExtension));
                if (duplicationCheckFile.exists()) {

                    File[] downloadFolderFileList = downloadFolder.listFiles(new FileFilter() {

                        @Override
                        public boolean accept(File pathname) {
                            return pathname.isFile();
                        }

                    });

                    boolean hasReName = false;
                    int num = 1;
                    downloadFileName = realFileName + " (" + String.valueOf(num++) + ")" + realFileExtension;
                    for (int i = 0; i < downloadFolderFileList.length; i++) {

                        File downloadFolderFile = downloadFolderFileList[i];
                        if (downloadFileName.equals(downloadFolderFile.getName())) {
                            hasReName = true;
                        }

                        if (i == (downloadFolderFileList.length - 1)) {

                            if (hasReName) {
                                hasReName = false;
                                downloadFileName = realFileName + " (" + String.valueOf(num++) + ")" + realFileExtension;
                                i = -1;
                            }

                        }

                    }

                } else {
                    downloadFileName = duplicationCheckFile.getName();
                }

                // 다운로드
                boolean result = false;
                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                try {

                    bis = new BufferedInputStream(huc.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream(new File(this.mDownloadFilePath, downloadFileName)));

                    byte[] readByte = new byte[4096];
                    int data = 0;
                    while ((data = bis.read(readByte, 0, 4096)) != -1) {
                        bos.write(readByte, 0, data);
                    }
                    bos.flush();

                    result = true;

                } catch (IOException e) {
                    throw e;
                } catch (Exception e){
                    throw e;
                } finally {

                    try {

                        bos.close();

                    }catch (Exception e){
                        // ignore
                    }

                    try {

                        bis.close();

                    }catch (Exception e){
                        // ignore
                    }

                }

                return result;

            }else{
                Log.w(LOG_TAG, "Failed download file. Cause download path is not exists.");
            }

        }else{
            Log.w(LOG_TAG, "Failed download file. Cause download setting path is null.");
        }

        return false;

    }

    private String resultForError(HttpURLConnection huc) throws Exception{

        StringBuilder response = new StringBuilder();
        BufferedReader br = null;
        try {

            String tmpReadLine = null;
            br = new BufferedReader(new InputStreamReader(huc.getErrorStream()), 4096);
            while ((tmpReadLine = br.readLine()) != null) {
                response.append(tmpReadLine);
            }

        }catch (Exception e){
            throw e;
        }finally {

            try {

                br.close();

            } catch (Exception e2) {
                // ignore
            }

        }

        return response.toString();

    }

    /**
     * 테스트 필요.
     * 헤더 정보 반환.
     */
    private void getHeaderInfo(HttpURLConnection huc){

        // 헤더 읽은 값 샘플 1
//        null : HTTP/1.1 200 OK
//        Content-Description : JSP Generated Data
//        Content-Disposition : attachment; filename="이시하라 사토미(Ishihara Satomi)_1024x1312.png"
//        Content-Length : 3587367
//        Content-Type : application/octet-stream;charset=utf-8
//        Date : Tue, 24 Jul 2018 08:27:03 GMT
//        Server : Apache-Coyote/1.1
//        X-Android-Received-Millis : 1532420818965
//        X-Android-Response-Source : NETWORK 200
//        X-Android-Selected-Protocol : http/1.1
//        X-Android-Sent-Millis : 1532420818950

        // 헤더 읽은 값 샘플 2
//        null : HTTP/1.1 404 Not Found
//        Content-Language : en
//        Content-Length : 1236
//        Content-Type : text/html;charset=utf-8
//        Date : Tue, 24 Jul 2018 08:28:17 GMT
//        Server : Apache-Coyote/1.1
//        X-Android-Received-Millis : 1532420893137
//        X-Android-Response-Source : NETWORK 404
//        X-Android-Selected-Protocol : http/1.1
//        X-Android-Sent-Millis : 1532420893127

        // 헤더 읽은 값 샘플 3
//        null : HTTP/1.1 200 OK
//        Content-Length : 14
//        Content-Type : text/html;charset=UTF-8
//        Date : Tue, 24 Jul 2018 08:26:13 GMT
//        Server : Apache-Coyote/1.1
//        Set-Cookie : JSESSIONID=A15251EE7AD89AC0D025501B850937B6; Path=/CodeSample-Web; HttpOnly
//        X-Android-Received-Millis : 1532420769416
//        X-Android-Response-Source : NETWORK 200
//        X-Android-Selected-Protocol : http/1.1
//        X-Android-Sent-Millis : 1532420769406

        Map<String, List<String>> headerList = huc.getHeaderFields();
        Set<String> set = headerList.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {

            String key = (String) iterator.next();
            List<String> list = headerList.get(key);

            for(String val : list){
                Log.i(LOG_TAG, key + " : " + val);
            }

        }

    }

}
