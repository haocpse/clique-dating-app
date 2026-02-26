# Clique Dating App

## 1. Tổ chức thư mục (Directory Structure)
Dự án backend được xây dựng gọn gàng theo kiến trúc **Spring Boot** tiêu chuẩn:
- `controller/`: Chứa các REST Controller định nghĩa các API endpoints để nhận yêu cầu từ Frontend.
- `service/`: Chứa business logic, xử lý các nghiệp vụ phức tạp của ứng dụng (VD: `DiscoveryService` xử lý vuốt/match, `AuthenticationService` xử lý đăng nhập).
- `repository/`: Các interface kế thừa từ Spring Data (như `JpaRepository`) để thao tác trực tiếp với Database.
- `entity/`: Các lớp (Class) ánh xạ (ORM) trực tiếp với các bảng trong cơ sở dữ liệu.
- `dto/`: (Data Transfer Object) Chứa các lớp Request và Response để mô tả định dạng dữ liệu giao tiếp với Client, tách biệt với Entity.
- `mapper/`: Chứa các Mapper (sử dụng MapStruct) để chuyển đổi dữ liệu qua lại giữa Entity và DTO.
- `config/`: Nơi khai báo các thiết lập cấu hình của hệ thống như Security, JWT, CORS,...
- `exception/`: Cơ chế bắt ngoại lệ tập trung (Global Exception Handler) quy định mã lỗi (ErrorCode) cho toàn ứng dụng.
- `ultis/`: Các class tiện ích dùng chung (VD: `JwtTokenProvider` tạo token, `FileSaver` lưu ảnh).

## 2. Lưu trữ dữ liệu (Data Storage)
- **Cơ sở dữ liệu chính**: Ứng dụng sử dụng **MySQL** làm hệ quản trị cơ sở dữ liệu quan hệ. (Được cấu hình trong file `application.yaml` qua chuỗi kết nối `jdbc:mysql://localhost:3306/clique_dating`).
- **Tương tác DB (ORM)**: Sử dụng **Spring Data JPA (Hibernate)** để xử lý mapping và các raw query.
- **Version Control DB**: Dự án sử dụng **Flyway** để quản lý các thay đổi/migrations của cấu trúc Database.
- **Lưu trữ tệp tin (File Storage)**: Hình ảnh đại diện, ảnh hồ sơ (User, Partner) được lưu trữ cục bộ (local) trong thư mục `uploads/` của dự án (thông qua hàm `FileSaver.save()`).

## 3. Logic Match (Xử lý tương hợp)
Logic Match được thực thi ngay khi một người dùng có hành động vuốt (Nằm trong hàm `action()` của `DiscoveryServiceImpl`):
1. Khi **Người dùng A** thực hiện hành động `LIKE` hoặc `SUPER_LIKE` **Người dùng B**.
2. Hệ thống lập tức truy vấn bảng `Like` để kiểm tra xem **Người dùng B** trước đó đã từng `LIKE` hoặc `SUPER_LIKE` lại **Người dùng A** hay chưa.
3. Nếu **CÓ**, hệ thống xác nhận đây là một **Match**.
4. Nó kiểm tra tiếp xem cặp đôi này đã tồn tại trong bảng `Match` chưa thông qua `matchRepository.existsMatchBetweenUsers`.
5. Nếu chưa từng tạo trước đó, ứng dụng sẽ gọi hàm `match(A, B)` để insert dữ liệu mới vào bảng `Match` với trạng thái `ACTIVE`. Ngay sau đó sẽ tiến hành chạy logic "Tìm slot trùng" (ở dưới).

## 4. Logic Tìm Slot Trùng (Find Matching Slots)
Nhằm tạo điều kiện để 2 người dùng có thể dễ dàng hẹn gặp, ngay khi logic **Match** phía trên thành công, ứng dụng sẽ tự động trích xuất các "ngày rảnh" và đối chiếu:
1. Hệ thống lấy toàn bộ danh sách lịch rảnh (`List<UserAvailability>`) đã cung cấp của cả 2 người dùng (A và B).
2. Sử dụng 2 vòng lặp lồng nhau (nested loop) để đối chiếu chéo từng slot của A với từng slot của B.
3. **Điều kiện để được xem là "Trùng Slot"**:
    - Cả 2 slot phải là lịch rảnh có xác định ngày tháng cụ thể (`specificDate != null`).
    - Cả 2 slot đều đang ở trạng thái kích hoạt (`isActive == true`).
    - Ngày `specificDate` của A và B **trùng khớp hoàn toàn** với nhau (`equals()`).
4. Nếu tìm thấy ngày trùng khớp, hệ thống tự động sinh ra một Lịch hẹn (`DateSchedule`) cho cặp đôi vào ngày đó (lấy mốc thời gian lúc 0h: `atStartOfDay()`).
    - Vai trò: Người vuốt sau (tạo ra Match) được gán làm Người gửi yêu cầu (`requester`), người kia làm Người nhận (`receiver`).
5. Cuối cùng, tất cả các Lịch hẹn trùng khớp này sẽ được insert hàng loạt (`saveAll(schedules)`) vào CSDL. Cặp đôi có thể xem danh sách lịch này, sau đó bổ sung **địa điểm (Partner)** và **Xác nhận (Confirm)** hoặc **Huỷ (Cancel)**.