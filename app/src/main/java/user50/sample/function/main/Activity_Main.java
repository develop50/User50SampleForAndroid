package user50.sample.function.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import user50.sample.R;
import user50.sample.function.network.Activity_Network;

public class Activity_Main extends Activity implements AdapterView.OnItemClickListener{

    private static final String LOG_TAG = Activity_Main.class.getSimpleName();

    private ListView lv_sample_list;
    private HashMap<String, Class<?>> mLvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initValue();
        initLayout();

    }

    private void doFastTest(){

        // 네비게이션바 높이 구하기
//        Log.w(LOG_TAG, "check has navigationbar : " + String.valueOf(Display_Util.hasNavigationBar(getApplicationContext())));
//        Log.w(LOG_TAG, "check navigationbar height : " + String.valueOf(Display_Util.getNavigationBarHeight(this)));
//        int[] deviceResolution = Display_Util.getDeviceResolutionSize(this);
//        Log.w(LOG_TAG, "check resolution : " + String.valueOf(deviceResolution[0]) + ", " + String.valueOf(deviceResolution[1]));
//        DisplayMetrics screenInfo = Display_Util.getScreenInfo(this);
//        Log.w(LOG_TAG, "check screen info : " + screenInfo.toString());
//        DisplayMetrics realScreenInfo = Display_Util.getRealScreenInfo(this);
//        Log.w(LOG_TAG, "check real screen info : " + realScreenInfo.toString());

        // 앱 삭제 요청 테스트
//        // 설치 된 앱 패키지명 리스트 확인
//        PackageManager pkgMgr = getPackageManager();
//        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> mApps = pkgMgr.queryIntentActivities(mainIntent, 0); // 실행 형태의 패키지만 추출
//
////        Collections.sort(mApps, new ResolveInfo.DisplayNameComparator(pkgMgr)); // 정렬
//        for (ResolveInfo resolveInfo : mApps) {
//
//            ActivityInfo activityInfo = resolveInfo.activityInfo;
//            Log.w(LOG_TAG, "check app package : " + activityInfo.packageName + ", app name : " + activityInfo.loadLabel(pkgMgr).toString());
//
//        }
//
//        // 앱 삭제 요청
//        Uri packageURI = Uri.parse("package:" + null);
//        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(uninstallIntent);

    }

    private void initValue(){

        mLvItem = new HashMap<String, Class<?>>();
//        mLvItem.put("카메라 샘플", Camera_Sample_Activity.class);
//        mLvItem.put("컬러피커 샘플", ColorPicker_Sample_Activity.class);
//        mLvItem.put("프래그먼트 샘플", Fragment_Sample_Activity.class);
//        mLvItem.put("GPS 샘플", Location_Sample_Activity.class);
//        mLvItem.put("권한 설정 샘플", Permission_Sample_Activity.class);
//        mLvItem.put("테스트", Test_Activity_Child.class);
//        mLvItem.put("외부 GPS", ExternalGps_Sample_Activity.class);
//        mLvItem.put("AR 다음맵", ArDaumMap_Activity.class);
//        mLvItem.put("네트워크 상태 확인", NetworkStateCheck_Sample_Activity.class);
//        mLvItem.put("익스펜더블 리스트뷰 샘플", ExpandableListView_Sample_Activity.class);
//        mLvItem.put("리사이클뷰 샘플", RecyclerView_Sample_Activity.class);
//        mLvItem.put("센서 샘플", Sensor_Sample_Activity.class);
//        mLvItem.put("테스트 뷰", TestView_Activity.class);
//        mLvItem.put("하위 레이아웃", Activity_SubLayout.class);
//        mLvItem.put("DB", DB_Sample_Activity.class);
//        mLvItem.put("로그용", Log_Activity.class);
//        mLvItem.put("Tistory 전용", Tistory_Activity.class);
//        mLvItem.put("geo 샘플", Geo_Sample_Activity.class);
//        mLvItem.put("Mock GPS 샘플", MockLocation_Sample_Activity.class);
//        mLvItem.put("서비스 샘플", Service_Sample_Activity.class);
//        mLvItem.put("노티피케이션(알림) 샘플", Notification_Sample_Activity.class);
//        mLvItem.put("데이터 바인딩 샘플", DataBinding_Sample_Activity.class);
//        mLvItem.put("블루투스 샘플", Bluetooth_Sample_Activity.class);
//        mLvItem.put("리스트뷰 샘플", ListView_Sample_Activity.class);
//        mLvItem.put("TTS 샘플", TTS_Sample_Activity.class);
//        mLvItem.put("시간 샘플", Time_Sample_Activity.class);
        mLvItem.put("심플 네트워크 샘플", Activity_Network.class);
//        mLvItem.put("apk 설치 유도 샘플", FileProvider_Sample_Activity.class);
//        mLvItem.put("액티비티 라이프 사이클 따라가는 객체 디자인 패턴", ActivityLifeCycle_Activity.class);
//        mLvItem.put("ConstraintLayout 샘플", ConstraintLayout_Sample_Activity.class);
//        mLvItem.put("전화 걸기, 끊기 샘플", Telephone_Sample_Activity.class);
//        mLvItem.put("zip 샘플", Zip_Sample_Activity.class);
//        mLvItem.put("파일탐색기 샘플", FileExplorer_Sample_Activity.class);
//        mLvItem.put("디바이스 정보보기 샘플", GetDeviceInfo_Activity.class);
//        mLvItem.put("다이얼로그 샘플", Dialog_Sample_Activity.class);
//        mLvItem.put("타이머 샘플", Timer_Sample_Activity.class);
//        mLvItem.put("OpenGL 샘플", OpenGL_Sample_Activity.class);
//        mLvItem.put("웹뷰(WebView) 샘플", Webview_Sample_Activity.class);
//        mLvItem.put("급여명세읽기 샘플", Activity_Salary.class);
//        mLvItem.put("XML 읽기 샘플", XMLRead_Sample_Activity.class);
//        mLvItem.put("그림판 샘플", Paint_Sample_Activity.class);
//        mLvItem.put("트리뷰 리스트뷰 샘플", Treeview_Sample_Activity.class);
//        mLvItem.put("와이파이를 이용한 실내위치 측정 샘플", WifiRtt_Sample_Activity.class);
//        mLvItem.put("암호화/복호화 샘플", Cipher_Sample_Activity.class);
//        mLvItem.put("구글 파이어베이스 Crashlytics 샘플", GoogleCrashlytics_Activity.class);
//        mLvItem.put("갤러리에서 이미지 가져오기 샘플", GalleryImagePick_Sample_Activity.class);
        mLvItem.put("Fast Test Item", null);
//        mLvItem.put("앱 설치, 삭제 리시버 샘플", Telephone_Sample_Activity.class);

    }

    private void initLayout(){

        String[] lvItem = new String[mLvItem.size()];
        int size = 0;
        for (Map.Entry<String, Class<?>> entry : mLvItem.entrySet()) {
            lvItem[size++] = entry.getKey();
        }

        lv_sample_list = (ListView)findViewById(R.id.main_lv_sample_list);
        lv_sample_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvItem));
        lv_sample_list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String selectItem = (String) lv_sample_list.getAdapter().getItem(position);

        Log.w(LOG_TAG, "check select item : " + selectItem);

        if("Fast Test Item".equals(selectItem)){
            doFastTest();
            return;
        }

        Intent i = new Intent(Activity_Main.this, mLvItem.get(selectItem));
        startActivity(i);

    }

}