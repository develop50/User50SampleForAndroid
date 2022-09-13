package com.example;

import com.example.test.LadderMain;

public class MyClass {
    
    public static void main(String[] args){
        System.out.println("hello java");

//        new MyClass();
        new LadderMain();
    }

    public final String[] SHAPE_ARR = {"│", "──", "┤", "├"};
    public final int STANDARD_LINE = 1;
    public final int BRIDGE_LINE = 3;
    public final int FINAL_LINE = STANDARD_LINE + BRIDGE_LINE;
    public final int HEIGHT = 8;

    public MyClass(){

//        String[] drawItems = {"something1", "something2", "something3", "something4", "something5"};
//
//        String[][] sadariField = new String[HEIGHT][drawItems.length * FINAL_LINE];
//        String[] user = new String[drawItems.length];
//        for (int i = 0; i < drawItems.length; i++) {
//            user[i] = "user" + i;
//        }
//
//        setField(sadariField, drawItems.length);
////                        setInput(user, "플레이어");
////                        setInput(result, "결과");
//        setBridge(sadariField);
//        result(sadariField, user, drawItems);



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
                    if(q == 0) {
                        realTimeScreenDraw(arr, user[q / FINAL_LINE]);
                        waitTime(250);
                    }
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


}