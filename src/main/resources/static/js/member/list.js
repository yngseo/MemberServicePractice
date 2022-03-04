$(".member-filter li").click(function() {
    if ($(this).attr('data-level') == 'total') {
        location.href="/list"
    } else {
        $.ajax({
            url: "/filter",
            type: "GET",
            data: {levelSeq : $(this).attr('data-level')},
            dataType: "json",
            success: function(data){
                let client = '';
                let state = '';

                $(".table tbody").empty();
                $.each(data, function (index, el) {
                    if (el.levelDto.levelName == '직원') {
                        client = 'CMT정보통신';
                    } else if (el.levelDto.levelName == '고객사') {
                        client = el.name;
                    } else if (el.levelDto.levelName == '고객') {
                        client = el.client;
                    }

                    if (el.approvalDate == null) {
                        state = '미승인'
                    } else {
                        state = '승인'
                    }

                    $(".table tbody").append("<tr><td>"+el.id+"</td><td>"+el.name+"</td><td>"+el.email+"</td><td>"+el.levelDto.levelName+"</td><td>"+client+"</td><td>"+state+"</td></tr>")
                })
            }
        });
    }
});

$("tbody tr").click(function() {
    let id = $(this).children().eq(0).text();
    location.href = "/list/"+id;
});