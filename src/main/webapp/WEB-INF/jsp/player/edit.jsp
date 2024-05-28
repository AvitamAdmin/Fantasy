<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/player/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/player')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('editForm');" aria-controls="tableData" type="submit" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
             <div class="col-sm-3">
                <form:input path="playerName" class="inputbox-cheil-small" placeholder="Player Name"/>
                <span>Player Name</span>
                <form:errors path="playerName" class="text-danger"></form:errors>
             </div>
               <div class="col-sm-3">
                   <select class="cheil-select" name="teamId">
                      <option value="">Select Team</option>
                      <c:forEach items="${teams}" var="team">
                          <c:choose>
                              <c:when test="${fn:contains( editForm.teamId, team) }">
                                <option value="${team.id}" selected>${team.teamName}</option>
                              </c:when>
                              <c:otherwise>
                                 <option value="${team.id}" >${team.teamName}</option>
                              </c:otherwise>
                          </c:choose>
                      </c:forEach>
                  </select>
               </div>
            <div class="col-sm-3">
                 <select class="cheil-select" name="playerRoleId">
                    <option value="">Select Role</option>
                    <c:forEach items="${playerRoles}" var="playerRole">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.playerRoleId, playerRole.id ) }">
                              <option value="${playerRole.id}" selected>${playerRole.playerRole}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${playerRole.id}" >${playerRole.playerRole}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-3">
                <form:input type="file" name="playerImage" path="playerImage" />
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