app.controller("EmailController", function ($scope, $http, $timeout) {
  $scope.items = [];
    $scope.item = {};
    $scope.totalItems = 0;
    $scope.pages = [];
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
  
    $scope.load_all = () => {
        $http.get(`${host}/emails`)
            .then((resp) => {
                $scope.items = resp.data;
                $scope.totalItems = $scope.items.length;
                $scope.items.reverse();
                $scope.pageChanged();
            })
            .catch((error) => {
                console.log("Error", error);
            });
    };
    $scope.showDetails = function (row) {
        $scope.item = angular.copy(row);
        $scope.item.isRead = true;
        if ($scope.selectedRow === row) {
            $scope.selectedRow = null;
        } else {
            $scope.selectedRow = row;    
        }
        $http.put(`${host}/emails/${row.id}`, $scope.item)
            .then((resp) => {
                $timeout(() => {
                    $scope.items.forEach((item) => {
                        if (item.id === row.id) {
                            item.isRead = true;
                          
                        }
                    });
                   
                });
            })
            .catch((error) => {
                console.log(error);
            });
    };
    $scope.setPage = (pageNo) => {
        $scope.currentPage = pageNo;
        $scope.pageChanged();
        $scope.message = "";
    };
    $scope.pageChanged = () => {
        var begin = ($scope.currentPage - 1) * $scope.itemsPerPage;
        var end = begin + $scope.itemsPerPage;
        $scope.pages = $scope.items.slice(begin, end);
    };
    $scope.checkForNewEmails = function () {
        $http.get(`${host}/emails/unread`)
            .then((response) => {
                $scope.hasNewEmail = response.data;
            })
            .catch((error) => {
                console.error("Lỗi khi kiểm tra email mới:", error);
            });
    };
  
    $scope.searchText = "";
    $scope.search = (text) => {
        $scope.message = "";
        var foundItems = $scope.items.filter((item) => {
            return item.sender.toLowerCase().includes(text.toLowerCase());
        });
  
        if (foundItems.length === 0) {
            $scope.pages = [];
            $scope.message = "No items found";
        } else {
            $scope.pages = foundItems;
        }
    };
  
    $scope.reloadPage = function () {
        window.location.reload();
    };
  
    $scope.load_all();
    $scope.checkForNewEmails();
});
