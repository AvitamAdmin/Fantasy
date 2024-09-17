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
                    <th class="th-sm">Id</th>
                      <th class="th-sm">Transaction</th>
                      <th class="th-sm">Member Name</th>
                      <th class="th-sm">Member Email</th>
                      <th class="th-sm">Amount Of Withdraw</th>
                      <th class="th-sm">chargeOfWithdraw</th>
                      <th class="th-sm">Withdraw Method</th>
                      <th class="th-sm">Processing Time</th>
                      <th class="th-sm">Amount In Method Currency</th>
                      <th class="th-sm">Date Of Create</th>
                      <th class="th-sm">Details</th>
                      <th class="th-sm">Withdrawal Status</th>
                    </tr>
              </thead>
              <tbody>
                    <tr>
                    <td class="td-sm">${withdrawalDetails.id}</td>
                        <td class="td-sm">${withdrawalDetails.transaction}</td>
                        <td class="td-sm">${withdrawalDetails.memberName}</td>
                        <td class="td-sm">${withdrawalDetails.memberEmail}</td>
                        <td class="td-sm">${withdrawalDetails.amountOfWithdraw}</td>
                        <td class="td-sm">${withdrawalDetails.chargeOfWithdraw}</td>
                        <td class="td-sm">${withdrawalDetails.withdrawMethod}</td>
                        <td class="td-sm">${withdrawalDetails.processingTime}</td>
                        <td class="td-sm">${withdrawalDetails.amountInMethodCurrency}</td>
                        <td class="td-sm">${withdrawalDetails.dateOfCreate}</td>
                        <td class="td-sm">${withdrawalDetails.details}</td>
                        <td class="td-sm">${withdrawalDetails.withdrawalStatus}</td>
                     </tr>
             </tbody>
         </table>

                        <p>Charge Already taken. Send ${withdrawalDetails.amountOfWithdraw} INR To The User</p>

<form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/pendingWithdrawal/action"  class="handle-upload" modelAttribute="withdrawalDetails" >

         <div class="col-sm-6">
                        Message<form:textarea path="message" id="message" rows="6" cols="30" class="inputbox-cheil-textarea"/>
         </div>

         <div class="col-sm-12">
              <div class="dt-buttons">
              <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/pendingWithdrawal/action')" aria-controls="tableData" title="Processed" type="button">Processed</button>
              <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Refund">Refund</button>
              </div>
            </div>
 </form:form>

    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>