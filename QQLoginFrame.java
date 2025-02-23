import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QQLoginFrame extends JFrame {

    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JCheckBox rememberPasswordCheckBox;

    private String credentialsFilePath = "D:\\LCY collection\\demo5\\untitled\\src\\user_credentials.txt";

    public QQLoginFrame() {
        // 设置窗体属性
        setTitle("QQ登录");
        setIconImage(new ImageIcon("tupian.png").getImage());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建布局
        setLayout(new BorderLayout());

        // 添加Logo和提示文字
        JPanel topPanel = new JPanel();
        JLabel logoLabel = new JLabel(new ImageIcon("tupian.png"));
        JLabel promptLabel = new JLabel("欢迎登录QQ");
        topPanel.add(logoLabel);
        topPanel.add(promptLabel);
        add(topPanel, BorderLayout.NORTH);

        // 添加账号和密码输入框
        JPanel centerPanel = new JPanel(new GridLayout(3, 2));
        centerPanel.add(new JLabel("账号："));
        usernameTextField = new JTextField();
        centerPanel.add(usernameTextField);
        centerPanel.add(new JLabel("密码："));
        passwordField = new JPasswordField();
        centerPanel.add(passwordField);
        centerPanel.add(new JLabel("用户身份："));
        String[] roles = {"游客", "普通用户", "管理员"};
        roleComboBox = new JComboBox<>(roles);
        centerPanel.add(roleComboBox);
        add(centerPanel, BorderLayout.CENTER);

        // 添加复选框
        JPanel bottomPanel = new JPanel();
        rememberPasswordCheckBox = new JCheckBox("记住密码");
        bottomPanel.add(rememberPasswordCheckBox);
        add(bottomPanel, BorderLayout.SOUTH);

        // 添加按钮
        JButton loginButton = new JButton("登录");
        JButton changePasswordButton = new JButton("修改密码");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(changePasswordButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 添加登录按钮点击事件
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateCredentialsFromFile()) {
                    openQQMainWindow();
                } else {
                    JOptionPane.showMessageDialog(QQLoginFrame.this, "账号或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 设置窗体大小和位置
        pack();
        setLocationRelativeTo(null); // 居中显示
    }

    private boolean validateCredentialsFromFile() {
        String enteredUsername = usernameTextField.getText();
        char[] enteredPassword = passwordField.getPassword();
        String selectedRole = (String) roleComboBox.getSelectedItem();

        List<String[]> userCredentialsList = readCredentialsFromFile();

        for (String[] credentials : userCredentialsList) {
            String storedUsername = credentials[0];
            String storedPassword = credentials[1];
            String storedRole = credentials[2];

            if (enteredUsername.equals(storedUsername) && String.valueOf(enteredPassword).equals(storedPassword)
                    && selectedRole.equals(storedRole)) {
                return true;
            }
        }

        return false;
    }

    private List<String[]> readCredentialsFromFile() {
        List<String[]> userCredentialsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(credentialsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                userCredentialsList.add(credentials);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userCredentialsList;
    }

    private void openQQMainWindow() {
        // 在这里可以打开QQ主界面的窗口
        JOptionPane.showMessageDialog(this, "登录成功！", "欢迎", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QQLoginFrame().setVisible(true);
            }
        });
    }
}
