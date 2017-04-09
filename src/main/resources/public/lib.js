(function () {

    const stooqTemplate = Handlebars.compile($("#stooq-template").html());

    $.ajax({
        dataType: "json",
        url: "/stooqs",
        success: onNewStooqData
    });

    function onNewStooqData(data) {
        var stooqs = fixStooqsUpdateTime(data);
        const stooqContent = stooqTemplate({
            stooqs: data
        });
        $("#stooq-values").html(stooqContent);
    }

    function fixStooqsUpdateTime(data) {
        return data.map(stooq => stooq.updateTime = new Date(stooq.updateTime).toLocaleString());
    }

})();