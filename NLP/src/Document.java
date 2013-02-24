
public class Document {

	private String text = "";
	private int docId=0;
	private String cl = "";
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public Document(int docId, String text, String cl) {
		super();
		this.text = text;
		this.docId = docId;
		this.cl = cl;
	}
	@Override
	public String toString() {
		return "Document [docId=" + docId + ",text=" + text + ",  cl=" + cl
				+ "]";
	}
	
	
	
	
	
}
