<script>
var table = $('#tableData').DataTable({
    ordering: false,
            responsive: true,
    language: {
        searchPlaceholder: "Search",
        search: "",
    },
    "pagingType": "first_last_numbers",
      "lengthMenu": [[100, 200,-1], [100, 200, "All"]],
      dom: 'flripBt',
    buttons: [
        {
            extend:    'excelHtml5',
            title: '',
            text: '<button class="btn btn-primary btn-icon" type="button">Excel</button>',
            filename: function(){
               var d = new Date();

               var n = d.getTime();
               return 'Zero-in '+$("#navBreadcrumb").text().split(" > ")[2] + 'reports ' + d.toLocaleDateString('en-GB').split('/').reverse().join('') + '-' + n;
           },
            titleAttr: 'Excel',
            header: 'true'
        }
    ]
} );

$(document).ready(function () {
    // Setup - add a text input to each footer cell
    $('#tableData thead th').each(function () {
        var title = $(this).text();
        $(this).html('<p style="display:none;">'+title+'</p><input type="text" id=' +title+ ' placeholder="Search ' + title + '" />');
    });
                table.columns().eq(0).each(function(colIdx) {
                            $('input', table.column(colIdx).header()).on('change', function() {
                                console.log(colIdx + '-' + this.value);
                                table
                                        .column(colIdx)
                                        .search(this.value)
                                        .draw();
                            });
                            });

                $('input').off('keyup keydown keypress');
});

</script>