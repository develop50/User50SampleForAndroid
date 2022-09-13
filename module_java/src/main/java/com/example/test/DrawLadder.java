package com.example.test;

public class DrawLadder {

   public void drawLadder(int a, int height, String[] stuff) {
      for(int i = 0; i < height; i++) {
         for(int j = 0; j < a; j++) {
            for(int k = 0; k < 5; k++) {
               System.out.print(" ");
            }
            System.out.print("│");
         }
         System.out.println();
      }
      for(int i = 0; i < a; i++) {
         for(int j = 0; j < 2; j++) {
            System.out.print(" ");
         }
         System.out.print(stuff[i]);
      }
   }

}
