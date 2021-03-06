package jmri.jmrit.throttle;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jmri.jmrit.XmlFile;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Load throttles from XML
 *
 * @author Glen Oberhauser 2004
 */
public class LoadXmlThrottlesLayoutAction extends AbstractAction {

    /**
     * Constructor
     *
     * @param s Name for the action.
     */
    public LoadXmlThrottlesLayoutAction(String s) {
        super(s);
        // disable the ourselves if there is no throttle Manager
        if (jmri.InstanceManager.getNullableDefault(jmri.ThrottleManager.class) == null) {
            setEnabled(false);
        }
    }

    public LoadXmlThrottlesLayoutAction() {
        this("Open Throttle");
    }

    JFileChooser fileChooser;

    /**
     * The action is performed. Let the user choose the file to load from. Read
     * XML for each ThrottleFrame.
     *
     * @param e The event causing the action.
     */
    public void actionPerformed(ActionEvent e) {
        if (fileChooser == null) {
            fileChooser = jmri.jmrit.XmlFile.userFileChooser(Bundle.getMessage("PromptXmlFileTypes"), "xml");
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            fileChooser.setCurrentDirectory(new File(ThrottleFrame.getDefaultThrottleFolder()));
        }
        int retVal = fileChooser.showOpenDialog(null);
        if (retVal != JFileChooser.APPROVE_OPTION) {
            return;
            // give up if no file selected
        }

        // if exising frames are open ask to destroy those or merge.
        if (ThrottleFrameManager.instance().getThrottleWindows().hasNext()) {
            Object[] possibleValues = {Bundle.getMessage("LabelMerge"),
                Bundle.getMessage("LabelReplace"),
                Bundle.getMessage("LabelCancel")};
            int selectedValue = JOptionPane.showOptionDialog(null,
                    Bundle.getMessage("DialogMergeOrReplace"),
                    Bundle.getMessage("OptionLoadingThrottles"),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
                    possibleValues[0]);
            if (selectedValue == JOptionPane.NO_OPTION) {
                // replace chosen - close all then load
                ThrottleFrameManager.instance().requestAllThrottleWindowsDestroyed();
            }
        }
        try {
            loadThrottlesLayout(fileChooser.getSelectedFile());
        } catch (java.io.IOException e1) {
            log.warn("Exception while reading file", e1);
        }
    }

    /**
     * Parse the XML file and create ThrottleFrames. Returns true if throttle
     * loaded successfully.
     *
     * @param f The XML file containing throttles.
     */
    @SuppressWarnings("unchecked")
    public boolean loadThrottlesLayout(java.io.File f) throws java.io.IOException {
        try {
            ThrottlePrefs prefs = new ThrottlePrefs();
            Element root = prefs.rootFromFile(f);
            List<Element> throttles = root.getChildren("ThrottleFrame");
            if ((throttles != null) && (throttles.size() > 0)) { // OLD FORMAT				
                for (java.util.Iterator<Element> i = throttles.iterator(); i.hasNext();) {
                    ThrottleFrame tf = ThrottleFrameManager.instance().createThrottleFrame();
                    tf.setXml(i.next());
                    tf.toFront();
                }
            } else {
                throttles = root.getChildren("ThrottleWindow");
                for (java.util.Iterator<Element> i = throttles.iterator(); i.hasNext();) {
                    ThrottleWindow tw = ThrottleFrameManager.instance().createThrottleWindow();
                    tw.setXml(i.next());
                    tw.setVisible(true);
                }
                Element tlp = root.getChild("ThrottlesListPanel");
                if (tlp != null) {
                    ThrottleFrameManager.instance().getThrottlesListPanel().setXml(tlp);
                }
            }
        } catch (org.jdom2.JDOMException ex) {
            log.warn("Loading Throttles exception", ex);
            return false;
        }
        return true;
    }

    /**
     * An extension of the abstract XmlFile. No changes made to that class.
     *
     * @author glen
     */
    static class ThrottlePrefs extends XmlFile {
    }

    // initialize logging
    private final static Logger log = LoggerFactory.getLogger(LoadXmlThrottlesLayoutAction.class.getName());

}
