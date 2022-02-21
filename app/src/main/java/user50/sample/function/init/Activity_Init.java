package user50.sample.function.init;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.security.MessageDigest;

import user50.sample.function.main.Activity_Main;
import user50.sample.BuildConfig;
import user50.sample.R;

/**
 * 앱을 실행하기 위한 초기 준비 작업 액티비티.
 */
public class Activity_Init extends Activity implements View.OnClickListener {

    //////////////
    // Constant //
    //////////////
    /**
     * 로그 태그
     */
    private static final String LOG_TAG = Activity_Init.class.getSimpleName();

    ////////////
    // Layout //
    ////////////
    private ScrollView sv_detail;
    private TextView tv_detail;
    private Button btn_next;

    //////////////
    // Variable //
    //////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

//        printAppKeyHash();

        initValue();
        initLayout();

        new AsyncTask_Init(Activity_Init.this).execute();

    }

    /**
     * 초기 값 설정.
     */
    private void initValue() {

    }

    /**
     * 초기 레이아웃 설정.
     */
    private void initLayout() {

        sv_detail = (ScrollView) findViewById(R.id.init_sv_detail);
        tv_detail = (TextView) findViewById(R.id.init_tv_detail);
        btn_next = (Button) findViewById(R.id.init_btn_next);

        btn_next.setTag(false);

        btn_next.setOnClickListener(this);

    }

    ////////////////////
    // Extends Method //
    ////////////////////
    @Override
    public void onBackPressed() {

    }

    ///////////////////////
    // Implements Method //
    ///////////////////////
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.init_btn_next:

                boolean tag = (boolean) v.getTag();
                if (tag) {
                    finish();
                } else {
                    next();
                }

                break;
        }

    }

    ////////////////////
    // Private Method //
    ////////////////////
    private void printAppKeyHash() {

        try {

            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.i(LOG_TAG, "APPLICATION KEY HASH : " + new String(Base64.encode(md.digest(), 0)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //////////////////////
    // Protected Method //
    //////////////////////

    ///////////////////
    // Public Method //
    ///////////////////
    public void next() {

        Intent i = new Intent(Activity_Init.this, Activity_Main.class);
        startActivity(i);

        finish();

    }

    /**
     * 초기화 진행 상황 상세 메시지 설정.
     *
     * @param detailMessage 진행 상황 상세 메시지
     */
    public void setDetailMessage(String detailMessage) {

        if (detailMessage == null) {
            return;
        }

        String oldDetailMessage = tv_detail.getText().toString().trim();
        String newDetailMessage = ((oldDetailMessage == null || oldDetailMessage.length() <= 0) ? "" : "\n") + detailMessage;

        SpannableStringBuilder builder = new SpannableStringBuilder(newDetailMessage);
        builder.setSpan(new ForegroundColorSpan(Color.WHITE), 0, newDetailMessage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_detail.append(builder);

    }

    /**
     * 초기화 진행 상황 상세 메시지 설정.
     *
     * @param detailMessage 진행 상황 상세 메시지
     * @param messageColor  메시지 표출 색상
     */
    public void setDetailMessage(String detailMessage, int messageColor) {

        if (detailMessage == null) {
            return;
        }

        if (!BuildConfig.DEBUG && messageColor == Color.GREEN) {
            return;
        }

        String newDetailMessage = "\n" + detailMessage;

        SpannableStringBuilder builder = new SpannableStringBuilder(newDetailMessage);
        builder.setSpan(new ForegroundColorSpan(messageColor), 0, newDetailMessage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_detail.append(builder);

    }


    /**
     * 초기화 진행 완료시 다음 화면으로 이동 버튼 활성화.
     */
    public void setNextViewEnabled() {
        btn_next.setEnabled(true);
    }

    /**
     * 오류 다이얼로그 보여주기.
     *
     * @param errorMsg 오류 메세지
     */
    public void showErrorDialog(String errorMsg) {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("오류");
        ab.setMessage(errorMsg);
        ab.setNegativeButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                finish();

            }

        });
        ab.create();
        ab.show();

    }

}
