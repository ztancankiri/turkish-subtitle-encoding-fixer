import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        try {
            String rootPath = args[0];

            String[] subtitles = new File(rootPath).list((dir, name) -> name.endsWith(".srt"));
            String outPath = Paths.get(rootPath, "out").toString();
            final boolean createdFolder = new File(outPath).mkdir();

            for (String subtitle : Objects.requireNonNull(subtitles)) {
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
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
