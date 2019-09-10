package pathfinder.model;

import java.util.List;

public class ControlNode {
	private TextNode type;
	private List<EntityNode> controllers;
	
	public ControlNode(TextNode type, List<EntityNode> controllers) {
		this.type = type;
		this.controllers = controllers;
	}
	
	public void setType(TextNode type) {
		this.type = type;
	}
	
	public void addController(EntityNode controller) {
		this.controllers.add(controller);
	}
	
	public TextNode getType() {
		return this.type;
	}
	
	public List<EntityNode> getControllers(){
		return this.controllers;
	}
}
