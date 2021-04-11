package com.jetbrains.vcpkg;

import com.jetbrains.vcpkg.model.Package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
