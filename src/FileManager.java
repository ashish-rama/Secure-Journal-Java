import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;

public class FileManager {
	
	public static final String JOURNAL_PATH = String.valueOf(System.getProperty("user.home")) + "/secure_journal/";
	public static final String ACCOUNTS_PATH = String.valueOf(JOURNAL_PATH) + "accounts/";
	public static final String SAVE_PATH = String.valueOf(System.getProperty("user.home")) + "/secure_journal/accounts/";
	
	public static final synchronized boolean containsJournalist(String username) {
	  return (new File(String.valueOf(SAVE_PATH) + username + ".j")).exists();
	}
	
	public static synchronized Journalist loadJournalist(String username) {
	  try {
	    return (Journalist)loadSerializedFile(new File(String.valueOf(SAVE_PATH) + username + ".j"));
	  } catch (Throwable e) {
	    System.err.println(e.getLocalizedMessage());
	    
	    return null;
	  } 
	}
	public static synchronized void saveJournalist(Journalist journalist) {
	  try {
	    storeSerializableClass(journalist, new File(String.valueOf(SAVE_PATH) + journalist.getUsername() + ".j"));
	  } catch (ConcurrentModificationException e) {
	    System.err.println(e.getLocalizedMessage());
	  } catch (Throwable e) {
	    System.err.println(e.getLocalizedMessage());
	  } 
	}
	
	public static final Object loadSerializedFile(File f) throws IOException, ClassNotFoundException {
	  if (!f.exists())
	    return null; 
	  ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
	  Object object = in.readObject();
	  in.close();
	  return object;
	}
	
	public static final void storeSerializableClass(Serializable o, File f) throws IOException {
	  ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
	  out.writeObject(o);
	  out.close();
	}

}
