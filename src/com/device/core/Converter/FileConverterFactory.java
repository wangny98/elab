package com.device.core.Converter;

/**
 * 
 * 格式转换工厂
 * 
 * @author  guoke
 * @version  [版本号, 2013-9-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileConverterFactory {

    private FileConverterFactory() {
    }

    public static FileConverter getOfficeConverter() {
        return OpenofficeConverter.getInstance();
    }

    public static FileConverter getSwfConverter(final String convertType) {
        return SwfConverter.getInstance(convertType);
    }

    public static FileConverter getVideoConverter() {
        return VideoConverter.getInstance();
    }
}
