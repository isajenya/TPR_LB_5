package org.example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgorithmService {

	private Point[][] criteriaMatrix;
	private Point[][][] alternativeMatrix;
	private List<Double> scale;
	private List<String> headersForCriteriaMatrix;
	private List<String> headersForAlternativeMatrix;
	private int amountOfCriteria;

	public void fillCriteriaMatrix(int amountOfCriteria) {
		fillScale();
		fillHeadersForCriteriaMatrix();
		criteriaMatrix = new Point[amountOfCriteria][amountOfCriteria];
		for (int i = 0; i < amountOfCriteria; i++) {
			for (int j = 0; j < amountOfCriteria; j++) {
				if (i == j) {
					criteriaMatrix[i][j] = new Point(1);
				} else if (i < j) {
					criteriaMatrix[i][j] = compareCriteria();
				} else {
					criteriaMatrix[i][j] = new Point(1.0 / criteriaMatrix[j][i].getValue());
				}
			}
		}

		countOwnVectorForCriteria();
		countWeightOfCriteria();
	}

	private void countOwnVectorForCriteria() {
		for (int i = 0; i < criteriaMatrix.length; i++) {
			criteriaMatrix[i][criteriaMatrix.length - 1].setOwnVector(Math.pow(criteriaMatrix[i][0].getValue() * criteriaMatrix[i][1].getValue() * criteriaMatrix[i][2].getValue() * criteriaMatrix[i][3].getValue(), 1.0 / criteriaMatrix.length));
		}
	}

	private void countOwnVectorForAlternative() {
		for (int k = 0; k < amountOfCriteria; k++) {
			for (int i = 0; i < alternativeMatrix.length; i++) {
				alternativeMatrix[k][i][alternativeMatrix.length - 1].setOwnVector(Math.pow(alternativeMatrix[k][i][0].getValue() * alternativeMatrix[k][i][1].getValue() * alternativeMatrix[k][i][2].getValue() * alternativeMatrix[k][i][3].getValue() * alternativeMatrix[k][i][4].getValue(), 1.0 / alternativeMatrix.length));
			}
		}
	}

	private void countWeightOfCriteria() {
		for (int i = 0; i < criteriaMatrix.length; i++) {
			criteriaMatrix[i][criteriaMatrix.length - 1].setWeightOfCriteria(criteriaMatrix[i][criteriaMatrix.length - 1].getOwnVector() / (criteriaMatrix[0][criteriaMatrix.length - 1].getOwnVector() +
					criteriaMatrix[1][criteriaMatrix.length - 1].getOwnVector() + criteriaMatrix[2][criteriaMatrix.length - 1].getOwnVector() + criteriaMatrix[3][criteriaMatrix.length - 1].getOwnVector()));
		}
	}

	private void countWeightOfAlternative() {
		for (int k = 0; k < amountOfCriteria; k++) {
			for (int i = 0; i < alternativeMatrix.length; i++) {
				alternativeMatrix[k][i][alternativeMatrix.length - 1].setWeightOfCriteria(alternativeMatrix[k][i][alternativeMatrix.length - 1].getOwnVector() / (alternativeMatrix[k][0][alternativeMatrix.length - 1].getOwnVector() +
						alternativeMatrix[k][1][criteriaMatrix.length - 1].getOwnVector() + alternativeMatrix[k][2][alternativeMatrix.length - 1].getOwnVector() + alternativeMatrix[k][3][alternativeMatrix.length - 1].getOwnVector() + alternativeMatrix[k][4][alternativeMatrix.length - 1].getOwnVector()));
			}
		}
	}

	private void fillHeadersForCriteriaMatrix() {
		headersForCriteriaMatrix = new ArrayList<>();
		headersForCriteriaMatrix.add("C1");
		headersForCriteriaMatrix.add("C2");
		headersForCriteriaMatrix.add("C3");
		headersForCriteriaMatrix.add("C4");

	}

	private void fillHeadersForAlternativeMatrix() {
		headersForAlternativeMatrix = new ArrayList<>();
		headersForAlternativeMatrix.add("A1");
		headersForAlternativeMatrix.add("A2");
		headersForAlternativeMatrix.add("A3");
		headersForAlternativeMatrix.add("A4");
		headersForAlternativeMatrix.add("A5");

	}

	private Point compareCriteria() {
		int scaleIndex = getRandomIndex(scale);
		return Point.builder()
				.value(scale.get(scaleIndex))
				.build();
	}

	public static int getRandomIndex(List<Double> scale) {
		Random random = new Random();
		return random.nextInt(scale.size());
	}


	private void fillScale() {
		scale = new ArrayList<>();
		scale.add(1.0);
		scale.add(3.0);
		scale.add(5.0);
		scale.add(7.0);
		scale.add(9.0);
	}

	public void printCriteriaMatrix() {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);

		System.out.print("\t");
		for (String header : headersForCriteriaMatrix) {
			System.out.print(String.format("%-7s", header));
		}
		System.out.print(String.format("%-7s %-7s", "W", "w"));
		System.out.println();

		for (int i = 0; i < criteriaMatrix.length; i++) {
			System.out.print(headersForCriteriaMatrix.get(i) + "\t");
			for (int j = 0; j < criteriaMatrix.length; j++) {
				System.out.print(String.format("%-7s", decimalFormat.format(criteriaMatrix[i][j].getValue())));
			}
			System.out.print(String.format("%-7s", decimalFormat.format(criteriaMatrix[i][criteriaMatrix.length - 1].getOwnVector())));
			System.out.print(String.format("%-7s", decimalFormat.format(criteriaMatrix[i][criteriaMatrix.length - 1].getWeightOfCriteria())));
			System.out.println();
		}
	}


	public void fillAlternativeMatrix(int amountOfAlternative, int amountOfCriteria) {
		this.amountOfCriteria = amountOfCriteria;
		alternativeMatrix = new Point[amountOfCriteria][amountOfAlternative][amountOfAlternative];
		for (int k = 0; k < amountOfCriteria; k++) {
			for (int i = 0; i < amountOfAlternative; i++) {
				for (int j = 0; j < amountOfAlternative; j++) {

					if (i == j) {
						alternativeMatrix[k][i][j] = new Point(1);
					} else if (i < j) {
						alternativeMatrix[k][i][j] = compareCriteria();
					} else {
						alternativeMatrix[k][i][j] = new Point(1.0 / alternativeMatrix[k][j][i].getValue());
					}
				}
			}
		}

		countOwnVectorForAlternative();
		countWeightOfAlternative();
	}

	public void printAlternativeMatrix() {
		System.out.println();
		fillHeadersForAlternativeMatrix();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);

		for (int k = 0; k < amountOfCriteria; k++) {

			printHeaderForAlternativeMatrix(k);

			for (int i = 0; i < alternativeMatrix.length; i++) {
				System.out.print(headersForAlternativeMatrix.get(i) + "\t");
				for (int j = 0; j < alternativeMatrix.length; j++) {
					System.out.print(String.format("%-7s", decimalFormat.format(alternativeMatrix[k][i][j].getValue())));
				}
				System.out.print(String.format("%-7s", decimalFormat.format(alternativeMatrix[k][i][alternativeMatrix.length - 1].getOwnVector())));
				System.out.print(String.format("%-7s", decimalFormat.format(alternativeMatrix[k][i][alternativeMatrix.length - 1].getWeightOfCriteria())));
				System.out.println();
			}
			System.out.println();
		}
	}

	private void printHeaderForAlternativeMatrix(int k) {
		System.out.println("According to criterion C" + (k + 1));
		System.out.print("\t");
		for (String header : headersForAlternativeMatrix) {
			System.out.print(String.format("%-7s", header));
		}
		System.out.print(String.format("%-7s %-7s", "W", "v"));
		System.out.println();
	}

	public void calcQualityIndicator() {
		List<Double> qualityIndicators = new ArrayList<>();
		for (int k = 0; k < amountOfCriteria; k++) {
			double qualityIndicator = 0;
			for (int i = 0; i < criteriaMatrix.length; i++) {
				qualityIndicator += criteriaMatrix[i][criteriaMatrix.length - 1].getWeightOfCriteria()
						* alternativeMatrix[i][k][alternativeMatrix.length - 1].getWeightOfCriteria();
			}
			qualityIndicators.add(qualityIndicator);
		}
		System.out.println(qualityIndicators);
	}
}