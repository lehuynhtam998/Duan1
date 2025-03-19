app.controller("hoaDonCtrl", function ($scope, $http) {


    $scope.exportToExcel = function () {
        // Tạo workbook mới
        var workbook = new ExcelJS.Workbook();
        var worksheet = workbook.addWorksheet("Sheet1");
    
        // Thêm tiêu đề cột
        worksheet.columns = [
          { header: 'Order ID', key: 'orderId' },
          { header: 'Order Type', key: 'orderType' },
          { header: 'Order Date', key: 'orderDate' },
          { header: 'Total Price', key: 'totalPrice' },
          { header: 'Ship Address', key: 'shipAddress' },
          { header: 'Order Status', key: 'orderStatus' },
          { header: 'Comments', key: 'comments' },
          { header: 'Customer Name', key: 'customerName' },
          { header: 'Customer Phone', key: 'customerPhone' },
          { header: 'Branch ID', key: 'branchId' }
      ];
        
        console.log($scope.items);
        // Thêm dữ liệu vào worksheet
        $scope.items.forEach((item) => {
          worksheet.addRow(item);
        });
    
        // Xuất file
        workbook.xlsx.writeBuffer().then(function (data) {
          var blob = new Blob([data], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          });
          var url = window.URL.createObjectURL(blob);
          var a = document.createElement("a");
          a.href = url;
          a.download = "export.xlsx";
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
        });
      };

    $scope.username = document.getElementById('username').textContent;
    $scope.items = [];
    $scope.form = {};


    $scope.currentPage = 1;
    $scope.itemsPerPage = 8;
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
        $http.get(`http://localhost:8080/api/orders/hoaDon`).then(resp => {
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

});