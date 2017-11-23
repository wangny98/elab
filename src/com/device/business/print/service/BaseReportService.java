package com.device.business.print.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import com.agile.erms.utils.CommUtils;
import com.device.business.print.bean.ChartBean;

/**
 * 
 * 报表的基础类
 * 
 * @author  geek
 * @version  [版本号, 2013-4-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseReportService {

    /**
     * 图标的公共属性设置方法    
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected ChartBean loadChart() {
        ChartBean chart = new ChartBean();
        chart.setShowBorder("0");
        chart.setBgColor("ffffff");
        chart.setDivLineIsDashed("1");
        chart.setPlotGradientColor(" ");
        chart.setShowAlternateHGridColor("1");
        chart.setOutCnvbaseFont("arial");
        chart.setOutCnvbaseFontSize("12");
        chart.setShowPlotBorder("0");
        chart.setLegendPosition("right");
        chart.setShowLegend("1");
        chart.setBaseFont("arial");
        chart.setBaseFontSize("12");
        chart.setHovercapbg("ffffff");
        chart.setHovercapborder("889E6D");
        chart.setNumdivlines("9");
        chart.setDivlinecolor("CCCCCC");
        chart.setAlternatehgridalpha("30");
        chart.setAlternatehgridcolor("CCCCCC");
        chart.setFormatNumberScale("0");
        return chart;
    }

    /**
     * 打印报表公共方法
     * <功能详细描述>
     * @param request
     * @param response
     * @param reportName 报表
     * @param list 保镖中显示的数据集合
     * @param map 报表中放入的参数集合
     * @throws JRException 
     * @see [类、类#方法、类#成员]
     */
    public static void htmlReport(HttpServletRequest request, HttpServletResponse response, String reportName,
            List list, Map parameters) throws ServletException, IOException, JRException {
        OutputStream os = response.getOutputStream();
        try {
            JasperPrint jasperPrint = null;
            JasperReport jasperReport = null;
            JRAbstractExporter exporter = new JRRtfExporter();
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            path = path + "report" + File.separator + reportName + ".jrxml";
            jasperReport = JasperCompileManager.compileReport(path);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(list));
            if (null != jasperPrint) {
                exporter = new JRHtmlExporter();
                response.setContentType("text/html");
                request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
                Map imagesMap = new HashMap();
                exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
                exporter.exportReport();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!CommUtils.isNullOrBlank(os)) {
                os.close();
            }
        }
    }

}
