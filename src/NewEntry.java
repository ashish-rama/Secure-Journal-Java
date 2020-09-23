import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

public class NewEntry extends JFrame {

	private static final long serialVersionUID = -7808184852613790621L;
	private JPanel contentPane;
	private JTextField textField;
	private Journalist journalist;
	private static NewEntry ne;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewEntry ne = new NewEntry(FileManager.loadJournalist(args[0]));
					ne.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewEntry(Journalist journalist) {
		this.journalist = journalist;
		ne = this;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("pics/icon.png")));
		setTitle("Create New Journal Entry");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 245);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblCreateNewJournal = new JLabel("Create New Journal Entry");
		lblCreateNewJournal.setFont(new Font("Tahoma", 1, 14));
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(new Font("Tahoma", 0, 12));
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Tahoma", 0, 12));
		
		JSeparator separator = new JSeparator();
		
		final JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("EEE MMM d, yyyy");
		dateChooser.setDate(Calendar.getInstance().getTime());
		
		this.textField = new JTextField();
		this.textField.setColumns(10);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        try {
		          Date date = dateChooser.getDate();
		          String title = NewEntry.this.textField.getText();
		          if (date == null) {
		            JOptionPane.showMessageDialog(null, "Please input a date for the entry.");
		          } else if (title.length() < 1) {
		            JOptionPane.showMessageDialog(null, "Please input a title for the entry.");
		          } else {
		            (NewEntry.getInstance()).journalist.getEntries().add(new JEntry(date, title));
		            FileManager.saveJournalist((NewEntry.getInstance()).journalist);
		            Journal.getInstance().reloadTree();
		            NewEntry.getInstance().dispose();
		          } 
		        } catch (Exception ex) {
		          System.err.println(ex.getLocalizedMessage());
		        } 
		      }
		    });
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		        NewEntry.getInstance().dispose();
		      }
		    });
		
		JSeparator separator_1 = new JSeparator();
		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(
		    gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(gl_contentPane.createSequentialGroup()
		      .addGap(93)
		      .addComponent(lblCreateNewJournal, -1, -1, 32767)
		      .addContainerGap(103, 32767))
		    .addGroup(GroupLayout.Alignment.TRAILING, gl_contentPane.createSequentialGroup()
		      .addGap(76)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(gl_contentPane.createSequentialGroup()
		          .addComponent(lblDate)
		          .addGap(18)
		          .addComponent((Component)dateChooser, -1, 143, 32767))
		        .addGroup(gl_contentPane.createSequentialGroup()
		          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		          .addComponent(lblTitle)
		          .addGap(18)
		          .addComponent(this.textField, -1, 184, 32767)))
		      .addGap(107))
		    .addGroup(gl_contentPane.createSequentialGroup()
		      .addGap(50)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(gl_contentPane.createSequentialGroup()
		          .addComponent(separator_1, -2, 271, -2)
		          .addContainerGap())
		        .addGroup(gl_contentPane.createSequentialGroup()
		          .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(gl_contentPane.createSequentialGroup()
		              .addGap(44)
		              .addComponent(btnAccept)
		              .addGap(48)
		              .addComponent(btnCancel))
		            .addComponent(separator, GroupLayout.Alignment.TRAILING, -1, 271, 32767))
		          .addGap(53)))));
		
		gl_contentPane.setVerticalGroup(
		    gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(gl_contentPane.createSequentialGroup()
		      .addContainerGap()
		      .addComponent(lblCreateNewJournal)
		      .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		      .addComponent(separator, -2, 2, -2)
		      .addGap(18)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
		        .addComponent(lblDate)
		        .addComponent((Component)dateChooser, -2, -1, -2))
		      .addGap(29)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(this.textField, -2, -1, -2)
		        .addComponent(lblTitle))
		      .addGap(18)
		      .addComponent(separator_1, -2, -1, -2)
		      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, 32767)
		      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(btnCancel)
		        .addComponent(btnAccept))
		      .addContainerGap()));
		
		this.contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	public static NewEntry getInstance() {
		return ne;
	}

}
