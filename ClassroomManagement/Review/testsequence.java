package ClassroomManagement.Review;

public class testsequence {
    public static void test() {
        sequence s = new sequence(10);
        for (int i = 0; i < 10; i++)
            s.add(Integer.toString(i));

        selector sl = s.getSelector();
        while (!sl.end()) {
            System.out.println(sl.current());
            sl.next();
        }
    }

    // ⚠️ Thêm hàm main để chạy test()
    public static void main(String[] args) {
        System.out.println("🧪 Bắt đầu kiểm thử Sequence:");
        test();
    }
}

