import java.util.ArrayList;

public class Node{
	
	public int degree;
	public int color = ConstantVariable.UNCOLORED;
	public int value;
	public int saturation_degree;
	public ArrayList<Node> adjacency = new ArrayList<Node>();
	
	public Node (int value) {
		this.value = value;
	}

	public void addEdge(Node ev) {adjacency.add(ev);}

	
}