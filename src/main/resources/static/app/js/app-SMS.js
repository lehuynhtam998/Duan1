// var app = angular.module("app-SMS", ["ngRoute"]);

// app.config(function ($routeProvider) {
//   $routeProvider
//     .when("/", {
//       templateUrl: "/app/spa/home.html",
//     })
//     .when("/thietLapChiNhanh", {
//       templateUrl: "/app/spa/thietLapChiNhanh.html",
//       controller: "QLChiNhanhCtrl"
//     })
//     .when("/quanLyChiNhanh", {
//       templateUrl: "/app/spa/quanLyChiNhanh.html",
//       controller: "QLChiNhanhCtrl"
//     })
//     .when("/quanLyNguoiDung", {
//       templateUrl: "/app/spa/quanLyNguoiDung.html",
//       controller: "qLyUserCtrl"
//     })
//     .when("/thietLapNguoiDung", {
//       templateUrl: "/app/spa/thietLapNguoiDung.html",
//       controller: "qLyUserCtrl"
//     })
//     .when("/f", {
//       templateUrl: "/app/spa/thietLapVaiTro.html",
//     })
//     .when("/danhmuc", {
//       templateUrl: "/app/spa/danhmuc.html",
//       controller: "danhMucCtrl",
//     })
//     .when("/themMoiHangHoa", {
//       templateUrl: "/app/spa/themMoiHangHoa.html",
//       controller: "danhMucCtrl",
//     })
//     .when("/loaiSanPham", {
//       templateUrl: "/app/spa/loaiSanPham.html",
//       controller: "loaiSanPhamCtrl",
//     })
//     .when("/kiemkho", {
//       templateUrl: "/app/spa/kiemkho.html",
//     })
//     .when("/themMoiKiemKho", {
//       templateUrl: "/app/spa/themMoiKiemKho.html",
//       controller: "kiemKhoCtrl",
//     })
//     .when("/dathang", {
//       templateUrl: "/app/spa/dathang.html",
//       controller: "datHangCtrl",
//     })
//     .when("/themMoiDathang", {
//       templateUrl: "/app/spa/themMoiDathang.html",
//       controller: "AddDatHangCtrl",
//     })
//     .when("/nhaphang", {
//       templateUrl: "/app/spa/nhaphang.html",
//       controller: "nhapHangCtrl"
//     })
//     .when("/themMoiNhapHang", {
//       templateUrl: "/app/spa/themMoiNhapHang.html",
//       controller: "AddNhapHangCtrl"
//     })
//     .when("/chuyenhang", {
//       templateUrl: "/app/spa/chuyenhang.html",
//       controller: "chuyenHangCtrl"
//     })
//     .when("/themMoiChuyenHang", {
//       templateUrl: "/app/spa/themMoiChuyenHang.html",
//     })
//     .when("/hoadon", {
//       templateUrl: "/app/spa/hoadon.html",
//       controller: "hoaDonCtrl"
//     })
//     .when("/themMoiHoaDon", {
//       templateUrl: "/app/spa/themMoiHoaDon.html", Cart

//     })
//     .when("/khoHang", {
//       templateUrl: "/app/spa/khoHang.html",
//     })
//     .when("/themMoiKhoHang", {
//       templateUrl: "/app/spa/themMoiKhoHang.html",
//     })
//     .when("/khachhang", {
//       templateUrl: "/app/spa/khachhang.html",
//     })
//     .when("/themMoiKhachHang", {
//       templateUrl: "/app/spa/themMoiKhachHang.html",
//     })
//     .when("/nhacungcap", {
//       templateUrl: "/app/spa/nhacungcap.html",
//       controller: "SupplierCtrl",
//     })
//     .when("/themMoiNhaCungCap", {
//       templateUrl: "/app/spa/themMoiNhaCungCap.html",
//       controller: "SupplierCtrl",
//     })
//     .when("/bccuoingay", {
//       templateUrl: "/app/spa/bccuoingay.html",
//     })
//     .when("/bcbanhang", {
//       templateUrl: "/app/spa/bcbanhang.html",
//     })
//     .when("/bchanghoa", {
//       templateUrl: "/app/spa/bchanghoa.html",
//     })
//     .when("/banHang", {
//       templateUrl: "/app/spa/banHang.html",
//       controller: "banHangCtrl",
//     })
//     .when("/authority", {
//       templateUrl: "/app/spa/authority.html",
//       controller: "authorityCtrl",
//     })
//     .when("/homthu", {
//       templateUrl: "/app/spa/homthu.html",
//       controller: "EmailController",
//     })
//     .otherwise({
//       redirectTo: "/",
//     });

//   // $httpProvider.defaults.headers.common["Authorization"] =
//   //   "Basic Z3Vlc3Q6MTIz";
// });

// let host = "http://localhost:8080/api";


var app = angular.module("app-SMS", ["ngRoute"]);

app.config(function ($routeProvider, $httpProvider) {
  $routeProvider
    .when("/", {
      templateUrl: "/app/spa/home.html",
    })
    .when("/thietLapChiNhanh", {
      templateUrl: "/app/spa/thietLapChiNhanh.html",
      controller: "QLChiNhanhCtrl"
    })
    .when("/quanLyChiNhanh", {
      templateUrl: "/app/spa/quanLyChiNhanh.html",
      controller: "QLChiNhanhCtrl"
    })
    .when("/quanLyNguoiDung", {
      templateUrl: "/app/spa/quanLyNguoiDung.html",
      controller: "qLyUserCtrl"
    })
    .when("/thietLapNguoiDung", {
      templateUrl: "/app/spa/thietLapNguoiDung.html",
      controller: "qLyUserCtrl"
    })
    .when("/f", {
      templateUrl: "/app/spa/thietLapVaiTro.html",
    })
    .when("/themMoiDathang", {
      templateUrl: "/app/spa/themMoiDathang.html",
      controller: "AddDatHangCtrl",
    })
    .when("/danhmuc", {
      templateUrl: "/app/spa/danhmuc.html",
      controller: "danhMucCtrl",
    })
    .when("/themMoiHangHoa", {
      templateUrl: "/app/spa/themMoiHangHoa.html",
      controller: "danhMucCtrl",
    })
    .when("/loaiSanPham", {
      templateUrl: "/app/spa/loaiSanPham.html",
      controller: "loaiSanPhamCtrl",
    })
    .when("/kiemkho", {
      templateUrl: "/app/spa/kiemkho.html",
    })
    .when("/themMoiKiemKho", {
      templateUrl: "/app/spa/themMoiKiemKho.html",
      controller: "kiemKhoCtrl",
    })
    .when("/dathang", {
      templateUrl: "/app/spa/dathang.html",
      controller: "datHangCtrl",
    })
    .when("/nhaphang", {
      templateUrl: "/app/spa/nhaphang.html",
      controller: "nhapHangCtrl"
    })
    .when("/themMoiNhapHang", {
      templateUrl: "/app/spa/themMoiNhapHang.html",
      controller: "AddNhapHangCtrl"
    })
    .when("/chuyenhang", {
      templateUrl: "/app/spa/chuyenhang.html",
      controller: "chuyenHangCtrl"
    })
    .when("/themMoiChuyenHang", {
      templateUrl: "/app/spa/themMoiChuyenHang.html",
    })
    .when("/hoadon", {
      templateUrl: "/app/spa/hoadon.html",
      controller: "hoaDonCtrl"
    })
    .when("/themMoiHoaDon", {
      templateUrl: "/app/spa/themMoiHoaDon.html",
    })
    .when("/khoHang", {
      templateUrl: "/app/spa/khoHang.html",
    })
    .when("/themMoiKhoHang", {
      templateUrl: "/app/spa/themMoiKhoHang.html",
    })
    .when("/khachhang", {
      templateUrl: "/app/spa/khachhang.html",
    })
    .when("/themMoiKhachHang", {
      templateUrl: "/app/spa/themMoiKhachHang.html",
    })
    .when("/nhacungcap", {
      templateUrl: "/app/spa/nhacungcap.html",
      controller: "SupplierCtrl",
    })
    .when("/themMoiNhaCungCap", {
      templateUrl: "/app/spa/themMoiNhaCungCap.html",
      controller: "SupplierCtrl",
    })
    .when("/bccuoingay", {
      templateUrl: "/app/spa/bccuoingay.html",
      controller: "ChartController",
    })
    .when("/bcbanhang", {
      templateUrl: "/app/spa/bcbanhang.html",
    })
    .when("/bchanghoa", {
      templateUrl: "/app/spa/bchanghoa.html",
    })
    .when("/banHang", {
      templateUrl: "/app/spa/banHang.html",
      controller: "banHangCtrl",
    })
    .when("/authority", {
      templateUrl: "/app/spa/authority.html",
      controller: "authorityCtrl",
    })
    .when("/homthu", {
      templateUrl: "/app/spa/homthu.html",
      controller: "EmailController",
    })
    .otherwise({
      redirectTo: "/",
    });

  $httpProvider.defaults.headers.common["Authorization"] =
    "Basic YWRtaW46MTIzNDU2";
});

let host = "http://localhost:8080/api";

