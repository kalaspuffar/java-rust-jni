import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

public class JavaRustFFI {
    private static java.lang.reflect.Field LIBRARIES;

    public static native void hello();
    public static native byte[] optimize_from_memory(byte[] data);


    static {
        try {
            LIBRARIES = ClassLoader.class.getDeclaredField("loadedLibraryNames");
            LIBRARIES.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean getLoadedLibraries(final ClassLoader loader, String absolutePath) throws Exception {
        final Vector<String> libraries = (Vector<String>) LIBRARIES.get(loader);
        return libraries.contains(absolutePath);
    }

/*
    public static String getLibraryPath(String dylib) {
        System.out.println(mapLibraryName(dylib));

        File f = new File(JavaRustFFI.class.getClassLoader().getResource(mapLibraryName(dylib)).getFile());
        return f.getParent();
    }
*/

    public static void main(String[] args) {
        try {
            File f = new File("optimize/target/debug/liboptimize.so");
            System.out.println(JavaRustFFI.getLoadedLibraries(ClassLoader.getSystemClassLoader(), f.getAbsolutePath()));

            System.load(f.getAbsolutePath());

            System.out.println(JavaRustFFI.getLoadedLibraries(ClassLoader.getSystemClassLoader(), f.getAbsolutePath()));

            hello();

            byte[] bytes = JavaRustFFI.optimize_from_memory(new byte[] {1,2,3,4});

            System.out.println(Arrays.toString(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static byte[] readBytes(InputStream inputStream) throws IOException {
        byte[] b = new byte[1024];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int c;
        while ((c = inputStream.read(b)) != -1) {
            os.write(b, 0, c);
        }
        return os.toByteArray();
    }
}
