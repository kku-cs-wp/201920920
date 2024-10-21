<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index Page</title>
    <!-- 추가적인 CSS 또는 JavaScript 파일 링크 -->
</head>
<body>

    <!-- 모달 팝업 HTML 추가 -->
    <div id="modalPop" class="modal" style="display: none;">
        <div class="modal-content">
            <span class="close-button" onclick="closeModal()">&times;</span>
            <div id="modal-body"></div>
        </div>
    </div>

    <style>
        /* Modal 스타일 정의 */
        .modal {
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }
        .close-button {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close-button:hover, .close-button:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>

    <!-- 모달 팝업을 위한 JavaScript 함수 추가 -->
    <script>
        function openModalFetch(data) {
            const modal = document.getElementById('modalPop');
            const modalBody = document.getElementById('modal-body');
            modalBody.innerHTML = data;
            modal.style.display = 'block';
        }

        function closeModal() {
            const modal = document.getElementById('modalPop');
            modal.style.display = 'none';
        }
    </script>

    <!-- 페이지를 로드하고 카드를 동적으로 생성하는 함수 -->
    <script>
        async function loadPage(pageId, pageUrl, reload = false) {
            const mainContent = document.querySelector('main');
            const existingPage = document.getElementById(pageId);

            // 이미 로드된 페이지가 있을 때 reload 조건에 따라 처리
            if (existingPage) {
                if (reload) {
                    existingPage.remove();
                } else {
                    setActivePage(pageId);
                    return;
                }
            }

            // POST 요청을 보내면서 파라미터를 전달
            const response = await fetch(pageUrl, {
                method: 'POST',
            });

            const isOk = response.ok;
            const data = await response.text();

            if (!isOk) {
                openModalFetch(data); // 오류 발생 시 모달 창 호출
            } else {
                const newPageCard = document.createElement('div');
                newPageCard.id = pageId;
                newPageCard.classList.add('page-card');
                newPageCard.innerHTML = data;
                mainContent.appendChild(newPageCard);

                setActivePage(pageId);
                adjustPaddingForHome(pageId);

                // 페이지 내 script 태그 재생성 처리
                const scripts = newPageCard.getElementsByTagName('script');
                Array.from(scripts).forEach((script, i) => {
                    const scriptId = `${pageId}_script_${i}`;
                    const existingScript = document.getElementById(scriptId);

                    if (existingScript) existingScript.remove();

                    const newScript = document.createElement('script');
                    newScript.id = scriptId;
                    newScript.text = script.text;
                    document.body.appendChild(newScript);
                });
            }
        }
    </script>

</body>
</html>
