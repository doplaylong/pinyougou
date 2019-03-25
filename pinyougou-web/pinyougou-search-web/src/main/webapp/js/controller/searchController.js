/** 定义搜索控制器 */
app.controller("searchController" ,function ($scope,$sce,$location,$controller,baseService) {

    /* 指定继承baseController */
    $controller("baseController",{$scope:$scope});

    /*定义搜索参数对象*/
    $scope.searchParam = {keywords:'', category:'', brand:'', price:'', spec:{},
        page:1,rows:20,sortField:'',sort:''};
    /*添加搜索选项方法*/
    $scope.addSearchItem = function (key, value) {
        if (key == 'keywords' || key == 'brand' || key == 'price') {
            /*判断是商品分类、品牌、价格*/
            $scope.searchParam[key] = value;
        }else {
            /*规则选项*/
            $scope.searchParam.spec[key] = value;
        }
        /*执行搜索*/
        $scope.search();
    };
    /*删除搜索选项方法*/
    $scope.removeSearchItem = function (key) {
        /*判断是商品分类、品牌、价格*/
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchParam[key] = '';
        }else {
            /*删除规格选项*/
            delete $scope.searchParam.spec[key];
        }
        /*执行搜索*/
        $scope.search();
    };
    /*定义搜索方法*/
    $scope.search = function () {
        baseService.sendPost("/Search",$scope.searchParam).then(function (response) {
            // 获取搜索结果
            $scope.resultMap = response.data;
            // 调用初始化页码方法
            initPageNum();
        });
    };
    /*定义初始化页码方法*/
    var initPageNum = function () {
        // 定义页码数组
        $scope.pageNums = [];
        // 获取总页数
        var totalPages = $scope.resultMap.totalPages;
        // 开始页码
        var firstPage = 1;
        // 结束页码
        var lastPage = totalPages;

        /*前面有点*/
        $scope.firstDot = true;
        /*后面有点*/
        $scope.lastDot = true;

        // 如果总页数大于5，显示部分页码
        if (totalPages > 5) {
            // 如果当前页码处于前面位置
            if ($scope.searchParam.page <= 3) {
                lastPage = 5; // 生成前5页页码
                $scope.firstDot = false;
            }
            // 如果当前页码处于后面位置
            else if($scope.searchParam.page >= totalPages -3){
                firstPage = totalPages -4; // 生成后5页页码
                $scope.lastDot = false;
            }else{ // 当前页码处于中间位置
                firstPage = $scope.searchParam.page - 2;
                lastPage = $scope.searchParam.page + 2;
            }
        }else {
            /*前面没点*/
            $scope.firstDot = false;
            /*后面没点*/
            $scope.lastDot = false;
        }
        // 循环产生页码
        for (var i = firstPage; i <= lastPage; i++){
            $scope.pageNums.push(i);
        }
    };
    /*定义排序搜索方法*/
    $scope.sortSearch = function (sortField, sort) {
        $scope.searchParam.sortField = sortField;
        $scope.searchParam.sort = sort;
        $scope.search();
    };
    /*获取检索关键字*/
    $scope.getKeywords = function () {
        $scope.searchParam.keywords = $location.search().keywords;
        $scope.search();
    };
    /*根据分页搜索方法*/
    $scope.pageSearch = function (page) {
        page = parseInt(page);
        /*页码认证*/
        if (page >= 1 && page <= $scope.resultMap.totalPages && page != $scope.searchParam.page) {
            $scope.searchParam.page = page;
            $scope.search();
        }
    };

    /*将html字符串转化为html标签*/
    $scope.trustHtml = function (htmlstr) {
        return $sce.trustAsHtml(htmlstr);
    };
});
