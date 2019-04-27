import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bshekhawat
 */
class Helpers {

    private static final String PRODUCTS_FILE = "src/main/resources/products.tab";
    private static final String SALES_FILE = "src/main/resources/sales.tab";


    /**
     * Reads the text file line by line
     *
     * @return {@code Stream<String>}
     * @throws IOException
     */
    private static Stream<String> productsFileStreamer() throws IOException {
        return Files.lines(Paths.get(PRODUCTS_FILE), Charset.defaultCharset())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet())
                .stream();
    }

    /**
     * Reads the text file line by line
     *
     * @return {@code Stream<String>}
     * @throws IOException
     */
    private static Stream<String> salesFileStreamer() throws IOException {
        return Files.lines(Paths.get(SALES_FILE), Charset.defaultCharset())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet())
                .stream();
    }


    /**
     * Extracts and maps test values from a string representation of each test line
     *
     * @throws IOException
     */
    public static List<Object[]> productsToObjectMapper() throws IOException {
        return productsFileStreamer().map(String::valueOf)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .map(s -> s.split("\t"))
                .map(array -> new Object[]{array[0], array[1]})
                .collect(Collectors.toList());
    }

    /**
     * Extracts and maps test values from a string representation of each test line
     *
     * @throws IOException
     */
    public static List<Object[]> salesToObjectMapper() throws IOException {
        return salesFileStreamer().map(String::valueOf)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .map(s -> s.split("\t"))
                .map(array -> new Object[]{array[0], array[1]})
                .collect(Collectors.toList());
    }

}
