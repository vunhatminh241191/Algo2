import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;

public class Main {
	
	public static double[] low_density = {0.26, 0.44, 0.61, 0.73};
	public static double[] high_density = {0.34, 0.59, 0.72, 0.82};
	
	public static ArrayList<Integer> number_of_vertices = new ArrayList<Integer>();
	public static ArrayList<Integer> group1 = new ArrayList<Integer>();
	public static ArrayList<Integer> group2 = new ArrayList<Integer>();
	public static ArrayList<Integer> group3 = new ArrayList<Integer>();
	public static ArrayList<Integer> group4 = new ArrayList<Integer>();
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		for (int i = 10; i <= 600; i += 10) {
			ConstantVariable.NUMBER_OF_VERTICES = i;
			number_of_vertices.add(i);
			HashMap<Integer, int[]> colors_and_times = new HashMap<Integer, int[]>();
			for (int j = 0; j < 4; j++) {
				double result = 0;
				int number_of_colors = 0;
				for (int k = 0; k < 100; k++) {
					long startTime = System.currentTimeMillis();
					Graph graph = new Graph(low_density[j], high_density[j]);
					GraphColoring graph_coloring = new GraphColoring(graph);
					int coloring = graph_coloring.NumberOfColor();
					result = result + (System.currentTimeMillis() - startTime);
					number_of_colors = number_of_colors + coloring;
				}
				result = Math.round(result / 100);
				number_of_colors = Math.round(number_of_colors/100);
				int[] current_colors_and_times = {number_of_colors, (int)result}; 
				colors_and_times.put(j+1, current_colors_and_times);
				if (j == 0) {group1.add((int)result);}
				else if (j == 1) {group2.add((int)result);}
				else if (j == 2) {group3.add((int)result);}
				else {group4.add((int)result);}
			}
			WritingFile(i, colors_and_times);
			System.out.println(i);
		}	
		WritingFile("group1", group1);
		WritingFile("group2", group2);
		WritingFile("group3", group3);
		WritingFile("group4", group4);
	}

	public static void WritingFile(int number_of_vertices,
			HashMap<Integer, int[]> colors_and_times) {
		try {
			File file = new File("src/output.txt");
			if(!file.exists()){file.createNewFile();}
			FileWriter fileWritter = new FileWriter(file.getName(),true);
			BufferedWriter writer = new BufferedWriter(fileWritter);
			writer.write("Number of Vertices: " + number_of_vertices + "\n");
			for (Integer group: colors_and_times.keySet()){
				writer.write("Group: " + group + "\n");
				writer.write("Minimum number of color: " + colors_and_times.get(group)[0] + "\n");
				writer.write("Average time for running: " + colors_and_times.get(group)[1] + "\n");
			}
			writer.write("---------------\n");
			writer.close();
		} catch(IOException e) {
            System.out.println("Error reading file 'output.txt'");
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("Error working with that file");
        }
	}
	
	public static void WritingFile(String filename, ArrayList<Integer> group) {
		try {
			File file = new File("src/" + filename);
			if(!file.exists()){file.createNewFile();}
			FileWriter fileWritter = new FileWriter(file.getName(),true);
			BufferedWriter writer = new BufferedWriter(fileWritter);
			for (int i = 10, k = 0; i <= 1200; i+=10, k++){
				writer.write("Number of Vertices: " + i + "\n");
				writer.write(group.get(k) + "\n");
				writer.write("---------------\n");
			}
			writer.close();
		} catch(IOException e) {
            System.out.println("Error reading file 'output.txt'");
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("Error working with that file");
        }
	}
}
