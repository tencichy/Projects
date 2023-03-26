package com.damiskot.classes;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Id {
  public Integer generate() throws FileNotFoundException {
      Integer newID;
      List<String> result = new ArrayList<>();
      try (Scanner scanner = new Scanner(new File("src/main/resources/id.AppId"))) {
          while (scanner.hasNextLine()) result.add(scanner.nextLine());
      } catch (IOException e) {
          e.printStackTrace();
      }
      newID = Integer.parseInt(result.get(0)) + 1;
          PrintWriter zapis = new PrintWriter("src/main/resources/id.AppId");
          zapis.print(newID);
          zapis.close();
      return newID;
  }
  public void resetId() throws IOException {
      File file = new File("src/main/resources/id.AppId");
      file.delete();
      file.createNewFile();
      PrintWriter zapis = new PrintWriter("src/main/resources/id.AppId");
      zapis.print(-1);
      zapis.close();
  }


}
