import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JLabel fontLabel;

    // -- buttons --
    JButton fontColorButton;
    JButton fontItalicButton;
    boolean italic;

    JButton fontBoldButton;
    boolean bold;

    // -- /buttons --

    JComboBox<String> fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JPanel topPanel;


    TextEditor() {
        // basic layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Bro text Editor");
        this.setSize(600, 600);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // textArea
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));

        // scrollPane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // spinner + fontLabel
        fontLabel = new JLabel("Font: ");
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(e -> textArea.setFont(new Font(textArea.getFont().getFamily(), textArea.getFont().getStyle(), (int) fontSizeSpinner.getValue())));

        // button
        fontColorButton = new JButton("Color ");
        fontColorButton.addActionListener(this);
        fontItalicButton = new JButton("Italic ");
        italic = false;
        fontItalicButton.addActionListener(this);
        fontBoldButton = new JButton("Bold ");
        bold = false;
        fontBoldButton.addActionListener(this);


        // fontBox
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox<>(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Microsoft YaHei");

        // ----- menubar -----
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        // ----- /menubar ----

        topPanel = new JPanel();
        topPanel.add(fontLabel);
        topPanel.add(fontSizeSpinner);
        topPanel.add(fontItalicButton);
        topPanel.add(fontBoldButton);
        topPanel.add(fontColorButton);
        topPanel.add(fontBox);

        this.setJMenuBar(menuBar);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fontColorButton) {
            Color color = JColorChooser.showDialog(null, "Choose a color", Color.black);
            textArea.setForeground(color);
        }
        if (e.getSource() == fontItalicButton) {
            if (italic) {
                textArea.setFont(new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle() & ~Font.ITALIC, textArea.getFont().getSize()));
            } else {
                textArea.setFont(new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle() | Font.ITALIC, textArea.getFont().getSize()));
            }
            italic = !italic;
        }
        if (e.getSource() == fontBoldButton) {
            if (bold) {
                textArea.setFont(new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle() & ~Font.BOLD, textArea.getFont().getSize()));
                textArea.setFont(new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle() | Font.ITALIC, textArea.getFont().getSize()));
                textArea.setFont(new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle() & ~Font.ITALIC, textArea.getFont().getSize()));
            } else {
                textArea.setFont(new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle() | Font.BOLD, textArea.getFont().getSize()));
            }
            bold = !bold;
        }
        if (e.getSource() == fontBox) {
            textArea.setFont(new Font((String) fontBox.getSelectedItem(), textArea.getFont().getStyle(), textArea.getFont().getSize()));
        }
        if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "HTML");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try (Scanner fileIn = new Scanner(file)) {
                    if (file.isFile()) {
                        while (fileIn.hasNextLine()) {
                            String line = fileIn.nextLine() + "\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
        if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try (PrintWriter fileOut = new PrintWriter(file)) {
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (e.getSource() == exitItem) {
            System.exit(0);
        }
    }
}
