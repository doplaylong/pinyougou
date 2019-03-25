/** 定义首页控制器层 */
app.controller("indexController", function($scope, $controller, baseService){

    /* 指定继承baseController*/
    $controller("baseController",{$scope:$scope});

    /** 根据广告分类 id 查询广告内容 */
    $scope.findContentByCategoryId = function (categoryId) {
        baseService.sendGet("/findContentByCategoryId?categoryId=" + categoryId)
            .then(function (response) {
                $scope.contentList = response.data;
            });
    };

    /*跳转到搜索系统*/
    $scope.search = function () {
        var keyword = $scope.keywords ? $scope.keywords : "";
        location.href="http://search.pinyougou.com?keywords=" + keyword;
    };

});