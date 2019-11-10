/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Digitizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author Vincent
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        helpBox = new javax.swing.JDialog();
        exitDiaBut = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        helpContent = new javax.swing.JTextArea();
        helpHeading = new javax.swing.JLabel();
        noFile = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        noFileText = new javax.swing.JTextArea();
        exitNoFileBut = new javax.swing.JButton();
        options = new javax.swing.JDialog();
        outputLabel = new javax.swing.JLabel();
        outputDirText = new javax.swing.JTextField();
        confirmOptions = new javax.swing.JButton();
        cancelOptions = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtRadio = new javax.swing.JRadioButton();
        pdfRadio = new javax.swing.JRadioButton();
        outputTypeGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        StartButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        File = new javax.swing.JMenu();
        Open = new javax.swing.JMenuItem();
        Exit = new javax.swing.JMenuItem();
        Edit = new javax.swing.JMenu();
        Options = new javax.swing.JMenuItem();
        Help = new javax.swing.JMenu();
        HelpDoc = new javax.swing.JMenuItem();

        fileChooser.setDialogTitle("");
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);

        helpBox.setTitle("Help Document");
        helpBox.setMinimumSize(new java.awt.Dimension(400, 300));

        exitDiaBut.setText("Ok");
        exitDiaBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitDiaButActionPerformed(evt);
            }
        });

        helpContent.setEditable(false);
        helpContent.setColumns(20);
        helpContent.setRows(5);
        helpContent.setText("*Supported images files consist of JPEDGand PNG\n*Will read a single PDF\n*Selecting a directory will only read supported images file.");
        jScrollPane2.setViewportView(helpContent);

        helpHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        helpHeading.setText("Help");

        javax.swing.GroupLayout helpBoxLayout = new javax.swing.GroupLayout(helpBox.getContentPane());
        helpBox.getContentPane().setLayout(helpBoxLayout);
        helpBoxLayout.setHorizontalGroup(
            helpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(helpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpBoxLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exitDiaBut))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(helpHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        helpBoxLayout.setVerticalGroup(
            helpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(helpHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitDiaBut)
                .addContainerGap())
        );

        noFile.setTitle("No Filles Selected");
        noFile.setMinimumSize(new java.awt.Dimension(400, 180));

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        noFileText.setEditable(false);
        noFileText.setColumns(20);
        noFileText.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        noFileText.setRows(1);
        noFileText.setText("No Files Selected");
        jScrollPane3.setViewportView(noFileText);

        exitNoFileBut.setText("ok");
        exitNoFileBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitNoFileButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout noFileLayout = new javax.swing.GroupLayout(noFile.getContentPane());
        noFile.getContentPane().setLayout(noFileLayout);
        noFileLayout.setHorizontalGroup(
            noFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(noFileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(noFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, noFileLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exitNoFileBut)))
                .addContainerGap())
        );
        noFileLayout.setVerticalGroup(
            noFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(noFileLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(exitNoFileBut)
                .addContainerGap())
        );

        options.setTitle("Options");
        options.setMinimumSize(new java.awt.Dimension(400, 300));

        outputLabel.setText("Output Directory: ");

        outputDirText.setText("./");

        confirmOptions.setText("Confirm");
        confirmOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmOptionsActionPerformed(evt);
            }
        });

        cancelOptions.setText("Cancel");
        cancelOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOptionsActionPerformed(evt);
            }
        });

        jLabel1.setText("Output Type: ");

        outputTypeGroup.add(txtRadio);
        txtRadio.setSelected(true);
        txtRadio.setText("Text");
        txtRadio.setActionCommand("Text");

        outputTypeGroup.add(pdfRadio);
        pdfRadio.setText("PDF");
        pdfRadio.setActionCommand("PDF");

        javax.swing.GroupLayout optionsLayout = new javax.swing.GroupLayout(options.getContentPane());
        options.getContentPane().setLayout(optionsLayout);
        optionsLayout.setHorizontalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(optionsLayout.createSequentialGroup()
                        .addComponent(outputLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputDirText))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(confirmOptions)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelOptions))
                    .addGroup(optionsLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pdfRadio)
                        .addGap(0, 215, Short.MAX_VALUE)))
                .addContainerGap())
        );
        optionsLayout.setVerticalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputLabel)
                    .addComponent(outputDirText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtRadio)
                    .addComponent(pdfRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelOptions)
                    .addComponent(confirmOptions))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Text Digitizer");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        StartButton.setText("Start");
        StartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });

        File.setText("File");

        Open.setText("Open");
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });
        File.add(Open);

        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        File.add(Exit);

        jMenuBar1.add(File);

        Edit.setText("Edit");

        Options.setText("Options");
        Options.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptionsActionPerformed(evt);
            }
        });
        Edit.add(Options);

        jMenuBar1.add(Edit);

        Help.setText("Help");

        HelpDoc.setText("Help Doc");
        HelpDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpDocActionPerformed(evt);
            }
        });
        Help.add(HelpDoc);

        jMenuBar1.add(Help);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(StartButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StartButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ArrayList<String> files = new ArrayList<String>();
    ArrayList<File> flist = new ArrayList<File>();
    
    private final String SCRIPT = "python src/main/python/ocr.py -f ";
    //python src/main/python/test.py

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String t = "";
            if (file.isFile()) {
                if ((file.getName().toLowerCase().endsWith(".jpg")
                        || file.getName().toLowerCase().endsWith(".png"))) {
                    files.add(file.getAbsolutePath());
                    flist.add(file);
                } else if (file.getName().toLowerCase().endsWith(".pdf")) {
                    parsePDF(file.getAbsolutePath());
                    flist.add(file);
                }
                t += file.getAbsolutePath() + "\n";
            } else {
                for (File f : file.listFiles()) {
                    if (f.isFile() && (f.getName().toLowerCase().endsWith(".jpg")
                            || f.getName().toLowerCase().endsWith(".png"))) {
                        files.add(f.getAbsolutePath());
                        flist.add(f);
                        t += f.getAbsolutePath() + "\n";
                    }
                }
            }
            textArea.setText(t);
        } else {
            System.out.println("File access cancelled.");
        }
    }//GEN-LAST:event_OpenActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_ExitActionPerformed

    private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartButtonActionPerformed
        if (files.isEmpty()) {
            noFile.setVisible(true);
            return;
        }
        for (int i = 0; i < files.size(); i++) {
            Process p;
            String line;
            try {
                if (files.get(i).startsWith("pdf")) {
                    String[] images = files.get(i).split("\n");
                    if (outputType.equals("Text")) {
                        FileWriter writer = new FileWriter(outputDir
                                + flist.get(i).getName().substring(0,
                                flist.get(i).getName().lastIndexOf(".")) + ".txt", true);
                        BufferedWriter bWriter = new BufferedWriter(writer);
                        for (int j = 1; j < images.length; j++) {
                            p = Runtime.getRuntime().exec(SCRIPT + images[j]);
                            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            while ((line = r.readLine()) != null) {
                                bWriter.write(line);
                                bWriter.newLine();
                                textArea.setText(textArea.getText() + line + "\n");
                            }
                        }
                        bWriter.close();
                    } else if (outputType.equals("PDF")) {
                        PDDocument document = new PDDocument();
                        for (int j = 1; j < images.length; j++) {
                            PDPage page = new PDPage();
                            document.addPage(page);
                            PDPageContentStream contentStream = new PDPageContentStream(document, page);
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                            contentStream.setLeading(14.5f);
                            contentStream.newLineAtOffset(50, 700); 
                            p = Runtime.getRuntime().exec(SCRIPT + images[j]);
                            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            while ((line = r.readLine()) != null) {
                                contentStream.showText(line);
                                contentStream.newLine();
                                textArea.setText(textArea.getText() + line + "\n");
                            }
                            contentStream.endText();
                            contentStream.close();
                        }
                        document.save(outputDir + flist.get(i).getName().substring(0,
                            flist.get(i).getName().lastIndexOf(".")) + ".pdf");
                        document.close();
                    }
                    //cleanTempImages(images);
                } else if (files.get(i).startsWith("err")) {
                    System.out.println("Error with reading pdf.");
                    //cleanTempImages(files.get(i).split("\n"));
                } else {
                    p = Runtime.getRuntime().exec(SCRIPT + files.get(i));
                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    if (outputType.equals("Text")) {
                        FileWriter writer = new FileWriter(outputDir + flist.get(i).getName().substring(0,
                                flist.get(i).getName().lastIndexOf(".")) + ".txt", true);
                        BufferedWriter bWriter = new BufferedWriter(writer);
                        while ((line = r.readLine()) != null) {
                            bWriter.write(line);
                            bWriter.newLine();
                            textArea.setText(textArea.getText() + line + "\n");
                        }
                        bWriter.close();
                    } else if (outputType.equals("PDF")) {
                        PDDocument document = new PDDocument();
                        PDPage page = new PDPage();
                        document.addPage(page);
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                        contentStream.setLeading(14.5f);
                        contentStream.newLineAtOffset(50, 700); 
                        while ((line = r.readLine()) != null) {
                            contentStream.showText(line);
                            contentStream.newLine();
                            textArea.setText(textArea.getText() + line + "\n");
                        }
                        contentStream.endText();
                        contentStream.close();
                        document.save(outputDir + flist.get(i).getName().substring(0,
                            flist.get(i).getName().lastIndexOf(".")) + ".pdf");
                        document.close();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        files = new ArrayList<String>();
        flist = new ArrayList<File>();
        textArea.setText(textArea.getText() + "Done!\n");
    }//GEN-LAST:event_StartButtonActionPerformed

    private void HelpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpDocActionPerformed
        helpBox.setVisible(true);
    }//GEN-LAST:event_HelpDocActionPerformed

    private void exitDiaButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitDiaButActionPerformed
        helpBox.setVisible(false);
    }//GEN-LAST:event_exitDiaButActionPerformed

    private void exitNoFileButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitNoFileButActionPerformed
        noFile.setVisible(false);
    }//GEN-LAST:event_exitNoFileButActionPerformed

    private String outputDir = "";
    private String outputType = "Text";

    private void OptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptionsActionPerformed
        outputDirText.setText(outputDir);
        options.setVisible(true);
    }//GEN-LAST:event_OptionsActionPerformed

    private void confirmOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmOptionsActionPerformed
        outputDir = outputDirText.getText();
        outputType = outputTypeGroup.getSelection().getActionCommand();
        options.setVisible(false);
    }//GEN-LAST:event_confirmOptionsActionPerformed

    private void cancelOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOptionsActionPerformed
        options.setVisible(false);
    }//GEN-LAST:event_cancelOptionsActionPerformed

    private final String TEMPDIR = "temp/";

    private void parsePDF(String fileName) {
        String out = "pdf";
        try {
            File f = new File(fileName);
            PDDocument doc = PDDocument.load(f);
            PDPageTree pages = doc.getPages();
            int i = 0;
            for (PDPage page : pages) {
                PDResources resources = page.getResources();
                for (COSName name : resources.getXObjectNames()) {
                    PDXObject obj = resources.getXObject(name);
                    if (obj instanceof PDImageXObject) {
                        PDImageXObject img = (PDImageXObject) obj;
                        String temp = TEMPDIR + f.getName().substring(0,
                                f.getName().lastIndexOf(".")) + i + ".jpg";
                        ImageIO.write(img.getImage(), "JPEG", new File(temp));
                        out += "\n" + temp;
                        i++;
                    }
                }
            }
            doc.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            out = "err" + out;
        }
        files.add(out);
        cleanTempImages(out.split("\n"));
    }

    private void cleanTempImages(String[] images) {
        for (int i = 1; i < images.length; i++) {
            File f = new File(images[i]);
            f.deleteOnExit();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (UnsupportedLookAndFeelException e) {
                        try {
                            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
                        } catch (UnsupportedLookAndFeelException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //catch (javax.swing.UnsupportedLookAndFeelException ex) {
        //    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        //}
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Edit;
    private javax.swing.JMenuItem Exit;
    private javax.swing.JMenu File;
    private javax.swing.JMenu Help;
    private javax.swing.JMenuItem HelpDoc;
    private javax.swing.JMenuItem Open;
    private javax.swing.JMenuItem Options;
    private javax.swing.JButton StartButton;
    private javax.swing.JButton cancelOptions;
    private javax.swing.JButton confirmOptions;
    private javax.swing.JButton exitDiaBut;
    private javax.swing.JButton exitNoFileBut;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JDialog helpBox;
    private javax.swing.JTextArea helpContent;
    private javax.swing.JLabel helpHeading;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JDialog noFile;
    private javax.swing.JTextArea noFileText;
    private javax.swing.JDialog options;
    private javax.swing.JTextField outputDirText;
    private javax.swing.JLabel outputLabel;
    private javax.swing.ButtonGroup outputTypeGroup;
    private javax.swing.JRadioButton pdfRadio;
    private javax.swing.JTextArea textArea;
    private javax.swing.JRadioButton txtRadio;
    // End of variables declaration//GEN-END:variables
}
