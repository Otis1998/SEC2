$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                sessionStorage.setItem('movie',JSON.stringify(data));
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
        if(movie.status==0){//如果电影未下架，显示下架按钮
            $('#delete-btn').css('display','');
        }
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

    // admin界面才有
    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-edit-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-edit-img-input').parent('.form-group').addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-edit-date-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }
    function getMovieForm() {
        return {
            id:movieId,
            name: $('#movie-edit-name-input').val(),
            startDate: $('#movie-edit-date-input').val(),
            posterUrl: $('#movie-edit-img-input').val(),
            description: $('#movie-edit-description-input').val(),
            type: $('#movie-edit-type-input').val(),
            length: $('#movie-edit-length-input').val(),
            country: $('#movie-edit-country-input').val(),
            starring: $('#movie-edit-star-input').val(),
            director: $('#movie-edit-director-input').val(),
            screenWriter: $('#movie-edit-writer-input').val(),
            language: $('#movie-edit-language-input').val()
        };
    }
        
        $("#modify-btn").click(function(){
        //修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）
            var movieInfo=JSON.parse(sessionStorage.getItem('movie'));
            $('#movie-edit-name-input').val(movieInfo.name),
            $('#movie-edit-date-input').val(movieInfo.startDate.slice(0,10)),
            $('#movie-edit-img-input').val(movieInfo.posterUrl),
            $('#movie-edit-description-input').val(movieInfo.description),
            $('#movie-edit-type-input').val(movieInfo.type),
            $('#movie-edit-length-input').val(movieInfo.length),
            $('#movie-edit-country-input').val(movieInfo.country),
            $('#movie-edit-star-input').val(movieInfo.starring),
            $('#movie-edit-director-input').val(movieInfo.director),
            $('#movie-edit-writer-input').val(movieInfo.screenWriter),
            $('#movie-edit-language-input').val(movieInfo.language)
        });
        $("#movie-edit-form-btn").click(function(){
            var formData = getMovieForm();
        if(!validateMovieForm(formData)) {
            return;
        }
        postRequest(
            '/movie/update',
            formData,
            function (res) {
                $("#movieEditModal").modal('hide');
                if(res.success){
                    alert("修改成功！");
                    window.location.reload()
                }else{
                    alert("修改失败！该电影后续仍有排片或已有观众购票且未使用")
                }
            },
             function (error) {
                alert(error);
            });
        });

    $("#delete-btn").click(function () {
        if(confirm("是否确认下架？")){
            //进行下架操作
            movieIdList=[movieId],
            postRequest(
                '/movie/off/batch',
                {movieIdList:movieIdList},
                function(res){
                    if(res.success){
                        alert("下架成功！");
                        $("#delete-btn").hide()
                    }
                    else{
                        alert("下架失败！")
                    }
                }
            );
        }
    });
    

});