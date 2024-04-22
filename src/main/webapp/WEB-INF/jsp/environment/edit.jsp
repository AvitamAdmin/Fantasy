<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/environment/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/environment')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('editForm');" aria-controls="tableData" type="submit" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-4">
                <form:input path="identifier" class="inputbox-cheil-small" placeholder="Enter Id" required="required" />
                <span>Enter Id</span>
                <form:errors path="identifier" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-4">
                <form:input path="shortDescription" class="inputbox-cheil-long" placeholder="Enter Description" />
                <span>Enter Description</span>
                <form:errors path="shortDescription" class="text-danger"></form:errors>
            </div>
        </div>
        <br/><br/>
        <div class="row">
            <div class="col-sm-12">
                <form:input path="preConditionURL" class="inputbox-cheil-small" placeholder="Pre Condition URL" />
                <span>Pre Condition URL</span>
                <form:errors path="preConditionURL" class="text-danger"></form:errors>
            </div>
        </div>
        <br/><br/>
        <div class="row">
            <div class="col-sm-12">
                <form:input path="hybrisUrlFormula" class="inputbox-cheil-small" placeholder="Hybris Url Formula" required="required"/>
                <span>Hybris Url Formula</span>
                <form:errors path="hybrisUrlFormula" class="text-danger"></form:errors>
            </div>
        </div>
        <br/><br/>
        <div class="row">
            <div class="col-sm-12">
                <form:input path="hybrisUrlExample" class="inputbox-cheil-small" placeholder="Hybris Url Example" />
                <span>Hybris Url Example</span>
                <form:errors path="hybrisUrlExample" class="text-danger"></form:errors>
            </div>
        </div>
            <br/><br/>
        <div class="row">
            <div class="col-sm-12">
                <form:input path="aemUrlFormula" class="inputbox-cheil-small" placeholder="AEM Url Formula" />
                <span>AEM Url Formula</span>
                <form:errors path="aemUrlFormula" class="text-danger"></form:errors>
            </div>
        </div>
            <br/><br/>
        <div class="row">
            <div class="col-sm-12">
                <form:input path="aemUrlExample" class="inputbox-cheil-small" placeholder="AEM Url Example" />
                <span>AEM Url Example</span>
                <form:errors path="aemUrlExample" class="text-danger"></form:errors>
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