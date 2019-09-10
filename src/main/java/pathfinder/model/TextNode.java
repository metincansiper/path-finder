package pathfinder.model;

public class TextNode {
	private String text;
	private String link;
	
	public TextNode(String text) {
		this.text = text;
	}
	
	public TextNode(String text, String link) {
		this.text = text;
		this.link = link;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getText() {
		return text;
	}
	
	public String getLink() {
		return link;
	}
}
