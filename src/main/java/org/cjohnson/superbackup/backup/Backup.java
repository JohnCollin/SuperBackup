package org.cjohnson.superbackup.backup;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Backup {
  
  private static IOFileFilter DEFAULT_FILE_FILTER = TrueFileFilter.TRUE;
  
  private Date dateOfBackup;
  
  private List<String> sourceBackupDirectoriesRawPaths;
  private List<String> destinationBackupDirectoriesRawPaths;
  
  private List<File> sourceBackupDirectories;
  private List<File> destinationBackupDirectories;
  
  private List<File> allFiles;
  
  public Backup(Date dateOfBackup, LinkedList<String> sourceBackupDirectoriesRawPaths, String backupRelocationRelativeDirectory) {
    // Date of Initialization Could Potentially be Different
    // than Date of Execution.
    this.dateOfBackup = dateOfBackup;
    
    this.sourceBackupDirectoriesRawPaths = sourceBackupDirectoriesRawPaths;
    
    // Take The Backup Relocation Relative Directory and modify all from source
    this.destinationBackupDirectoriesRawPaths = new LinkedList<>();
    for(String sourceBackupDirectoryRawPath : sourceBackupDirectoriesRawPaths) {
      destinationBackupDirectoriesRawPaths.add(backupRelocationRelativeDirectory + sourceBackupDirectoryRawPath);
    }
    
    // Convert String Raw Paths to File Objects for Copy
    convertStringPathsToFileObjectsForCopy(sourceBackupDirectoriesRawPaths, (LinkedList<String>) destinationBackupDirectoriesRawPaths);
  
    // Get all subdirectory files that are being backed up
    // for logging reasons.
    this.allFiles = new LinkedList<>();
    assembleBackupFileList(sourceBackupDirectoriesRawPaths);
  }
  
  private void convertStringPathsToFileObjectsForCopy(LinkedList<String> sourceBackupDirectoriesRawPaths, LinkedList<String> destinationBackupDirectoriesRawPaths) {
    this.sourceBackupDirectories = new LinkedList<>();
    for (String sourceBackupDirectoriesRawPath : sourceBackupDirectoriesRawPaths) {
      sourceBackupDirectories.add(new File(sourceBackupDirectoriesRawPath));
    }
    
    this.destinationBackupDirectories = new LinkedList<>();
    for (String destinationBackupDirectoriesRawPath : destinationBackupDirectoriesRawPaths) {
      destinationBackupDirectories.add(new File(destinationBackupDirectoriesRawPath));
    }
  }
  
  private void assembleBackupFileList(LinkedList<String> sourceBackupDirectoriesRawPaths) {
    for(String backupDirectory : sourceBackupDirectoriesRawPaths) {
      // Create the directory file object
      File directoryToBackup = new File(backupDirectory);
      
      // Search the directory for all subdirectories and
      // files using the Apache Commons IO FileUtils Utility.
      Collection<File> collectionOfFiles = FileUtils.listFiles(directoryToBackup,
        DEFAULT_FILE_FILTER,
        DEFAULT_FILE_FILTER);
      
      // Cast Collection to its Specific Type (LinkedList)
      LinkedList<File> listOfFiles = (LinkedList<File>) collectionOfFiles;
      
      // Convert LinkedList into Array for Copying
      File[] arrayOfFiles = new File[listOfFiles.size()];
      arrayOfFiles = listOfFiles.toArray(arrayOfFiles);
      
      // Copy all files into the collectionOfBackupFiles LinkedList.
      Collections.addAll(allFiles, arrayOfFiles);
    }
  }
  
  public void perform() throws IOException {
    for(int copyIndex = 0; copyIndex < sourceBackupDirectories.size(); copyIndex++) {
      FileUtils.copyDirectory(sourceBackupDirectories.get(copyIndex), destinationBackupDirectories.get(copyIndex));
    }
  }
  
  public Date getDateOfBackup() {
    return dateOfBackup;
  }
  
  public void setDateOfBackup(Date dateOfBackup) {
    this.dateOfBackup = dateOfBackup;
  }
  
  public List<String> getSourceBackupDirectoriesRawPaths() {
    return sourceBackupDirectoriesRawPaths;
  }
  
  public void setSourceBackupDirectoriesRawPaths(List<String> sourceBackupDirectoriesRawPaths) {
    this.sourceBackupDirectoriesRawPaths = sourceBackupDirectoriesRawPaths;
  }
  
  public List<String> getDestinationBackupDirectoriesRawPaths() {
    return destinationBackupDirectoriesRawPaths;
  }
  
  public void setDestinationBackupDirectoriesRawPaths(List<String> destinationBackupDirectoriesRawPaths) {
    this.destinationBackupDirectoriesRawPaths = destinationBackupDirectoriesRawPaths;
  }
  
  public List<File> getSourceBackupDirectories() {
    return sourceBackupDirectories;
  }
  
  public void setSourceBackupDirectories(List<File> sourceBackupDirectories) {
    this.sourceBackupDirectories = sourceBackupDirectories;
  }
  
  public List<File> getDestinationBackupDirectories() {
    return destinationBackupDirectories;
  }
  
  public void setDestinationBackupDirectories(List<File> destinationBackupDirectories) {
    this.destinationBackupDirectories = destinationBackupDirectories;
  }
  
  public List<File> getAllFiles() {
    return allFiles;
  }
  
  public void setAllFiles(List<File> allFiles) {
    this.allFiles = allFiles;
  }
  
  @Override
  public String toString() {
    return "Backup{" +
      "dateOfBackup=" + dateOfBackup +
      ", sourceBackupDirectoriesRawPaths=" + sourceBackupDirectoriesRawPaths +
      ", destinationBackupDirectoriesRawPaths=" + destinationBackupDirectoriesRawPaths +
      ", sourceBackupDirectories=" + sourceBackupDirectories +
      ", destinationBackupDirectories=" + destinationBackupDirectories +
      ", allFiles=" + allFiles +
      '}';
  }
}
