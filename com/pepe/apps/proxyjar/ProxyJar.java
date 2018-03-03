package com.pepe.apps.proxyjar;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.*;

/**
 *
 * @author pepe
 */
public class ProxyJar extends JFrame {

    private final String[] tabHeadings
        = new String[]{"IP Address", "Port", "Country", "Type", "Last Update", "Response Times"};
    private final String[] comboXmxItem = {"None", "Gigabytes", "Megabytes"};
    private Object[][] tabObjects;
    private JFileChooser fileChooser;
    private File fileSelected;
    private String pathFile;
    private String temp;
    private DefaultTableModel tabModel;
    private DefaultTableCellRenderer centerRenderer;
    private int selectedRow;

    public ProxyJar() {
        ReadDB.DATA = null;
        new ReadDB();
        initValues();
        initUI();
    }

    private void initValues() {        
        this.fileChooser = new JFileChooser();
        tabObjects = ReadDB.DATA;        
        initTableModel(tabObjects, tabHeadings);
    }

    private void initTable() {
        tab.setModel(tabModel);
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 6; i++) {
            tab.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tab.getTableHeader().setReorderingAllowed(false);
        scrollTab.setViewportView(tab);
    }
    
    private void initTableModel(Object[][] data, Object[] columns) {
        this.tabModel = new DefaultTableModel(data, columns) {
            Class[] types = new Class[]{
                String.class, Integer.class, String.class, String.class
            };

//            public Class getColumnClass(int columnIndex) {
//                return types[columnIndex];
//            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
    }
    
    private void initUI() {
        initBar();

        lbl1 = new JLabel("Selected Server:");
        lbl2 = new JLabel("Selected Jar File:");
        lbl3 = new JLabel("Memory Settings (Optional):");
        lbl4 = new JLabel("Java Arguments:");
        txtFile = new JTextField();
        txtCell = new JTextField();
        txtXmx = new JTextField();
        txtArgs = new JTextField();
        btnSelect = new JButton("Select Jar File...");
        btnExec = new JButton("Exec");
        btnSave = new JButton("Save As...");
        btnUpdate = new JButton("Update");
        tab = new JTable();
        scrollTab = new JScrollPane();
        comboXmx = new JComboBox();

        setTitle("ProxyJar");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initTable();        
        txtFile.setEditable(false);
        txtCell.setEditable(false);
        comboXmx.setModel(new DefaultComboBoxModel(comboXmxItem));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCell, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTab, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnExec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbl3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtXmx, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboXmx, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbl4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtArgs))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSelect)
                                .addGap(18, 18, 18)
                                .addComponent(lbl2)
                                .addGap(12, 12, 12)
                                .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollTab, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl1)
                    .addComponent(txtCell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave)
                    .addComponent(btnUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSelect)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbl2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl3)
                                .addComponent(txtXmx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboXmx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl4)
                            .addComponent(txtArgs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnExec, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        pack();

        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                tabMouseListener(evt);
            }
        });
        
        txtXmx.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                txtXmxKeyListener(evt);
            }
        });
        
        comboXmx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                comboXmxItemStateChange(e);
            }
        });
        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        btnExec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnExecActionPerformed(evt);
            }
        });
    }

    private void initBar() {
        bar = new JMenuBar();
        menu1 = new JMenu("ProxyJar");
        itemExit = new JMenuItem("Exit");
        menu2 = new JMenu("Help");
        itemHelp = new JMenuItem("Instructions");

        menu1.add(itemExit);

        menu2.add(itemHelp);

        bar.add(menu1);
        bar.add(menu2);

        setJMenuBar(bar);
    }

    private void tabMouseListener(MouseEvent evt) {
        if (evt.getClickCount() == 1) {
            JTable cell = (JTable) evt.getSource();
            selectedRow = cell.getSelectedRow();
            txtCell.setText(ReadDB.DATA[selectedRow][0] + ":" + ReadDB.DATA[selectedRow][1]);
            txtArgs.setText(
                "-DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1] + " -jar " + txtFile.getText()
            );
        }
    }   
    
    private void txtXmxKeyListener(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {            
            switch(comboXmx.getSelectedItem().toString()) {                
                case "None":
                    if (!txtArgs.getText().equals("")) {
                            txtArgs.setText("-DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                                          + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1] 
                                          + " -jar " + txtFile.getText());  
                    }
                break;
                    
                case "Gigabytes":
                    if(!txtArgs.getText().equals("")) {
                        txtArgs.setText(
                            "-DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                            + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1]
                            + " -Xmx" + txtXmx.getText() + "G"
                            + " -jar " + txtFile.getText());
                    }
                break;

                case "Megabytes":
                    if(!txtArgs.getText().equals("")) {
                        txtArgs.setText(
                            "-DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                            + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1]
                            + " -Xmx" + txtXmx.getText() + "m"
                            + " -jar " + txtFile.getText());
                    }
                break;
            }
        }
    }
    
    private void comboXmxItemStateChange(ItemEvent evt) {
        if (!txtXmx.getText().equals("")) {
            switch(comboXmx.getSelectedItem().toString()) {                
                case "None":
                    if (!txtArgs.getText().equals("")) {
                            txtArgs.setText("-DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                                          + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1] 
                                          + " -jar " + txtFile.getText());  
                    }
                break;
                    
                case "Gigabytes":
                    if(!txtArgs.getText().equals("")) {
                        txtArgs.setText(
                            "-DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                            + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1]
                            + " -Xmx" + txtXmx.getText() + "G"
                            + " -jar " + txtFile.getText());
                    }
                break;

                case "Megabytes":
                    if(!txtArgs.getText().equals("")) {
                        txtArgs.setText(
                            "-DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                            + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1]
                            + " -Xmx" + txtXmx.getText() + "m"
                            + " -jar " + txtFile.getText());
                    }
                break;
            }
        }
    }
    
    private void btnSaveActionPerformed(ActionEvent evt) {
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        File file = null;
        BufferedWriter writer = null;
        try {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                writer = new BufferedWriter(new FileWriter(file));
                for(int i = 0; i < 25; i++) {
                    writer.append(ReadDB.DATA[i][0] + ":" + ReadDB.DATA[i][1] + "\n");                    
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void btnUpdateActionPerformed(ActionEvent evt) {
        ReadDB.DATA = null;
        new ReadDB().setVisible(false);
        this.tabObjects = ReadDB.DATA;
        tabModel.setDataVector(tabObjects, tabHeadings);
        initTable();
    }

    private void btnSelectActionPerformed(ActionEvent evt) {
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectFile();
        }
    }

    private void btnExecActionPerformed(ActionEvent evt) {
        String cmd = null;
        if(!txtXmx.getText().equals("")) {
            switch(comboXmx.getSelectedItem().toString()) {
                case "Gigabytes":
                cmd = "java -DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                    + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1]
                    + " -Xmx" + txtXmx.getText() + "G"
                    + " -jar " + txtFile.getText();
                break;
                
                case "Megabytes":
                cmd = "java -DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                    + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1]
                    + " -Xmx" + txtXmx.getText() + "m"
                    + " -jar " + txtFile.getText();
                break;
            }
        } else {
            cmd = "java -DsocksProxyHost=" + ReadDB.DATA[selectedRow][0]
                + " -DsocksProxyPort=" + ReadDB.DATA[selectedRow][1]                    
                + " -jar " + txtFile.getText();
        }
        try {
            if(!txtFile.getText().equals("")) {
                Runtime.getRuntime().exec(cmd);                                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectFile() {
        fileSelected = fileChooser.getSelectedFile();
        pathFile = fileSelected.getAbsolutePath();

        temp = pathFile;
        temp = temp.substring(temp.indexOf("."));

        if (temp.equals(".jar")) {
            txtFile.setText(pathFile);
        } else {
            JOptionPane.showMessageDialog(null,
                    "ERROR!!!: Invalid Route or File!"
                    + "\n"
                    + "Don't select the file in a hidden directory.");
        }
    }

    public static void main(String[] args) {
        new ProxyJar().setVisible(true);
    }

    private JMenuBar bar;
    private JMenu menu1;
    private JMenuItem itemExit;
    private JMenu menu2;
    private JMenuItem itemHelp;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JTable tab;
    private JScrollPane scrollTab;
    private JComboBox comboXmx;
    private JButton btnSelect;
    private JButton btnExec;
    private JButton btnSave;
    private JButton btnUpdate;
    private JTextField txtFile;
    private JTextField txtCell;
    private JTextField txtXmx;
    private JTextField txtArgs;
}
