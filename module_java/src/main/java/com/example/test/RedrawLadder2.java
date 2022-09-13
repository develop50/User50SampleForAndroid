package com.example.test;

import java.util.Random;

public class RedrawLadder2 {

   Random random = new Random();

   String [] arr = {"│", "├", "──", "┤"};
   int b = 0;


   public void redrawLadder(int a, int height, String[] stuff, String[] name, String[] c) {

      for(int i = 0; i < c.length; i++) {
         System.out.print((i + 1) + ". " + name[i]);
         for(int j = 0; j < 5; j++) {
            System.out.print(" ");
         }
      }

      System.out.println();

      for(int i = 0; i < stuff.length; i++) {
         System.out.print((i + 1));
         for(int j = 0; j < 5; j++) {
            System.out.print(" ");
         }
      }

      System.out.println();

      for(int i = 0; i < a; i++) {
         System.out.print("│");
         for(int j = 0; j < 5; j++) {
            System.out.print(" ");
         }
      }

      System.out.println();
      //초반 1번라인 찍는 로직


      for(int k = 0; k < (height - 2); k++) {
         for(int i = 0; i < a; i++) {

            if(b == 1 && i != 0) {
               b = 3;
            }
            else if(i == a - 1) {
               if(b == 1) {
                  b = 3;
               }
               else {
                  b = 0;
               }
            }
            else {
               b = random.nextInt(2);
            }


            System.out.print(arr[b]);

            if(b == 1 && i != a - 1) {
               for(int j = 0; j < 5; j++) {
                  System.out.print("─");
               }
            }
            else {
               for(int j = 0; j < 5; j++) {
                  System.out.print(" ");
               }
            }

            if(b == 1) {
               c[i] = name[i + 1];
               c[i + 1] = name[i];
            }

            for(int q = 0; q < c.length; q++) {
               name[q] = c[q];
            }

         }

         System.out.println();



      }
      //중간라인 찍는 로직


      for(int i = 0; i < a; i++) {
         System.out.print("│");
         for(int j = 0; j < 5; j++) {
            System.out.print(" ");
         }
      }

      System.out.println();

      for(int i = 0; i < stuff.length; i++) {
         System.out.print((i + 1));
         if(i >= 9) {
            for(int j = 0; j < 4; j++) {
               System.out.print(" ");
            }
         }else {
            for(int j = 0; j < 5; j++) {
               System.out.print(" ");
            }
         }
      }

      System.out.println();

      for(int i = 0; i < stuff.length; i++) {
         System.out.print((i + 1) + ". " + stuff[i]);
         for(int j = 0; j < 5; j++) {
            System.out.print(" ");
         }
      }

      System.out.println();
      System.out.println();
      System.out.println();

      //마지막 라인 찍는 로직
      for(int i = 0; i < c.length; i++) {
         System.out.println();
         System.out.print(c[i] + "님 : " + stuff[i]);
      }
   }
}
