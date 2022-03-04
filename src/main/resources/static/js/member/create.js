$(document).ready(function () {

    let levelSeq = $(".login-id").attr("data-levelSeq");

    $(".ceo-field").css("display", "none");
    if (levelSeq == 1) {
        $(".client-field").css("display", "none");
        $(".department-field").css("display", "none");
        $(".jobGrade-field").css("display", "none");
    } else if (levelSeq == 3) {
        $(".client-field").css("display", "block");
        $(".department-field").css("display", "block");
        $(".jobGrade-field").css("display", "block");
    }

    // 라디오버튼 클릭시 이벤트 발생
    $("input:radio[name=levelSeq]").click(function () {
        cleanField(); // 익명 함수
        if ($("input[name=levelSeq]:checked").val() == "2") {
            $(".ceo-field").css("display", "none");
            $(".client-field").css("display", "none");
            $(".department-field").css("display", "block");
            $(".jobGrade-field").css("display", "block");
        } else if ($("input[name=levelSeq]:checked").val() == "3") {
            $(".ceo-field").css("display", "block");
            $(".client-field").css("display", "none");
            $(".department-field").css("display", "none");
            $(".jobGrade-field").css("display", "none");
        } else if ($("input[name=levelSeq]:checked").val() == "4") {
            $(".ceo-field").css("display", "none");
            $(".client-field").css("display", "block");
            $(".department-field").css("display", "block");
            $(".jobGrade-field").css("display", "block");
        }
    })
});

//id check
$("#check-btn").click(function () {
    let idReg = /^[a-z]+[a-z0-9]{5,19}$/g;
    $(".id-form p").remove();
    if ($("input[name=id]").val().length == 0) { // input이 비었을 때
        $(".id-form").append("<p class='error-msg' style='color: red'>필수 정보입니다.</p>");
        $('input[name=id]').focus();
    } else if ($("input[name=id]").val().length != 0) { // input에 값이 있을 때
        if (!idReg.test($("input[name=id]").val())) { // 정규식 표현과 일치하지 않으면 에러 메세지
            $(".id-form").append("<p class='error-msg' style='color: red'>영문자로 시작하는 영문자 또는 숫자 6~20자여야 합니다.</p>");
            $('input[name=id]').focus();
        } else { // 정규식 표현과 일치하면 id check
            checkId();
        }
    }
});

// form submit
$("#submit-btn").click(function () {
    if ($('input[name=id]').attr("check-result") == "fail" || inputID != $("input[name=id]").val()) {
        $(".id-form p").remove();
        $(".id-form").append("<p class='error-msg' style='color: red'>아이디 중복체크를 해주시기 바랍니다.</p>");
        $('input[name=id]').focus();
        return false;
    } else if ($(".client-field").css("display") == "block" && $('.client-list option:selected').val() == "선택") {
        $(".client-field p").remove();
        $(".client-field").append("<p class='error-msg' style='color: red'>소속회사를 선택하여야 합니다.</p>");
    } else {
        $(".frm").submit();
    }
});

let inputID;
function checkId () {
    $.ajax({
        url: "/checkId",
        type: "post",
        data: {id: $("input[name=id]").val()},
        datatype: "json",
        success: function (data) {
            if (data > 0) {
                $(".id-form").append("<p class='error-msg' style='color: red'>이미 존재하는 ID 입니다.</p>");
                $('input[name=id]').focus();
                $("input[name=id]").attr("check-result", "fail");
            } else if (data == 0) {
                $(".id-form").append("<p class='success-msg' style='color: green'>사용가능한 ID 입니다.</p>");
                $("input[name=id]").attr("check-result", "success");
                inputID = $("input[name=id]").val();
            }
        }
    })
}

// Radio 변경 시 필드 초기화
function cleanField() {  // 익명 함수
    $("input[name=ceo]").val("");
    $("select[name=client]").val("").prop("selected", true);
    $("input[name=department]").val("");
    $("input[name=jobGrade]").val("");
}
