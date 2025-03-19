app.controller("chuyenHangCtrl", function ($scope, $http) {
    $scope.username = document.getElementById('username').textContent;
    $scope.items = [];
    $scope.form = {};

    $scope.currentPage = 1;
    $scope.itemsPerPage = 5;
    $scope.totalItems;

    $scope.setPage = (pageNo) => {
        $scope.currentPage = pageNo;
        $scope.pageChanged();
        $scope.message = "";
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
        $scope.page = $scope.items.slice(begin, end);
    };

    $scope.init = () => {
        $http.get(`http://localhost:8080/api/orders/chuyenHang`).then(resp => {
            $scope.items = resp.data;
            console.log("items:", $scope.items);
            $scope.totalItems = $scope.items.length;
            $scope.pageChanged();
        }).catch(error => {
            console.log("ErrorPro: ", error)
        });
    }

    $scope.init();

    // Hiển thị table
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
    // End
//Phần code của Tâm

    $scope.update = () => {
        var item = angular.copy($scope.form);
        $http.get(`${host}/order-details/OrderId/${item.orderId}`).then(resp => {
            item.orderDetails = resp.data;
            let updateProductPromises = [];
            $scope.orderDetails.forEach(detail => {
                updateProductPromises.push($http.get(`${host}/products/${detail.product.productId}`).then(resp => {
                    let product = resp.data;
                    if (item.orderType === "import") {
                        product.giaNhap = detail.import_price;
                        product.unitPrice = detail.price;
                        product.quantity += detail.quantity;
                    } else {
                        product.quantity -= detail.quantity;
                    }
                    return $http.put(`${host}/products/${product.productId}`, product);
                }));
            });

            Promise.all(updateProductPromises).then(() => {
                $http.put(`${host}/orders/updateTotal/${item.orderId}`, item).then(() => {
                    $scope.items.forEach(itm => {
                        if (itm.orderId === $scope.form.orderId) {
                            itm.orderStatus = item.orderStatus;
                        }
                    });
                    alert("Cập nhật thành công!");
                }).catch(error => {
                    console.log("Error OrderDetail: ", error.data.message);
                });
            }).catch(error => {
                alert("Cập nhật thất bại");
                console.log("Error: ", error.message);
            });
        }).catch(error => {
            console.log("Error: ", error.message);
        });
    };

    $scope.cancelOrder = (order) => {
        if (order.orderStatus === 5) {
            alert("Không thể hủy đơn hàng đang giao hàng.");
            return;
        }

        $scope.form.orderStatus = 4; 
        $scope.update(); 
    };

//
});
