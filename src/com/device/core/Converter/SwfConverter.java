package com.device.core.Converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.device.util.PathHelper;
import com.device.util.ReadServerParam;

public class SwfConverter implements FileConverter {

    private String converter;
    private static SwfConverter swfConverter = null;

    private static String INPUT_STREAM = "INPUTSTREAM";
    private static String ERROR_STREAM = "ERRORSTREAM";

    private SwfConverter(String convertType) {
        PathHelper helper = new PathHelper();
        //swftools的安装目录
        String softwarePath = ReadServerParam.getMessage("convert.swf.path").trim();
        if (ConvertConstant.PDF2SWF.equals(convertType)) {
            if (helper.isLinux()) {
                converter = softwarePath + File.separator + "pdf2swf";
            } else {
                converter = softwarePath + File.separator + "pdf2swf.exe";
            }
        } else if (ConvertConstant.JPG2SWF.equals(convertType)) {
            if (helper.isLinux()) {
                converter = softwarePath + File.separator + "jpeg2swf";
            } else {
                converter = softwarePath + File.separator + "jpeg2swf.exe";
            }
        } else if (ConvertConstant.GIF2SWF.equals(convertType)) {
            if (helper.isLinux()) {
                converter = softwarePath + File.separator + "gif2swf";
            } else {
                converter = softwarePath + File.separator + "gif2swf.exe";
            }
        } else if (ConvertConstant.PNG2SWF.equals(convertType)) {
            if (helper.isLinux()) {
                converter = softwarePath + File.separator + "png2swf";
            } else {
                converter = softwarePath + File.separator + "png2swf.exe";
            }
        }
    }

    public static synchronized SwfConverter getInstance(String convertType) {
        if (swfConverter == null) {
            swfConverter = new SwfConverter(convertType);
        }
        return swfConverter;
    }

    public String[] convert1(File src, File dest) throws IOException, InterruptedException {
        System.out.println("src exist:" + src.exists());
        Thread.sleep(500);
        String command = converter + "  " + src.getAbsolutePath() + " -o " + dest.getAbsolutePath() + " -T 9 -f";
        System.out.println("command:" + command);
        System.out.println("开始转换文档: " + src.getName());
        Process proc = Runtime.getRuntime().exec(command);
        //存储返回结果，第一个为标准信息，第二个为错误信息
        String result[] = new String[2];

        Object mutexInstream = new Object();
        Object mutexErrorstream = new Object();
        new ReadThread(proc.getInputStream(), INPUT_STREAM, result, mutexInstream).start();
        new ReadThread(proc.getErrorStream(), ERROR_STREAM, result, mutexErrorstream).start();
        //确保子线程已启动
        Thread.sleep(20);
        /*
         * 这里一定要等标准流与错误都读完了后才能继续执行后面的代码，否则外面引用返回的结果可能
         * 为null或空串，所以要等两个线程执行完，这里确保读取的结果已返回。在读取时使用了两个线
         * 程，因为发现在一个线程里读这种流时，有时会阻塞，比如代码实现时先读取标准流，而运行时
         * 命令却执行失败，这时读标准流的动作会阻塞，导致程序最终挂起，先读错误流时如果执行时成
         * 功，这时又可能挂起。还有一个问题就是即使使用两个线程分别读取流，如果不使用同步锁时，也
         * 会有问题：主线程读不到子线程返回的数据，这主要是由于主线读取时子线还没未返回读取到的信
         * 息，又因为两个读线程不能互斥，但又要与主线程同步，所以使用了两个同步锁，这样两个线程谁
         * 先执行互不影响，而且主线程阻塞直到标准信息与错误信息都返回为止
         */
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
                if (proc.waitFor() != 0) {
                    result[0] = null;
                } else {
                    result[1] = null;
                }
            }
        }
        return result;
    }

    public boolean convert2(File src, File dest) {
        String command = converter + " \"" + src.getAbsolutePath() + "\" -o " + dest.getAbsolutePath() + " -T 9 -f";
        System.out.println("command:" + command);
        try {
            System.out.println("开始转换文档: " + src.getName());
            synchronized (src) {
                Process proc = Runtime.getRuntime().exec(command);
                ProcessBuilder builder = new ProcessBuilder();
                builder.redirectErrorStream(true);
                InputStream stdErr = proc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stdErr));
                String line = null;
                boolean flag = true;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    flag = true;
                }
                int exitVal = proc.waitFor();
                System.out.println("ProcessExitVal:" + exitVal);
                if (exitVal != 0) {
                    return false;
                }
                System.out.println("成功转换为SWF文件！");
                return flag;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("转换文档为swf文件失败！");
            return false;
        }
    }

    public boolean convert(File src, File dest) {
        try {
            String[] result = convert1(src, dest);
            System.out.println("error info:---------------\r\n" + result[1]);
            System.out.println("std info:-----------------\r\n" + result[0]);
            return true;
        } catch (Exception e) {
            System.out.println("swf文件转换异常:" + e.getMessage());
            return false;
        }
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
