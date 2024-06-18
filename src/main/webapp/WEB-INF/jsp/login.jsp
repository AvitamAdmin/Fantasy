<%@ include file="security/loginHeader.jsp" %>
  <body>
  <br/>
  <br/>
  <br/>
    <div class="main-content">

      <form method="POST" action="${contextPath}/login" class="form-signin">
      <br/>
      <div class="row">
              <div class="col-sm-4"></div>
              <div  style="text-align:center;" class="col-sm-4">
                  <img style="width:180px;background-color:#6D33FF" src="${contextPath}/images/logo.png"/>
              </div>
              <div class="col-sm-4"></div>
      </div>
      <br/><br/>
      <div class="row form-group ${error != null ? 'has-error' : ''}">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
                <span>${message}</span>
                <input name="username" type="email" class="inputbox-cheil-small" placeholder="Email" required/>
                <span>Email Id</span>
            </div>
            <div class="col-sm-4"></div>
      </div>
        <div class="row form-group ${error != null ? 'has-error' : ''}">
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                    <input name="password" type="password" class="inputbox-cheil-small" placeholder="Password" required/>
                    <span>Password</span>
                    <span>${error}</span>
                </div>
                <div class="col-sm-4"></div>
        </div>
        <br/>
        <div class="row form-group ${error != null ? 'has-error' : ''}">
            <div class="col-sm-4"></div>
                <div class="col-sm-4 text-center">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="btn btn-lg btn-primary btn-cheil" type="submit">Log In</button>
                </div>
            <div class="col-sm-4"></div>
        </div>
         <div class="row">
             <div class="col-sm-4"></div>
             <div class="col-sm-4">
                 <h4 class="text-center cheil-text"><a href="${contextPath}/forgotpassword">Reset the password</a></h4>
             </div>
             <div class="col-sm-4"></div>
          </div>
          <br/>
          <div class="row">
               <div class="col-sm-4"></div>
               <div class="col-sm-4">
                   <h4 class="text-center">OR</h4>
               </div>
               <div class="col-sm-4"></div>
         </div>
         <br/>
         <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4 text-center">
                <button class="btn btn-lg btn-primary btn-cheil" type="button" onclick="location.href='${contextPath}/register'">Register</button>
            </div>
            <div class="col-sm-4"></div>
         </div>
      </form>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  </body>
  <%@ include file="footer.jsp" %>
</html>