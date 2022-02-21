package user50.sample.function.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import user50.sample.R;

public class Adapter_Network extends BaseAdapter{

	/** 로그 태그 */
	private static final String LOG_TAG = Adapter_Network.class.getSimpleName();

	private Context mContext;
	private ArrayList<Pair<String, String>> mItemList;

	/**
	 * 생성자.
	 * @param context
	 */
	public Adapter_Network(Context context) {

		this.mContext = context;
		this.mItemList = new ArrayList<Pair<String, String>>();
//		this.mItemList.add(new Pair<String, String>("param1", "abcd"));
//		this.mItemList.add(new Pair<String, String>("param2", "가나다라"));
//		this.mItemList.add(new Pair<String, String>("param3", "1234"));
//		this.mItemList.add(new Pair<String, String>("command_id", "AA1234567890"));
//		this.mItemList.add(new Pair<String, String>("svcXml", "[{\"command_id\":\"AA1234567890\",\"car_num\":\"22나2222\",\"phone_num\":\"01093526891\",\"lat\":\"37.896105\",\"lon\":\"127.727568\",\"car_state\":\"0\",\"center_id\":\"4200504\",\"center_nm\":\"테스트센터2\",\"car_typ\":\"기타()\",\"nocbl_nm\":\"테스트호출명2\",\"dspord\":\"2\"}]"));
//		this.mItemList.add(new Pair<String, String>("command_id", "AA1234567890"));
//		this.mItemList.add(new Pair<String, String>("car_num", "22나2222"));
//		this.mItemList.add(new Pair<String, String>("phone_num", "01093526891"));
//		this.mItemList.add(new Pair<String, String>("svcXml", "{\"Param\":[{\"CAR_ID\":\"1000001009\",\"ADJ_WARD_ID\":\"4200503\"}],\"ServiceNm\":\"SQL.CCC.NGAVL_VL01001\"}"));

	}

	@Override
	public int getCount() {
		return this.mItemList.size();
	}

	@Override
	public Pair<String, String> getItem(int position) {
		return this.mItemList.get(position);
	}

	public Pair<String, String> getItem(String key) {

        for (Pair<String, String> data : mItemList) {

            if (key.equals(data.first)) {
                return data;
            }

        }

        return null;

    }

	public ArrayList<Pair<String, String>> getItemList(){
	    return this.mItemList;
    }

    public HashMap<String, String> getItemListToHashMap(){

	    HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Pair<String, String> data : mItemList) {
            hashMap.put(data.first, data.second);
        }

        return hashMap;

    }

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_network_parameter, null);
		}

		TextView tv_key = (TextView)convertView.findViewById(R.id.item_network_parameter_tv_key);
		TextView tv_value = (TextView)convertView.findViewById(R.id.item_network_parameter_tv_value);
        Button btn_delete = (Button)convertView.findViewById(R.id.item_network_parameter_btn_delete);
        Button btn_modify = (Button)convertView.findViewById(R.id.item_network_parameter_btn_modify);

        Pair<String, String> parameter = getItem(position);

		tv_key.setText(parameter.first);
		tv_value.setText(parameter.second);
		btn_delete.setTag(position);
        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final int deletePosition = (int) v.getTag();

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setTitle("파라미터 삭제");
                builder.setMessage("선택한 파라미터를 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        remove(deletePosition);

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

        });
        btn_modify.setTag(parameter);
        btn_modify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Pair<String, String> modifyParameter = (Pair<String, String>) v.getTag();

                View dialogView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.dialog_network_parameter, null);
                final EditText edit_key = (EditText)dialogView.findViewById(R.id.dialog_network_parameter_edit_key);
                final EditText edit_value = (EditText)dialogView.findViewById(R.id.dialog_network_parameter_edit_value);

                edit_key.setText(modifyParameter.first);
                edit_value.setText(modifyParameter.second);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setTitle("파라미터 수정");
                builder.setView(dialogView);
                builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String modifyKey = edit_key.getText().toString().trim();
                        String modifyValue = edit_value.getText().toString().trim();
                        if(modifyKey == null || modifyKey.length() <= 0){
                            Toast.makeText(mContext, "키 값이 없는 상태로 수정을 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(!modifyParameter.first.equals(modifyKey)){

                            Pair<String, String> parameter = getItem(modifyKey);
                            if(parameter != null){
                                Toast.makeText(mContext, "이미 존재하는 키 값으로 수정 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }

                        dialog.dismiss();

                        int findIndex = -1;
                        for (int i = 0; i < mItemList.size(); i++) {

                            if(mItemList.get(i).first.equals(modifyParameter.first)){
                                findIndex = i;
                                break;
                            }

                        }

                        if(findIndex != -1){
                            mItemList.set(findIndex, new Pair<String, String>(modifyKey, modifyValue));
                        }

                        notifyDataSetChanged();

                        Toast.makeText(mContext, "파라미터를 수정하였습니다.", Toast.LENGTH_SHORT).show();

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

        });

        convertView.setTag("convertview tag" + String.valueOf(position));

		return convertView;
		
	}

	public void add(String key, String value){

	    if(key == null || key.length() <= 0){
            Log.w(LOG_TAG, "Can not add parameter. Cause parameter key is null.");
	        return;
        }

        this.mItemList.add(new Pair<String, String>(key, value));

	    notifyDataSetChanged();

    }

	public void add(Pair<String, String> parameter){

		if(parameter == null){
			Log.w(LOG_TAG, "Can not add parameter. Cause parameter is null.");
			return;
		}

		this.mItemList.add(parameter);

		notifyDataSetChanged();

	}

	public void update(String key, String value){

        if(key == null || key.length() <= 0){
            Log.w(LOG_TAG, "Can not update parameter. Cause key is null.");
            return;
        }

        int findIndex = -1;
        for (int i = 0; i < this.mItemList.size(); i++) {

            if(this.mItemList.get(i).first.equals(key)){
                findIndex = i;
                break;
            }

        }

        if(findIndex != -1){
            this.mItemList.set(findIndex, new Pair<String, String>(key, value));
        }

        notifyDataSetChanged();

    }

	public void remove(int position){

		if(position < 0 || position >= this.mItemList.size()){
			Log.w(LOG_TAG, "Can not remove parameter. Cause remove position is not valid.");
			return;
		}

		this.mItemList.remove(position);

		notifyDataSetChanged();

	}

	public void clear(){

		this.mItemList = new ArrayList<Pair<String, String>>();

		notifyDataSetChanged();

	}
	
}
