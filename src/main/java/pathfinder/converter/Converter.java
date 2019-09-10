package pathfinder.converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.Control;
import org.biopax.paxtools.model.level3.ControlType;
import org.biopax.paxtools.model.level3.Controller;
import org.biopax.paxtools.model.level3.Conversion;
import org.biopax.paxtools.model.level3.Entity;
import org.biopax.paxtools.model.level3.EntityFeature;
import org.biopax.paxtools.model.level3.ModificationFeature;
import org.biopax.paxtools.model.level3.PhysicalEntity;
import org.biopax.paxtools.model.level3.SequenceLocation;
import org.biopax.paxtools.model.level3.SequenceModificationVocabulary;

import pathfinder.model.ControlNode;
import pathfinder.model.ConversionNode;
import pathfinder.model.EntityNode;
import pathfinder.model.ModificationNode;
import pathfinder.model.TextNode;
import pathfinder.util.ModificationUtil;

public class Converter {
	private Set<Conversion> conversions;
	
	public Converter(Model model) {
		this.conversions = model.getObjects(Conversion.class);
	}
	
	public List<ConversionNode> getConversionNodes(){
		return this.setToConversionNodes(this.conversions);
	}
	
	
	private List<ConversionNode> setToConversionNodes(Set<Conversion> set) {
		List<ConversionNode> conversionNodes = new ArrayList<ConversionNode>();
		for ( Conversion conversion : set ) {
			conversionNodes.add( conversionToNode(conversion) );
		}
		
		return conversionNodes;
	}

//	private TextNode makeCommaNode() {
//		return new TextNode(",");
//	}
	
//	private TextNode makeLtoRNode() {
//		return new TextNode(" -> ");
//	}
	
	private ControlNode controlToNode(Control control) {
		Set<Controller> controllerSet = control.getController();
		ControlType controlType = control.getControlType();
		List<EntityNode> controllerNodes = setToEntityNodes(controllerSet);
		
		String controlTypeStr = controlTypeToStr(controlType);
		TextNode typeNode = new TextNode(controlTypeStr, control.toString());
		ControlNode res = new ControlNode(typeNode, controllerNodes);
		
		return res;
	}
	
	private <T extends Entity> EntityNode entityToNode(T entity) {
		String name = entity.getDisplayName();
		String id = entity.toString();
		TextNode nameNode = new TextNode(name, id);
		
		List<ModificationNode> modificationNodes = new ArrayList<ModificationNode>();
		
		if ( entity instanceof PhysicalEntity ) {
			Set<EntityFeature> entityFeatures = ((PhysicalEntity) entity).getFeature()
					.stream()
					.filter(e -> e instanceof ModificationFeature)
					.collect(Collectors.toSet());
			
//			List<ModificationNode> modificationNodes = new ArrayList<ModificationNode>();
			for ( EntityFeature ef : entityFeatures ) {
				ModificationFeature mf = (ModificationFeature) ef;
				SequenceModificationVocabulary modTypeVocab = mf.getModificationType();
				
				if ( modTypeVocab != null ) {
					Set<String> terms = modTypeVocab.getTerm();
					SequenceLocation seqLoc = mf.getFeatureLocation();
					String modificationType = ModificationUtil.getModificationType(terms);
					ModificationNode modNode = new ModificationNode(modificationType);
					
					if ( seqLoc != null ) {
						modNode.addLocation(seqLoc.toString());
					}
					modificationNodes.add(modNode);
				}
			}
			
			modificationNodes = ModificationNode.mergeModifications(modificationNodes);
		}
		
		EntityNode entityNode = new EntityNode(nameNode, modificationNodes);
		return entityNode;
	}
	
	private List<ControlNode> setToControlNodes(Set<Control> set){
		List<ControlNode> controlNodes = new ArrayList<ControlNode>();
		for ( Control control : set ) {
			controlNodes.add( controlToNode(control) );
		}
		
		return controlNodes;
	}
	
	private <T extends Entity> List<EntityNode> setToEntityNodes(Set<T> set){
		List<EntityNode> entityNodes = new ArrayList<EntityNode>();
		for ( T entity : set ) {
			entityNodes.add( entityToNode(entity) );
		}
		
		return entityNodes;
	}
	
	private String controlTypeToStr(ControlType controlType) {
		String res = null;
		
		if ( controlType == null ) {
			res = "CONTROLLED";
		}
		else if ( controlType.toString().startsWith("ACTIVATION") ) {
			res = "ACTIVATED";
		}
		else if ( controlType.toString().startsWith("INHIBITION") ) {
			res = "INHIBITED";
		}
		
		assert res != null;
		
		return res;
	}

	private ConversionNode conversionToNode(Conversion conversion) {
		Set<PhysicalEntity> leftSet = conversion.getLeft();
		Set<PhysicalEntity> rightSet = conversion.getRight();
		Set<Control> controlSet = conversion.getControlledOf();
		
		List<EntityNode> left = setToEntityNodes(leftSet);
		List<EntityNode> right = setToEntityNodes(rightSet);
		List<ControlNode> controls = setToControlNodes(controlSet);
		
		return new ConversionNode(left, right, controls);
	}
}
