app.controller("nhapHangCtrl", function ($scope, $http) {
    $scope.username = document.getElementById('username').textContent;
    $scope.items = [];
    $scope.form = {};

    $scope.currentPage = 1;
    $scope.itemsPerPage = 2;
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
        $scope.items.reverse();
        $scope.page = $scope.items.slice(begin, end);
        
    };

    $scope.init = () => {
        $http.get(`http://localhost:8080/api/orders/nhapHang`).then(resp => {
            $scope.items = resp.data;
            console.log("items:", $scope.items);
            $scope.totalItems = $scope.items.length;
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
    //End
    $scope.update = () => {
        $http.post('/api/order-details/saveAll', $scope.orderDetails).then(resp => {
            var items = resp.data;
            var totalAmount = items.reduce(function (total, orderDetail) {
                return total + (orderDetail.import_price * orderDetail.quantity);
            }, 0);
            var obj = angular.copy($scope.form);
            // var item = angular.copy($scope.form);
            console.log("obj",obj);
            obj.totalPrice = totalAmount;
            obj.orderStatus = 2;
            $http.put(`${host}/orders/updateTotal/${obj.orderId}`, obj).then(reps => {
                console.log("sau Luu: ", reps.data)
                $scope.items.forEach((item) => {
                    if (item.orderId === obj.orderId) {
                      item.totalPrice = totalAmount;
                      item.orderStatus = 2;
                    }
                });
                console.log($scope.items);
            }).catch(error => {
                console.log("Error OrderDetail: ", error.data.message)
            });
            alert("Cập Nhật Thành Công");
        }).catch(error => {
            console.log("ErrorOrder: ", error.data.message);
            alert("Cập Nhật Thất Bại");
        })
    }
});