package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DietPlannerTest {

	@BeforeAll
	static void beforeAll() {
		System.out.print("Before all unit test.");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.print("After all unit tests.");
	}
	
	private DietPlanner dietPlanner;
	@BeforeEach
	void setup() {
		this.dietPlanner = new DietPlanner(20, 30, 50);
	}
	
	@AfterEach
	void afterEach() {
		System.out.print("A unit test was finished.");
	}
	
	@Test
	void should_ReturnCorrectDietPlan_When_CorrectCoder() {
		// given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		// when
		DietPlan actual = dietPlanner.calculateDiet(coder);
		// then
		assertAll(
				() -> assertEquals(expected.getCalories(), actual.getCalories()),
				() -> assertEquals(expected.getProtein(), actual.getProtein()),
				() -> assertEquals(expected.getFat(), actual.getFat()),
				() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
				);
	}

}
