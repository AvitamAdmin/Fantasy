<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/pointsMaster/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/pointsMaster')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
           <div class="col-sm-2">
                <select class="cheil-select" name="matchTypeId">
                   <option value="">Select MatchType</option>
                   <c:forEach items="${matchTypes}" var="child">
                       <c:choose>
                           <c:when test="${fn:contains( editForm.matchTypeId, child ) }">

                             <option value="${child.id}" selected>${child.name}</option>
                           </c:when>
                           <c:otherwise>
                                    <option value="${child.id}" >${child.name}</option>
                           </c:otherwise>
                       </c:choose>
                   </c:forEach>
               </select>
           </div>
            <div class="col-sm-1">
                <form:input path="runs" class="inputbox-cheil-small" placeholder="runs" />
                <span>Runs</span>
                <form:errors path="runs" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                <form:input path="wickets" class="inputbox-cheil-small" placeholder="wickets" />
                <span>Wickets</span>
                <form:errors path="wickets" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                <form:input path="halfCentury" class="inputbox-cheil-small" placeholder="halfCentury" />
                <span>Half-Century</span>
                <form:errors path="halfCentury" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                <form:input path="century" class="inputbox-cheil-small" placeholder="Century" />
                <span>Century</span>
                <form:errors path="century" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                <form:input path="thirtyPlus" class="inputbox-cheil-small" placeholder="30 Plus" />
                <span>30 Plus</span>
                <form:errors path="thirtyPlus" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                <form:input path="threeWickets" class="inputbox-cheil-small" placeholder="3 Wickets" />
                <span>3 Wickets</span>
                <form:errors path="threeWickets" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                <form:input path="fiveWickets" class="inputbox-cheil-small" placeholder="5 Wickets" />
                <span>5 Wickets</span>
                <form:errors path="fiveWickets" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                  <form:input path="hattrickWickets" class="inputbox-cheil-small" placeholder="hattrickWickets" />
                  <span>Hattrick Wickets</span>
                  <form:errors path="hattrickWickets" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                <form:input path="hattrickSixes" class="inputbox-cheil-small" placeholder="hattrickSixes" />
                <span>Hattrick-Sixes</span>
                <form:errors path="hattrickSixes" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-1">
                  <form:input path="economy" class="inputbox-cheil-small" placeholder="economy" />
                  <span>Economy</span>
                  <form:errors path="economy" class="text-danger"></form:errors>
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