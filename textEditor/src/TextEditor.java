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
            if ((textPane.getFont().getStyle() & Font.ITALIC) != 0) {
                textPane.setFont(new Font(textPane.getFont().getFontName(), textPane.getFont().getStyle() & ~Font.ITALIC, textPane.getFont().getSize()));
            } else {
                textPane.setFont(new Font(textPane.getFont().getFontName(), textPane.getFont().getStyle() | Font.ITALIC, textPane.getFont().getSize()));
            }
        }
        if (e.getSource() == fontBoldButton) {
            if ((textPane.getFont().getStyle() & Font.BOLD) != 0) {
                textPane.setFont(new Font(textPane.getFont().getFontName(), textPane.getFont().getStyle() & ~Font.BOLD, textPane.getFont().getSize()));
                textPane.setFont(new Font(textPane.getFont().getFontName(), textPane.getFont().getStyle() | Font.ITALIC, textPane.getFont().getSize()));
                textPane.setFont(new Font(textPane.getFont().getFontName(), textPane.getFont().getStyle() & ~Font.ITALIC, textPane.getFont().getSize()));
            } else {
                textPane.setFont(new Font(textPane.getFont().getFontName(), textPane.getFont().getStyle() | Font.BOLD, textPane.getFont().getSize()));
            }
        }
        if (e.getSource() == fontUnderlineButton) {
            boolean allUnderlined = true;
            for (int m = 0; m < textPane.getStyledDocument().getLength(); ++m) {
                Element element = textPane.getStyledDocument().getCharacterElement(m);
                AttributeSet attrs = element.getAttributes();
                if (!StyleConstants.isUnderline(attrs)) {
                    allUnderlined = false;
                    break;
                }
            }
            if (allUnderlined) {
                Style style = textPane.addStyle("Underline", null);
                StyleConstants.setUnderline(style, false);
                textPane.getStyledDocument().setCharacterAttributes(0, textPane.getStyledDocument().getLength(), style, false);
            } else {
                Style style = textPane.addStyle("Underline", null);
                StyleConstants.setUnderline(style, true);
                textPane.getStyledDocument().setCharacterAttributes(0, textPane.getStyledDocument().getLength(), style, false);
            }
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
