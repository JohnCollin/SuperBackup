package org.cjohnson.superbackup.backupconfiguration;

import java.util.LinkedList;

public class BackupConfiguration {
  
  LinkedList<String> sourceBackupDirectoriesRawPaths;
  
  String backupRelocationRelativeDirectory;
  
  public BackupConfiguration(LinkedList<String> sourceBackupDirectoriesRawPaths, String backupRelocationRelativeDirectory) {
    this.sourceBackupDirectoriesRawPaths = sourceBackupDirectoriesRawPaths;
    this.backupRelocationRelativeDirectory = backupRelocationRelativeDirectory;
  }
  
  public LinkedList<String> getSourceBackupDirectoriesRawPaths() {
    return sourceBackupDirectoriesRawPaths;
  }
  
  public void setSourceBackupDirectoriesRawPaths(LinkedList<String> sourceBackupDirectoriesRawPaths) {
    this.sourceBackupDirectoriesRawPaths = sourceBackupDirectoriesRawPaths;
  }
  
  public String getBackupRelocationRelativeDirectory() {
    return backupRelocationRelativeDirectory;
  }
  
  public void setBackupRelocationRelativeDirectory(String backupRelocationRelativeDirectory) {
    this.backupRelocationRelativeDirectory = backupRelocationRelativeDirectory;
  }
}
