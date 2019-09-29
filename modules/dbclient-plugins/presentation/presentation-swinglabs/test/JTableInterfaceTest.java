
import org.jdesktop.swingx.JXTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.*;
import java.awt.*;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 5 déc. 2008
 * Time: 15:21:02
 * To change this template use File | Settings | File Templates.
 */
public class JTableInterfaceTest {

    public static void main(String[] args) {
        JXTable t = new JXTable();
        t.setDefaultRenderer(Object.class,
                new DefaultTableRenderer() {

                    Color initialColor;

                    @Override
                    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (initialColor == null) {
                            initialColor = Color.BLACK;
                        }
                        TableModel pm = jtable.getModel();
                        String[] keyValue = new String[]{(String) pm.getValueAt(row, 0), (String) pm.getValueAt(row, 1)};
                        if (keyValue[0].startsWith("java.")) {
                            setForeground(Color.GREEN.darker().darker());
//                            System.out.println(getForeground() + " :(green) " + keyValue[0]);
                        } else if (keyValue[0].startsWith("user.")) {
                            setForeground(Color.BLUE.darker());
//                            System.out.println(getForeground() + " :(blue) " + keyValue[0]);
                        } else if (keyValue[0].startsWith("os.")
                                || keyValue[0].startsWith("awt.")
                                || keyValue[0].startsWith("java.awt.")
                                || keyValue[0].startsWith("line.")
                                || keyValue[0].startsWith("file.")
                                || keyValue[0].startsWith("path.")
                                || keyValue[0].startsWith("sun.")) {
                            setForeground(Color.RED.darker());
//                            System.out.println(getForeground() + " :(RED+) " + keyValue[0]);
                        } else {
                            setForeground(Color.YELLOW.darker());
//                            System.out.println(getForeground() + " :(yellow)  " + keyValue[0]);
                            //setForeground(initialColor);
                        }
//                        super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
//                        System.out.println("\t" + getForeground() + " : " + keyValue[0]);
                        return super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
                    }
                });
//        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//
//            Color initialColor;
//
//            public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                if (initialColor == null) {
//                    initialColor = getForeground();
//                }
//                super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
//                TableModel pm = jtable.getModel();
//                String[] keyValue = new String[]{(String) pm.getValueAt(row, 0), (String) pm.getValueAt(row, 1)};
//                if (keyValue[0].startsWith("java.")) {
//                    setForeground(Color.GREEN.darker().darker());
//                    System.out.println(getForeground() + " :(green) " + keyValue[0]);
//                } else if (keyValue[0].startsWith("user.")) {
//                    setForeground(Color.BLUE.darker());
//                    System.out.println(getForeground() + " :(blue) " + keyValue[0]);
//                } else if (keyValue[0].startsWith("os.")
//                        || keyValue[0].startsWith("awt.")
//                        || keyValue[0].startsWith("java.awt.")
//                        || keyValue[0].startsWith("line.")
//                        || keyValue[0].startsWith("file.")
//                        || keyValue[0].startsWith("path.")
//                        || keyValue[0].startsWith("sun.")) {
//                    setForeground(Color.RED.darker());
//                    System.out.println(getForeground() + " :(RED+) " + keyValue[0]);
//                } else {
//                    setForeground(Color.YELLOW.darker());
//                    System.out.println(getForeground() + " :(yellow)  " + keyValue[0]);
//                    //setForeground(initialColor);
//                }
//                super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
//                System.out.println("\t" + getForeground() + " : " + keyValue[0]);
//                return this;
//            }
//        });
        t.setModel(new DefaultTableModel(new Object[][]{{"user.machin", "12"}, {"os.machine", "09"}, {"toto.pkoa", "01"}}, new Object[]{"A", "B"}));
        JFrame f = new JFrame();
        f.add(new JScrollPane(t));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
