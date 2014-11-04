package cz.kobzol.bulanci.desktop;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

public class StartupForm extends JFrame {
    private JPanel root;
    private JTextField addressField;
    private JTextField portField;
    private JTextArea logArea;
    private JButton runButton;
    private JTextField nicknameField;

    private ActionListener actionListener;

    public StartupForm() throws HeadlessException {
        this("Připojte se k serveru");
    }

    public StartupForm(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(root);
        pack();

        java.awt.event.ActionListener actionListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!StartupForm.this.checkValidity()) {
                    StartupForm.this.addLog("Port, nebo nickname není správný");
                    return;
                }

                StartupForm.this.actionListener.connect(
                        StartupForm.this,
                        StartupForm.this.addressField.getText(),
                        StartupForm.this.portField.getText(),
                        StartupForm.this.nicknameField.getText()
                );
            }
        };

        runButton.addActionListener(actionListener);
        nicknameField.addActionListener(actionListener);
    }

    /**
     * Check form validity
     */
    protected boolean checkValidity()
    {
        boolean isOk = true;
        System.out.println(this.portField.getText());
        if (!this.portField.getText().matches("^\\d+$")) {
            this.portField.setBackground(Color.red);
            isOk = false;
        } else {
            this.portField.setBackground(Color.white);
        }
        if (!this.nicknameField.getText().matches("[\\w\\d]+")) {
            this.nicknameField.setBackground(Color.red);
            isOk = false;
        } else {
            this.nicknameField.setBackground(Color.white);
        }
        return isOk;
    }

    /**
     * Action Listener
     */
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * Adds some text to visual log
     * @param text
     */
    public void addLog(String text)
    {
        this.logArea.append(text + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    @Override
    public void setVisible(boolean b) {

        if (actionListener == null) {
            throw new IllegalStateException("Before visible set first StartupForm::setDoConnectListener");
        }

        super.setVisible(b);
    }


    /**
     * Listener is called by form, when is player ready, etc.
     */
    public interface ActionListener {
        public void connect(StartupForm form, String address, String port, String nickname);
    }

}
