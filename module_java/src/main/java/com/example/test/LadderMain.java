package com.example.test;

import java.util.Scanner;

public class LadderMain {

    Scanner scan = new Scanner(System.in);
    PeopleNumber pn = new PeopleNumber();
    DrawLadder dl = new DrawLadder();
    RedrawLadder2 rl2 = new RedrawLadder2();

    public LadderMain() {
        doProcess();
    }

    public void doProcess() {
        //참가자 인원을 받는다(경품의 수를 정한다.)
        pn.doProcess();
        //사다리를 그린다.
        dl.drawLadder(pn.a, pn.height, pn.stuff);

        //콘솔창을 초기화한다.
        nullPoint();
        rl2.redrawLadder(pn.a, pn.height, pn.stuff, pn.name, pn.c);
    }

    public void nullPoint() {
        for(int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

}