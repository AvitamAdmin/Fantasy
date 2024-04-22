<%@ include file="../include.jsp" %>
<div class="main-content">
<div class="row">
    <div class="col-sm-12">
        <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
    </div>
</div>
  <div class="row">
    <div class="col-sm-12">
         <table id="tableData" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%" data-tableName="Stock Data">
              <input type="hidden" id="rowSelectorId" name="rowSelectorId" value="">
              <thead>
                    <tr>
                      <th class="th-sm">Timestamp</th>
                      <th class="th-sm">TestID</th>
                      <th class="th-sm">ProductURLS</th>
                      <th class="th-sm">Result</th>
                      <th class="th-sm">Order Number</th>
                      <th class="th-sm">View Report</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${results}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.timeStamp}</td>
                        <td class="td-sm">${model.testName}</td>
                        <td class="td-sm">${model.productUrls}</td>
                        <td class="td-sm">${model.status}</td>
                        <td class="td-sm">${model.orderNumber}</td>
                        <td class="td-sm"><a href="${contextPath}${model.reportFilePath}" target="_blank" style="color:blue;">View</a></td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableAction-qa.jsp" %>