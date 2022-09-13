package com.example.test;

public class PeopleNumber {

   int a;
   int height;
   String [] stuff;
   String [] name;
   String [] c;

   public void doProcess() {
      peopleNum();
      peopleName();
      scanStuff();
      scanHeight();
   }

   public void peopleNum() {
      System.out.print("참가 인원 수를 입력하세요 : ");
      a = 5;
      c = new String [a];
   }

   public void peopleName() {
      name = new String[a];
      System.out.print("참가자 이름을 입력하세요 : ");
      for(int i = 0; i < name.length; i++) {
         name[i] = "User" + i;
         c[i] = name[i];
      }
      for(int i = 0; i < name.length; i++) {
         System.out.println("이름 : " + (i + 1) + ". " + name[i]);
      }
   }

   public void scanStuff() {
      stuff = new String [a];
      System.out.print("경품을 입력하세요: ");
      for(int i = 0; i < stuff.length; i++) {
         stuff[i] = "Something" + i;
      }
      for(int i = 0; i < stuff.length; i++) {
         System.out.println("경품 : " + (i + 1) + ". " + stuff[i]);
      }
   }

   public void scanHeight() {
      System.out.print("높이를 설정해주세요 : ");
      height = 10;
   }

}