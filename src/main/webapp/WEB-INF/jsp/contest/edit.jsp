<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/contest/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/contest')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
                <form:input path="name" class="inputbox-cheil-small" placeholder="Enter Name" />
                <span>Name</span>
                <form:errors path="name" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="totalPrice" class="inputbox-cheil-small" placeholder="Enter Total Price" />
                <span>Total Price</span>
                <form:errors path="totalPrice" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="entryFee" class="inputbox-cheil" placeholder="Entry Fee" />
                <span>Entry Fees</span>
                <form:errors path="entryFee" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="noOfMembers" class="inputbox-cheil" placeholder="Members " />
                <span>No.Of.Members</span>
                <form:errors path="noOfMembers" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
              <form:input path="winPercent" class="inputbox-cheil"  />
              <span>Win %</span>
              <form:errors path="winPercent" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="maxTeams" class="inputbox-cheil" placeholder="Teams " />
                <span>Max Teams</span>
                <form:errors path="maxTeams" class="text-danger"></form:errors>
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