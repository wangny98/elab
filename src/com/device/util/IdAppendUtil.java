package com.device.util;

import java.util.Collection;
import java.util.Iterator;

public class IdAppendUtil {
    public static String Collection2String(Collection<String> id) {
        StringBuffer sb = new StringBuffer("('");
        if (id.size() > 0) {
            Iterator<String> it = id.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (null == str)
                    continue;
                sb.append("','" + str);
            }

        }
        sb.append("')");
        return sb.toString();
    }
    
    public static String Array2String(String[] ids){
    	StringBuffer sb = new StringBuffer("('");
    	
    	for(int i=0;i<ids.length;i++){
    		if(i!=0){
    			sb.append("','"+ids[i]);
    		}else{
    			sb.append(ids[i]);
    		}
    	}
	 sb.append("')");
     return sb.toString();
    }
}
