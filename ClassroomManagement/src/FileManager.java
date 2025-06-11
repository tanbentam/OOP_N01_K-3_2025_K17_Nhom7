import java.io.*;

public class FileManager {
    public static void saveData(ClassManager manager, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(manager);
            System.out.println("Đã lưu dữ liệu thành công vào file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClassManager loadData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ClassManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Không tìm thấy file dữ liệu, khởi tạo dữ liệu mới.");
            return new ClassManager();  // Nếu chưa có file, tạo mới
        }
    }
}

