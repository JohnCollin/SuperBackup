package org.cjohnson.superbackup.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cjohnson.superbackup.SuperBackup;
import org.cjohnson.superbackup.backup.Backup;
import org.cjohnson.superbackup.backupconfiguration.BackupConfiguration;
import org.cjohnson.superbackup.utility.DateFormatter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackupInternalServer {
  
  public static Logger LOGGER = Logger.getLogger(SuperBackup.class.getName());
  
  private Date serverStartDate;
  
  private GsonBuilder gsonBuilder;
  private Gson gson;
  
  public BackupInternalServer() {
    this.serverStartDate = new Date();
    
    this.gsonBuilder = new GsonBuilder();
    this.gsonBuilder.setPrettyPrinting();
    
    this.gson = gsonBuilder.create();
  
    System.out.println("Intializing Server...");
    
    String formatted = DateFormatter.formatDate(serverStartDate);
  
    System.out.println("Loading Configuration from File: \"backup-configuration.json\"...");
    String inputJsonString = null;
    try {
      inputJsonString = getJsonFromConfiguration();
    } catch (IOException e) {
      System.out.println("Internal Error While Loading File \"backup-configuration.json\".");
      e.printStackTrace();
      System.exit(1);
    }
  
    System.out.println("Loaded Backup Configuration Successfully.");
    BackupConfiguration backupConfiguration = gson.fromJson(inputJsonString, BackupConfiguration.class);
    
    String path = MessageFormat.format(backupConfiguration.getBackupRelocationRelativeDirectory(), formatted);
    System.out.println("Backups will be dumped to path \"" + path + "\" this execution.");
  
    System.out.println("Performing Backup Operation...");
    Backup backup = new Backup(serverStartDate,
      backupConfiguration.getSourceBackupDirectoriesRawPaths(),
      path);
    try {
      backup.perform();
    } catch (IOException e) {
      System.out.println("Internal Error While Backing Up Files.");
      e.printStackTrace();
      System.exit(1);
    }
  
    System.out.println("Completed Backup Operation Successfully.");
  
    System.out.println("Generating Full Backup Summary...");
    String jsonString = gson.toJson(backup);
  
    System.out.println("Writing JSON Summary to File...");
    try {
      publishJsonToFile(path, jsonString);
    } catch (IOException e) {
      System.out.println("Internal Error While Writing JSON Summary to File.");
      e.printStackTrace();
      System.exit(1);
    }
  }
  
  private String getJsonFromConfiguration() throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get("backup-configuration.json"));
    return new String(encoded, StandardCharsets.UTF_8);
  }
  
  public void publishJsonToFile(String path, String jsonString) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(path + "recent-output.json"));
    writer.write(jsonString);
    writer.close();
  }
  
}
