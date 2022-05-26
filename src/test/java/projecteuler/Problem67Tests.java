package projecteuler;

import org.junit.jupiter.api.Test;
import projecteuler.problem67.Triangle;
import static projecteuler.problem67.Triangle.newInstance;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for Problems 18 and 67 from Project Euler")
class Problem67Tests {

    static class  Challenge67ArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments>  provideArguments(ExtensionContext context) throws IOException {
            ClassLoader classLoader = getClass().getClassLoader();
            Path   path18 = Paths.get(Objects.requireNonNull(classLoader.getResource("p018_triangle.txt")).getFile()),
                   path67 = Paths.get(Objects.requireNonNull(classLoader.getResource("p067_triangle.txt")).getFile());
            return Stream.of(
                    Arguments.of(1074, Files.lines(path18)),
                    Arguments.of(7273, Files.lines(path67)));
        }
    }

    @DisplayName("https://projecteuler.net/problem=18 and https://projecteuler.net/problem=67")
    @ParameterizedTest @ArgumentsSource(Challenge67ArgumentsProvider.class)
    void  problem67(int expectedResult, Stream<String> rows) {
        var triangle = newInstance(rows.toArray(String[]::new));
        assertEquals(expectedResult, triangle.findMaxPathSum());
    }

    @DisplayName("Verifies if Triangle's static factory methods work correctly")
    @Test
    void  problem67StaticFactories() {
        Triangle[]   triangles = {
                newInstance(new int[] {  3, 7, 4, 2, 4, 6, 8, 5, 9, 3  }),
                newInstance("3, 7, 4, 2, 4, 6, 8, 5, 9, 3"),
                newInstance(new String[] { "3", "7 4", "2 4 6", "8 5 9 3" })
        };

        for (var triangle : triangles) {
            assertEquals(23, triangle.findMaxPathSum());
            assertEquals(23, triangle.findMaxPathSumNaive());
        }
        // Incorrect number of elements in the last line must result in an IllegalArgumentException
        assertThrowsExactly(IllegalArgumentException.class, () -> newInstance("3, 7, 4, 2, 4, 6, 8, 5, 9, 3, 0"));
    }
}