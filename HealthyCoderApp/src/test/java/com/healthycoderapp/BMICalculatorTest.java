package com.healthycoderapp;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


class BMICalculatorTest {

	private String environment = "dev";
	
	@BeforeAll
	static void beforeAll() {
		System.out.print("Before all unit test.");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.print("After all unit tests.");
	}
	
	@Nested
	class IsDietRecommendedTests{
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
		
		@RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
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
	}
	
	@Nested
	class FindCoderWithWorstBMITests{
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
		@DisplayName(value = "Get Worst Coder from populated list with 1000 elements in less than 500 ms")
		void should_ReturnCoderWithWorstBMIIn1Ms_WhenCoderListHas10000Elements() {
			// given
			assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
			List<Coder> coders = new ArrayList<>();
			for (int i=0; i < 10000; i++) {
				coders.add(new Coder(1.0 + i, 10.0 + i));
			}
			//when
			Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
			//then
			assertTimeout(Duration.ofMillis(500), executable);
		}
	}
	
	@Nested
	class GetBMIScoresTests{
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
}

