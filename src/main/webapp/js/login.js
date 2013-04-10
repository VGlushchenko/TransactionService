$(document).ready(function() {

    $('.user-transactions').click(function (e) {
        $.ajax({
            url: 'user/transaction',
            type: 'GET',
            dataType : "json",
            error: function () {
                alert(1)
            },
            success: function (response) {
                var sstr;
                    for (var i = 0; i<response.length; i++)
                        str = response.debit;
                alert(sstr);
            }
        });
    })

});