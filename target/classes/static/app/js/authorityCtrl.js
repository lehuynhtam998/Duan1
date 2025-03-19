app.controller("authorityCtrl", function ($scope, $http) {
  var url = `${host}/authorities`;

  $http.get(url).then((resp) => {
    $scope.db = resp.data;
    console.log(resp.data);
  });

  $scope.index_of = (username, role) => {
    return $scope.db.authorities.findIndex(
      (a) => a.employee.username === username && a.role.roleId === role
    );
  };

  $scope.update = (username, role) => {
    var index = $scope.index_of(username, role);
    if (index >= 0) {
      var id = $scope.db.authorities[index].id;
      $http.delete(`${host}/authorities/${id}`).then((resp) => {
        $scope.db.authorities.splice(index, 1);
      });
    } else {
      var authority = {
        employee: { username: username },
        role: { roleId: role },
      };
      $http.post(`${host}/authorities`, authority).then((resp) => {
        $scope.db.authorities.push(resp.data);
      });
    }
  };
});
