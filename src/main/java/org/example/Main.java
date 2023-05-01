package org.example;

public class Main {
	public static void main(String[] args) {
		AlgorithmService algorithmService = new AlgorithmService();
		algorithmService.fillCriteriaMatrix(4);
		algorithmService.fillAlternativeMatrix(5);
		algorithmService.printCriteriaMatrix();
		algorithmService.printAlternativeMatrix();
		algorithmService.calcQualityIndicator();
	}
}