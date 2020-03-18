package org.cjohnson.superbackup.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateFormatter {
  
  private static String DATE_PATTERN = "MMM-dd-yyyy-HH-mm";
  
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy-HH-mm");
  
  public static String formatDate(Date date) {
    return simpleDateFormat.format(date);
  }
  
}
