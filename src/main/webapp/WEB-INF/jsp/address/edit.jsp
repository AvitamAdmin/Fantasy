<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/address/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/address')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
             <div class="col-sm-6">
                <form:input path="mobileNumber" class="inputbox-cheil-small" placeholder="Mobile Number" readonly="true"/>
             </div>
            <div class="col-sm-6">
                <form:input path="line_1" class="inputbox-cheil-small" placeholder="Line_1" />
                <span>Line_1</span>
                <form:errors path="line_1" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-6">
                <form:input path="line_2" class="inputbox-cheil-small" placeholder="Line_2" />
                <span>Line_2</span>
                <form:errors path="line_2" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-6">
                <form:input path="city" class="inputbox-cheil-small" placeholder="City" />
                <span>City</span>
                <form:errors path="city" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-6">
                <form:input path="state" class="inputbox-cheil-small" placeholder="State" />
                <span>State</span>
                <form:errors path="state" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-6">
                <form:input path="pinCode" class="inputbox-cheil-small" placeholder="PinCode" />
                <span>PinCode</span>
                <form:errors path="pinCode" class="text-danger"></form:errors>
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