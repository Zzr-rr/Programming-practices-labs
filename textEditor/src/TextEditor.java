import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {

    JTextPane textPane;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JLabel fontLabel;

    // -- buttons --
    JButton fontColorButton;
    JButton fontItalicButton;
    JButton fontBoldButton;
    JButton fontUnderlineButton;

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

        // textPane
        textPane = new JTextPane();
        textPane.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));

        // scrollPane
        scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // spinner + fontLabel
        fontLabel = new JLabel("Font: ");
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(e -> textPane.setFont(new Font(textPane.getFont().getFamily(), textPane.getFont().getStyle(), (int) fontSizeSpinner.getValue())));

        // button
        fontColorButton = new JButton("Color ");
        fontItalicButton = new JButton("Italic ");
        fontBoldButton = new JButton("Bold ");
        fontUnderlineButton = new JButton("Underline ");
        fontColorButton.addActionListener(this);
        fontItalicButton.addActionListener(this);
        fontBoldButton.addActionListener(this);
        fontUnderlineButton.addActionListener(this);

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
        topPanel.add(fontUnderlineButton);
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
            textPane.setForeground(color);
        }
        if (e.getSource() == fontItalicButton) {
            StyledDocument doc = textPane.getStyledDocument();
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            Style italicStyle = textPane.addStyle("Italic", null);
            Style regularStyle = textPane.addStyle("Regular", null);
            StyleConstants.setItalic(italicStyle, true);
            StyleConstants.setItalic(regularStyle, false);
            boolean isAllItalic = true;
            for (int i = start; i < end; ++i) {
                Element element = doc.getCharacterElement(i);
                if (!StyleConstants.isItalic(element.getAttributes())) {
                    isAllItalic = false;
                    break;
                }
            }
            // reverse the condition of selected text.
            if (isAllItalic) {
                doc.setCharacterAttributes(start, end - start, regularStyle, false);
                if (start == end) {
                    doc.setCharacterAttributes(0, doc.getLength(), regularStyle, false);
                }
            } else {
                doc.setCharacterAttributes(start, end - start, italicStyle, false);
            }
            if (start < end) textPane.select(start, end);
        }
        if (e.getSource() == fontBoldButton) {
            StyledDocument doc = textPane.getStyledDocument();
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            Style boldStyle = textPane.addStyle("Bold", null);
            Style regularStyle = textPane.addStyle("Regular", null);
            StyleConstants.setBold(boldStyle, true);
            StyleConstants.setBold(regularStyle, false);
            boolean isAllBold = true;
            for (int i = start; i < end; i++) {
                Element element = doc.getCharacterElement(i);
                if (!StyleConstants.isBold(element.getAttributes())) {
                    isAllBold = false;
                    break;
                }
            }
            // reverse the condition of selected text.
            if (isAllBold) {
                doc.setCharacterAttributes(start, end - start, regularStyle, false);
                if (start == end) {
                    doc.setCharacterAttributes(0, doc.getLength(), regularStyle, false);
                }
            } else {
                doc.setCharacterAttributes(start, end - start, boldStyle, false);
            }
            if (start < end) textPane.select(start, end);
        }
        if (e.getSource() == fontUnderlineButton) {
            StyledDocument doc = textPane.getStyledDocument();
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            Style underlineStyle = textPane.addStyle("Underline", null);
            Style regularStyle = textPane.addStyle("Regular", null);
            StyleConstants.setUnderline(underlineStyle, true);
            StyleConstants.setUnderline(regularStyle, false);
            boolean isAllUnderlined = true;
            for (int i = start; i < end; ++i) {
                Element element = doc.getCharacterElement(i);
                if (!StyleConstants.isUnderline(element.getAttributes())) {
                    isAllUnderlined = false;
                    break;
                }
            }
            // reverse the condition of selected text.
            if (isAllUnderlined) {
                doc.setCharacterAttributes(start, end - start, regularStyle, false);
                if (start == end) {
                    doc.setCharacterAttributes(0, doc.getLength(), regularStyle, false);
                }
            } else {
                doc.setCharacterAttributes(start, end - start, underlineStyle, false);
            }
            if (start < end) textPane.select(start, end);
        }
        if (e.getSource() == fontBox) {
            textPane.setFont(new Font((String) fontBox.getSelectedItem(), textPane.getFont().getStyle(), textPane.getFont().getSize()));
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
                            textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), line, null);
                        }
                    }
                } catch (FileNotFoundException | BadLocationException ex) {
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
                    fileOut.println(textPane.getText());
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
