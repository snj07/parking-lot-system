package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class contains utility methods for reading the file
 */
public class FileUtils {

    /**
     * Utility method for creating console output like string from an String object
     *
     * @param output - input string
     * @return -  expected console output string
     */
    public static String buildExpectedString(String output) {
        StringWriter expectedStringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(expectedStringWriter);
        String[] strArr = output.split("\n");
        if (strArr.length == 0) return "";
        for (String s : strArr) {
            printWriter.println(s);
        }
        printWriter.close();
        return expectedStringWriter.toString();
    }

    public String readFile(String filePath) {
        Path path = Paths.get(filePath);
        if (path.isAbsolute()) {
            return readAbsoluteFile(path);
        }
        return readResourceFile(filePath);
    }

    public String readAbsoluteFile(Path absoluteFilePath) {
        return readFile(absoluteFilePath);
    }

    public String readResourceFile(String resourceFile) {
        Path path = null;
        try {
            URI fileUri = getFileFromResource(resourceFile);
            if (fileUri == null) {
                System.out.println("Could not find file with path " + resourceFile);
                return null;
            }
            path = Paths.get(getFileFromResource(resourceFile));

        } catch (Exception e) {
            // Fallback to resource access
            try {
                path = Paths.get(Thread.currentThread().getContextClassLoader().getResource(resourceFile).toURI());
            } catch (URISyntaxException uriSyntaxException) {
                System.err.format("URISyntaxException: %s", uriSyntaxException);
            }

        }
        return readFile(path);

    }

    public String readFile(Path filePath) {
        if (filePath == null) {
            return "";
        }
        if (Files.exists(filePath) && !Files.isDirectory(filePath) && Files.isReadable(filePath)) {
            return readFileFromPath(filePath);
        }
        return null;
    }

    private URI getFileFromResource(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource != null) {
            try {
                return resource.toURI();
            } catch (URISyntaxException e) {
                System.err.format("URISyntaxException: %s%n", e);
            }
        }
        return null;

    }

    private String readFileFromPath(Path filePath) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim()).append("\n");
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return sb.toString();

    }
}
