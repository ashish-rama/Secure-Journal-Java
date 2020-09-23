import java.io.Serializable;
import java.util.Date;

public class JEntry implements Serializable {

	private static final long serialVersionUID = -3180403498848333971L;
	private Date date;
	private String title;
	private String content;
	
	public JEntry(Date date, String title) {
	  this.date = date;
	  this.title = title;
	  this.content = "Dear Journal,\r\n\r\n\t";
	}
	
	public Date getDate() {
	  return this.date;
	}
	
	public String getTitle() {
	  return this.title;
	}
	
	public String getContent() {
	  return this.content;
	}
	
	public void setDate(Date date) {
	  this.date = date;
	}
	
	public void setTitle(String title) {
	  this.title = title;
	}
	
	public void setContent(String content) {
	  this.content = content;
	}

}
