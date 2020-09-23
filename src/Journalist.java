import java.io.Serializable;
import java.util.ArrayList;

public class Journalist implements Serializable {

	private static final long serialVersionUID = -6227824734051435759L;
	private String username;
	private String password;
	private ArrayList<JEntry> entries;
	
	public Journalist(String username, String password) {
	  this.username = username;
	  this.password = password;
	  this.entries = new ArrayList<>();
	}
	
	public String getPassword() {
	  return this.password;
	}
	
	public String getUsername() {
	  return this.username;
	}
	
	public ArrayList<JEntry> getEntries() {
	  return this.entries;
	}
	
	public void setPassword(String password) {
	  this.password = password;
	}
	
	public ArrayList<JEntry> getSortedEntries() {
	  if (this.entries.size() > 1) {
	    ArrayList<JEntry> list = this.entries;
	    for (int i = 0; i < this.entries.size() - 1; i++) {
	      int minIndex = findMinimum(list, i);
	      if (minIndex != i)
	        swap(list, i, minIndex); 
	    } 
	    return list;
	  } 
	  return this.entries;
	}
	
	private int findMinimum(ArrayList<JEntry> list, int first) {
	  int minIndex = first;
	  for (int i = first + 1; i < list.size(); i++) {
	    if (((JEntry)list.get(i)).getDate().before(((JEntry)list.get(minIndex)).getDate()))
	      minIndex = i; 
	  }  return minIndex;
	}
	
	private void swap(ArrayList<JEntry> list, int first, int minIndex) {
	  if (first != minIndex) {
	    JEntry temp = list.get(first);
	    list.set(first, list.get(minIndex));
	    list.set(minIndex, temp);
	  } 
	}

}
