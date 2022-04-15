package it.polimi.ingsw.model;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * CloudTile class test
 */
public class CloudTileTest {

  private final int[] testArr = {2, 1, 0, 0, 0};

  /**
   * Test the addStudent Method with normal behaviour
   */
  @Test
  public void testFillCloud() {

    CloudTile cloudTest = new CloudTile(0);
    Assert.assertEquals(Arrays.toString(cloudTest.getStudents()), "[0, 0, 0, 0, 0]");
    cloudTest.fillCloud(testArr);
    Assert.assertEquals(Arrays.toString(cloudTest.getStudents()), "[2, 1, 0, 0, 0]");
  }

  /**
   * Test the emptyCloud Method by removing current students both when Cloud empty and not
   */
  @Test
  public void testEmptyCloud() {

    CloudTile cloudTest = new CloudTile(0);

    cloudTest.emptyCloud();
    Assert.assertEquals(Arrays.toString(cloudTest.getStudents()), "[0, 0, 0, 0, 0]");
    cloudTest.fillCloud(testArr);
    int[] test = cloudTest.emptyCloud();
    Assert.assertEquals(Arrays.toString(test), "[2, 1, 0, 0, 0]");
  }
}
