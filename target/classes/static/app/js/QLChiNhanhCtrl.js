app.controller('QLChiNhanhCtrl', function ($scope, $http) {
    $scope.form = {};//đọc dữ liệu trên form
    $scope.items = [];//dùng để lưu tạm sau khi load
    $scope.filteredItems = [];
    $scope.username = document.getElementById('username').textContent;
    console.log($scope.username);

    //reset
    $scope.reset = () => {
        $scope.form = {};
    }
    //Create
    $scope.create = function () {
        console.log("start");

        $http.get(`${host}/employees/${$scope.username}`).then(reps => {
            $scope.form.employee = reps.data;
            console.log("employee:", reps.data);

            // Các dòng lệnh sau sẽ chỉ được thực thi sau khi lấy được dữ liệu nhân viên
            $scope.form.nguoiTao = reps.data.fullname
            $scope.form.branchType = "Retail";
            $scope.form.branchState = 1;
            console.log("Form: ", $scope.form);
            var item = angular.copy($scope.form);
            console.log("item: ", item);

            $http.post(`${host}/branches`, item).then(reps => {
                $scope.items.push(reps.data);
                console.log("Create:", reps.data);
                alert("Thêm mới thành công");
                $scope.reset();
            }).catch(error => {
                alert("Thêm mới thất bại");
                console.log("Error", error.data.message);
            });
        }).catch(error => {
            alert("Không thể lấy dữ liệu nhân viên");
            console.log("Error", error);
        });
    };
    

    //Update
    $scope.update = function () {
        var item = angular.copy($scope.form);
        console.log(item)
        $http.put(`${host}/branches/${item.branchId}`, item).then(reps => {
            var index = $scope.items.findIndex(p => p.branchId == item.branchId);
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
        console.log("a", $scope.filteredItems)
        console.log("b", $scope.items)
        $scope.filteredItems = $scope.items.filter(function (item) {
            //Các trường của list số + .toString(),chữ + toLowerCase()
            return item.branchId.toString().includes($scope.searchKeyword) ||
                item.branchName.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
                item.managerName.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
                item.address.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
                item.phone.toString().includes($scope.searchKeyword);
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
        $http.get(`${host}/branches`).then((resp) => {
            //Lưu danh sách sau khi load
            $scope.items = resp.data;
            //filteredItems sử dụng để tìm kiếm,đổ tất cả lên trang hoặc theo key search
            $scope.filteredItems = $scope.items;
            console.log("Branch: ", $scope.items)
        }).catch((error) => {
            console.log("Error", error);
        });
        //load categories
        $http.get(`${host}/employees/roleUser`).then((resp) => {
            $scope.selectRoleUser = resp.data;
        })
    };
    $scope.init();

});