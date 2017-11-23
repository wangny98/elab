package com.device.core.Converter;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.device.util.ReadServerParam;

public class VideoConverter implements FileConverter {

    private static VideoConverter videoConverter = null;
    private static final String ffmpegPath;
    private static String INPUT_STREAM = "INPUTSTREAM";
    private static String ERROR_STREAM = "ERRORSTREAM";
    static {
        ffmpegPath = ReadServerParam.getMessage("convert.player.path");
    }

    public static VideoConverter getInstance() {
        if (videoConverter == null) {
            videoConverter = new VideoConverter();
        }
        return videoConverter;
    }

    public File convert2AVI(File src, File dest) throws Exception {
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegPath + File.separator + "mencoder");
        commend.add(src.getAbsolutePath());
        commend.add("-oac");
        commend.add("lavc");
        commend.add("-lavcopts");
        commend.add("acodec=mp3:abitrate=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        String destPath = dest.getAbsolutePath();
        String filename = destPath.substring(0, destPath.lastIndexOf(".")).trim();
        commend.add(filename + ".avi");
        ProcessBuilder builder = new ProcessBuilder();
        Process process = builder.command(commend).redirectErrorStream(true).start();
        //存储返回结果，第一个为标准信息，第二个为错误信息
        String result[] = new String[2];

        Object mutexInstream = new Object();
        Object mutexErrorstream = new Object();
        new ReadThread(process.getInputStream(), INPUT_STREAM, result, mutexInstream).start();
        new ReadThread(process.getErrorStream(), ERROR_STREAM, result, mutexErrorstream).start();
        //确保子线程已启动
        Thread.sleep(20);
        synchronized (mutexInstream) {
            synchronized (mutexErrorstream) {
                /*
                 * 导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止
                 * 。如果已终止该子进程，此方法立即返回。如果没有终止该子进程，调用的线程将被
                 * 阻塞，直到退出子进程。  
                 * process.waitFor()目的是等待子进程完成后再往下执行，不过这里好像没有什么
                 * 太大的作用除了用来判断返回的状态码外，因为如果程序进到这里表示子线程已执行完
                 * 毕，process子进程理所当然的也已执行完毕，如果子进程process未执行完，我想
                 * 读流的操作肯定会阻塞的。
                 * 
                 * 另外，使用process.waitFor()要注的是一定不要在数据流读取前使用，否则线程
                 * 也会挂起，导致该现象的原因可能是该命令的输内容出比较多，而运行窗口的输出缓冲
                 * 区不够大，最后没不能写缓冲引起，所以这里先使用了两个单独的线程去读，这样不管
                 * 数据量有多大，都不会阻塞了。
                 */
                if (process.waitFor() != 0) {
                    result[0] = null;
                } else {
                    result[1] = null;
                }
            }
        }
        System.out.println("error info:---------------\r\n" + result[1]);
        System.out.println("std info:-----------------\r\n" + result[0]);
        File aviFile = new File(filename + ".avi");
        return aviFile;
    }

    public String[] convert2FLV(File src, File dest) throws Exception {
        List<String> command = new ArrayList<String>();
        command.add(ffmpegPath + File.separator + "ffmpeg");
        command.add("-i");
        command.add(src.getAbsolutePath());
        command.add("-ab");
        command.add("56");
        command.add("-ar");
        command.add("22050");
        command.add("-qscale");
        command.add("8");
        command.add("-r");
        command.add("15");
        command.add("-s");
        command.add("600x500");
        command.add(dest.getAbsolutePath());
        Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
        //存储返回结果，第一个为标准信息，第二个为错误信息
        String result[] = new String[2];

        Object mutexInstream = new Object();
        Object mutexErrorstream = new Object();
        new ReadThread(videoProcess.getInputStream(), INPUT_STREAM, result, mutexInstream).start();
        new ReadThread(videoProcess.getErrorStream(), ERROR_STREAM, result, mutexErrorstream).start();
        //确保子线程已启动
        Thread.sleep(20);
        synchronized (mutexInstream) {
            synchronized (mutexErrorstream) {
                /*
                 * 导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止
                 * 。如果已终止该子进程，此方法立即返回。如果没有终止该子进程，调用的线程将被
                 * 阻塞，直到退出子进程。  
                 * process.waitFor()目的是等待子进程完成后再往下执行，不过这里好像没有什么
                 * 太大的作用除了用来判断返回的状态码外，因为如果程序进到这里表示子线程已执行完
                 * 毕，process子进程理所当然的也已执行完毕，如果子进程process未执行完，我想
                 * 读流的操作肯定会阻塞的。
                 * 
                 * 另外，使用process.waitFor()要注的是一定不要在数据流读取前使用，否则线程
                 * 也会挂起，导致该现象的原因可能是该命令的输内容出比较多，而运行窗口的输出缓冲
                 * 区不够大，最后没不能写缓冲引起，所以这里先使用了两个单独的线程去读，这样不管
                 * 数据量有多大，都不会阻塞了。
                 */
                if (videoProcess.waitFor() != 0) {
                    result[0] = null;
                } else {
                    result[1] = null;
                }
            }
        }
        System.out.println("error info:---------------\r\n" + result[1]);
        System.out.println("std info:-----------------\r\n" + result[0]);
        return result;
    }

    public boolean convert(File src, File dest) {
        String destPath = src.getAbsolutePath();
        try {
            String filetype = destPath.substring(destPath.lastIndexOf(".") + 1).trim();
            if (filetype.equalsIgnoreCase("rm") || filetype.equalsIgnoreCase("rmvb")
                    || filetype.equalsIgnoreCase("wmv9")) {
                System.out.println("[视频转换]:开始转换成avi");
                src = convert2AVI(src, dest);
                if (null == src || (!src.exists())) {
                    System.out.println("[视频转换]:转换为avi失败");
                    return false;
                }
                convert2FLV(src, dest);
            } else if (filetype.equalsIgnoreCase("avi") || filetype.equalsIgnoreCase("mpg")
                    || filetype.equalsIgnoreCase("wmv") || filetype.equalsIgnoreCase("3gp")
                    || filetype.equalsIgnoreCase("mov") || filetype.equalsIgnoreCase("mp4")
                    || filetype.equalsIgnoreCase("asf") || filetype.equalsIgnoreCase("asx")) {
                System.out.println("[视频转换]:开始转换成flv");
                convert2FLV(src, dest);
            }

        } catch (Exception e) {
            System.out.println("[视频转换]:转换失败：" + e.getMessage());
            return false;
        }
        return true;
    }

    /*
     * 标准流与错误流读取线程
     */
    private static class ReadThread extends Thread {
        private InputStream is;
        private String[] resultArr;
        private String type;
        private Object mutex;

        public ReadThread(InputStream is, String type, String[] resultArr, Object mutex) {
            this.is = is;
            this.type = type;
            this.resultArr = resultArr;
            this.mutex = mutex;
        }

        @SuppressWarnings("unchecked")
        public void run() {
            synchronized (mutex) {
                try {
                    int readInt = is.read();
                    ArrayList result = new ArrayList();

                    /*
                     * 这里读取时我们不要使用字符流与缓冲流，发现执行某些命令时会阻塞，不
                     * 知道是什么原因。所有这里使用了最原始的流来操作，就不会出现问题。
                     */
                    while (readInt != -1) {
                        result.add(Byte.valueOf(String.valueOf((byte) readInt)));
                        readInt = is.read();
                    }

                    byte[] byteArr = new byte[result.size()];
                    for (int i = 0; i < result.size(); i++) {
                        byteArr[i] = ((Byte) result.get(i)).byteValue();
                    }
                    if (ERROR_STREAM.equals(this.type)) {
                        resultArr[1] = new String(byteArr);
                    } else {
                        resultArr[0] = new String(byteArr);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
