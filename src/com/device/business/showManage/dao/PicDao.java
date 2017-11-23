package com.device.business.showManage.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.showManage.bean.PicBean;
import com.device.business.showManage.dao.mapper.PicMapper;
import com.device.constant.DmConstants;
import com.device.core.Converter.FileConverTerThread;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class PicDao {
	@Autowired
	PicMapper picMapper;
	
	public int addListPic(List<PicBean> list,String pic_descript){
		int count = 0;
		for(PicBean bean:list){
			bean.setPic_descript(pic_descript);
			int add = this.add(bean);
			count = count + add;
		}
		return count;
	}
	
	public String importDoc(HttpServletRequest request,String show_id,String pic_descript){
		int count = 0;
		List<PicBean> list = new ArrayList<PicBean>();
		boolean boo = this.write(request, show_id,list);
		if(list.size() > 0 && boo){
			count = this.addListPic(list,pic_descript);
		}
		if (count > 0) {
            return "{success:true}";
        } else {
            return "{success:false}";
        }
	}
	
	public boolean write(HttpServletRequest request,String show_id,List<PicBean> list){
		boolean isUpload = ServletFileUpload.isMultipartContent(request);
		if (isUpload) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            OutputStream out;
            InputStream in;
            try {
            	List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    PicBean bean = new PicBean();
                    if (!item.isFormField()) {
                    	//获取路径名  
                        String value = item.getName();
                        //索引到最后一个反斜杠  
                        int start = value.lastIndexOf("\\");
                        //截取 上传文件的 字符串名字，加1是 去掉反斜杠，  
                        String filename = value.substring(start + 1);

                        String id = PrimaryKeyUtil.getSeq();
                        bean.setId(id);
                        bean.setPic_name(filename);
                        bean.setPic_path(DmConstants.docDir + DmConstants.picDir);
                        bean.setShow_id(show_id);
                        list.add(bean);
                        int startDx = filename.lastIndexOf(".");
                        String dx = "";
                        String storeName = null;
                        if (startDx > 0) {
                            dx = filename.substring(startDx + 1);
                            storeName = id + "." + dx;
                        } else {
                            storeName = id;
                        }
                        File file = new File(DmConstants.docDir + DmConstants.picDir, storeName);
                        if (file == null) {
                            throw new FileNotFoundException();
                        }
                      //手动写的  
                        out = new FileOutputStream(file);

                        in = item.getInputStream();

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
                        //new Thread(new FileConverTerThread(DmConstants.docDir + DmConstants.picDir, storeName)).start();
                    }
                }
                return true;
            }catch(Exception e){
            	e.printStackTrace();
                return false;
            }finally{
            }
		} else {
            return false;
        }
	}
	
	public ArrayList<PicBean> query(String searchName, int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<PicBean> list = new ArrayList<PicBean>();
		list = picMapper.query("%" + searchName + "%", rows);
		return list;
	}
	
	public int count(String searchName) {
		int i = 0;
		i = picMapper.count("%" + searchName + "%");
		return i;
	}
	
	public int add(PicBean bean) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		return picMapper.add(bean);
	}
	
	public PicBean load(String id) {
		return picMapper.load(id);
	}
	
	public int update(PicBean bean){
		return picMapper.update(bean);
	}

	public int delete(String id) {
		return picMapper.delete(id);
	}
}
