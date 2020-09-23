import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Journal extends JFrame {

	private static final long serialVersionUID = -6492009286109589047L;
	private JPanel contentPane;
	private Journalist journalist;
	private static Journal j;
	private transient JEntry curEntry;
	
	DefaultMutableTreeNode entryNode = new DefaultMutableTreeNode("Journal Entries");
	DefaultTreeModel entryTreeModel = new DefaultTreeModel(this.entryNode);
	final JTextPane txtpn = new JTextPane();
	JTree entryTree = new JTree();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Journal j = new Journal(FileManager.loadJournalist("ashish"));
					j.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Journal(Journalist journalist) {
		this.journalist = journalist;
		j = this;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("pics/icon.png")));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < journalist.getSortedEntries().size(); i++) {
			this.entryNode.add(new DefaultMutableTreeNode(String.valueOf(i + 1) + ") " + (new SimpleDateFormat("EEE MMMM d, yyyy")).format((
					(JEntry)journalist.getSortedEntries().get(i)).getDate())));
		}
		String name = (journalist.getUsername().length() > 1) ? (String.valueOf(journalist.getUsername().substring(0, 1).toUpperCase()) + 
		journalist.getUsername().substring(1)) : journalist.getUsername().toUpperCase();
			
		setTitle("Welcome to Your Secure Journal, " + name + "!");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", 0, 12));
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", 0, 12));
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e) {
		    	  Journal.this.addNewEntryActionPerformed(e);
		      }
		    });
		
		//mntmNew.setIcon(new ImageIcon(Journal.class.getResource("/com/sun/java/swing/plaf/windows/icons/File.gif")));
		mnFile.add(mntmNew);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        Journal.this.save();
		      }
		    });
		//mntmSave.setIcon(new ImageIcon(Journal.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        Journal.this.save();
		        Journal.this.exit();
		      }
		    });
		//mntmExit.setIcon(new ImageIcon(Journal.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Segoe UI", 0, 12));
		menuBar.add(mnHelp);
		
		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		mntmInstructions.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JOptionPane.showMessageDialog(null, "Secure Journal lets you password protect your journal entries by making a user\nrequired to log onto their account. To begin, add a new entry by clicking \"Add New Entry,\"\nset a date and title, and begin typing! You can edit, remove, and create entries by selecting\nthe buttons. Make sure to save your work! Enjoy Secure Journal!");
		      }
		    });
		
		
		
		//mntmInstructions.setIcon(new ImageIcon(Journal.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		mnHelp.add(mntmInstructions);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JOptionPane.showMessageDialog(null, "Secure Journal created by Ashish Ramachandran. \nCheck out the github repository for updates and more applications! Enjoy.");
		      }
		    });
		
		//mntmAbout.setIcon(new ImageIcon(Journal.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		mnHelp.add(mntmAbout);
		
		JScrollPane entryScrollPane = new JScrollPane();
		entryScrollPane.setToolTipText("Displays all of your journal entries");
		
		JScrollPane textScrollPane = new JScrollPane();
		
		this.entryTree.setModel(this.entryTreeModel);
		entryScrollPane.setViewportView(this.entryTree);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(1);
		
		JButton btnAddNewEntry = new JButton("Add New Entry");
		btnAddNewEntry.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e) {
		    	  Journal.this.addNewEntryActionPerformed(e);
		      }
		    });
		
		btnAddNewEntry.setToolTipText("Create new entry");
		
		JButton btnSaveEntry = new JButton("Save Current Entry");
		btnSaveEntry.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        Journal.this.save();
		      }
		    });
		btnSaveEntry.setToolTipText("Save your current entry");
		
		JButton btnCloseApplication = new JButton("Close Application");
		btnCloseApplication.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        Journal.this.save();
		        Journal.this.dispose();
		      }
		    });
		btnCloseApplication.setToolTipText("Close your journal");
		
		JButton btnEditEntry = new JButton("Edit Entry");
		btnEditEntry.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        Journal.this.editEntryActionPerformed(e);
		      }
		    });
		btnEditEntry.setToolTipText("Edit selected entry");
		
		JButton btnOpenEntry = new JButton("Open Entry");
		btnOpenEntry.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        Journal.this.openEntryActionEvent(e);
		      }
		    });
		btnOpenEntry.setToolTipText("Open selected entry");
		
		JButton btnDeleteEntry = new JButton("Delete Entry");
		btnDeleteEntry.setToolTipText("Delete selected entry");
		btnDeleteEntry.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        Journal.this.removeEntryActionPerformed(e);
		      }
		    });
		
		JSeparator separator_1 = new JSeparator();
		
		JLabel label = new JLabel("Ashish's Secure Journal");
		label.setFont(new Font("Castellar", 1, 17));
		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnAddNewEntry, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
										.addComponent(btnOpenEntry, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
										.addComponent(btnEditEntry, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
										.addComponent(btnDeleteEntry, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
										.addComponent(entryScrollPane, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
									.addGap(8)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textScrollPane, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(89)
											.addComponent(btnSaveEntry)
											.addGap(95)
											.addComponent(btnCloseApplication))))
								.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 704, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(217)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(entryScrollPane, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddNewEntry)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEditEntry)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnOpenEntry)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDeleteEntry)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textScrollPane, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSaveEntry)
								.addComponent(btnCloseApplication))
							.addGap(22))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
							.addContainerGap())))
		);
		
		
		this.txtpn.setToolTipText("");
		this.txtpn.setEditable(false);
		textScrollPane.setViewportView(this.txtpn);
		this.contentPane.setLayout(gl_contentPane);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(new WindowAdapter()
		    {
		      public void windowClosing(WindowEvent e) {
		        try {
		          Journal.this.save();
		          Journal.this.exit();
		        } catch (Exception ex) {
		          System.err.println(ex.getLocalizedMessage());
		        } 
		      }
		    });
	}
	
	public void addNewEntryActionPerformed(ActionEvent e) {
		try {
			@SuppressWarnings("unused")
			NewEntry ne = new NewEntry(journalist);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void editEntryActionPerformed(ActionEvent e) {
		  try {
		    save();
		    if (this.txtpn.isEditable()) {
		      this.txtpn.setText("");
		      this.txtpn.setEditable(false);
		    } 
		    if (!this.entryTree.getLastSelectedPathComponent().toString().equals("")) {
		      int positionToReadTill = this.entryTree.getLastSelectedPathComponent().toString().indexOf(")");
		      @SuppressWarnings("unused")
			EditEntry ee = new EditEntry(journalist, this.journalist.getSortedEntries().get(Integer.parseInt(
		              this.entryTree.getLastSelectedPathComponent().toString().substring(0, positionToReadTill)) - 1));
		    }
		  
		  }
		  catch (NullPointerException ex) {
		    JOptionPane.showMessageDialog(this, "Please select an entry first.");
		  } 
		}
		
		public void openEntryActionEvent(ActionEvent e) {
		  try {
		    save();
		    if (!this.entryTree.getLastSelectedPathComponent().toString().equals("")) {
		      int positionToReadTill = this.entryTree.getLastSelectedPathComponent().toString().indexOf(")");
		      openEntry(this.journalist.getSortedEntries().get(Integer.parseInt(
		              this.entryTree.getLastSelectedPathComponent().toString().substring(0, positionToReadTill)) - 1));
		    } 
		  } catch (NullPointerException ex) {
		    JOptionPane.showMessageDialog(this, "Please select an entry first.");
		  } 
		}
		
		public static Journal getInstance() {
		  return j;
		}
		
		public void reloadTree() {
		  this.entryNode.removeAllChildren();
		  for (int i = 0; i < this.journalist.getSortedEntries().size(); i++)
		    this.entryNode.add(new DefaultMutableTreeNode(String.valueOf(i + 1) + ") " + (new SimpleDateFormat("EEE MMMM d, yyyy")).format((
		            (JEntry)this.journalist.getSortedEntries().get(i)).getDate())));
		  this.entryTreeModel.reload(this.entryNode);
		}
		
		public void exit() {
		  getInstance().dispose();
		  System.exit(0);
		}
		
		public boolean save() {
		  if (this.txtpn.isEditable()) {
		    int index = this.txtpn.getText().indexOf("Dear Journal,");
		    if (index != -1) {
		      this.curEntry.setContent(this.txtpn.getText().substring(index));
		    } else {
		      JOptionPane.showMessageDialog(null, "You need to have the words \"Dear Journal,\" so the\nprogram knows where to start saving the entry's content. Your\nentry will NOT be saved otherwise."); 
		      return false;
		    }
		    FileManager.saveJournalist(journalist);
		    return true;
		  } 
		  return false;
		}
		
		public void openEntry(JEntry entry) {
		  this.curEntry = entry;
		  if (!this.txtpn.isEditable())
		    this.txtpn.setEditable(true); 
		  this.txtpn.setText("Date: " + (new SimpleDateFormat("EEEE MMMM d, yyyy")).format(entry.getDate()) + "\r\n" + 
		      "Title: " + entry.getTitle() + "\r\n\r\n" + entry.getContent());
		}
		
		private void removeEntryActionPerformed(ActionEvent e) {
		  try {
		    save();
		    if (!this.entryTree.getLastSelectedPathComponent().toString().equals("") && 
		      JOptionPane.showConfirmDialog(this, "Delete this entry?") == 0) {
		      int positionToReadTill = this.entryTree.getLastSelectedPathComponent().toString().indexOf(")");
		      this.journalist.getSortedEntries().remove(Integer.parseInt(
		            this.entryTree.getLastSelectedPathComponent().toString().substring(0, positionToReadTill)) - 1);
		      reloadTree();
		      if (this.txtpn.isEditable()) {
		        this.txtpn.setText("");
		        this.txtpn.setEditable(false);
		      }
		    }
		    FileManager.saveJournalist(journalist);
		  } catch (NullPointerException ex) {
		    JOptionPane.showMessageDialog(this, "Please select an entry first.");
		  } 
		}

}
