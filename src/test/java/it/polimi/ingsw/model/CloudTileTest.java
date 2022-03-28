package it.polimi.ingsw.model;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * CloudTile class test
 */
public class CloudTileTest {

  private final CloudTile cloudTest = new CloudTile(0);

  /**
   * Test the addStudent Method
   */
  @Test
  public void testAddStudent(){
    Assert.assertEquals(Arrays.toString(cloudTest.getStudents()), "[0, 0, 0, 0, 0]");
    cloudTest.addStudent(0);
    cloudTest.addStudent(0);
    cloudTest.addStudent(1);
    Assert.assertEquals(Arrays.toString(cloudTest.getStudents()), "[2, 1, 0, 0, 0]");
  }

  /**
   * Test the emptyCloud Method
   */
  @Test
  public void testEmptyCloud() {
    cloudTest.emptyCloud();
    Assert.assertEquals(Arrays.toString(cloudTest.getStudents()), "[0, 0, 0, 0, 0]");
    cloudTest.addStudent(0);
    int[] test = cloudTest.emptyCloud();
    Assert.assertEquals(Arrays.toString(test), "[1, 0, 0, 0, 0]");
  }
}
