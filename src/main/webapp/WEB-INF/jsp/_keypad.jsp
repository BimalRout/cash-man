<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Generates numeric keypad --%>
<div class="keypad-container">
    <div class="form-group">
        <button class="keypad-clear-key btn btn-default btn-block" type="button"><c:out value="${param.clearButtonName}" default="Clear"/></button>
    </div>

    <script src="/js/keypad.js"></script>

    <div class="form-group">
        <table class="three-td-table">
            <tbody>
                <c:set var="keys">1,2,3,4,5,6,7,8,9,0</c:set>
                <tr>
                    <c:forTokens var="key" items="${keys}"
                                 delims=",">
                        <c:if test="${key == 0}">
                            <td></td>
                        </c:if>
                        <td>
                            <button class="keypad-key btn btn-default btn-block" type="button" value="<c:out value='${key}'/>" ><c:out value='${key}'/></button>
                        </td>
                        <c:if test="${key != 0 && key % 3 == 0}">
                            </tr>
                            <tr>
                        </c:if>
                        <c:if test="${key == 0}">
                            <td></td>
                        </c:if>
                    </c:forTokens>
                </tr>
            </tbody>
        </table>
    </div>

</div>