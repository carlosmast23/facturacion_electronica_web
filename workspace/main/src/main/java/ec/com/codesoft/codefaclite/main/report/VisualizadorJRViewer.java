/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.report;

import static ec.com.codesoft.codefaclite.main.report.VisualizadorJRViewer.Extension.DOCX;
import static ec.com.codesoft.codefaclite.main.report.VisualizadorJRViewer.Extension.ODT;
import static ec.com.codesoft.codefaclite.main.report.VisualizadorJRViewer.Extension.RTF;
import static ec.com.codesoft.codefaclite.recursos.RecursoCodefac.HTML;
import static ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoReporteEnum.PDF;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;

import java.lang.reflect.Constructor;
import java.util.Locale;
import java.util.ResourceBundle;
import net.sf.jasperreports.view.save.JRCsvSaveContributor;
import net.sf.jasperreports.view.save.JRDocxSaveContributor;
import net.sf.jasperreports.view.save.JREmbeddedImagesXmlSaveContributor;
import net.sf.jasperreports.view.save.JRHtmlSaveContributor;
import net.sf.jasperreports.view.save.JRMultipleSheetsXlsSaveContributor;
import net.sf.jasperreports.view.save.JROdtSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import net.sf.jasperreports.view.save.JRPrintSaveContributor;
import net.sf.jasperreports.view.save.JRRtfSaveContributor;
import net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor;
import net.sf.jasperreports.view.save.JRXmlSaveContributor;

/**
 *
 * @auhor
 * link: https://xbuba.com/questions/47349042
 */
public class VisualizadorJRViewer extends JRViewer {

    private static final Extension[] extensions;

    static {
        //HERE YOU CAN ADD WHATEVER EXTENSION YOU WANT
        extensions = new Extension[]{Extension.PDF,Extension.RTF,Extension.DOCX,Extension.ODT,Extension.HTML};
        //ADD THIS IF YOU WANT ALL
        //extensions = Extension.values();
    }

    public VisualizadorJRViewer(JasperPrint jasperPrint) {
        super(jasperPrint);

    }

    @Override
    protected JRViewerToolbar createToolbar() {
        JRViewerToolbar toolbar = super.createToolbar();
        Locale locale = viewerContext.getLocale();
        ResourceBundle resBundle = viewerContext.getResourceBundle();

        JRSaveContributor[] jrsc = new JRSaveContributor[extensions.length];
        Class[] type = {Locale.class, ResourceBundle.class};
        Object[] obj = {locale, resBundle};
        for (int i = 0; i < extensions.length; i++) {
            try {
                Constructor cons = extensions[i].getClazz().getConstructor(type);
                jrsc[i] = (JRSaveContributor) cons.newInstance(obj);
            } catch (Exception x) {
                x.printStackTrace();
            }
        }

        toolbar.setSaveContributors(jrsc);
        return toolbar;
    }

    public enum Extension {
        PDF(JRPdfSaveContributor.class),
        RTF(JRRtfSaveContributor.class),
        SINGLE_SHEET_XLS(JRSingleSheetXlsSaveContributor.class),
        MULTIPLE_SHEET_XLS(JRMultipleSheetsXlsSaveContributor.class),
        DOCX(JRDocxSaveContributor.class),
        ODT(JROdtSaveContributor.class),
        HTML(JRHtmlSaveContributor.class),
        XML(JRXmlSaveContributor.class),
        CSV(JRCsvSaveContributor.class),
        PRINT(JRPrintSaveContributor.class),
        EMBEDDED_IMAGES_XML(JREmbeddedImagesXmlSaveContributor.class);

        private Class<? extends JRSaveContributor> clazz;

        Extension(Class<? extends JRSaveContributor> clazz) {
            this.clazz = clazz;
        }

        public Class<? extends JRSaveContributor> getClazz() {
            return clazz;
        }
    }
}
