import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

// a feature envy is a function with a bool output that allows to detect the methods and the data (attributes) that are "bad smells", by comparing the number of used methods internally in a function, and comparing it to how many moethods were used externallly in this method, same for attributes
// basically, the num of internal methods that are used for this specific internerl class, should be greater, than the external methods used for this class, same logic for attributs
public class FeatureEnvyScanner {

    public static void main(String[] args) throws IOException {
        // Provide the path to the JAR file
        String jarFilePath = "C:\\Users\\ikram\\OneDrive\\Bureau\\S9\\Projet_Sadou\\JarGraphAnalyzer\\java-json.jar";

        // Extract JAR contents to a temporary directory
        Path tempDir = Files.createTempDirectory("jarContents");
        extractJar(jarFilePath, tempDir.toString());

        // Iterate through Java source files and analyze them
        Files.walk(Paths.get(tempDir.toString()))
                .filter(Files::isRegularFile)
                .filter(file -> file.toString().endsWith(".java"))
                .forEach(FeatureEnvyScanner::analyzeJavaFile);

        // Clean up: Delete the temporary directory
        Files.walkFileTree(tempDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void extractJar(String jarFilePath, String destination) throws IOException {
        try (FileSystem fs = FileSystems.newFileSystem(Paths.get(jarFilePath), null)) {
            Path jarRoot = fs.getPath("/");
            Files.walkFileTree(jarRoot, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path dest = Paths.get(destination, file.toString());
                    Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    private static void analyzeJavaFile(Path javaFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(javaFilePath.toFile());

            // Parse the Java source file
            CompilationUnit cu = JavaParser.parse(fileInputStream);

            // Create a visitor to detect feature envy
            FeatureEnvyVisitor visitor = new FeatureEnvyVisitor();
            visitor.visit(cu, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Visitor class to detect feature envy
    private static class FeatureEnvyVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);

            // Check if the method is calling methods from another class excessively
            n.findAll(MethodCallExpr.class).forEach(methodCallExpr -> {
                String methodName = methodCallExpr.getNameAsString();
                String declaringClass = methodCallExpr.resolve().getQualifiedName();

                // Simple condition to check if it's a potential feature envy
                if (!Objects.equals(declaringClass, n.resolve().getQualifiedName())) {
                    System.out.println("Potential Feature Envy: Method '" +
                            n.resolve().getQualifiedName() + "' calls method '" +
                            methodName + "' from class '" + declaringClass + "'");
                }
            });
        }
    }
}
