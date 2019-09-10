package web;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pathfinder.converter.Converter;
import pathfinder.model.ConversionNode;

@RestController
public class Controller {
	@RequestMapping("/pathsbetween")
	public List<ConversionNode> pathsBetween(@RequestParam(value="source", required=false) Set<String> sources) throws IOException {
		if ( sources == null ) {
			return new ArrayList<ConversionNode>();
		}
		String kind = "PathsBetween";
		return getConversionNodes(kind, sources, null);
	}
	
	@RequestMapping("/pathsfromto")
	public List<ConversionNode> pathsFromTo(@RequestParam(value="source", required=false) Set<String> sources, @RequestParam(value="target", required=false) Set<String> targets) throws IOException {

		if ( sources == null && targets == null ) {
			return new ArrayList<ConversionNode>();
		}
		
		String kind = "PathsFromTo";
    	return getConversionNodes(kind, sources, targets);
	}
	
	private List<ConversionNode> getConversionNodes(String kind, Set<String> sources, Set<String> targets) throws IOException{
		URL u = makeURL(kind, sources, targets);
		InputStream in = u.openStream();
    	BioPAXIOHandler handler = new SimpleIOHandler();
    	Model model = handler.convertFromOWL(in);
    	Converter converter = new Converter(model);
    	return converter.getConversionNodes();
	}
	
	private URL makeURL(String kind, Set<String> sources, Set<String> targets) throws MalformedURLException {
		String baseUrl = "https://www.pathwaycommons.org/pc2/graph?limit=1&format=biopax";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl);
		if ( sources != null ) {
			for ( String source : sources ) {
				builder.queryParam("source", source);
			}
		}
		if ( targets != null ) {
			for ( String target : targets ) {
				builder.queryParam("target", target);
			}
		}
		if ( kind != null ) {
			builder.queryParam("kind", kind);
		}
		
		String uriStr = builder.build().toUriString();
		System.out.println(uriStr);
		URL url = new URL(uriStr);
		
		return url;
	}
}
