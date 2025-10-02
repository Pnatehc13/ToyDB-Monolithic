import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


class Util {
    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static TreeMap<Integer, Row> Index = new TreeMap<>();
    public static TreeMap<String, ArrayList<Integer>> IndexName = new TreeMap<>();
    public static Scanner sc = new Scanner(System.in);

    public static void reader(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sf = new Scanner(file);
        while (sf.hasNextLine()) {
            String x = sf.nextLine();
            String[] parts = x.split(" ");
            try {
                Row r = new Row(Integer.parseInt(parts[0]), parts[1], parts[2]);
                Util.Index.put(r.id, r);
            } catch (NumberFormatException ne) {
                System.out.println("Invalid arg");
            }
        }
    }

    public static void add(String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        for (Row row : Index.values()) {
            fw.write(String.valueOf(row) + "\n");
        }
        fw.close();
    }
}