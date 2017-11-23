import java.io.Serializable;
    import java.lang.reflect.Field;

    import java.text.SimpleDateFormat;

    import java.util.ArrayList;

    import java.util.Date;

    import java.util.Hashtable;

    import java.util.List;


    /**

     * FOR JDBC RUID Operation,This class can generate SQL by parameter of

     *  Object(update/insert/delete)

     *  or Class(select)

     *

     *@category com.util

     *@class SQLUtil

     *@author zhusheng3@126.com

     *@date 2008-6-7 下午04:08:40

     *@see

     */

    public class SQLUtil

    {

    	/** 原文地址：http://www.software8.co/wzjs/java/2875.html

         * @param args 

         */  

        public static void main(String[] args) {  

              


            System.out.println(genCreateTableSql("com.device.business.tracker.bean.UseAuditBean"));   

            

        }  

          

        public static String getBeanName(String bean){  

            try {  

                Class clz = Class.forName(bean);  

                String clzStr = clz.toString();  

                //得到类名  

                String beanName = clzStr.substring(clzStr.lastIndexOf(".")+1).toLowerCase();  

                return beanName;  

            } catch (ClassNotFoundException e) {  

                e.printStackTrace();  

                return "";  

            }  

        }  

          

        public static List<String> getBeanPropertyList(String bean){  

            try {  

                Class clz = Class.forName(bean);  

                Field[] strs = clz.getDeclaredFields();  

                List<String> propertyList = new ArrayList<String>();  

                for (int i = 0; i < strs.length; i++) {  

                    String protype = strs[i].getType().toString();  

                    propertyList.add(protype.substring(protype.lastIndexOf(".")+1)+"`"+strs[i].getName());  

                }  

                return propertyList;  

            } catch (ClassNotFoundException e) {  

                e.printStackTrace();  

                return null;  

            }  

        }  

          

        public static String getBeanFilesList(String bean){  

            try {  

                Class clz = Class.forName(bean);  

                Field[] strs = clz.getDeclaredFields();  

                StringBuffer sb = new StringBuffer();  

                for (int i = 0; i < strs.length; i++) {  

                    String protype = strs[i].getType().toString();  

                    if (!strs[i].getName().equals("tableName")&&!strs[i].getType().equals("List")) {  

                       sb.append(strs[i].getName()+",");  

                    }  

                }  

                sb.deleteCharAt(sb.toString().lastIndexOf(","));  

                return sb.toString();  

            } catch (ClassNotFoundException e) {  

                e.printStackTrace();  

                return null;  

            }  

        }  

          

        /** 

         * 生成建表句 

         * @param bean 

         * @return 

         */  

        public static String genCreateTableSql(String bean){  

            List<String> beanPropertyList =  getBeanPropertyList(bean);  

            StringBuffer sb = new StringBuffer("create table wnk_pdt_"+getBeanName(bean)+"( ");  

            for (String string : beanPropertyList) {  

                String[] propertys = string.split("`");  

                if (!propertys[1].equals("tableName")&&!propertys[1].equals("param")&&!propertys[0].equals("List")) {  

                    if (propertys[1].equals("id")) {  

                        sb.append("   id varchar(200) primary key, ");  

                    } else {  

                        if (propertys[0].equals("int")) {  

                            sb.append("   " + propertys[1] + " int default 0 comment '', ");  

                        } else if (propertys[0].equals("String")) {  

                            sb.append("   " + propertys[1] + " varchar(200) default '' comment '', ");  

                        } else if (propertys[0].equals("double")) {  

                            sb.append("   " + propertys[1] + " double(10,2) default 0.0 comment '', ");  

                        } else if (propertys[0].equals("Date")) {  

                            sb.append("   " + propertys[1] + " datetime comment '', ");  

                        }  

                    }  

                }  

            }  

            sb.append(")");  

            sb.deleteCharAt(sb.lastIndexOf(","));  

            return sb.toString();  

        }  

          

        /** 

         * 生成查询语句 

         * @param bean 

         * @return 

         */  

        public static String genSelectAllSql(String bean){  

            String filesList =  getBeanFilesList(bean);  

            return "select   "+filesList+"   from   wnk_pdt_"+getBeanName(bean)+"";  

        }  

          

        /** 

         * 生成插入语句 

         * @param bean 

         * @return 

         */  

       /* public static String genInsertSql(String bean){  

            String filesList =  getBeanFilesList(bean);  

            int fl = DataUtil.getCountSonStr(filesList,",")+1;  

            String wenhao = "";  

            for (int i = 0; i < fl; i++) {  

                if(i==fl-1){  

                    wenhao = wenhao+"?";  

                }else{  

                    wenhao = wenhao+"?,";  

                }  

            }  

            return "insert into wnk_pdt_"+getBeanName(bean)+"("+filesList+") values("+wenhao+")";  

        }  */


    }