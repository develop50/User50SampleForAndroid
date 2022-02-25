package user50.sample.function.filemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import user50.sample.R;

/**
 * 파일탐색기 리스트를 보여 줄 어뎁터.
 * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE} 또는
 * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE} 권한 필요.
 * @author abyser
 */
public class Adapter_FileManager extends BaseAdapter {

    //////////////
    // Constant //
    //////////////
    /**
     * 로그 태그
     */
    private static final String LOG_TAG = Adapter_FileManager.class.getSimpleName();

    ////////////
    // Layout //
    ////////////

    //////////////
    // Variable //
    //////////////
    /**
     * {@link Context}
     */
    private Context mContext;
    /**
     * 리스트 아이템.
     */
    private ArrayList<File> mList;
    /**
     * 최상위 경로.
     */
    private String mRootFolderPath;
    /**
     * 내부 저장소 경로.
     */
    private String mInternalStoragePath;
    /**
     * 현재 경로.
     */
    private String mCurrentPath;

    /**
     * 생성자.
     * @param context {@link Context}
     */
    public Adapter_FileManager(Context context) {
        this(context, null);
    }

    /**
     * 생성자.
     * @param context {@link Context}
     * @param rootFolderPath 최상위 경로. null인 경우 내부 저장소로 설정
     */
    public Adapter_FileManager(Context context, String rootFolderPath) {

        this.mContext = context;
        this.mList = new ArrayList<File>();
        this.mRootFolderPath = rootFolderPath;
        this.mInternalStoragePath = null;
        this.mCurrentPath = null;

        init();

    }

    /**
     * 초기 파일 리스트 설정.
     */
    private void init(){

        try {

            mInternalStoragePath = getStoragePath(false);

        } catch (Exception e) {
            e.printStackTrace();
            mInternalStoragePath = null;
        }

        File rootFolder = null;
        if(mRootFolderPath == null || mRootFolderPath.length() <= 0){

            if(mInternalStoragePath == null || mInternalStoragePath.length() <= 0){

                rootFolder = null;
                mRootFolderPath = null;

            }else{

                File storageFolder = new File(mInternalStoragePath);
                // /storage 경로는 확인 안되는지 확인해보기
//                rootFolder = storageFolder.getParentFile().getParentFile();
                rootFolder = storageFolder;
                mRootFolderPath = rootFolder.getPath();

            }

        }else{

            rootFolder = new File(mRootFolderPath);
            if(!rootFolder.exists()){
                rootFolder = null;
                mRootFolderPath = null;
            }

        }

        if(rootFolder == null){
            mList = new ArrayList<File>();
            mCurrentPath = "Unknown";
        }else{
            mList = new ArrayList<File>(Arrays.asList(rootFolder.listFiles()));
            mCurrentPath = rootFolder.getPath();
        }

    }

    ///////////////////
    // Extend Method //
    ///////////////////
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public File getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int tmp_position = position;

        if (convertView == null) {
            convertView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.item_filemanager, null);
        }

        ImageView iv_ic = (ImageView)convertView.findViewById(R.id.filemanager_list_item_iv_icon);
        TextView tv_name = (TextView)convertView.findViewById(R.id.filemanager_list_item_tv_name);

        File f = mList.get(tmp_position);

        if("...".equals(f.getName())){

            iv_ic.setImageResource(R.drawable.filemanager_ic_dir_open);
            tv_name.setText("...");

            return convertView;

        }else{

            if(f.isDirectory()){
                iv_ic.setImageResource(R.drawable.filemanager_ic_dir_close);
            }else{
                iv_ic.setImageResource(R.drawable.filemanager_ic_file);
            }

        }

        tv_name.setText(f.getName());

        return convertView;

    }

    //////////////////////
    // Implement Method //
    //////////////////////

    ////////////////////
    // Private Method //
    ////////////////////
    /**
     * 저장소 경로 반환.
     * @param isGetExternalPath 외부 저장소 반환 여부
     * @return 저장소 경로
     */
    private String getStoragePath(boolean isGetExternalPath) throws IOException {

        String storagePath = "";

        String internalPath = "";
        String externalPath = "";

        // /storage/저장소명/Android/data/패키지명 형식의 File 리스트 가져오기
        File[] packageDirs = this.mContext.getExternalFilesDirs("");

        // 우리나라는 드물지만 2개 이상의 저장소를 가진 디바이스가 있어
        // 해당 디바이스는 따로 처리가 필요 할것으로 판단되어 오류 처리 함.
        if (packageDirs.length > 2) {
            throw new IOException("Can not find path. Cause device has more than 2 storage.");
        }

        for (int i = 0; i < packageDirs.length; i++) {

            File packageDir = packageDirs[i];
            if (packageDir != null) {

                String tmpPackagePath = packageDir.getPath();
                String tmpStoragePath = tmpPackagePath.substring(0, tmpPackagePath.indexOf("/Android"));

                if (tmpStoragePath.toLowerCase().contains("emulated")) {
                    internalPath = tmpStoragePath;
                } else {
                    externalPath = tmpStoragePath;
                }

            }

        }

        if (isGetExternalPath) {

            if (externalPath != null && externalPath.length() > 0) {
                storagePath = externalPath;
            } else {
                throw new IOException("Can not find path. Cause find storage path is null.");
            }

        } else {

            if (internalPath != null && internalPath.length() > 0) {
                storagePath = internalPath;
            } else {
                throw new IOException("Can not find path. Cause find storage path is null.");
            }

        }

        return storagePath;

    }

    /**
     * 클릭한 파일이 폴더인지 여부 반환.
     * @param clickFile 클릭한 파일
     * @return true : 클릭한 파일이 폴더임, false : 클릭한 파일이 파일임
     */
    private boolean isClickFolder(File clickFile){

        if("...".equals(clickFile.getName()) || clickFile.isDirectory()){
            return true;
        }

        return false;

    }

    /**
     * 폴더 클릭 이벤트.
     * @param position 클릭한 폴더 포지션
     */
    private void clickFolder(int position){

        File f = null;
        File[] fileList = null;

        if(isClickInFolder(mList.get(position))){

            f = mList.get(position);
            if(mInternalStoragePath.equals((f.getPath() + "/0"))){
                f = new File(mInternalStoragePath);
            }
            File[] tmpFileList = f.listFiles();
            if(tmpFileList == null){
                showToast("파일 접근 권한이 없습니다.", true);
                return;
            }

            fileList = new File[tmpFileList.length + 1];
            fileList[0] = new File(f.getPath() + "/...");
            int size = 1;
            for (File data : tmpFileList) {
                fileList[size++] = data;
            }

        }else{

            f = new File(mCurrentPath);
            if(mInternalStoragePath.equals(f.getPath())){
                f = f.getParentFile().getParentFile();
            }else{
                f = f.getParentFile();
            }
            File[] tmpFileList = f.listFiles();

            if(mRootFolderPath.equals(f.getPath())){

                fileList = tmpFileList;

            }else{

                fileList = new File[tmpFileList.length + 1];
                fileList[0] = new File(f.getPath() + "/...");
                int size = 1;
                for (File data : tmpFileList) {
                    fileList[size++] = data;
                }

            }

        }

        mCurrentPath = fileList[0].getParent();

        refreshFileList(fileList);

    }

    /**
     * 클릭한 폴더 파일이 하위 폴더인지 여부 반환.
     * @param clickFile 클릭한 폴더 파일
     * @return true : 클릭한 폴더 파일이 하위 폴더임, false : 클릭한 폴더 파일이 상위 가기 폴더임
     */
    private boolean isClickInFolder(File clickFile){
        return (!"...".equals(clickFile.getName()));
    }

    /**
     * 파일 리스트 갱신.
     * @param fileList
     */
    private void refreshFileList(File[] fileList){

        mList = new ArrayList<File>(Arrays.asList(fileList));

        notifyDataSetChanged();

    }

    /**
     * 파일 클릭 이벤트.
     * @param position 클릭한 파일 포지션
     */
    private void clickFile(int position) {

        File f = mList.get(position);

        if (isPngFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_IMAGE, f).execute();
        } else if (isJpgFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_IMAGE, f).execute();
        } else if (isPropFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_TEXT, f).execute();
        } else if (isTxtFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_TEXT, f).execute();
        } else if (isXmlFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_TEXT, f).execute();
        } else if (isJsonFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_TEXT, f).execute();
        } else if (isHwpFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_HWP, f).execute();
        } else if (isPdfFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_PDF, f).execute();
        } else if (isExcelFile(f)) {
            new Async_Show(this, this.mContext, Async_Show.SHOW_VIEW_TYPE_EXCEL, f).execute();
        } else {
            showToast("보기를 지원하지 않는 파일 형식입니다.", true);
        }

    }

    /**
     * png 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : png 확장자의 파일임, false : png 확장자의 파일이 아님
     */
    private boolean isPngFile(File file) {
        return ("png".equals(getFileExtension(file.getName())));
    }

    /**
     * jpg 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : jpg 확장자의 파일임, false : jpg 확장자의 파일이 아님
     */
    private boolean isJpgFile(File file) {
        return ("jpg".equals(getFileExtension(file.getName())));
    }

    /**
     * 프로퍼티 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : 프로퍼티 확장자의 파일임, false : 프로퍼티 확장자의 파일이 아님
     */
    private boolean isPropFile(File file) {
        return ("prop".equals(getFileExtension(file.getName())));
    }

    /**
     * txt 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : txt 확장자의 파일임, false : txt 확장자의 파일이 아님
     */
    private boolean isTxtFile(File file) {
        return ("txt".equals(getFileExtension(file.getName())));
    }

    /**
     * xml 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : xml 확장자의 파일임, false : xml 확장자의 파일이 아님
     */
    private boolean isXmlFile(File file) {
        return ("xml".equals(getFileExtension(file.getName())));
    }

    /**
     * json 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : json 확장자의 파일임, false : json 확장자의 파일이 아님
     */
    private boolean isJsonFile(File file) {
        return ("json".equals(getFileExtension(file.getName())));
    }

    /**
     * hwp 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : json 확장자의 파일임, false : json 확장자의 파일이 아님
     */
    private boolean isHwpFile(File file) {
        return ("hwp".equals(getFileExtension(file.getName())));
    }

    /**
     * pdf 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : json 확장자의 파일임, false : json 확장자의 파일이 아님
     */
    private boolean isPdfFile(File file) {
        return ("pdf".equals(getFileExtension(file.getName())));
    }

    /**
     * xls, xlsx 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : json 확장자의 파일임, false : json 확장자의 파일이 아님
     */
    private boolean isExcelFile(File file) {
        return ("xls".equals(getFileExtension(file.getName())) || "xlsx".equals(getFileExtension(file.getName())));
    }

    /**
     * sqlite 확장자의 파일 여부 반환.
     *
     * @param file 확인 할 파일
     * @return true : gxdb 확장자의 파일임, false : gxdb 확장자의 파일이 아님
     */
    private boolean isSqliteFile(File file) {
        return ("sqlite".equals(getFileExtension(file.getName())));
    }

    /**
     * 롱클릭한 파일이 폴더인지 여부 반환.
     * @param longClickFile 롱클릭한 파일
     * @return true : 롱클릭한 파일이 폴더임, false : 롱클릭한 파일이 파일임
     */
    private boolean isLongClickFolder(File longClickFile){
        return longClickFile.isDirectory();
    }

    /**
     * 폴더 지우기.
     * @param longClickFile 롱클릭한 파일
     */
    private void deleteFolder(File longClickFile){
        delete(longClickFile);
    }

    /**
     * 파일 지우기.
     * @param longClickFile 롱클릭한 파일
     */
    private void deleteFile(File longClickFile){
        delete(longClickFile);
    }

    /**
     * 파일 지우기.
     * @param longClickFile
     */
    private void delete(final File longClickFile){

        final ProgressDialog deleteProgressDialog = new ProgressDialog(mContext);
        deleteProgressDialog.setCancelable(false);
        deleteProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        deleteProgressDialog.setTitle((longClickFile.isDirectory() ? "폴더" : "파일") + " 삭제");
        deleteProgressDialog.setMessage((longClickFile.isDirectory() ? "폴더를" : "파일을") + " 삭제 중 입니다.\n잠시만 기다려 주시기 바랍니다.");

        AsyncTask<Void, Void, Void> asyncDelete = new AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                deleteProgressDialog.show();

            }

            @Override
            protected Void doInBackground(Void... aVoid) {

                longClickFile.delete();

                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                refresh();

                deleteProgressDialog.dismiss();

                showToast("삭제를 완료하였습니다.", false);

            }

        };
        asyncDelete.execute();

    }

    //////////////////////
    // Protected Method //
    //////////////////////
    protected AlertDialog showCustomViewOneButtonDialog(Context context, String title, View view,  String btnMsg, DialogInterface.OnClickListener btnListener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton(btnMsg, btnListener);
        builder.create();
        return builder.show();

    }

    protected AlertDialog showCustomViewTwoButtonDialog(Context context, String title, View view,  String btnMsg, DialogInterface.OnClickListener btnListener, String btnMsg2, DialogInterface.OnClickListener btnListener2){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton(btnMsg, btnListener);
        builder.setNegativeButton(btnMsg2, btnListener2);
        builder.create();
        return builder.show();

    }

    /**
     * 디바이스 가로, 세로 해상도 크기 반환.
     * @param context Context
     * @return int[0] : 가로 픽셀 수
     *         int[1] : 세로 픽셀 수
     */
    protected int[] getDeviceResolutionSize(Context context){

        WindowManager window_manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        Display dis = window_manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        dis.getMetrics(metrics);

        return new int[]{metrics.widthPixels, metrics.heightPixels};

    }

    /**
     * File 객체를 Uri 객체로 변환하여 반환.
     * @param context 안드로이드 버전 Nougat (API LEVEL 24) 이상인 경우만 사용. {@link Context}
     * @param fileProviderAuthority 안드로이드 버전 Nougat (API LEVEL 24) 이상인 경우만 사용. 할 AndroidManifest.xml에 정의한 android:authorities명
     * @param file 변환 할 File 객체
     * @return null : 안드로이드 버전 Nougat (API LEVEL 24) 이상인 경우 인자 값이 null인 경우, 이하인 경우 File 객체가 null인 경우, Uri 객체 : 정상 반환
     */
    protected Uri transFileToUri(Context context, String fileProviderAuthority, File file){

        if(file == null){
            return null;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            if (context == null || fileProviderAuthority == null || fileProviderAuthority.length() <= 0) {
                return null;
            }

            return FileProvider.getUriForFile(context, fileProviderAuthority, file);

        } else {
            return Uri.fromFile(file);
        }

    }

    /**
     * 파일 확장자에 따른 MIME 타입 반환.
     * @param fileExtension '.' 이 없는 파일 확장자
     * @return null : 매칭되는 MIME 타입이 없는 경우, MIME 타입 : 매칭되는 MIME 타입
     */
    protected String getMimeTypeFromFileExtension(String fileExtension){

        String noDotFileExtension = fileExtension;
        if(fileExtension.indexOf(".") != -1){
            noDotFileExtension = fileExtension.trim().substring(0, 1);
        }

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(noDotFileExtension);

    }

    ///////////////////
    // Public Method //
    ///////////////////
    /**
     * 토스트 보여주기.
     * @param msg 보여줄 메세지
     * @param isShowShort 짧게 보여줄지 여부
     */
    public void showToast(String msg, boolean isShowShort){
        Toast.makeText(this.mContext, msg, (isShowShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)).show();
    }

    /**
     * 오류 다이얼로그 보여주기.
     * @param context {@link Context}
     * @param title 보여줄 다이얼로그 제목
     * @param msg 보여줄 메세지
     */
    public void showErrorDialog(Context context, String title, String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.create();
        builder.show();

    }

    /**
     * 파일 확장자 반환.
     * @param file 파일 확장자를 포함한 파일
     * @return null : 파일이 null일 경우, 파일 확장자 : 정상 반환
     */
    public String getFileExtension(String file) {

        if(file == null){
            return null;
        }

        return file.substring(file.lastIndexOf(".") + 1);

    }

    /**
     * 파일 확장자를 제외한 파일명 반환.
     * @param file 파일 확장자를 포함한 파일
     * @return null : 파일이 null일 경우, 파일명 : 정상 반환
     */
    public static String getFileName(String file) {

        if(file == null){
            return null;
        }

        String fileName = file.substring(0, file.lastIndexOf("."));

        return fileName.substring(fileName.lastIndexOf("/") + 1);

    }

    /**
     * 현재 파일 리스트가 보여지고 있는 경로 반환.
     * @return 현재 파일 리스트가 보여지고 있는 경로
     */
    public String getCurrentPath(){
        return this.mCurrentPath;
    }

    /**
     * 현재 경로를 기준으로 파일 리스트 갱신.
     */
    public void refresh(){

        File currentPath = new File(mCurrentPath);
        if(mRootFolderPath.equals(mCurrentPath)){

            mList = new ArrayList<File>(Arrays.asList(currentPath.listFiles()));

        }else{

            File[] tmpFileList = currentPath.listFiles();
            File[] fileList = new File[tmpFileList.length + 1];
            fileList[0] = new File(mCurrentPath + "/...");
            int size = 1;
            for (File data : tmpFileList) {
                fileList[size++] = data;
            }

            mList = new ArrayList<File>(Arrays.asList(fileList));

        }

        notifyDataSetChanged();

    }

    /**
     * 파일 클릭 이벤트.
     * @param position 클릭한 파일 포지션
     */
    public void click(int position){

        if(isClickFolder(mList.get(position))){
            clickFolder(position);
        }else{
            clickFile(position);
        }

    }

    /**
     * 파일 롱클릭 이벤트.
     * @param position 롱클릭한 파일 포지션
     */
    public void longClick(int position){

        final File longClickFile = mList.get(position);

        if("...".equals(longClickFile.getName())){
            return;
        }

        boolean isLongClickFolder = false;
        if(isLongClickFolder(longClickFile)){
            isLongClickFolder = true;
        }
        final boolean finalIsLongClickFolder = isLongClickFolder;

        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setCancelable(false);
        builder.setTitle((finalIsLongClickFolder ? "폴더" : "파일") + " 삭제");
        builder.setMessage("선택한 " + (finalIsLongClickFolder ? "폴더를" : "파일을 ") + "삭제하시겠습니까?");
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                if(finalIsLongClickFolder){
                    deleteFolder(longClickFile);
                }else{
                    deleteFile(longClickFile);
                }

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

    }

}
