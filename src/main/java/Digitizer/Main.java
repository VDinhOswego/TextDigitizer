/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Digitizer;

import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
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
     * WorkTask works in the background to prevent the GUI from freezing.
     */
    class WorkTask extends SwingWorker<Void, Void> {

        /**
         * Sets Progress bar and calls the corresponding method to the current
         * outputMerge value to do the task.
         *
         * @return null
         */
        @Override
        protected Void doInBackground() {
            progressBar.setIndeterminate(true);
            if (outputMerge) {
                mergeOut();
            } else {
                separateOut();
            }
            return null;
        }

        /**
         * Resets selected files and returns cursor and start button to the
         * normal state.
         */
        @Override
        protected void done() {
            files = new ArrayList<String>();
            flist = new ArrayList<File>();
            setCursor(Cursor.getDefaultCursor());
            textArea.setText(textArea.getText() + "Done!\n");
            progressBar.setIndeterminate(false);
            startButton.setEnabled(true);
        }

        /**
         * Iterates through selected files and runs the OCR script on each.
         * After, the output of the script is written to either a plain text or
         * PDF file. Keeps output of selected files separate.
         */
        void separateOut() {
            for (int i = 0; i < files.size(); i++) {
                Process p;
                String line;
                try {
                    if (files.get(i).startsWith("pdf")) {
                        String[] images = files.get(i).split("\n");
                        if (outputType.equals("Text")) {
                            FileWriter writer = new FileWriter(outputDir
                                    + flist.get(i).getName().substring(0,
                                    flist.get(i).getName().lastIndexOf(".")) + ".txt", false);
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
                                contentStream.newLineAtOffset(25, 700);
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
                                    flist.get(i).getName().lastIndexOf(".")) + ".txt", false);
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
                            contentStream.newLineAtOffset(25, 700);
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
        }

        /**
         * Iterates through selected files and runs the OCR script on each.
         * After, the output of the script is written to either a plain text or
         * PDF file. Merges output of selected files under the first file's
         * name.
         */
        void mergeOut() {
            String outputName = outputDir + flist.get(0).getName().substring(0,
                    flist.get(0).getName().lastIndexOf("."));
            if (outputType.equals("Text")) {
                for (int i = 0; i < files.size(); i++) {
                    Process p;
                    String line;
                    try {
                        if (files.get(i).startsWith("pdf")) {
                            String[] images = files.get(i).split("\n");
                            FileWriter writer = new FileWriter(outputName + ".txt", true);
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
                            //cleanTempImages(images);
                        } else if (files.get(i).startsWith("err")) {
                            System.out.println("Error with reading pdf.");
                            //cleanTempImages(files.get(i).split("\n"));
                        } else {
                            p = Runtime.getRuntime().exec(SCRIPT + files.get(i));
                            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            FileWriter writer = new FileWriter(outputName + ".txt", true);
                            BufferedWriter bWriter = new BufferedWriter(writer);
                            while ((line = r.readLine()) != null) {
                                bWriter.write(line);
                                bWriter.newLine();
                                textArea.setText(textArea.getText() + line + "\n");
                            }
                            bWriter.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (outputType.equals("PDF")) {
                PDDocument document = new PDDocument();
                for (int i = 0; i < files.size(); i++) {
                    Process p;
                    String line;
                    try {
                        if (files.get(i).startsWith("pdf")) {
                            String[] images = files.get(i).split("\n");
                            for (int j = 1; j < images.length; j++) {
                                PDPage page = new PDPage();
                                document.addPage(page);
                                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                                contentStream.setLeading(14.5f);
                                contentStream.newLineAtOffset(25, 700);
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
                            //cleanTempImages(images);
                        } else if (files.get(i).startsWith("err")) {
                            System.out.println("Error with reading pdf.");
                            //cleanTempImages(files.get(i).split("\n"));
                        } else {
                            p = Runtime.getRuntime().exec(SCRIPT + files.get(i));
                            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            PDPage page = new PDPage();
                            document.addPage(page);
                            PDPageContentStream contentStream = new PDPageContentStream(document, page);
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                            contentStream.setLeading(14.5f);
                            contentStream.newLineAtOffset(25, 700);
                            while ((line = r.readLine()) != null) {
                                contentStream.showText(line);
                                contentStream.newLine();
                                textArea.setText(textArea.getText() + line + "\n");
                            }
                            contentStream.endText();
                            contentStream.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    document.save(outputName + ".pdf");
                    document.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

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
        exitNoFileBut = new javax.swing.JButton();
        noFileLabel = new javax.swing.JLabel();
        options = new javax.swing.JDialog();
        outputLabel = new javax.swing.JLabel();
        outputDirText = new javax.swing.JTextField();
        confirmOptions = new javax.swing.JButton();
        cancelOptions = new javax.swing.JButton();
        typeLabel = new javax.swing.JLabel();
        txtRadio = new javax.swing.JRadioButton();
        pdfRadio = new javax.swing.JRadioButton();
        outputBrowseButton = new javax.swing.JButton();
        outputMergeLabel = new javax.swing.JLabel();
        outputMergeCheck = new javax.swing.JCheckBox();
        outputTypeGroup = new javax.swing.ButtonGroup();
        fileChooser1 = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        startButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileBar = new javax.swing.JMenu();
        openItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editBar = new javax.swing.JMenu();
        optionsItem = new javax.swing.JMenuItem();
        helpBar = new javax.swing.JMenu();
        helpDocItem = new javax.swing.JMenuItem();

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Files", "jpg", "png", "pdf"));
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);

        helpBox.setTitle("Help Document");
        helpBox.setMinimumSize(new java.awt.Dimension(400, 380));
        helpBox.setPreferredSize(new java.awt.Dimension(400, 380));

        exitDiaBut.setText("Ok");
        exitDiaBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitDiaButActionPerformed(evt);
            }
        });

        helpContent.setEditable(false);
        helpContent.setColumns(20);
        helpContent.setRows(5);
        helpContent.setText("Open:\n-Supported images file types are JPEG and PNG.\n-Will read a single PDF file at a time.\n-Selecting a directory will only select images and PDF's inside \n  the directory.\n-Only multiple image files (no PDF's) at the same time.\n\nOptions:\n-The ouput location can be changed by selecting \"Browse\".\n-The output can be converted to a plain text file or PDF.\n-The output of multiple files can be merged into a single file.\n\nStart:\n-Click the Start button to start the conversions.\n-\"Done!\" will appear in the text area when the procees is done.");
        jScrollPane2.setViewportView(helpContent);

        helpHeading.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        helpHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        helpHeading.setText("Help");

        javax.swing.GroupLayout helpBoxLayout = new javax.swing.GroupLayout(helpBox.getContentPane());
        helpBox.getContentPane().setLayout(helpBoxLayout);
        helpBoxLayout.setHorizontalGroup(
            helpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(helpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(helpHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpBoxLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpBoxLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitDiaBut)
                .addContainerGap())
        );
        helpBoxLayout.setVerticalGroup(
            helpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(helpHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitDiaBut)
                .addContainerGap())
        );

        noFile.setTitle("No Filles Selected");
        noFile.setMinimumSize(new java.awt.Dimension(400, 180));

        exitNoFileBut.setText("ok");
        exitNoFileBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitNoFileButActionPerformed(evt);
            }
        });

        noFileLabel.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        noFileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noFileLabel.setText("No Files Selected");

        javax.swing.GroupLayout noFileLayout = new javax.swing.GroupLayout(noFile.getContentPane());
        noFile.getContentPane().setLayout(noFileLayout);
        noFileLayout.setHorizontalGroup(
            noFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(noFileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(noFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(noFileLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exitNoFileBut))
                    .addComponent(noFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        noFileLayout.setVerticalGroup(
            noFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(noFileLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitNoFileBut)
                .addContainerGap())
        );

        options.setTitle("Options");
        options.setMinimumSize(new java.awt.Dimension(400, 190));

        outputLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        outputLabel.setText("Output Directory: ");

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

        typeLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        typeLabel.setText("Output Type: ");

        outputTypeGroup.add(txtRadio);
        txtRadio.setSelected(true);
        txtRadio.setText("Text");
        txtRadio.setActionCommand("Text");

        outputTypeGroup.add(pdfRadio);
        pdfRadio.setText("PDF");
        pdfRadio.setActionCommand("PDF");

        outputBrowseButton.setText("Browse");
        outputBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputBrowseButtonActionPerformed(evt);
            }
        });

        outputMergeLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        outputMergeLabel.setText("Merge Output:");

        outputMergeCheck.setText("On");

        javax.swing.GroupLayout optionsLayout = new javax.swing.GroupLayout(options.getContentPane());
        options.getContentPane().setLayout(optionsLayout);
        optionsLayout.setHorizontalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsLayout.createSequentialGroup()
                        .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(optionsLayout.createSequentialGroup()
                                .addComponent(outputLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outputDirText, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                            .addGroup(optionsLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(confirmOptions)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelOptions)
                            .addComponent(outputBrowseButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(optionsLayout.createSequentialGroup()
                        .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(optionsLayout.createSequentialGroup()
                                .addComponent(typeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdfRadio))
                            .addGroup(optionsLayout.createSequentialGroup()
                                .addComponent(outputMergeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outputMergeCheck)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        optionsLayout.setVerticalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputLabel)
                    .addComponent(outputDirText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputBrowseButton))
                .addGap(18, 18, 18)
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(txtRadio)
                    .addComponent(pdfRadio))
                .addGap(18, 18, 18)
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputMergeLabel)
                    .addComponent(outputMergeCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelOptions)
                    .addComponent(confirmOptions))
                .addContainerGap())
        );

        fileChooser1.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Text Digitizer");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        fileBar.setText("File");

        openItem.setText("Open");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        fileBar.add(openItem);

        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileBar.add(exitItem);

        jMenuBar1.add(fileBar);

        editBar.setText("Edit");

        optionsItem.setText("Options");
        optionsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsItemActionPerformed(evt);
            }
        });
        editBar.add(optionsItem);

        jMenuBar1.add(editBar);

        helpBar.setText("Help");

        helpDocItem.setText("Help Doc");
        helpDocItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpDocItemActionPerformed(evt);
            }
        });
        helpBar.add(helpDocItem);

        jMenuBar1.add(helpBar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Lists of the sources
     */
    ArrayList<String> files = new ArrayList<String>();
    ArrayList<File> flist = new ArrayList<File>();

    /**
     * Command Line string to run OCR script
     */
    private final String SCRIPT = "python src/main/python/ocr.py -f ";
    //python src/main/python/test.py

    /**
     * Method to open the file chooser and populate source list
     *
     * @param evt event
     */
    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
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
                    } else if (f.isFile() && f.getName().toLowerCase().endsWith(".pdf")) {
                        parsePDF(f.getAbsolutePath());
                        flist.add(f);
                        t += f.getAbsolutePath() + "\n";
                    }
                }
            }
            textArea.setText(t);
        } else {
            System.out.println("File access cancelled.");
        }
    }//GEN-LAST:event_openItemActionPerformed

    /**
     * Closes the program
     *
     * @param evt event
     */
    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitItemActionPerformed

    /**
     * Checks if there are files selected and if there are then a WorkTask is
     * created and executed
     *
     * @param evt event
     */
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (files.isEmpty()) {
            noFile.setVisible(true);
            return;
        }
        startButton.setEnabled(false);
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        WorkTask task = new WorkTask();
        task.execute();
    }//GEN-LAST:event_startButtonActionPerformed

    /**
     * Opens the Help window
     *
     * @param evt event
     */
    private void helpDocItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpDocItemActionPerformed
        helpBox.setVisible(true);
    }//GEN-LAST:event_helpDocItemActionPerformed

    /**
     * Closes the Help window
     *
     * @param evt event
     */
    private void exitDiaButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitDiaButActionPerformed
        helpBox.setVisible(false);
    }//GEN-LAST:event_exitDiaButActionPerformed

    /**
     * Closes the no File alert
     *
     * @param evt event
     */
    private void exitNoFileButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitNoFileButActionPerformed
        noFile.setVisible(false);
    }//GEN-LAST:event_exitNoFileButActionPerformed

    /**
     * Variables representing the option values
     */
    private String outputDir = "";
    private String outputType = "Text";
    private boolean outputMerge = false;

    /**
     * Opens the options window and sets the current options
     *
     * @param evt event
     */
    private void optionsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsItemActionPerformed
        outputDirText.setText(outputDir);
        if (outputType.equals("PDF")) {
            pdfRadio.setSelected(true);
        } else if (outputType.equals("Text")) {
            txtRadio.setSelected(true);
        }
        outputMergeCheck.setSelected(outputMerge);
        options.setVisible(true);
    }//GEN-LAST:event_optionsItemActionPerformed

    /**
     * Applies the selected options and closes the Options window
     *
     * @param evt event
     */
    private void confirmOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmOptionsActionPerformed
        outputDir = outputDirText.getText();
        outputType = outputTypeGroup.getSelection().getActionCommand();
        outputMerge = outputMergeCheck.isSelected();
        options.setVisible(false);
    }//GEN-LAST:event_confirmOptionsActionPerformed

    /**
     * Closes the Options window discarding selected options
     *
     * @param evt event
     */
    private void cancelOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOptionsActionPerformed
        options.setVisible(false);
    }//GEN-LAST:event_cancelOptionsActionPerformed

    /**
     * Opens a file chooser and sets the selected Directory as the output
     * Directory
     *
     * @param evt event
     */
    private void outputBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputBrowseButtonActionPerformed
        if (fileChooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            outputDirText.setText(fileChooser1.getSelectedFile().getAbsolutePath());
        } else {
            System.out.println("File access cancelled.");
        }
    }//GEN-LAST:event_outputBrowseButtonActionPerformed

    /**
     * Directory where temporary images are saved
     */
    private final String TEMPDIR = "temp/";

    /**
     * Parses the image files from the given PDF and saves them as jpeg's to the
     * temp directory
     *
     * @param fileName Path to file
     */
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

    /**
     * Deletes given images on successful exit of program
     *
     * @param images String Array of image paths
     */
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
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code">
        //Sets LAF to the System's default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelOptions;
    private javax.swing.JButton confirmOptions;
    private javax.swing.JMenu editBar;
    private javax.swing.JButton exitDiaBut;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JButton exitNoFileBut;
    private javax.swing.JMenu fileBar;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JFileChooser fileChooser1;
    private javax.swing.JMenu helpBar;
    private javax.swing.JDialog helpBox;
    private javax.swing.JTextArea helpContent;
    private javax.swing.JMenuItem helpDocItem;
    private javax.swing.JLabel helpHeading;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JDialog noFile;
    private javax.swing.JLabel noFileLabel;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JDialog options;
    private javax.swing.JMenuItem optionsItem;
    private javax.swing.JButton outputBrowseButton;
    private javax.swing.JTextField outputDirText;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JCheckBox outputMergeCheck;
    private javax.swing.JLabel outputMergeLabel;
    private javax.swing.ButtonGroup outputTypeGroup;
    private javax.swing.JRadioButton pdfRadio;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton startButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JRadioButton txtRadio;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
}
