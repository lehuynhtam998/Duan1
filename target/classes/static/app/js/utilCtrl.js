app.controller("utilCtrl", function () {
    // $scope.exportToExcel = function (items, columns) {
    //     // Tạo workbook mới
    //     var workbook = new ExcelJS.Workbook();
    //     var worksheet = workbook.addWorksheet("Sheet1");
    
    //     // Thêm tiêu đề cột
    //     worksheet.columns = [
    //       { header: 'Order ID', key: 'orderId' },
    //       { header: 'Order Type', key: 'orderType' },
    //       { header: 'Order Date', key: 'orderDate' },
    //       { header: 'Total Price', key: 'totalPrice' },
    //       { header: 'Ship Address', key: 'shipAddress' },
    //       { header: 'Order Status', key: 'orderStatus' },
    //       { header: 'Comments', key: 'comments' },
    //       { header: 'Customer Name', key: 'customerName' },
    //       { header: 'Customer Phone', key: 'customerPhone' },
    //       { header: 'Branch ID', key: 'branchId' }
    //   ];
        
    //     console.log($scope.items);
    //     // Thêm dữ liệu vào worksheet
    //     $scope.items.forEach((item) => {
    //       worksheet.addRow(item);
    //     });
    
    //     // Xuất file
    //     workbook.xlsx.writeBuffer().then(function (data) {
    //       var blob = new Blob([data], {
    //         type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    //       });
    //       var url = window.URL.createObjectURL(blob);
    //       var a = document.createElement("a");
    //       a.href = url;
    //       a.download = "export.xlsx";
    //       document.body.appendChild(a);
    //       a.click();
    //       document.body.removeChild(a);
    //     });
    //   };
});