package com.device.constant;

public class ReturnCode {
	
	//系统错误
	public static final Integer SystemException = 10001;
	
	/**
     *  0 成功
     */
    public static final int SUCCESS = 0;

    /**
     *  1 失败
     */
    public static final int FAILURE = 1;

    /**
     * 参数错误
     */
    public static final int PARAMERROR = 100;

    /***********************************业务返回常量代码 start********************************/

    /**
     * 添加失败
     */
    public static final int ADD_ERROR = 101;

    /**
     * 删除失败
     */
    public static final int DELETE_ERROR = 102;

    /**
     * 更新失败
     */
    public static final int UPDATE_ERROR = 103;

    /**
     * 数据加载失败
     */
    public static final int LOAD_ERROR = 104;

    /**
     * 操作失败
     */
    public static final int OPERATE_ERROR = 105;

    /**
     * 属性和属性值同时重复
     */
    public static final int REPEAT_ERROR = 106;
    /**
     * 属性和描述同时重复
     */
    public static final int THEREPEAT_ERROR = 107;
    /**
     * 已是第一个
     */
    public static final int THEFIRSTONE = 108;
    /**
     * 已是最后一个
     */
    public static final int THELASTONE = 109;

}
