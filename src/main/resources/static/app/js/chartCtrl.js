app.controller('ChartController', function ($scope, $http) {
    $scope.labels = [];
    $scope.data = [];
    $scope.orderChuaXL = 0;

    // Lấy số đơn chưa xử lý
    $http.get(`${host}/orders/orderChuaXL`)
        .then((resp) => {
            $scope.orderChuaXL = resp.data;
        }).catch(error => {
            console.log("Error: ", error.message);
        });

    // Lấy doanh thu theo danh mục
    $http.get(`http://localhost:8080/api/categories/revenue`)
        .then((resp) => {
            var responseData = resp.data;
            responseData.forEach(function (item) {
                $scope.labels.push(item.categoryName);  
                $scope.data.push(item.totalRevenue);    
            });
            $scope.initChart(); 
        }).catch(error => {
            console.log("Error: ", error.message);
        });

    // Hàm khởi tạo biểu đồ
    $scope.initChart = function () {
        var ctx = document.getElementById('myChart').getContext('2d');
        new Chart(ctx, {
            type: 'pie', 
            data: {
                labels: $scope.labels,
                datasets: [{
                    label: "Revenue",
                    data: $scope.data,
                    backgroundColor: [
                        'rgba(100, 147, 255, 0.6)',
                        'rgba(255, 99, 132, 0.6)',
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(153, 102, 255, 0.6)',
                        'rgba(255, 159, 64, 0.6)'
                    ],
                    borderColor: [
                        'rgba(100, 147, 255, 1)',
                        'rgba(255, 99, 132, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: 'Category Revenue'
                    }
                }
            }
        });
    };
});
