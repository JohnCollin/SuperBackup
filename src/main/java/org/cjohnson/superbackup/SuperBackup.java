package org.cjohnson.superbackup;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuperBackup {
  
  public static void main(String[] args) {
    Date date = new Date();
    String dateAsString = date.toString();
  
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy-HH-mm");
    String formatted = simpleDateFormat.format(date);
    
    File sourceWorldDirectory = new File("world");
    File destinationWorldDirectory = new File("backups/" + formatted + "/world");
    File sourceNetherDirectory = new File("world_nether");
    File destinationNetherDirectory = new File("backups/" + formatted + "/world_nether");
    File sourceEndDirectory = new File("world_the_end");
    File destinationEndDirectory = new File("backups/" + formatted + "/world_the_end");
  
    try {
      FileUtils.copyDirectory(sourceWorldDirectory, destinationWorldDirectory);
      FileUtils.copyDirectory(sourceNetherDirectory, destinationNetherDirectory);
      FileUtils.copyDirectory(sourceEndDirectory, destinationEndDirectory);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
