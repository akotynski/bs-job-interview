(function () {

    const stooqTemplate = Handlebars.compile($("#stooq-template").html());
    const errorTemplate = Handlebars.compile($("#stooq-error-template").html());

    $.ajax({
        dataType: "json",
        url: "/stooqs",
        success: onNewStooqData
    });

    function onNewStooqData(data) {
        if(data.length === 0) {
            showError();
            return;
        }

        var stooqs = fixStooqsUpdateTime(data);
        const stooqContent = stooqTemplate({
            stooqs: data
        });
        $("#stooq-values").html(stooqContent);
    }

    function fixStooqsUpdateTime(data) {
        return data.map(stooq => stooq.updateTime = new Date(stooq.updateTime).toLocaleString());
    }

    function showError() {
        $("#stooq-values").html(errorTemplate({}));
    }

})();