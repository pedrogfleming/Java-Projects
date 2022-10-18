package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


class BMICalculatorTest {

	@ParameterizedTest
	@DisplayName(value="Diet recommended")
	@ValueSource(doubles = {89.0, 110.0})
	void should_ReturnTrue_When_DietRecommended(Double coderWeight) {
		// given
		double weight = coderWeight;
		double height = 1.72;
		// when
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		// then
		assertTrue(recommended);		
	}
	
	@ParameterizedTest(name = "weight={0}, height={1}")
	@DisplayName(value="Diet no recommended from array of strings")
	@CsvSource(value = {"40.0, 1.72", "40.0, 1.75", "60.0, 1.78"})
	void should_ReturnFalse_When_DietNotRecommended(Double coderWeight, Double coderHeight) {
		// given
		double weight = coderWeight;
		double height = coderHeight;
		// when
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		// then
		assertFalse(recommended);		
	}
	
	@ParameterizedTest(name = "weight={0}, height={1}")
	@DisplayName(value = "Diet is recommend from CSV file")
	@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
	void should_ReturnTrue_When_DietRecommended_FromCSV(Double coderWeight, Double coderHeight) {
		// given
		double weight = coderWeight;
		double height = coderHeight;
		// when
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		// then
		assertTrue(recommended);		
	}
	
	@Test
	@DisplayName(value = "Height cant be zero")
	void should_ThrowArithmeticException_When_HeightZero() {
		// given
		double weight = 50.0;
		double height = 0;
		// when
		Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
		// then
		assertThrows(ArithmeticException.class, executable);		
	}
	
	@Test
	@DisplayName(value = "Get the worst coder of a populated list")
	void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
		// given
		List<Coder> coders = new ArrayList<>();
		coders.add(new Coder(1.80, 60.0));
		coders.add(new Coder(1.82, 98.0));
		coders.add(new Coder(1.82, 64.7));		
		// when
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
		// then
		assertAll(
				() -> assertEquals(1.82, coderWorstBMI.getHeight()),
				() -> assertEquals(98.0,coderWorstBMI.getWeight())
				);		
	}
	
	@Test
	@DisplayName(value = "Get null on worst coder when the list is empty")
	void should_ReturnNullWorstBMICoder_When_CoderListEmpty() {
		// given
		List<Coder> coders = new ArrayList<>();
		// when
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
		// then
		assertNull(coderWorstBMI);
	}
	
	@Test
	@DisplayName(value = "Get correct BMI scores of populated list")
	void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
		// given
		List<Coder> coders = new ArrayList<>();
		coders.add(new Coder(1.80, 60.0));
		coders.add(new Coder(1.82, 98.0));
		coders.add(new Coder(1.82, 64.7));	
		double[] expected = {18.52, 29.59, 19.53};
		// when
		double[] bmiScores = BMICalculator.getBMIScores(coders);
		// then
		assertArrayEquals(expected, bmiScores);		
	}
}
