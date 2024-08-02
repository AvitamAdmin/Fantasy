<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/deposit/depositLog/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-3">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/deposit/depositLog')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
             <div class="col-sm-3">
                <form:input path="userId" class="inputbox-cheil-small" placeholder="User Id" readonly="true" />
                <span>User Id</span>
                <form:errors path="userId" class="text-danger"></form:errors>
             </div>
            <div class="col-sm-3">
                <form:input path="payingAmount" class="inputbox-cheil-small" placeholder="Paying Amount" />
                 <span>Paying Amount</span>
                <form:errors path="payingAmount" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="gatewayName" class="inputbox-cheil-small" placeholder="Gateway Name" />
                <span>Gateway Name</span>
                <form:errors path="gatewayName" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="transactionId" class="inputbox-cheil-small" placeholder="Transaction Id" />
                <span>Transaction Id</span>
                <form:errors path="transactionId" class="text-danger"></form:errors>
            </div>
            <br><br>
            <div class="col-sm-3">
                 <form:input path="depositStatus" class="inputbox-cheil-small" placeholder="Deposit Status" />
                 <span>Status</span>
                 <form:errors path="depositStatus" class="text-danger"></form:errors>
            </div>
        </div>
       </form:form>
       <c:if test="${not empty message}">
           <div class="alert alert-danger" role="alert" id="errorMessage">
               <spring:message code="${message}" />
           </div>
       </c:if>
    </div>
  </div>
</div>