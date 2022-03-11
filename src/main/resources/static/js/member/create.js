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
    if ($(".id-input").val().length == 0) { // input이 비었을 때
        $(".id-form").append("<p class='error-message'>필수 정보입니다.</p>");
        $('.id-input').focus();
    } else if ($(".id-input").val().length != 0) { // input에 값이 있을 때
        if (!idReg.test($(".id-input").val())) { // 정규식 표현과 일치하지 않으면 에러 메세지
            $(".id-form").append("<p class='error-message'>영문자로 시작하는 영문자 또는 숫자 6~20자여야 합니다.</p>");
            $('.id-input').focus();
        } else { // 정규식 표현과 일치하면 id check
            checkId();
        }
    }
});

// form submit
$("#submit-btn").click(function () {
    let levelSeq = $(".login-id").attr("data-levelSeq");
    let emailReg = /^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/;

    if(levelSeq == 1 && !$("input[name=levelSeq]").is(":checked")) {
        $(".levelSeq-form p").remove();
        $(".levelSeq-form").append("<p class='error-message'>필수 선택입니다.</p>");
        return false;
    }

    if ($('.id-input').attr("check-result") == "fail" || inputID != $(".id-input").val()) {
        $(".id-form p").remove();
        $(".id-form").append("<p class='error-message'>아이디 중복체크를 해주시기 바랍니다.</p>");
        $('.id-input').focus();
        return false;
    }

    if ($(".name-input").val().length == 0) {
        $(".name-form p").remove();
        $(".name-form").append("<p class='error-message'>필수 입력 정보입니다.</p>");
        $('.name-input').focus();
        return false;
    } else if ($(".name-input").val().length < 2 || $(".name-input").val().length > 10) {
        $(".name-form p").remove();
        $(".name-form").append("<p class='error-message'>2~10자여야 합니다.</p>");
        $('.name-input').focus();
        return false;
    }

    if ($(".email-input").val().length == 0) {
        $(".email-form p").remove();
        $(".email-form").append("<p class='error-message'>필수 입력 정보입니다.</p>");
        $('.email-input').focus();
        return false;
    } else if (!emailReg.test($(".email-input").val())) {
        $(".email-form p").remove();
        $(".email-form").append("<p class='error-message'>올바른 형식의 이메일 주소여야 합니다.</p>");
        $('.email-input').focus();
        return false;
    }

    if ($(".client-field").css("display") == "block" && $('.client-list option:selected').val() == "선택") {
        $(".client-field p").remove();
        $(".client-field").append("<p class='error-message'>소속회사를 선택하여야 합니다.</p>");
        return false;
    }

    $(".frm").submit();
});

$("#cancel-btn").click(function () {
    location.href = document.referrer;
});

// id 중복 체크
let inputID;
function checkId () {
    $.ajax({
        url: "/checkId",
        type: "post",
        data: {id: $(".id-input").val()},
        datatype: "json",
        success: function (data) {
            if (data > 0) {
                $(".id-form").append("<p class='error-message'>이미 존재하는 ID 입니다.</p>");
                $('.id-input').focus();
                $(".id-input").attr("check-result", "fail");
            } else if (data == 0) {
                $(".id-form").append("<p class='success-message'>사용가능한 ID 입니다.</p>");
                $(".id-input").attr("check-result", "success");
                inputID = $(".id-input").val();
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
