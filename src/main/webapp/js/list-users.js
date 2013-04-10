/*
var LIST_USERS = {

    initFunctions: function () {
        this.showHistory(1, 1);
        this.initHandlers();
    },

    initHandlers: function () {
        var self = this;

        $(document).on('click', '.pagination li', function (e) {
            self.showClickedPage(e);
        });
    },

    showClickedPage: function (e) {
        var targetPage = $(e.target);
        var page = targetPage.text();
        $('.pagination li').removeClass('active')
        targetPage.parent().addClass('active');

        this.showHistory(page);
    },

    showHistory: function (page, static) {
        var self = this;

        $('#user-transactions tbody').empty();

        $.ajax({
            url: 'history/json',
            type: 'GET',
            dataType : "json",
            data: {page: page},
            error: function () {
                alert("Error on server")
            },
            success: function (response) {
                self.buildTransactionHistoryTable(response.entity);

                if (static == 1)
                self.initPager(response.credit);
            }
        });

    },

    buildTransactionHistoryTable: function (transactions) {
        for (var i = 0; i < transactions.length; i++)
            this.addTransactionRow(transactions[i]);
    },

    initPager: function (pages) {
        for (var i = 1; i < pages + 1; i++) {
            $('.page-forward').before('<li class="page"><a style="cursor:pointer">' + i + '</a></li>');
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
    }
}*/
