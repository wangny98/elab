package com.device.constant;

/**
 * 该类封装model的常量
 * @author geek
 * @version  [版本号, 2012-8-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DmConstants {

    /**
     * 文档存储路径
     */
    public static final String docDir = "D://elab//";
    /**
     * 文档存储路径
     */
    public static final String scienceDir = "science";

    public static final String noteDir = "note";

    public static final String cnsDir = "cns";
    
    public static final String picDir = "pic";
    /**
     * 用户性别：男
     */
    public static final int USER_SEX_MALE = 1;
    /**
     * 用户性别：女
     */
    public static final int USER_SEX_FEMALE = 2;

    public static final String DEPARTMENT_TYPE = "2";

    /**
     * 登录session的key
     */
    public static final String SESSION_INFO = "loginEL";

    /**
     * 用户密码已初始化
     */
    public static final Integer USER_PWD_INITIAL = 1;

    /**
     * 用户密码未初始化
     */
    public static final Integer USER_PWD_UNINITIAL = 2;

    /**
     * 用户密码强制认证
     */
    public static final Integer USER_PWD_ENFORCE = 0;

    /**
     * 用户密码非强制认证
     */
    public static final Integer USER_PWD_NOT_ENFORCE = 1;

    /**
     * 密码认证
     */
    public static final Integer USER_IDENTITY_TYPE_PASSWORD = 0;
    /**
     * KEY认证
     */
    public static final Integer USER_IDENTITY_TYPE_KEY = 1;
    /**
     * 指纹认证
     */
    public static final Integer USER_IDENTITY_TYPE_FINGER_MARK = 2;
    /**
     * 动态口令认证
     */

    public static final Integer USER_IDENTITY_TYPE_DYNAMIC_PASSWORD = 3;
    /**
     * 域认证
     */
    public static final Integer USER_IDENTITY_TYPE_AD = 4;

    /**
     * 可逆算法AES
     */
    public static final Integer USER_ENCRYPT_TYPE_SYMMETRIC = 1;

    /**
     * 不可逆算法MD5
     */

    public static final Integer USER_ENCRYPT_TYPE_ASYMMETRIC = 0;

    /**
     * 国际化中文语言
     */
    public static final String INTERNATION_CHINESE = "zh_CN";

    /**
     * 国际化英文语言
     */
    public static final String INTERNATION_ENGLISH = "en_US";
}
