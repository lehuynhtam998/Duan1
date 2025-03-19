app.controller("navCtrl", function ($scope) {
    $scope.isCollapsed = {
        hanghoa: true,
        giaodich: true,
        doitac: true,
        baocao: true
      };
    
      $scope.toggleCollapse = function(section) {
        $scope.isCollapsed[section] = !$scope.isCollapsed[section];
      };
});