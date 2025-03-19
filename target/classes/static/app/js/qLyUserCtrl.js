app.controller('qLyUserCtrl', function ($scope, $http) {
    $scope.form = {};//đọc dữ liệu trên form
    $scope.items = [];//dùng để lưu tạm sau khi load
    $scope.filteredItems = [];

    //reset
    $scope.reset = () => {
        $scope.form = {};
    }
    //Create
    $scope.create = function () {
        $scope.form.registrationDate = new Date();
        var item = angular.copy($scope.form);
        $http.post(`${host}/employees`, item).then(reps => {
            $scope.items.push(reps.data);
            console.log("Create:", reps.data);
            alert("Them moi thanh cong");
            $scope.reset();
        }).catch(error => {
            alert("Them moi that bai");
            console.log("Error", error);
        })
    }

    //Update
    $scope.update = function () {
        var item = angular.copy($scope.form);
        console.log(item)
        $http.put(`${host}/employees/${item.username}`, item).then(reps => {
            var index = $scope.items.findIndex(p => p.username == item.username);
            $scope.items[index] = item;
            alert("Cap nhat thanh cong");
            console.log("Update : ", reps.data)
        }).catch(error => {
            alert("Cap nhat that bai");
            console.log("Error", error);
        });
    }

    //Hiển thị table
    $scope.selectedRow = null;
    $scope.showDetails = function (row) {
        console.log(row)
        $scope.form = angular.copy(row);
        if ($scope.selectedRow === row) {
            $scope.selectedRow = null;
        } else {
            $scope.selectedRow = row;
        }
    };
    //End
    //Hiển Thị page, phân trang , Tìm kiếm 
    $scope.searchKeyword = '';

    $scope.filterItems = function () {
        console.log("a",$scope.filteredItems)
        console.log("b",$scope.items)
        $scope.filteredItems = $scope.items.filter(function (item) {
            //Các trường của list số + .toString(),chữ + toLowerCase()
            return item.username.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
                item.fullname.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
                item.email.toLowerCase().includes($scope.searchKeyword.toLowerCase());
        });
        $scope.pager.first();
    };

    $scope.pager = {
        page: 0,
        size: 4,
        // dùng để duyệt mảng và đổ lên client
        get items() {
            var start = this.page * this.size;
            return $scope.filteredItems.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.filteredItems.length / this.size);
        },
        first() {
            this.page = 0;
        },
        pre() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        }
    };
    //End

    //Khởi Tạo
    $scope.init = () => {
        //load categories
        $http.get(`${host}/employees`).then((resp) => {
            //Lưu danh sách sau khi load
            $scope.items = resp.data;
            //filteredItems sử dụng để tìm kiếm,đổ tất cả lên trang hoặc theo key search
            $scope.filteredItems = $scope.items;
            console.log("employees: ", $scope.items)
        }).catch((error) => {
            console.log("Error", error);
        });
    };

    $scope.init();
});