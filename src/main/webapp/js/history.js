var HISTORY = {

    initFunctions: function () {
        this.showHistory(1, 1);
        this.initHandlers();
    },

    initHandlers: function () {
        var self = this;

        $(document).on('click', '.pagination li', function (e) {
            if ($(e.target).parent().hasClass('disabled')) return;
            self.showClickedPage(e);
        });
    },

    beforePage: function () {
        var page = $('div.pagination li.active').attr('page');
        return parseInt(page) - 1;
    },

    forwardPage: function () {
        var page = $('div.pagination li.active').attr('page');
        return parseInt(page) + 1;
    },

    showClickedPage: function (e) {

        var targetPage = $(e.target).parent();
        var lastPage = $('.pagination li:nth-last-child(2)').attr('page');
        var page;
        if (targetPage.hasClass('page-before')) {
            page = this.beforePage()
        }
        else if (targetPage.hasClass('page-forward')) {
            page = this.forwardPage();
        }
        else {
            page = targetPage.attr('page');
        }

        $('.pagination li').removeClass('active').removeClass('disabled');

        $('.pagination li[page="' + page + '"]').addClass('active');

        if ($('.pagination li[page="1"]').hasClass('active'))
            $('.page-before').addClass('disabled');
        else if ($('.pagination li[page="' + lastPage + '"]').hasClass('active'))
            $('.page-forward').addClass('disabled');

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

    initPager: function (pages, static) {
        for (var i = 1; i < pages + 1; i++) {
            $('.page-forward').before('<li class="page" page="' + i + '"><a style="cursor:pointer">' + i + '</a></li>');
        }
        $('.pagination li[page="1"]').addClass('active');
        $('.page-before').addClass('disabled');
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
}