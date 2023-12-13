import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GraphGenerator {
    public static void generateGraphJson(List<ClassAnalyzer.ClassInfo> classInfoList, String outputPath) {
        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write("{\n  \"nodes\": [\n");

            // Write nodes
            for (ClassAnalyzer.ClassInfo classInfo : classInfoList) {
                fileWriter.write("    { \"id\": \"" + classInfo.getClassName() + "\", \"group\": \"class\" },\n");
            }

            fileWriter.write("  ],\n  \"links\": [\n");

            // Write links (parent-child relationships)
            for (ClassAnalyzer.ClassInfo classInfo : classInfoList) {
                if (classInfo.getClazz().getSuperclass() != null) {
                    String parentClassName = classInfo.getClazz().getSuperclass().getName();
                    fileWriter.write("    { \"source\": \"" + parentClassName + "\", \"target\": \"" + classInfo.getClassName() + "\" },\n");
                }
            }

            fileWriter.write("  ]\n}");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
