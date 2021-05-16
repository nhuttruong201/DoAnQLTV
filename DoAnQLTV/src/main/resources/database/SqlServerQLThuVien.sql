create database QuanLyThuVien
go
use QuanLyThuVien
go


create table chucvu
(
	machucvu nvarchar(20) primary key,
	tenchucvu nvarchar(1000) not null
)

create table nhanvien
(
	manhanvien int identity(1, 1) primary key,
	hoten nvarchar(1000) not null,
	namsinh int not null,
	gioitinh nvarchar(20) not null,
	sodienthoai nvarchar(20) not null,
	diachi nvarchar(1000) not null,
	machucvu nvarchar(20) not null,
	foreign key (machucvu) references chucvu (machucvu)
)

create table quyenhan
(
	maquyenhan int primary key,
	tenquyenhan nvarchar(1000) not null
)

create table taikhoan
(
    id int identity(1, 1)  primary key,
	tentaikhoan varchar(50),
	matkhau varchar(50) not null,
	email varchar(1000) not null,
	manhanvien int not null,
	maquyenhan int not null,
	foreign key (manhanvien) references nhanvien (manhanvien),
	foreign key (maquyenhan) references quyenhan (maquyenhan)
)

create table theloai
(
	matheloai nvarchar(20) primary key,
	tentheloai nvarchar(1000) not null
)


create table nhaxuatban
(
	manhaxuatban nvarchar(20) primary key,
	tennhaxuatban nvarchar(1000) not null
)

create table sach
(
	masach int identity(1, 1) primary key,
	tensach nvarchar(1000) not null,
	tacgia nvarchar(1000) not null,
	namxuatban int not null,
	manhaxuatban nvarchar(20) not null,
	matheloai nvarchar(20) not null,
	soluong int not null,
	foreign key (matheloai) references theloai (matheloai),
	foreign key (manhaxuatban) references nhaxuatban (manhaxuatban)
)

create table trangthaithe
(
	matrangthai nvarchar(20) primary key,
	tentrangthai nvarchar(100) not null
)

create table thethuvien
(
	mathe int identity(1, 1) primary key,
	hoten nvarchar(1000) not null,
	gioitinh nvarchar(20) not null,
	sodienthoai nvarchar(30) not null,
	diachi nvarchar(1000) not null,
	hansudung date,
	matrangthai nvarchar(20) not null,
	foreign key (matrangthai) references trangthaithe (matrangthai)
)


create table phieumuon
(
	maphieumuon int identity(1, 1) primary key,
	mathe int not null,
	manhanvien int not null,
	ngaymuon date not null,
    hantra date not null,
	ngaytra date,
	trangthai int not null,
	foreign key (mathe) references thethuvien (mathe),
	foreign key (manhanvien) references nhanvien (manhanvien)
)

create table chitietphieumuon
(
	mactpm int identity primary key,
	maphieumuon int not null,
	masach int not null,
	soluong int not null,
	foreign key (maphieumuon) references phieumuon (maphieumuon),
	foreign key (masach) references sach (masach)
	--constraint pk_ctpm primary key(maphieumuon, masach)
)

set dateformat dmy;

-- insert nhà xuất bản
insert into nhaxuatban values ('kimdong', N'Nhà xuất bản Kim Đồng');
insert into nhaxuatban values ('dantri', N'Nhà xuất bản Dân trí');
insert into nhaxuatban values ('tre', N'Nhà xuất bản Trẻ');
insert into nhaxuatban values ('dhqghanoi', N'Nhà xuất bản Đại học Quốc gia Hà Nội');
insert into nhaxuatban values ('dhqgtphcm', N'Nhà xuất bản Đại học Quốc gia Thành phố Hồ Chí Minh');
insert into nhaxuatban values ('giaoduc', N'Nhà xuất bản Giáo Dục');
insert into nhaxuatban values ('laodong', N'Nhà xuất bản Lao Động');
insert into nhaxuatban values ('qtqgst', N'Nhà xuất bản Chính trị Quốc gia - Sự thật');
-- insert thể loại
insert into theloai values ('ctpl', N'Chính trị pháp luật');
insert into theloai values ('khcnkt', N'Khoa học Công nghệ - kinh tế');
insert into theloai values ('vhnt', N'Văn học nghệ thuật');
insert into theloai values ('vhxhls', N'Văn hóa Xã hội - Lịch sử');
insert into theloai values ('gt', N'Giáo trình');
insert into theloai values ('ttt', N'Truyện, tiểu thuyết');
insert into theloai values ('tltltg', N'Tâm lý');
insert into theloai values ('tn', N'Thiếu nhi');
-- insert sách
insert into sach values (N'Đắc Nhân Tâm', N'Dale Carnegie', 2000, 'tre', 'tltltg', 100);
insert into sach values (N'Tuổi trẻ đáng giá bao nhiêu', N'Roise Nguyễn', 2000, 'tre', 'vhnt', 100);
insert into sach values (N'Cho tôi xin 1 vé đi tuổi thơ', N'Nguyễn Nhật Ánh', 2000, 'tre', 'vhnt', 100);
-- insert chức vụ
insert into chucvu values ('cvql', N'Quản lý');
insert into chucvu values ('cvtt', N'Thủ thư');
-- insert nhân viên
insert into nhanvien values ( N'Võ Nhựt Trường', 2000, N'Nam', '0356616590', N'Thủ Đức', 'cvql');
insert into nhanvien values ( N'Đỗ Quang Trung', 2000, N'Nam', '1111111111', N'Thủ Đức', 'cvql');
insert into nhanvien values ( N'Nguyễn Hoàng Vinh', 2000, N'Nam', '2222222222', N'Thủ Đức', 'cvql');
insert into nhanvien values ( N'Lương Hoàng Long', 2000, N'Nam', '2222222222', N'Thủ Đức', 'cvtt');
-- insert quyền hạn
insert into quyenhan values (0, 'Staff');
insert into quyenhan values (1, 'Admin');
-- insert tài khoản
insert into taikhoan values ('admin', 'admin', 'nhuttruongvo201@gmail.com', 1, 1);
insert into taikhoan values ('nhuttruong', 'admin', 'null', 1, 1);
insert into taikhoan values ('quangtrung', 'admin', 'null', 2, 1);
insert into taikhoan values ('hoangvinh', 'admin', 'null', 3, 1);
insert into taikhoan values ('staff', 'admin', 'null', 4, 0);

-- insert trạng thái thẻ
insert into trangthaithe values ('open', N'Mở');
insert into trangthaithe values ('lock', N'Bị khóa');
-- insert thẻ thư viện
-- set dateformat trước khi insert mới
set dateformat dmy;
insert into thethuvien values (N'Nguyễn Văn A', N'Nam', '1111', N'Thủ Đức', '04/05/2021', 'open');
insert into thethuvien values (N'Nguyễn Thị B', N'Nữ', '2222', N'Tân Bình', '04/05/2021', 'open');
insert into thethuvien values (N'Nguyễn Thị C', N'Nữ', '3333', N'Quận 1', '30/04/2021', 'lock');
insert into thethuvien values (N'Nguyễn Thị D', N'Nam', '4444', N'Hóc Môn', '30/04/2021', 'lock');
--_______________________________________________________________________________________________________




