/** 定义控制器层 */
app.controller('userController', function($scope, $timeout, baseService){

    // 定义用户对象user
    $scope.user = {};
    // 定义保存用户的方法
    $scope.save = function () {
        if ($scope.password && $scope.user.password == $scope.password) {
            baseService.sendPost("/user/save?code=" + $scope.code,$scope.user).then(function (response) {
                if(response.data){
                    alert("注册成功!");
                    // 跳转到登录页面
                    // 清空表单数据
                    $scope.user = {};
                    $scope.password = "";
                    $scope.code = "";
                }else {
                    alert("注册失败！");
                }
            });
        }else {
            alert("密码不一致，请重新输入~")
        }
    };

    // 定义标签文本
    $scope.tipMsg = "获取短信验证码";
    $scope.flag = false;

    // 获取短信验证码
    $scope.sendSmsCode = function () {
        if($scope.user.phone && /^1[3|4|5|7|8|9]\d{9}$/.test($scope.user.phone)){
            baseService.sendGet("/user/sendSmsCode?phone=" + $scope.user.phone).then(function (response) {
                if (response.data) {
                    // 禁用tipMsg
                    $scope.flag = true;
                    // 调用倒计时方法
                    $scope.downcount(90);
                }else {
                    alert("获取短信验证码失败！");
                }
            });
        }else {
            alert("手机号不能为空或者手机号码不正确");
        }
    };

    // 倒计时方法
    $scope.downcount = function (seconds) {
        if (seconds > 0) {
            seconds--;
            $scope.tipMsg = seconds + "秒后重新获取";
            // 开启定时器
            $timeout(function () {
                $scope.downcount(seconds);
            },1000);
        }else {
            $scope.tipMsg = "获取短信验证码";
            $scope.flag = false;
        }
    };
});