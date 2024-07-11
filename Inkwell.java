import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;


public class Inkwell extends JFrame {
    private final JTextArea textArea;

    public Inkwell() {
        // Set the title
        super("Inkwell");
        setLayout(new BorderLayout());

        // Set the window size
        setSize(600, 800);

        // Create a new JTextArea for the editor.
        textArea = new JTextArea(30, 60);

        // Set text area line wrap
        textArea.setLineWrap(true);

        // Set caret color
        textArea.setCaretColor(Color.WHITE);

        // Set text area background
        textArea.setBackground(Color.BLACK);

        // Set text area foreground
        textArea.setForeground(Color.WHITE);

        // Set tab size to four spaces
        textArea.setTabSize(4);

        // Set font style and size
        textArea.setFont(new Font("Liberation Mono", Font.PLAIN, 16));

        // Set highlighted text color
        textArea.setSelectedTextColor(Color.BLACK);

        // Set highlighted reel color
        textArea.setSelectionColor(Color.WHITE);

        // Add a scrollbar to the JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the JTextArea and its scrollbar to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Create a menu bar for the editor.
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create file menu in the menu bar.
        JMenu fileMenu = new JMenu("File");

        // Add items to the File menu
        JMenuItem openItem = new JMenuItem("Open...");
        JMenuItem saveItem = new JMenuItem("Save As...");
        JMenuItem quitItem = new JMenuItem("Quit");

        // Add the items to their menus
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(quitItem);

        // Add the File menu to the menu bar
        menuBar.add(fileMenu);

        // Set up event handlers for the items
        openItem.addActionListener(new OpenHandler());
        saveItem.addActionListener(new SaveHandler());
        quitItem.addActionListener(e -> System.exit(0));

        // Add shortcut for opening files as ctrl+o
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));

        // Add shortcut for saving files as ctrl+s
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

        // Add shortcut for exit as ctrl+q
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));

        // Close the editor if the user clicks the x button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // Class for file opening
    private class OpenHandler extends Component implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(Inkwell.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    textArea.setText(sb.toString());

                    // Set the title to the file name
                    Inkwell.this.setTitle(fileChooser.getSelectedFile().getName());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            Inkwell.this, "Error reading file: " + ex.getMessage()
                    );
                }
            }
        }
    }

    // Class for file saving
    private class SaveHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(Inkwell.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    java.io.FileWriter writer =
                            new java.io.FileWriter(fileChooser.getSelectedFile());
                    writer.write(textArea.getText());
                    writer.close();

                    // Set the title to the file name
                    Inkwell.this.setTitle(fileChooser.getSelectedFile().getName());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Inkwell.this,
                            "Error writing file: " + ex.getMessage());
                }
            }
        }
    }

    // Main method (Play Button)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Inkwell().setVisible(true));
    }
}
