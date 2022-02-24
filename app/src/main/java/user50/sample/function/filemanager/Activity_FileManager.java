package user50.sample.function.filemanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import user50.sample.R;

/**
 * 파일탐색기 샘플 액티비티.
 */
public class Activity_FileManager extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    //////////////
    // Constant //
    //////////////
    /**
     * 로그 태그
     */
    private static final String LOG_TAG = Activity_FileManager.class.getSimpleName();

    ////////////
    // Layout //
    ////////////
    private TextView tv_current_path;
    private ListView lv_file_explorer;
    private Adapter_FileManager mAdapter;

    //////////////
    // Variable //
    //////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filemanager);

        initValue();
        initLayout();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdapter.refresh();

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

        tv_current_path = (TextView)findViewById(R.id.filemanager_tv_current_path);
        lv_file_explorer = (ListView)findViewById(R.id.filemanager_lv_file_explorer);

        mAdapter = new Adapter_FileManager(this);
        tv_current_path.setText(mAdapter.getCurrentPath());
        lv_file_explorer.setAdapter(mAdapter);
        lv_file_explorer.setOnItemClickListener(this);
        lv_file_explorer.setOnItemLongClickListener(this);

    }

    /////////////
    // Extends //
    /////////////

    ////////////////
    // Implements //
    ////////////////
    /////////////////////////////////////
    // AdapterView.OnItemClickListener //
    /////////////////////////////////////
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mAdapter.click(position);
        tv_current_path.setText(mAdapter.getCurrentPath());

    }

    /////////////////////////////////////////
    // AdapterView.OnItemLongClickListener //
    /////////////////////////////////////////
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        mAdapter.longClick(position);

        return true;

    }

}
