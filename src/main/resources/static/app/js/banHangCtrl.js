app.controller("banHangCtrl", function ($scope, $http) {
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

    $scope.showInvoice = false;
    $scope.inHoaDon = function () {
        $scope.showInvoice = true;
        setTimeout(function () {
            window.print();
            $scope.showInvoice = false;
            $scope.$apply();
        }, 0);
    };

    $scope.checkQty = (item) => {
        if (item.qty === undefined) {
            $scope.cart.showAlert("Số Lượng hàng tồn không đủ");
        }
    }

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
        console.log("aa",$scope.page);
    };

    $scope.init = () => {
        localStorage.removeItem('cart_orderSellProd');
        //Load Branch
        $http.get(`${host}/branches`).then((resp) => {
            $scope.selectBoxBranchs = resp.data;

        }).catch(error => {
            console.log("Error: ", error.message)
        });
        //end
        var productImagesUrl = `${host}/product-images`;
        var url = `${host}/products`;
        if ($scope.selectedOption) {
            $scope.currentPage = 1;
            $scope.cart.clear();
            url = `${host}/products/${$scope.selectedOption.branchId}/branch`;
            console.log($scope.selectedOption.branchId);
        }
        $http.get(url).then(respProd => {
            $scope.products = respProd.data;
            $http.get(productImagesUrl).then(respProdImg => {
                $scope.productImages = respProdImg.data;

                //Lưu danh sách sau khi load
                $scope.items = $scope.products.map(product => {
                    const images = $scope.productImages
                        .filter(image => image.product.productId === product.productId)
                        .map(image => image.url);
                    return {
                        ...product,
                        images
                    };
                });
                $scope.currentPage = 1;
                $scope.totalItems = $scope.items.length;
                $scope.pageChanged();
            }).catch(error => {
                console.log("ErrorProImg: ", error)
            });
        }).catch(error => {
            console.log("ErrorPro: ", error)
        });

        //Load Nhà cung cấp
        $http.get(`${host}/suppliers`).then((resp) => {
            $scope.selectBoxSuppliers = resp.data;
        })
    }
    $scope.init();



    $scope.cart = {
        items: [],
        //Them san pham 
        add(id) {
            if (!$scope.selectedOption) {
                this.showAlert('Hãy Chọn Chi Nhánh Bán Hàng');
            } else {
                var item = this.items.find(item => item.productId == id);
                if (item) {
                    item.qty++;
                    this.saveToLocalStorage();
                } else {
                    $http.get(`/api/products/${id}`).then(resp => {
                        resp.data.qty = 1;
                        this.items.push(resp.data);
                        this.saveToLocalStorage();
                    })
                }
                this.showAlert('Thêm mới Thành Công');
            }
        },
        showAlert(mess) {
            var alertBox = document.createElement('div');
            alertBox.innerHTML = mess;
            alertBox.style.cssText = 'position:fixed;top:9%;right:40%;width:20%;border-radius: 10px;background-color:rgba(100, 147, 255,1);color:white;text-align:center;padding:20px;z-index:9999;';
            document.body.appendChild(alertBox);

            setTimeout(function () {
                document.body.removeChild(alertBox);
            }, 1500); // Đóng alert sau 1 giây
        },
        remove(id) {
            var index = this.items.findIndex(item => item.productId == id);
            this.items.splice(index, 1);
            this.saveToLocalStorage();
            this.showAlert('Xóa Thành Công');
        },
        clear() {
            this.items = [];
            this.saveToLocalStorage();
        },
        get count() {
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },
        get amount() {
            return this.items
                .map(item => item.qty * item.unitPrice)
                .reduce((total, qty) => total += qty, 0);
        },
        //Luu gio hang vao local storage
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart_orderSellProd", json);
        },
        //Doc gio hang tu local storage
        loadFromLocalStorage() {
            var json = localStorage.getItem("cart_orderSellProd");
            this.items = json ? JSON.parse(json) : [];
        }
    }
    $scope.cart.loadFromLocalStorage();

    $scope.order = {
        orderType: "order",
        seller: $scope.username,
        orderDate: new Date(),
        sales_channel: "offline",
        get totalPrice() {
            return $scope.cart.amount;
        },
        shipAddress: "",
        orderStatus: 3,
        comments: "",
        get branch() {
            return $scope.selectedOption;
        },
        get orderDetails() {
            return $scope.cart.items.map(item => {
                return {
                    product: { productId: item.productId },
                    import_price: item.giaNhap,
                    price: item.unitPrice,
                    quantity: item.qty
                }
            })
        },
        nguoiNhan: "",
        sdtNguoiNhan: "",
        purchase() {
            var order = angular.copy(this);
            console.log("order: ", order)
            $http.post("/api/orders", order).then(resp => {
                var item = resp.data;
                $http.get(`${host}/order-details/OrderId/${item.orderId}`).then(respOrdtail => {
                    var orderDetails = respOrdtail.data;
                    orderDetails.forEach(detail => {
                        var product;
                        $http.get(`${host}/products/${detail.product.productId}`).then(resp => {
                            //Product Trong cửa Hàng
                            product = resp.data;
                            product.quantity = product.quantity - detail.quantity;
                            // Cập Nhật lại product
                            $http.put(`${host}/products/${product.productId}`, product).then(reps => {
                            }).catch(error => {
                                console.log("Error: ", error.message);
                            });
                        }).catch(error => {
                            alert("Cập Nhật Thất bại");
                            console.log("Error: ", error.message);
                        });
                    });
                });

                alert("Thanh Toán thành công");
                $scope.cart.clear();
            }).catch(error => {
                alert("Thanh Toán thất bại");
                console.log(error);
            });
        }
    }
    $scope.isInvalidAmount = function(amount) {
        return isNaN(amount) || amount === '$' || amount === '';
    };
    
});
