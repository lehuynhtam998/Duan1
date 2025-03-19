var site = angular.module("mySite", ["ngRoute"]);
console.log("js thanh cong");

site.config(function ($httpProvider) {
  $httpProvider.defaults.headers.common["Authorization"] =
    "Basic Z3Vlc3Q6MTIz";
});

site.factory("ProductService", [
  "$http",
  function ($http) {
    var baseUrl = "/api/products/suggestions";

    return {
      getSuggestions: function (keywords) {
        return $http.get(baseUrl, { params: { keywords: keywords } });
      },
    };
  },
]);

let host = "http://localhost:8080/api";

site.controller("siteCtrl", [
  "$scope",
  "$http",
  "ProductService",
  function ($scope, $http, ProductService) {
    $scope.searchQuery = "";
    $scope.searchResults = [];

    $scope.search = function () {
      if ($scope.searchQuery.length > 2) {
        ProductService.getSuggestions($scope.searchQuery).then(
          function (response) {
            $scope.searchResults = response.data;
          },
          function (error) {
            console.error("Error fetching suggestions:", error);
          }
        );
      } else {
        $scope.searchResults = [];
      }
    };

    // ------------Xu ly Cart
    var usernameElement = document.getElementById("username");
    if (usernameElement) {
      $scope.username = usernameElement.textContent;
    } else {
      $scope.username = "";
    }

    $scope.products = [];
    $scope.product = {};

    $scope.productImages = [];
    $scope.productWithImgs = [];
    $scope.totalPrice = 0;

    $scope.loadFromLocalStorage = () => {
      var json = localStorage.getItem("cart");
      $scope.productWithImgs = json ? JSON.parse(json) : [];
    };

    $scope.saveToLocalStorage = () => {
      var json = JSON.stringify(angular.copy($scope.productWithImgs));
      localStorage.setItem("cart", json);
    };

    $scope.add = function (id) {
      debugger;
      console.log("Nút Thêm vào giỏ hàng đã được nhấn với ID:", id);

      $http
        .get(`${host}/products/${id}`)
        .then(function (response) {
          let product = response.data;
          let existingProduct = $scope.productWithImgs.find(
            (item) => item.product.productId === product.productId
          );
          if (existingProduct) {
            existingProduct.product.quantity += 1;
            $scope.saveToLocalStorage();
            $scope.showAlert("Sản phẩm đã được thêm vào giỏ hàng!");
          } else {
            product.quantity = 1;
            $http.get(`${host}/product-images`).then(function (response) {
              let productImages = response.data;
              let productImage = productImages.find(
                (img) => img.product.productId === product.productId
              );
              $scope.productWithImgs.push({
                product: product,
                imgUrl: productImage ? productImage.url : null,
              });
              $scope.saveToLocalStorage();
              $scope.showAlert("Sản phẩm đã được thêm vào giỏ hàng!");
            });
          }
        })
        .catch(function (error) {
          console.error("Lỗi khi lấy thông tin sản phẩm:", error);
        });
    };
    $scope.remove = function (id) {
      $scope.productWithImgs = $scope.productWithImgs.filter(
        (item) => item.product.productId !== id
      );
      $scope.saveToLocalStorage();
    };

    $scope.total = () => {
      return $scope.productWithImgs
        .map((item) => item.product.unitPrice * item.product.quantity)
        .reduce((totalPrice, quantity) => (totalPrice += quantity), 0);
    };

    $scope.showAlert = (message) => {
      var alertBox = document.createElement("div");
      alertBox.innerHTML = message;
      alertBox.style.cssText =
        "position:fixed;top:13.5%;right:13.5%;width:20%;border-radius: 10px;background-color:rgba(242, 113, 37,0.7);color:white;text-align:center;padding:20px;z-index:9999;";
      document.body.appendChild(alertBox);

      setTimeout(function () {
        document.body.removeChild(alertBox);
      }, 1000);
    };

    $scope.clear = () => {
      $scope.productWithImgs = [];
      $scope.saveToLocalStorage();
    };

    $scope.loadFromLocalStorage();

    // ------------Xu ly Place order

    $scope.order = {
      orderType: "order",
      orderDate: new Date(),
      seller: $scope.username,
      totalPrice: $scope.total(),
      sales_channel: "online",
      orderStatus: 1,
      branch: { branchId: 1 },
      get orderDetails() {
        return $scope.productWithImgs.map((item) => {
          return {
            product: { productId: item.product.productId },
            price: item.product.unitPrice,
            quantity: item.product.quantity,
            import_price: 0,
          };
        });
      },
      purchase() {
        var order = angular.copy($scope.order);
        $http
          .post(`${host}/orders`, order)
          .then((resp) => {
            alert("Đặt Hàng thành công");
            $scope.clear();
            location.href = "http://localhost:8080/sms/orderhistory";
          })
          .catch((error) => {
            alert("Đặt Hàng thất bại");
            console.log(error);
          });
      },
    };

    // ------------Xu ly Order history

    $scope.orderHistory = [];
    console.log($scope.username);
    $http
      .get(`${host}/orders/${$scope.username}/history`)
      .then((resp) => {
        $scope.orderHistory = resp.data;
        var username = { username: $("#username").text().trim() };
        $scope.filteredOrderHistory = $scope.orderHistory.filter(
          (order) => order.branch.managerBranch.username === username
        );
        console.log($scope.orderHistory);
      })
      .catch((error) => {
        console.log(error);
      });

      $scope.cancelOrder = function (orderId) {
        console.log("Cancelling order with ID:", orderId);
        
        if (confirm("Bạn có chắc muốn huỷ đơn hàng này không?")) {
            // Gửi yêu cầu hủy đơn hàng tới server
            $http.put('/sms/cancel/' + orderId)
                .then(function (response) {
                    // Xóa đơn hàng khỏi giao diện người dùng
                    const index = $scope.orderHistory.findIndex(order => order.orderId === orderId);
                    if (index > -1) {
                        $scope.orderHistory.splice(index, 1);
                        alert("Đơn hàng đã được huỷ thành công.");
                        $scope.loadOrderHistory();
                    } else {
                        alert("Không tìm thấy đơn hàng.");
                        $scope.loadOrderHistory();
                    }
                })
                .catch(function (error) {
                    // Xử lý lỗi nếu có
                    alert("Đơn hàng đã được huỷ thành công.");
                    console.error("Error cancelling order:", error);
                    $scope.loadOrderHistory();
                });
            
            // Có thể gọi loadOrderHistory() ở đây nếu bạn muốn lấy lại dữ liệu từ server
            $scope.loadOrderHistory();
        }
    };

    $scope.loadOrderHistory = function () {
      $http
        .get(`${host}/orders/${$scope.username}/history`)
        .then((resp) => {
          $scope.orderHistory = resp.data;
          console.log($scope.orderHistory);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    

  },


]);


