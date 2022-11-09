package ru.stqa.pft.sandbox;

public class MyFirstProgram {
  public static void main (String[] args) {

    Point p1 = new Point(2,3);
    Point p2 = new Point(7,10);
    System.out.println("Расстояние между точками (" + p1.x + ";" + p1.y + ") и (" + p2.x + ";" + p2.y + ") = " + p1.distance(p2));
  }
}