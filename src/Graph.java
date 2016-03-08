import java.util.Random;

public class Graph {
	
	Node[] nodes = new Node[ConstantVariable.NUMBER_OF_VERTICES];
	boolean[][] checking_graph = new boolean[ConstantVariable.NUMBER_OF_VERTICES]
			[ConstantVariable.NUMBER_OF_VERTICES];
	
	public Graph (double low_density, double high_density){
		for (int i =0; i < ConstantVariable.NUMBER_OF_VERTICES; i++) {
			Node node = new Node(i);
			nodes[i] = node;
		}
		Random random = new Random();
		double desired_density = low_density +(high_density - low_density) * random.nextDouble();
		GenerateGraph(desired_density);
	}
	
	public void addEdge(Node node_sv, Node node_ev) {
		node_sv.addEdge(node_ev);
        node_sv.degree++;
	}

	public void GenerateGraph(double desired_density) {
		for (int i = 0; i < ConstantVariable.NUMBER_OF_VERTICES; i++) {
			for (int j = 0; j < ConstantVariable.NUMBER_OF_VERTICES; j++) {
				if (j == i) {checking_graph[i][j] = true;}
				else if (checking_graph[i][j] == true || checking_graph[j][i] == true){
					break;}
				else {
					Random random = new Random();
					double randomValue = random.nextDouble();
					if (randomValue < desired_density){
						addEdge(nodes[i], nodes[j]);
						addEdge(nodes[j], nodes[i]);
						checking_graph[i][j] = true;
						checking_graph[j][i] = true;}
					
				}
			}
		}
	}

}
