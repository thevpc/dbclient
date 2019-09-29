// LastModifier by taha : 27/09/2002 01:06
package net.vpc.dbclient.plugin.tool.recordeditor;

import javax.swing.*;

import java.awt.*;
import java.util.Date;
import java.util.TimerTask;

public class BasicStatusBar extends JLabel {
    protected java.util.Timer timer;
    protected Box content;
    private int heigth;

    public static final long DELAY = 10000;
    public static final Color ERROR_COLOR = new Color(255,0,0);
    public static final Color SUCCESS_COLOR = new Color(1,128,0);
    public static final Icon SUCCESS_ICON = null;//Resources.loadImageIcon("/images/net/vpc/app/swing/Success.gif");
    public static final Color WARNING_COLOR = new Color(255,153,0);
    public static final Color INFO_COLOR = new Color(82,109,165);

    private long delay = DELAY;
    private Color errorColor = ERROR_COLOR;
    private Icon errorIcon = null;
    private Color successColor = SUCCESS_COLOR;
    private Icon successIcon = SUCCESS_ICON;
    private Color warningColor = WARNING_COLOR;
    private Icon warningIcon = null;
    private Color infoColor = INFO_COLOR;
    private Icon infoIcon = null;

    public Color getErrorColor() {
        return errorColor;
    }

    public void setErrorColor(Color errorColor) {
        this.errorColor = errorColor;
    }

    public Icon getErrorIcon() {
        return errorIcon;
    }

    public void setErrorIcon(Icon errorIcon) {
        this.errorIcon = errorIcon;
    }

    public Color getSuccessColor() {
        return successColor;
    }

    public void setSuccessColor(Color successColor) {
        this.successColor = successColor;
    }

    public Icon getSuccessIcon() {
        return successIcon;
    }

    public void setSuccessIcon(Icon successIcon) {
        this.successIcon = successIcon;
    }

    public Color getWarningColor() {
        return warningColor;
    }

    public void setWarningColor(Color warningColor) {
        this.warningColor = warningColor;
    }

    public Icon getWarningIcon() {
        return warningIcon;
    }

    public void setWarningIcon(Icon warningIcon) {
        this.warningIcon = warningIcon;
    }

    public Color getInfoColor() {
        return infoColor;
    }

    public void setInfoColor(Color infoColor) {
        this.infoColor = infoColor;
    }

    public Icon getInfoIcon() {
        return infoIcon;
    }

    public void setInfoIcon(Icon infoIcon) {
        this.infoIcon = infoIcon;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public BasicStatusBar() {
        this(25, DELAY);
    }

    public void showModalSuccess(String message) {
        showSuccess(message);
        JOptionPane.showMessageDialog(this,message,null,JOptionPane.INFORMATION_MESSAGE);
    }

    public BasicStatusBar(int heigth) {
        this(heigth, DELAY);
    }

    public void showModalError(String message) {
        showError(message);
        JOptionPane.showMessageDialog(this,message,("Error"),JOptionPane.ERROR_MESSAGE);
    }

    public void showModalError(Throwable message) {
        showError(message);
        JOptionPane.showMessageDialog(this,message,("Error"),JOptionPane.ERROR_MESSAGE);
    }

    public void showModalInfo(String message) {
        showInfo(message);
        JOptionPane.showMessageDialog(this,message,null,JOptionPane.INFORMATION_MESSAGE);
    }

    public void showModalWarning(String message) {
        showWarning(message);
        JOptionPane.showMessageDialog(this,message,"Attention",JOptionPane.WARNING_MESSAGE);
    }


    public BasicStatusBar(int heigth, long delay) {
        super();
        this.heigth=heigth;
        this.delay = delay;
        setFont(new java.awt.Font("Arial", 1, 10));
        setBorder(BorderFactory.createRaisedBevelBorder());
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);
        //setPreferredSize(getSize());
    }

    /*
    public void setText(String text){
        setText(text,null,DELAY);
    }
    public void setText(String text,java.awt.Color color){
        setText(text,color,DELAY);
    }
    */

    public void showMessage(String text, java.awt.Color color, Icon icon, long intervall) {
        String tcolor=(color==errorColor) ? "ERROR":(color==infoColor)? "INFO":(color==successColor)? "SUCCESS" :(color==warningColor)? "WARNING" :"???";
        //System.out.println("["+new Date()+"] : "+tcolor+" : "+text);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (color != null) {
            setForeground(color);
        }
        setText(text);
        setToolTipText("[Dernier message : " +(new Date())+"] "+ text);
        setIcon(icon);
        if (intervall > 0) {
            timer = new java.util.Timer(true);
            timer.schedule(new TimerTask() {
                public void run() {
                    clear();
                }
            }
                    , new Date(new Date().getTime() + intervall));
            //label.setText(text);
        }
    }

    public JLabel getLabel() {
        return this;
    }

    /*
    public void paint(java.awt.Graphics g) {
            super.paint(g);
    }*/

    public void clear() {
        setText("");
        setIcon(null);
    }


    public void showInfo(String message) {
        showMessage(message, infoColor , infoIcon, delay);
    }

    public void showError(String message) {
        Toolkit.getDefaultToolkit().beep();
        showMessage(message, errorColor, errorIcon, delay);
    }
    public void showWarning(String message) {
//        Toolkit.getDefaultToolkit().beep();
        showMessage(message,warningColor, warningIcon, delay);
    }

    /*
     public void showError(String message,String debugString){
         showError(message);
         //System.out.println (debugString);
     }
     */
    public void showSuccess(String message) {
        showMessage(message, successColor, successIcon, delay);
    }

    public void showError(Throwable ce) {
//        ConstraintsEvent c = new ConstraintsEvent(this);
        String msg = ce.getMessage();
        if (msg == null || msg.length() == 0) {
            msg = (ce instanceof java.sql.SQLException)
                    ?
                    ("[Base]:" + ce.getMessage())
                    :
                    ce.toString();
        }
        showError(msg);
    }


    public Dimension getPreferredSize(){
        Dimension d=super.getPreferredSize();
        return new Dimension(d.width,heigth);
    }

    public Dimension getMinimumSize(){
        Dimension d=super.getMinimumSize();
        return new Dimension(d.width,heigth);
    }

    public Dimension getMaximumSize(){
        Dimension d=super.getMaximumSize();
        return new Dimension(d.width,heigth);
    }

}
