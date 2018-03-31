<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="_head.jsp"/>

    <h1 class="text-center header">Home</h1>
    <hr class="divider"/>

    <c:if test="${not empty msg}">
        <div class="alert alert-info text-center">
            <c:out value="${msg}"/>
        </div>
    </c:if>

    <form id="creditCardNumberForm"
          name="creditCardNumberForm"
          role="form"
          action="<c:url value='enter_card' />"
          method="POST"
          autocomplete="off">

        <div class="form-group">
            <c:set var="numberInputId" value="number-input"/>
            <div class="form-group">
                <label for="${numberInputId}" class="sr-only">Credit Card Number</label>
                <input id="${numberInputId}" class="form-control text-center" type="text" name="number"
                       placeholder="Card Number" readonly required>
            </div>
        </div>

        <jsp:include page="_keypad.jsp"/>
        <script>$(document).ready(function() { startKeypad('#${numberInputId}', 19, true); });</script>

        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">OK</button>
        </div>

        <%-- needed for CSRF protection - we should not disable this protection for web applications --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    </form>

    <script>
        // prevent submitting if nothing was entered
        $(document).ready(function() {
            $('#creditCardNumberForm').submit(function() {
                if ($.trim($("#${numberInputId}").val()) === "") {
                    return false;
                }
            });
        });
    </script>

<jsp:include page="_foot.jsp"/>
