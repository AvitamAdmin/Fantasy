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
                      <th class="th-sm">PK</th>
                      <th class="th-sm">Bill No</th>
                      <th class="th-sm">Date</th>
                      <th class="th-sm">Customer Name</th>
                      <th class="th-sm">Rate</th>
                      <th class="th-sm">Particulars</th>
                      <th class="th-sm">Kg</th>
                      <th class="th-sm">Quantity</th>
                      <th class="th-sm">Total Price</th>
                      <th class="th-sm">Gross Price</th>
                      <th class="th-sm">Shop Name</th>
                      <th class="th-sm">Shop Short Description</th>
                      <th class="th-sm">Shop Address</th>
                      <th class="th-sm">Mobile Number</th>
                      <th class="th-sm">UPI Payment</th>
                      <th class="th-sm">Cash Payment</th>
                      <th class="th-sm">Pending Amount</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm">${model.billNo}</td>
                        <td class="td-sm">${model.date}</td>
                        <td class="td-sm">${model.customerName}</td>
                        <td class="td-sm">${model.rate}</td>
                        <td class="td-sm">${model.particulars}</td>
                        <td class="td-sm">${model.kg}</td>
                        <td class="td-sm">${model.quantity}</td>
                        <td class="td-sm">${model.totalPrice}</td>
                        <td class="td-sm">${model.grossPrice}</td>
                        <td class="td-sm">${model.shopName}</td>
                        <td class="td-sm">${model.shopShortDescription}</td>
                        <td class="td-sm">${model.shopAddress}</td>
                        <td class="td-sm">${model.mobileNumber}</td>
                        <td class="td-sm">${model.upiPayment}</td>
                        <td class="td-sm">${model.cashPayment}</td>
                        <td class="td-sm">${model.pendingAmount}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>