function checkPwd() {
    let pw1 = $('.password').val();
    let pw2 = $('.password-confirm').val();
    if (pw1.length == 0 || pw1 != pw2) {
        $('.check-new-pw').css('color', 'red');
        $('.check-new-pw').html('암호가 일치하지 않습니다.');
        $(".password-confirm").attr("check-result", "fail");
    } else {
        $('.check-new-pw').css('color', 'black');
        $('.check-new-pw').html('암호가 확인 되었습니다.');
        $(".password-confirm").attr("check-result", "success");
    }
};

$(".modify-pw-btn").click(function () {
    /*let pw1 = $('.password').val();
    let pw2 = $('.password-confirm').val();*/
    if ($(".password-confirm").attr("check-result") == "fail") {
        alert('비밀번호가 일치하지 않습니다.');
        $('.password').focus();
    } else {
        $('.frm').submit(); // alert 작업 필요
        /*$.ajax({
            url: "/modifyPassword",
            type: "PUT",
            data: {currentPw : $('.current-password').val(),
                password : $('.password').val()},
            dataType: "json",
            success: function(data){
                if (data == 1) {
                    alert('비밀번호가 변경되었습니다. 로그인 페이지로 이동합니다.');
                    location.href = "/login"
                } else {
                    $('.current-pw-form p').remove();
                    $('.current-pw-form').append("<p class='error-msg' style='color: red'>비밀번호가 일치하지 않습니다.</p>")
                    $('.current-password').focus();
                }
            }
        });*/
    }
});

$(".cancel-btn").click(function () {
    history.back();
});