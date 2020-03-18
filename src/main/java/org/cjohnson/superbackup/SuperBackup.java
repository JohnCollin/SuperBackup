package org.cjohnson.superbackup;

import org.cjohnson.superbackup.server.BackupInternalServer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperBackup {
  
  public static Logger LOGGER = Logger.getLogger(SuperBackup.class.getName());
  
  public static void main(String[] args) {
    System.out.println("Starting Backup Internal Server...");
    BackupInternalServer backupInternalServer = new BackupInternalServer();
    System.out.println("Finished Backup.");
  }
  
}
