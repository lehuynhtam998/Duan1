app.controller('loaiSanPhamCtrl', function ($scope, $http) {
    $scope.form = {};//đọc dữ liệu trên form
    $scope.items = [];//dùng để lưu tạm sau khi load
    //sử dụng để hiển thị trang và tìm kiếm

    //reset
    $scope.reset = () => {
        $scope.form = {};
    }
    //Create
    $scope.create = function () {
        console.log("star")
        var item = angular.copy($scope.form);
        $http.post(`${host}/categories`, item).then(reps => {
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
        $http.put(`${host}/categories/${item.categoryId}`, item).then(reps => {
            var index = $scope.items.findIndex(p => p.categoryId == item.categoryId);
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
            return item.categoryId.toString().includes($scope.searchKeyword) ||
                item.name.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
                item.description.toLowerCase().includes($scope.searchKeyword.toLowerCase());
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
        $http.get(`${host}/categories`).then((resp) => {
            //Lưu danh sách sau khi load
            $scope.items = resp.data;
            //filteredItems sử dụng để tìm kiếm,đổ tất cả lên trang hoặc theo key search
            $scope.filteredItems = $scope.items;
            console.log("Categories: ", $scope.items)
        }).catch((error) => {
            console.log("Error", error);
        });
    };

    $scope.init();

    //Chỉnh Sủa Tab
    $scope.switchTab = function (event, tabId) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ <a>

        // Xóa lớp 'active' từ tất cả các tab
        var tabs = document.querySelectorAll('.nav-link');
        tabs.forEach(function (tab) {
            tab.classList.remove('active');
        });

        // Thêm lớp 'active' vào tab được nhấp
        var clickedTab = document.getElementById(tabId + '-tab');
        clickedTab.classList.add('active');

        // Ẩn tất cả các nội dung tab
        var tabContents = document.querySelectorAll('.tab-pane');
        tabContents.forEach(function (content) {
            content.classList.remove('show', 'active');
        });

        // Hiển thị nội dung của tab được nhấp
        var activeContent = document.getElementById(tabId);
        activeContent.classList.add('show', 'active');
    };
});