<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.tags.core" prefix="fn" %>
<%@ taglib prefix="custom" uri="kr.ac.kku.cs.wp.tags.custom" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User List</title>
</head>
<body>
    <h1>User List</h1>
    <div id="refresh">
        <div id="user-count" style="margin-bottom: 20px;">전체 사용자 수: <strong>${fn:length(requestScope.users)}</strong></div>
        <div class="user-card-container" id="user-list">
            <c:forEach var="user" items="${users}">
                <custom:userCard status="${user.status}"
                                 email="${user.email}" name="${user.name}"
                                 roles="${user.userRoles[0].role.role},${user.userRoles[1].role.role}"
                                 id="${user.id}" />
            </c:forEach>
        </div>
    </div>
</body>
<script>
    // 사용자 필터링 함수
    function filterUsers() {
        const filterValue = document.getElementById('user-filter').value.toLowerCase();
        filter({ 'id': filterValue, 'name': filterValue, 'email': filterValue, 'status': filterValue, 'userRoles': [{ 'roleName': filterValue }] });
    }

    // 서버에 요청하여 사용자 목록을 업데이트하는 함수
    async function filter(params) {
        const mainContent = document.getElementById('refresh');
        const response = await fetch('/user/userlist', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(params),
        });

        const isOk = response.ok;
        const data = await response.text();

        if (!isOk) {
            openModalFetch(data); // 오류 발생 시 모달을 열어줌
        } else {
            mainContent.innerHTML = data; // 성공 시 사용자 목록 업데이트
        }
    }
</script>
</html>

