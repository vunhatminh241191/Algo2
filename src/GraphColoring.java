import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;


public class GraphColoring {

	public Graph graph;
	public ArrayList<Node> decreasing_order_nodes = new ArrayList<Node>();
	public ArrayList<Node> saturation_order_nodes = new ArrayList<Node>();
	public HashSet<Integer> possible_color = new HashSet<Integer>();
	public int uncolored = ConstantVariable.NUMBER_OF_VERTICES;
	
	public GraphColoring(Graph graph) {
		this.graph = graph;
	}
	
	public void SortedDegreeNodes() {
		Comparator<Node> sortedNode = (n1, n2) -> Integer.compare(
				n1.degree, n2.degree);
		Arrays.asList(graph.nodes).stream().sorted(sortedNode.reversed()).forEach(
				n -> decreasing_order_nodes.add(n)); 
	}
	
	public void SaturationDegreeNodes() {
		Comparator<Node> saturationNode = (n1, n2) -> Integer.compare(
				n1.saturation_degree, n2.saturation_degree);
		Arrays.asList(graph.nodes).stream().sorted(saturationNode.reversed()).forEach(
				n -> saturation_order_nodes.add(n));
	}
	
	public int NumberOfColor() {
		SortedDegreeNodes();
		Node init_node = decreasing_order_nodes.get(0);
		graph.nodes[decreasing_order_nodes.get(0).value].color = 1;
		decreasing_order_nodes.remove(0);
		uncolored--;
		possible_color.add(1);
		IncreasingSaturation(init_node);
		SaturationDegreeNodes();
		DeleteSaturation(init_node);
		while (uncolored > 0) {
			Node finding_node = FindTheNextNode();
			HashSet<Integer> color = new HashSet<Integer>();
			for (int i = 0; i < finding_node.adjacency.size(); i++) {
				 if (finding_node.adjacency.get(i).color != ConstantVariable.UNCOLORED) {
					 color.add(finding_node.adjacency.get(i).color);
				 }
			}
			if (color.equals(possible_color)){
				Iterator<Integer> itr = possible_color.iterator();
				int value = 0;
				while(itr.hasNext()) {value = (int) itr.next();}
				possible_color.add(value+1);
				graph.nodes[finding_node.value].color = value + 1;}
			else {
				HashSet<Integer> temp = new HashSet<Integer>(possible_color);
				HashSet<Integer> temp1 = new HashSet<Integer>(possible_color);
				temp.retainAll(color);
				temp1.removeAll(temp);
				graph.nodes[finding_node.value].color = temp1.iterator().next();
			}
			DeleteDegree(finding_node);
			IncreasingSaturation(finding_node);
			SaturationDegreeNodes();
			DeleteSaturation(finding_node);
			uncolored--;
		}
		
		return possible_color.size();
	}
	
	public void IncreasingSaturation(Node node) {
		for (int i = 0; i < node.adjacency.size(); i++){
			if (node.adjacency.get(i).color == ConstantVariable.UNCOLORED){
				int value = node.adjacency.get(i).value;
				graph.nodes[value].saturation_degree = graph.nodes[value].saturation_degree + 1;
				node.adjacency.get(i).saturation_degree = node.adjacency.get(i).saturation_degree + 1;
			}
		}
	}
	
	public Node FindTheNextNode(){
		ArrayList<Node> next_saturation_node = new ArrayList<Node>();
		ArrayList<Node> next_degree_node = new ArrayList<Node>();
		int saturation = saturation_order_nodes.get(0).saturation_degree;
		int degree = decreasing_order_nodes.get(0).degree;
		int next_node = 0;
		boolean checking_true = false;
		for (int i = 0; i < saturation_order_nodes.size(); i++) {
			if (saturation_order_nodes.get(i).saturation_degree == saturation) {
				next_saturation_node.add(saturation_order_nodes.get(i));
			}
		}
		if (next_saturation_node.size() > 1) {
			for (int i = 0; i < decreasing_order_nodes.size(); i++) {
				if (decreasing_order_nodes.get(i).degree == degree) {
					next_degree_node.add(decreasing_order_nodes.get(i));
				}
			}
			for (int i = 0; i < next_saturation_node.size(); i++) {
				for (int j = 0; j < next_degree_node.size(); j++) {
					if (next_degree_node.get(j).value == next_saturation_node.get(i).value){
						next_node = i;
						checking_true = true;
						break;
					}
				}
				if (checking_true){break;}
			}
			return next_saturation_node.get(next_node);
		}
		return next_saturation_node.get(0);
	}
	
	public void DeleteSaturation(Node node) {
		for (int i = 0 ; i < saturation_order_nodes.size(); i++) {
			if (saturation_order_nodes.get(i).value == node.value) {
				saturation_order_nodes.remove(i);
				break;
			}
		}
	}
	
	public void DeleteDegree(Node node) {
		for (int i = 0; i < decreasing_order_nodes.size(); i++) {
			if (decreasing_order_nodes.get(i).value == node.value) {
				decreasing_order_nodes.remove(i);
				break;
			}
		}
	}
}
