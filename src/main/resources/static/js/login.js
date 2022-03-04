$(document).ready(function () {
    $(".login-btn").click(function () {
        if ($("input[name=username]").val().length == 0) {
            $(".form-group p").remove();
            $(".id-field").append("<p class='error-message'>필수 정보입니다.</p>");
            $("input[name=username]").focus()
        } else if ($("input[name=password]").val().length == 0) {
            $(".form-group p").remove();
            $(".password-field").append("<p class='error-message'>필수 정보입니다.</p>");
        } else {
            $(".frm").submit();
        }
    })
});