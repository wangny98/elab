package com.device.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class PathHelper {

    private static final PathHelper generator = new PathHelper();

    public static PathHelper getInstance() {
        return generator;
    }

    /**
     * 取得相对路径父路�?
     * @param relatedPath
     * @return
     * @throws IOException
     */
    public String getParentPath(String relatedPath) throws IOException {
        String path = null;
        if (relatedPath == null) {
            throw new NullPointerException();
        }
        String clsPath = getPathFromClass(this.getClass());
        clsPath = clsPath.substring(0, clsPath.indexOf(relatedPath));
        File file = new File(clsPath);
        path = file.getCanonicalPath();
        return path;
    }

    public String getPathFromClass(Class cls) throws IOException {
        String path = null;
        if (cls == null) {
            throw new NullPointerException();
        }
        URL url = getClassLocationURL(cls);
        if (url != null) {
            path = url.getPath();
            if ("jar".equalsIgnoreCase(url.getProtocol())) {
                try {
                    path = new URL(path).getPath();
                } catch (MalformedURLException e) {
                }
                int location = path.indexOf("!/");
                if (location != -1) {
                    path = path.substring(0, location);
                }
            }
            File file = new File(path);
            path = file.getCanonicalPath();
        }
        return path;
    }

    private URL getClassLocationURL(final Class cls) {
        if (cls == null)
            throw new IllegalArgumentException("null input: cls");
        URL result = null;
        final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
        final ProtectionDomain pd = cls.getProtectionDomain();
        // java.lang.Class contract does not specify
        // if 'pd' can ever be null;
        // it is not the case for Sun's implementations,
        // but guard against null
        // just in case:
        if (pd != null) {
            final CodeSource cs = pd.getCodeSource();
            // 'cs' can be null depending on
            // the classloader behavior:
            if (cs != null)
                result = cs.getLocation();

            if (result != null) {
                // Convert a code source location into
                // a full class file location
                // for some common cases:
                if ("file".equals(result.getProtocol())) {
                    try {
                        if (result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip"))
                            result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
                        else if (new File(result.getFile()).isDirectory())
                            result = new URL(result, clsAsResource);
                    } catch (MalformedURLException ignore) {
                    }
                }
            }
        }

        if (result == null) {
            // Try to find 'cls' definition as a resource;
            // this is not
            // document to be legal, but Sun's
            // implementations seem to //allow this:
            final ClassLoader clsLoader = cls.getClassLoader();
            result = clsLoader != null ? clsLoader.getResource(clsAsResource) : ClassLoader
                    .getSystemResource(clsAsResource);
        }
        return result;
    }

    /**
     * 返回true linux 系统
     * 返回false windows 系统
     * <功能详细描述>
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public Boolean isLinux() {
        boolean isLinux = true;
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os.startsWith("win") || os.startsWith("Win")) {
            isLinux = false;
        }
        return isLinux;
    }

    /**
     * 根据系统取得斜杠
     * <功能详细描述>
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public String getSlash() {
        String slash = "";
        if (isLinux()) {
            slash = "/";
        } else {
            slash = "\\";
        }
        return slash;
    }

}
