app.controller("datHangCtrl", function ($scope, $http) {
    $scope.username = document.getElementById('username').textContent;
    $scope.items = [];
    $scope.form = {};

    $scope.selectedOption = '0';

    $scope.currentPage = 1;
    $scope.itemsPerPage = 8;
    $scope.totalItems;

    $scope.setPage = (pageNo) => {
        $scope.currentPage = pageNo;
        $scope.pageChanged();
        $scope.message = "";
    };

    $scope.filterItems = function () {
        if ($scope.selectedOption != 0) {
            $scope.filteredItems = $scope.items.filter(function (item) {
                //Các trường của list số + .toString(),chữ + toLowerCase()
                return item.orderStatus.toString().includes($scope.selectedOption);
            });
        } else {
            $scope.filteredItems = $scope.items;
        }
        console.log("items fill:", $scope.filteredItems);
        $scope.currentPage = 1;
        $scope.pageChanged();
    };

    $scope.pageChanged = () => {
        var begin = ($scope.currentPage - 1) * $scope.itemsPerPage;
        var end = begin + $scope.itemsPerPage;
        if ($scope.totalItems % 2 !== 0) {
            if ($scope.totalItems < end) {
                var begin = ($scope.currentPage - 1) * $scope.itemsPerPage - 1;
            }
            if ($scope.totalItems === end) {
                $scope.currentPage += 0.5;
            }
        }
        
        $scope.filteredItems;
        $scope.filteredItems.reverse();
        $scope.page = $scope.filteredItems.slice(begin, end);
    };

    $scope.init = () => {
        $http.get(`http://localhost:8080/api/orders/datHang`).then(resp => {
            $scope.items = resp.data;
            console.log("items int:", $scope.items);
            $scope.filteredItems = $scope.items;
            $scope.totalItems = $scope.filteredItems.length + 1;
            $scope.pageChanged();
        }).catch(error => {
            console.log("ErrorPro: ", error)
        });
    }

    $scope.init();

    //Hiển thị table
    $scope.selectedRow = null;
    $scope.showDetails = function (row) {
        $scope.form = angular.copy(row);
        $http.get(`${host}/order-details/OrderId/${$scope.form.orderId}`).then(resp => {
            $scope.orderDetails = resp.data;
        }).catch(error => {
            console.log("Error: ", error.message);
        })
        if ($scope.selectedRow === row) {
            $scope.selectedRow = null;
        } else {
            $scope.selectedRow = row;
        }
    };
    $scope.xacNhanHT = () => {
        var item = angular.copy($scope.form);
        $http.get(`${host}/order-details/OrderId/${item.orderId}`).then(resp => {
            item.orderDetails = resp.data;
            $scope.orderDetails.forEach(detail => {
                var product;
                $http.get(`${host}/products/${detail.product.productId}`).then(resp => {
                    //Product Trong cửa Hàng
                    product = resp.data;
                    if (item.orderType === "import") {
                        product.giaNhap = detail.import_price;
                        product.unitPrice = detail.price;
                        product.quantity = product.quantity + detail.quantity;
                    } else {
                        product.quantity = product.quantity - detail.quantity;
                    }
                    // Cập Nhật lại product
                    $http.put(`${host}/products/${product.productId}`, product).then(reps => {
                        item.orderStatus = 3;
                        $http.put(`${host}/orders/updateTotal/${item.orderId}`, item).then(reps => {
                            $scope.items.forEach((item) => {
                                if (item.orderId === $scope.form.orderId) {
                                    item.orderStatus = 3;
                                }
                            });
                        }).catch(error => {
                            console.log("Error OrderDetail: ", error.data.message)
                        });
                    }).catch(error => {
                        console.log("Error: ", error.message);
                    });
                }).catch(error => {
                    alert("Cập Nhật Thất bại");
                    console.log("Error: ", error.message);
                });
            });
            alert("Cập Nhật Thành Công");
        }).catch(error => {
            console.log("Error: ", error.message);
        })
    }
    //End
    $scope.update = (index) => {
        $http.post('/api/order-details/saveAll', $scope.orderDetails).then(resp => {
            var items = resp.data;
            var totalAmount = items.reduce(function (total, orderDetail) {
                return total + (orderDetail.price * orderDetail.quantity);
            }, 0);
            var obj = angular.copy($scope.form);
            if (index === 1) {
                obj.orderStatus = 2;
            }
            obj.totalPrice = totalAmount;
            $http.put(`${host}/orders/updateTotal/${obj.orderId}`, obj).then(reps => {
                $scope.items.forEach((item) => {
                    if (item.orderId === obj.orderId) {
                        item.totalPrice = totalAmount;
                        if (index === 1) {
                            item.orderStatus = 2;
                        }
                    }
                });
            }).catch(error => {
                console.log("Error OrderDetail: ", error.data.message)
            });
            if (index === 1) {
                alert("Cập Nhật Thành Công");
            } else {
                alert("Xác Nhận Giao Hàng");
            }
        }).catch(error => {
            console.log("ErrorOrder: ", error.data.message);
            alert("Cập Nhật Thất Bại");
        })
    }
});