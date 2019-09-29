package net.vpc.dbclient.plugin.tool.neormf.settings;

import org.vpc.neormf.jbgen.config.ConfigNode;
import org.vpc.neormf.jbgen.config.ConfigFilter;
import net.vpc.dbclient.plugin.tool.neormf.NUtils;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.TreeSet;
import java.util.Collection;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 14 févr. 2009
 * Time: 20:19:45
 * To change this template use File | Settings | File Templates.
 */
public class ChangeFilterPanel extends NSettingPanel{

    public static class RegExpText extends JPanel{
        private JTextField textField;
        private JButton configButton;
        private String name;
        private TreeSet<String> values=new TreeSet<String>();
        private RegExpText(String name,String value) {
            this.name = name;
            configButton = new JButton("...");
            configButton.setMargin(new Insets(0, 0, 0, 0));
            textField = new JTextField(value);
            textField.setColumns(20);
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            add(textField);
            add(configButton);
            setBorder(BorderFactory.createEtchedBorder());
            configButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    configButtonAction();
                }
            });
        }
        
        private boolean extendedMatch(String pattern,String attrib,String value){
            ConfigNode configNode = new ConfigNode("pattern");
            configNode.setAttribute(attrib,pattern);
            return (configNode.accept(ConfigFilter.valueOf("pattern<"+attrib+"=\"" + value + "\">")[0]));
        }

        private void configButtonAction(){
            firePropertyChange("RegExpText.PreAction",null,this);
            Box verticalBox = Box.createVerticalBox();
            final Hashtable<String,JCheckBox> checks=new Hashtable<String, JCheckBox>();
            JToolBar jtb=new JToolBar();
            JButton selectAll = new JButton("++");
            JButton selectNone = new JButton("--");

            selectAll.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (JCheckBox jCheckBox : checks.values()) {
                        jCheckBox.setSelected(true);
                    }
                }
            });
            selectNone.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (JCheckBox jCheckBox : checks.values()) {
                        jCheckBox.setSelected(false);
                    }
                }
            });
            jtb.add(selectAll);
            jtb.add(selectNone);

            jtb.setFloatable(false);
            JPanel panel=new JPanel(new BorderLayout());
            panel.add(jtb,BorderLayout.PAGE_START);
            for (String value : values) {
                JCheckBox jCheckBox = new JCheckBox(value);
                jCheckBox.setSelected(extendedMatch(textField.getText(),"name",value));
                verticalBox.add(jCheckBox);
                checks.put(value,jCheckBox);
            }
            JScrollPane p=new JScrollPane(verticalBox);
            p.setPreferredSize(new Dimension(300,400));
            panel.add(p,BorderLayout.CENTER);

            int i = JOptionPane.showConfirmDialog(this, panel, "Select", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null);
            if(i==JOptionPane.OK_OPTION){
                Collection<JCheckBox> v = checks.values();
                textField.setText(getRegExpText(v));
            }
            firePropertyChange("RegExpText.Action",null,this);
        }

        private String getRegExpText(Collection<JCheckBox> checks){
            StringBuilder sb=new StringBuilder();
            boolean allSelected=true;
            for (JCheckBox check : checks) {
                if(check.isSelected()){
                    if(sb.length()>0){
                      sb.append("|");
                    }
                    sb.append(check.getText());
                }else{
                    allSelected=false;
                }
            }

            if(allSelected){
               return "*";
            }else if(sb.length()==0){
                return "";
            }
            return sb.toString();
        }

        private void setText(String exp){
            textField.setText(exp);
        }

        private String getText(){
            return textField.getText();
        }
        
        public void setEnabledComponent(boolean enabled) {
            textField.setEnabled(enabled);
            configButton.setEnabled(enabled);
        }

        public void setValues(Collection<String> values){
            this.values.clear();
            if (values!=null) {
                this.values.addAll(values);
            }
        }
    }

    RegExpText includeName = new RegExpText("includeName","*");
    RegExpText excludeName = new RegExpText("excludeName" ,"");
    RegExpText includeType = new RegExpText("includeType","*");
    RegExpText excludeType = new RegExpText("excludeType","");

    public ChangeFilterPanel(String tagId, String tagTitle, NeormfSettingsComponent neormfSettingsComponent) {
        super(tagId, tagTitle, neormfSettingsComponent);
        this.setLayout(new DumbGridBagLayout()
                .addLine("[<includeNameLabel][<-=includeName]")
                .addLine("[<includeTypeLabel][<-=includeType]")
                .addLine("[<excludeNameLabel][<-=excludeName]")
                .addLine("[<excludeTypeLabel][<-=excludeType]")
                .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        this.add(new JLabel("Include Type"), "includeTypeLabel");
        this.add(includeType, "includeType");
        this.add(new JLabel("Include Name"), "includeNameLabel");
        this.add(includeName, "includeName");
        this.add(new JLabel("Exclude Type"), "excludeTypeLabel");
        this.add(excludeType, "excludeType");
        this.add(new JLabel("Exclude Name"), "excludeNameLabel");
        this.add(excludeName, "excludeName");

    }

    public void load(ConfigNode node) {
        if (node == null) {
            includeName.setText("*");
            includeType.setText("*");
            excludeName.setText("");
            excludeType.setText("");
            return;
        }
        ConfigNode child = NUtils.findChild(node,"include",true,false, NUtils.NotFoundAction.DISABLE);
        includeName.setText(child == null ? "*" : child.getName());
        includeType.setText(child == null ? "*" : child.getAttribute("type"));
        child = NUtils.findChild(node,"exclude", true,false, NUtils.NotFoundAction.DISABLE);
        excludeName.setText(child == null ? null : child.getName());
        excludeType.setText(child == null ? null : child.getAttribute("type"));
    }

    public void store(ConfigNode node) {
        if (includeName.getText().equals("*") && includeType.getText().equals("*")) {
            NUtils.findChild(node,"include",NUtils.NotFoundAction.DELETE).remove();
        } else {
            ConfigNode includeNode = NUtils.findChild(node, "include", NUtils.NotFoundAction.DELETE);
            includeNode.setName(includeName.getText());
            includeNode.setAttribute("type", includeType.getText());
        }
        if (excludeName.getText().equals("") && excludeType.getText().equals("")) {
            NUtils.findChild(node, "exclude",NUtils.NotFoundAction.DELETE).remove();
        } else {
            ConfigNode excludeNode = NUtils.findChild(node, "exclude", NUtils.NotFoundAction.DELETE);
            excludeNode.setName(excludeName.getText());
            excludeNode.setAttribute("type", excludeType.getText());
        }
    }

    public void setEnabledComponent(boolean enabled) {
        includeName.setEnabledComponent(enabled);
        excludeName.setEnabledComponent(enabled);
        includeType.setEnabledComponent(enabled);
        excludeType.setEnabledComponent(enabled);
    }

    public RegExpText getExcludeName() {
        return excludeName;
    }

    public RegExpText getExcludeType() {
        return excludeType;
    }

    public RegExpText getIncludeName() {
        return includeName;
    }

    public RegExpText getIncludeType() {
        return includeType;
    }
}

