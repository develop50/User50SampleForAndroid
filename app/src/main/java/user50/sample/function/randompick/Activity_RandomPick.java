package user50.sample.function.randompick;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

import user50.sample.R;

public class Activity_RandomPick extends Activity implements View.OnClickListener {

    //////////////
    // Constant //
    //////////////
    /**
     * 로그 태그
     */
    private static final String LOG_TAG = Activity_RandomPick.class.getSimpleName();

    ////////////
    // Layout //
    ////////////
    private Spinner spi_pick_type;
    private TextView tv_pick_list;
    private Button btn_pick;
    private LinearLayout linear_pick_option;

    //////////////
    // Variable //
    //////////////
    public final int DRAW_TYPE_RANDOM = 0;
    public final int DRAW_TYPE_COUNT = 1;
    public final int DRAW_TYPE_GHOST_LEG = 2;

    public final String[] SHAPE_ARR = {"│", "──", "┤", "├"};
    public final int STANDARD_LINE = 1;
    public final int BRIDGE_LINE = 3;
    public final int FINAL_LINE = STANDARD_LINE + BRIDGE_LINE;
    public final int HEIGHT = 8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randompick);

        initValue();
        initLayout();

    }

    /**
     * 초기 값 설정.
     */
    private void initValue() {

        String a = "" +
                "{\n" +
                "    \"RESTAURANT_INFO\": [\n" +
                "        {\n" +
                "            \"RESTAURANT_NAME\": \"SOME NAME\",\n" +
                "            \"RESTAURANT_DATE\": \"월~금,11:00~20:00\",\n" +
                "            \"RESTAURANT_TYPE\": \"한식, 중식, 일식, 양식\",\n" +
                "            \"RESTAURANT_MENU\": [\n" +
                "                {\n" +
                "                    \"NAME\": \"MENU1\",\n" +
                "                    \"PRICE\": 10000\n" +
                "                },\n" +
                "                {\n" +
                "                    \"NAME\": \"MENU2\",\n" +
                "                    \"PRICE\": 120000\n" +
                "                }\n" +
                "            ],\n" +
                "            \"LOCATION\": {\n" +
                "                \"LONGITUDE\": 127.123456,\n" +
                "                \"LATITUDE\": 38.123456,\n" +
                "                \"FLOOR\": \"1\",\n" +
                "                \"ADDRESS_JIBUN\": \"\",\n" +
                "                \"ADDRESS_ROAD\": \"\",\n" +
                "                \"KAKAO\": {\n" +
                "                    \"URL\": \"\",\n" +
                "                    \"RATING\": 3.1\n" +
                "                },\n" +
                "                \"NAVER\": {\n" +
                "                    \"URL\": \"\",\n" +
                "                    \"RATING\": 3.1\n" +
                "                },\n" +
                "                \"GOOGLE\": {\n" +
                "                    \"URL\": \"\",\n" +
                "                    \"RATING\": 3.1\n" +
                "                }\n" +
                "            },\n" +
                "            \"REVIEW\": [\n" +
                "                {\n" +
                "                    \"DATE\": \"22-08-02\",\n" +
                "                    \"URL\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"PERSONAL_OPINION\": \"사견\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

    }

    /**
     * 초기 레이아웃 설정.
     */
    private void initLayout() {

        spi_pick_type = (Spinner) findViewById(R.id.randompick_spi_pick_type);
        tv_pick_list = (TextView) findViewById(R.id.randompick_tv_pick_list);
        btn_pick = (Button) findViewById(R.id.randompick_btn_pick);
        linear_pick_option = (LinearLayout) findViewById(R.id.randompick_linear_pick_option);

        spi_pick_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"랜덤 뽑기", "가장 많이 나온 랜덤 뽑기", "사다리 뽑기"}));

        btn_pick.setOnClickListener(this);

    }

    ////////////////////
    // Extends Method //
    ////////////////////
    @Override
    public void onBackPressed() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    ///////////////////////
    // Implements Method //
    ///////////////////////
    //////////////////////////
    // View.OnClickListener //
    //////////////////////////
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.randompick_btn_pick:

                String inputDrawItemString = tv_pick_list.getText().toString();
                if (inputDrawItemString == null || inputDrawItemString.length() <= 0) {
                    printMessage(2, "뽑기 목록을 입력하지 않으면 사용 할 수 없습니다.");
                    break;
                }
                String[] drawItems = inputDrawItemString.split(",");

                switch (spi_pick_type.getSelectedItemPosition()) {
                    case DRAW_TYPE_RANDOM:

                        printMessage(1, "당첨 : " + drawItems[new Random().nextInt(drawItems.length)].trim());

                        break;
                    case DRAW_TYPE_COUNT:

//                            printMessage(1, "랜덤을 돌릴 수를 입력해 주시기 바랍니다.");
                        // TODO
                        // 동적 추가 옵션 View 생성 및 추가
//                            linear_pick_option.addView();

                        break;
                    case DRAW_TYPE_GHOST_LEG:

//                            printMessage(1, "사다리를 탈 수를 입력해 주시기 바랍니다.");
                        // TODO
                        // 동적 추가 옵션 View 생성 및 추가
//                            linear_pick_option.addView();

                        String[][] sadariField = new String[HEIGHT][drawItems.length * FINAL_LINE];
                        String[] user = new String[drawItems.length];

                        setField(sadariField, drawItems.length);
//                        setInput(user, "플레이어");
//                        setInput(result, "결과");
                        setBridge(sadariField);
                        result(sadariField, user, drawItems);

                        break;
                    default:
                        printMessage(2, "알 수 없는 뽑기 방식입니다.");
                        break;
                }

                break;
        }

    }

    ////////////////////
    // Private Method //
    ////////////////////

    /**
     * 알림 메세지 출력.
     *
     * @param messageType 알림 메세지 타입. 1 : 일반, 2 : 오류
     * @param message     알림 메세지
     */
    private void printMessage(int messageType, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(messageType == 1 ? "알림" : "오류");
        builder.setMessage(message);
        builder.create();
        builder.show();

    }

    private void setField(String[][] arr, int num) {

        for (int q = 0; q < HEIGHT; q++) {

            for (int w = 0; w < FINAL_LINE * num; w++) {

                if (w % 4 == STANDARD_LINE - 1) {

                    arr[q][w] = SHAPE_ARR[0];
                    System.out.print(SHAPE_ARR[0]);

                } else {

                    arr[q][w] = "  ";
                    System.out.print("  ");

                }

            }

            System.out.println();

        }

    }

    private void setBridge(String[][] arr) {

        int lineNumber = arr[0].length / FINAL_LINE;
        int[] checkLine = new int[lineNumber];

        for (int q = 1; q < arr.length - 1; q++) {

            for (int w = 0; w < lineNumber - 1; w++) {

                if ((int) (Math.random() * 2) == 0) {
                    checkLine[w] += checkLine[w] < 3 ? setBridgeDetail(arr, q, w * FINAL_LINE) : 0;
                }

            }

            if (q == arr.length - 2) {

                for (int w = 0; w < lineNumber - 1; w++) {

                    if (checkLine[w] == 0) {
                        q = 1;
                        break;
                    }

                }

            }

        }

    }

    private int setBridgeDetail(String[][] sadari, int q, int w) {

        if (sadari[q][w].equals(SHAPE_ARR[0]) && sadari[q][w + FINAL_LINE].equals(SHAPE_ARR[0])) {

            sadari[q][w] = SHAPE_ARR[3];
            for (int e = 1; e < FINAL_LINE + 1; e++) {

                if (e < FINAL_LINE) {
                    sadari[q][w + e] = SHAPE_ARR[1];
                } else {
                    sadari[q][w + e] = SHAPE_ARR[2];
                }

            }

            return 1;

        }

        return 0;

    }

    private void result(String[][] arr, String[] user, String[] result) {

        realTimeScreenDraw(arr, "");
        String tmp = "";

        for (int q = 0; q < arr[0].length; q += FINAL_LINE) {

            int x = 0;
            int y = q;
            for (int w = 0; w < arr.length; w++) {

                if (arr[x][y].equals(SHAPE_ARR[3])) {
                    y += FINAL_LINE;
                } else if (arr[x][y].equals(SHAPE_ARR[2])) {
                    y -= FINAL_LINE;
                }

                //"♥"이동 로직
                tmp = arr[x][y];
                arr[x][y] = "♥";
                realTimeScreenDraw(arr, user[q / FINAL_LINE]);
                waitTime(250);
                arr[x][y] = tmp;
                x++;

            }
            System.out.println(user[q / FINAL_LINE] + "의 결과는 " + result[y / FINAL_LINE]);
            user[q / FINAL_LINE] += "의 결과 -> " + result[y / FINAL_LINE];
            waitTime(1500);

        }
        blank();

        System.out.println("────── 결과창 ──────");
        for (String temp : user) {
            System.out.printf("%s %n", temp);
        }

    }

    private void realTimeScreenDraw(String[][] arr, String user) {

        blank();
        System.out.println("현재 유저 -> " + user);

        for (int q = 0; q < arr.length; q++) {

            for (int w = 0; w < arr[q].length; w++) {
                System.out.print(arr[q][w]);
            }

            System.out.println();

        }

    }

    private void waitTime(int waitNum) {

        try {
            Thread.sleep(waitNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void blank() {
        System.out.printf("%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n");
    }

    //////////////////////
    // Protected Method //
    //////////////////////

    ///////////////////
    // Public Method //
    ///////////////////

}