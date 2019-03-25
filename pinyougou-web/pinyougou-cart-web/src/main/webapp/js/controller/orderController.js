/* 订单控制器 */
app.controller("orderController", function ($scope, $controller, baseService) {
    /* 指定继承cartController */
    $controller("cartController", {$scope: $scope});

    /* 根据用户名获取地址列表*/
    $scope.findAddressByUser = function () {
        baseService.sendGet("/order/findAddressByUser").then(function (response) {
            $scope.addressList = response.data;
            /*循环用户地址集合*/
            for (var i in response.data) {
                if (response.data[i].isDefault == 1) {
                    /*设置默认地址*/
                    $scope.address = response.data[i];
                    break;
                }
            }
        });
    };

    /* 选择地址 */
    $scope.selectAddress = function (item) {
        $scope.address = item;
    };

    /* 判断是否是当前选中的地址 */
    $scope.isSelectdAddress = function (item) {
        return $scope.address == item;
    };

    /* 定义order对象封装参数 */
    $scope.order = {paymentType: '1'};
    /* 选择支付方式 */
    $scope.selectPayType = function (type) {
        $scope.order.paymentType = type;
    };

    // 保存订单
    $scope.saveOrder = function () {
        $scope.order.receiver = $scope.address.contact;
        $scope.order.receiverAreaName = $scope.address.address;
        $scope.order.receiverMobile = $scope.address.mobile;
        baseService.sendPost("/order/save", $scope.order).then(function (response) {
            if (response.data) {
                if ($scope.order.paymentType == 1) {
                    location.href = "/order/pay.html";
                } else {
                    location.href = "/order/paysuccess.html";
                }
            } else {
                alert("提交订单失败！");
            }
        });
    };


});