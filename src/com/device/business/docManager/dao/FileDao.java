package com.device.business.docManager.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.agile.erms.utils.CommUtils;
import com.device.business.docManager.bean.ContentNodeBean;
import com.device.business.docManager.bean.FileBean;
import com.device.business.docManager.bean.ScienceBean;
import com.device.business.docManager.dao.mapper.ContentManagerMapper;
import com.device.business.docManager.dao.mapper.FileManagerMapper;
import com.device.business.docManager.dao.mapper.ScienceManageMapper;
import com.device.business.login.element.LoginElement;
import com.device.core.Converter.FileConverTerThread;
import com.device.core.Converter.PathUtil;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;

public class FileDao {

    @Autowired
    FileManagerMapper fileManagerMapper;

    @Autowired
    ScienceManageMapper scienceManageMapper;

    @Autowired
    ContentManagerMapper contentManagerMapper;

    public List<FileBean> listFile(String searchName, String types, int start, int limit, String nodeId) {
        List<FileBean> list = new ArrayList<FileBean>();
        RowBounds rows = new RowBounds(start, limit);
        System.out.println("nodeId : " + nodeId);
        String typesStr = "(" + types + ")";
        list = fileManagerMapper.listFile(searchName, typesStr, nodeId, rows);
        return list;
    }

    public int countFile(String searchName, String types, String nodeId) {
        int count = fileManagerMapper.countFile(searchName, "(" + types + ")", nodeId);
        return count;
    }

    /**
     * 批量上传
     * <功能详细描述>
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String importDoc(HttpServletRequest request, ScienceBean scienceBean) {
        int count = 0;
        String nodeId = request.getParameter("id");
        String hasForm = request.getParameter("hasForm");
        ContentNodeBean bean = contentManagerMapper.infoContentNode(nodeId);
        if (bean != null) {
            List<FileBean> list = new ArrayList<FileBean>();
            boolean boo = this.write(request, bean.getPath(), Integer.valueOf(bean.getTypeId()), list, nodeId);
            if (list.size() > 0 && boo) {
                count = this.addListFile(list, Integer.valueOf(bean.getTypeId()), scienceBean, hasForm);
            }
        }
        if (count > 0) {
            return "{success:true}";
        } else {
            return "{success:false}";
        }
    }

    //文件上传
    @SuppressWarnings( { "unchecked", "unused" })
    public boolean write(HttpServletRequest request, String path, int type, List<FileBean> list, String nodeId) {

        boolean isUpload = ServletFileUpload.isMultipartContent(request);
        if (isUpload) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                // 解析request请求
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    FileBean bean = new FileBean();
                    if (!item.isFormField()) {
                        //获取路径名  
                        String value = item.getName();
                        //索引到最后一个反斜杠  
                        int start = value.lastIndexOf("\\");
                        //截取 上传文件的 字符串名字，加1是 去掉反斜杠，  
                        String filename = value.substring(start + 1);

                        String id = PrimaryKeyUtil.getSeq();
                        bean.setId(id);
                        bean.setName(filename);
                        int startDx = filename.lastIndexOf(".");
                        String dx = "";
                        String storeName = null;
                        if (startDx > 0) {
                            dx = filename.substring(startDx + 1);
                            storeName = id + "." + dx;
                        } else {
                            storeName = id;
                        }

                        //File file = new File(path, id);
                        File file = new File(path, storeName);
                        if (file == null) {
                            throw new FileNotFoundException();
                        }
                        bean.setPath(path);
                        bean.setDirId(nodeId);
                        bean.setType(type);
                        LoginElement login = AuthorityContext.getLoginElement();
                        bean.setCreatorId(login.getUserId());
                        bean.setCreatorName(login.getUserFullName());
                        bean.setCreatorTime(new Date());
                        list.add(bean);
                        //手动写的  
                        OutputStream out = new FileOutputStream(file);

                        InputStream in = item.getInputStream();

                        int length = 0;
                        byte[] buf = new byte[1024];

                        System.out.println("获取上传文件的总共的容量：" + item.getSize());

                        // in.read(buf) 每次读到的数据存放在   buf 数组中  
                        while ((length = in.read(buf)) != -1) {
                            //在   buf 数组中 取出数据 写到 （输出流）磁盘上  
                            out.write(buf, 0, length);

                        }
                        in.close();
                        out.close();
                        if (dx.equalsIgnoreCase("doc") || dx.equalsIgnoreCase("docx") || dx.equalsIgnoreCase("ppt")
                                || dx.equalsIgnoreCase("pptx") || dx.equalsIgnoreCase("xls")
                                || dx.equalsIgnoreCase("xlsx") || dx.equalsIgnoreCase("txt")) {
                            new Thread(new FileConverTerThread(path, storeName)).start();
                        }
                    }
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    @Transactional
    public int addListFile(List<FileBean> list, int type, ScienceBean scienceBean, String hasForm) {
        int count = 0;
        for (FileBean bean : list) {
            System.out.println("文档上传 ： " + bean);
            int add = fileManagerMapper.addFile(bean);
            if (hasForm != null && hasForm.equals("1")) {
                scienceBean.setId(bean.getId());
                scienceManageMapper.add(scienceBean);
            }
            count = count + add;
        }
        return count;
    }

    @SuppressWarnings("unused")
    public int exportFile(String id, HttpServletResponse response,HttpServletRequest request) throws IOException {
        FileBean bean = fileManagerMapper.getFile(id);
        fileManagerMapper.increaseDownload(id);
        if (bean == null || bean.getPath() == null) {
            return 101;// 文件不存在
        } else {
            response.setContentType("application/x-download;charset=UTF-8");
            System.out.println("下载文件 ： " + bean.getName());
            String finalFileName =null;
            String fileName = bean.getName();
            String userAgent = request.getHeader("User-Agent"); 
            byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8"); // name.getBytes("UTF-8")处理safari的乱码问题 
            finalFileName = new String(bytes, "ISO-8859-1");

            
            response.addHeader("Content-Disposition", "attachment;filename=" + finalFileName);

            String filename = bean.getName();
            if (filename == null) {
                return 101;// 文件不存在
            } else {
                int startDx = filename.lastIndexOf(".");
                String dx = "";
                String storeName = new String();
                if (startDx > 0) {
                    dx = filename.substring(startDx + 1);
                    storeName = id + "." + dx;
                }
                System.out.println("读取文件 ： " + storeName);
                File file = new File(bean.getPath(), storeName);
                if (file == null) {
                    return 101;// 文件不存在
                } else {
                    OutputStream output = null;
                    FileInputStream in = null;
                    try {
                        output = response.getOutputStream();
                        in = new FileInputStream(file);
                        int length = 0;
                        byte[] buf = new byte[1024];
                        while ((length = in.read(buf)) != -1) {
                            //在   buf 数组中 取出数据 写到 （输出流）磁盘上  
                            output.write(buf, 0, length);
                        }

                        in.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (in != null) {
                            in.close();
                        }
                        if (output != null) {
                            output.close();
                        }
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                        if (output != null) {
                            output.close();
                        }
                    }
                }
            }

            return 100;//成功
        }
    }

    public RestResponse docPreview(String id) throws IOException {
        RestResponse restResponse = new RestResponse();
        FileBean bean = fileManagerMapper.getFile(id);
        if (!CommUtils.isNullOrBlank(bean) && !CommUtils.isNullOrBlank(bean.getName())) {
            String dx = PathUtil.getDx(bean.getName());
            if (!dx.equalsIgnoreCase("doc") && !dx.equalsIgnoreCase("docx") && !dx.equalsIgnoreCase("ppt")
                    && !dx.equalsIgnoreCase("pptx") && !dx.equalsIgnoreCase("xls") && !dx.equalsIgnoreCase("xlsx")
                    && !dx.equalsIgnoreCase("txt")) {
                restResponse.setRetCode(102);//文档不支持预览
                restResponse.setSuccess(false);
                return restResponse;
            }
        }
        fileManagerMapper.increasePreview(id);
        File f = new File(bean.getPath() + "\\swf", bean.getId() + ".swf");
        InputStream in = null;
        OutputStream out = null;
        File outFile = null;
        try {
            in = new FileInputStream(f);
            String docPath = PathUtil.getWebRootDir() + "tmp/" + bean.getId() + ".swf";
            outFile = new File(docPath);
            out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
            }
            out.flush();
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        if (outFile == null) {
            restResponse.setRetCode(101);//文件不存在
            restResponse.setSuccess(false);
        } else {
            restResponse.setRetObject("path", "tmp/" + bean.getId() + ".swf");
            restResponse.setRetObject("isOffice", true);
            restResponse.setSuccess(true);
        }

        return restResponse;
    }

    public int deleteDoc(String id) {
        FileBean bean = fileManagerMapper.getFile(id);
        if (!CommUtils.isNullOrBlank(bean) && !CommUtils.isNullOrBlank(bean.getPath())
                && !CommUtils.isNullOrBlank(bean.getName())) {
            String dx = PathUtil.getDx(bean.getName());
            String fileStr = bean.getPath() + "\\" + bean.getId() + "." + dx;
            File f = new File(fileStr);
            if (f != null) {
                f.delete();
            }
            if (PathUtil.isOffice(dx)) {
                String pdfStr = bean.getPath() + "\\pdf\\" + bean.getId() + ".pdf";
                File pdf = new File(pdfStr);
                if (pdf != null) {
                    pdf.delete();
                }
            }
            if (PathUtil.isOffice(dx)) {
                String swfStr = bean.getPath() + "\\swf\\" + bean.getId() + ".swf";
                File swf = new File(swfStr);
                if (swf != null) {
                    swf.delete();
                }
            }
        }
        if (bean.getType() == 1) {//科研论文
            scienceManageMapper.delete(id);
        }
        return fileManagerMapper.delSingleFile(id);
    }
}
