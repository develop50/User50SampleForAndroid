package user50.sample.function.filemanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import user50.sample.R;

/**
 * 파일 보여주기 스레드.
 */
public class Async_Show extends AsyncTask<Void, Void, Object> {

    //////////////
    // Constant //
    //////////////
    /**
     * 문자열 보여주기 형식.
     */
    protected static final int SHOW_VIEW_TYPE_TEXT = 0;
    /**
     * 문자열 편집 보여주기 형식.
     */
    protected static final int SHOW_VIEW_TYPE_EDIT = 1;
    /**
     * 이미지 보여주기 형식.
     */
    protected static final int SHOW_VIEW_TYPE_IMAGE = 2;
    /**
     * 한글 문서 보여주기 형식.
     */
    protected static final int SHOW_VIEW_TYPE_HWP = 3;
    /**
     * PDF 문서 보여주기 형식.
     */
    protected static final int SHOW_VIEW_TYPE_PDF = 4;
    /**
     * EXCEL 문서 보여주기 형식.
     */
    protected static final int SHOW_VIEW_TYPE_EXCEL = 5;

    ////////////
    // Layout //
    ////////////

    //////////////
    // Variable //
    //////////////
    private Adapter_FileManager mAdapterFileManager;
    private Context mContext;
    private int mViewType;
    private File mShowFile;

    private ProgressDialog mLoadingDialog;

    /**
     * 생성자.
     * @param adapterFileManager {@link Adapter_FileManager}
     * @param context {@link Context}
     * @param viewType {@link #SHOW_VIEW_TYPE_TEXT}, {@link #SHOW_VIEW_TYPE_EDIT}, {@link #SHOW_VIEW_TYPE_IMAGE}, {@link #SHOW_VIEW_TYPE_HWP}, {@link #SHOW_VIEW_TYPE_PDF}, {@link #SHOW_VIEW_TYPE_EXCEL}
     * @param showFile
     */
    public Async_Show(Adapter_FileManager adapterFileManager, Context context, int viewType, File showFile){

        this.mAdapterFileManager = adapterFileManager;
        this.mContext = context;
        this.mViewType = viewType;
        this.mShowFile = showFile;

    }

    ////////////////////
    // Extends Method //
    ////////////////////
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.mLoadingDialog = new ProgressDialog(this.mContext);
        this.mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.mLoadingDialog.setMessage("파일을 읽는 중 입니다.");
        this.mLoadingDialog.show();

    }

    @Override
    protected Object doInBackground(Void... voids) {

        Object result = null;

        switch (this.mViewType){
            case SHOW_VIEW_TYPE_TEXT:

                StringBuilder text = new StringBuilder();

                BufferedReader br = null;
                try {

                    br = new BufferedReader(new FileReader(this.mShowFile));
                    String readLine = "";
                    while (((readLine = br.readLine()) != null)) {
                        text.append(readLine + "\n");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    try {

                        br.close();

                    } catch (Exception e) {
                        // ignore
                    }

                }

                result = text.toString();

                break;
            case SHOW_VIEW_TYPE_EDIT:

                break;
            case SHOW_VIEW_TYPE_IMAGE:

                result = BitmapFactory.decodeFile(this.mShowFile.getPath());

                break;
            case SHOW_VIEW_TYPE_HWP:
            case SHOW_VIEW_TYPE_PDF:
            case SHOW_VIEW_TYPE_EXCEL:

                break;
        }

        return result;

    }

    @Override
    protected void onPostExecute(Object aObject) {
        super.onPostExecute(aObject);

        this.mLoadingDialog.dismiss();

        View v = ((Activity) this.mContext).getLayoutInflater().inflate(R.layout.dialog_filemanager_show, null);
        switch (this.mViewType){
            case SHOW_VIEW_TYPE_TEXT:

                ((ScrollView) v.findViewById(R.id.dialog_filemanager_show_sv_text)).setVisibility(View.VISIBLE);
                ((TextView) v.findViewById(R.id.dialog_filemanager_show_tv_text)).setText((String)aObject);

                this.mAdapterFileManager.showCustomViewOneButtonDialog(mContext, this.mShowFile.getName() + " 파일 보기", v, "닫기", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

                break;
            case SHOW_VIEW_TYPE_EDIT:

                final String targetFilePath = this.mShowFile.getParent();
                final String targetFileFullName = this.mShowFile.getName();
                final String targetFileName = this.mAdapterFileManager.getFileName(targetFileFullName);
                final String targetFileExtension = this.mAdapterFileManager.getFileExtension(targetFileFullName);

                final EditText edit = (EditText) v.findViewById(R.id.dialog_filemanager_show_edit_edit);
                edit.setVisibility(View.VISIBLE);
                edit.setHint("수정 할 파일 이름");
                edit.setText(targetFileName);

                this.mAdapterFileManager.showCustomViewTwoButtonDialog(this.mContext, "이름 수정", v,
                        "수정", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String changeFileName = edit.getText().toString().trim();
                                if (changeFileName == null || changeFileName.length() <= 0) {
                                    Async_Show.this.mAdapterFileManager.showToast("수정 할 파일 이름을 입력해 주시기 바랍니다.", true);
                                    return;
                                }

                                File changeFile = new File(targetFilePath, changeFileName + "." + targetFileExtension);
                                if (!targetFileName.equals(changeFileName) && changeFile.exists()) {
                                    Async_Show.this.mAdapterFileManager.showToast("수정 할 파일 이름이 경로에 이미 존재합니다.\n다른 이름을 입력해 주시기 바랍니다.", true);
                                    return;
                                }

                                boolean isChangeFileNameOk = Async_Show.this.mShowFile.renameTo(changeFile);
                                if (isChangeFileNameOk) {

                                    dialog.dismiss();

                                    Async_Show.this.mAdapterFileManager.showToast("파일 이름을 수정하였습니다.", true);

                                    Async_Show.this.mAdapterFileManager.refresh();

                                } else {
                                    Async_Show.this.mAdapterFileManager.showToast("알 수 없는 이유로 인하여 이름 수정에 실패하였습니다.\n다시 시도해 주시기 바랍니다.", true);
                                }

                            }

                        },
                        "취소", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }

                        });

                break;
            case SHOW_VIEW_TYPE_IMAGE:

                ImageView iv = (ImageView) v.findViewById(R.id.dialog_filemanager_show_iv_image);
                iv.setVisibility(View.VISIBLE);
                iv.setImageBitmap((Bitmap) aObject);

                this.mAdapterFileManager.showCustomViewOneButtonDialog(mContext, this.mShowFile.getName() + " 파일 보기", v, "닫기", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

                break;
            case SHOW_VIEW_TYPE_HWP:
            case SHOW_VIEW_TYPE_PDF:
            case SHOW_VIEW_TYPE_EXCEL:

                try {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(this.mAdapterFileManager.transFileToUri(this.mContext, this.mContext.getPackageName() + ".provider", this.mShowFile));
                    i.setType(this.mAdapterFileManager.getMimeTypeFromFileExtension(this.mAdapterFileManager.getFileExtension(this.mShowFile.getName())));
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    this.mContext.startActivity(i);

                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    this.mAdapterFileManager.showErrorDialog(this.mContext, this.mShowFile.getName() + " 파일 보기 실패", "디바이스내 뷰어 앱을 찾을 수 없습니다.\n뷰어 앱을 설치 후 다시 시도해 주시기 바랍니다.");
                } catch (Exception e) {
                    e.printStackTrace();
                    this.mAdapterFileManager.showErrorDialog(this.mContext, this.mShowFile.getName() + " 파일 보기 오류", "오류로 인하여 파일을 열을 수 없습니다.\n\n오류\n" + e.getMessage() + "\n\n다시 시도해 주시기 바랍니다.");
                    return;
                }

                break;
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

}