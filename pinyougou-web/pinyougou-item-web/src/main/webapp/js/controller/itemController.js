app.controller("itemController",function ($scope,$controller,$http) {

    /* 指定继承baseController */
    $controller("baseController",{$scope:$scope});

    /* 定义商品数据加减的方法 */
    $scope.addNum = function (x) {
        $scope.num = parseInt($scope.num);
        $scope.num += x;
        if ($scope.num < 1) {
            $scope.num = 1;
        }
    };

    /*记录用户选择的规格选项*/
    $scope.specItems = {};
    /*定义用户选择规格选项的方法*/
    $scope.selectSpec = function (specName,optionName) {
        // 赋值
        $scope.specItems[specName] = optionName;
        /*查找对应的sku商品*/
        searchSku();
    };

    /*判断某个规格选项是否被选中*/
    $scope.isSelected = function (specName,optionName) {
        // 取值
        return $scope.specItems[specName] == optionName;
    };

    /* 加载默认的sku */
    $scope.loadSku = function () {
        /*取第一个sku*/
        $scope.sku = itemList[0];
        /* 获取sku商品选择的选项规格 */
        $scope.specItems = JSON.parse($scope.sku.spec);
    };

    /*根据用户选中的规格选项，查找对应的sku商品*/
    var searchSku = function () {
        for(var i = 0; i < itemList.length; i++){
            /*判断规格选项是不是当前用户选中的*/
            if(itemList[i].spec == JSON.stringify($scope.specItems)){
                $scope.sku = itemList[i];
                return;
            }
        }
    };

    /* 添加sku商品到购物车 */
    $scope.addToCart = function () {
        $http.get("http://cart.pinyougou.com/cart/addCart?itemId=" + $scope.sku.id +
            "&num=" + $scope.num,{withCredentials:true}).then(function (response) {
            if (response.data) {
                // 跳转到购物车页面
                location.href = "http://cart.pinyougou.com/cart.html";
            }else {
                alert("请求失败！");
            }
        });
    };
});