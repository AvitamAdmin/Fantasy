<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/kyc/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/kyc')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('editForm');" aria-controls="tableData" type="submit" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">

            <div class="col-sm-3">
                <form:input path="userIds" class="inputbox-cheil-small" placeholder="Enter-Id" />
                <span>UserId</span>
                <form:errors path="userIds" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="panNumber" class="inputbox-cheil-small" placeholder="Enter Pan-Number" />
                <span>Pan-Number</span>
                <form:errors path="panNumber" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="panImage" type="file" class="inputbox-cheil-small" />
                <span>Pan-Image</span>
                <form:errors path="panImage" class="text-danger"></form:errors>
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