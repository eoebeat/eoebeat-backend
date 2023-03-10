package com.eoemusic.eoebackend.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 09/02/23 4:55 PM
 */
@Slf4j
public class DateUtil {

  private static final ThreadLocal<DateFormat> threadlocalDetailFormat = ThreadLocal
      .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
  private static final ThreadLocal<DateFormat> threadlocalDateDashFormat = ThreadLocal
      .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
  private static final ThreadLocal<DateFormat> threadlocalDateDotFormat = ThreadLocal
      .withInitial(() -> new SimpleDateFormat("yyyy.MM.dd"));
  private static final ThreadLocal<DateFormat> threadlocalMonthDotFormat = ThreadLocal
      .withInitial(() -> new SimpleDateFormat("yyyy.MM"));
  private static final ThreadLocal<DateFormat> threadlocalMonthFormat = ThreadLocal
      .withInitial(() -> new SimpleDateFormat("yyyy.MM"));
  private static final ThreadLocal<DateFormat> threadlocalMonthChiFormat = ThreadLocal
      .withInitial(() -> new SimpleDateFormat("yyyy年M月"));

  public static String transformDetailTime(Date resourceDate) {
    return threadlocalDetailFormat.get().format(resourceDate);
  }

  public static String transformMonth(Date resourceDate) {
    return threadlocalMonthChiFormat.get().format(resourceDate);
  }

  public static String parseDateDotToChi(String resourceDateDot) {
    return parseStringToString(threadlocalMonthDotFormat, threadlocalMonthChiFormat,
        resourceDateDot);
  }

  public static String parseDateDashToDot(String resourceDateDash) {
    return parseStringToString(threadlocalDateDashFormat, threadlocalDateDotFormat,
        resourceDateDash);
  }

  public static String parseStringToString(ThreadLocal<DateFormat> threadlocalFormatFrom,
      ThreadLocal<DateFormat> threadlocalFormatTo, String resourceDate) {
    Date date = null;
    try {
      date = threadlocalFormatFrom.get().parse(resourceDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return threadlocalFormatTo.get().format(date);
  }

  public static int[] parseYearMonth(String resourceYearMonth) {
    int[] yearMonth = new int[2];
    try {
      Date date = threadlocalMonthFormat.get().parse(resourceYearMonth);
      Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
      cal.setTime(date);
      yearMonth[0] = cal.get(Calendar.YEAR);
      yearMonth[1] = cal.get(Calendar.MONTH) + 1;
    } catch (ParseException e) {
      log.error("parse year and month failed, {}", e);
    }
    return yearMonth;
  }

  public static void clear() {
    threadlocalDetailFormat.remove();
    threadlocalDateDashFormat.remove();
    threadlocalDateDotFormat.remove();
    threadlocalMonthFormat.remove();
  }
}
