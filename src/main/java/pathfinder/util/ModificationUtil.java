package pathfinder.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ModificationUtil {
	
	private static Set<String> phosphorylationTypes = new HashSet<>(Arrays.asList(
		"phosphorylation", "phosphorylation site",
		"phosphate group", "phosphorylated residue",
		"phosphorylated", "o-phospho-l-serine",
		"o-phospho-l-threonine", "o-phospho-l-tyrosine",
		"o4'-phospho-l-tyrosine", "mi:0170",
		"phosres", "optyr", "opthr", "opser"
	));
	
	private static Set<String> activationTypes = new HashSet<>(Arrays.asList(
		"active", "residue modification, active"
	));
	
	private static Set<String> inactivationTypes = new HashSet<>(Arrays.asList(
		"inactive", "residue modification, inactive"
	));
	
	private static Set<String> ubiquitinationTypes = new HashSet<>(Arrays.asList(
		"ubiquitination site", "ubiquitination", "ubiquitinylated lysine"
	));
	
	private static Set<String> methylationTypes = new HashSet<>(Arrays.asList(
		"methylated lysine", "n6,n6-dimethyl-l-lysine", "n6-methyl-l-lysine"
	));
	
	private static Set<String> glycosylationTypes = new HashSet<>(Arrays.asList(
		"n4-glycosyl-l-asparagine", "o-glycosyl-l-threonine", 
		"o-glucosyl-l-serine"
	));
	
	private static Set<String> fucosylationTypes = new HashSet<>(Arrays.asList(
		"o-fucosyl-l-threonine", "o-fucosyl-l-serine"
	));
	
	private static String PHOSPHORYLATED = "phosphorylated";
	private static String ACTIVATED = "activated";
	private static String INACTIVATED = "inactivated";
	private static String UBIQUITINATED = "ubiquitinated";
	private static String METHYLATED = "methylated";
	private static String GLYCOSYLATED = "glycosylated";
	private static String FUCOSTLATED = "fucosylated";
	private static String UNKNOWN = "x";
	
	
	public static String getModificationType(Set<String> terms) {
		if ( terms == null || terms.isEmpty() ) {
			return UNKNOWN;
		}
		
		String term = terms.iterator().next().toLowerCase();
		
		if ( phosphorylationTypes.contains( term ) ) {
			return PHOSPHORYLATED;
		}
		
		if ( activationTypes.contains( term ) ) {
			return ACTIVATED;
		}
		
		if ( inactivationTypes.contains( term ) ) {
			return INACTIVATED;
		}
		
		if ( ubiquitinationTypes.contains(term) ) {
			return UBIQUITINATED;
		}
		
		if ( methylationTypes.contains(term) ) {
			return METHYLATED;
		}
		
		if ( glycosylationTypes.contains(term) ) {
			return GLYCOSYLATED;
		}
		
		if ( fucosylationTypes.contains(term) ) {
			return FUCOSTLATED;
		}
			
		return UNKNOWN;
	}
}
