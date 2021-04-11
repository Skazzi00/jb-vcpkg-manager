package com.jetbrains.vcpkg;

import com.jetbrains.vcpkg.model.Package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class App {
    private JPanel panelMain;
    private JTabbedPane tabPanel;
    private JPanel Installed;
    private JPanel Install;

    private DefaultTableModel model;

    private JTable installedTable;
    private JTextField packageNameField;
    private JButton installButton;
    private JLabel loadingBar;
    private JTextField textField1;
    PackageService packageService;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Vcpkg Manager");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static void showInfo(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title, JOptionPane.ERROR_MESSAGE);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.setEnabled(true);
        tabPanel = new JTabbedPane();
        tabPanel.setForeground(new Color(-4512727));
        panelMain.add(tabPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        Installed = new JPanel();
        Installed.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabPanel.addTab("Installed", Installed);
        final JScrollPane scrollPane1 = new JScrollPane();
        Installed.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        installedTable.setAutoCreateRowSorter(false);
        installedTable.setAutoResizeMode(3);
        installedTable.setEnabled(true);
        installedTable.setFillsViewportHeight(true);
        installedTable.setGridColor(new Color(-16777216));
        installedTable.setShowHorizontalLines(true);
        installedTable.putClientProperty("html.disable", Boolean.FALSE);
        scrollPane1.setViewportView(installedTable);
        Install = new JPanel();
        Install.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabPanel.addTab("Install", Install);
        packageNameField = new JTextField();
        Install.add(packageNameField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        Install.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        installButton.setText("Install");
        Install.add(installButton, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        Install.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        Install.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Enter package name:");
        Install.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadingBar = new JLabel();
        loadingBar.setEnabled(true);
        loadingBar.setIcon(new ImageIcon(getClass().getResource("/Loading-bar.gif")));
        loadingBar.setText("");
        loadingBar.setVisible(false);
        Install.add(loadingBar, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    class DeletePopUp extends JPopupMenu {
        JMenuItem anItem;

        public DeletePopUp(String packageName, Runnable successCallback) {
            anItem = new JMenuItem("Delete");
            anItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        packageService.removePackage(packageName);
                        showInfo("Done!", "Deletion completed");
                        if (successCallback != null)
                            successCallback.run();
                    } catch (CommandFailureException commandFailureException) {
                        commandFailureException.printStackTrace();
                        showError(commandFailureException.getMessage(), "Error!");
                    }
                }
            });
            add(anItem);
        }

    }

    private void createUIComponents() {
        packageService = new VcpkgService(Runtime.getRuntime());
        installButton = new JButton();
        installButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loadingBar.setVisible(true);
                new SwingWorker<>() {
                    @Override
                    protected Object doInBackground() {
                        try {
                            packageService.installPackage(packageNameField.getText());
                            loadingBar.setVisible(false);
                            showInfo(packageNameField.getText() + " installed!", "Done!");
                            updatePackages();
                        } catch (CommandFailureException commandFailureException) {
                            loadingBar.setVisible(false);
                            commandFailureException.printStackTrace();
                            showError(commandFailureException.getMessage(), "Error!");
                        }
                        return null;
                    }
                }.execute();
            }
        });

        updatePackages();
        installedTable = new JTable(model);
        installedTable.setDefaultEditor(Object.class, null);
        installedTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int r = installedTable.rowAtPoint(e.getPoint());
                if (r >= 0 && r < installedTable.getRowCount()) {
                    installedTable.setRowSelectionInterval(r, r);
                } else {
                    installedTable.clearSelection();
                }

                int rowindex = installedTable.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = new DeletePopUp(
                            installedTable.getModel().getValueAt(rowindex, 0).toString(),
                            () -> model.removeRow(rowindex));
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void updatePackages() {
        Package[] packages = null;
        try {
            packages = packageService.listInstalledPackages();
        } catch (CommandFailureException e) {
            e.printStackTrace();
            showError(e.getMessage(), "Error!");
        }
        assert packages != null;
        String[][] data = Arrays.stream(packages)
                .map((p) -> new String[]{p.packageName, p.version}).toArray(String[][]::new);

        model = new DefaultTableModel(data, new String[]{"Package name", "Version"});
    }
}
