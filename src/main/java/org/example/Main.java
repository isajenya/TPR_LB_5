package org.example;

public class Main {
	public static void main(String[] args) {
		AlgorithmService algorithmService = new AlgorithmService();
		algorithmService.fillCriteriaMatrix(3);
		algorithmService.fillAlternativeMatrix(3);
		algorithmService.printCriteriaMatrix();
		algorithmService.printAlternativeMatrix();
		algorithmService.calcQualityIndicator();
	}
}