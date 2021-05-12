# DoAnQLTV
Đồ án Java - Quản lý thư viện với Spring boot framework


# lưu ý một số lệnh:
# Lệnh thymeleaf:
* Chi tiết xem: https://loda.me/spring-boot-9-giai-thich-cach-thymeleaf-van-hanh-expression-demo-full-loda1558267496214/

# Render biến:
vd:
public String Exemple(Model model){
    String ex = "ví dụ";
    // gửi biến ex qua form cần render thông qua biến var
    model.addAttribute("var", ex);
    // render form index
    return "index"
}
- Lấy giá trị của biến ex từ biến var trong form index.html:
vd1: <span th:text="${var}"></span>
-> kết quả: ví dụ
vd2: <span th:text="'Giá trị nhận được là: ' + ${var}"></span>
-> Kết quả: Giá trị nhận được là: ví dụ

# Render List:
public String Exemple(Model model){
    // Lưu vào listEx kiểu ExEntity tất cả những phần từ của ExRepo
   List<ExEntity> listEx = ExRepo.findAll();
    // gửi listEx qua form cần render thông qua biến list
    model.addAttribute("list", listEx);
    // render form index
    return "index"
}

- Lấy giá trị của listEx từ biến list trong form index.html:
vd: do là list nên dùng th:each để duyệt, tương tự dòng forEach Java:
<ul>
    <li th:each="i : ${list}"> 
        <span th:text="*{i.getId()}"></span>
        <span th:text="*{i.getName()}"></span>
    </li>
</ul>


- Set các thuộc tính input:
<input type="text" th:value="${test}" th:placeholder="${test}">
