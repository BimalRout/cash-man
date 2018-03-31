<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="_head.jsp"/>

    <h1 class="text-center header">Amount Update Report</h1>
    <hr class="divider"/>

    <form role="form"
          action="<c:url value='/j_spring_security_logout'/>"
          method="POST">
        <div class="form-group">
            <label for="currentDate" class="text-center">Date</label>
            <input id="currentDate" class="form-control text-center" type="text" value="${currentDate}" readonly>
        </div>
        <div class="form-group">
            <label for="number" class="text-center">Credit Card Number</label>
            <input id="number" class="form-control text-center" type="text" value="${number}" readonly>
        </div>
        <div class="form-group">
            <label for="withdrawalAmount" class="text-center">Twenty Denominations</label>
            <input id="withdrawalAmount" class="form-control text-center" type="text" value="${twentyDenom}" readonly>
        </div>
        <div class="form-group">
            <label for="balanceAmount" class="text-center">Fifty Denominations</label>
            <input id="balanceAmount" class="form-control text-center" type="text" value="${fiftyDenom}" readonly>
        </div>
        <hr class="divider"/>
        <div class="form-group">
            <label for="balanceAmount" class="text-center">Amount</label>
            <input id="balanceAmount" class="form-control text-center" type="text" value="${amount}$" readonly>
        </div>
        

        <div class="form-group">
            <table class="three-td-table">
                <tr>
                    <td>
                        <button type="submit" class="btn btn-default btn-block">Exit</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </td>
                    <td>
                        <a href="<c:url value='/operations'/>" class="btn btn-default btn-block">Back</a>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
    </form>

<jsp:include page="_foot.jsp"/>