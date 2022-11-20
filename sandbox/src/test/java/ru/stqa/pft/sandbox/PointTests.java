package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistance1() {
    Point p1 = new Point(2, 3);
    Point p2 = new Point(7, 10);

    Assert.assertEquals(p1.distance(p2), 8.602325267042627);
  }

  @Test
  public void testDistance2() {
    Point p1 = new Point(7,4);
    Point p2 = new Point(3,8);

    Assert.assertEquals(p1.distance(p2),5.656854249492381);
  }
}
