import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
public class Editor extends JFrame implements ActionListener {
    public static int tsz = 14;
    JTextArea txt;
    JScrollPane scrl;
    JLabel fntszlbl;
    JSpinner fntsz;
    //=============================
    JMenuBar menu;
    JMenu file;
    JMenu edit;
    JMenu fnt;
    JMenu fnttype;
    JMenuItem newfile;
    JMenuItem save;
    JMenuItem open;
    JMenuItem exit;
    JMenu help;
    JMenuItem about;
    JMenuItem fsize;
    JFrame f;
    JFrame fsz;
    Editor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TextPad");
        this.setSize(800,600);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        txt = new JTextArea();
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setFont(new Font("Arial",Font.PLAIN,tsz));
        scrl = new JScrollPane(txt);
        scrl.setPreferredSize(new Dimension(775,528));
        scrl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fntszlbl = new JLabel("Size:");
        fntsz = new JSpinner();
        fntsz.setPreferredSize(new Dimension(60,20));
        fntsz.setValue(14);
        fntsz.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                txt.setFont(new Font(txt.getFont().getFamily(),Font.PLAIN, (int) fntsz.getValue()));
            }
        });
        //=================Menu=================
            menu = new JMenuBar();
            file = new JMenu("File");
            newfile = new JMenuItem("New");
            open = new JMenuItem("Open");
            save = new JMenuItem("Save");
            exit = new JMenuItem("Exit");
            edit = new JMenu("Edit");
            fnt =  new JMenu("Font");
            fnttype = new JMenu("Font Type");
            fsize = new JMenu("Font Size");
            help = new JMenu("Help");
            about = new JMenuItem("About");

            newfile.addActionListener(this);
            open.addActionListener(this);
            save.addActionListener(this);
            exit.addActionListener(this);
            about.addActionListener(this);
            fsize.addActionListener(this);

            file.add(newfile);
            file.add(open);
            file.add(save);
            file.add(exit);
            menu.add(file);
            edit.add(fnt);
            fnt.add(fsize);
            menu.add(help);
            help.add(about);
        //=================Menu=================
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        this.setJMenuBar(menu);
        this.add(scrl);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
                if(e.getSource()==newfile){
                    txt.setText(null);
                }
                if(e.getSource()==open){
                    JFileChooser filechsr = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("TextPad Document Files","tdf");
                    filechsr.setFileFilter(filter);
                    int response = filechsr.showOpenDialog(null);
                    if(response == JFileChooser.APPROVE_OPTION){
                            File docfile = new File(filechsr.getSelectedFile().getAbsolutePath());
                            Scanner filein = null;
                        try {
                            txt.setText(null);
                            filein = new Scanner(docfile);
                            if(docfile.isFile()){
                                while(filein.hasNextLine()) {
                                    String line = filein.nextLine() + "\n";
                                    txt.append(line);
                                }
                            }
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(f, "Unable to open document.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException(ex);
                        }
                        finally {
                            filein.close();
                        }
                    }
                }
                if(e.getSource()==save){
                    JFileChooser filechsr = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("TextPad Document Files","tdf");
                    filechsr.setFileFilter(filter);
                    int response = filechsr.showSaveDialog(null);
                    if(response==JFileChooser.APPROVE_OPTION){
                        File filetxt;
                        PrintWriter fileout = null;
                        filetxt = new File(filechsr.getSelectedFile().getAbsolutePath());
                        try {
                            fileout = new PrintWriter(filetxt);
                            fileout.println(txt.getText());
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(f, "Unable to save document.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException(ex);
                        }
                        finally {
                            fileout.close();

                        }

                    }
                }
                if(e.getSource()==fsize){

                }
                if(e.getSource()==about){
                    f=new JFrame();
                    JOptionPane.showMessageDialog(f,"TextPad™ v1.00, Copyright © 2023 BenjaminIsEpic."+"\nFor TextPad updates goto:\nhttps://github.com/BenjaminIsEpic/textpadjava");


                }
                if(e.getSource()==exit){
                    System.exit(0);
                }
            }
        }