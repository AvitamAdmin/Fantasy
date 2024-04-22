<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/subsidiary/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/subsidiary')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
                <form:input path="subId" class="inputbox-cheil-small" placeholder="Id/Name" />
                <span>Id/Name</span>
                <form:errors path="subId" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="shortDescription" class="inputbox-cheil-long" placeholder="Short description (128 letters)" />
                <span>Short description</span>
                <form:errors path="shortDescription" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="cluster" class="inputbox-cheil-small" placeholder="Cluster id/Name" />
                <Span>Cluster id</span>

                <form:errors path="cluster" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="isoCode" class="inputbox-cheil-small" placeholder="IsoCode" />
                <span>IsoCode</span>
                <form:errors path="shortDescription" class="text-danger"></form:errors>
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