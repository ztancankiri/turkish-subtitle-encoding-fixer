import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            String rootPath = args[0];

            String[] subtitles = new File(rootPath).list((dir, name) -> name.endsWith(".srt"));
            String outPath = Paths.get(rootPath, "out").toString();
            new File (outPath).mkdir();

            for (String subtitle : subtitles) {
                String oldSubtitlePath = Paths.get(rootPath,  subtitle).toString();
                String newSubtitlePath = Paths.get(rootPath, "out", subtitle).toString();

                InputStream in = new FileInputStream(oldSubtitlePath);
                Reader reader = new InputStreamReader(in, Charset.forName("ISO-8859-9"));
                OutputStream out = new FileOutputStream(newSubtitlePath);
                Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                writer.write('\uFEFF');
                char[] buffer = new char[1024];
                int read;
                while ((read = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, read);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
