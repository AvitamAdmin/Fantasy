<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/matches/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/matches')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
                           <select class="cheil-select" name="team1">
                              <option value="">Select Team1</option>
                              <c:forEach items="${teams}" var="child">
                                  <c:choose>
                                      <c:when test="${fn:contains( editForm.team1, child ) }">
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
                <select class="cheil-select" name="team2">
                                 <option value="">Select Team2</option>
                                 <c:forEach items="${teams}" var="child">
                                     <c:choose>
                                         <c:when test="${fn:contains( editForm.team2, child ) }">
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
                <form:input path="dateAndTime" type="datetime-local" class="inputbox-cheil-small" placeholder="Date And Time" />
                <span>Date And Time</span>
                <form:errors path="dateAndTime" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
              <select class="cheil-select" name="tournamentId">
                 <option value="">Select Tournament</option>
                     <c:forEach items="${tournament}" var="child">
                         <c:choose>
                             <c:when test="${fn:contains( editForm.tournament, child ) }">
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
               <select class="cheil-select" name="sportType">
                     <option value="">Select SportType</option>
                        <c:forEach items="${sportType}" var="child">
                            <c:choose>
                                <c:when test="${fn:contains( editForm.sportType, child ) }">
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
              <select class="cheil-select" name="contest">
                 <option value="">Select contest</option>
                    <c:forEach items="${contest}" var="child">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.contest, child ) }">
                              <option value="${child.id}" selected>${child.name}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${child.id}" >${child.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>

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