var TRANSACTIONS = {

    initFunctions: function () {
        this.initHandlers();
    },

    initHandlers: function () {
        var self = this;

        $('.history').click(function (e) {
            self.showHistory(e);
        });
        $('.create-transaction').click(function (e) {
            self.showSendForm(e);
        });

        /*$('.page').click(function (e) {
            self.showClickedPage(e);
        });*/
    },

    showClickedPage: function (e) {
        var targetPage = $(e.target);
        var numberOfPage = targetPage.text();

        targetPage.addClass('active');

        this.showHistory(numberOfPage);
    },

    showTransactionTable: function () {
        $('#send-form').hide();
        $('#user-transactions').show();
    },

    showHistory: function (page, e) {
        var self = this;

        $('#user-transactions tbody').empty();

        $.ajax({
            url: 'history/json',
            type: 'GET',
            dataType : "json",
            data: {page: 1},
            error: function () {
                alert("Error on server")
            },
            success: function (response) {
                self.showTransactionTable();
                for (var i = 0; i<response.entity.length; i++)
                    self.addTransactionRow(response.entity[i]);
                    self.initPager(response.credit);
            }
        });

    },

    initPager: function (pages) {
        for (var i = 0; i < pages; i++) {
            $('.page-forward').before('<li class="page"><a>' + i + '</a></li>');
        }
    },

    addTransactionRow: function (tr) {
        var transactionRow = this.generateTransactionRow(tr);
        $('#user-transactions tbody').append(transactionRow)
    },

    generateTransactionRow: function (tr) {
        var transactionRow = "<tr class='transaction'>" +
            "<td class='debit-user'>" + tr.debit + "</td>" +
            "<td class='credit-user'>" + tr.credit + "</td>" +
            "<td class='status'>" + tr.status + "</td>" +
            "<td class='sum'>" + tr.sum + "</td>" +
            "</tr>"

        return transactionRow;
    },

    showSendForm: function () {
        $('#user-transactions').hide();
        $('li').removeClass('active');
        $('.create-transaction').addClass('active')
        $('#send-form').show();
    }

}