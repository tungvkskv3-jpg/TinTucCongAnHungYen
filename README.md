# Tin tức Công an Hưng Yên (Android)
Ứng dụng Kotlin/WebView đọc nội dung trực tiếp từ https://congan.hungyen.gov.vn/.

## Cách chạy
1. Giải nén dự án, mở thư mục `HungYenNews` bằng Android Studio.
2. Chờ Gradle Sync (cần mạng, Android SDK 35, JDK 17).
3. Kết nối điện thoại Android có bật USB debugging hoặc chọn emulator.
4. Bấm Run để cài ứng dụng. Muốn xuất APK: Build > Build Bundle(s) / APK(s) > Build APK(s).

## Chức năng
Trang chủ, Tin ANTT, Cảnh báo tội phạm, tìm kiếm website, lưu một liên kết, chia sẻ, làm mới, điều hướng Back. Tìm kiếm sử dụng URL dự kiến `tim-kiem.html?keyword=`; cần kiểm thử với biểu mẫu tìm kiếm thực tế của website. Ứng dụng không có thông báo đẩy, không lưu toàn bộ tin offline. Đây là ứng dụng đọc tin độc lập, không phải ứng dụng chính thức của Công an Hưng Yên.


## Biểu tượng ứng dụng
Biểu tượng được tạo từ mẫu hình người dùng đã chọn, nằm tại app/src/main/res/mipmap-*/ic_launcher.png và được khai báo trong AndroidManifest.xml.
