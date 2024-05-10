package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	Sorting sorting = new Sorting();
	@Test
	void testaBubbleSort() {
		int[] arr = { 5, 4, 3, 2, 1 };
		int[] sorted = { 1, 2, 3, 4, 5 };

		sorting.bubble(arr);

		assertArrayEquals(sorted, arr);
	}

	@Test
	void deveriaLancarExcecaoNoBubble() {
		int[] arr = null;

		Exception exception = assertThrows(Exception.class, () -> sorting.bubble(arr));
		assertTrue(exception.getMessage().contains("Cannot read the array length because \"numeros\" is null"));
	}

	@Test
	void testaInsertionSort() {
		int[] arr = { 5, 4, 3, 2, 1 };
		int[] sorted = { 1, 2, 3, 4, 5 };

		sorting.insertion(arr);

		assertArrayEquals(sorted, arr);
	}

	@Test
	void deveriaLancarExcecaoNoInsertion() {
		int[] arr = null;

		Exception exception = assertThrows(Exception.class, () -> sorting.insertion(arr));
		assertTrue(exception.getMessage().contains("Cannot read the array length because \"numeros\" is null"));
	}

	@Test
	void testaQuickSort() {
		int[] arr = { 5, 4, 3, 2, 1 };
		int[] sorted = { 1, 2, 3, 4, 5 };

		sorting.quick(arr, 0, arr.length -1);
		
		assertArrayEquals(sorted, arr);
	}

	@Test
	void deveriaLancarExcecaoNoQuick() {
		int[] arr = null;

		Exception exception = assertThrows(Exception.class, () -> sorting.quick(arr, 0, arr.length -1));
		assertTrue(exception.getMessage().contains("Cannot read the array length because \"<parameter1>\" is null"));
	}
}
