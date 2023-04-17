package klee.contrib.spring.search.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringUtilsTest {

	static class first2LowerCase {
		@ParameterizedTest
		@CsvSource({ " '', ''", "toto, toto", "Toto, toto" })
		void allCases(String entry, String transformed) {
			// THEN
			var result = StringUtils.first2LowerCase(entry);
			// THEN
			assertEquals(transformed, result);
		}
	}

	static class first2UpperCase {
		@ParameterizedTest
		@CsvSource({ " '', ''", "toto, Toto", "Toto, Toto" })
		void allCases(String entry, String transformed) {
			// THEN
			var result = StringUtils.first2UpperCase(entry);
			// THEN
			assertEquals(transformed, result);
		}
	}

	static class constToLowerCamelCase {
		@ParameterizedTest
		@CsvSource({ "\"\", \"\"", "TOTO, toto", "TOTO_TATA, totoTata", "_TOTO_TATA, TotoTata" })
		void allCases(String entry, String transformed) {
			// THEN
			var result = StringUtils.constToLowerCamelCase(entry);
			// THEN
			assertEquals(transformed, result);
		}
	}

	static class constToUpperCamelCase {
		@ParameterizedTest
		@CsvSource({ "\"\", \"\"", "TOTO, Toto", "TOTO_TATA, TotoTata", "_TOTO_TATA, TotoTata" })
		void allCases(String entry, String transformed) {
			// THEN
			var result = StringUtils.constToUpperCamelCase(entry);
			// THEN
			assertEquals(transformed, result);
		}
	}
}
