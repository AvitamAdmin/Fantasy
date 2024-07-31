(function($) {
    $(document).ready(function() {

        let loc = window.location;
        let pathName = loc.pathname.substring(loc.pathname.lastIndexOf('/') + 1);
        if (!pathName)
            window.location.href = '/home';
        $('#myNavbar ul li, #myNavbar ul li a').removeClass('active');
        $('.' + pathName + ',.' + pathName + ' a').addClass('active');

        $('.side-menu ul li').removeClass('active');
        $('.side-menu .' + pathName).addClass('active');

        $('.navbar-nav li a').on('click', function(e) {
            $('header .navbar-nav li, header .navbar-nav li a, .side-menu li').removeClass('active');
            $(this).parent('li').addClass('active');
            $(this).addClass('active');
        });

        $('.side-menu li a').on('click', function(e) {
            $('header .navbar-nav li, header .navbar-nav li a, .side-menu li').removeClass('active');
            $(this).parent('li').addClass('active');
        });

        $('.pager-search input[type=text]').focusin(
            function() {
                $('.pager-search svg').hide();
            }).focusout(
            function() {
                $('.pager-search svg').show();
            });

    });

    $(window).on('load', function() {
        $('#tableData').wrap('<div class="table-data-wrapper"></div>');
    });

})(jQuery);

function handleOperationForm(elm) {
    $('#checkType').val(elm.value);
    let action = "/toolkit/handleOperation".concat(elm.value.replace(/\s+/g, ''));
    $("#operationForm").attr("action", action);
    ajaxformSubmit("operationForm");
}

function ajaxformSubmit(id) {
    $('#actionMessage').hide();
    var frm = $("#" + id);
    var url = frm.attr('action');
    frm.submit(function(e) {
        $("body").addClass("loading");
        e.preventDefault();
        e.stopImmediatePropagation();
        var formData = new FormData(this);
        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function(data) {
                $("body").removeClass("loading");
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else {
                    // data.form contains the HTML for the replacement form
                    $('#appContent').html(data);
                    $("#navBreadcrumb").text(url.replaceAll('/', " > "));
                    $('.modal-backdrop').remove();
                    if (url.includes('edit')) {
                        $('#actionMessage').text('Data Modified/Added Successfully');
                        $('#actionMessage').show();
                    }
                    else if (url.includes('delete')) {
                        $('#actionMessage').text('Data Deleted Successfully');
                        $('#actionMessage').show();
                    }
                    else if (url.includes('upload')){
                        $('#actionMessage').text('Data Uploaded Successfully');
                        $('#actionMessage').show();
                    }
                }
            },
            error: function(data) {
                $("body").removeClass("loading");
                console.log(JSON.stringify(data));
            },
        });
    });
}

function submitOperationForm(url) {
    $('#actionMessage').hide();
    $("body").addClass("loading");
    $.ajax({
        type: "POST",
        url: url,
        timeout: 600000,
        success: function(data) {
            $("body").removeClass("loading");
            if (data.redirect) {
                // data.redirect contains the string URL to redirect to
                window.location.href = data.redirect;
            } else {
                // data.form contains the HTML for the replacement form
                $('#appContent').html(data);
            }
        },
        error: function(e) {
            $("body").removeClass("loading");
            $('#appContent').html(e);
        }
    });
}

function submitFormById(id) {
    $('#actionMessage').hide();
    $("body").addClass("loading");
    var form = $(id);
    //form.preventDefault(); // prevent actual form submit
    var url = form.attr('action'); //get submit url [replace url here if desired]
    $.ajax({
        type: "POST",
        url: url,
        data: form.serialize(), // serializes form input
        timeout: 600000,
        success: function(data) {
            $("body").removeClass("loading");
            if (data.redirect) {
                // data.redirect contains the string URL to redirect to
                window.location.href = data.redirect;
            } else {
                // data.form contains the HTML for the replacement form
                $('#appContent').html(data);
                $("#navBreadcrumb").text(url.replaceAll('/', " > "));
                $('.modal-backdrop').remove();
                if (url.includes('edit')) {
                    $('#actionMessage').text('Data Modified/Added Successfully');
                    $('#actionMessage').show()
                }
                else if (url.includes('delete')) {
                    $('#actionMessage').text('Data Deleted Successfully');
                    $('#actionMessage').show();
                }
                else if (url.includes('upload')){
                    $('#actionMessage').text('Data Uploaded Successfully');
                    $('#actionMessage').show();
                }
            }
        },
        error: function(e) {
            $("body").removeClass("loading");
            $('#appContent').html(e);
        }
    });
}

function fire_ajax_submit(url, ignoreBreadCrumb) {
    $('#actionMessage').hide();
    $("body").addClass("loading");
    $.ajax({
        type: "GET",
        url: url,
        timeout: 600000,
        success: function(data) {
            $("body").removeClass("loading");
            if (url.includes('toolkit')) {
                $('#sideMenu').hide();
                $('#toolkitSideMenu').show();
            } else {
                $('#sideMenu').show();
                $('#toolkitSideMenu').hide();
            }
            $('#appContent').html(data);
            $('.modal-backdrop').remove();
            if (url.includes('delete')) {
                $('#actionMessage').text('Data Deleted Successfully');
                $('#actionMessage').show();
            }
            if (url.includes('edits')) {
                $("#navBreadcrumb").text(" > Finders > Find Resolution");
            }
            else if (ignoreBreadCrumb != 'true') {
                $("#navBreadcrumb").text(url.replaceAll('/', " > "));
            }
            else {
                $("#navBreadcrumb").text(" > admin > scheduler");
            }
            if ($('#action_error').length) {
                console.log($("#action_error").text());
            }
            $('#tableData').wrap('<div class="table-data-wrapper"></div>');
        },
        error: function(e) {
            $("body").removeClass("loading");
            $('#appContent').html(e);
        }
    });
}

function gerParamsForDataSource(url, index) {
    $("body").addClass("loading");
    $.ajax({
        type: "GET",
        url: url + event.target.value,
        timeout: 600000,
        success: function(data) {
            $("body").removeClass("loading");
            var x = Number(index.substring(index.indexOf('[') + 1, index.indexOf(']')));
            $('#select_param_' + x).html(data);
        },
        error: function(e) {
            $("body").removeClass("loading");
            $('#select_param_').html(e);
        }
    });
}

function getMatchId(id) {
    var matchId = $(id).val();
    //alert(matchId);

    $.ajax({
        type: "GET",
        url: "/admin/userTeams/getPlayersByMatchId/" + matchId,
        timeout: 600000,
        dataType: 'json',
        success: function(data) {
        //alert(data);
        $('#players').empty();
        $.each(data, function(i, v) {

           $.each(v, function(index, value){
            console.log(value);
            $('#players').append($('<option>', { value : v[index].id}).text(v[index].name));
            });
        });
        },
        error: function(e) {
            alert(e);
        }

    });
}

function getMatchId(id) {
    var matchId = $(id).val();
    var playerIds = [];
    var playersName = [];

    $.ajax({
        type: "GET",
        url: "/admin/userTeams/getPlayersByMatchId/" + matchId,
        timeout: 600000,
        dataType: 'json',
        success: function(data) {
        //alert(data);
        console.log(data);
        $('#players').empty();

        $.each(data, function(i, v) {
            if(i == 1){
            $.each(v, function(index, value){
                playerIds[index] = value;
                console.log(playerIds[index]);
            });
            }
        });
        $.each(data, function(i, v) {
            if(i == 2){
            $.each(v, function(index, value){
                playersName[index] = value;
                console.log(playersName[index]);
            });
            }
        });
        //$('#players').append($('<option>', { value : v[index].id}).text(v[index].playerName));
        for(var i = 0; i<playerIds.length; i++){
        $('#players').append($('<option></option>').val(playerIds[i]).html(playersName[i]));
        }
        },
        error: function(e) {
            alert(e);
        }

    });
}

var count=0;
var kg=0.0;
var totalPrice=0.0;
var quantity=0.0;
var particular="";
var grossPrice = 0.0;
var amountReceived=0.0;
var pendingAmount=0.0;
var sellingPrice=0.0;
var adjustableRate=0.0;
var adjustableTotalPrice=0.0;


function getVegetablePrice(counts) {

    //var indexCount = $(".cheil-select2").length-1;
    //console.log(indexCount);
    //count = indexCount;
    count=counts;
    //++count;
    console.log(count);
    particular = $('#particulars'+count+'').val();
    console.log(particular);
    $.ajax({
        type: "GET",
        url: "/admin/billofsupply/getVegetableById/" + particular,
        timeout: 600000,
        dataType: 'json',
        success: function(data) {
        alert(data);
        console.log(data);
        sellingPrice = data;
        //$('#rate'+count+'').empty();
        $('#rate'+count+'').val(sellingPrice);
        $('#quantity'+count+'').val(1);
        //$('#rate'+count+'').append($('<option></option>').val(data).html(data));

        },
        error: function(e) {
            alert(e);
        }

    });
}


function getParticularKgTotalPrice(counts) {
    count=counts;
    console.log(count);
    kg = $('#kg'+count+'').val();
    console.log(kg);
    particular = $('#particulars'+count+'').val();
    sellingPrice = $('#rate'+count+'').val();
    console.log(sellingPrice);

   $.ajax({
            type: "GET",
            url: "/admin/billofsupply/validateProductData/" + particular,
            timeout: 600000,
            dataType: 'json',
            success: function(data) {
            var stock = data;
            alert("Available stock "+stock);
            console.log(stock);
            if(kg>stock){
            alert("Kg is greater than stock. Kindly update stock!!!");
            }
            else{
            totalPrice = kg * sellingPrice;
            $('#totalPrice'+count+'').empty();
            //$('#grossPrice').empty();

            $('#totalPrice'+count+'').val(totalPrice);
            grossPrice=parseInt($('#grossPrice').val());
            grossPrice = grossPrice + totalPrice;
            $('#grossPrice').val(grossPrice);
            //count++;
            }
            },
            error: function(e) {
                alert(e);
            }

        });

}

function getAdjustableRate(counts) {
    count=counts;
    //--count;
    console.log(count);
    adjustableRate = $('#rate'+count+'').val();
    kg = $('#kg'+count+'').val();
    if(kg==null){
    alert("Kindly enter kilogram");
    }
    console.log(kg);
    console.log(adjustableRate);

   $.ajax({
            type: "GET",
            url: "/admin/billofsupply/validateProductData/" + particular,
            timeout: 600000,
            dataType: 'json',
            success: function(data) {
            var stock = data;
            alert(stock);
            console.log(stock);

            if(kg>stock){
            alert("Kg is greater than stock. Kindly update stock!!!");
            }
            else{
            totalPrice=$('#totalPrice'+count+'').val();
            adjustableTotalPrice = kg * adjustableRate;
            $('#totalPrice'+count+'').empty();
            $('#totalPrice'+count+'').val(adjustableTotalPrice);

            //$('#grossPrice').empty();
            grossPrice = grossPrice + adjustableTotalPrice - totalPrice;
            $('#grossPrice').val(grossPrice);
            //count++;
            }

            },
            error: function(e) {
                alert(e);
            }

        });
        }


function getPendingAmount(customerName) {
    var customerName = $(customerName).val();

    $.ajax({
        type: "GET",
        url: "/admin/billofsupply/getPendingAmount/" + customerName,
        timeout: 600000,
        dataType: 'json',
        success: function(data) {
        alert(data);
        console.log(data);
        pendingAmount = data;
        $('#pendingAmount').val(data);

        },
        error: function(e) {
            alert(e);
        }

    });
}


function changePendingAmount() {
    //console.log(count);
    amountReceived = $('#amountReceived').val();
    grossPrice = $('#grossPrice').val();
    pendingAmount=$('#pendingAmount').val();
    console.log(amountReceived);
    console.log(grossPrice);
    console.log(pendingAmount);
    $.ajax({
        type: "GET",
        url: "/admin/billofsupply/changePendingAmount/" + amountReceived + "/" + grossPrice + "/" + pendingAmount,
        timeout: 600000,
        dataType: 'json',
        success: function(data) {
        alert(data);
        console.log(data);
        $('#pendingAmount').empty();
        $('#pendingAmount').val(data);

        },
        error: function(e) {
            alert(e);
        }

    });
}

function removeRow(countVal) {

    console.log(countVal);
    if(count>=0){
    //count=count-1;
    //count = $(".cheil-select").length-1;
    console.log(count);
    var totalPriceRemove = $('#totalPrice'+countVal+'').val();
        console.log(totalPriceRemove);
        grossPrice=parseInt($('#grossPrice').val());
        grossPrice = grossPrice - totalPriceRemove;
        console.log(grossPrice);
        pendingAmount=parseInt($('#pendingAmount').val());
        $('#grossPrice').val(grossPrice);
    $('#inputParamRow'+countVal).remove();
    }

  }


