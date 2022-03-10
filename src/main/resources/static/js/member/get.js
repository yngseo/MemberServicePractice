$(".approval-btn").click(function () {
    if (confirm("해당 계정 사용을 승인 하시겠습니까?") == true) {
        $.ajax({
            url: "/approval",
            type: "PUT",
            data: {id: $(".id").text()},
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (data == 1) {
                    alert("승인 되었습니다.");
                    location.href="/list";
                } else {
                    alert("ERROR");
                    return false;
                }
            }
        });
    } else {
        return false;
    }
});

$(".password-btn").click(function () {
    if (confirm("해당 계정 비밀번호를 초기화 하시겠습니까?") == true) {
        $.ajax({
            url: "/initialize",
            type: "PUT",
            data: {id: $(".id").text()},
            dataType: "json",
            success: function (data) {
                if (data == 1) {
                    alert("초기화 되었습니다.");
                    location.href="/list";
                } else {
                    alert("ERROR");
                    return false;
                }
            }
        });
    } else {
        return false;
    }
});

$(".list-btn").click(function () {
    location.href = document.referrer;
    /*location.href = "/list" + criteria;*/ // 필터
});