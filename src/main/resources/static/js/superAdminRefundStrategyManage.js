
$(document).ready(function () {
    getStrategyList();

    function getStrategyList() {
        getRequest(
            '/refund-strategy/all',
            function (res) {
                renderStrategies(res.content)
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderStrategies(strategies) {
        $(".content-refund").empty();
        var strategiesDomStr = "";

        strategies.forEach(function (strategy) {
            var state = "<span class='gray-text strategy-line-item'>  未生效 </span>";
            if (strategy.state === 1) {
                state = "<span class='primary-text strategy-line-item'>  已生效 </span>";
            }
            var refundable = "<span class='error-text strategy-line-item'> 不可退票 </span>";
            if (strategy.refundable === 1) {
                refundable = "<span class='primary-text strategy-line-item'> 可退票 </span>";
            }

            strategiesDomStr+=
                "<div class='strategy-container'>" +
                "    <div class='strategy-card card'>" +
                "       <div class='strategy-line'>" +
                "           <span class='title'>"+strategy.name+"</span>" +  refundable+ " | " + state +
                "       </div>" +
                "       <div class='strategy-line'>" +
                "           <span>手续费：" +strategy.charge+ " 元</span>" +
                "           <button type=\"button\" class=\"btn btn-danger strategy-enable-btn\" style='margin-left: auto' data-strategy-id='"+strategy.id+"'>" +
                "           <i class='icon-ok'></i> 生效</button>" +
                "           <button type=\"button\" class=\"btn btn-primary strategy-edit-btn\" data-strategy='"+JSON.stringify(strategy)+"' " +
                "               style='float:right' data-backdrop=\"static\" data-toggle=\"modal\"> " +
                "               <i class=\"icon-edit-sign\"></i> 修改</button>" +
                "       </div>" +
                "    </div>" +
                "</div>";
        });
        $(".content-refund").append(strategiesDomStr);
    }

    $("#refund-edit-form-btn").click(function () {
        var refundable = 0;
        if ($("#refundable-edit-select").find("option:selected").text() === "是") {
            refundable = 1;
        }
        let strategyForm = {"id": Number($('#refundEditModal')[0].dataset.id), "name": $("#refund-name-edit-input").val(), "refundable": refundable, "availableHour":0, "charge": $("#refund-charge-edit-input").val(), "state":0};

        postRequest(
            '/refund-strategy/update',
            strategyForm,
            function (res) {
                if (res.success) {
                    getStrategyList();
                    $("#refundEditModal").modal("hide");
                } else {
                    alert(res.message)
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#refund-form-btn").click(function () {
        var refundable = 0;
        if ($("#refundable-select").find("option:selected").text() === "是") {
            refundable = 1;
        }
        let strategyForm = {"name": $("#refund-name-input").val(), "refundable": refundable, "availableHour":0, "charge": $("#refund-charge-input").val(), "state":0};

        postRequest(
            '/refund-strategy/add',
            strategyForm,
            function (res) {
                if (res.success) {
                    getStrategyList();
                    $("#refundModal").modal("hide");
                } else {
                    alert(res.message)
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $(document).on('click','.strategy-edit-btn',function (e) {
        var strategy = JSON.parse(e.target.dataset.strategy);
        var charge = strategy.charge;
        if (strategy.refundable === 1) {
            $("#refundable-edit-select").get(0).selectedIndex = 0;
        } else {
            $("#refundable-edit-select").get(0).selectedIndex = 1;
        }

        $("#refund-name-edit-input").val(strategy.name);
        $("#refund-charge-edit-input").val(strategy.charge);
        $('#refundEditModal').modal('show');
        $('#refundEditModal')[0].dataset.id = strategy.id;
        console.log(strategy);
    });

    $(document).on('click','.strategy-enable-btn',function (e) {
        var id = e.target.dataset.strategyId;
        getRequest(
            '/refund-strategy/enable/' + id,
            function (res) {
                getStrategyList();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#refund-form-remove-btn").click(function () {
        var r=confirm("确认要删除该退票策略吗");
        if (r) {
            deleteRequest(
                '/refund-strategy/delete/' + $('#refundEditModal')[0].dataset.id,
                {},
                function (res) {
                    if(res.success){
                        getStrategyList();
                        $("#refundEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            )
        }
    });
});