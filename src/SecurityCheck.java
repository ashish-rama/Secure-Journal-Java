import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class SecurityCheck extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8559455879575492804L;
	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private static SecurityCheck sc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File dir = new File(FileManager.JOURNAL_PATH);
					if (!dir.isDirectory()) {
						dir.mkdir();
						(new File(FileManager.ACCOUNTS_PATH)).mkdir();
					} 
					SecurityCheck sc = new SecurityCheck();
					sc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SecurityCheck() {
		sc = this;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("pics/icon.png")));
		setTitle("Ashish's Secure Journal Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Microsoft Sans Serif", 1, 15));
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Microsoft Sans Serif", 1, 15));
		
		this.usernameTextField = new JTextField();
		this.usernameTextField.setToolTipText("Enter a username");
		this.usernameTextField.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.setToolTipText("Login to your account");
		loginButton.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e) {
		        try {
		          String name = SecurityCheck.this.usernameTextField.getText();
		          if (name.length() < 1) {
		            JOptionPane.showMessageDialog(null, "Please login with a username and correct password.");
		          } else if (!FileManager.containsJournalist(name)) {
		            JOptionPane.showMessageDialog(null, "This account does not exist! Please try again.");
		          } else if (!FileManager.loadJournalist(name).getPassword().equals(SecurityCheck.this.passwordField.getText())) {
		            JOptionPane.showMessageDialog(null, "You have entered an incorrect password for this account.");
		          } else {
		        	Journal.main(new String[] { name });
		        	SecurityCheck.getInstance().dispose();
		          }
		        
		        } catch (Exception er) {
		          System.err.println(er.getLocalizedMessage());
		        } 
		      }
		    });
		loginButton.setFont(new Font("Tahoma", 0, 15));
		
		JButton createAccButton = new JButton("Create Account");
		createAccButton.setToolTipText("Create your new account");
		createAccButton.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e) {
		        try {
		          String name = SecurityCheck.this.usernameTextField.getText();
		          String pass = SecurityCheck.this.passwordField.getText();
		          if (name.length() < 1 && pass.length() < 1) {
		            JOptionPane.showMessageDialog(null, "Please create an account with a username and password.");
		          } else if (name.length() < 1 || pass.length() < 1) {
		            JOptionPane.showMessageDialog(null, "Please create an account with a username and password.");
		          }
		          else if (FileManager.containsJournalist(name)) {
		            JOptionPane.showMessageDialog(null, "An account with this username already exists. Please choose another.");
		          } else {
		            FileManager.saveJournalist(new Journalist(name, pass));
		            JOptionPane.showMessageDialog(null, "Your account has been created!");
		          } 
		        } catch (Exception er) {
		          System.err.println(er.getLocalizedMessage());
		        } 
		      }
		    });
		createAccButton.setFont(new Font("Tahoma", 0, 15));
		
		JLabel lblAshishsSecureJournal = new JLabel("Ashish's Secure Journal Login");
		lblAshishsSecureJournal.setHorizontalAlignment(SwingConstants.CENTER);
		lblAshishsSecureJournal.setFont(new Font("Castellar", 1, 17));
		
		this.passwordField = new JPasswordField();
		this.passwordField.setToolTipText("Enter a password");
		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(
		    gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(gl_contentPane.createSequentialGroup()
		      .addGap(80)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING, false)
		        .addGroup(gl_contentPane.createSequentialGroup()
		          .addComponent(lblPassword)
		          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		          .addComponent(this.passwordField))
		        .addGroup(gl_contentPane.createSequentialGroup()
		          .addComponent(lblUsername)
		          .addGap(8)
		          .addComponent(this.usernameTextField, -2, 161, -2)))
		      .addContainerGap(93, 32767))
		    .addGroup(gl_contentPane.createSequentialGroup()
		      .addContainerGap(31, 32767)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addComponent(lblAshishsSecureJournal, -2, 363, -2)
		        .addGroup(gl_contentPane.createSequentialGroup()
		          .addComponent(loginButton, -2, 162, -2)
		          .addGap(27)
		          .addComponent(createAccButton, -2, 177, -2)))
		      .addGap(27)));
		
		gl_contentPane.setVerticalGroup(
		    gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
		    .addGroup(gl_contentPane.createSequentialGroup()
		      .addContainerGap()
		      .addComponent(lblAshishsSecureJournal)
		      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, 32767)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
		        .addComponent(lblUsername)
		        .addGroup(gl_contentPane.createSequentialGroup()
		          .addComponent(this.usernameTextField, -2, -1, -2)
		          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
		      .addGap(24)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(lblPassword)
		        .addComponent(this.passwordField, -2, -1, -2))
		      .addGap(33)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(loginButton, -2, 73, -2)
		        .addComponent(createAccButton, -2, 73, -2))
		      .addGap(24)));
		
		this.contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public static SecurityCheck getInstance() {
		return sc;
	}

}
