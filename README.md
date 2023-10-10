20231006_MemberBoard Project
1. 데이터 관련
   a. 회원데이터: 회원번호(id), 이메일(memberEmail), 비밀번호(memberPassword),
   이름(memberName), 전화번호(memberMobile), 생년월일(memberBirth) 프로필
   사진(memberProfile), 가입일자(createdAt)
   b. 게시글데이터: 글번호(id), 제목(boardTitle), 작성자(boardWriter), 내용
   (boardContents), 조회수(boardHits), 작성일자(createdAt), 첨부파일여부
   (fileAttached)
   c. 게시글 첨부파일 데이터: 번호(id), 게시글번호(boardId), 원본파일이름
   (originalFileName), 저장파일이름(storedFileName)
   d. 댓글데이터: 댓글번호(id), 게시글번호(boardId), 작성자(commentWriter), 댓글내
   용(commentContents), 작성일자(createdAt)
2. 테이블 설계 관련
   a. Spring Data JPA 를 잘 활용해봅시다.
   b. pk, fk, not null, default, unique 등 제약 조건 모두 고려
   c. 게시글, 댓글 테이블은 회원 테이블을 참조함.
   d. 댓글, 게시글 첨부파일 테이블은 게시글 테이블을 참조함.
   e. 회원 프로필 사진은 회원 테이블에서 관리해도 되고, 별도의 테이블을 만들어도 됨.
3. 주요 기능
   a. index.html 에는 회원가입, 로그인, 글목록으로 가는 버튼(또는 링크) 있음.
   b. 회원관련 기능
   i. 회원가입
1. 이메일, 비밀번호, 이름, 전화번호, 프로필사진을 입력받음
2. axios로 이메일 중복확인을 함.
3. 비밀번호, 전화번호 등은 정규식 체크도 해볼 것.
   ii. 로그인
   20231006_MemberBoard Project 2
1. 로그인 완료시 페이징처리된 글목록 화면으로 이동함
   iii. 로그아웃
1. 로그아웃 완료시 index 페이지로 이동
   iv. 일반회원
1. 게시글작성, 조회 가능
2. 본인이 작성한 글에 대해서만 글 상세조회시 수정, 삭제 버튼이 보임.
   v. 관리자
1. 관리자 아이디는 admin 으로 함.
2. 관리자용 페이지(admin.html)가 따로 있음.
3. 관리자 페이지에서 회원 목록 페이지로 이동할 수 있음.
4. 회원목록 페이지에서 회원 삭제 가능함.
   c. 게시판 관련 기능
   i. 글쓰기 기능
1. 글쓰기할 때 작성자는 따로 입력하지 않음. 글쓰기 화면 출력되면 로그인
   이메일이 작성자 칸에 입력되어 있음.
   <input type="text" name="boardWriter" class="form-control" th:value
   ="${session.loginEmail}" placeholder="작성자">
2. 제목, 내용, 첨부파일을 입력할 수 있음.
   ii. 페이징 목록 출력 기능
1. 기본적으로 한화면에 5개씩글이 노출되고 페이지번호는 3개가 나옴. (바꿔
   도 됨)
   iii. 글수정, 글삭제 기능
1. 작성자 본인만 수정, 삭제 가능
   <button th:if="${session.loginEmail == board.boardWriter}" class="btn
   btn-warning" onclick="req_fn('update')">수정</button>
2. 관리자는 삭제만 가능
   iv. 검색 기능
   20231006_MemberBoard Project 3
1. 작성자, 제목으로 검색할 수 있음.
2. 검색결과는 페이징 처리
   d. 댓글 관련 기능
   i. 댓글 작성 기능
1. 댓글작성시 글작성과 마찬가지로 작성자는 로그인 이메일이 미리 작성되
   어 있음. 내용만 작성하면 됨.
   e. 마이페이지 관련 기능
   i. 로그인하면 마이페이지로 이동할 수 있는 링크가 보임.
   ii. 마이페이지로 접속하여 회원정보 수정을 할 수 있음.
   iii. 회원정보 수정시 비밀번호를 체크하여 일치하지 않으면 수정처리를 하지 않고
   alert 창을 출력함.
   f. 추가로 고려해볼 수 있는 기능(필수는 아니고 선택사항 입니다.)
   i. 인터셉터를 적용하여 로그인 여부에 따라 접속할 수 있는 페이지 분리
   ii. 페이징 목록에서 한화면에서 볼 수 있는 글 갯수 선택하기
   iii. 마이페이지에서 본인의 프로필사진도 보임.(프로필 사진이 없다면 카카오톡 처
   럼 기본 이미지가 보임.)
   iv. 조회수 순으로 목록 보여주기
   v. 회원 탈퇴 기능
   vi. 댓글 삭제 기능
   vii. 댓글 좋아요, 싫어요 기능 등