package pathfinder.model;

import java.util.List;

public class ConversionNode {
	
	private List<EntityNode> left;
	private List<EntityNode> right;
	private List<ControlNode> controls;
	
	public ConversionNode(List<EntityNode> left, List<EntityNode> right, List<ControlNode> controls) {
		this.left = left;
		this.right = right;
		this.controls = controls;
	}
	
	public void setLeft(List<EntityNode> left) {
		this.left = left;
	}
	
	public void setRight(List<EntityNode> right) {
		this.right = right;
	}
	
	public void addControl(ControlNode control) {
		this.controls.add(control);
	}
	
	public List<EntityNode> getLeft(){
		return this.left;
	}
	
	public List<EntityNode> getRight(){
		return this.right;
	}
	
	public List<ControlNode> getControls(){
		return this.controls;
	}
}
