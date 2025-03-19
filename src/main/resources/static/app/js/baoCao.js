app.controller('ReportController', function ($scope, $http) {
    $scope.labels = [];
    $scope.monthlyRevenueData = [];
    $scope.successfulOrdersData = [];
    $scope.productRevenueData = [];
    $scope.spendingByMonth = [];


    $scope.year = 2024;


    $http.get(`${host}/orders/monthly-revenue?year=${$scope.year}`)
    .then((resp) => {
        console.log("Monthly Revenue Data: ", resp.data); 
        $scope.monthlyRevenueData = resp.data.map(item => ({
            month: item[0],
            year: item[1],
            revenue: item[2]
        }));
        $scope.initMonthlyRevenueChart(); 
    }).catch(error => {
        console.log("Error: ", error.message);
    });

    $http.get(`${host}/orders/successful-orders?year=${$scope.year}`)
    .then((resp) => {
        console.log("Successful Orders Data: ", resp.data); 
        $scope.successfulOrdersData = resp.data.map(item => ({
            month: item[0],
            year: item[1],
            count: item[2]
        }));
        $scope.initSuccessfulOrdersChart(); 
    }).catch(error => {
        console.log("Error: ", error.message);
    });

    $http.get(`${host}/products/total-cost-by-month?year=${$scope.year}`)
    .then((resp) => {
        console.log("Total Price By Month Data: ", resp.data);
        $scope.spendingData = resp.data;
        $scope.initSpendingByMonth(); 
    }).catch(error => {
        console.log("Error: ", error.message);
    });


    $http.get(`${host}/orders/product-revenue?year=${$scope.year}`)
        .then((resp) => {
            const labels = [...new Set(resp.data.map(item => item[2]))]; 
            const dataByMonth = {};
            resp.data.forEach(item => {
                if (!dataByMonth[item[0]]) dataByMonth[item[0]] = {};
                dataByMonth[item[0]][item[2]] = item[3]; 
            });
            $scope.initProductRevenueChart(labels, dataByMonth); 
        }).catch(error => {
            console.log("Error: ", error.message);
        });

    
    $scope.initMonthlyRevenueChart = function () {
        var ctx = document.getElementById('monthlyRevenueChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: $scope.monthlyRevenueData.map(data => 'Tháng ' + data.month), 
                datasets: [{
                    label: 'Doanh thu (VNĐ)',
                    data: $scope.monthlyRevenueData.map(data => data.revenue), 
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    barThickness: 30, 
                    categoryPercentage: 0.8 
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        stacked: false,
                        beginAtZero: true
                    },
                    y: {
                        stacked: false,
                        beginAtZero: true
                    }
                }
            }
        });
    };
    

    $scope.initSuccessfulOrdersChart = function () {
        var ctx = document.getElementById('successfulOrdersChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar', 
            data: {
                labels: $scope.successfulOrdersData.map(data => 'Tháng ' + data.month), 
                datasets: [{
                    label: 'Số lượng đơn hàng thành công',
                    data: $scope.successfulOrdersData.map(data => data.count), 
                    backgroundColor: 'rgba(153, 102, 255, 0.6)', 
                    borderColor: 'rgba(153, 102, 255, 1)', 
                    borderWidth: 1, 
                    barThickness: 30, 
                    categoryPercentage: 0.8 
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        stacked: false,
                        beginAtZero: true
                    },
                    y: {
                        stacked: false,
                        beginAtZero: true
                    }
                }
            }
        });
    };

    $scope.initProductRevenueChart = function (labels, dataByMonth) {
        var ctx = document.getElementById('productRevenueChart').getContext('2d');
        var datasets = [];
        Object.keys(dataByMonth).forEach(month => {
            datasets.push({
                label: 'Doanh thu tháng (VNĐ)' + month,
                data: labels.map(label => dataByMonth[month][label] || 0),
                backgroundColor: 'rgba(255, 159, 64, 0.6)',
                borderColor: 'rgba(255, 159, 64, 1)',
                borderWidth: 1
            });
        });

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: datasets
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    };

    $scope.initSpendingByMonth = function () {
        if (!$scope.spendingData || !$scope.spendingData.length) {
            console.log("No spending data available.");
            return;
        }

        var ctx = document.getElementById('spendingByMonth').getContext('2d');
        
        const labels = $scope.spendingData.map(data => 'Tháng ' + data[0]); 
        const dataValues = $scope.spendingData.map(data => data[1] || 0);

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Chi tiêu theo tháng (VNĐ)',
                    data: dataValues,
                    backgroundColor: 'rgba(255, 99, 132, 0.6)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        beginAtZero: true
                    },
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    };
});
