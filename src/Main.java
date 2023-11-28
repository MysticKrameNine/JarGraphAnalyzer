public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the path to a JAR file.");
            return;
        }

        String jarFilePath = args[0];

        ClassAnalyzer classAnalyzer = new ClassAnalyzer();
        classAnalyzer.analyzeClasses(jarFilePath);
    }
}
