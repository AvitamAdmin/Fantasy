<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/teamLineup/edit"  class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/teamLineup')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
              <select class="cheil-select" name="teamId">
                <option value="">Select Team</option>
                    <c:forEach items="${teams}" var="child">
                      <c:choose>
                        <c:when test="${fn:contains( editForm.teamId, child ) }">
                          <option value="${child.id}" selected>${child.name}</option>
                        </c:when>
                        <c:otherwise>
                         <option value="${child.id}" >${child.name}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
              </select>
            </div>
           <div class="col-sm-3">
             <select class="cheil-select" name="playerId">
               <option value="">Select Players</option>
                   <c:forEach items="${players}" var="child">
                     <c:choose>
                       <c:when test="${fn:contains( editForm.playerId, child ) }">
                         <option value="${child.id}" selected>${child.name}</option>
                       </c:when>
                       <c:otherwise>
                        <option value="${child.id}" >${child.name}</option>
                       </c:otherwise>
                     </c:choose>
                   </c:forEach>
             </select>
           </div>

           <div class="col-sm-3">
                  <h5>Line Up Status</h5>
                                  <c:choose>
                                      <c:when test="${editForm.status}">
                                           <c:set var="varChecked" value="checked"></c:set>
                                       </c:when>
                                       <c:otherwise>
                                           <c:set var="varUnchecked" value="checked"></c:set>
                                       </c:otherwise>
                                  </c:choose>

                                  <input type="radio" name="status"  value="true" ${varChecked}> Available
                                  <input type="radio" name="status" value="false" ${varUnchecked}> Not-Available
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