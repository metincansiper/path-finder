package pathfinder.model;

import java.util.List;

public class EntityNode {
	private TextNode name;
	private List<ModificationNode> modifications;
	
	public EntityNode(TextNode name, List<ModificationNode> modifications) {
		this.name = name;
		this.modifications = modifications;
	}
	
	public void setModifications(List<ModificationNode> modifications) {
		this.modifications = modifications;
	}
	
	public void setName(TextNode name) {
		this.name = name;
	}
	
	public List<ModificationNode> getModifications(){
		return this.modifications;
	}
	
	public TextNode getName() {
		return this.name;
	}
}
