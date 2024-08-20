<%@ include file="../include.jsp" %>
<style>
    table {
        border-collapse: collapse;
        width: 100%;
    }
    th, td {
        border: 1px solid black;
        padding: 8px;
        text-align: left;
    }

    .data-box {
        border: 1px solid #ddd;
        border-radius: 5px;
        padding: 15px;
        background-color: #f9f9f9;
        text-align: center;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
    }

</style
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
<div class="main-content">
<div class="row">
    <div class="col-sm-12">
        <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
    </div>
</div>
  <br><br>
  <div class="container">
     <div class="row">
           <h2 style="text-align:center; color:Orange"><b>DASHBOARD</b></h2>
     </div>
      <br><br>
      <div class="row">
                <div class="col-sm-3">
                   <div class="data-box">
                      <p>Total Users</p>
                      <h4>3679<h4>
                   </div>
                </div>
                <div class="col-sm-3">
                  <div class="data-box">
                      <p>Verified Users</p>
                      <h4>3170<h4>
                  </div>
                </div>
                <div class="col-sm-3">
                  <div class="data-box">
                      <p>Unverified Users</p>
                      <h4>435<h4>
                  </div>
                </div>
                <div class="col-sm-3">
                  <div class="data-box">
                      <p>Banned Users</p>
                      <h4>79<h4>
                  </div>
                </div>
      </div>
      </br>
      <div class="row">
              <div class="col-sm-3">
                <div class="data-box">
                    <p>Total Deposits</p>
                    <h4>${totalDeposits}<h4>
                </div>
              </div>
              <div class="col-sm-3">
                <div class="data-box">
                    <p>Pending Deposits</p>
                    <h4>${pendingDeposits}<h4>
                </div>
              </div>
              <div class="col-sm-3">
                <div class="data-box">
                    <p>Accepted Deposits</p>
                    <h4>${approvedDeposits}<h4>
                </div>
              </div>
              <div class="col-sm-3">
                <div class="data-box">
                  <p>Monthly Deposits</p>
                  <h4>Rs.410497<h4>
                </div>
              </div>
      </div>
      <br>
      <div class="row">
            <div class="col-sm-3">
               <div class="data-box">
                  <p>Total WithDrawls</p>
                  <h4>245<h4>
               </div>
            </div>
            <div class="col-sm-3">
              <div class="data-box">
                <p>Pending WithDrawls</p>
                <h4>50<h4>
              </div>
            </div>
            <div class="col-sm-3">
              <div class="data-box">
                <p>Accepted WithDrawls</p>
                <h4>195<h4>
              </div>
            </div>
            <div class="col-sm-3">
              <div class="data-box">
                <p>Monthly WithDrawls</p>
                <h4>Rs.118409<h4>
              </div>
            </div>
      </div>
      <br>
      <div class="row">
          <div class="col-sm-3">
             <div class="data-box">
                <p>Live Matches</p>
                <h4>6<h4>
             </div>
          </div>
          <div class="col-sm-3">
            <div class="data-box">
              <p>Upcoming Matches</p>
              <h4>3<h4>
            </div>
          </div>
          <div class="col-sm-3">
            <div class="data-box">
              <p>Closed Matches</p>
              <h4>5<h4>
            </div>
          </div>
      </div>
      <br>
        <div id="barchart_material" style="width: 900px; height: 500px;"></div>
      </br>
  </div>

  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['bar']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Month', 'Deposits', 'Withdraw'],
          ['Feb2024', 1400, 490],
          ['Mar2024', 1170, 460],
          ['Apr2024', 660, 1120],
          ['May2024', 1030, 540],
          ['Jun2024', 950, 400],
          ['Jul2024', 170, 60],
          ['Aug2024', 510, 320],
          ['Sep2024', 330, 190]
        ]);

        var options = {
          chart: {
            title: 'Monthly Deposits & Withdraw',
            subtitle: 'Deposit, Withdraw',
          },
          bars: 'vertical'
        };

        var chart = new google.charts.Bar(document.getElementById('barchart_material'));

        chart.draw(data, google.charts.Bar.convertOptions(options));
      }
    </script>