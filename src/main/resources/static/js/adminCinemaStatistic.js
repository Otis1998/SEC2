$(document).ready(function () {

    var placingRateDate = formatDate(new Date());

    initUser();

    getScheduleRate();

    getBoxOffice();

    getAudiencePrice();

    initDate();

    getPlacingRate();

    getPopularMovie();

    function initUser() {
        if (sessionStorage.getItem("role") === "manager") {
            $("#left-container-text").html("<div class=\"nav-user-container\" style=\"margin-bottom: 50px;\">\n" +
                "            <img class=\"avatar-lg\" src=\"/images/defaultAvatar.jpg\" />\n" +
                "            <p class=\"title\">Admin</p>\n" +
                "        </div>\n" +
                "        <ul class=\"nav nav-pills nav-stacked\">\n" +
                "            <li role=\"presentation\"><a href=\"/admin/movie/manage\"><i class=\"icon-film\"></i> 电影管理</a></li>\n" +
                "            <li role=\"presentation\"><a href=\"/admin/session/manage\"><i class=\"icon-calendar\"></i> 排片管理</a></li>\n" +
                "            <li role=\"presentation\"><a href=\"/admin/promotion/manage\"><i class=\"icon-gift\"></i> 活动管理</a></li>\n" +
                "            <li role=\"presentation\"><a href=\"/admin/cinema/manage\"><i class=\"icon-cogs\"></i> 影院管理</a></li>\n" +
                "            <li role=\"presentation\"  class=\"active\"><a href=\"#\"><i class=\"icon-bar-chart\"></i> 影院统计</a></li>\n" +
                "        </ul>")
        }
    }

    function initDate() {
        $('#place-rate-date-input').val(placingRateDate);

        // 过滤条件变化后重新查询
        $('#place-rate-date-input').change(function () {
            placingRateDate = $('#place-rate-date-input').val();
            getPlacingRate();
        });
    }

    function getScheduleRate() {

        getRequest(
            '/statistics/scheduleRate',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return {
                        value: item.time,
                        name: item.name
                    };
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title: {
                        text: '今日排片率',
                        subtext: new Date().toLocaleDateString(),
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        x: 'center',
                        y: 'bottom',
                        data: nameList
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    series: [
                        {
                            name: new Date().toLocaleDateString(),
                            type: 'pie',
                            radius: [30, 110],
                            center: ['50%', '50%'],
                            roseType: 'area',
                            data: tableData
                        }
                    ]
                };
                var scheduleRateChart = echarts.init($("#schedule-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getBoxOffice() {

        getRequest(
            '/statistics/boxOffice/total',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title: {
                        text: '所有电影票房',
                        subtext: '截止至' + new Date().toLocaleDateString(),
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#box-office-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getAudiencePrice() {
        getRequest(
            '/statistics/audience/price',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.price;
                });
                var nameList = data.map(function (item) {
                    return formatDate(new Date(item.date));
                });
                var option = {
                    title: {
                        text: '每日客单价',
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'line'
                    }]
                };
                var scheduleRateChart = echarts.init($("#audience-price-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getPlacingRate() {
        getRequest(
            '/statistics/PlacingRate?date=' + placingRateDate.replace(/-/g, '/'),
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.placingRate;
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title: {
                        text: '上座率',
                        subtext: placingRateDate.toString(),
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value',
                        axisLabel: {
                            show: true,
                            interval: 'auto',
                            formatter: '{value} %'
                        },
                        show: true
                    },
                    tooltip: {
                        name: new Date().toLocaleDateString(),
                        trigger: 'axis',
                        triggerOn: "mousemove",
                        showContent: true,
                        showDelay: 0,
                        hideDelay: 100,
                        position: ['50%', '50%'],
                        formatter: "{a} <br/>{b} : ({c}%)",
                        backgroundColor: "transparent",
                        borderWidth: 0,
                        padding: 5,
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var placingRateChart = echarts.init($("#place-rate-container")[0]);
                placingRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getPopularMovie() {
        $("#putUp-btn").click(function () {//获得受欢迎电影
                var x = parseInt($("#days-input").val());
                var y = parseInt($("#movieNum-input").val());
                getRequest(
                    '/statistics/popular/movie?days=' + x + '&movieNum=' + y,
                    function (res) {
                        var data = res.content || [];
                        var tableData = data.map(function (item) {
                            return item.recentFare;
                        });
                        var nameList = data.map(function (item) {
                            return item.name;
                        });
                        var option = {
                            title: {
                                text: '热门电影',
                                subtext: '截止至' + new Date().toLocaleDateString(),
                                x: 'center'
                            },
                            xAxis: {
                                type: 'category',
                                data: nameList,
                                axisLabel: {
                                    interval: 0
                                }
                            },
                            yAxis: {
                                type: 'value',
                                axisLabel: {
                                    show: true,
                                    interval: 'auto',
                                },
                                show: true
                            },
                            series: [{
                                data: tableData,
                                type: 'bar'
                            }]
                        };
                        var popularMovieChart = echarts.init($("#popular-movie-table-container")[0]);
                        popularMovieChart.setOption(option)
                    },
                    function (error) {
                        alert(JSON.stringify(error));
                    }
                );
            }
        );
    };
});