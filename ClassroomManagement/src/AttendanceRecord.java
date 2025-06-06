import java.time.LocalDate;
import java.util.Objects;

// Bản ghi điểm danh
public class AttendanceRecord {
    private LocalDate date;
    private boolean isPresent;
    private boolean hasPermission;

    public AttendanceRecord(LocalDate date, boolean isPresent, boolean hasPermission) {
        this.date = date;
        this.isPresent = isPresent;
        this.hasPermission = hasPermission;
    }

    // Getter methods
    public LocalDate getDate() {
        return date;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public boolean hasPermission() {
        return hasPermission;
    }

    // 🆕 Xuất bản ghi điểm danh dưới dạng chuỗi định dạng
    public String getFormattedRecord() {
        return String.format("Ngày: %s | Có mặt: %s | Có phép: %s",
                date.toString(),
                isPresent ? "✔️ Có" : "❌ Không",
                hasPermission ? "✔️ Có" : "❌ Không");
    }

    // 🆕 Override toString() để dễ dàng xuất báo cáo
    @Override
    public String toString() {
        return getFormattedRecord();
    }

    // 🆕 Đảm bảo mỗi bản ghi điểm danh là duy nhất theo ngày
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AttendanceRecord)) return false;
        AttendanceRecord other = (AttendanceRecord) obj;
        return Objects.equals(date, other.date) &&
               isPresent == other.isPresent &&
               hasPermission == other.hasPermission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, isPresent, hasPermission);
    }
}
