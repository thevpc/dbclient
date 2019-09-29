package net.vpc.dbclient.plugin.tool.neormf.settings;

import net.vpc.swingext.JFileTextField;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 15 févr. 2009
 * Time: 02:55:35
 * To change this template use File | Settings | File Templates.
 */
public class JFileTextFieldDefault extends JPanel {
    JFileTextField fileTextField =new JFileTextField();
    JTextField label=new JTextField();

    public JFileTextFieldDefault() {
        super();
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        add(fileTextField);
        fileTextField.setBorder(null);
        add(label);
        label.setEditable(false);
        fileTextField.addPropertyChangeListener(JFileTextField.SELECTED_FILE,new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                revalidateLabel();
            }
        });
        fileTextField.addPropertyChangeListener("defaultFolder",new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                revalidateLabel();
            }
        });
        setBorder(BorderFactory.createEtchedBorder());
    }

    public JFileChooser getJFileChooser() {
        return fileTextField.getJFileChooser();
    }

    public void setFile(String file) {
        fileTextField.setFile(file);
    }

    public void setFile(File file) {
        fileTextField.setFile(file);
    }

    private void revalidateLabel(){
        File file = fileTextField.getAbsoluteFile();
        label.setText(filetoString(file));
    }

    private String filetoString(File f){
        try {
            return f==null?"":f.getCanonicalPath();
        } catch (IOException e) {
            return f.getAbsolutePath();
        }
    }

    public String getFilePath() {
        return fileTextField.getFilePath();
    }

    public JFileTextField getFileTextField() {
        return fileTextField;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        fileTextField.setEnabled(enabled);
        label.setEnabled(enabled);
    }

    public File getAbsoluteFile() {
        return fileTextField.getAbsoluteFile();
    }

    public File getFile() {
        return fileTextField.getFile();
    }

    public void setDefaultFolder(File defaultFolder) {
        fileTextField.setDefaultFolder(defaultFolder);
    }

}
