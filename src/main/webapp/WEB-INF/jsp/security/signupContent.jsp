<div class="main-content">
  <div class="row">
    <form:form method="POST" modelAttribute="userForm" class="handle-uploadn">
      <br/>
      <div class="row">
              <div class="col-sm-3"></div>
              <div style="text-align:center;" class="col-sm-6">
                <img style="width:150px;" src="${contextPath}/images/logo.png"/>
              </div>
              <div  class="col-sm-3"></div>
      </div>
      <br/><br/>
        <spring:bind path="username">
        <div class="row form-group ${status.error ? 'has-error' : ''}">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form:input type="email" path="username" class="inputbox-cheil" placeholder="Corporate email"
                            autofocus="true" required="required"></form:input>
                            <span>Corporate email</span>
                <form:errors path="username" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="row form-group ${status.error ? 'has-error' : ''}">
                <div class="col-sm-3"></div>
                <div class="col-sm-6">
                    <form:input type="password" path="password" class="inputbox-cheil-small" placeholder="Password"></form:input>
                    <span>Password</span>
                    <form:errors path="password" class="text-danger"></form:errors>
                </div>
                <div class="col-sm-3"></div>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="row form-group ${status.error ? 'has-error' : ''}">
                <div class="col-sm-3"></div>
                <div class="col-sm-6">
                    <form:input type="password" path="passwordConfirm" class="inputbox-cheil-long"
                            placeholder="Confirm Password" required="required"></form:input>
                            <span>Confirm Password</span>
                    <form:errors path="passwordConfirm" class="text-danger"></form:errors>
                </div>
                <div class="col-sm-3"></div>
            </div>
        </spring:bind>
         <br/>
        <spring:bind path="organization">
                <div class="row form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-3"></div>
                    <div class="col-sm-6">
                        <form:input type="text" path="organization" class="inputbox-cheil" placeholder="Organization" required="required"></form:input>
                                    <span>Organisation</span>
                        <form:errors path="organization" class="text-danger"></form:errors>
                    </div>
                    <div class="col-sm-3"></div>
                </div>
                </spring:bind>
                <br/>
        <div class="row">
        <div class="col-sm-3"></div>
            <div class="col-sm-6" style="font-size:12px;">Select the role you would like to enroll</div>
        </div>

        <spring:bind path="roles">

        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <select name="roles[]" id="roles" class="lg-btn 3col active cheil-select" required="required">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.id}">${role.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </spring:bind>
        <br/>
        <spring:bind path="referredBy">
        <div class="row form-group ${status.error ? 'has-error' : ''}">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form:input type="text" path="referredBy" class="inputbox-cheil" placeholder="Referred By" required="required"></form:input>
                            <span>Referred by</span>
                <form:errors path="referredBy" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </spring:bind>
        <div class="row">
        <div class="col-sm-3"></div>
                    <div class="col-sm-6" style="font-size:13px;margin-top:-10px">The referrer is your department head. He/she will receive the confirmation email and approve your access.</div>
                </div>
        <br/>
        <br/>
        <div class="row">
        <div class="col-sm-4"></div>
                            <div class="col-sm-4" style="font-size:13px; text-align:center;">By clicking Submit, I agree to the <a href='#'>Terms and Conditions</a> Platform usage and the Privacy Statement.</div>
                        </div>
                        <br/>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-6" style="display:flex;justify-content: center;">
                <button class="btn btn-lg btn-primary btn-block btn-cheil" type="submit">Submit</button>
            </div>
            <div class="col-sm-3"></div>
        </div>
      </form:form>
      <c:if test="${not empty message}">
         <div class="alert alert-danger" role="alert" id="errorMessage">
             <spring:message code="${message}" />
         </div>
     </c:if>
    </div>
  </div>