package pathfinder.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ModificationNode {
	private String type;
	private Set<String> locations;
	
	public ModificationNode(String type) {
		this.type = type;
		this.locations = new HashSet<String>();
	}
	
	public ModificationNode(String type, String initialLocation) {
		this( type );
		this.addLocation( initialLocation );
	}
	
	public void addLocation( String location ) {
		this.locations.add( location );
	}
	
	public void addLocations( Set<String> location ) {
		this.locations.addAll( location );
	}
	
	public void setType( String type ) {
		this.type = type;
	}
	
	public Set<String> getLocations() {
		return this.locations;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static List<ModificationNode> mergeModifications(List<ModificationNode> modifications){
		Map<String, ModificationNode> modByType = new HashMap<String, ModificationNode>();
		
		for ( ModificationNode modification : modifications ) {
			String type = modification.getType();
			if ( !modByType.containsKey(type) ) {
				modByType.put(type, modification);
			}
			else {
				modByType.get(type).addLocations(modification.getLocations());
			}
		}
		
		return modByType.values().stream()
				.collect(Collectors.toList());
	}
}
