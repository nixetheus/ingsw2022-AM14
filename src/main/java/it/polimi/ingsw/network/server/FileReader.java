package it.polimi.ingsw.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * FileReader Class is created to allow the jar
 * to correctly read the files into the resources
 */
public class FileReader {

  /**
   *
   * @param filename path of file
   * @return absolute path that works with jar
   * @throws IOException file not found
   */
  private static String readFromJARFile(String filename)
      throws IOException
  {
    InputStream is = FileReader.class.getResourceAsStream(filename);
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    StringBuffer sb = new StringBuffer();
    String line;
    while ((line = br.readLine()) != null)
    {
      sb.append(line);
    }
    br.close();
    isr.close();
    is.close();
    return sb.toString();
  }

  public static String getPath(String path) throws IOException {
    return readFromJARFile(path);
  }

}





