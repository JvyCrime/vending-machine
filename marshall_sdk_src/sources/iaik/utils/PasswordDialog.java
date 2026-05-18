package iaik.utils;

import iaik.security.ssl.SSLContext;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

/* JADX INFO: loaded from: classes2.dex */
class PasswordDialog extends JDialog {
    private static boolean a;
    private JPasswordField b;
    private JLabel c;
    private JButton d;
    private char[] e;

    /* JADX INFO: renamed from: iaik.utils.PasswordDialog$1, reason: invalid class name */
    class AnonymousClass1 {
    }

    class a extends KeyAdapter implements ActionListener {
        private final PasswordDialog a;

        private a(PasswordDialog passwordDialog) {
            this.a = passwordDialog;
        }

        a(PasswordDialog passwordDialog, AnonymousClass1 anonymousClass1) {
            this(passwordDialog);
        }

        public void actionPerformed(ActionEvent actionEvent) {
            PasswordDialog passwordDialog = this.a;
            PasswordDialog.a(passwordDialog, PasswordDialog.a(passwordDialog).getPassword());
            this.a.setVisible(false);
        }

        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == '\n') {
                PasswordDialog passwordDialog = this.a;
                PasswordDialog.a(passwordDialog, PasswordDialog.a(passwordDialog).getPassword());
                this.a.setVisible(false);
            }
        }
    }

    static {
        a();
    }

    public PasswordDialog(String str) {
        super(JOptionPane.getRootFrame(), str, true);
        a((Component) getParent());
    }

    static JPasswordField a(PasswordDialog passwordDialog) {
        return passwordDialog.b;
    }

    public static void a() {
        if (a) {
            return;
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Warning: Could not set look and feel:");
            System.err.println(e);
        }
        a = true;
    }

    private void a(Component component) {
        JPanel jPanel = new JPanel((LayoutManager) null);
        setContentPane(jPanel);
        JLabel jLabel = new JLabel("Enter KeyStore Password:");
        this.c = jLabel;
        jLabel.setBounds(18, 10, 214, 25);
        jPanel.add(this.c);
        JPasswordField jPasswordField = new JPasswordField();
        this.b = jPasswordField;
        jPasswordField.setBounds(18, 35, 214, 22);
        this.b.requestFocus();
        jPanel.add(this.b);
        JButton jButton = new JButton();
        this.d = jButton;
        jButton.setBounds(89, 69, 70, 23);
        this.d.setText("OK");
        jPanel.add(this.d);
        a aVar = new a(this, null);
        this.b.addKeyListener(aVar);
        this.d.addActionListener(aVar);
        pack();
        setSize(SSLContext.CERTTYPE_ECDSA_EC, 129);
        setLocationRelativeTo(component);
        setDefaultCloseOperation(0);
        toFront();
        this.b.requestFocus();
    }

    static char[] a(PasswordDialog passwordDialog, char[] cArr) {
        passwordDialog.e = cArr;
        return cArr;
    }

    public char[] b() {
        return this.e;
    }

    public void c() {
        if (this.e != null) {
            int i = 0;
            while (true) {
                char[] cArr = this.e;
                if (i >= cArr.length) {
                    break;
                }
                cArr[i] = 0;
                i++;
            }
        }
        this.b.setText("");
    }
}
