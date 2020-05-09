// 检查用户名是否可用
$(document).ready(function () {
    $("#username").blur(function () {
        let username = $("#username").val();
        if (username!=null&&username!="") {
            $.post("/isUsernameRegisted", {username: $("#username").val()}, function (responseTxt, statusTxt, xhr) {
                if (statusTxt == "success") {
                    if (responseTxt==true) {
                        alert("用户名已存在");
                    }else {
                        $("#error").html("<p class=\"alert alert-success\" id=\"error\"><Strong>恭喜您,用户名可使用</Strong></p>");
                    }
                } else {
                    alert("网络请求发生错误");
                }
            })
        }
    })
});

// 检查用户输入的密码
$(document).ready(function () {
    $("#password1").blur(function () {
        let password = $("#password").val();
        let password1 = $("#password1").val();
        if (password!=password1&&password!=null&&password!="") {
            $("#error").html("<p class=\"alert alert-warning\" id=\"error\"><Strong>对不起,两次密码不一致,请确认输入</Strong></p>");
        }
    })
});

// 检查用户输入的邮箱
$(document).ready(function () {
    $("#mail").blur(function () {
        let mail = $("#mail").val();
        let regExp = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        if (!regExp.test(mail)) {
            $("#error").html("<p class=\"alert alert-warning\" id=\"error\"><Strong>邮箱格式不合法,请确认输入</Strong></p>");
        }
    })
});

// 检查用户输入的手机号
$(document).ready(function () {
    $("#phone").blur(function () {
        let phone = $("#phone").val();
        if (!(/^1(3|4|5|7|8)\d{9}$/.test(phone))) {
            $("#error").html("<p class=\"alert alert-warning\" id=\"error\"><Strong>手机号不合法,请确认输入</Strong></p>");
            return;
        }
        if (phone!=null&&phone!="") {
            $.post("/isPhoneRegisted", {phone: $("#phone").val()}, function (responseTxt, statusTxt, xhr) {
                if (statusTxt == "success") {
                    if (responseTxt==true) {
                        alert("手机号已被注册");
                    }
                } else {
                    alert("网络请求发生错误");
                }
            })
        }
    })
});

// 检查用户输入的身份证号
$(document).ready(function () {
    $("#identity").blur(function () {
        function varify(id) {
            // true "验证通过", false //校验不通过
            let format = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/;
            //号码规则校验
            if (!format.test(id)) {
                return false
            }
            //区位码校验
            //出生年月日校验   前正则限制起始年份为1900;
            let year = id.substr(6, 4),//身份证年
                month = id.substr(10, 2),//身份证月
                date = id.substr(12, 2),//身份证日
                time = Date.parse(month + '-' + date + '-' + year),//身份证日期时间戳date
                now_time = Date.parse(new Date()),//当前时间戳
                dates = (new Date(year, month, 0)).getDate();//身份证当月天数
            if (time > now_time || date > dates) {
                return false;
            }
            //校验码判断
            let c = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);   //系数
            let b = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');  //校验码对照表
            let id_array = id.split("");
            let sum = 0;
            for (let k = 0; k < 17; k++) {
                sum += parseInt(id_array[k]) * parseInt(c[k]);
            }
            if (id_array[17].toUpperCase() != b[sum % 11].toUpperCase()) {
                return false;
            }
            return true;
        }

        let identity = $("#identity").val();
        let check = varify(identity);
        if (!check) {
            $("#error1").html("<p class=\"alert alert-warning\" id=\"error\"><Strong>对不起,你输入的身份证号格式错误</Strong></p>");
            return;
        }
        if (identity != null && identity != "") {
            $.post("/isIdentityRegisted", {identity: $("#identity").val()}, function (responseTxt, statusTxt, xhr) {
                if (statusTxt == "success") {
                    if (responseTxt == true) {
                        alert("身份证号已被注册");
                    }
                } else {
                    alert("网络请求发生错误");
                }
            })
        }
    })
});

// 检查用户输入的身份证号起始日期和结束日期
$(document).ready(function () {
    $("#end-date").blur(function () {

        let start_val = $("#start-date").val();
        let end_val = $("#end-date").val();

        let start_year = Number(start_val.substring(0,4));
        let end_year = Number(end_val.substring(0,4));

        let validate = end_year-start_year;

        if ((validate==5 || validate==10)&&start_val.substring(6)==end_val.substring(6)) {
            $("#error1").html("<p class=\"alert alert-success\" id=\"error\"><Strong>恭喜您,身份证日期符合规范</Strong></p>");
        }else {
            $("#error1").html("<p class=\"alert alert-warning\" id=\"error\"><Strong>对不起,请检查您的身份证日期</Strong></p>");
        }
    })
});

// 鼠标点击注册按钮
$(document).ready(function () {
    $("#btn-register").click(function () {
        let username = $("#username").val();
        let password = $("#password").val();
        let password1 = $("#password1").val();
        let name = $("#name").val();
        let identity = $("#identity").val();
        let phone = $("#phone").val();
        let mail = $("#mail").val();
        let start_date = $("#start-date").val();
        let end_date = $("#end-date").val();
        if (username!=null&&username!=""&&password1!=null&&password1!=""&&password==password1
            &&name!=null&&name!=""&&identity!=null&&identity!=""&&phone!=null&&phone!=""&&mail!=null&&mail!="") {
            $.post("/signup",
                {username: username, password:password1,name:name,identity:identity,phone:phone,mail:mail,start_date:start_date,end_date:end_date},
                function (response) {
                 if (response.code == 200){
                     alert("恭喜您, 注册成功");
                     window.location.href="/login";
                 } else{
                     alert(response.msg);
                 }
            })
        }
    })
});